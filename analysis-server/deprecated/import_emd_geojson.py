import os, json, datetime
import pymysql
from dotenv import load_dotenv

load_dotenv()
DB_HOST = os.getenv("DB_HOST")
DB_PORT = int(os.getenv("DB_PORT"))
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")

BASE_DIR = os.path.dirname(os.path.abspath(__file__))           # 현재 파일이 있는 디렉토리
DATA_DIR = os.path.join(BASE_DIR, "..", "data")                 # ../data 경로
GEOJSON_PATH = os.path.join(DATA_DIR, "bnd_dong_2024.geojson")  # GeoJSON 파일 전체경로

# "YYYYMMDD" → datetime.date
def parse_base_date(s):
    if not s:
        return None
    try:
        return datetime.datetime.strptime(s, "%Y%m%d").date()
    except Exception:
        return None

# 메인 로직
def main():
    # GeoJSON 파일 읽기
    with open(GEOJSON_PATH, "r", encoding="utf-8") as f:
        gj = json.load(f)
    
    # GeoJSON 표준 구조에서 features 배열 꺼내기
    features = gj.get("features", [])
    print(f"features: {len(features)}")

    # DB 연결
    conn = pymysql.connect(
        host=DB_HOST, port=DB_PORT, user=DB_USER, password=DB_PASSWORD,
        database=DB_NAME, autocommit=False, charset="utf8mb4",
    )

    cur = conn.cursor()

    # INSERT SQL
    # - ST_GeomFromGeoJSON(%s): 문자열로 받은 GeoJSON geometry를 공간도형으로 변환
    # - ST_SRID(..., 4326): SRID 4326(WGS84) 지정 (좌표계 메타데이터 보장)
    sql = """
        INSERT INTO emd (base_date, sig_code, dong_code, dong_name, geom)
        VALUES (%s, %s, %s, %s, ST_SRID(ST_GeomFromGeoJSON(%s), 4326))
    """

    # 배치 적재를 위한 버퍼와 파라미터
    # 버퍼(buffer)는 파이썬 코드 안에서 잠깐 쌓아두는 바구니(리스트)를 뜻함. 여기선 batch
    # 버퍼에 모았다가 한 번에 인서트
    batch = []
    BATCH_SIZE = 300
    inserted = 0

    # features를 순회하며 속성/도형을 파싱 → 배치 버퍼에 적재
    for ft in features:
        # 속성 딕셔너리 꺼내기 (없으면 빈 dict)
        props = ft.get("properties", {})

        # geometry 딕셔너리를 GeoJSON 문자열로 직렬화
        # ensure_ascii=False: 한글이 \uXXXX로 깨지지 않게 원문 유지
        geom = json.dumps(ft.get("geometry", None), ensure_ascii=False)

        # 테이블 컬럼에 매핑할 속성값 추출
        adm_cd = str(props.get("ADM_CD", "")).strip()
        adm_nm = str(props.get("ADM_NM", "")).strip()
        base_date = parse_base_date(str(props.get("BASE_DATE", "")).strip())

        # 필수값 부족하면 스킵
        if not geom or not adm_cd or not adm_nm:
            continue
            
        # 시군구 코드: ADM_CD 앞 5자리 (예: 11680 = 서울 강남구)
        sig_code = adm_cd[:5] if len(adm_cd) >= 5 else None

        batch.append((base_date, sig_code, adm_cd, adm_nm, geom))

        # 버퍼가 가득 차면 executemany로 일괄 삽입 후 커밋
        if len(batch) >= BATCH_SIZE:
            cur.executemany(sql, batch)
            conn.commit()
            inserted += len(batch)
            print(f"inserted {inserted} rows...")
            batch.clear()

    # 루프 종료 후 버퍼에 남은 레코드가 있으면 마저 삽입
    if batch:
        cur.executemany(sql, batch)
        conn.commit()
        inserted += len(batch)

    print(f"done. inserted total {inserted} rows.")

    cur.close()
    conn.close()

if __name__ == "__main__":
    main()