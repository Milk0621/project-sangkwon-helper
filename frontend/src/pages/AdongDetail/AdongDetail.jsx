import styles from "./AdongDetail.module.css";
import api from "../../api/api";
import { useEffect, useMemo, useState } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid, Cell, Rectangle } from "recharts";

function AdongDetail() {
    const BAR_COLORS = ["#7C83FD", "#96BAFF", "#7BD3EA", "#A1E3A1", "#FFD166", "#FF8FA3"]; // 원하는 팔레트

    const [totalStores, setTotalStores] = useState("");
    const [topCategory, setTopCategory] = useState("");
    const [distribution, setDistribution] = useState([]);
    const [top6, setTop6] = useState([]);

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
            setDistribution(data.distribution);
            setTop6(data.top6);
        }

        fetchAdongStats();
    }, [])

    return (
        <div className={styles.adongDtailBg}>
            <div className={`wrap ${styles.pageWrap}`}>
                <div className={styles.title}>
                    <button>&larr; 돌아가기</button>
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
                        <div className={styles.chartBox}>
                            <ResponsiveContainer width="100%" height={400}>
                                <BarChart
                                    data={top6}
                                    barSize={85}               // 막대 두께
                                    margin={{ top: 8, right: 20, left: 0, bottom: 0 }}
                                >
                                    <CartesianGrid vertical={false} stroke="#e9eef5" />
                                    <XAxis
                                        dataKey="name"
                                        axisLine={false}
                                        tickLine={false}
                                        tick={{ fontSize: 12, fill: "#64748b" }}
                                        interval={0}
                                    />
                                    <YAxis
                                        axisLine={false}
                                        tickLine={false}
                                        tick={{ fontSize: 12, fill: "#94a3b8" }}
                                        width={36}
                                    />
                                    <Tooltip
                                        cursor={{ fill: "rgba(0,0,0,0.04)" }}
                                        formatter={(v) => v?.toLocaleString()}
                                        contentStyle={{
                                            borderRadius: 8,
                                            border: "1px solid #e2e8f0",
                                            boxShadow: "0 8px 24px rgba(0,0,0,0.08)"
                                        }}
                                    />
                                    <Bar dataKey="count" stroke="none" radius={[6, 6, 0, 0]}>
                                        {top6.map((_, i) => (
                                            <Cell key={i} fill={BAR_COLORS[i % BAR_COLORS.length]} />
                                        ))}
                                    </Bar>
                                </BarChart>
                            </ResponsiveContainer>
                        </div>
                    </div>
                    <div className={styles.contentBox}>
                        <div>
                            <p>상가 종류별 백분율</p>
                            <span>전체 업종 기준 데이터</span>
                        </div>
                        <div className={styles.ratioList}>
                            {distribution.map((item, idx)=>(
                                <div className={styles.storesRatio}>
                                    <div>
                                        <p>{item.name}</p>
                                        <span>{item.ratio}%</span>
                                    </div>
                                    <p>{item.count}개</p>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AdongDetail;