import React,{useEffect}from "react"
import CONSTS from "../shared/index"
import { useSearchParams,useNavigate } from "react-router-dom"
import {api} from "../utils/api"
import { AxiosError } from "axios"
import {message} from "antd"
export default function Logout(){
    const [searchParams] = useSearchParams()
    const navigate = useNavigate()
    let redirect_uri = ''
    useEffect(()=>{
        redirect_uri = searchParams.get(CONSTS.REDIRECT_URI)!
        //console.log(redirect_uri)
        api.logout()
        .then()
        .catch((err:AxiosError)=>{
            message.error(err.message)
            Promise.reject(err)
        })
        localStorage.removeItem('token')
        if(redirect_uri == null || redirect_uri === ''){
            navigate("/passport/login")
        }else{
            navigate(redirect_uri,{replace:true})
        }
    },[])
    return(
        <></>
    )
}