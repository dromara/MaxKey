---
title: Jenkins集成指南
sidebar_position: 10
---

## Jenkins 介绍
Jenkins 是⼀个开源软件项⽬，是基于 Java 开发的⼀种持续集成⼯具，⽤于监控持续重 复的⼯作，
旨在提供⼀个开放易⽤的软件平台，使软件的持续集成变成可能。

Jenkins 是⼀个功能强⼤的应⽤程序，允许持续集成和持续交付项⽬，⽆论⽤的是什么 平台。这是
⼀个免费的源代码，可以处理任何类型的构建或持续集成。集成 Jenkins 可以⽤ 于⼀些测试和部署
技术。

Jenkins 是⼀种软件允许持续集成。Jenkins 安装在⼀台服务上也中央构建发⽣的地⽅。 

官⽅⽹站地址：https://www.jenkins.io/

## Jenkins 安装配置
### Jenkins 安装
请参照官⽅⽂档 https://www.jenkins.io/doc/book/installing/

### 认证插件安装
登录 jenkins 安装 cas-plugin

参⻅⽂档 https://plugins.jenkins.io/cas-plugin/
<img src="/doc/images/integration/jenkins/1.png"  />

### 认证配置
配置认证服务， 进⼊Configure Global Security， 具体配置⼊下
<img src="/doc/images/integration/jenkins/2.png"  />

备注：配置的URL为：http://yourdomain/sign/authz/cas/

## MaxKey 配置及登录验证
### 应⽤配置
进⼊后台"应⽤管理" ，编辑应⽤
<img src="/doc/images/integration/jenkins/3.png"  />

进入"CAS配置",配置如下
<img src="/doc/images/integration/jenkins/4.png"  />

### 应⽤访问赋权
如果不在该列表内，可以“新增成员”

### 单点登录验证
重新登录MaxKey，点击"Jenkins"图标单点登录