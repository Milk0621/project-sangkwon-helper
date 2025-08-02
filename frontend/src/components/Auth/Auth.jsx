import styles from './Auth.module.css';

function Auth() {
    return(
        <div>
            <div className={styles.authBanner}>
                <h1>로그인</h1>
                <p>상권분석 플랫폼에 다시 오신 것을 환영합니다</p>
            </div>
        </div>
    )
}

export default Auth;