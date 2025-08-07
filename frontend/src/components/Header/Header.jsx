import { Link } from 'react-router-dom';
import styles from './Header.module.css';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../../store/userSlice';

function Header() {
    const user = useSelector((state) => state.user.user);

    const dispatch = useDispatch();
    const handleLogout = () => {
        localStorage.removeItem("token");
        dispatch(logout());
    };
    return(
        <header className={styles.header}>
            <div>
                <div className={styles.logo}>
                    <Link to="/">
                        <div className={styles.kor}>
                            상권<span className={styles.blue}>도우미</span>
                        </div>
                        <div className={styles.eng}>COMMERCIAL HELPER</div>
                    </Link>
                </div>
                <nav className={styles.nav}>
                    <Link to="/">홈</Link>
                    <Link to="/analysis">상권 분석</Link>
                    <Link to="/map">지도 보기</Link>
                    <Link to="/guide">이용 가이드</Link>
                </nav>
                <nav className={styles.nav}>
                    {user ? (
                        <>
                            <Link to="/mypage">{user.name}님</Link>
                            <button onClick={handleLogout}>로그아웃</button>
                        </>
                        ) : (
                        <Link to="/auth">로그인</Link>
                        )}     
                </nav>
            </div>
        </header>
    )
}

export default Header;