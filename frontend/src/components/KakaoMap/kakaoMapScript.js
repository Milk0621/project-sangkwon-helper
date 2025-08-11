const loadKakaoMapScript = () => {
    const kakaoMapKey = process.env.REACT_APP_KAKAO_MAP_KEY;

    return new Promise((resolve, reject) => {
        if (!kakaoMapKey) {
            reject(new Error("카카오맵 키 오류"));
            return;
        }
        
        const script = document.createElement("script");
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoMapKey}&autoload=false`;
        script.async = true;
        script.onload = () => {
            window.kakao.maps.load(() => {
                resolve(window.kakao);
            });
        };
        script.onerror = () => reject(new Error("카카오맵 불러오기 실패"));
        document.head.appendChild(script);
    });
};

export default loadKakaoMapScript;