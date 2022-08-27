<template>
    <header class="header">
        <div className= 'title'>
        <img src="../assets/logo.jpg" alt="" />
            Max
            <span>Key</span>
            单点登录认证系统
        </div>
    </header>
</template>

<script lang="ts" setup>
</script>

<style lang="less">
.header {
    height: 64px;
    border-bottom: 1px solid #e5e5e5;
    padding-left: 198px;
    .title {
      display: flex;
      height: 100%;
      align-items: center;
      font-size: 28px;
      font-weight: 650;
      img {
        width: 44px;
        height: 44px;
      }
      span {
        color: #ffd700
      }
    }
    
  }
</style>        