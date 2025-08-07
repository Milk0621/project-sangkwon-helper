import { Link } from 'react-router-dom';
import styles from './Header.module.css';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../../store/userSlice';
import api from '../../api/api';

function Header() {
    const user = useSelector((state) => state.user.user);

    const dispatch = useDispatch();
    const handleLogout = async () => {
        const token = localStorage.getItem("token");

        try {
            await api.post("/auth/logout", {}, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (err) {
            console.error("서버 로그아웃 실패", err);
        }

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
                            <a onClick={handleLogout} style={{cursor:'pointer'}}>로그아웃</a>
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