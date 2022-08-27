import React from "react"
import {ReactComponent as C} from "../assets/svg/C.svg"
import  "../assets/scss/footer.scss"
export default function Footer(){
    return (
        <footer>
        <div>
        MaxKey Enterprise Edition
        <br />
        Version v3.5.2 GA
        <br />
        Copyright  
        <C/>
        2022 
        <a href="//ww.maxkey.top"> MaxKey </a>
        . All rights reserved . 
        </div>
      </footer>

    )
}