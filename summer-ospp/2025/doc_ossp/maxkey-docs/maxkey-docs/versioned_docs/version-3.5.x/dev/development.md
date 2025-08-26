---
layout: zh/default
---
# 开发指南



## 开发工具及相关软件

<table border="0" class="table table-striped table-bordered ">
	<thead>
		<th  >软件</th><th>版本</th><th>备注</th>
	</thead>
	<tbody>
		<tr>
			<td>JDK</td>
			<td>1.8 +</td>
			<td>JAVA运行及开发工具包</td>
		</tr>
		<tr>
			<td>Node.js</td>
			<td>v 16.15.0 LTS +</td>
			<td>Node.js开发工具包</td>
		</tr>
		<tr>
			<td>eclipse-jee</td>
			<td>2022-09 +</td>
			<td>推荐JAVA开发工具</td>
		</tr>
		<tr>
			<td>Visual Studio Code</td>
			<td>Version 1.73.1 +</td>
			<td>推荐前端开发工具</td>
		</tr>
		<tr>
			<td>MySQL</td>
			<td>8.0.21 +</td>
			<td>数据库服务器</td>
		</tr>
		<tr>
			<td>Gradle</td>
			<td>7.5+ </td>
			<td>代码构建</td>
		</tr>
		<tr>
			<td>Tomcat/tomcat-embed</td>
			<td>9 +</td>
			<td>应用服务器(内置)</td>
		</tr>
		<tr>
			<td>Kafka</td>
			<td>2.5.0 +</td>
			<td>用户生命周期管理同步消息中间件(可选)</td>
		</tr>
		<tr>
			<td>RocketMQ</td>
			<td>4.9.0 +</td>
			<td>用户生命周期管理同步消息中间件(可选)</td>
		</tr>
		<tr>
			<td>Redis</td>
			<td>6 +</td>
			<td>高速缓存内存数据库(可选)</td>
		</tr>
		<tr>
			<td>OpenLDAP</td>
			<td>2.2 +</td>
			<td>企业目录服务器(可选)</td>
		</tr>
	</tbody>
</table>		

## 项目代码目录

<table border="0" class="table table-striped table-bordered ">
	<thead>
		<th  >MaxKey</th><th>一级目录</th><th>二级目录</th><th>三级目录</th><th>说明</th>
	</thead>
	<tbody>
		<tr>
			<td></td>
			<td>README.md</td>
			<td></td>
			<td></td>
			<td>关于MaxKey项目</td>
		</tr>
		<tr>
			<td></td>
			<td>LICENSE</td>
			<td></td>
			<td></td>
			<td>Apache License v2许可证</td>
		</tr>
		<tr>
			<td></td>
			<td>NOTICE</td>
			<td></td>
			<td></td>
			<td>MaxKey版权声明</td>
		</tr>
		<tr>
			<td></td>
			<td>ReleaseNotes.txt</td>
			<td></td>
			<td></td>
			<td>GA版本发布记录描述</td>
		</tr>
		<tr>
			<td></td>
			<td>config</td>
			<td></td>
			<td></td>
			<td>构建方式配置Jar,Docker,Standard</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-authentications</td>
			<td></td>
			<td></td>
			<td>登录认证</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-authentication-captcha</td>
			<td></td>
			<td>登录认证-验证码</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-authentication-core</td>
			<td></td>
			<td>登录认证-核心功能</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-authentication-otp</td>
			<td></td>
			<td>登录认证-令牌和一次性口令</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-authentication-provider</td>
			<td></td>
			<td>登录认证-认证服务</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-authentication-social</td>
			<td></td>
			<td>登录认证-社交账号</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-common</td>
			<td></td>
			<td></td>
			<td>通用基础包和工具类</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-core</td>
			<td></td>
			<td></td>
			<td>基础包</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-identitys</td>
			<td></td>
			<td></td>
			<td>身份管理</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-identity-rest</td>
			<td></td>
			<td>REST身份管理接口</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-identity-scim</td>
			<td></td>
			<td>SCIM2.0身份管理接口</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-synchronizers</td>
			<td></td>
			<td></td>
			<td>身份同步器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-synchronizer</td>
			<td></td>
			<td>同步器接口</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-synchronizer-activedirectory</td>
			<td></td>
			<td>微软Active Directory同步器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-synchronizer-feishu</td>
			<td></td>
			<td>飞书同步器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-synchronizer-ldap</td>
			<td></td>
			<td>标准LDAP同步器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-synchronizer-dingtalk</td>
			<td></td>
			<td>钉钉同步器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-synchronizer-workweixin</td>
			<td></td>
			<td>企业微信同步器</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-lib</td>
			<td></td>
			<td></td>
			<td>使用jar包</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-persistence</td>
			<td></td>
			<td></td>
			<td>数据库持久化和实时数据同步</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-protocols</td>
			<td></td>
			<td></td>
			<td>认证协议实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-authorize</td>
			<td></td>
			<td>认证协议及单点注销实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-cas</td>
			<td></td>
			<td>CAS认证协议实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-extendapi</td>
			<td></td>
			<td>扩展API实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-formbased</td>
			<td></td>
			<td>Formbased实现，桌面认证实现开发浏览器插件实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-jwt</td>
			<td></td>
			<td>JWT实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-oauth-2.0</td>
			<td></td>
			<td>OAuth 2.x，OpenID Connect实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-saml-2.0</td>
			<td></td>
			<td>SAML 2.0实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-tokenbased</td>
			<td></td>
			<td>tokenbased实现</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-web-frontend</td>
			<td></td>
			<td></td>
			<td>web前端</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-web-app</td>
			<td></td>
			<td>认证服务前端</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-web-mgt-app</td>
			<td></td>
			<td>管理服务前端</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-webs</td>
			<td></td>
			<td></td>
			<td>web服务</td>
		</tr>
		
		
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-web-maxkey</td>
			<td></td>
			<td>认证系统</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-web-mgt</td>
			<td></td>
			<td>管理系统</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-gataway</td>
			<td></td>
			<td>基于Spring Cloud套件的网关服务</td>
		</tr>
		<tr>
			<td></td>
			<td>shellscript</td>
			<td></td>
			<td></td>
			<td>Window和LINUX启动脚本</td>
		</tr>
		<tr>
			<td></td>
			<td>sql</td>
			<td></td>
			<td></td>
			<td>数据库MYSQL脚本,GA版本对应SQL</td>
		</tr>
		<tr>
			<td></td>
			<td>checkstyle</td>
			<td></td>
			<td></td>
			<td>编码规范配置</td>
		</tr>
		<tr>
			<td></td>
			<td>build.gradle</td>
			<td></td>
			<td></td>
			<td>默认工程构建及版本控制</td>
		</tr>
		<tr>
			<td></td>
			<td>build_cnf.gradle</td>
			<td></td>
			<td></td>
			<td>工程构建配置脚本</td>
		</tr>
		<tr>
			<td></td>
			<td>gradle.properties</td>
			<td></td>
			<td></td>
			<td>版本参数配置</td>
		</tr>
		<tr>
			<td></td>
			<td>settings.gradle</td>
			<td></td>
			<td></td>
			<td>项目引入</td>
		</tr>
		<tr>
			<td></td>
			<td>gradle</td>
			<td></td>
			<td></td>
			<td>gradle的配置</td>
		</tr>
		
		<tr>
			<td></td>
			<td>release.bat</td>
			<td></td>
			<td></td>
			<td>标准和Jar构建版本</td>
		</tr>
		<tr>
			<td></td>
			<td>release_docker.bat</td>
			<td></td>
			<td></td>
			<td>docker构建版本</td>
		</tr>
		<tr>
			<td></td>
			<td>setEnvVars.bat</td>
			<td></td>
			<td></td>
			<td>JDK及Gradle路径配置,开发人员配置</td>
		</tr>
		<tr>
			<td></td>
			<td>release_cnf_docker.bat</td>
			<td></td>
			<td></td>
			<td>构建Docker配置</td>
		</tr>
		<tr>
			<td></td>
			<td>release_cnf_jar.bat</td>
			<td></td>
			<td></td>
			<td>构建Jar配置</td>
		</tr>
		<tr>
			<td></td>
			<td>release_cnf_standard.bat</td>
			<td></td>
			<td></td>
			<td>构建Standard配置</td>
		</tr>
			
		</tbody>
</table>

## 开发环境启动

1)MaxKey统一认证系统

maxkey-webs/maxkey-web-maxkey/src/main/java/org/maxkey/MaxKeyApplication.java 

2)MaxKey身份安全管理系统

maxkey-webs/maxkey-web-mgt/src/main/java/org/maxkey/MaxKeyMgtApplication.java

3)MaxKey统一认证前端

maxkey-web-frontend/maxkey-web-app
```powershell
yarn start
```

4)MaxKey身份安全管理前端

maxkey-web-frontend/maxkey-web-mgt-app
```powershell
yarn start
```

## 项目构建

### 标准构建

1.配置环境变量
```powershell
setEnvVars.bat

set JAVA_HOME=D:\JavaIDE\jdk1.8.0_91

set GRADLE_HOME=D:\IDE\gradle-7.2
```

2.启动构建

```powershell
gradlew build -x test或者release.bat
```

3.构建结果

构建包路径

MaxKey/build/maxkey-jars

依赖包路径

MaxKey/build/MaxKey-v(version)GA


### Docker构建

1.Docker 构建配置

```powershell
release_cnf_docker.bat
```

2.启动构建

```powershell
gradlew build jib -x test或者release_docker.bat
```

3.构建的结果

maxkey-web-manage/

maxkey-web-maxkey/


### SpringBoot构建

1.SpringBoot Jar 构建配置

```powershell
release_cnf_jar.bat
```

2.启动构建

gradlew build -x test或者release.bat


3.构建的结果

maxkey-webs/maxkey-web-manage/

maxkey-webs/maxkey-web-maxkey/



### 前端构建Build

1)MaxKey统一认证前端

maxkey-web-frontend/maxkey-web-app

```powershell
ng build --prod --base-href /maxkey/
```

2)MaxKey身份安全管理前端

maxkey-web-frontend/maxkey-web-mgt-app

```powershell
ng build --prod --base-href /maxkey-mgt/
```

## 问题及解决

```
“A cycle was detected in the build path of project: XXX” 
```

解决方法：
 
Eclipse Menu -> Window -> Preferences... -> Java -> Compiler -> Building -> Building path problems -> Circular dependencies -> 将Error改成Warning

问题2

```
Access restriction
```

解决方案：

Eclipse Menu -> Window -> Preferences... -> Java -> Compiler ->  Errors/Warnings界面的Deprecated and restricted API下。把Forbidden reference (access rules): 的规则由默认的Error改为Warning即可。


问题3
```powershell
 yarn start
 ```

```
CategoryInfo          : SecurityError: ，PSSecurityException
FullyQualifiedErrorId : UnauthorizedAccess
```

解决方案：
```powershell
Set-ExecutionPolicy RemoteSigned -Scope Process
```


<h3><a href="./development_v3.3">v3.3开发指南</a></h3>