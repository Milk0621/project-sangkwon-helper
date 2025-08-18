import { useEffect, useRef, useState } from "react";
import loadKakaoMap from "./kakaoMapScript";
import styles from "./KakaoMap.module.css";

function KakaoMap({ selectedAdong, markers = [], onMarkerClick }) {
    console.log(markers);
    const containerRef = useRef(null);
    const mapRef = useRef(null);
    const [ready, setReady] = useState(false);

    const overlaysRef = useRef([]); // 현재 표시 중인 마커들
    
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
                setReady(true);
                console.log("기본 지도 불러옴");
            } catch (err) {
                console.error("기본 지도 불러오기 실패", err);
            }
    })();

    return () => { mounted = false; };
    }, []);

    // 선택된 행정동으로 이동
    useEffect(() => {
        if (!ready || !mapRef.current || !window.kakao || !window.kakao.maps || !selectedAdong) return;
        
        const { kakao } = window;
        const { centerLat, centerLng } = selectedAdong;
        const pos = new kakao.maps.LatLng(centerLat, centerLng);
        mapRef.current.setLevel(5);
        mapRef.current.panTo(pos);
        console.log("좌표", pos.toString());
    }, [ready, selectedAdong]);

    useEffect(() => {
        if (!ready || !mapRef.current || !window.kakao || !window.kakao.maps) return;
        const { kakao } = window;

        // 이전 마커 제거
        overlaysRef.current.forEach(x => x.setMap(null));
        overlaysRef.current = [];

        if (!markers.length) return;

        markers.forEach(m => {
            const lat = m.lat;
            const lng = m.lng;
            if (lat == null || lng == null) return;

            const position = new kakao.maps.LatLng(lat, lng);

            const el = document.createElement("div");
            el.className = styles.marker;
            el.innerHTML = `
                <div class="${styles.count}">${(m.count ?? 0).toLocaleString()}</div>
                <div class="${styles.name}">${(m.name ?? "")}</div>
            `
            el.style.cursor = "pointer";
            el.onclick = () => {
                mapRef.current.setLevel(5);
                mapRef.current.panTo(position);
                onMarkerClick && onMarkerClick(m);
            }

            const ov = new kakao.maps.CustomOverlay({
                position: position,
                content: el,
                xAnchor: 0.5,
                yAnchor: 1,       // 하단이 좌표 지점
                clickable: true,
            });
            ov.setMap(mapRef.current);
            overlaysRef.current.push(ov);
        });
    }, [ready, markers, onMarkerClick]); 

        return (<div ref={containerRef} className={styles.kakaoMap} />);
    }

export default KakaoMap;