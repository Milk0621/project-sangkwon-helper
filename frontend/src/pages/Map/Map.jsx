import { useCallback, useEffect, useRef, useState } from "react";
import KakaoMap from "../../components/KakaoMap/KakaoMap";
import styles from "./Map.module.css";
import api from "../../api/api";

function Map() {
    const [sido, setSido] = useState("");
    const [sidos, setSidos] = useState([]);
    const [sigungu, setSigungu] = useState("");
    const [sigunguList, setSigunguList] = useState([]);
    const [dong, setDong] = useState("");
    const [dongList, setDongList] = useState([]);
    const [region, setRegion]= useState("");

    const [category, setCategory] = useState("ALL");
    const [sort, setSort] = useState("BIZ_DESC"); // 상가수 많은순 기본

    const [isSidoOpen, setIsSidoOpen] = useState(false);
    const [isSigunguOpen, setIsSigunguOpen] = useState(false);

    const sidoWrapRef = useRef(null);
    const sigunguWrapRef = useRef(null);

    // 시/도 목록
    const fetchSido = useCallback(async () => {
        try {
            const res = await api.get("/areas/sidos");
            console.log(res.data.name);
            const list = Array.isArray(res.data) ? res.data : (res.data?.items ?? []);
            setSidos(list);
        } catch (err) {
            console.error("시/도 불러오기 실패", err);
            setSidos([]);
        }
    }, []);

    // 시군구 목록
    const fetchSigungus = useCallback(async (sidoName) => {
        if (!sidoName) return;
        try {
            const encoded = encodeURIComponent(sidoName);
            const res = await api.get(`/areas/${encoded}/sigungus`);
            console.log(res.data);
            const items = Array.isArray(res.data) ? res.data : res.data?.items;
            setSigunguList(Array.isArray(items) ? items : []);
            setSigungu(""); // 이전 선택 초기화
        } catch (err) {
            console.error("시군구 불러오기 실패", err);
            setSigunguList([]);
        }
    }, [category, sort]);

    // 시/도 토글
    const toggleSido = useCallback( async (e) => {
        e.preventDefault(); // form submit 방지
        if (!isSidoOpen && sidos.length === 0) {
            await fetchSido();
        }
        setIsSidoOpen((prev) => !prev);
        // 다른 드롭다운 닫기
        setIsSigunguOpen(false);
    }, [isSidoOpen, sidos.length, fetchSido]);

    // 시군구 토글
    const toggleSigungu = useCallback(async (e) => {
        e.preventDefault();
        if (!sido) return; // 시/도 미선택 시 열지 않음
        if (!isSigunguOpen && sigunguList.length === 0) {
            await fetchSigungus(sido);
        }
        setIsSigunguOpen((prev) => !prev);
        // 다른 드롭다운 닫기
        setIsSidoOpen(false);
    }, [isSigunguOpen, sigunguList.length, fetchSigungus, sido]);

    // 시/도 선택
    const handleSelectSido = (name) => {
        setSido(name);
        setIsSidoOpen(false);
        setSigungu("");
        setSigunguList([]);

        fetchSigungus(name);    // 시군구 미리 불러오기
    };

    // 시군구 선택
    const handleSelectSigungu = (name) => {
        setSigungu(name);
        setIsSigunguOpen(false);
    }

    // 검색 시 동 목록 불러오기
    const fetchSearch = async () => {
        try {
            const res = await api.get(`/areas/${sido}/${sigungu}/dong`);
            console.log(res.data);
            const items = Array.isArray(res.data) ? res.data : res.data?.items;
            setDongList(items);
            setRegion(`${sido} ${sigungu}`);
        } catch (err) {
            console.error("동 불러오기 실패", err);
            setDongList([]);
        }
    }

    // 동 선택
    const handleSelectDong = (name) => {
        setDong(name);
        // 좌표 불러와서 지도 표시
    }

    // 외부 클릭시 닫기
    useEffect(() => {
        const onClickOutside = (e) => {
            if (sidoWrapRef.current && !sidoWrapRef.current.contains(e.target)) {
                setIsSidoOpen(false);
            }
            if (sigunguWrapRef.current && !sigunguWrapRef.current.contains(e.target)) {
                setIsSigunguOpen(false);
            }
        };
        if (isSidoOpen || isSigunguOpen) {
            document.addEventListener("mousedown", onClickOutside);
        }
        return () => document.removeEventListener("mousedown", onClickOutside);
    }, [isSidoOpen, isSigunguOpen]);
    
    return(
        <>
            <div className="wrap margin">
                <div className={styles.title}>
                    <h1>지역별 상가 현황</h1>
                    <p>행정동별 상가 분포와 상권 정보를 확인해보세요</p>
                </div>

                <div className={styles.searchBox}>
                    <div className={styles.search} >
                        <div className={styles.inputWrap} ref={sidoWrapRef}>
                            <button 
                                type="button"
                                className={styles.filterBar}
                                onClick={toggleSido}
                            >
                                {sido ? sido : "시/도를 선택해주세요"}
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
                        <div className={styles.inputWrap}>
                            <button 
                                type="button"
                                className={styles.filterBar}
                                onClick={toggleSigungu}
                            >
                                {sigungu ? sigungu : "시/군/구를 선택해주세요"}
                            </button>
                            {isSigunguOpen && (
                                <ul className={styles.dropdown} ref={sigunguWrapRef}>
                                    {sigunguList.map((item, idx) => (
                                        <li
                                            key={item.name}
                                            className={styles.option}
                                            onClick={() => handleSelectSigungu(item.name)}
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

                        <button className={styles.searchBtn} onClick={() => fetchSearch()}>검색</button>
                    </div>
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
                                <div className={styles.adong}>
                                    <div>
                                        <p>{sigungu} {item.name}</p>
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