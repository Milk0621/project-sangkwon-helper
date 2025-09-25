import styles from './Home.module.css';

function Home() {
    return(
        <div>
            <div className={styles.mainBanner}>
                <h1>지역 창업을 위한 상권 분석 플랫폼</h1>
                <p>지금 바로 나에게 맞는 위치를 찾아보세요!</p>
                <button>분석 시작하기</button>
            </div>
            <div className={styles.introduceBox}>
                <div>
                    <h1 style={{fontWeight: "700"}}>서비스 소개</h1>
                    <p>데이터 기반의 정확한 상권 분석으로 성공적인 창업을 도와드립니다</p>
                </div>
                <div className={`${styles.introduce} wrap`}>
                    <div className={styles.statusImg} >
                        <img src="/img/commercial-status.png" />
                    </div>
                    <div>
                        <div className={styles.introduceCon}>
                            <i className={`${styles.icon} ${styles.icon1}`} />
                            <div className={styles.introduceTxt}>
                                <h5>정확한 위치 분석</h5>
                                <p>빅데이터 기반으로 최적의 창업 위치를 제안해드립니다</p>
                            </div>
                        </div>
                        <div className={styles.introduceCon}>
                            <i className={`${styles.icon} ${styles.icon2}`} />
                            <div className={styles.introduceTxt}>
                                <h5>실시간 데이터</h5>
                                <p>최신 상권 트렌드와 유동인구 데이터를 실시간으로 제공합니다</p>
                            </div>
                        </div>
                        <div className={styles.introduceCon}>
                            <i className={`${styles.icon} ${styles.icon3}`} />
                            <div className={styles.introduceTxt}>
                                <h5>정확한 위치 분석</h5>
                                <p>업종별 특성을 고려한 개인화된 상권 분석 결과를 제공합니다</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className={styles.functionGuideBox}>
                <div>
                    <h1 style={{fontWeight: "700"}}>기능 안내</h1>
                    <p>창업 성공을 위한 필수 기능들을 한 곳에서 만나보세요</p>
                </div>
                <div className={`${styles.functionGuide} wrap`}>
                    <div className={styles.functionGuideCon}>
                        <i className={`${styles.icon} ${styles.icon4}`} />
                        <div>
                            <h5>상권 현황 분석</h5>
                            <p>지역별 업종 분포, 임대료, 매출 현황 등 상권의 전반적인 현황을 한눈에 파악할 수 있습니다.</p>
                        </div>
                        <div>
                            이미지1
                        </div>
                    </div>
                    <div className={styles.functionGuideCon}>
                        <i className={`${styles.icon} ${styles.icon5}`} />
                        <div>
                            <h5>유동인구 분석</h5>
                            <p>시간대별, 연령대별, 성별 유동인구 패턴을 분석하여 타켓 고객층을 파악할 수 있습니다.</p>
                        </div>
                        <div>
                            이미지2
                        </div>
                    </div>
                    <div className={styles.functionGuideCon}>
                        <i className={`${styles.icon} ${styles.icon6}`} />
                        <div>
                            <h5>경쟁 업체 분석</h5>
                            <p>동일 업종 경쟁업체의 분포와 성과를 분석하여 차별화된 사업 전략을 수립할 수 있습니다.</p>
                        </div>
                        <div>
                            이미지3
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Home;