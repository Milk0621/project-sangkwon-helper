import { useState } from 'react';
import styles from './Auth.module.css';

function Auth() {
    const [isLogin, setIsLogin] = useState(true);

    return(
        <>
            <div className={styles.authBanner}>
                <h1> {isLogin ? '로그인' : '회원가입'} </h1>
                <p> {isLogin ? '상권분석 플랫폼에 다시 오신 것을 환영합니다' : '상권분석 플랫폼과 함께 성공적인 창업을 시작하세요'} </p>
            </div>
            <div>
                <div className={styles.form}>
                    <div className={styles.btnDiv}>
                        <button className={isLogin && styles.active} onClick={()=>setIsLogin(true)}>로그인</button>
                        <button className={!isLogin && styles.active} onClick={()=>setIsLogin(false)}>회원가입</button>
                    </div>

                    {/* 회원가입 폼 */}
                    {!isLogin && (
                        <>
                        <div className={styles.inputBox}>
                            <label>이름 <span>*</span></label>
                            <input 
                                type="text"
                                placeholder='이름을 입력하세요' 
                            />
                        </div>
                        <div className={styles.inputBox}>
                            <label>휴대폰 번호</label>
                            <input 
                                type="tel"
                                placeholder='010-0000-0000' 
                            />
                        </div>
                        <div className={styles.inputBox}>
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
                        </>
                    )}

                    {/* 공통 */}
                    <div className={styles.inputBox}>
                        <label>이메일 <span>*</span></label>
                        <input 
                            type="email"
                            required
                            placeholder='example@email.com'
                        />
                    </div>
                    <div className={styles.inputBox}>
                        <label>비밀번호 <span>*</span></label>
                        <input 
                            type="password"
                            required
                            placeholder='비밀번호를 입력하세요'
                        />
                    </div>

                    {!isLogin && (
                        <div className={styles.inputBox}>
                            <label>비밀번호 확인 <span>*</span></label>
                            <input 
                                type="password" 
                                required
                                placeholder='비밀번호를 다시 입력하세요'
                            />
                        </div>
                    )}
                    {isLogin ? 
                        <button type="submit">로그인</button>
                        :
                        <button type="submit">회원가입</button>
                    }
                </div>
                <div className={styles.textBox}>
                    <h5 style={{fontWeight:'600'}}>
                        {isLogin ? '로그인 후 이용 가능한 서비스' : '회원가입 혜택'}
                    </h5>
                    <div className={styles.text}>
                        <i>✓</i>
                        <span>무료 상권분석 리포트 제공</span>
                    </div>
                    <div className={styles.text}>
                        <i>✓</i>
                        <span>맞춤형 창업 위치 추천</span>
                    </div>
                    <div className={styles.text}>
                        <i>✓</i>
                        <span>실시간 상권 데이터 업데이트</span>
                    </div>
                    <div className={styles.text}>
                        <i>✓</i>
                        <span>전문가 창업 컨설팅</span>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Auth;