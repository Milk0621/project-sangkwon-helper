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
