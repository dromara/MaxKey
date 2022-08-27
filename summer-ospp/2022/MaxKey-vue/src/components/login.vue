<template>
    <div>
        <Header />
        <form class="form-signin" method="post" >
          <h3>业界领先的IAM身份管理和认证产品</h3>
          <div class="login-type" :class="isqrCode ? 'activeTwo' : 'activeOne'">
            <span @click="switchTab(1)" >
              <i><Icon iconClass="user" class="user"></Icon></i>
              账号登录
            </span>
            <span @click = "switchTab(2)">
              <i><Icon iconClass="erweima"></Icon></i>
              扫码登录
            </span>
          </div>
          <template v-if = "!isqrCode">
            <div class="username">
              <label for="username" class="label">
                <Icon iconClass="user" class="erweima"></Icon>
              </label>
              <input 
              v-model="form.username"
              type="text" 
              name="username" 
              id="username" 
              class="form-control" 
              placeholder="用户名"
              />
            </div>
            <div class="password">
              <label for="inputPassword" class="label">
                  <Icon iconClass="key"></Icon>
              </label>
              <input 
              v-model="form.password"
              :type= "passwordType"
              name="password" 
              id="inputPassword" 
              class="form-control" 
              placeholder="密码" required/>
              <i @click="show" v-show="visible">
                  <Icon 
                iconClass="eyes-open"
                class="eyes-img"
                />
              </i>             
              <i @click="show" v-show="!visible">
                  <Icon 
                      iconClass="eyes-close"      
                      class="eyes-img"/>
              </i>            
            </div>
            <div class="check">
              <label for="check" class="label">
                <Icon iconClass="lock"/>
              </label>
              <input
              type="text"
              id="check"
              placeholder="验证码"
              class="form-control" 
              v-model="form.captcha"        
              />           
              <div class="code">
                <img :src="imageCaptcha" @click="getImageCaptcha" alt=""/>
              </div>
            </div>          
            <div class="remember">
              <input type="checkbox" id="remember" v-model="rememberMe" />
              <label for="remember">记住登录</label>
              <router-link to="/passport/forgot">忘记密码</router-link>
            </div>              
            <button type="button" class="btn"  @click="login" >登录</button>
            <div class="otherLogin">
              <span> 其他登录方式 </span>
                <template
                  v-for="prod in providers"
                  >
                  <img :src= 'prod.icon' alt="" width="32px" @click="socialauth(prod.provider)">                
                  </template>                                 
            </div>
        </template>
        <div v-if="isqrCode">
          <div id="div_qrcodelogin"></div>
        </div>          
      </form>     
      <Footer/>
    </div>   
</template>

<script lang="ts" setup>
import { AxiosError,AxiosResponse} from 'axios'
import {api} from "../utils/api"
import {reactive,ref,onBeforeMount, onBeforeUpdate} from "vue"
import CONSTS from "../shared/index"
import Icon from "./Icon.vue"
import {ElLoading,ElMessage} from "element-plus"
import {useRouter} from "vue-router"
import Header from "./Header.vue"
import Footer from "./Footer.vue"
interface Form {
    username: string,
    password: string,
    captcha: string ,
     
}

let state=""
let captchaType = ""
let loginType="normal"
const router = useRouter()

const form = reactive<Form>({
        username:"",
        password:"",
        captcha:"", 
    })
const rememberMe = ref<boolean>(false)
const isqrCode  =ref<boolean>(false)
let passwordType=ref("password")
let visible=ref(false)
let imageCaptcha= ref<string>("")

let providers = ref<object[]>([])//reactive不可以监视？
let qrScan = ""
const getUrl=(value:string)=>{
  let params=window.location.search.substring(1).split("&")
  for(let i = 0; i < params.length; i++){
    let param=params[i].split('=')
    if(param[0]===value){
      return param[1]
    }
  }
  return '';
}

const socialauth=(provider:string): void =>{
  //clear User
  localStorage.setItem('user',JSON.stringify({}))
  api.authorize(provider)
  .then((res)=>{
    // debugger
    window.location.href = res.data.data
  })
}

const congressLogin=(congress:string)=>{
  api.congress({congress:congress})
    .then((res: AxiosResponse)=>{
      res=res.data
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

onBeforeMount(()=>{
  if(getUrl(CONSTS.REDIRECT_URI)!==''){
    api.setRedirectUri(getUrl(CONSTS.REDIRECT_URI))
  }
  if(getUrl(CONSTS.CONGRESS)){
    congressLogin(getUrl(CONSTS.CONGRESS))
  }
  //init socails,state
  api.clear()
  
  api.get({remember_me:localStorage.getItem(CONSTS.REMEMBER)})
    .then((res:AxiosResponse)=>{
      res=res.data
      if(res.code != 0){
        console.log(res.msg)
      }else{        
        if(res.data.state){          
          providers.value=res.data.socials.providers
          qrScan = res.data.socials.qrScan
          state =res.data.state
          captchaType = res.data.captcha
          if(captchaType !== 'NONE'){
            //初始化图像验证码
            api.getImageCaptcha({state,captcha:captchaType})
              .then((res)=>{
                res=res.data
                imageCaptcha.value=res.data.image
              })
          }
        }
      }
    })
    .catch((err:AxiosError)=>{
      Promise.reject(err)
    })

})

const show=()=>{
    visible.value=!(visible.value);
    if(!visible.value)passwordType.value="password"
    else passwordType.value="text"   
}

const getImageCaptcha=():void=>{
  api.getImageCaptcha({state,captcha:captchaType})
  .then((res)=>{
    res=res.data
    imageCaptcha.value=res.data.image;
  })
  .catch(err=>Promise.reject(err))
}

const login=()=>{   
   if(form.username===''|| form.password===''|| form.captcha===''){
      return;
   }
    localStorage.setItem(CONSTS.REMEMBER,rememberMe.value === true?"true":"false")
    api.login(
      {
        authType:loginType,
        state,
        username:form.username,
        password:form.password,
        captcha:form.captcha,
        mobile:null,
        otpCaptcha:null,
        remeberMe:rememberMe.value,
      })
      .then((res:AxiosResponse)=>{            
            res=res.data
            if(res.code!=0){
              //ElMessage.error(res.message)
              getImageCaptcha()
            }else{
              //清空路由复用信息?
              api.auth(res.data)              
              api.navigate({})
              router.push("/dashboard/home")
              
            }                  
           // res.data.token&&localStorage.setItem('token',res.data.token)            
          })
      .catch((err:AxiosError)=>{
          console.log(err.message)
          Promise.reject(err)
      })          
}

const switchTab=(index:number):void=>{
  if(index===1){
    isqrCode.value = false
  }
  if(index===2){
    isqrCode.value=true
    localStorage.setItem('user',JSON.stringify({}))
    api.scanqrcode(qrScan)
    .then((res:AxiosResponse)=>{
      res=res.data
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
</script>

<style lang="less" scoped>
 @import  "../assets/less/login.less";
</style>