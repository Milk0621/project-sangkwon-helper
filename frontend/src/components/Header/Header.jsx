import { Link } from 'react-router-dom';
import styles from './Header.module.css';

function Header() {
    return(
        <header className={styles.header}>
                <div className={styles.logo}>
                    <Link to="/">상권도우미</Link>
                </div>
                <nav className={styles.nav}>
                    <Link to="/">홈</Link>
                    <Link to="/analysis">상권 분석</Link>
                    <Link to="/map">지도 보기</Link>
                    <Link to="/guide">이용 가이드</Link>
                </nav>
                <nav className={styles.nav}>
                    <Link to="/auth">로그인</Link>
                </nav>
        </header>
    )
}

export default Header;