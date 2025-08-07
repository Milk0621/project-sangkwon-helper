import requests
import pandas as pd
import os
from dotenv import load_dotenv
import time

# env에서 API 키 불러오기
load_dotenv()
SERVICE_KEY = os.getenv("SERVICE_KEY")

# 경로 설정
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_DIR = os.path.join(BASE_DIR, "..", "data")
INPUT_CSV = os.path.join(DATA_DIR, "행정경계조회.csv")  # adong 코드들이 있는 파일
OUTPUT_CSV = os.path.join(DATA_DIR, "행정동_상가업소조회.csv")

# 행정경계 데이터 불러오기
df_adong = pd.read_csv(INPUT_CSV)
# print(df_adong)

adong_codes = df_adong["adongCd"].drop_duplicates().astype(str).tolist()
# print(adong_codes)

all_stores = []
for idx, adong in enumerate(adong_codes):
    print(f"[{idx + 1}/{len(adong_codes)}] 요청 중: adongCd={adong}")

    url = "https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInDong"
    params = {
        "serviceKey": SERVICE_KEY,
        "pageNo": "1",
        "numOfRows": "1000",
        "divId": "adongCd",
        "key": adong,
        "type": "json"
    }

    try:
        response = requests.get(url, params=params)
        time.sleep(0.3)  # API 제한 방지

        if response.status_code == 200:
            data = response.json()
            items = data.get("body", {}).get("items", [])
            if items:
                all_stores.extend(items)
        else:
            print(f"요청 실패 ({response.status_code}): {adong}")
    except Exception as e:
        print(f"예외 발생 ({adong}): {e}")

# 결과 저장
if all_stores:
    df = pd.DataFrame(all_stores)
    os.makedirs(DATA_DIR, exist_ok=True)
    df.to_csv(OUTPUT_CSV, index=False, encoding="utf-8-sig")
    print(f"총 {len(df)}개 업소 저장 완료 → {OUTPUT_CSV}")
else:
    print("데이터가 없음")