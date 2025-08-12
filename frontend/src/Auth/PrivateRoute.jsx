import { useSelector } from "react-redux";
import { Navigate, Outlet, useLocation } from "react-router-dom";

export default function PrivateRoute() {
  const user = useSelector((state) => state.user.user);
  const location = useLocation();

  // 로그인 안 되어 있으면 /auth 로 보내되, 돌아올 곳(state.from) 저장
  if (!user) {
    return <Navigate to="/auth" replace state={{ from: location }} />;
  }
  return <Outlet />; // 통과
}