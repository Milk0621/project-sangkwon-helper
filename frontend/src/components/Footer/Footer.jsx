import styles from './Footer.module.css';

function Footer() {
    return(
        <footer>
            <div>
                <div>
                    <h4>상권분석</h4>
                    <p>지역 상권분석 플랫폼으로 <br /> 성공적인 창업을 도와드립니다</p>
                </div>
                <div>
                    <h5>서비스</h5>
                    <ul>
                        <li>상권 분석</li>
                        <li>유동인구 분석</li>
                        <li>경쟁업체 분석</li>
                        <li>위치 추천</li>
                    </ul>
                </div>
                <div>
                    <h5>회사</h5>
                    <ul>
                        <li>회사 소개</li>
                        <li>팀</li>
                        <li>채용정보</li>
                        <li>투자정보</li>
                    </ul>
                </div>
                <div>
                    <h5>고객지원</h5>
                    <ul>
                        <li>이용가이드</li>
                        <li>FAQ</li>
                        <li>문의하기</li>
                        <li>개발자 API</li>
                    </ul>
                </div>
            </div>
            <div className={styles.footerBottom}>
                <p>© 2025 상권분석 플랫폼. All rights reserved.</p>
            </div>
        </footer>
    )
}

export default Footer;