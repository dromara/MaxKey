<template>
  <div>
    <Header />
    <form class="form-signin" method="post" >
        <h3>业界领先的IAM身份管理和认证产品</h3>
        <div class="identity" :class="nextstep?'activeTwo':'activeOne'">
          <span class="oneSpan">
            <Icon iconClass="1" class="icon" v-if="!nextstep"></Icon>
            <Icon iconClass="right" class="icon" v-else></Icon>
            验证身份
          </span>
          <Icon iconClass="arrow-right" class="icon"></Icon>
          <span class="twoSpan">
            <Icon iconClass="2" class="icon"></Icon>
            设置新密码
          </span>
        </div>
        <template v-if="!nextstep">
          <div class="find-type">
          <span @click="changeFindType(1)" id="phone">
            手机找回
          </span>
          <span @click="changeFindType(2)" id="email">
            邮箱找回
          </span>
          </div>
          <div class="phone" v-if='isphone'>
            <label for="phone" class="label">
                <Icon iconClass="mobile"></Icon>
            </label>
            <input 
            v-model="form.mobile"
            type="text" 
            name="phone" 
            id="phone" 
            class="input" 
            placeholder="手机号码"
            />
          </div>
          <div class="phone" v-else>
            <label for="email" class="label">
                <Icon iconClass="email"></Icon>
            </label>
             <input 
            v-model="form.email"
            type="text" 
            name="email" 
            id="email" 
            class="input" 
            placeholder="邮箱"
            />

          </div>
          <div class="imageCode">
              <label for="imagecode" class="label">
                <Icon iconClass="lock"></Icon>
              </label>
            <input 
            v-model="form.captcha"
            type= "text"
            name="imagecode" 
            id="imagecode" 
            class="input" 
            placeholder="图形验证码" required/>
            <div class="code">
                <img :src="imageCaptcha" @click="getImageCaptcha" alt=""/>
            </div>
          </div>
          <div class="check">
            <label for="check" class="label">
              <Icon iconClass="email"/>
            </label>
            <input
            type="text"
            id="check"
            placeholder="验证码"
            class="input" 
            v-model="form.otpCaptcha"        
            />
            <button type="button" @click="sendOtpCode" :disabled="count>0?true:false">{{count>0?count+'s':'发送验证码'}}</button>            
          </div> 
          <div class="next">
            <button type="button" class="btn" @click="onNextReset">下一步</button>
            <router-link to="/passport/login">返回登录</router-link>
          </div>            
        </template>       
    </form>    
    <el-form
    v-if="nextstep"
    ref="ruleFormRef"
    :model="ruleForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="demo-ruleForm"
  >
    <el-form-item label="新密码:" prop="pass">
      <el-input 
      v-model="ruleForm.pass" 
      type="password" 
      autocomplete="off" 
      placeholder="new password"
      show-password
      />
    </el-form-item>
    <el-form-item label="确认密码:" prop="checkPass">
      <el-input
        v-model="ruleForm.checkPass"
        type="password"
        autocomplete="off"
        placeholder="confirm password"
        show-password
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)"
        >Submit</el-button>
    </el-form-item>
  </el-form>    
    <Footer/>
  </div>  
</template>

<script lang="ts" setup>
import {reactive,ref,onBeforeMount} from "vue"
import Header from "./Header.vue"
import Icon from "./Icon.vue"
import Footer from "./Footer.vue"
import {ElMessage, ElForm,ElFormItem,ElInput,ElButton,FormInstance} from "element-plus"
import {AxiosError,AxiosResponse} from "axios"
import {api} from "../utils/api"

interface Form{
  mobile: string,
  email:string,
  password ?:string,
  otpCaptcha:string,
  captcha:string,
  confirmPassword?: string
}
let imageCaptcha = ref<string>("")
const form = reactive<Form>({
  mobile:'',
  email:'',
  captcha:'',
  otpCaptcha:'',
})
let state=""
let userId=""
let username=""
let count = ref<number>(0)
const isphone = ref<boolean>(true)
let nextstep = ref<boolean>(false)
//切换方式
const changeFindType=(index:number)=>{
  let ePhone,eEmail:HTMLSpanElement
  ePhone = document.getElementById('phone')!
  eEmail = document.getElementById('email')!
  if(index === 1){
      if(!isphone.value){
        eEmail.style.color='#000'
        eEmail.style.backgroundColor='#fff'
      } 

      ePhone.style.color='#fff'
      ePhone.style.backgroundColor='#1890ff'  
      isphone.value=true 
  }else{
      if(isphone.value){
        ePhone.style.color='#000'
        ePhone.style.backgroundColor='#fff'
      }             
      eEmail.style.color='#fff'
      eEmail.style.backgroundColor='#1890ff'  
      isphone.value = false
      
  }

}
//获取图形验证码
const getImageCaptcha=():void=>{
  api.getImageCaptcha({})
    .then((res:AxiosResponse)=>{
      res=res.data
      imageCaptcha.value = res.data.image
      state = res.data.state
    })
    .catch((err:AxiosError)=>{
      ElMessage.error(err.message)
      Promise.reject(err)
    })
}
onBeforeMount(()=>{
  getImageCaptcha()
})
//发送验证码
const sendOtpCode=()=>{
  if(isphone.value && form.mobile === ''){
    return;
  }
  if(!isphone.value && form.email === ''){
    return;
  }
  if(isphone.value){
    api.produceOtp({
      mobile:form.mobile,
      state,
      captcha: form.captcha
    })
    .then((res:AxiosResponse)=>{
      res=res.data
      if(res.code !==0){
        ElMessage.error("发送失败")
        getImageCaptcha()
      }else{
        userId = res.data.userId
        username = res.data.username
      }     
    })
    .catch(err=>{
      ElMessage.error(err.message)
      Promise.reject(err)
    })
    
  }else{
    api.produceEmailOtp({
      email:form.email,
      state,
      captcha: form.captcha,
    })
    .then((res:AxiosResponse)=>{
        res=res.data
        console.log(res)
        if(res.code !==0){
          ElMessage.error(`发送失败`)
          getImageCaptcha()
        }else{
          userId = res.data.userId
          username = res.data.username

        }
        
    })
    .catch((err:AxiosError)=>{
      ElMessage.error(err.message)
      Promise.reject(err)
    })
  }
  count.value = 59
  
  let interval = setInterval(()=>{
    count.value-=1;
    if(count.value <= 0){
      clearInterval(interval)
    }
  },1000)

}

//下一步
const onNextReset=(e:MouseEvent)=>{
  if(form.otpCaptcha===''){
    return;
  }
  nextstep.value = true
}
const ruleFormRef = ref<FormInstance>()

const validatePass= (rule:any,value:any,callback:any)=>{
  if(value=''){
    callback(new Error('Please input the password'))
  }else{
    if(ruleForm.checkPass !== ''){
      if(!ruleFormRef.value)return
      ruleFormRef.value.validateField('checkPass',()=>null)
    }
    callback()
  }
}
const validatePass2=(rule:any,value:any,callback:any)=>{
  if(value ===''){
    callback(new Error('Please input the password again'))
  }else if(value !== ruleForm.pass){
    callback(new Error("Two input don't match"))
  }else{
    callback()
  }
}

const ruleForm =reactive({
  pass:'',
  checkPass: '',
})
const rules = reactive({
  pass:[{validator: validatePass,trigger:'blur'}],
  checkPass:[{validator: validatePass2,trigger: 'blur'}]
})

const submitForm =(formEl: FormInstance | undefined)=>{
  if(!formEl)return
  formEl.validate((valid)=>{
    if(valid) {
      api.setPassword({
        forgotType: isphone.value?'mobile':'email',
        userId,
        username,
        password: ruleForm.pass,
        confirmPassword: ruleForm.checkPass,
        otpCaptcha: form.otpCaptcha,
        state
      })
      .then(res=>{
        res=res.data
        if(res.code !== 0) {
          ElMessage.error('密码修改失败')
          getImageCaptcha()
          nextstep.value = false
        }else{
          ElMessage.success('密码修改成功')
        }
      })
      .catch(err=>{
        ElMessage.error(err.message)
        Promise.reject(err)
      })
    }else {
      console.log('error submit')
      return false
    }
  })
}
</script>

<style lang="less" scoped>
 @import  "../assets/less/forgot.less";
 .el-input{
  width: 460px;
 }
 .el-form {
  margin-left: 320px;
 }
 .el-form-item {
  margin-bottom: 25px
 }
 .el-button {
    width: 80px;
    height: 40px;
 }
 
</style>