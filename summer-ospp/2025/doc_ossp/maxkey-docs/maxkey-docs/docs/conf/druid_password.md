---
title: Druid配置数据库加密
sidebar_position: 9
---

Druid 是一个 JDBC 组件库，包含数据库连接池、SQL Parser 等组件, 被大量业务和技术产品使用或集成，经历过最严苛线上业务场景考验，是你值得信赖的技术产品。

## 导入Druid依赖
gradle.properties
```
druidVersion                    =1.2.15
druidspringbootstarterVersion   =1.2.15
```

build.gradle
```
implementation group: 'com.alibaba', name: 'druid', version: "${druidVersion}"
implementation group: 'com.alibaba', name: 'druid-spring-boot-starter', version: "${druidspringbootstarterVersion}"
```
版本根据实际情况进行更新，当前版本1.2.15

## 命令窗口中执行

```java
java -cp druid-1.2.15.jar com.alibaba.druid.filter.config.ConfigTools 数据库密码
```

假设数据库密码是**maxkey**,命令如下

![druid_encrypt](/images/config/druid_encrypt.png)

## 项目配置文件
1、filters添加config

2、配置解密，同时指定公钥

生成环境建议如下配置通过java-jar启动命令时指定spring.druid.publickey的值(java -jar xx.jar --spring.druid.publickey=公钥)，避免通过yml获取到公钥，开发环境可将公钥配置在idea启动参数内。

```ini
spring.druid.publickey=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIOu1Ew3t8xLDoaVs1byFllwf55yRqz1ekJviQ7wWsuYnOL4WWsIb7tUj9foiYt58kdua6rWcVBAsTjHHR4tLPECAwEAAQ==
spring.datasource.username=root
spring.datasource.password=F78ZV92w6MtSfMajYRqHDeorcColhpMiIokwfl2ecFLAhKS6gPMxzAEJgALtssonYNx0aDFQnQ0/ZjMhxeqL7w==
spring.datasource.filters=config
spring.datasource.connectionProperties=config.decrypt=true;config.decrypt.key=${spring.druid.publickey}
```