import { useEffect, useRef } from "react";
import loadKakaoMap from "./kakaoMapScript";
import styles from "./KakaoMap.module.css";

function KakaoMap({ filters, selectedAdong }) {
    const containerRef = useRef(null);
    const mapRef = useRef(null);
    
    // 마운트 시 한 번만 지도 초기화
    useEffect(() => {
        let mounted = true;

        (async () => {
            try {
                await loadKakaoMap(); // <= 반드시 기다림
                if (!mounted || mapRef.current || !containerRef.current) return;

                const { kakao } = window;
                mapRef.current = new kakao.maps.Map(containerRef.current, {
                    center: new kakao.maps.LatLng(37.50614187, 127.04414706),
                    level: 5,
                });
                console.log("기본 지도 불러옴");
            } catch (err) {
                console.error("기본 지도 불러오기 실패", err);
            }
    })();

    return () => { mounted = false; };
    }, []);

    // 선택된 행정동으로 이동
    useEffect(() => {
        if (!mapRef.current || !selectedAdong) return;
        const { centerLat, centerLng } = selectedAdong || {};
        if (!centerLat || !centerLng) return;

        const { kakao } = window;
        const pos = new kakao.maps.LatLng(centerLat, centerLng);
        mapRef.current.setLevel(5);
        mapRef.current.panTo(pos);
        console.log("좌표", pos.toString());
    }, [selectedAdong]);

        return (<div ref={containerRef} className={styles.kakaoMap} />);
    }

export default KakaoMap;