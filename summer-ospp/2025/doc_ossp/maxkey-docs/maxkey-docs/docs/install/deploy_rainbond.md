---
title: Rainbond部署
sidebar_position: 6
---

## Rainbond简介
 
[Rainbond](https://github.com/goodrain/rainbond) 是云原生且易用的云原生应用管理平台。云原生应用交付的最佳实践，简单易用。专注于以应用为中心的理念。赋能企业搭建云原生开发云、云原生交付云。

**对于企业：** Rainbond 是开箱即用的云原生平台，借助 Rainbond 可以快速完成企业研发和交付体系的云原生转型。

**对于开发者：** 基于 Rainbond 开发、测试和运维企业业务应用，开箱即用的获得全方位的云原生技术能力。包括但不仅限于持续集成、服务治理、架构支撑、多维度应用观测、流量管理。

**对于项目交付：** 基于 Rainbond 搭建产品版本化管理体系，搭建标准化客户交付环境，使传统的交付流程可以自动化、简单化和可管理。

Rainbond 帮助企业解决如下四类应用管理场景：

**云原生应用研发与组装** —〉**应用模型分发** —〉**应用模型安装/升级** —〉**应用智能运维**

> 它非常适合 2B 软件厂商和行业集成商使用，也适合中小型 2C 软件厂商。

## 通过Rainbond应用商店快速安装MaxKey

* 在开源应用商店中搜索 `MaxKey`，点击安装

![image-20210924143027246](https://i.loli.net/2021/09/24/oi8G2eVf1B97UDP.png)

* 部署完成后的拓扑图

* maxkey-web-maxkey 是认证服务

* maxkey-web-mgt 是管理服务

  > 账号密码均是：admin   maxkey

![image-20210924143842909](https://i.loli.net/2021/09/24/xbdDGjAIvuVMXOf.png)

## MaxKey能做什么

* MaxKey是认证平台，可将公司内部的服务平台对接至MaxKey，进行统一登录。比如可以将公司内部的 `GitLab` `禅道` `Jenkins` 等支持单点登录协议的服务平台。
* 本文将通过对接  `禅道` 实现统一登录。

### 通过Rainbond应用商店快速安装禅道

* 在开源应用商店中搜索 `禅道`，点击进行安装。

![image-20210924145650319](https://i.loli.net/2021/09/24/3VeDYIg6nm5lGrx.png)

* 安装完成后，访问 [禅道 ](https://www.zentao.net/book)进行初始化设置。

  > Mysql密码在组件的依赖中获取。

* 进入禅道后，点击  后台 > 二次开发 > 应用 > 添加应用。
  * 名称：自定义
  * 代号：maxkey
  * 免密登录：开启
  * IP：无限制

### 配置MaxKey实现统一登录

* 进入MaxKey管理服务中，进入应用管理页，编辑 `禅道项目管理`，进入编辑页面。
* 需修改：
  * 登录地址：禅道登录地址
  * 秘钥：填写上一步在禅道中添加应用时的秘钥



![image-20210924151018815](https://i.loli.net/2021/09/24/EfArgPO168YmMzS.png)

* 进入 MaxKey认证服务中，点击`禅道项目管理`，即可跳转至禅道页面并自动登录。

![maxkey-zentao](https://static.goodrain.com/images/maxkey-zentao.gif)
