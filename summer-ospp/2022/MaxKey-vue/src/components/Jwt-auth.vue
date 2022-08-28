<script lang="ts" setup>
import {onBeforeMount} from "vue"
import {useRoute,useRouter} from "vue-router"
import {api} from "../utils/api"
let jwt = ''
const route = useRoute()
const router = useRouter()
onBeforeMount(()=>{
    jwt = route.query['jwt'] as string
    api.jwtAuth({jwt})
    .then((res)=>{
        res=res.data
        if(res.code != 0){
            router.push('/passport/login')
        }else{
            api.auth(res.data)
            api.navigate({})
        }
    })
})
</script>

