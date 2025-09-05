import { useEffect, useState } from "react";
import KakaoMap from "../../components/KakaoMap/KakaoMap";
import styles from "./Map.module.css";
import api from "../../api/api";
import AreaSearchController from "../../components/AreaSearchController/AreaSearchController";
import { useNavigate } from "react-router-dom";

function Map() {
    const navigate = useNavigate();

    const [appliedSido, setAppliedSido] = useState("");
    const [appliedSigungu, setAppliedSigungu] = useState("");
    const [dong, setDong] = useState("");
    const [dongList, setDongList] = useState([]);
    const [region, setRegion]= useState("");

    const [selectedAdong, setSelectedAdong] = useState(null);
    const [markers, setMarkers] = useState([]);

    // 시군구 중심 좌표 불러오기
    const fetchSigunguCenter = async (sidoName, sigunguName) => {
        try {
            const res = await api.get(`/areas/${sidoName}/${sigunguName}/center`);
            const { centerLat, centerLng } = res.data;
            setSelectedAdong({
                centerLat: centerLat,
                centerLng: centerLng
            });
        } catch (err) {
            console.error("시군구 중심 좌표 불러오기 실패", err);
        }
    }

    // 동 중심 좌표 불러오기
    const fetchDongCenter = async (sidoName, sigunguName, dongName) => {
        try {
            const res = await api.get(`/areas/${sidoName}/${sigunguName}/${dongName}/center`);
            const { centerLat, centerLng } = res.data;
            setSelectedAdong({
                centerLat: centerLat,
                centerLng: centerLng
            });
        } catch (err) {
            console.error("동 중심 좌표 불러오기 실패", err);
        }
    }

    // 동 목록 불러오기
    const handleSearch = async (sidoName, sigunguName) => {
        try {
            const res = await api.get(`/areas/${sidoName}/${sigunguName}/dong`);
            const items = Array.isArray(res.data) ? res.data : res.data?.items;
            setAppliedSido(sidoName);
            setAppliedSigungu(sigunguName);
            setRegion(`${sidoName} ${sigunguName}`);

            const markers = items
                .map(({ name, count, centerLat, centerLng }) => ({
                    name,
                    count: count || 0,
                    lat: centerLat,
                    lng: centerLng,
                }))

            setDongList(items);
            setMarkers(markers);

            fetchSigunguCenter(sidoName, sigunguName);

        } catch (err) {
            console.error("동 불러오기 실패", err);
            setDongList([]);
        }
    }

    // 동 선택
    const handleSelectDong = (name) => {
        setDong(name);
        setRegion(`${appliedSido} ${appliedSigungu}`);
        fetchDongCenter(appliedSido, appliedSigungu, name);
    }

    useEffect(() => {
        handleSearch("서울특별시", "강남구");
    }, [])

    const handleMarkerClick = (marker) => {
        setDong(marker.name);
        setRegion(`${appliedSido} ${appliedSigungu} ${marker.name}`);
        setSelectedAdong({ centerLat: marker.lat, centerLng: marker.lng });
    }

    const handleDetailClick = (e, item) => {
        e.stopPropagation();
        const path = `/map/${appliedSido}/${appliedSigungu}/${item.name}`;
        navigate(path);
    }

    return(
        <>
            <div className="wrap margin">
                <div className={styles.title}>
                    <h1>지역별 상가 현황</h1>
                    <p>행정동별 상가 분포와 상권 정보를 확인해보세요</p>
                </div>

                <div className={styles.searchBox}>
                    <AreaSearchController onSearch={handleSearch} />
                </div>
                <div className={styles.contentBox}>
                    <div className={styles.leftContent}>
                        <div className={styles.leftTop}>
                            <h4> {region} 상가 현황 지도</h4>
                        </div>
                        <KakaoMap 
                            selectedAdong={selectedAdong}
                            markers={markers}
                            onMarkerClick={handleMarkerClick}
                        />
                    </div>
                    <div className={styles.rightContent}>
                        <div>
                            <div className={styles.rightTop}>
                                <h4 style={{fontWeight: "600"}}>지역별 상가 현황</h4>
                                <p>각 행정동별 상가 현황을 확인하세요.</p>
                            </div>
                            {dongList.map((item, idx) => (
                                <div className={styles.adong} onClick={()=>{handleSelectDong(item.name)}}>
                                    <div>
                                        <p>{appliedSigungu} {item.name}</p>
                                        <span>{(item.count || 0).toLocaleString()}개</span>
                                    </div>
                                    <div>
                                        <span>Top 업종: {item.topUpjong || '-'}</span>
                                        <button className={styles.detailBtn} onClick={(e) => handleDetailClick(e, item)}>상세보기</button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Map;