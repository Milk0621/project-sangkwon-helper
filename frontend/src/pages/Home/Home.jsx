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
        </div>
    )
}

export default Home;