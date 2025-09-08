import styles from "./AdongDetail.module.css";

function AdongDetail() {
    return (
        <div className={styles.adongDtailBg}>
            <div className="wrap">
                <div className={styles.title}>
                    <button>돌아가기</button>
                    <h2>강남구 역삼동 상권 분석</h2>
                </div>
                <span>상세한 상권 현황과 트렌드를 확인하세요</span>
                <div className={styles.contentTop}>
                    <div className={styles.contentBox}>
                        <div>
                            <span>총 상가 수</span>
                            <p>1,246개</p>
                        </div>
                    </div>
                    <div className={styles.contentBox}>
                        <div>
                            <span>가장 많은 업종</span>
                            <p>과학 기술</p>
                        </div>
                    </div>
                    <div className={styles.contentBox}>
                        <div>
                            <span>가장 적은 업종</span>
                            <p>공공 기관</p>
                        </div>
                    </div>
                </div>
                <div className={styles.contentMid}>
                    <div className={`${styles.contentBox} ${styles.colSpan2}`}>
                        <div>
                            <p>업종별 상가 현황</p>
                            <span>전체 1,247개 상가 기준</span>
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