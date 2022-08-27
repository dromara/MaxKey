import { createApp} from 'vue'
import App from './App.vue'
import router from "./router"
import Icon from '@/components/Icon.vue'
const req = require.context("./assets/svg",false,/\.svg$/)
const requireAll = (requireContext:__WebpackModuleApi.RequireContext) => requireContext.keys().map(requireContext)
requireAll(req)
//requireAll(req)
createApp(App)
    .use(router)
    .component('Icon',Icon)
    .mount('#app')
