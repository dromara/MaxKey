<h2>开发指南</h2>

<h3>开发工具及相关软件</h3>

JDK 1.8 +

Gradle 6.0 +

eclipse-jee-2019-12 +

Tomcat 9 +

MySQL Server 5.5 +

kafka 2.5.0 +

Redis 6 +

OpenLDAP 2.2 +


<h3>程序目录</h3>

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
			<td>许可证</td>
		</tr>
		<tr>
			<td></td>
			<td>database-sql</td>
			<td></td>
			<td></td>
			<td>版本对应MYSQL</td>
		</tr>
		<tr>
			<td></td>
			<td>docs</td>
			<td></td>
			<td></td>
			<td>项目网站</td>
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
			<td>maxkey-authentications</td>
			<td></td>
			<td></td>
			<td>登录认证</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-client-sdk</td>
			<td></td>
			<td></td>
			<td>JAVA集成使用SDK</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-connectors</td>
			<td></td>
			<td></td>
			<td>身份供应连接器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-connector-activedirectory</td>
			<td></td>
			<td>ActiveDirectory连接器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-connector-base</td>
			<td></td>
			<td>身份供应连接器接口</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-connector-ldap</td>
			<td></td>
			<td>LDAP连接器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-connector-dingtalk</td>
			<td></td>
			<td>钉钉连接器</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-connector-workweixin</td>
			<td></td>
			<td>企业微信连接器</td>
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
			<td>maxkey-dao</td>
			<td></td>
			<td></td>
			<td>数据库访问</td>
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
			<td>maxkey-identity-kafka</td>
			<td></td>
			<td>kafka身份同步</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-identity-scim</td>
			<td></td>
			<td>SCIM2.0身份管理</td>
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
			<td>认证协议实现</td>
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
			<td>maxkey-protocol-desktop</td>
			<td></td>
			<td>桌面认证实现模拟键盘输入登录</td>
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
			<td>Formbased实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-oauth-2.0</td>
			<td></td>
			<td>oauth-2.0实现</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td>maxkey-protocol-saml-2.0</td>
			<td></td>
			<td>saml-2.0实现</td>
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
			<td>maxkey-web-manage</td>
			<td></td>
			<td></td>
			<td>管理系统</td>
		</tr>
		<tr>
			<td></td>
			<td>maxkey-web-maxkey</td>
			<td></td>
			<td></td>
			<td>认证系统</td>
		</tr>
		<tr>
			<td></td>
			<td>shellscript</td>
			<td></td>
			<td></td>
			<td>启动脚本</td>
		</tr>
		<tr>
			<td></td>
			<td>build.gradle</td>
			<td></td>
			<td></td>
			<td>工程构建及版本控制</td>
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
			<td>gradleSetEnv.bat</td>
			<td></td>
			<td></td>
			<td>JDK及Gradle路径配置，用于构建脚本，需要开发人员自行配置</td>
		</tr>
		<tr>
			<td></td>
			<td>gradleBuildClean.bat</td>
			<td></td>
			<td></td>
			<td>清除历史构建版本</td>
		</tr>
		<tr>
			<td></td>
			<td>gradleBuildRelease.bat</td>
			<td></td>
			<td></td>
			<td>构建新版本</td>
		</tr>
		<tr>
			<td></td>
			<td>gradleIDEClean.bat</td>
			<td></td>
			<td></td>
			<td>清除IDE的设置</td>
		</tr>
		<tr>
			<td></td>
			<td>gradleIDETask.bat</td>
			<td></td>
			<td></td>
			<td>设置IDE</td>
		</tr>		
		</tbody>
</table>

<h3>工程构建BuildRelease</h3>

1. 配置环境变量

gradleSetEnv.bat

set JAVA_HOME=D:\JavaIDE\jdk1.8.0_91

set GRADLE_HOME=D:\JavaIDE\gradle-5.4.1


2. 启动构建

gradleBuildRelease.bat


3. 构建结果

构建包路径

MaxKey/build/maxkey-jars

依赖包路径

MaxKey/build/maxkey-depjars


<h3>问题及解决</h3>
问题1

“A cycle was detected in the build path of project: XXX” 

解决方法：
 
Eclipse Menu -> Window -> Preferences... -> Java -> Compiler -> Building -> Building path problems -> Circular dependencies -> 将Error改成Warning

问题2

Access restriction

解决方案：

Eclipse Menu -> Window -> Preferences... -> Java -> Compiler ->  Errors/Warnings界面的Deprecated and restricted API下。把Forbidden reference (access rules): 的规则由默认的Error改为Warning即可。
