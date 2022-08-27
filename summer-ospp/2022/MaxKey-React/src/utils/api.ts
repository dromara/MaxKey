import {AxiosPromise} from 'axios'
import axios from "axios"
//import qs from "qs"
import * as CryptoJS from "crypto-js"
import CONSTS from "../shared/index"
import Cookies from "js-cookie"
export const baseUrl = 'http://127.0.0.1:9527/sign'
axios.defaults.baseURL = baseUrl
axios.defaults.headers.post['Content-Type'] = 'application/json';
export const api ={
    redirect_uri:'',
    //授权
    login(data:any):AxiosPromise{
        return axios.post('/login/signin?_allow_anonymous=true',{
           ...data
        })
    },
    //获取登录信息
    get(params:any):AxiosPromise{
        return axios.get('/login/get?_allow_anonymous=true',
            {params}
        )
    },
    //设置回调地址
    setRedirectUri(uri: string){
        this.redirect_uri = CryptoJS.enc.Base64url.parse(uri).toString(CryptoJS.enc.Utf8)
        console.log(`uri:${this.redirect_uri}`)
        localStorage.setItem(CONSTS.REDIRECT_URI,this.redirect_uri)
    },
    //使用复用信息
    congress(authParam:any):AxiosPromise{
        return axios.post('/login/congress?_allow_anonymous=true',
        {...authParam}
        )
    },

    //验证
    auth(authJwt: any){
        let user ={
            name: `${authJwt.displayName}(${authJwt.username})`,
            displayName: authJwt.displayName,
            username: authJwt.username,
            userId: authJwt.id,
            avatar: './assets/svg/avatar.svg',
            email: authJwt.email,
            passwordSetType: authJwt.passwordSetType
        }
        let hostsnames = window.location.hostname.split('.')
        let subHostName = window.location.hostname
        if(hostsnames.length>=2){
            subHostName=`${hostsnames[hostsnames.length-2]}.${hostsnames[hostsnames.length-1]}`;
        }
        Cookies.set(CONSTS.CONGRESS,authJwt.token,{path:'/'})
        Cookies.set(CONSTS.ONLINE_TICKET,authJwt.ticket,{domain:subHostName,path:'/'})
        if(authJwt.remeberMe){
            localStorage.setItem(CONSTS.REMEMBER,authJwt.remeberMe)
        }
        localStorage.setItem('user',JSON.stringify(user))
        localStorage.setItem('token',JSON.stringify(authJwt))


    },
    jwtAuth(authParam: any){
        return axios.get(`/login/jwt/trust?_allow_anonymous=true`, authParam)
    },
    //重定向地址
    navigate(authJwt:any){
        if(localStorage.getItem(CONSTS.REDIRECT_URI) != null){
            this.redirect_uri =`${localStorage.getItem(CONSTS.REDIRECT_URI)}`
            localStorage.removeItem(CONSTS.REDIRECT_URI)
        }
        if(this.redirect_uri !== ''){
            window.location.href=this.redirect_uri
        }

    },
    //获取图片验证码
    getImageCaptcha(params:any){
        return axios.get('/captcha?_allow_anonymous=true',
        {params}
        )

    },
    //获取用户列表
    appList(){
        return axios.get('/appList',
            {headers:{
                'Authorization':`Bearer ${Cookies.get(CONSTS.CONGRESS)}`,
                'token': Cookies.get(CONSTS.CONGRESS) as string | number | boolean
                }
            }
        )
    },
    //忘记密码时产生验证码
    produceOtp(params:any) {
        return axios.get('/forgotpassword/produceOtp?_allow_anonymous=true', {params});
    },
    //邮箱验证
    produceEmailOtp(params:any){
        return axios.get('/forgotpassword/produceEmailOtp?_allow_anonymous=true',{params});
    },
    //修改密码
    setPassword(params:any){
        return axios.get('/forgotpassword/setpassword?_allow_anonymous=true', {params})
    },

    //验证登录账号
    authorize(provider:string){
        return axios.get(`/logon/oauth20/authorize/${provider}?_allow_anonymous=true`)
    },
    scanqrcode(provider:string){
        return axios.get(`/logon/oauth20/scanqrcode/${provider}?_allow_anonymous=true`)        
    },
    //获取个人信息
    getProfile(){
        return axios.get('/config/profile/get',
        {
            headers:{
            'Authorization':`Bearer ${Cookies.get(CONSTS.CONGRESS)}`,
            'token': Cookies.get(CONSTS.CONGRESS) as string | number | boolean
            }
        }
        )
    },
    //更新个人信息
    updateProfile(body:any){
        return axios.put('/config/profile/update', 
        {...body},
        {
            headers:{
            'Authorization':`Bearer ${Cookies.get(CONSTS.CONGRESS)}`,
            'token': Cookies.get(CONSTS.CONGRESS) as string | number | boolean
            }
        }
        )
    },
    logout(){
        Cookies.remove(CONSTS.CONGRESS)
        return axios.get('/login/logout')

    },
    //清除token
    clear(){
        Cookies.set('token','')
        localStorage.setItem(CONSTS.REMEMBER,'')
    },  

    
}