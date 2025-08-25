import os, math
import pymysql
import pandas as pd
from dotenv import load_dotenv

load_dotenv()
DB_HOST = os.getenv("DB_HOST")
DB_PORT = int(os.getenv("DB_PORT"))
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")

BASE_DIR = os.path.dirname(os.path.abspath(__file__))   # 현재 파일이 있는 디렉토리
DATA_DIR = os.path.join(BASE_DIR, "..", "data")         # ../data 경로
AREA_CSV = os.path.join(DATA_DIR, "행정경계조회.csv")
BIZ_CSV  = os.path.join(DATA_DIR, "행정동_상가업소조회.csv")

def _read_csv_safe(path: str, need_cols: list[str]) -> pd.DataFrame:
    """
    - 모든 컬럼을 우선 문자열로 읽어서 mixed-type 경고 제거
    - 빈 문자열을 None(SQL NULL)로, NaN도 None으로 변환
    - 필요한 컬럼이 없으면 생성
    """
    # dtype=str + keep_default_na=False 로 "NaN","NULL","" 등을 문자로 유지
    df = pd.read_csv(
        path,
        encoding="utf-8-sig",
        dtype=str,
        keep_default_na=False,
        low_memory=False
    )

    # 필요 컬럼이 없으면 생성
    for c in need_cols:
        if c not in df.columns:
            df[c] = None

    # 최종 컬럼 순서 맞추기
    df = df[need_cols]

    # 빈문자 → None
    df = df.replace({"": None})

    # pandas NaN → None (혹시 모를 NaN을 안전하게 NULL로)
    df = df.where(pd.notnull(df), None)

    return df

def insert_biz_master(batch: int = 10000):
    # 최종으로 DB에 넣을 컬럼(테이블 스키마와 동일; timestamp 컬럼 제외)
    final_cols = [
        "bizesId","bizesNm","brchNm",
        "indsLclsCd","indsLclsNm","indsMclsCd","indsMclsNm","indsSclsCd","indsSclsNm",
        "ksicCd","ksicNm",
        "ctprvnCd","ctprvnNm","signguCd","signguNm","adongCd","adongNm",
        "lon","lat","rdnmAdr","lnoAdr"
    ]

    df = _read_csv_safe(BIZ_CSV, final_cols)

    # 필수키 누락 행 제거
    df = df.dropna(subset=["bizesId", "adongCd"])

    # 길이 제한 보정 (DB 스키마와 일치)
    df["bizesNm"] = df["bizesNm"].astype(object).where(df["bizesNm"].notnull(), None)
    df["brchNm"]  = df["brchNm"].astype(object).where(df["brchNm"].notnull(), None)
    df["rdnmAdr"] = df["rdnmAdr"].astype(object).where(df["rdnmAdr"].notnull(), None)
    df["lnoAdr"]  = df["lnoAdr"].astype(object).where(df["lnoAdr"].notnull(), None)

    df["bizesNm"] = df["bizesNm"].apply(lambda x: x[:200] if isinstance(x, str) else x)
    df["brchNm"]  = df["brchNm"].apply(lambda x: x[:200] if isinstance(x, str) else x)
    df["rdnmAdr"] = df["rdnmAdr"].apply(lambda x: x[:300] if isinstance(x, str) else x)
    df["lnoAdr"]  = df["lnoAdr"].apply(lambda x: x[:300] if isinstance(x, str) else x)

    # 좌표 숫자 변환 (틀리면 NaN → None)
    for col in ["lon", "lat"]:
        if col in df.columns:
            num = pd.to_numeric(df[col], errors="coerce")
            # object로 캐스팅 후 NaN → None
            df[col] = num.astype(object)
            df[col] = df[col].where(pd.notnull(df[col]), None)

    # executemany에 들어갈 값 튜플
    values = list(df[final_cols].itertuples(index=False, name=None))

    sql = f"""
    INSERT INTO biz_master
    ({",".join(final_cols)})
    VALUES ({",".join(["%s"]*len(final_cols))})
    ON DUPLICATE KEY UPDATE
      bizesNm=VALUES(bizesNm),
      brchNm=VALUES(brchNm),
      indsLclsCd=VALUES(indsLclsCd),
      indsLclsNm=VALUES(indsLclsNm),
      indsMclsCd=VALUES(indsMclsCd),
      indsMclsNm=VALUES(indsMclsNm),
      indsSclsCd=VALUES(indsSclsCd),
      indsSclsNm=VALUES(indsSclsNm),
      ksicCd=VALUES(ksicCd),
      ksicNm=VALUES(ksicNm),
      ctprvnCd=VALUES(ctprvnCd),
      ctprvnNm=VALUES(ctprvnNm),
      signguCd=VALUES(signguCd),
      signguNm=VALUES(signguNm),
      adongCd=VALUES(adongCd),
      adongNm=VALUES(adongNm),
      lon=VALUES(lon),
      lat=VALUES(lat),
      rdnmAdr=VALUES(rdnmAdr),
      lnoAdr=VALUES(lnoAdr),
      updated_at=CURRENT_TIMESTAMP
    """

    total = len(values)
    if total == 0:
        print("[biz_master] no rows to insert.")
        return

    # DB 연결 및 배치 실행
    conn = pymysql.connect(
        host=DB_HOST, port=DB_PORT, user=DB_USER, password=DB_PASSWORD,
        database=DB_NAME, charset="utf8mb4", autocommit=False
    )
    try:
        n_batches = math.ceil(total / batch)
        with conn.cursor() as cur:
            for i in range(n_batches):
                chunk = values[i*batch:(i+1)*batch]
                cur.executemany(sql, chunk)
                print(f"[biz_master] batch {i+1}/{n_batches} inserted: {len(chunk)}")
        conn.commit()
        print(f"[biz_master] done: {total}")
    except Exception as e:
        conn.rollback()
        raise
    finally:
        conn.close()

def insert_adm_area():
    need_cols = ["ctprvnCd","ctprvnNm","signguCd","signguNm","adongCd","adongNm"]
    df = _read_csv_safe(AREA_CSV, need_cols)
    sql = """
    INSERT INTO adm_area
    (ctprvnCd, ctprvnNm, signguCd, signguNm, adongCd, adongNm)
    VALUES (%s,%s,%s,%s,%s,%s)
    ON DUPLICATE KEY UPDATE
      ctprvnNm=VALUES(ctprvnNm),
      signguNm=VALUES(signguNm),
      adongNm=VALUES(adongNm)
    """
    values = list(df[need_cols].itertuples(index=False, name=None))
    conn = pymysql.connect(
        host=DB_HOST, port=DB_PORT, user=DB_USER, password=DB_PASSWORD,
        database=DB_NAME, charset="utf8mb4", autocommit=False
    )
    try:
        with conn.cursor() as cur:
            cur.executemany(sql, values)
        conn.commit()
        print(f"[adm_area] inserted/updated: {len(values)}")
    finally:
        conn.close()

if __name__ == "__main__":
    insert_adm_area()
    insert_biz_master()