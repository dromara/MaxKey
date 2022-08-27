<template>
    <div class="body">
        <header>
            <div class= 'title'>
                <img src="../assets/logo.jpg" alt="" />
                Max
                <span>Key</span>
                单点登录认证系统
            </div>
            <router-link to="/passport/logout">
                <Icon iconClass="quit"></Icon>
            </router-link>
        </header>
        <div class="nav">    
            <ul>
                <li>
                    <router-link to="/dashboard/home">应用</router-link>
                </li>
                <li>
                    <router-link to="/dashboard/user">个人</router-link>
                </li>
            </ul>
        </div>
        <div class="container">
            <router-view></router-view>
        </div>
        <Footer/>
    </div>
  
</template>

<script lang="ts" setup>
import Footer from "./Footer.vue"
import Icon from "./Icon.vue"
</script>

<style lang="less" scoped>
* {
    margin: 0;
    padding: 0;
}

html,.body {
    width: 100%;
    height: auto;
    min-height: 100%;
    overflow-x: hidden;
    background-color: #f5f7fa;
}
li {
    list-style: none;
}
header {
    height: 64px;
    border-bottom: 1px solid #e5e5e5;
    padding-left: 16px;
    position: relative;
    display: flex;
    align-items: center;
    .title {
      display: flex;
      height: 100%;
      align-items: center;
      font-size: 20px;
      font-weight: 650;
      img {
        width: 50px;
        height: 50px;
      }
      span {
        color: #ffd700
      }
    }
    a {
        position: absolute;
        right: 16px;

    }
    
  }
.nav {
    ul {
        display: flex;
        flex-flow: row wrap;
        align-items: center;
        background-color: #001529;
        height: 46px;
        padding-left: 198px;
        li {
            width: 80px;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;  
            background-color: #001529;          
            a {
                text-decoration: none;
                color: #a6adb4;
            }
            &:hover {
                background-color: #1890ff;
                a {
                    color: #fff;
                }
            }
        }
      }
}
.container {
    padding: 24px 198px;   
}
  

</style>