import styles from "./Analysis.module.css";

function Analysis() {
    return(
        <>
            <div className={styles.analysisBanner}>
                <h1> 상권 분석 시작하기 </h1>
                <p> 몇 가지 질문에 답하시면 맞춤형 상권 분석 결과를 제공해드립니다 </p>
            </div>
            <div className={styles.top}>
                <div className={styles.step}>
                    <div className={styles.icon}>1</div>
                    <span>기본 정보</span>
                </div>
                <div className={styles.bar}></div>
                <div className={styles.step}> 
                    <div className={styles.icon}>2</div>
                    <span>위치 선택</span>
                </div>
                <div className={styles.bar}></div>
                <div className={styles.step}>
                    <div className={styles.icon}>3</div>
                    <span>분석 완료</span>
                </div>
            </div> 

            <div>
                <div className={styles.analysisForm}>
                    <h3>기본 정보를 입력해주세요</h3>
                    <div>
                        <span>창업하려는 업종을 선택해주세요</span>
                        <div className={styles.grid4}>
                            <button>카페</button>
                            <button>음식점</button>
                            <button>편의점</button>
                            <button>미용실</button>
                            <button>학원</button>
                            <button>병원</button>
                            <button>의류점</button>
                            <button>기타</button>
                        </div>
                    </div>
                    <div>
                        <span>예상 창업 자금을 선택해주세요</span>
                        <div className={styles.grid2}>
                            <button>1천만원 미만</button>
                            <button>1천만원 ~ 3천만원</button>
                            <button>3천만원 ~ 5천만원</button>
                            <button>5천만원 이상</button>
                        </div>
                    </div>
                    <div>
                        <span>창업 경험이 있으신가요?</span>
                        <div className={styles.grid2}>
                            <button>없음</button>
                            <button>있음</button>
                        </div>
                    </div>
                    <button className={styles.nextBtn}>다음</button>
                </div>
            </div>

            <div>
                <div className={styles.analysisForm}>
                    <h3>희망 지역을 선택해주세요</h3>
                    <div>
                        <span>지역을 선택해주세요</span>
                        <div className={styles.inputWrap}>
                            <ul className={styles.dropdown}>
                                <li className={styles.option}></li>
                            </ul>
                        </div>
                    </div>
                    <div>
                        <span>예상 창업 자금을 선택해주세요</span>
                        <div className={styles.grid2}>
                            <button>1천만원 미만</button>
                            <button>1천만원 ~ 3천만원</button>
                            <button>3천만원 ~ 5천만원</button>
                            <button>5천만원 이상</button>
                        </div>
                    </div>
                    <div>
                        <span>창업 경험이 있으신가요?</span>
                        <div className={styles.grid2}>
                            <button>없음</button>
                            <button>있음</button>
                        </div>
                    </div>
                    <button className={styles.nextBtn}>다음</button>
                </div>
            </div>
        </>
    )
}

export default Analysis;