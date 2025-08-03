import styles from './Auth.module.css';

function Auth() {
    return(
        <div>
            <div className={styles.authBanner}>
                <h1>로그인</h1>
                <p>상권분석 플랫폼에 다시 오신 것을 환영합니다</p>
            </div>
            <div>
                <div className={styles.form}>
                    <div className={styles.btnDiv}>
                        <button className={styles.active}>로그인</button>
                        <button>회원가입</button>
                    </div>
                    <div className={styles.input}>
                        <label>이름 <span>*</span></label>
                        <input 
                            type="text"
                            placeholder='이름을 입력하세요' 
                        />
                    </div>
                    <div>
                        <label>휴대폰 번호</label>
                        <input 
                            type="tel"
                            placeholder='010-0000-0000' 
                        />
                    </div>
                    <div>
                        <label>관심 업종</label>
                        <select>
                            <option value="">선택하세요</option>
                            <option value="카페">카페</option>
                            <option value="음식점">음식점</option>
                            <option value="편의점">편의점</option>
                            <option value="미용실">미용실</option>
                            <option value="학원">학원</option>
                            <option value="병원">병원</option>
                            <option value="의류점">의류점</option>
                            <option value="기타">기타</option>
                        </select>
                    </div>
                    <div>
                        <label>이메일 <span>*</span></label>
                        <input 
                            type="email"
                            required
                            placeholder='example@email.com'
                        />
                    </div>
                    <div>
                        <label>비밀번호 <span>*</span></label>
                        <input 
                            type="password"
                            required
                            placeholder='비밀번호를 입력하세요'
                        />
                    </div>
                    <div>
                        <label>비밀번호 확인 <span>*</span></label>
                        <input 
                            type="password" 
                            required
                            placeholder='비밀번호를 다시 입력하세요'
                        />
                    </div>
                    <button type="submit">로그인</button>
                </div>
            </div>
        </div>
    )
}

export default Auth;