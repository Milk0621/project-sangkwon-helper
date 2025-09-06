import axios from "axios";

// Access Token 보관 (메모리 + 세션 저장소)
let accessToken = sessionStorage.getItem("access_token") || null;

export function setAccessToken(token) {
    accessToken = token || null;
    if (token) sessionStorage.setItem("access_token", token);
    else sessionStorage.removeItem("access_token");
}

const api = axios.create({
    baseURL: `http://localhost:8080/api`,
    withCredentials: true   // 쿠키 자동 포함
});

// 요청 인터셉터: Authorization 헤더 주입
api.interceptors.request.use((config)=>{
    if (accessToken) {
        config.headers = config.headers || {};
        config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
});

// 401 처리: 토큰 재발급 큐/락
let isRefreshing = false;
let waitQueue = []; // {resolve, reject}

function resolveQueue(err, token) {
  waitQueue.forEach(p => (err ? p.reject(err) : p.resolve(token)));
  waitQueue = [];
}

// 응답 인터셉터: 401이면 /auth/refresh → 원요청 재시도
api.interceptors.response.use(
    (res) => res,
    async (error) => {
        const original = error.config;

        // 401이 아니거나, 재시도 한 번 이미 했으면 그대로 실패
        if (error?.response?.status !== 401 || original?._retry) {
            return Promise.reject(error);
        }
        original._retry = true;

        // 이미 다른 탭/요청이 갱신 중이면 기다렸다가 재시도
        if (isRefreshing) {
            await new Promise((resolve, reject) => waitQueue.push({ resolve, reject }));
            if (accessToken) {
                original.headers = original.headers || {};
                original.headers.Authorization = `Bearer ${accessToken}`;
            }
            return api(original);
        }

        // 여기서 갱신 시도
        isRefreshing = true;
        try {
            // refresh 호출은 Authorization 헤더 금지! (쿠키만 사용)
            const res = await api.post(`/auth/refresh`,
                {
                    withCredentials: true,
                    headers: { Authorization: undefined },
                }
            );

            // 서버가 { accessToken } 반환한다고 가정
            const newAccess = res?.data?.accessToken;
            if (!newAccess) throw new Error("No accessToken in refresh response");
            setAccessToken(newAccess);

            resolveQueue(null, newAccess);
            // 원요청 재시도
            original.headers = original.headers || {};
            original.headers.Authorization = `Bearer ${newAccess}`;
            return api(original);
        } catch (e) {
            setAccessToken(null);
            resolveQueue(e, null);
            // 여기서 로그인 페이지로 보내거나, 토스트 띄우기
            // window.location.href = "/login";
            return Promise.reject(e);
        } finally {
            isRefreshing = false;
        }
    }
);

export default api;