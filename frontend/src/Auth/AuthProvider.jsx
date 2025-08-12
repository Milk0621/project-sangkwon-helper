import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import api from "../api/api";
import { setUser } from "../store/userSlice";

export default function AuthProvider({ children }) {
  const dispatch = useDispatch();
  const [ready, setReady] = useState(false);

  useEffect(() => {
    (async () => {
      const token = localStorage.getItem("token");
      if (token) {
        api.defaults.headers.Authorization = `Bearer ${token}`;
        try {
          const me = await api.get("/users/me");
          dispatch(setUser(me.data));
        } catch (e) {
          // 토큰 만료/무효 → 정리
          localStorage.removeItem("token");
          delete api.defaults.headers.Authorization;
        }
      }
      setReady(true);
    })();
  }, [dispatch]);

  if (!ready) return null; // 또는 전역 스피너
  return children;
}