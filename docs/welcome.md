<h1>MaxKey介绍</h1>
<b>MaxKey(马克思的钥匙)</b>用户单点登录认证系统(Sigle Sign On System)，寓意是最大钥匙,是<b>业界领先的企业级IAM身份管理和身份认证产品</b>,支持OAuth 2.0/OpenID Connect、SAML 2.0、JWT、CAS等标准化的开放协议，提供<b>简单、标准、安全和开放</b>的用户身份管理(IDM)、身份认证(AM)、单点登录(SSO)、资源管理和权限管理等。

什么是**单点登录(Single Sign On）**，简称为**SSO**？

用户只需要登录认证中心一次就可以访问所有相互信任的应用系统，无需再次登录，主要功能：
  
1.所有应用系统共享一个身份认证系统

2.所有应用系统能够识别和提取ticket信息

  MaxKey <a href="https://www.maxkey.top" target="_blank">**官方文档**</a> <span class="divider">|</span> <a href="https://github.com/shimingxy/MaxKey" target="_blank">**GitHub**</a> <span class="divider">|</span> <a href="https://gitee.com/shimingxy/MaxKey" target="_blank">**码云(Gitee)**</a>

  QQ交流群：**434469201**  <span class="divider">|</span> 邮箱EMAIL: **shimingxy@163.com**
  
<h2>认证协议</h2>

<table border="0" class="table table-striped table-bordered ">
	<tbody>
		<tr class="a">
			<th>序号</th>
			<th>协议</th>
			<th>支持</th>
		</tr>
				
		<tr class="b">
			<td>1 </td>
			<td>OAuth 2.0/OpenID Connect</td>
			<td> 高 </td>
		</tr>
		<tr class="a">
			<td>2 </td>
			<td>SAML 2.0 </td>
			<td>高 </td>
		</tr>  
		<tr class="b">
			<td>3 </td>
			<td>JWT</td>
			<td> 高 </td>
		</tr>
		<tr class="a">
			<td>4 </td>
			<td>CAS</td>
			<td>高 </td>
		</tr>  
		<tr class="b">
			<td>5 </td>
			<td>FormBased</td>
			<td> 中 </td>
		</tr>
		<tr class="a">
			<td>6 </td>
			<td>TokenBased(Post/Cookie)</td>
			<td> 中</td>
		</tr>  
		<tr class="b">
			<td>7 </td>
			<td>ExtendApi</td>
			<td> 低 </td>
		</tr>
		<tr class="a">
			<td>8 </td>
			<td>EXT</td>
			<td> 低</td>
		</tr>  
	</tbody>
</table>
<img src="{{ "/images/authz.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>登录支持</h2>

<table border="0" class="table table-striped table-bordered ">
	<tbody>
		<tr class="a">
			<th>序号</th>
			<th>登录方式</th>
		</tr>
		<tr class="b">
			<td>1 </td>
			<td>动态验证码 字母/数字/算术</td>
		</tr>
		<tr class="a">
			<td>2 </td>
			<td>双因素认证 </td>
		</tr>  
		<tr class="b">
			<td>3 </td>
			<td>短信认证  腾讯云短信/阿里云短信/网易云信 </td>
		</tr> 
		<tr class="a">
			<td>4 </td>
			<td>Google/Microsoft Authenticator/FreeOTP/支持TOTP或者HOTP</td>
		</tr>
		<tr class="b">
			<td>5 </td>
			<td>Kerberos/SPNEGO/AD域</td>
		</tr>  
		<tr class="a">
			<td>6 </td>
			<td>社交账号:微信/QQ/微博/钉钉/Google/Facebook/其他</td>
		</tr>
	</tbody>
</table>
<img src="{{ "/images/authn.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>优势</h2>

1. 提供标准的认证接口以便于其他应用集成SSO，安全的移动接入，安全的API、第三方认证和互联网认证的整合。

2. 提供用户生命周期管理，支持SCIM 2协议，基于Apache Kafka代理，通过连接器(Connector)实现身份供给同步。

3. 认证中心具有平台无关性、环境多样性，支持Web、手机、移动设备等, 如Apple iOS，Andriod等，将认证能力从B/S到移动应用全面覆盖。

4. 多种认证机制并存，各应用系统可保留原有认证机制，同时集成认证中心的认证；应用具有高度独立性，不依赖认证中心，又可用使用认证中心的认证，实现单点登录。

5. 基于Java平台开发，采用Spring、MySQL、Tomcat、Apache Kafka、Redis等开源技术，支持微服务，扩展性强。  

6. 许可证 Apache License, Version 2.0，开源免费。 

