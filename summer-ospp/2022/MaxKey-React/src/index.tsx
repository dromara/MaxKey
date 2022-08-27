import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.scss';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter,Routes,Route} from "react-router-dom"
import Login from "./routes/Login"
import Home from "./routes/Home"
import User from "./routes/User"
import Forgot from "./routes/Forgot"
import AppList from './routes/AppList';
import Logout from "./routes/Logout"
import JwtAuth from './routes/Jwt-auth';
import NotFoundPage from "./routes/NotFoundPage"
import "antd/dist/antd.min.css"
const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <BrowserRouter>
  <Routes>
    <Route path="/" element={<App/>}></Route>
    <Route path="/passport/login" element={<Login/>}></Route>
    <Route path="/passport/forgot" element={<Forgot/>}></Route>
    <Route path="/dashboard/" element={<Home/>}>
      <Route path="home" element={<AppList/>}></Route>
      <Route path="user" element={<User/>}></Route>
    </Route>
    <Route path="/passport/logout" element={<Logout/>}></Route>
    <Route path="/passport/jwt/auth" element={<JwtAuth/>}></Route>
    <Route path="*" element={<NotFoundPage/>}></Route>
  </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
