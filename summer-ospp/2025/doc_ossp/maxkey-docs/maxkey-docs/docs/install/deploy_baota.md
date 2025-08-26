---
title: 宝塔面板部署
sidebar_position: 7
---

## 宝塔简介
 
[宝塔服务器面板，一键全能部署及管理]( https://www.bt.cn/u/AjsXmi) 安全高效的服务器运维面板

![baota-docker](/images/install/baota/baota.png)

## 前提

安装宝塔面板，前往[宝塔面板](https://www.bt.cn/u/AjsXmi)官网，选择对应的脚本下载安装。

宝塔版本 9.2.0+

## 宝塔面板一键部署MaxKey

1. 登录宝塔面板，在菜单栏中点击 Docker，根据提示安装 Docker 和 Docker Compose 服务。

![baota-docker](/images/install/baota/baota-0.png)

在宝塔面板安装 Docker 服务，若已有则跳过。

2. 在Docker-应用商店查询到 MaxKey，点击安装

![baota-maxkey-1](/images/install/baota/baota-1.png)

3. 设置域名等基本信息，点击确定

![baota-maxkey-2](/images/install/baota/baota-2.png)

名称：应用名称，默认maxkey-随机字符

版本选择：默认latest

域名：如需通过域名直接访问，请在此配置域名并将域名解析到服务器

允许外部访问：如您需通过IP+Port直接访问，请勾选，如您已经设置了域名，请不要勾选此处

端口：默认 8088，可自行修改

提交后面板会自动进行应用初始化，大概需要1-5分钟，初始化完成后即可访问。

4. 安装过程中无法拉取镜像解决方案

配置docker加速

![baota-maxkey-docker](/images/install/baota/baota-docker.png)


## 访问 MaxKey

如您设置了域名，请直接在浏览器地址栏中输入域名访问，如```http://demo.maxkey.top:8088/maxkey/#/passport/login``` ，即可访问 MaxKey 控制台。

如您选择了通过IP+Port访问，请在浏览器地址栏中输入域名访问 ```http://<宝塔面板IP>:8088/maxkey/#/passport/login```，即可访问 MaxKey 控制台。

![baota-maxkey-login](/images/install/baota/baota-maxkey.png)

默认账号 admin

默认密码 maxkey

## 宝塔官方安装帮助

[【教程贴】Docker应用-MaxKey 安装帮助](https://www.bt.cn/bbs/forum.php?mod=viewthread&tid=139535)



