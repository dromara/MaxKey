<h1>1、MaxKey介绍</h1>
**MaxKey(马克思的钥匙)**，寓意是最大钥匙，是基于开放用户安全身份认证系统（User Security Access System）,提供简单、可靠和安全的用户认证和单点登录，包含用户认证、单点登录、资源管理、权限管理等。

什么是**单点登录(Single Sign On）**，简称为**SSO**？

  用户只需要登录认证中心一次就可以访问所有相互信任的应用系统，无需再次登录。
  
  <a href="https://github.com/shimingxy/MaxKey" target="_blank">**MaxKey on GitHub**</a>
  
  <a href="https://gitee.com/shimingxy/MaxKey" target="_blank">**MaxKey on 码云(Gitee)**</a>

  QQ交流群：**434469201** <a href="https://jq.qq.com/?_wv=1027&k=5WrpQ6o" target="_blank">点击链接加入群聊</a>
  


<h2>1.1、标准化认证协议：</h2>
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

<h2>1.2、登录支持</h2>

<table border="0" class="table table-striped table-bordered ">
	<tbody>
		<tr class="a">
			<th>序号</th>
			<th>登录方式</th>
		</tr>
		<tr class="b">
			<td>1 </td>
			<td>动态验证码</td>
		</tr>
		<tr class="a">
			<td>2 </td>
			<td>双因素认证 </td>
		</tr>  
		<tr class="b">
			<td>3 </td>
			<td>Google Authenticator</td>
		</tr>
		<tr class="a">
			<td>4 </td>
			<td>Kerberos/Spengo/AD域</td>
		</tr>  
		<tr class="b">
			<td>5 </td>
			<td>社交账号:微信/QQ/微博/钉钉/Google/Facebook/其他</td>
		</tr>
	</tbody>
</table>

1. 提供标准的认证接口以便于其他应用集成SSO，安全的移动接入，安全的API、第三方认证和互联网认证的整合。

2. 认证中心具有平台无关性、环境多样性，支持Web、手机、移动设备等, 如Apple iOS，Andriod等，将认证能力从B/S到移动应用全面覆盖。

3. 多种认证机制并存，各应用系统可保留原有认证机制，同时集成认证中心的认证；应用具有高度独立性，不依赖认证中心，又可用使用认证中心的认证，实现单点登录。

4. 基于Java平台开发，采用Spring、Spring Boot、MyBatis、bootstrap等开源技术，支持微服务，扩展性强。  

5. 许可证 Apache License, Version 2.0，开源免费。 

<h1>2、界面</h1>
<h2>2.1、MaxKey认证</h2>

<h3>2.1.1、登录界面</h3>

<img src="{{ "/images/maxkey_login.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>2.1.2、主界面</h3>
<img src="{{ "/images/maxkey_index.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>2.2、MaxKey管理</h2>

<h3>2.2.1、用户管理界面</h3>

<img src="{{ "/images/maxkey_mgt_users.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>2.2.2、应用管理界面</h3>

<img src="{{ "/images/maxkey_mgt_apps.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h1>3、下载</h1>

百度网盘下载
<table border="0" class="table table-striped table-bordered ">
	<tbody>
		<tr class="a">
			<th>版本</th>
			<th>日期</th>
			<th>下载地址</th>
			<th>提取码</th>
		</tr>
				
		<tr class="b">
			<td>v 1.0 GA </td>
			<td>2019/12/06</td>
			<td> <a href="https://pan.baidu.com/s/15j7RSUQybCVlHx8uyFk2rQ" target="_blank">https://pan.baidu.com/s/15j7RSUQybCVlHx8uyFk2rQ</a> </td>
			<td><b>g17z</b> </td>
		</tr>
		<tr class="a">
			<td>v 1.2 GA </td>
			<td>2020/02/18</td>
			<td> <a href="https://pan.baidu.com/s/1NDeB_g_-6Qbn_bHkTGnFGA" target="_blank">https://pan.baidu.com/s/1NDeB_g_-6Qbn_bHkTGnFGA</a>  </td>
			<td><b>6bda</b> </td>
		</tr>  
	</tbody>
</table>






