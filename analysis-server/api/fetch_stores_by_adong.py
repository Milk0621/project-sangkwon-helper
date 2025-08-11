import requests
import pandas as pd
import os
from dotenv import load_dotenv
import time
from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry

# env에서 API 키 불러오기
load_dotenv()
SERVICE_KEY = os.getenv("SERVICE_KEY")

# 경로 설정
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_DIR = os.path.join(BASE_DIR, "..", "data")
INPUT_CSV = os.path.join(DATA_DIR, "행정경계조회.csv")  # adong 코드들이 있는 파일
OUTPUT_CSV = os.path.join(DATA_DIR, "행정동_상가업소조회.csv")

# API
BASE_URL = "https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInDong"
NUM_ROWS = 1000

# 세션 설정 (재시도 로직 포함)
session = requests.Session()
retry = Retry(
    total=5,  # 최대 재시도 횟수
    backoff_factor=0.3,  # 재시도 간 대기 시간 조정
    status_forcelist=[500, 502, 503, 504],  # 특정 상태 코드에서 재시도
)
adapter = HTTPAdapter(max_retries=retry)
session.mount("https://", adapter)
session.mount("http://", adapter)

def main():
    # adong 목록
    adongs = pd.read_csv(INPUT_CSV, dtype={"adongCd": str})["adongCd"].dropna().unique()

    all_items = []
    for i, adong in enumerate(adongs, start=1):
        print(f"[{i}/{len(adongs)}] adongCd={adong}")
        page = 1
        while True:
            params = {
                "serviceKey": SERVICE_KEY,
                "divId": "adongCd",
                "key": adong,
                "pageNo": str(page),
                "numOfRows": str(NUM_ROWS),
                "type": "json",
            }
            try:
                r = session.get(BASE_URL, params=params, timeout=60)  # session.get 사용
                if r.status_code != 200:
                    print(f"  요청 실패: {r.status_code}")
                    break

                # 응답이 비어있거나 JSON 형식이 아닌 경우 처리
                try:
                    body = r.json().get("body", {})
                except requests.exceptions.JSONDecodeError:
                    print(f"  JSONDecodeError 발생: 응답이 비어있거나 잘못된 형식입니다. adongCd={adong} 건너뛰기.")
                    break  # JSONDecodeError가 발생하면 해당 adongCd를 건너뛰고 종료

                items = body.get("items", []) or []
                if not items:
                    print("데이터 없음 → 건너뛰고 다음 항목으로 이동")
                    break  # 데이터를 건너뛰고 다음 adongCd로 넘어감

                print(f"수집 {len(items)}건")
                all_items.extend(items)  # 전체 리스트에 이번 페이지 데이터 추가

                # 1000건보다 적게 오면 마지막 페이지이므로 반복 종료
                if len(items) < NUM_ROWS:
                    break

                page += 1
                time.sleep(0.2)  # 호출 간격
            except requests.exceptions.RequestException as e:
                print(f"요청 중 오류 발생: {e}. {adong} 건너뛰기.")
                break  # 다른 오류가 발생한 경우 해당 adongCd를 건너뛰고 종료

    # 저장(+ bizesId 기준 중복 제거)
    df = pd.DataFrame(all_items)
    if "bizesId" in df.columns:
        df = df.drop_duplicates(subset=["bizesId"])

    os.makedirs(DATA_DIR, exist_ok=True)
    df.to_csv(OUTPUT_CSV, index=False, encoding="utf-8-sig")
    print(f"[완료] {len(df):,}건 저장 → {OUTPUT_CSV}")

if __name__ == "__main__":
    main()