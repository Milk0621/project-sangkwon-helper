import { useState } from "react";
import styles from "./MyPage.module.css";

function MyPage(){
    const [tab, setTab] = useState("profile");

    return(
        <>
            <div className={styles.mypageBanner}>
                <div className="wrap">
                    <h1>마이페이지</h1>
                    <p>내 정보와 상권 분석 내역을 관리하세요</p>
                </div>
            </div>
            <div className={styles.body}>
                <div className={`${styles.bodyCon} wrap`}>
                <aside className={styles.leftCol}>
                    <section className={styles.profileCard}>
                    <div className={styles.avatar}>
                        <i className="fas fa-user" aria-hidden="true" />
                    </div>
                    <div className={styles.profileMeta}>
                        <strong className={styles.profileName}>홍길동</strong>
                        <span className={styles.profileEmail}>hong@example.com</span>
                    </div>

                    <nav className={styles.nav}>
                        <button
                        type="button"
                        className={`${styles.navBtn} ${tab === "profile" ? styles.active : ""}`}
                        onClick={() => setTab("profile")}
                        >
                        <i className="fas fa-id-card" aria-hidden="true" />
                        <span>개인정보</span>
                        </button>
                        <button
                        type="button"
                        className={`${styles.navBtn} ${tab === "history" ? styles.active : ""}`}
                        onClick={() => setTab("history")}
                        >
                        <i className="fas fa-chart-line" aria-hidden="true" />
                        <span>분석 내역</span>
                        </button>
                        <button
                        type="button"
                        className={`${styles.navBtn} ${tab === "settings" ? styles.active : ""}`}
                        onClick={() => setTab("settings")}
                        >
                        <i className="fas fa-cog" aria-hidden="true" />
                        <span>설정</span>
                        </button>
                    </nav>
                    </section>

                    <section className={styles.statsCard}>
                    <h3>나의 통계</h3>
                    <ul className={styles.statsList}>
                        <li>
                        <span>총 분석 횟수</span>
                        <strong>3회</strong>
                        </li>
                        <li>
                        <span>완료된 분석</span>
                        <strong>2회</strong>
                        </li>
                        <li>
                        <span>가입일</span>
                        <strong>2024-01-01</strong>
                        </li>
                    </ul>
                    </section>
                </aside>

                <main className={styles.rightCol}>
                    <div className={styles.panelHeader}>
                    <h2>개인정보</h2>
                    <button type="button" className={styles.primaryBtn}>
                        수정
                    </button>
                    </div>

                    <section className={styles.infoCard}>
                    <div className={styles.infoGrid}>
                        <div className={styles.field}>
                        <label>이름</label>
                        <input type="text" value="홍길동" readOnly />
                        </div>
                        <div className={styles.field}>
                        <label>이메일</label>
                        <input type="text" value="hong@example.com" readOnly />
                        </div>
                        <div className={styles.field}>
                        <label>휴대폰 번호</label>
                        <input type="text" value="010-1234-5678" readOnly />
                        </div>
                        <div className={styles.field}>
                        <label>관심 업종</label>
                        <input type="text" value="카페" readOnly />
                        </div>
                    </div>
                    </section>

                    {tab === "history" && (
                    <section className={styles.placeholder}>
                        <p>분석 내역이 여기에 표시됩니다.</p>
                    </section>
                    )}
                    {tab === "settings" && (
                    <section className={styles.placeholder}>
                        <p>설정 화면이 여기에 표시됩니다.</p>
                    </section>
                    )}
                </main>
                </div>
            </div>
        </>
    )
}

export default MyPage;