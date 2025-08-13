import { useCallback, useEffect, useRef, useState } from "react";
import KakaoMap from "../../components/KakaoMap/KakaoMap";
import styles from "./Map.module.css";
import api from "../../api/api";

function Map() {
    const [sido, setSido] = useState("");
    const [sidos, setSidos] = useState([]);
    const [sigunguList, setSigunguList] = useState([]);
    const [category, setCategory] = useState("ALL");
    const [sort, setSort] = useState("BIZ_DESC"); // 상가수 많은순 기본
    const [isSidoOpen, setIsSidoOpen] = useState(false);

    const dropdownRef = useRef(null);

    const fetchSido = useCallback(async () => {
        try {
            const res = await api.get("/areas/sidos");
            console.log(res.data.name);
            setSidos(res.data);
        } catch (err) {
            console.error("시/도 불러오기 실패", err);
            setSidos([]);
        }
    }, []);

    const fetchSigungus = useCallback(async (sidoName) => {
        if (!sidoName) return;
        try {
            const encoded = encodeURIComponent(sidoName);
            const res = await api.get(`/areas/sidos/${encoded}/sigungus`);
            setSigunguList(res.data);
            console.log(res.data);
        } catch (err) {
            console.error("동 불러오기 실패", err);
        }
    }, [category, sort]);

    const toggleSido = useCallback( async (e) => {
        e.preventDefault(); // form submit 방지
        if (!isSidoOpen && sidos.length === 0) {
            await fetchSido();
        }
        setIsSidoOpen((prev) => !prev);
    }, [isSidoOpen, sidos.length, fetchSido])

    const handleSelectSido = (name) => {
        setSido(name);
        setIsSidoOpen(false);
        fetchSigungus(name);
    };

    useEffect(() => {
        const onClickOutside = (e) => {
        if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
            setIsSidoOpen(false);
        }
        };
        if (isSidoOpen) document.addEventListener("mousedown", onClickOutside);
        return () => document.removeEventListener("mousedown", onClickOutside);
    }, [isSidoOpen]);
    
    return(
        <>
            <div className="wrap margin">
                <div className={styles.title}>
                    <h1>지역별 상가 현황</h1>
                    <p>행정동별 상가 분포와 상권 정보를 확인해보세요</p>
                </div>

                <div className={styles.searchBox}>
                    <form className={styles.search} >
                        <div className={styles.inputWrap} ref={dropdownRef}>
                            <button 
                                type="button"
                                className={styles.filterBar}
                                onClick={toggleSido}
                            >
                                {sido ? sido : "지역을 선택해주세요"}
                            </button>
                            {isSidoOpen && (
                                <ul className={styles.dropdown} >
                                    {sidos.map((item, idx) => (
                                        <li
                                            key={item.name}
                                            className={styles.option}
                                            onClick={() => handleSelectSido(item.name)}
                                        >
                                            {item.name}
                                            <span className={styles.count}>&#40;{item.count}&#41;</span>
                                        </li>
                                    ))}
                                </ul>
                            )}
                        </div>

                        <select
                            className={styles.select}
                            value={category}
                            onChange={(e) => setCategory(e.target.value)}
                        >
                            <option value="ALL">전체</option>
                            <option value="카페">카페</option>
                            <option value="음식점">음식점</option>
                            <option value="편의점">편의점</option>
                            <option value="미용실">미용실</option>
                            <option value="학원">학원</option>
                            <option value="병원">병원</option>
                            <option value="의류점">의류점</option>
                            <option value="기타">기타</option>
                        </select>

                        <select
                            className={styles.select}
                            value={sort}
                            onChange={(e) => setSort(e.target.value)}
                        >
                            <option value="BIZ_DESC">상가수 많은순</option>
                            <option value="BIZ_ASC">상가수 적은순</option>
                            <option value="NAME_ASC">이름 오름차순</option>
                        </select>

                        <button className={styles.searchBtn} type="submit" onClick={() => console.log("[BTN] submit clicked")}>검색</button>
                    </form>
                </div>
                <div className={styles.contentBox}>
                    <div className={styles.leftContent}>
                        <div className={styles.leftTop}>
                            <h4>서울시 상가 현황 지도</h4>
                        </div>
                        <KakaoMap />
                    </div>
                    <div className={styles.rightContent}>
                        <div>
                            <div className={styles.rightTop}>
                                <h4 style={{fontWeight: "600"}}>지역별 상가 현황</h4>
                                <p>총 10개 지역</p>
                            </div>
                            <div className={styles.adong}>
                                <div>
                                    <p>강남구 역삼동</p>
                                    <span>1247개</span>
                                </div>
                                <div>
                                    <span>850만원</span>
                                    <span>15,420명</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Map;