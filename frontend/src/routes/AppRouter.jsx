import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Layout from '../components/Layout/Layout';
import Home from '../pages/Home/Home';
import Auth from '../components/Auth/Auth';
import Map from '../pages/Map/Map';
import PrivateRoute from '../Auth/PrivateRoute';

function AppRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Home />} />
                    <Route path="/auth" element={<Auth />} />
                    
                    <Route element={<PrivateRoute />}>
                        <Route path="/map" element={<Map />} />
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AppRouter;