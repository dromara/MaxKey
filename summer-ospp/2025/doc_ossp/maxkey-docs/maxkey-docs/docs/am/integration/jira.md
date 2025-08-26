---
title:  Jira集成指南
sidebar_position: 10
---

## Atlassian Jira介绍
JIRA是Atlassian公司出品的项目与事务跟踪工具，被广泛应用于缺陷跟踪、客户服务、需求收集、流程审批、任务跟踪、项目跟踪和敏捷管理等工作领域。

JIRA中配置灵活、功能全面、部署简单、扩展丰富，其超过150项特性得到了全球115个国家超过19,000家客户的认可。

官方网站地址：https://www.atlassian.com/software/jira

## Jira安装配置
### Jira 安装
请参照官方文档
https://confluence.atlassian.com/adminjiraserver0813/installing-jira-applications-1027137422.html

安装路径D:\MaxKey\3party\Jira8.13.10

数据路径D:\MaxKey\3party\Jira8.13.10_data

### Jira启动https
修改D:\MaxKey\3party\Jira8.13.10\conf

```xml
<Connector  port="8443" protocol="org.apache.coyote.http11.Http11Protocol"
            maxHttpHeaderSize="8192" SSLEnabled="true"
            maxThreads="150" minSpareThreads="25" maxSpareThreads="75"
            enableLookups="false" disableUploadTimeout="true"
            acceptCount="100" scheme="https" secure="true"
            // highlight-start
            keystoreFile="D:/MaxKey/3party/Jira8.13.10/conf/maxkeyserver.keystore" keystorePass="maxkey"
            // highlight-end
            clientAuth="false" sslProtocol="TLS" useBodyEncodingForURI="true"/>
```

### 认证配置
配置认证服务，进入Jira，具体配置入下
<img src="/doc/images/integration/jira/1.png"  />
<img src="/doc/images/integration/jira/2.png"  />
<img src="/doc/images/integration/jira/3.png"  />
基本URL更改为https://jira.maxkey.top:8443

<img src="/doc/images/integration/jira/4.png"  />
<img src="/doc/images/integration/jira/5.png"  />

备注:

单一登录发行者:http://yourdomain/sign/saml

身份提供者单一登录URL:http://yourdomain/sign/authz/saml20/{appid}

用户名映射:```{NameID}```

## MaxKey 配置及登录验证
### 应⽤配置
进⼊后台"应⽤管理" ，编辑应⽤
<img src="/doc/images/integration/jira/6.png"  />

进入"SAML配置",配置如下
<img src="/doc/images/integration/jira/7.png"  />

### 应⽤访问赋权
如果不在该列表内，可以“新增成员”

### 单点登录验证
重新登录MaxKey，点击“Jira”图标单点登录