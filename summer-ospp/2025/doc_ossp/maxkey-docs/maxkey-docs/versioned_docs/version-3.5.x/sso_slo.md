---
title: 单点注销
sidebar_position: 9
---
## 单点注销
	
单点注销(Single Logout)是指用户在一个系统退出后，其所能单点登录访问的所有系统都同时退出。单点注销主要是为提高安全性，避免用户忘记退出所有应用而造成信息的泄密。
    	
    	
IDP支持单点注销(SLO),即用户不仅仅从认证中心注销，同时也注销从认证中心访问的应用系统。	
		
		
其实现方式也非常简单，由于SSO和单点登录的应用都是分开的，使用不同的域名，只是通过认证中心在多个应用系统中传递身份和登录系统。因此，首先注销单点登录应用，然后修改每个应用系统都使用SSO的单点注销页面，SSO的退出页面会将用户登录的Session注销掉。
		
## 单点注销过程
		
1,应用系统先完成本系统注销，注销完成后调用认证中心的单点注销地址。
		
		
2,修改应用单点注销完成后的地址为https://sso.maxkey.top/maxkey/force/logout,参数reLoginUrl为注销完成后访问地址。
		


## 单点注销机制
MaxKey在登录完成后，会生成在线令牌，该令牌存储在Cookie和服务器中，当单点登录是会向应用传递在线令牌，应用通过判断令牌的状态检查当前用户是否在线，如果令牌失效，则应用自动注销，达到单点注销的功能。


### IDP主动注销
MaxKey注销时向SP发送注销请求，请求包含注销的令牌，SP获取注销的令牌，通知客户端进行注销

基于CAS的单点登录，在单点登录时SP记住MaxKey的ticket，当MaxKey单点注销时向SP发送参数为logoutRequest，请求内容入下

```xml
<samlp:LogoutRequest xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol" ID="%s" Version="2.0" IssueInstant="%s">
	<saml:NameID xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">%s</saml:NameID>
	<samlp:SessionIndex>%s</samlp:SessionIndex>
</samlp:LogoutRequest>
```

基于MaxKey在线token注销机制，在单点登录时MaxKey会把在线token发送给SP，SP需要存储该令牌，当MaxKey单点注销时向SP发送请求
<table border="0" class="table table-striped table-bordered ">
	<thead>
		<tr>
			<th>序号</th><th>参数</th><th>备注</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td><td>request</td><td>logoutRequest</td>
		</tr>
		<tr>
			<td>2</td><td>id</td><td>随机id</td>
		</tr>
		<tr>
			<td>3</td><td>principal</td><td>登录用户名</td>
		</tr>
		<tr>
			<td>4</td><td>issueInstant</td><td>登出时间</td>
		</tr>
		<tr>
			<td>5</td><td>ticket</td><td>当前在线ticket</td>
		</tr>
	</tbody>
</table>


### SP接口注销
SP向MaxKey的接口定时发送请求，检查令牌的有效性，如果令牌失效，则应用退出登录，验证在线token地址sign/onlineticket/validate,参数为ticket为令牌id

### Cookie有效性注销
登录完成后在线令牌存储在.maxkey.top的域名下，Cookie名称为online_ticket，SP应用和MaxKey使用**子域名.maxkey.top**，应用根据令牌有效性判断是否注销。


### 关闭浏览器注销
MaxKey注销，然后在注销的界面使用javascript关闭浏览器。


## 实现机制比较
<table border="0" class="table table-striped table-bordered ">
<thead>
	<th >序号</th><th>机制</th><th>适应场景</th>
</thead>
<tbody>
	<tr>
		<td>1</td>
		<td>IDP主动注销</td>
		<td>SP实现接口用于MaxKey调用</td>
	</tr>
	<tr>
		<td>2</td>
		<td>SP接口注销</td>
		<td>对MaxKey请求频繁有一定压力</td>
	</tr>
	<tr>
		<td>3</td>
		<td>Cookie有效性注销</td>
		<td>同域，实现简单</td>
	</tr>
	<tr>
		<td>4</td>
		<td>注销MaxKey并关闭浏览器</td>
		<td>关闭整个浏览器</td>
	</tr>
</tbody>
</table>
    