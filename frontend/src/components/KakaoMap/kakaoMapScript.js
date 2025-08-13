const loadKakaoMap = () =>
  new Promise((resolve, reject) => {
    const key = process.env.REACT_APP_KAKAO_MAP_KEY;
    if (!key) return reject(new Error("KAKAO KEY 없음"));

    // 이미 로드되어 있으면 통과
    if (window.kakao && window.kakao.maps) {
      window.kakao.maps.load(() => resolve(window.kakao));
      return;
    }

    // 이미 스크립트 태그가 있으면 onload만 기다림
    let script = document.getElementById("kakao-map-sdk");
    if (!script) {
      script = document.createElement("script");
      script.id = "kakao-map-sdk";
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false`;
      script.async = true;
      document.head.appendChild(script);
    }

    script.onload = () => {
      if (!(window.kakao && window.kakao.maps)) {
        reject(new Error("kakao.maps 없음"));
        return;
      }
      window.kakao.maps.load(() => resolve(window.kakao));
    };
    script.onerror = () => reject(new Error("카카오맵 로딩 실패"));
  });

export default loadKakaoMap;
