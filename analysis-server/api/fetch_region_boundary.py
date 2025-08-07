import requests
import pandas as pd
import os
from dotenv import load_dotenv

load_dotenv()
SERVICE_KEY = os.getenv("SERVICE_KEY")

url = "https://apis.data.go.kr/B553077/api/open/sdsc2/baroApi"

params = {
    "serviceKey": SERVICE_KEY,
    "resId": "dong",
    "catId": "admi",
    "type": "json"
}

response = requests.get(url, params=params)
print(response)

if response.status_code == 200:
    data = response.json()

    items = data.get("body", {}).get("items", [])
    print(items)

    if not items:
        print("데이터가 없습니다.")
    else:
        df = pd.DataFrame(items)
        print("데이터 수:", len(df))

        BASE_DIR = os.path.dirname(os.path.abspath(__file__))
        DATA_DIR = os.path.join(BASE_DIR, "..", "data")

        os.makedirs(DATA_DIR, exist_ok=True)

        csv_path = os.path.join(DATA_DIR, "행정경계조회.csv")
        df.to_csv(csv_path, index=False, encoding='utf-8-sig')

else:
    print("API 요청 실패:", response.status_code, response.text)