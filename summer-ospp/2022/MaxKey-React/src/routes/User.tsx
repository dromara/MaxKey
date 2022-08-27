/* eslint-disable no-template-curly-in-string */
import React,{useEffect,useState}  from "react"
import {Button,Form,Input,Select,message,Spin} from 'antd'
import useSyncCallback from "../hook/useSyncCallback"
import {AxiosResponse,AxiosError} from "axios"
import {api} from "../utils/api"
import "../assets/scss/user.scss"
const {Option} = Select
const validateMessages = {
    required: '${label} is required!',
    types: {
      number: '${label} is not a valid number!',      
    },
  };
export default function User() {
    const [form] =Form.useForm()
    //let formValue = useRef(null)
    const [formjson,setFormJson] = useState({})
    const [loading,setLoading] = useState(true)
    const onFinish=(values:any)=>{
        //console.log(values)
        api.updateProfile(values)
        .then((response:AxiosResponse)=>{
            const res=response.data
            if(res.code === 0){
                message.success("更新成功")
                localStorage.setItem("user",JSON.stringify(values))
            }else{
                message.error("更新失败")
            }
        })
        .catch((err:AxiosError)=>{
            Promise.reject(err)
            message.error(err.message)
        })
        
    }
   const func= useSyncCallback(()=>{
        console.log(formjson)
        form.resetFields()
        setLoading(false) 
   })
    
    useEffect(()=>{
        api.getProfile()
        .then((response:AxiosResponse)=>{           
            const res=response.data
            if(res.code===0){ 
                setFormJson({
                    familyName:res.data.familyName,
                    middleName:res.data.middleName,
                    givenName : res.data.givenName,
                    nickName : res.data.nickName,
                    idCardNo : res.data.idCardNo,
                    preferredLanguage : res.data.preferredLanguage,
                    webSite : res.data.webSite,
                })
                func();                              
            }
        })
        .catch((err:AxiosError)=>{
            Promise.reject(err)
            message.error(err.message)
        })
    },[form])
    
    return (
        <>
        <Spin spinning = {loading}>          
            <Form 
                form={form}
                onFinish={onFinish}
                validateMessages={validateMessages}
                initialValues={formjson}
                >               
                    <Form.Item  name='familyName' label="姓:">
                        <Input />
                    </Form.Item>
                    <Form.Item  name='middleName' label="中间名:">
                        <Input />
                    </Form.Item>
                            
                    <Form.Item name='givenName' label="名:">
                        <Input/>
                    </Form.Item>
                    <Form.Item name='nickName' label="昵称:">
                        <Input/>
                    </Form.Item>
                    <Form.Item  label="证件类型:">
                        <Select defaultValue="身份证">
                            <Option value="未知">未知</Option>
                            <Option value="身份证">身份证</Option>
                            <Option value="护照">护照</Option>
                            <Option value="学生证">学生证</Option>
                            <Option value="军人证">军人证</Option>
                        </Select>
                    </Form.Item>
                    
                    <Form.Item name='idCardNo' label="证件号码:" rules={[{type:'number'}]}>
                        <Input/>
                    </Form.Item>
                    <Form.Item label="婚姻状态:">
                        <Select defaultValue="未知">
                            <Option value="未知">未知</Option>
                            <Option value="单身">单身</Option>
                            <Option value="已婚">已婚</Option>
                            <Option value="离异">离异</Option>
                            <Option value="丧偶">丧偶</Option>
                        </Select>
                    </Form.Item>
                    
                    <Form.Item name='birthday' label="出生日期:" >
                        <Input/>
                    </Form.Item>
                    <Form.Item name='preferredLanguage'label="语言偏好:">
                        <Input/>
                    </Form.Item>
                    <Form.Item name='workStart' label="工作开始时间:" >
                        <Input/>
                    </Form.Item> 
                    <Form.Item name='webSite' label="个人主页:">
                        <Input/>
                    </Form.Item> 
                    <Form.Item name='phone' label="即时通讯:">
                        <Input/>
                    </Form.Item> 
                    <Form.Item >
                        <Button type="primary" htmlType="submit">
                        提交
                        </Button>
                    </Form.Item>
            </Form>
        </Spin>   
        </>
    )
       
}
