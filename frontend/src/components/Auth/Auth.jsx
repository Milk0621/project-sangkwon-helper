import { useState } from 'react';
import styles from './Auth.module.css';
import api from '../../api/api';
import { useDispatch } from 'react-redux';
import { setUser } from '../../store/userSlice';
import { useNavigate } from 'react-router-dom';

function Auth() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [isLogin, setIsLogin] = useState(true);

    // 회원가입 상태 관리
    const [name, setName] = useState(''); 
    const [number, setNumber] = useState('');
    const [industry, setIndustry] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [isSubmitted, setIsSubmitted] = useState(false);
    const [errors, setErrors] = useState({});

    // 이름 실시간 유효성 검사
    const handleNameChange = (e) => {
        const value = e.target.value;
        setName(value);

        if (value === '') {
            setErrors(prev => ({ ...prev, name: null}));
        } else if (!/^[가-힣]{2,10}$/.test(value)) {
            setErrors(prev => ({ ...prev, name: '이름은 한글 2~10자로 입력해주세요.' }));
        } else {
            setErrors(prev => ({ ...prev, name: null }));
        }
    };

    // 전화번호 실시간 유효성 검사
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

    // 이메일 실시간 유효성 검사
    const handleEmailChange = (e) => {
        const value = e.target.value;
        setEmail(value);

        if (value === ''){
            setErrors(prev => ({ ...prev, email: null}));
        }else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
            setErrors(prev => ({ ...prev, email: '이메일 형식이 올바르지 않습니다.' }));
        } else {
            setErrors(prev => ({ ...prev, email: null }));
        }
    };

    // 비밀번호 실시간 유효성 검사
    const handlePasswordChange = (e) => {
        const value = e.target.value;
        setPassword(value);

        if (value === '') {
            setErrors(prev => ({ ...prev, password: null}));
        } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(value)) {
            setErrors(prev => ({ ...prev, password: '비밀번호는 영문과 숫자 조합 8자 이상이어야 합니다.' }));
        } else {
            setErrors(prev => ({ ...prev, password: null }));
        }
    };

    // 비밀번호 확인 실시간 유효성 검사
    const handleConfirmPasswordChange = (e) => {
        const value = e.target.value;
        setConfirmPassword(value);

        if (value === '') {
            setErrors(prev => ({ ...prev, confirmPassword: null}));
        } else if (password !== value) {
            setErrors(prev => ({ ...prev, confirmPassword: '비밀번호가 일치하지 않습니다.' }));
        } else {
            setErrors(prev => ({ ...prev, confirmPassword: null }));
        }
    };    

    // 관심 업종 유효성 검사
    const handleIndustryChange = (e) => {
        const value = e.target.value;
        setIndustry(value);

        if (value === ''){
            setErrors(prev => ({ ...prev, industry: null }));
        } else {
            setErrors(prev => ({ ...prev, industry: null }));
        }
    };   

    // 로그인 요청
    const handleLogin = async () => {
        try {
            const res = await api.post("/auth/login", {
                email,
                password
            });

            const { accessToken } = res.data;
            localStorage.setItem("token", accessToken);
            
            const token = localStorage.getItem("token");
            const userInfo = await api.get("/users/me", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            dispatch(setUser(userInfo.data));

            alert("로그인 성공!");
            navigate("/");
        } catch (err) {
            console.error("로그인 실패", err);
            alert("로그인에 실패했습니다.");
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setIsSubmitted(true);

        if (isLogin) {
            handleLogin();
            return;
        }

        // 필수값 누락 시 유효성 검사
        const newErrors = {};
        if (!name || !email || !password || !confirmPassword) {
            if (!name) newErrors.name = '이름을 입력해주세요.';
            if (!email) newErrors.email = '이메일을 입력해주세요.';
            if (!password) newErrors.password = '비밀번호를 입력해주세요.';
            if (!confirmPassword) newErrors.confirmPassword = '비밀번호 확인을 입력해주세요.';
            setErrors(prev => ({ ...prev, ...newErrors }));
            return;
        }

        // 에러가 있으면 제출 막기
        const hasError = Object.values({ ...errors, ...newErrors }).some(error => error !== null);
        if (hasError) {
            console.log('회원가입 실패: 유효성 검사 통과 못함');
            return;
        }

        // 모든 유효성 통과 시 처리
        const fetchRegister = async () => {
            try {
                const res = await api.post("/auth/register", {
                    email,
                    password,
                    name,
                    number,
                    industry
                })
                console.log("회원가입 요청 성공", res);
                setName('');
                setNumber('');
                setIndustry('');
                setEmail('');
                setPassword('');
                setConfirmPassword('');
                alert("회원가입 성공");
                setIsLogin(true);
            } catch (err) {
                const message = err.response?.data;

                if (message === "이미 사용 중인 이메일입니다.") {
                    setErrors(prev => ({ ...prev, email: message }));
                } else if (message === "이미 사용 중인 이름입니다.") {
                    setErrors(prev => ({ ...prev, name: message }));
                } else {
                    console.error("회원가입 요청 실패", err);
                }
            }
        }
        fetchRegister();
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
                        <button type='button' className={isLogin && styles.active} onClick={()=>setIsLogin(true)}>로그인</button>
                        <button type='button' className={!isLogin && styles.active} onClick={()=>setIsLogin(false)}>회원가입</button>
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
                            {(errors.name && (isSubmitted || name)) && <p className={styles.error}>{errors.name}</p>}
                        </div>
                        <div className={styles.inputBox}>
                            <label>휴대폰 번호</label>
                            <input 
                                type="tel"
                                placeholder='010-0000-0000'
                                value={number}
                                onChange={handleNumberChange}
                            />
                            {(errors.number && (isSubmitted || number)) && <p className={styles.error}>{errors.number}</p>}
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
                            {(errors.industry && (isSubmitted || industry)) && <p className={styles.error}>{errors.industry}</p>}
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
                        {(errors.email && (isSubmitted || email)) && <p className={styles.error}>{errors.email}</p>}
                    </div>
                    <div className={styles.inputBox}>
                        <label>비밀번호 <span>*</span></label>
                        <input 
                            type="password"
                            placeholder='비밀번호를 입력하세요'
                            value={password}
                            onChange={handlePasswordChange}
                        />
                        {(errors.password && (isSubmitted || password)) && <p className={styles.error}>{errors.password}</p>}
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
                            {(errors.confirmPassword && (isSubmitted || confirmPassword)) && <p className={styles.error}>{errors.confirmPassword}</p>}
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