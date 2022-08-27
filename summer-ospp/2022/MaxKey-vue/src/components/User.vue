<template>
 <el-form :inline="true" :model="form" label-width="180px" class="demo-form-inline">
    <el-form-item label="姓:">
        <el-input v-model="form.familyName"></el-input>
    </el-form-item>
     <el-form-item label="中间名:">
        <el-input v-model="form.middleName"></el-input>
    </el-form-item>
    <el-form-item label="名:">
        <el-input v-model="form.givenName"></el-input>
    </el-form-item>
    <el-form-item label="昵称:">
        <el-input v-model="form.nickName"></el-input>
    </el-form-item>
    <el-form-item label="证件类型:">
        <el-select v-model="form.cardType" placeholder="身份证">
            <el-option label="未知" value="未知"></el-option>
            <el-option label="身份证" value="身份证"></el-option>
            <el-option label="护照" value="护照"></el-option>
            <el-option label="学生证" value="学生证"></el-option>
            <el-option label="军人证" value="军人证"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="证件号码:">
        <el-input v-model="form.IdCardNo"></el-input>
    </el-form-item>
    <el-form-item label="婚姻状态:">
        <el-select v-model="form.marriage" placeholder="未知">
            <el-option label="未知" value="未知"></el-option>
            <el-option label="单身" value="单身"></el-option>
            <el-option label="已婚" value="已婚"></el-option>
            <el-option label="离异" value="离异"></el-option>
            <el-option label="丧偶" value="丧偶"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="出生日期:">
        <el-input v-model="form.birthday"></el-input>
    </el-form-item>
    <el-form-item label="语言偏好:">
        <el-input v-model="form.preferredLanguage"></el-input>
    </el-form-item>
    <el-form-item label="工作开始时间:">
        <el-input v-model="form.workStart"></el-input>
    </el-form-item>
    <el-form-item label="个人主页:">
        <el-input v-model="form.webSite"></el-input>
    </el-form-item>
    <el-form-item label="即时通讯:">
        <el-input v-model="form.phone"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
    </el-form-item>
 </el-form>
</template>

<script lang="ts" setup>
import {reactive,onBeforeMount} from "vue"
import {ElForm,ElFormItem,ElInput,ElSelect,ElOption,ElButton, ElMessage} from "element-plus"
import {api} from "../utils/api"
import {AxiosResponse,AxiosError} from "axios"
interface Form{
    familyName: string,
    middleName: string,
    givenName: string,
    nickName: string,
    cardType: string,
    IdCardNo: string,
    marriage: string,
    birthday: string,
    preferredLanguage: string,
    workStart: string,
    webSite: string,
    phone: string
}

let form = reactive<Form>({
    familyName:'',
    middleName: '',
    givenName: '',
    nickName: '',
    cardType: '',
    IdCardNo: '',
    marriage: '',
    birthday: '',
    preferredLanguage: '',
    workStart: '',
    webSite: '',
    phone: '',
})
onBeforeMount(()=>{
    api.getProfile()
    .then((res:AxiosResponse)=>{
        console.log(res.data)
        res=res.data
        if(res.code===0){           
            form.familyName=res.data.familyName
            form.middleName=res.data.middleName
            form.givenName = res.data.givenName
            form.nickName = res.data.nickName
            form.IdCardNo = res.data.idCardNo
            form.preferredLanguage = res.data.preferredLanguage
            form.webSite = res.data.webSite
        }
    })
    .catch((err:AxiosError)=>{
        Promise.reject(err)
        ElMessage.error(err.message)
    })

})
const onSubmit=(e:MouseEvent):void=>{
    e.preventDefault()
    api.updateProfile(form)
    .then((res)=>{
        res=res.data
        if(res.code === 0){
            ElMessage.success("更新成功")
            localStorage.setItem("user",JSON.stringify(form))
        }else{
             ElMessage.error("更新失败")
        }
    })
    .catch(err=>{
        Promise.reject(err)
        ElMessage.error(err.message)
    })
}
</script>

<style lang="less">
.el-form {
    background-color: #fff;
    padding:50px 0;
   
}
.el-form-item {
    margin-right: 0 !important
}
.el-button {
    display: block;
}
.el-input {
    width: 358px;
}
</style>