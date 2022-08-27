<template>
    <ul>
        <template v-for="app in appList">
            <li @click="onAuthz($event,app.id)"><img :src="app.iconBase64" alt=""/></li>
        </template> 
    </ul>   
</template>

<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { onBeforeMount,ref } from 'vue';        
import {api} from "../utils/api"
import {baseUrl} from "../utils/api"

let appList = ref([])
onBeforeMount(()=>{
    api.appList()
    .then((res)=>{       
        appList.value = res.data.data
    })
    .catch(err=>{
        ElMessage.error(err.message)
        Promise.reject(err)
    })  
})

const onAuthz=(e:MouseEvent,appId:string):void=>{
    e.preventDefault()
    for(let i = 0;i < appList.value.length; i++){
        if(appList.value[i].id === appId &&(appList.value[i].protocol == 'Basic' || appList.value[i].inducer == 'SP')){
            window.open(appList.value[i].loginUrl);
            return
        }
    }
    window.open(`${baseUrl}/authz/${appId}`)
}
</script>

<style lang="less" scoped>
ul {
    display: flex;
    flex-wrap: wrap;
    li {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 254px;
        height: 127px;
        background-color: #fff;
        margin-right: 24px;
        margin-bottom: 12px;
        img {
            height: 65px;
            width: 65px;
        }
        &:hover {
            cursor: pointer;
            box-shadow: 
                0px 2px 5px 3px rgba(0,0,0,0.15) ;
                
        }
    }
    &>li:nth-child(4n){
        margin-right: 0;
    }
}
 

</style>