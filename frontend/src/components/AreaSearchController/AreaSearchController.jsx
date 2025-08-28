import { useCallback, useEffect, useRef, useState } from "react";
import api from "../../api/api";
import styles from "./AreaSearchController.module.css";

function AreaSearchController({ onSearch }){
    const [sido, setSido] = useState("");
    const [sidos, setSidos] = useState([]);
    const [sigungu, setSigungu] = useState("");
    const [sigunguList, setSigunguList] = useState([]);

    const [lcls, setLcls] = useState("");
    const [lclsList, setLclsList] = useState([]);

    const [isSidoOpen, setIsSidoOpen] = useState(false);
    const [isSigunguOpen, setIsSigunguOpen] = useState(false);
    const [isLclsOpen, setIsLclsOpen] = useState(false);

    const sidoWrapRef = useRef(null);
    const sigunguWrapRef = useRef(null);
    const lclsWrapRef = useRef(null);

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
    });

    // 시군구 목록
    const fetchSigungus = useCallback(async (sidoName) => {
        if (!sidoName) return;
        try {
            const res = await api.get(`/areas/${sidoName}/sigungus`);
            console.log(res.data);
            const items = Array.isArray(res.data) ? res.data : res.data?.items;
            setSigunguList(Array.isArray(items) ? items : []);
            setSigungu(""); // 이전 선택 초기화
        } catch (err) {
            console.error("시군구 불러오기 실패", err);
            setSigunguList([]);
        }
    });

    // 대분류 목록
    const fetchLcls = useCallback(async () => {
        try {
        const res = await api.get("/category/large"); // << 여기 연결
        const items = Array.isArray(res.data) ? res.data : res.data?.items;
        setLclsList(Array.isArray(items) ? items : []);
        } catch (err) {
        console.error("대분류 불러오기 실패", err);
        setLclsList([]);
        }
    }, []);

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

    // 대분류 토글
    const toggleLcls = useCallback(
        async (e) => {
        e.preventDefault();
        if (!isLclsOpen && lclsList.length === 0) {
            await fetchLcls();
        }
        setIsLclsOpen((prev) => !prev);
        setIsSidoOpen(false);
        setIsSigunguOpen(false);
        },
        [isLclsOpen, lclsList.length, fetchLcls]
    );

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

    const handleSelectLcls = (item) => {
        setLcls(item.name);
        // setLclsCode(item.code);
        setIsLclsOpen(false);
    };

    // 외부 클릭시 닫기
    useEffect(() => {
        const onClickOutside = (e) => {
            if (sidoWrapRef.current && !sidoWrapRef.current.contains(e.target)) {
                setIsSidoOpen(false);
            }
            if (sigunguWrapRef.current && !sigunguWrapRef.current.contains(e.target)) {
                setIsSigunguOpen(false);
            }
            if (lclsWrapRef.current && !lclsWrapRef.current.contains(e.target)) {
                setIsLclsOpen(false);
            }
        };
        if (isSidoOpen || isSigunguOpen || isLclsOpen) {
            document.addEventListener("mousedown", onClickOutside);
        }
        return () => document.removeEventListener("mousedown", onClickOutside);
    }, [isSidoOpen, isSigunguOpen]);

    return(
        <div className={styles.search}>
            <div className={styles.inputWrap} ref={sidoWrapRef}>
                <button type="button" className={styles.filterBar} onClick={toggleSido}>
                    {sido || "시/도를 선택해주세요"}
                </button>
                {isSidoOpen && (
                <ul className={styles.dropdown}>
                    {sidos.map((item) => (
                    <li key={item.name} className={styles.option} onClick={() => handleSelectSido(item.name)}>
                        {item.name}
                        <span className={styles.count}>&#40;{item.count}&#41;</span>
                    </li>
                    ))}
                </ul>
                )}
            </div>

            <div className={styles.inputWrap} ref={sigunguWrapRef}>
                <button type="button" className={styles.filterBar} onClick={toggleSigungu}>
                    {sigungu || "시/군/구를 선택해주세요"}
                </button>
                {isSigunguOpen && (
                <ul className={styles.dropdown}>
                    {sigunguList.map((item) => (
                    <li key={item.name} className={styles.option} onClick={() => handleSelectSigungu(item.name)}>
                        {item.name}
                        <span className={styles.count}>&#40;{item.count}&#41;</span>
                    </li>
                    ))}
                </ul>
                )}
            </div>

            <div className={styles.inputWrap} ref={lclsWrapRef}>
                <button type="button" className={styles.filterBar} onClick={toggleLcls}>
                    {lcls || "업종(대분류) 선택"}
                </button>
                {isLclsOpen && (
                    <ul className={styles.dropdown}>
                        {lclsList.map((item)=>(
                            <li key={item.code} className={styles.option} onClick={()=>handleSelectLcls(item)}>
                                {item.name}
                            </li>
                        ))}
                    </ul>
                )}
            </div>

            <button className={styles.searchBtn} onClick={() => onSearch?.(sido, sigungu)}>검색</button>
        </div>
    )
}

export default AreaSearchController;