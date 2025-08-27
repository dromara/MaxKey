---
title: 社交账号登录
sidebar_position: 7
---
# 第三方账号登录

为了方便用户的登录，可以通过第三方的账号(例如新浪微博、微信、钉钉等)登录MaxKey，简单配置即可实现用户登录。

本文以新浪微博为例

![authn_s_1](/images/authn/authn_s_1.png)

<h3>登录流程</h3>

![authn_s](/images/authn/authn_s.png)

## 第三方认证配置
在新浪微博开放平台https://open.weibo.com/申请接入，新浪配置如下

![authn_s_2](/images/authn/authn_s_2.png)

![authn_s_3](/images/authn/authn_s_3.png)

## 认证配置
文件启用第三方登录启用
maxkey/application-https(http).properties

<pre><code class="ini hljs">
#enable social sign on
maxkey.login.socialsignon=true
</code></pre>

## 后台参数配置

后台管理 -> "配置管理" ->"社交服务" 
![authn_sa_1](/images/authn/authn_sa_1.png)

选中 "微博" -> "编辑" ，微博的app key和app secret填入凭证和密钥

![authn_sa_2](/images/authn/authn_sa_2.png)

## 账号绑定
登录MaxKey，并绑定新浪微博账号

![authn_s_4](/images/authn/authn_s_4.png)

## 登录测试

退出后，进入登录界面，点击新浪微博图标，跳转到新浪微博，输入用户名和密码后，直接登录MaxKey,即MaxKey信任了微博账号，


## 第三方支持

MaxKey使用JustAuth作为第三方OAuth2登录认证库，认证所支持的第三方，请见JustAuth官方说明

<a href="https://www.justauth.cn/" target="_blank"  alt="JustAuth">
<img src="/images/authn/justauth.png" width = "100" height = "100"  />
</a>
