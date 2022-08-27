import React from "react"
import "../assets/scss/header.scss"
import logo from "../assets/logo.jpg"
export default function Header(){
    return(
        <header>
          <div className= 'title'>
            <img src={logo} alt="" />
              Max
              <span>Key</span>
              单点登录认证系统
          </div>
        </header>
    )
}