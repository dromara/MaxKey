import React,{ useState,useEffect} from "react"
import Header from "../components/Header"
import {ReactComponent as One} from "../assets/svg/1.svg"
import {ReactComponent as Two} from "../assets/svg/2.svg"
import {ReactComponent as Arrow} from "../assets/svg/arrow-right.svg"
import {ReactComponent as Mobile} from "../assets/svg/mobile.svg"
import {ReactComponent as Lock} from "../assets/svg/lock.svg"
import {ReactComponent as Email} from "../assets/svg/email.svg"
import {Button,message,Form,Input} from "antd"
import {ReactComponent as Right} from "../assets/svg/right.svg"
import { AxiosResponse,AxiosError } from "axios"
import {Link} from "react-router-dom"
import {api} from "../utils/api"
import Footer from "../components/Footer"
import "../assets/scss/forgot.scss"

export default function Forgot(){
    const [mobile,setMobile] = useState<string>("")
    const [captcha,setCaptcha] = useState<string>("")
    const [otpCaptcha,setotpCaptcha] = useState<string>("")
    const [isphone,setIsphone] = useState<boolean>(true)
    const [imageCaptcha,setImage] = useState<string>('')
    const [email,setEmail] = useState<string>('')
    const [count,setCount] =useState<number>(60)
    const [nextstep,setNextStep] = useState<boolean>(false)
    const [state,setState] =useState<string>("")
    let userId = ""
    let username = ""

    const onMobileChange=(e:React.FormEvent<HTMLInputElement>)=>{
        setMobile(((e.target) as HTMLInputElement).value)
    }

    const onCaptchaChange = (e:React.FormEvent<HTMLInputElement>)=>{
        setCaptcha(((e.target) as HTMLInputElement).value)
    }
    const onEmailChange = (e:React.FormEvent<HTMLInputElement>)=>{
        setEmail(((e.target) as HTMLInputElement).value)
    }
    const onCodeChange = (e:React.FormEvent<HTMLInputElement>)=>{
        setotpCaptcha(((e.target) as HTMLInputElement).value)
    }

    //获得验证码
    const getImageCaptcha=():void=>{
        api.getImageCaptcha({})
        .then((response:AxiosResponse)=>{
          const res=response.data
          setImage(res.data.image)
          setState(res.data.state)
        })
        .catch((err:AxiosError)=>Promise.reject(err))
    }

    //初始化
    useEffect(()=>{
        getImageCaptcha()
    },[])

    //发送验证码
    const sendOtpCode=()=>{
        if(isphone && mobile === ''){
          return;
        }
        if(!isphone && email === ''){
          return;
        }

        //产生验证码
        if(isphone){
          api.produceOtp({
            mobile,
            state,
            captcha
          })
          .then((response:AxiosResponse)=>{
            const res=response.data
            if(res.code !==0){
              message.error("发送失败")
              getImageCaptcha()
            }else{
              userId = res.data.userId
              username = res.data.username
            }     
          })
          .catch(err=>{
            message.error(err.message)
            Promise.reject(err)
          })
          
        }else{
          api.produceEmailOtp({
            email,
            state,
            captcha,
          })
          .then((response:AxiosResponse)=>{
              const res=response.data
              //console.log(res)
              if(res.code !==0){
                message.error(`发送失败`)
                getImageCaptcha()
              }else{
                userId = res.data.userId
                username = res.data.username     
              }             
          })
          .catch((err:AxiosError)=>{
            message.error(err.message)
            Promise.reject(err)
          })
        }
        
        let interval = setInterval(()=>{
          setCount(c=>c-=1);
          if(count <= 0){
            clearInterval(interval)
          }
        },1000)      
      }

    //下一步
    const onNextReset=()=>{
        if(otpCaptcha === ''){
            return;
        }
        setNextStep(true)
    }

    //如果点击的是第一个设置为true,点击的是第二个设置为false
    const changeFindType=(index:number):void=>{
        let ePhone,eEmail:HTMLSpanElement
        ePhone = document.getElementById('phone')!
        eEmail = document.getElementById('email')!
        if(index === 1){
            if(!isphone){
                eEmail.style.color='#000'
                eEmail.style.backgroundColor='#fff'
            } 
            
            ePhone.style.color='#fff'
            ePhone.style.backgroundColor='#1890ff'  
            setIsphone(true)
            
        }else{
            if(isphone){
                ePhone.style.color='#000'
                ePhone.style.backgroundColor='#fff'
            }             
            eEmail.style.color='#fff'
            eEmail.style.backgroundColor='#1890ff'  
            setIsphone(false)          
        }
    }
    const [form] = Form.useForm()

    const onFinish=(values:any)=>{
        api.setPassword({
            forgotType: isphone?'mobile':'email',
            userId,
            username,
            password:values.password,
            confirmPasswrod:values.confirm,
            otpCaptcha,
            state
        })
        .then((response:AxiosResponse)=>{
            const res=response.data
            if(res.code !== 0){
                message.error('密码修改失败!')
                getImageCaptcha()
                setNextStep(false)
            }else{
                message.success('密码修改成功!')
            }

        })
        .catch((err:AxiosError)=>{
            message.error(err.message)
            Promise.reject(err)//返回一个拒绝对象，包含错误原因
        })
    }

    const formItemLayout = {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
      };

    return (
        <div>
            <Header></Header>
            <form className="form-signin" method="post">
                <h3>业界领先的IAM身份管理和认证产品</h3>
                <div className="identity">            
                    <span className={!nextstep?'activeOne':''}>
                        {!nextstep?<One/>:<Right/>}
                        验证身份
                    </span>
                        <Arrow/>
                    <span className={nextstep?'activeTwo':''}>
                        <Two/>
                        设置新密码
                    </span>
                </div>
                {!nextstep &&
                <>
                <div className="find-type">
                    <span onClick={()=>changeFindType(1)} id="phone">
                    手机找回
                    </span>
                    <span onClick={()=>changeFindType(2)} id="email">
                    邮箱找回
                    </span>
                </div>
                {isphone
                    ? <div className="phone">
                        <label htmlFor="phone" className="label">
                            <Mobile/>
                        </label>
                        <input 
                        value={mobile}
                        type="text" 
                        name="phone" 
                        id="phone" 
                        className="input" 
                        placeholder="手机号码"
                        onChange={onMobileChange}
                        />
                    </div>
                    : <div className="phone">
                        <label htmlFor="email" className="label">
                            <Email/>
                        </label>
                        <input 
                        value={email}
                        type="text" 
                        name="email" 
                        id="phone" 
                        className="input" 
                        placeholder="邮箱"
                        onChange={onEmailChange}
                        />
                     </div>              
                }
                <div className="imageCode">
                    <label htmlFor="imagecode" className="label">
                    <Lock/>
                    </label>
                    <input 
                    value={captcha}
                    type= "text"
                    name="imagecode" 
                    id="imagecode" 
                    className="input" 
                    onChange={onCaptchaChange}
                    placeholder="图形验证码" required/>
                <div className="code">
                    <img src={imageCaptcha} alt="" />
                </div>
                </div>
                <div className="check">
                    <label htmlFor="check" className="label">
                    <Email/>
                    </label>
                    <input
                    type="text"
                    id="check"
                    placeholder="验证码"
                    className="input" 
                    value={otpCaptcha}    
                    onChange={onCodeChange}   
                    />
                    <button 
                    type="button" 
                    onClick={sendOtpCode}
                    disabled={count > 0 &&count <= 59 ? true : false}
                    >{count > 0&&count <= 59 ? count+'s' : '发送验证码'}
                    </button>                    
                </div>       
                <div className="next">
                    <button className="btn" type="button" onClick={onNextReset}>下一步</button>
                    <Link to="/passport/login" replace={true}> 返回登录</Link>
                </div> 
                </>}       
            </form>
           {nextstep&&
            <Form id="form"
                form = {form}
                {...formItemLayout}
                onFinish={onFinish}
            >
                <Form.Item
                    name="password"
                    label="Password"
                    rules={[
                    {
                        required: true,
                        message: 'Please input your password!',
                    },
                    ]}
                    hasFeedback
                >
                    <Input.Password/>
                </Form.Item>

                <Form.Item
                    name="confirm"
                    label="Confirm Password"
                    dependencies={['password']}
                    hasFeedback
                    rules={[
                    {
                        required: true,
                        message: 'Please confirm your password!',
                    },
                    ({ getFieldValue }) => ({
                        validator(_, value) {
                        if (!value || getFieldValue('password') === value) {
                            return Promise.resolve();
                        }
                        return Promise.reject(new Error('The two passwords that you entered do not match!'));
                        },
                    }),
                    ]}
                >
                <Input.Password />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        提交
                    </Button>
                </Form.Item>
                <Link to="/passport/login" replace={true}>返回登录</Link>
            </Form>
            }
            <Footer/>
        </div>
    )

}
