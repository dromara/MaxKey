<template>
  
</template>

<script lang="ts" setup>
import { onBeforeMount } from "@vue/runtime-core"
import { ElMessage } from "element-plus/lib/components"
import CONSTS from "@/shared"
import {api} from "../utils/api"
import { useRoute,useRouter } from "vue-router"

let redirect_uri = ''
const route = useRoute()
const router = useRouter()
onBeforeMount(()=>{
    redirect_uri = route.query[CONSTS.REDIRECT_URI] as string
    api.logout()
    localStorage.removeItem('token')
    if(redirect_uri == null || redirect_uri == ''){
        router.push('/passport/login')
    }else{
        router.push(redirect_uri)
    }
})

</script>