import React,{useEffect,useState} from "react"
import {api} from "../utils/api"
import {baseUrl} from "../utils/api"
import {AxiosResponse,AxiosError} from "axios"
import {message,Spin} from "antd"
import "../assets/scss/applist.scss"
export default function AppList(){
    const [appList,setAppList] = useState<Array<any>>([])
    const [loading,setLoading] = useState<boolean>(true)
    useEffect(()=>{
        api.appList()
        .then((res:AxiosResponse)=>{
            if(res.data.code===0){
                setAppList(res.data.data)
                setLoading(false)
            }          
        })
        .catch((err:AxiosError)=>{
            message.error(err.message)
            Promise.reject(err)
        })

    },[])
    const onAuthz = (e: React.MouseEvent<HTMLElement>,appId:string):void=>{
        e.preventDefault()
        for(let i = 0;i < appList.length; i++){
            if(appList[i].id === appId &&(appList[i].protocol === 'Basic' || appList[i].inducer === 'SP')){
                window.open(appList[i].loginUrl);
                return;
            }
        }
        window.open(`${baseUrl}/authz/${appId}`)
    }
    return (
        <Spin spinning={loading}>
            <ul>           
            {appList.length>0&&appList.map((app:any)=>
                <li onClick={(e)=>onAuthz(e,app.id)} key={app.id}><img src={app.iconBase64} alt=""/></li>
            )}        
            </ul>
        </Spin>
        

    )
}