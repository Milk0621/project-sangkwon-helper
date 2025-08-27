import os
import pandas as pd
import requests
import pymysql
from datetime import datetime
from dotenv import load_dotenv

load_dotenv()
SERVICE_KEY = os.getenv("SERVICE_KEY")

DB_HOST = os.getenv("DB_HOST")
DB_PORT = int(os.getenv("DB_PORT"))
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")

url = "https://apis.data.go.kr/B553077/api/open/sdsc2/smallUpjongList"
params = {
    "serviceKey" : SERVICE_KEY,
    "type": "json"
}

response = requests.get(url, params=params)
print(response)

if response.status_code == 200:
    data = response.json()

    items = data.get("body", {}).get("items", [])

    conn = pymysql.connect(
        host=DB_HOST,
        port=DB_PORT,
        user=DB_USER,
        password=DB_PASSWORD,
        database=DB_NAME,
        autocommit=False,
        charset="utf8mb4"
    )

    sql = """
    INSERT INTO raw_category (
        indsLclsCd, indsLclsNm,
        indsMclsCd, indsMclsNm,
        indsSclsCd, indsSclsNm,
        stdrDt
    )
    VALUES (%s, %s, %s, %s, %s, %s, %s)
    """

    with conn.cursor() as cur:
        for row in items:
            stdr = row.get("stdrDt")
            # 문자열을 DATE 타입으로 변환
            try:
                stdr_dt = datetime.strptime(stdr, "%Y-%m-%d").date()
            except:
                stdr_dt = datetime.strptime(stdr, "%Y%m%d").date()

            cur.execute(sql, (
                row["indsLclsCd"], row["indsLclsNm"],
                row["indsMclsCd"], row["indsMclsNm"],
                row["indsSclsCd"], row["indsSclsNm"],
                stdr_dt
            ))
    conn.commit()
    conn.close()

print(len(items))
