<template>
    <footer class="footer">
        <div>
            MaxKey Enterprise Edition
        <br />
        Version v3.5.2 GA
        <br />
        Copyright  
        <Icon iconClass="C"/>
        2022 
        <a href="//ww.maxkey.top"> MaxKey </a>
        . All rights reserved . 
        </div>
    </footer>
  
</template>

<script lang="ts">
import { defineComponent } from "@vue/runtime-core"
import Icon from "./Icon.vue"
export default defineComponent({
    components:{Icon}
})
</script>

<style lang="less" scoped>
    .footer {
        width: 100%;
        padding-top: 30px;
        margin-bottom: 30px;
        border-top: 1px solid #e5e5e5;
        text-align: center;
        font-size: 14px;
        //color: rgb(139, 140, 140);
        color: rgba(0, 0, 0, 0.45);
        a {
            text-decoration: none;
            color: #1890FF;
        }
    }
</style>