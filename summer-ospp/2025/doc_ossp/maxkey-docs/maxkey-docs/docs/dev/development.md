---
layout: zh/default
sidebar_position: 1
---
# 开发指南



## 开发工具及相关软件


<table border="0" class="table table-striped table-bordered ">
	<thead>
		<th  >软件</th><th>版本</th><th>推荐</th><th>备注</th>
	</thead>
	<tbody>
		<tr>
			<td>JDK</td>
			<td>17 +</td>
			<td></td>
			<td>JAVA运行及开发工具包</td>
		</tr>
		<tr>
			<td>Node.js</td>
			<td>v20.15.1 +</td>
			<td></td>
			<td>Node.js开发工具包</td>
		</tr>
		<tr>
			<td>eclipse-jee</td>
			<td>2024-09</td>
			<td>推荐</td>
			<td>JAVA开发工具</td>
		</tr>
		<tr>
			<td>Visual Studio Code</td>
			<td>1.95.3 +</td>
			<td>推荐</td>
			<td>前端开发工具</td>
		</tr>
		<tr>
			<td>MySQL</td>
			<td>8.0.30 +</td>
			<td></td>
			<td>数据库服务器</td>
		</tr>
		<tr>
			<td>Gradle</td>
			<td>8.8+ </td>
			<td></td>
			<td>代码构建</td>
		</tr>
		<tr>
			<td>Redis</td>
			<td>6 +</td>
			<td>可选</td>
			<td>高速缓存内存数据库</td>
		</tr>
		<tr>
			<td>Kafka</td>
			<td>2.5.0 +</td>
			<td>可选</td>
			<td>用户生命周期管理同步消息中间件</td>
		</tr>
		<tr>
			<td>RocketMQ</td>
			<td>4.9.0 +</td>
			<td>可选</td>
			<td>用户生命周期管理同步消息中间件</td>
		</tr>
	</tbody>
</table>		
 

## 项目目录

```
MaxKey                                              #项目目录
├── README.md                                       #MaxKey项目介绍
├── LICENSE                                         #Apache License v2许可证
├── NOTICE                                          #MaxKey版权声明
├── ReleaseNotes.txt                                #GA版本发布记录描述
├── config                                          #构建方式配置Jar,Docker,Standard
├── docker                                          #Docker & docker-compose部署文件
├── integrations                                    #集成的例子
├── maxkey-authentications                          #登录认证      
│   ├── maxkey-authentication-core                  #登录认证-核心功能
│   ├── maxkey-authentication-provider              #登录认证-认证服务
│   └── maxkey-authentication-provider-mgt          #管理端登录认证-认证服务
├── maxkey-common                                   #通用基础包和工具类
├── maxkey-core                                     #基础包
├── maxkey-lib                                      #本地jar包引入
├── maxkey-persistence                              #数据库持久化和实时数据同步
├── maxkey-protocols                                #认证协议实现
│   ├── maxkey-protocol-authorize                   #认证协议及单点注销实现
│   ├── maxkey-protocol-cas                         #CAS认证协议实现
│   ├── maxkey-protocol-extendapi                   #飞扩展API实现
│   ├── maxkey-protocol-formbased                   #Formbased实现，桌面认证实现开发浏览器插件实现
│   ├── maxkey-protocol-jwt                         #JWT实现
│   ├── maxkey-protocol-oauth-2.0                   #OAuth 2.x，OpenID Connect实现
│   ├── maxkey-protocol-saml-2.0                    #SAML 2.0实现
│   └── maxkey-protocol-tokenbased                  #tokenbased实现
├── maxkey-starters                                 #通用starter组件
│   ├── maxkey-starter-captcha                      #图形验证码starter组件
│   ├── maxkey-starter-ip2location                  #IP地址转归属地starter组件
│   ├── maxkey-starter-otp                          #OTP动态口令starter组件
│   ├── maxkey-starter-sms                          #短信发送starter组件
│   ├── maxkey-starter-social                       #社交账号登录starter组件
│   └── maxkey-starter-web                          #web公共starter组件
├── maxkey-synchronizers                            #身份同步器maxkey-synchronizer
│   ├── maxkey-synchronizer                         #同步器接口
│   ├── maxkey-synchronizer-activedirectory         #微软Active Directory同步器
│   ├── maxkey-synchronizer-feishu                  #飞书同步器
│   ├── maxkey-synchronizer-ldap                    #标准LDAP同步器
│   ├── maxkey-synchronizer-dingtalk                #钉钉同步器
│   ├── maxkey-synchronizer-workweixin              #企业微信同步器
│   └── maxkey-synchronizer-jdbc                    #JDBC同步器
├── maxkey-web-apis                                 #开放接口API
│   ├── maxkey-web-api-rest                         #REST身份管理接口
│   └── maxkey-web-api-scim                         #SCIM2.0身份管理接口
├── maxkey-web-frontend                             #web前端
│   ├── maxkey-web-app                              #认证服务前端
│   └── maxkey-web-mgt-app                          #管理服务前端
├── maxkey-webs                                     #web服务
│   ├── maxkey-web-maxkey                           #认证系统
│   ├── maxkey-web-mgt                              #管理系统
│   ├── maxkey-web-openapi                          #开放API接口
│   └── maxkey-gataway                              #基于Spring Cloud套件的网关服务
├── shellscript                                     #Window和LINUX启动脚本
├── sql                                             #数据库MYSQL脚本,GA版本对应SQL
├── checkstyle                                      #编码规范配置
├── gradle.properties                               #版本参数配置
├── build.gradle                                    #默认工程构建及版本控制
├── build_cnf.gradle                                #工程构建配置脚本
├── gradle                                          #gradle的配置
├── release.bat                                     #标准构建版本
├── release_cnf_docker.bat                          #构建Docker配置
├── release_cnf_standard.bat                        #构建标准配置
├── release_cnf_tradition.bat                       #构建传统配置
├── release_docker.bat                              #docker构建
├── release_docker_jib.bat                          #docker jib构建
├── release_frontend_docker.bat                     #docker前端构建
├── setEnvVars.bat                                  #JDK及Gradle路径配置,开发人员配置
└── settings.gradle                                 #项目模块配置
```
