import { useEffect, useRef } from "react";
import loadKakaoMap from "./kakaoMapScript";
import styles from "./KakaoMap.module.css";

function KakaoMap() {
    
    const mapRef = useRef(null);

    useEffect(()=>{
        loadKakaoMap()
            .then((kakao) => {
                const options = {
                    center: new kakao.maps.LatLng(33.45071, 126.570667),
                    level: 3
                }
                new kakao.maps.Map(mapRef.current, options);
            })
            .catch((err) => console.error(err));
    }, [])
    return(
        <div ref={mapRef} className={styles.kakaoMap} />
    )
}

export default KakaoMap;