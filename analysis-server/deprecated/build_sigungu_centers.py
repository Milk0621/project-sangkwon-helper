import os
import pymysql
import math
from shapely import wkb                 # DB에서 받은 바이너리(WKB)를 Shapely 객체로 변환
from shapely.ops import unary_union     # 여러 폴리곤을 '하나'로 합치기
from dotenv import load_dotenv

# 1) .env에서 DB 접속정보 읽기
load_dotenv()
DB_HOST = os.getenv("DB_HOST")
DB_PORT = int(os.getenv("DB_PORT"))
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")

def normalize(lng, lat):
    """좌표 정합성 체크 + 자동 스왑 + 반올림. 문제 있으면 None 반환."""
    # 숫자/유한수 검사
    if not (isinstance(lng, (int, float)) and isinstance(lat, (int, float))):
        return None
    if not (math.isfinite(lng) and math.isfinite(lat)):
        return None

    # 위도/경도 범위 바탕으로 스왑 감지
    # 정상: |lat|<=90, |lng|<=180
    if abs(lat) > 90 and abs(lng) <= 90 and abs(lat) <= 180:
        lng, lat = lat, lng  # 뒤바뀐 것으로 판단하고 교체

    # 최종 범위 검증
    if not (-180 <= lng <= 180 and -90 <= lat <= 90):
        return None

    # 필요 이상 정밀도는 잘라서 저장
    return float(f"{lng:.7f}"), float(f"{lat:.7f}")

def main():
    conn = pymysql.connect(
        host=DB_HOST,
        port=DB_PORT,
        user=DB_USER,
        password=DB_PASSWORD,
        database=DB_NAME,
        autocommit=False,
        charset="utf8mb4",
    )

    try:
        # emd에서 '구별로' 동 경계를 가져오기
        with conn.cursor() as cur:
            # signguCd가 NULL인 행은 제외 (안전)
            cur.execute("""
                SELECT signguCd, ST_AsBinary(geom)
                FROM emd
                WHERE signguCd IS NOT NULL
                ORDER BY signguCd
            """)
            rows = cur.fetchall()
        print(f"총 {len(rows):,}개의 동 폴리곤을 읽음")

        # rows 형식: [(signguCd, wkb_bytes), ...]
        # 같은 signguCd끼리 모아두기 (딕셔너리: 구코드 -> [폴리곤들])
        grouped = {}  # 예: {"11680": [Polygon, Polygon, ...], ...}
        for sig, wkb_bytes in rows:
            # WKB(바이너리)를 Shapely Geometry 객체로 변환
            geom = wkb.loads(wkb_bytes)
            # 딕셔너리에 리스트가 없으면 만들어주기
            if sig not in grouped:
                grouped[sig] = []
            grouped[sig].append(geom)


        # 구 경계 = (그 구에 속한 모든 동 폴리곤) 합치기 → 내부 대표점 계산
        print("구 대표점 계산 시작...")
        centers = []  # [(center_lng, center_lat, signguCd), ...] 업데이트용
        bad = []
        for sig, geoms in grouped.items():
            # 동 폴리곤들을 하나로 합치기 (unary_union - 여러 공간 데이터의 합집합을 구함)
            union_geom = unary_union(geoms)

            # representative_point(): 폴리곤 '내부'의 점을 준다 (centroid와 다름)
            pt = union_geom.representative_point()
            lng_lat = normalize(pt.x, pt.y)
            if lng_lat is None:
                bad.append(sig)
                continue
            lng, lat = lng_lat
            centers.append((lng, lat, sig))

        print(f"대표점 계산 완료: {len(centers):,} 구")

        # C. area_center_gu 테이블에 좌표 업데이트
        with conn.cursor() as cur:
            cur.executemany("""
                UPDATE area_center_gu
                   SET center_lng = %s,
                       center_lat = %s
                 WHERE signguCd = %s
            """, centers)
        conn.commit()
        print("업데이트 완료!")

    except Exception as e:
        # 에러가 나면 롤백하고 에러 내용 출력
        conn.rollback()
        print("문제 발생")
        print(e)

    finally:
        conn.close()

if __name__ == "__main__":
    main()