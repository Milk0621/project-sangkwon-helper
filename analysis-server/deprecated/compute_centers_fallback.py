import os, pymysql
from shapely import wkb     # WKB(바이너리) -> Shapely Geometry 변환
from shapely.validation import make_valid   # invalid geometry 보정
from dotenv import load_dotenv

load_dotenv()
DB_HOST = os.getenv("DB_HOST")
DB_PORT = int(os.getenv("DB_PORT"))
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")

# 한 번에 처리할 로우 수(청크 크기)
# 너무 크면 메모리/네트워크 부담↑, 너무 작으면 왕복비용↑. 300~1000 권장.
CHUNK = 800

# 특정 컬럼 존재 여부 확인
def col_exists(conn, table, col):
    with conn.cursor() as cur:
        cur.execute("""
            SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
            WHERE TABLE_SCHEMA=%s AND TABLE_NAME=%s AND COLUMN_NAME=%s
        """, (DB_NAME, table, col))
        return cur.fetchone()[0] > 0
    
# center/center_lon/center_lat 컬럼 보장
def ensure_cols(conn):
    with conn.cursor() as cur:
        if not col_exists(conn, "emd", "center"):
            cur.execute("ALTER TABLE emd ADD COLUMN center POINT NULL")
        if not col_exists(conn, "emd", "center_lon"):
            cur.execute("ALTER TABLE emd ADD COLUMN center_lon DOUBLE NULL")
        if not col_exists(conn, "emd", "center_lat"):
            cur.execute("ALTER TABLE emd ADD COLUMN center_lat DOUBLE NULL")
        conn.commit()

# 대표점 계산(가능한 한 내부점)
def rep_point_safe(geom):
    """대표점 계산을 최대한 안전하게"""
    try:
        return geom.representative_point()
    except Exception:
        pass
    try:
        g2 = make_valid(geom)
        return g2.representative_point()
    except Exception:
        pass
    # 최후 수단: centroid (밖으로 나갈 수 있지만 거의 해결)
    return geom.centroid

# 메인 로직
def main():
    conn = pymysql.connect(
        host=DB_HOST, port=DB_PORT, user=DB_USER,
        password=DB_PASSWORD, database=DB_NAME,
        autocommit=False, charset="utf8mb4"
    )

    # 필요한 컬럼 확보
    ensure_cols(conn)

    # 업데이트 쿼리 준비
    # - center: ST_GeomFromText('POINT(lon lat)', 4326)로 SRID 4326 명시
    # - center_lon/center_lat: 숫자형으로 별도 저장 (지도 마커 렌더링시 속도↑)
    update_sql = """
        UPDATE emd
           SET center = ST_GeomFromText(%s, 4326),
               center_lon = %s,
               center_lat = %s
         WHERE id = %s
    """

    total_updated, total_skipped = 0, 0     # 통계용 카운터

    # 남아있는 NULL을 '없어질 때까지' 청크 반복 처리
    while True:
        # center IS NULL 인 행을 id 순으로 CHUNK 만큼 가져오기
        # ST_AsBinary(geom)으로 WKB(바이너리) 형태로 가져오면 문자열(GeoJSON)보다 가벼움
        with conn.cursor() as cur:
            cur.execute("""
                SELECT id, ST_AsBinary(geom)
                  FROM emd
                 WHERE center IS NULL
                 ORDER BY id
                 LIMIT %s
            """, (CHUNK,))
            rows = cur.fetchall()

        # 더 가져올게 없으면 종료
        if not rows:
            break

        batch = []          # executemany()에 줄 값 모음
        skipped_this = 0    # 이번 청크에서 스킵된 건수 (빈 도형/오류 등)

        # 한 행씩 WKB -> Shapely geometry 변환 후 대표점 계산
        for rid, wkb_bytes in rows:
            try:
                g = wkb.loads(wkb_bytes)    # 바이너리를 Shapely Geometry로 파싱
                if g.is_empty:              # 빈 도형은 스킵
                    skipped_this += 1
                    continue
                pt = rep_point_safe(g)      # 내부 대표점 계산(위 유틸 함수)
                lon, lat = pt.x, pt.y       # POINT의 x=경도(lon), y=위도(lat)

                # MySQL WKT(문자열) 포맷으로 준비: "POINT(lon lat)"
                batch.append((f"POINT({lon} {lat})", lon, lat, rid))

            except Exception:
                # 파싱/계산 도중 문제가 난 레코드는 스킵
                skipped_this += 1

        # 모인 배치를 일괄 업데이트
        if batch:
            with conn.cursor() as cur2:
                cur2.executemany(update_sql, batch)     # 여러 행 한 번에 업데이트
                conn.commit()                           
            total_updated += len(batch)                 # 누적 업데이트 수 증가

        total_skipped += skipped_this                   # 누적 스킵 수 증가
        print(f"chunk done: +{len(batch)} updated, +{skipped_this} skipped | total updated={total_updated}")

    conn.close()
    print(f"ALL DONE. total updated={total_updated}, total skipped={total_skipped}")

if __name__ == "__main__":
    main()
