/* eslint-disable jsx-a11y/anchor-has-content */
import React,{useState,useEffect} from "react"
import "../assets/scss/login.scss"
import { AxiosError, AxiosResponse } from 'axios'
import {ReactComponent as User} from "../assets/svg/user.svg"
import {ReactComponent as Key} from "../assets/svg/key.svg"
import {ReactComponent as EyesOpen} from "../assets/svg/eyes-open.svg"
import {ReactComponent as EyesClose} from "../assets/svg/eyes-close.svg"
import { ReactComponent as Eweima } from "../assets/svg/erweima.svg"
import {ReactComponent as Lock} from "../assets/svg/lock.svg"
import logo from "../assets/logo.jpg"
import Footer from "../components/Footer"
import {useNavigate,Link} from "react-router-dom"
import {api} from "../utils/api"
import CONSTS from "../shared/index"
import {message} from "antd"
export default function Login(){
    const [username,setUsername]=useState<string>()
    const [password,setPassword]=useState<string|number>()
    const [isVisible,setVisible]=useState<boolean>(false)
    const [rememberMe,setRemember]=useState<boolean>(false)
    const [captcha,setCode] = useState<string>("")
    const [providers,setProviders] = useState<Array<object>>([])
    const [imageCaptcha,setImage] = useState<string>('')
    const [state,setSta] = useState<string>('')
    const [qrScan,setqrScan] = useState<string>("")
    const [isqrCode,setisqrCode] = useState<boolean>(false)
    let captchaType = ""
    let loginType="normal"
    const navigate = useNavigate()

    const onUserNameChange=(e:React.FormEvent<HTMLInputElement>)=>setUsername(((e.target) as HTMLInputElement).value)
    const onPasswordChange=(e:React.FormEvent<HTMLInputElement>)=>setPassword(((e.target) as HTMLInputElement).value)
    const onRememberChange=(e:React.FormEvent<HTMLInputElement>)=>setRemember(((e.target) as HTMLInputElement).checked)
    const onCodeChange = (e:React.FormEvent<HTMLInputElement>)=>setCode(((e.target) as HTMLInputElement).value)
    
    //点击show后切换visible
    const show=()=>{
      setVisible(!isVisible)
    }
    
    //获得验证码
    const getImageCaptcha=():void=>{
      api.getImageCaptcha({state,captcha:captchaType})
      .then((res:AxiosResponse)=>{
        res=res.data
        setImage(res.data.image);
      })
      .catch((err:AxiosError)=>Promise.reject(err))
    }
    const getUrl=(value:string):string=>{
      let params = window.location.search.substring(1).split('&')
      for(let i =0; i< params.length; i++){
        let param = params[i].split('=')
        if(param[0]=== value){
          return param[1]
        }
      }
      return ''
    }
    const congressLogin=(congress:string)=>{
      api.congress({congress:congress})
      .then((response: AxiosResponse)=>{
        const res=response.data
        if(res.code !== 0){
          console.log(res.msg)
        }else{
          //设置用户token信息
          api.auth(res.data)
          api.navigate({})
        }     
      })
      .catch((err:AxiosError)=>{
        console.log(err.message)
        Promise.reject(err)
      })
    }

   //初始化
   useEffect(()=>{
    if(getUrl(CONSTS.REDIRECT_URI) !==''){
      api.setRedirectUri(getUrl(CONSTS.REDIRECT_URI))
    }
    if(getUrl(CONSTS.CONGRESS)){
      congressLogin(getUrl(CONSTS.CONGRESS))
    }
    //init socails,state
    api.clear()    
    api.get({remember_me:localStorage.getItem(CONSTS.REMEMBER)})
      .then((response:AxiosResponse)=>{
        const res=response.data
        if(res.code !== 0){
          console.log(res.msg)
        }else{        
          if(res.data.state){          
            setProviders(res.data.socials.providers)
            setSta(res.data.state)
            setqrScan(res.data.socials.qrScan)           
            captchaType = res.data.captcha
            if(captchaType !== 'NONE'){
              //初始化图像验证码
              api.getImageCaptcha({state,captcha:captchaType})
                .then((res:AxiosResponse)=>{
                  res=res.data
                  setImage(res.data.image)
                })
            }
          }
        }
      })
      .catch((err:AxiosError)=>{
        Promise.reject(err)
      })

   },[])
   
  const socialauth=(provider:string): void =>{
    //clear User
    localStorage.setItem('user',JSON.stringify({}))
    api.authorize(provider)
    .then((res:AxiosResponse)=>{
      // debugger
      window.location.href = res.data.data
    })
  }
   
   const login=()=>{
    if(username===''|| password===''|| captcha===''){
      return;
   }
    localStorage.setItem(CONSTS.REMEMBER,rememberMe === true?"true":"false")
    
    api.login(
      {
        authType:loginType,
        state:state,
        username,
        password,
        captcha,
        mobile:null,
        otpCaptcha:null,
        remeberMe:rememberMe,
      })
      .then((response:AxiosResponse)=>{            
            const res=response.data
            if(res.code!== 0){
              message.error("登录失败!")
              getImageCaptcha()
            }else{
              //清空路由复用信息?
              api.auth(res.data)              
              api.navigate({})
              navigate("/dashboard/home")
              
            }                           
          })
      .catch((err:AxiosError)=>{
          console.log(err.message)
          Promise.reject(err)
      })              
   }  
   
   const switchTab=(index:number):void=>{
    if(index===1){
      setisqrCode(false)
    }
    if(index===2){
      setisqrCode(true)
      localStorage.setItem('user',JSON.stringify({}))
      api.scanqrcode(qrScan)
      .then((response:AxiosResponse)=>{
        const res=response.data
        if(res.code === 0){
          if(qrScan === 'workweixin'){
            qrScanWorkweixin(res.data)
          }else if(qrScan === 'dingtalk'){
            qrScanDingtalk(res.data)
          }else if(qrScan  === 'feishu'){
            qrScanFeishu(res.data)
          }
        }
      })
      .catch((err:AxiosError)=>{
        Promise.reject(err)
      })
    }
  }

  const qrScanWorkweixin=(data: any) => {
    //see doc https://developer.work.weixin.qq.com/document/path/91025
    // @ts-ignore
    let wwLogin = new WwLogin({
      id: 'div_qrcodelogin',
      appid: data.clientId,
      agentid: data.agentId,
      redirect_uri: encodeURIComponent(data.redirectUri),
      state: data.state,
      href: 'data:text/css;base64,LmltcG93ZXJCb3ggLnFyY29kZSB7d2lkdGg6IDI1MHB4O30NCi5pbXBvd2VyQm94IC50aXRsZSB7ZGlzcGxheTogbm9uZTt9DQouaW1wb3dlckJveCAuaW5mbyB7d2lkdGg6IDI1MHB4O30NCi5zdGF0dXNfaWNvbiB7ZGlzcGxheTpub25lfQ0KLmltcG93ZXJCb3ggLnN0YXR1cyB7dGV4dC1hbGlnbjogY2VudGVyO30='
    });
  }
const qrScanFeishu=(data: any)=> {
    //see doc https://open.feishu.cn/document/common-capabilities/sso/web-application-sso/qr-sdk-documentation
    //remove old div
    var qrcodeDiv = document.querySelector('#div_qrcodelogin');
    qrcodeDiv?.childNodes.forEach(function (value, index, array) {
      qrcodeDiv?.removeChild(value);
    });
    // @ts-ignore
    let fsredirectUri = `https://passport.feishu.cn/suite/passport/oauth/authorize?client_id=${data.clientId}&redirect_uri=${encodeURIComponent(
      data.redirectUri
    )}&response_type=code&state=${data.state}`;
    // @ts-ignore
    var redirectUri = fsredirectUri;
    // @ts-ignore
    let QRLoginObj = QRLogin({
      id: 'div_qrcodelogin',
      goto: redirectUri,
      width: '300',
      height: '300',
      style: 'border: 0;'
    });
  }

const qrScanDingtalk=(data: any)=> {
    //see doc https://open.dingtalk.com/document/isvapp-server/scan-qr-code-to-log-on-to-third-party-websites
    var url = encodeURIComponent(data.redirectUri);
    var gotodingtalk = encodeURIComponent(
      `https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=${data.clientId}&response_type=code&scope=snsapi_login&state=${data.state}&redirect_uri=${url}`
    );
    // @ts-ignore
    let ddredirect_uri = `https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=${data.clientId}&response_type=code&scope=snsapi_login&state=${data.state}&redirect_uri=${data.redirectUri}`;
    // @ts-ignore
    var obj = DDLogin({
      id: 'div_qrcodelogin', //这里需要你在自己的页面定义一个HTML标签并设置id，例如<div id="login_container"></div>或<span id="login_container"></span>
      goto: gotodingtalk, //请参考注释里的方式
      style: 'border:none;background-color:#FFFFFF;',
      width: '360',
      height: '400'
    });
  }
    return(
      <div>
        <header>
          <div className= 'title'>
            <img src={logo} alt="" />
              Max
              <span>Key</span>
              单点登录认证系统
          </div>
        </header>
        <form className="form-signin" method="post">
          <h3>业界领先的IAM身份管理和认证产品</h3>
          <div className="login-type">
            <span onClick = {()=>switchTab(1)} className={!isqrCode?'activeOne':''}>
              <i><User/></i>
              账号登录
            </span>
            <span onClick = {()=>switchTab(2)} className={isqrCode?'activeTwo':''}>
              <i><Eweima/></i>
              扫码登录
            </span>
          </div>
          {!isqrCode
            ?<>
              <div className="username">
                <label htmlFor="username" className="label">
                  <User/>
                </label>
                <input 
                value={username} 
                onChange={onUserNameChange}
                type="text" 
                name="username" 
                id="username" 
                className="form-control" 
                placeholder="用户名"
                />
              </div>
              <div className="password">
                <label htmlFor="inputPassword" className="label">
                    <Key/>
                </label>
                <input 
                value={password}
                onChange={onPasswordChange}
                type={isVisible?'text':'password'} 
                name="password" 
                id="inputPassword" 
                className="form-control" 
                placeholder="密码" required/>
                <EyesOpen
                  style={{
                    display:isVisible?'block':'none'
                  }}
                  className="eyes-img"
                  onClick={show}
                  />
                <EyesClose
                style={{
                  display:isVisible?'none':'block'
                  }}           
                className="eyes-img"
                onClick={show}/>
              </div>
              <div className="check">
                <label htmlFor="check" className="label">
                  <Lock />
                </label>
                <input
                type="text"
                id="check"
                placeholder="验证码"
                className="form-control" 
                value={captcha}   
                onChange={onCodeChange}        
                />
                <div className="code">
                  <img src = {imageCaptcha} alt="" onClick={getImageCaptcha}/>
                </div>
              </div>          
              <div className="remember">
                <input type="checkbox" id="remember" checked={rememberMe} onChange={onRememberChange}/>
                <label htmlFor="remember">记住登录</label>
                <Link to="/passport/forgot" replace={true} >忘记密码</Link>
              </div>        
              <button className="btn" type="button" onClick={login} >登录</button>
              <div className="otherLogin">
                <>
                <span>其他登录方式</span>
              {
                providers.map((prod:any)=>
                  <img src= {prod.icon} alt="" onClick={()=>socialauth(prod.provider)} key={prod.id}/>
                )
              }
              </>
              </div>
            </>
            :<div>
              <div id='div_qrcodelogin'></div>
            </div>
          }
          
      </form>
        <Footer/>
      </div>
           
    )
}
