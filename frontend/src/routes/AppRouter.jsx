import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Layout from '../components/Layout/Layout';
import Home from '../pages/Home/Home';
import Auth from '../components/Auth/Auth';
import Map from '../pages/Map/Map';
import PrivateRoute from '../Auth/PrivateRoute';
import Guide from '../pages/Guide/Guide';
import Analysis from '../pages/Analysis/Analysis';
import AdongDetail from '../pages/AdongDetail/AdongDetail';

function AppRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Home />} />
                    <Route path="/auth" element={<Auth />} />
                    
                    <Route element={<PrivateRoute />}>
                        <Route path="/analysis" element={<Analysis />} />
                        <Route path="/map" element={<Map />} />
                        <Route path="/map/:sido/:sigungu/:dong" element={<AdongDetail />} />
                    </Route>

                    <Route path="/guide" element={<Guide />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AppRouter;