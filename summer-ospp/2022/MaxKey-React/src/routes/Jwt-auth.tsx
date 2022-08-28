import React, { useEffect } from "react"
import { useSearchParams ,useNavigate} from "react-router-dom"
import {api} from "../utils/api"
import { AxiosResponse } from "axios"
export default function JwtAuth(){
    let jwt = ''
    let [searchParams] = useSearchParams()
    const navigate = useNavigate()
    useEffect(()=>{
        jwt = searchParams.get('jwt')!
        api.jwtAuth({jwt})
        .then((response:AxiosResponse)=>{
            const res = response.data
            if(res.code !==0) {
                navigate('/passport/login',{replace:true})
            }else{
                api.auth(res.data)
                api.navigate({})
            }
        })
    },[])
    return(
        <>
        </>
    )
}