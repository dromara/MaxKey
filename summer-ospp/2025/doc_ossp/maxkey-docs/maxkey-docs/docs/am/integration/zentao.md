---
title: 禅道项目管理集成指南
sidebar_position: 26
---

## 禅道(ZenTao) 介绍
禅道是第一款国产的开源项目管理软件，她的核心管理思想基于敏捷方法scrum，内置了产品管理和项目管理，同时又根据国内研发现状补充了测试管理、计划管理、发布管理、文档管理、事务管理等功能，在一个软件中就可以将软件研发中的需求、任务、bug、用例、计划、发布等要素有序的跟踪管理起来，完整地覆盖了项目管理的核心流程。

官方网站地址：https://www.zentao.net/

## 安装配置
禅道11.5.1版本开始，增加第三方应用免密登录禅道的功能

具体单点登录的方案

https://www.zentao.net/book/zentaopmshelp/344.html

软件下载

https://www.zentao.net/download.html

ZenTaoPMS.***.win**.exe

在window本地安装

安装完成后目录
<img src="/doc/images/integration/zentao/1.png"  />

运行start.exe

点击“服务”，修改端口避免和maxkey冲突
<img src="/doc/images/integration/zentao/2.png"  />

去掉”启用Apache用户访问验证”，后“启动禅道”
<img src="/doc/images/integration/zentao/3.png"  />

## 禅道配置免密登录禅道
https://www.zentao.net/book/zentaopmshelp/344.html

禅道11.5.1版本开始，增加第三方应用免密登录禅道的功能。

下面，我们来介绍一下免密登录的具体配置。

### 添加应用，开启免密登录

登录禅道，到后台--二次开发--应用，添加应用时开启免密登录。

只有免密登录，选择开启后方可使用。

<img src="/doc/images/integration/zentao/4.png"  />

### 免密登录的签名机制
以图中红框内容为例，假设你的禅道访问地址为www.zentao.net 或者 http://47.105.128.128/biz 。

我们的请求格式则为：

http://www.zentao.net/api.php?m=user&f=apilogin&account=account&code=test&time=timestamp&token=token

或者 

http://47.105.128.128/biz/api.php?m=user&f=apilogin&account=account&code=test&time=timestamp&token=token

<img src="/doc/images/integration/zentao/5.png"  />

说明：

m：模块名，是固定的，不可更改。

f ：方法名， 是固定的，不可更改。

account：你想要登录的用户名，该用户需存在于禅道系统中。

code：应用代号。

time：当前时间戳，php可用time()函数获取。时间戳只一次有效，下次免密登录时，需要刷新页面。

token：算法为：code、应用密钥、time()字符串合并，再进行 md5 加密。

```
$code  = 'test';
$key   = 'a5246932b0f371263c252384076cd3f0';
$time  = '1557034496';
$token = md5($code . $key . $time);
```

### 错误提示
401  缺少参数或应用未设置密钥

403  被限制访问

404  应用不存在

405  token已失效

406  用户不存在

407  错误的时间戳

## MaxKey 配置及登录验证
### 应⽤配置
进⼊后台"应⽤管理" ，编辑应⽤
<img src="/doc/images/integration/zentao/6.png"  />

"API配置",配置如下
<img src="/doc/images/integration/zentao/7.png"  />

"扩展配置",配置如下
<img src="/doc/images/integration/zentao/8.png"  />

### 应⽤访问赋权
如果不在该列表内，可以“新增成员”

### 单点登录验证
重新登录MaxKey，点击"Jenkins"图标单点登录