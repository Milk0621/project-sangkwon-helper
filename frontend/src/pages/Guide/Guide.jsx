import styles from "./Guide.module.css";

function Guide() {
    return(
        <div className={styles.guideBanner}>
            <h1> 이용 가이드 </h1>
            <p> 5단계로 간단하게 시작하는 상권 분석 서비스 </p>
        </div>
    )
}

export default Guide;