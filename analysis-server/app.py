import os
import pymysql
from flask import Flask, jsonify
from flask_cors import CORS
from dotenv import load_dotenv

load_dotenv()
DB_HOST = os.getenv("DB_HOST")
DB_PORT = int(os.getenv("DB_PORT", "3306"))
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")

def get_conn():
    return pymysql.connect(
        host=DB_HOST,
        port=DB_PORT,
        user=DB_USER,
        password=DB_PASSWORD,
        db=DB_NAME,
        autocommit=True,
        cursorclass=pymysql.cursors.DictCursor,
        charset="utf8mb4"
    )

app = Flask(__name__)
CORS(app)

@app.get("/ping")
def ping():
    return {"ok": True}

# 모든 행정동의 상가 수(동 이름 포함)
@app.get("/areas/biz-counts")
def areas_biz_counts():
    sql = """
    SELECT a.adongCd, a.adongNm, COALESCE(COUNT(b.bizesId), 0) AS bizCount
    FROM adm_area a
    LEFT JOIN biz_master b ON b.adongCd = a.adongCd
    GROUP BY a.adongCd, a.adongNm
    ORDER BY bizCount DESC;
    """
    with get_conn() as conn, conn.cursor() as cur:
        cur.execute(sql)
        rows = cur.fetchall()
    return jsonify(rows)

# 특정 행정동의 상가 수
@app.get("/areas/<adong_cd>/biz-count")
def area_biz_count(adong_cd):
    sql = "SELECT COUNT(*) AS bizCount FROM biz_master WHERE adongCd=%s"
    with get_conn() as conn, conn.cursor() as cur:
        cur.execute(sql, (adong_cd,))
        row = cur.fetchone()
    return jsonify({"adongCd": adong_cd, "bizCount": row["bizCount"]})

if __name__ == "__main__":
    # 개발 중엔 5001 포트
    app.run(host="0.0.0.0", port=5001, debug=True)