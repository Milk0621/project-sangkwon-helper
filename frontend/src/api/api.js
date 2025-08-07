import axios from "axios";

const api = axios.create({
    baseURL: `http://localhost:8080/api`,
    withCredentials: true   // 쿠키 자동 포함
});

api.interceptors.request.use((config)=>{
    const token = localStorage.getItem("token");
    if(token) config.headers.Authorization = `Bearer ${token}`;
    return config;
});

export default api;