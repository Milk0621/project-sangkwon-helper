import styles from './Home.module.css';

function Home() {
    return(
        <div>
            <div className={styles.mainBanner}>
                <h1>지역 창업을 위한 상권 분석 플랫폼</h1>
                <p>지금 바로 나에게 맞는 위치를 찾아보세요!</p>
                <button>분석 시작하기</button>
            </div>
            <div>
                <h1>서비스 소개</h1>
                <p>데이터 기반의 정확한 상권 분석으로 성공적인 창업을 도와드립니다</p>
            </div>
        </div>
    )
}

export default Home;