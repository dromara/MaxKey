import {createRouter,createWebHashHistory} from "vue-router"
export default createRouter({
    history:createWebHashHistory(),
    routes:[
        {
            path:"/",
            component:()=>Promise.resolve(import("../components/login.vue"))
        },
        {
            path:'/passport/login',
            component:()=>Promise.resolve(import("../components/login.vue")),
        },
        {
            path:'/passport/forgot',
            component:()=>Promise.resolve(import("../components/Forgot.vue"))
        },
        {
            path:'/dashboard',
            component:()=>Promise.resolve(import("../components/Home.vue")),
            children:[
                {
                    path:'home',
                    component:()=>Promise.resolve(import("../components/AppList.vue"))
                },
                {
                    path:'user',
                    component:()=>Promise.resolve(import("../components/User.vue"))
                }
            ]         
        },
        {
            path:'/passport/logout',
            component: ()=>Promise.resolve(import("../components/Logout.vue"))

        },
        {
            path:'/404',
            name:'NotFound',
            component:()=>Promise.resolve(import('@/components/NotFound.vue'))

        },{
            path:'/:catchAll(.*)',
            redirect: '/404'
        }
    ]
})