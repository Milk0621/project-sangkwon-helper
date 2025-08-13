import { useCallback, useState } from "react";
import KakaoMap from "../../components/KakaoMap/KakaoMap";
import styles from "./Map.module.css";
import api from "../../api/api";

function Map() {
    const [query, setQuery] = useState("");
    const [category, setCategory] = useState("ALL");
    const [sort, setSort] = useState("BIZ_DESC"); // 상가수 많은순 기본

    // 제출 시 KakaoMap에 전달할 필터 패킷
    const [filters, setFilters] = useState({ query: "", category: "ALL", sort: "BIZ_DESC" });

    const [selectedAdong, setSelectedAdong] = useState(null);

    const onSubmit = useCallback(async (e) => {
        e.preventDefault();
        setFilters({ query, category, sort });

        try {
            const res = await api.get("/adongs/search", {
                params: {query, limit: 5}
            });
            console.log(res.data);

            if (!Array.isArray(res.data) || res.data.length === 0) {
                alert("검색 결과가 없습니다.");
                return;
            }

            setSelectedAdong(res.data[0]);
        } catch (err) {
            console.error(err);
            alert("검색 중 오류가 발생했습니다.")
        } finally {
            console.log("[SUBMIT] end");
        }

    }, [query, category, sort]);
    
    return(
        <>
            <div className="wrap margin">
                <div className={styles.title}>
                    <h1>지역별 상가 현황</h1>
                    <p>행정동별 상가 분포와 상권 정보를 확인해보세요</p>
                </div>

                <div className={styles.searchBox}>
                    <form className={styles.search} onSubmit={onSubmit}>
                        <div className={styles.inputWrap}>
                            <span className={styles.searchIcon} aria-hidden>🔍</span>
                            <input
                            type="text"
                            placeholder="지역명을 검색하세요 (예: 강남구, 홍대동)"
                            value={query}
                            onChange={(e) => setQuery(e.target.value)}
                            />
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
                        <KakaoMap filters={filters} selectedAdong={selectedAdong} />
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