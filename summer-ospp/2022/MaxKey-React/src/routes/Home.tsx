import React from "react"
import "../assets/scss/home.scss"
import {Link} from "react-router-dom"
import Footer from "../components/Footer"
import logo from "../assets/logo.jpg"
import {ReactComponent as Quit} from "../assets/svg/quit.svg"
import {Outlet} from "react-router-dom"
export default function Home() {   
    
    return (
        <div className="body">
            <header className="header">
                <div className= 'title'>
                    <img src={logo} alt="" />
                    Max
                    <span>Key</span>
                    单点登录认证系统
                </div>
                <Link to="/passport/logout">
                <Quit className="quit"></Quit>
                </Link>
                
            </header>
            <div className="nav">    
                <ul>
                    <li>
                        <Link to="/dashboard/home">应用</Link>
                    </li>
                    <li>
                        <Link to="/dashboard/user">个人</Link>
                    </li>
                </ul>
            </div>
            <div className="container">
               <Outlet/>
            </div>
            <Footer/>

        </div>
    )
}