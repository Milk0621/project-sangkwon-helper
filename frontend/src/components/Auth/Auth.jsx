import { useState } from 'react';
import styles from './Auth.module.css';

function Auth() {
    const [isLogin, setIsLogin] = useState(true);

    // 회원가입 상태 관리
    const [name, setName] = useState(''); 
    const [number, setNumber] = useState('');
    const [industry, setIndustry] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [errors, setErrors] = useState({});

    // 이름 실시간 유효성 검사
    const handleNameChange = (e) => {
        const value = e.target.value;
        setName(value);

        if (!/^[가-힣]{2,10}$/.test(value)) {
            setErrors(prev => ({ ...prev, name: '이름은 한글 2~10자로 입력해주세요.' }));
        } else {
            setErrors(prev => ({ ...prev, name: null }));
        }
    };

    const handleNumberChange = (e) => {
        const value = e.target.value;
        setNumber(value);

        if (value === ''){
            setErrors(prev => ({ ...prev, number: null }));
        } else if (!/^010-\d{4}-\d{4}$/.test(value)) {
            setErrors(prev => ({ ...prev, number: '휴대폰 번호는 010-0000-0000 형식으로 입력해주세요.' }));
        } else {
            setErrors(prev => ({ ...prev, number: null }));
        }
    };

    const handleEmailChange = (e) => {
        const value = e.target.value;
        setEmail(value);

        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
            setErrors(prev => ({ ...prev, email: '이메일 형식이 올바르지 않습니다.' }));
        } else {
            setErrors(prev => ({ ...prev, email: null }));
        }
    };

    const handlePasswordChange = (e) => {
        const value = e.target.value;
        setPassword(value);

        if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(value)) {
            setErrors(prev => ({ ...prev, password: '비밀번호는 영문과 숫자 조합 8자 이상이어야 합니다.' }));
        } else {
            setErrors(prev => ({ ...prev, password: null }));
        }
    };

    const handleConfirmPasswordChange = (e) => {
        const value = e.target.value;
        setConfirmPassword(value);

        if (password !== value) {
            setErrors(prev => ({ ...prev, confirmPassword: '비밀번호가 일치하지 않습니다.' }));
        } else {
            setErrors(prev => ({ ...prev, confirmPassword: null }));
        }
    };    

    const handleIndustryChange = (e) => {
        const value = e.target.value;
        setIndustry(value);

        if (value === ''){
            setErrors(prev => ({ ...prev, industry: null }));
        } else {
            setErrors(prev => ({ ...prev, industry: null }));
        }
    };   

    const handleSubmit = (e) => {
        e.preventDefault();

        if (isLogin) {
            // 로그인 로직은 따로 처리
            console.log('로그인 시도:', email, password);
            return;
        }

        // 모든 유효성 통과 시 처리
        console.log('회원가입 성공:', { name, number, email, password, industry });
    };

    return(
        <>
            <div className={styles.authBanner}>
                <h1> {isLogin ? '로그인' : '회원가입'} </h1>
                <p> {isLogin ? '상권분석 플랫폼에 다시 오신 것을 환영합니다' : '상권분석 플랫폼과 함께 성공적인 창업을 시작하세요'} </p>
            </div>

            <div>
                <form className={styles.form} onSubmit={handleSubmit}>
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
                                value={name}
                                onChange={handleNameChange}
                            />
                            {errors.name && <p className={styles.error}>{errors.name}</p>}
                        </div>
                        <div className={styles.inputBox}>
                            <label>휴대폰 번호</label>
                            <input 
                                type="tel"
                                placeholder='010-0000-0000'
                                value={number}
                                onChange={handleNumberChange}
                            />
                            {errors.number && <p className={styles.error}>{errors.number}</p>}
                        </div>
                        <div className={styles.inputBox}>
                            <label>관심 업종</label>
                            <select value={industry} onChange={handleIndustryChange}>
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
                            {errors.industry && <p className={styles.error}>{errors.industry}</p>}
                        </div>
                        </>
                    )}

                    {/* 공통 */}
                    <div className={styles.inputBox}>
                        <label>이메일 <span>*</span></label>
                        <input 
                            type="email"
                            placeholder='example@email.com'
                            value={email}
                            onChange={handleEmailChange}
                        />
                        {errors.email && <p className={styles.error}>{errors.email}</p>}
                    </div>
                    <div className={styles.inputBox}>
                        <label>비밀번호 <span>*</span></label>
                        <input 
                            type="password"
                            placeholder='비밀번호를 입력하세요'
                            value={password}
                            onChange={handlePasswordChange}
                        />
                        {errors.password && <p className={styles.error}>{errors.password}</p>}
                    </div>

                    {!isLogin && (
                        <div className={styles.inputBox}>
                            <label>비밀번호 확인 <span>*</span></label>
                            <input 
                                type="password" 
                                placeholder='비밀번호를 다시 입력하세요'
                                value={confirmPassword}
                                onChange={handleConfirmPasswordChange}
                            />
                            {errors.confirmPassword && <p className={styles.error}>{errors.confirmPassword}</p>}
                        </div>
                    )}
                    <button type="submit">{isLogin ? '로그인' : '회원가입'}</button>
                </form>
                <div className={styles.textBox}>
                    <h5 style={{fontWeight:'600'}}>
                        {isLogin ? '로그인 후 이용 가능한 서비스' : '회원가입 혜택'}
                    </h5>
                    <div className={styles.text}><i>✓</i><span>무료 상권분석 리포트 제공</span></div>
                    <div className={styles.text}><i>✓</i><span>맞춤형 창업 위치 추천</span></div>
                    <div className={styles.text}><i>✓</i><span>실시간 상권 데이터 업데이트</span></div>
                    <div className={styles.text}><i>✓</i><span>전문가 창업 컨설팅</span></div>
                </div>
            </div>
        </>
    )
}

export default Auth;