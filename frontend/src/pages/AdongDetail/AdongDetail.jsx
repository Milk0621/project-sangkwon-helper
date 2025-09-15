import styles from "./AdongDetail.module.css";
import api from "../../api/api";
import { useEffect, useMemo, useState } from "react";

function AdongDetail() {

    const [totalStores, setTotalStores] = useState("");
    const [topCategory, setTopCategory] = useState("-");
    const [distribution, setDistribution] = useState([]);

    // URL 쿼리에서 지역 파라미터 추출 (React Router 없이도 동작)
    const { sido, sigungu, dong } = useMemo(()=>{
        const url = new URL(window.location.href);
        const pathSegments = url.pathname.split('/');
        return {
            sido: decodeURIComponent(pathSegments[2]),
            sigungu: decodeURIComponent(pathSegments[3]),
            dong: decodeURIComponent(pathSegments[4])
        }
    }, [])

    console.log(sido, sigungu, dong);

    // 백엔드 호출
    useEffect(()=>{
        const fetchAdongStats = async () => {
            const res = await api.get(`/areas/${sido}/${sigungu}/${dong}/stats`, {
                params: { topN: 6 }
            });
            console.log(res);
            const data = res.data;

            setTotalStores(data.totalStores);
            setTopCategory(data.topCategoryName);

        }

        fetchAdongStats();
    }, [])

    return (
        <div className={styles.adongDtailBg}>
            <div className="wrap">
                <div className={styles.title}>
                    <button>돌아가기</button>
                    <h2>{sigungu} {dong}</h2>
                </div>
                <span>상세한 상권 현황과 트렌드를 확인하세요</span>
                <div className={styles.contentTop}>
                    <div className={styles.contentBox}>
                        <div>
                            <span>총 상가 수</span>
                            <p>{totalStores.toLocaleString()}개</p>
                        </div>
                    </div>
                    <div className={styles.contentBox}>
                        <div>
                            <span>가장 많은 업종</span>
                            <p>{topCategory}</p>
                        </div>
                    </div>
                    <div className={styles.contentBox}>
                        <div>
                            <span>유동인구</span>
                            <p>구현 예정</p>
                        </div>
                    </div>
                </div>
                <div className={styles.contentMid}>
                    <div className={`${styles.contentBox} ${styles.colSpan2}`}>
                        <div>
                            <p>업종별 상가 현황</p>
                            <span>전체 {totalStores.toLocaleString()}개 상가 기준</span>
                        </div>
                    </div>
                    <div className={styles.contentBox}>
                        <div>
                            <p>상가 종류별 백분율</p>
                            <span>상위 7개 데이터</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AdongDetail;