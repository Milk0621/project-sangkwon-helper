import { useState } from "react";
import KakaoMap from "../../components/KakaoMap/KakaoMap";
import styles from "./Map.module.css";
import api from "../../api/api";
import AreaSearchController from "../../components/AreaSearchController/AreaSearchController";

function Map() {
    const [appliedSido, setAppliedSido] = useState("");
    const [appliedSigungu, setAppliedSigungu] = useState("");
    const [dong, setDong] = useState("");
    const [dongList, setDongList] = useState([]);
    const [region, setRegion]= useState("");

    // 동 목록 불러오기
    const handleSearch = async (sidoName, sigunguName) => {
        try {
            const res = await api.get(`/areas/${sidoName}/${sigunguName}/dong`);
            const items = Array.isArray(res.data) ? res.data : res.data?.items;
            setDongList(items);
            setAppliedSido(sidoName);
            setAppliedSigungu(sigunguName);
            setRegion(`${sidoName} ${sigunguName}`);
        } catch (err) {
            console.error("동 불러오기 실패", err);
            setDongList([]);
        }
    }

    // 동 선택
    const handleSelectDong = (name) => {
        setDong(name);
        setRegion(`${appliedSido} ${appliedSigungu} ${name}`);
        // 좌표 불러와서 지도 표시
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
                        <KakaoMap />
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
                                        <span>{item.count}개</span>
                                    </div>
                                    <div>
                                        <span>850만원</span>
                                        <span>15,420명</span>
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