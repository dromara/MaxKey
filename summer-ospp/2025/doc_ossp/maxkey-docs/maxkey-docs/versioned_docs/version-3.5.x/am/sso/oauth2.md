---
sidebar_position: 1
---

# OAuth2应用集成
本文介绍OAuth2应用如何与MaxKey进行集成。

## 认证流程
采用Authorization Code获取Access Token的授权验证流程又被称为Web Server Flow，适用于所有Server端的应用。其调用流程示意图如下：
![sso_oauth](/images/sso/sso_oauth.png)


对于应用而言，其流程由获取Authorization Code和通过Authorization Code获取Access Token这2步组成。

1.引导需要授权的用户到如下地址：

```   
http://sso.maxkey.top/sign/authz/oauth/v20/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI 
```   

2.页面跳转至 

```   
YOUR_REGISTERED_REDIRECT_URI/?code=CODE
```   

3.换取Access Token

```   
http://sso.maxkey.top/sign/authz/oauth/v20/token?client_id=YOUR_CLIENT_ID&client_secret=YOUR _SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE
```   

返回值

```     
{ "access_token":"SlAV32hkKG", "remind_in ":3600, "expires_in":3600 }
```

## 应用注册
应用在MaxKey管理系统进行注册，注册的配置信息如下

![sso_oauth_conf](/images/sso/sso_oauth_conf.png)


## API接口标准
   
 <table border="0" class="table table-striped table-bordered ">
		 <tr>
			<th> <strong>接口 </strong> </th>
			<th> <strong>说明 </strong> </th>
			<th> <strong>详细说明 </strong> </th>
			<th> <strong>调用方法 </strong> </th>
		  </tr>
		  <tr>
			<td> /authz/oauth/v20/authorize </td>
			<td> 请求用户授权Token </td>
			<td> http://sso.maxkey.top/sign接收app sso认证请求,<br/>client_id为需要认证的应用的id;</td>
			<td> APP </td>
		  </tr>
		  <tr>
			<td> /authz/oauth/v20/token </td>
			<td> 获取授权过的 Access Token </td>
			<td> 后台应用获取 tokencode ，调用接口进行 tokencode 校验；<br/>校验成功获取访问 token </td>
			<td> APP </td>
		  </tr>
		  <tr>
			<td> /api/oauth/v20/me </td>
			<td> 授权用户信息查询接口 </td>
			<td> 通过访问 token 获取登录用户信息 </td>
			<td> APP </td>
		  </tr>		  
 </table>
 

### 授权接口

/authz/oauth/v20/authorize

请求用户授权Token

<table border="0" class="table table-striped table-bordered ">
   <tr>
	<th> 接口名称 </th>
	<th> 请求用户授权Token </th>
  </tr>
  <tr>
	<td> url </td>
	<td> http://sso.maxkey.top/sign/authz/oauth/v20/authorize</td>
  </tr>
  <tr>
	<td> 请求方式 </td>
	<td> http get/post </td>
  </tr>
 </table>
 	 
<h5>请求参数</h5>

 <table border="0" class="table table-striped table-bordered ">
   <tr>
	<th>参数 </th>
	<th> 说明 </th>
  </tr>
  <tr>
	<td> client_id </td>
	<td> 注册应用时分配的client_id。 </td>
  </tr>

   <tr>
	<td> redirect_uri </td>
	<td>应用回调地址，注册时需要配置</td>
  </tr>
  <tr>
	<td>grant_type</td>
	<td>授权类型。</td>
  </tr>
   <tr>
	<td>etc param</td>
	<td>其他参数。</td>
  </tr>
  
  <tr>
		<td colspan="2" align="left">
		响应返回app应用程序，包含请求参数如下：
		</td>
  </tr>
  <tr>
		<td colspan="2" align="left">
		http://app.maxkey.org/app/callback?tokencode =PQ7q7W91a-oMsCeLvIaQm6bTrgtp7
		</td>
  
  </tr>
  <tr>
		<td>tokencode</td>
		<td>用于调用/authz/oauth/token，接口获取授权后的访问token。</td>
  </tr>
 </table> 	

### 令牌接口
/authz/oauth/v20/token

通过/authz/oauth/v20/token用tokencode换取访问token

 <table border="0" class="table table-striped table-bordered ">
   <tr>
	<th> 接口名称 </th>
	<th> token 接口 </th>
  </tr>
  <tr>
	<td> url </td>
	<td> http://sso.maxkey.top/sign/authz/oauth/v20/token </td>
  </tr>
  <tr>
	<td> 请求方式 </td>
	<td> http get/post </td>
  </tr>
 </table>
 	 
 <h5>请求参数</h5>
 <table border="0" class="table table-striped table-bordered ">
   <tr>
	<th>参数 </th>
	<th> 说明 </th>
  </tr>
  <tr>
	<td> client_id </td>
	<td> 注册应用时分配的client_id。 </td>
  </tr>
  <tr>
	<td> client_secret </td>
	<td> 注册应用时分配的client_secret</td>
  </tr>
   <tr>
	<td> redirect_uri </td>
	<td>应用回调地址，注册时需要配置</td>
  </tr>
  <tr>
	<td>tokencode</td>
	<td>调用authz/oauth/v20/authorize获得的tokencode值。</td>
  </tr>
  <tr>
	<td>grant_type</td>
	<td>授权类型。Grant type</td>
  </tr>
  <tr>
	<td>username</td>
	<td>当grant_type=password时，此参数表示直接认证用户名。</td>
  </tr>
  <tr>
	<td>password</td>
	<td>当grant_type=password时，此参数表示直接认证用户密码。</td>
  </tr>
  <tr>
	<td>etc param</td>
	<td>其他参数</td>
  </tr>
  <tr align="left">
	<td colspan="2" align="left">
实际请求如下：

```http
The actual request might look like:
POST /authz/oauth/v20/token token HTTP/1.1
Host: sso.maxkey.org/openapi
Content-Type: application/x-www-form-urlencoded
tokencode= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7&
client_id=QPKKKSADFUP876&
client_secret=client_secret&
redirect_uri=http://app.maxkey.org/app/callback
```
</td>
  </tr>
  <tr>
		<td colspan="2" align="left">
		返回数据
		</td>
  </tr>
  <tr>
		<td colspan="2" align="left">
		A successful response to this request contains the following fields:
		</td>
  
  </tr>
  <tr>
		<td>access_token</td>
		<td>用该token能调用SSO的API</td>
  </tr>
  <tr>
		<td colspan="2">
		成功返回JSON数据，如下：

```json
{ 
	"access_token":"token_id",
	"id_token":"id_token"
}
```
</td>
  </tr>
 </table> 	

### 用户属性接口
/api/oauth/v20/me

<table  border="0" class="table table-striped table-bordered ">
 	   <tr>
	    <th> 接口名称 </th>
	    <th> token 接口 </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td>http://sso.maxkey.top/sign/api/oauth/v20/me</td>
	  </tr>
	  <tr>
	    <td> 请求方式 </td>
	    <td> http get/post </td>
	  </tr>
</table>
 	 
<h5>请求参数</h5>

<table  border="0" class="table table-striped table-bordered ">
 	   <tr>
	    <th>参数 </th>
	    <th> 说明 </th>
  	  </tr>
	  <tr>
	    <td> access_token </td>
	    <td> 调用sso/ token获得的token值。 </td>
	  </tr>
	  <tr align="left">
	  	<td colspan="2" align="left">
	  				实际请求如下：

```http
POST /oauth/ userinfo HTTP/1.1
Host: sso.maxkey.org/openapi
Content-Type: application/x-www-form-urlencoded
access_token= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7
```

</td>
	  </tr>
	  <tr>
	  		<td colspan="2" align="left">
	  		返回数据/ response data
	  		</td>
	  </tr>
	  <tr>
	  		<td colspan="2">
	  		<p>成功返回JSON数据，如下：</p>

```json
{
	"userid":"zhangs"
}
```

<br/>
zhangs是认证的用户ID
	  		</td>
	  </tr>
 	 </table> 	



OAuth认证接口属性列表

<table   border="0" class="table table-striped table-bordered ">
   <tr >
	<th> 属性名(Attribute) </th>
	<th> 描述 </th>
	<th>数据类型</th>
  </tr>
  <tr>
	<td>uid</td>
	<td>uid</td>
	<td>字符串</td>
  </tr>
 </table> 	



## OAuth2.0 错误码

MaxKey OAuth2.0实现中，授权服务器在接收到验证授权请求时，会按照OAuth2.0协议对本请求的请求头部.请求参数进行检验，若请求不合法或验证未通过，授权服务器会返回相应的错误信息，包含以下几个参数：

error: 错误码

error_description: 错误的描述信息



错误信息的返回方式有两种：

当请求授权Endpoint：http://sso.maxkey.top/sign/authz/oauth/v20/authorize 时出现错误，返回方式是：跳转到redirect_uri,并在uri 的query parameter中附带错误的描述信息。

当请求access token endpoint:http://sso.maxkey.top/sign/authz/oauth/v20/token 时出现错误，返回方式：返回JSON文本。

例如：
```json
{
	"error":"unsupported_response_type",
	"error_description":"不支持的 ResponseType."
}
```

OAuth2.0错误响应中的错误码定义如下表所示：

 <table  border="0" class="table table-striped table-bordered ">
	<thead>
	  <th>编号</th><th>错误码(error)</th><th>描述(error_description)</th>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td>empty_client_id</td>
			<td>参数client_id为空</td>
		</tr>
		<tr>
			<td>2</td>
			<td>empty_client_secret</td>
			<td>参数client_secret为空</td>
		</tr>
		 <tr>
			<td>3</td>
			<td>empty_redirect_uri</td>
			<td>参数redirect_uri为空</td>
		</tr>
		 <tr>
			<td>4</td>
			<td>empty_response_type</td>
			<td>参数response_type为空</td>
		</tr>
		 <tr>
			<td>5</td>
			<td>empty_code</td>
			<td>code为空</td>
		</tr>
		 <tr>
			<td>6</td>
			<td>app_unsupport_sso</td>
			<td>应用不支持sso登录</td>
		</tr>
		 <tr>
			<td>7</td>
			<td>app_unsupport_oauth</td>
			<td>应用不支持OAuth认证</td>
		</tr>
		 <tr>
			<td>8</td>
			<td>invalid_client_id</td>
			<td>非法的client_id</td>
		</tr>
		 <tr>
			<td>9</td>
			<td>invalid_response_type</td>
			<td>非法的response_type</td>
		</tr>
		 <tr>
			<td>10</td>
			<td>invalid_scope</td>
			<td>非法的scope</td>
		</tr>
		<tr>
			<td>11</td>
			<td>invalid_grant_type</td>
			<td>非法的grant_type</td>
		</tr>
		<tr>
			<td>12</td>
			<td>redirect_uri_mismatch</td>
			<td>非法的redirect_uri</td>
		</tr>
		<tr>
			<td>13</td>
			<td>unsupported_response_type</td>
			<td>不支持传递的response_type</td>
		</tr>
		
		<tr>
			<td>14</td>
			<td>invalid_code</td>
			<td>非法的code</td>
		</tr>
		<tr>
			<td>15</td>
			<td>unsupported_refresh_token</td>
			<td>不支持refresh_token的方式</td>
		</tr>
		<tr>
			<td>16</td>
			<td>access_token_exprise</td>
			<td>access_token过期</td>
		</tr>
		<tr>
			<td>17</td>
			<td>invalid_access_token</td>
			<td>非法的access_token</td>
		</tr>
		<tr>
			<td>18</td>
			<td>invalid_refresh_token</td>
			<td>非法的refresh_token</td>
		</tr>
		<tr>
			<td>19</td>
			<td>refresh_token_exprise</td>
			<td>refresh_token过期</td>
		</tr>
</tbody>
 </table>
 

## OAuth2客户端集成


本文使用JAVA WEB程序为例

### 引入依赖包

```java
gson-2.2.4.jar
maxkey-client-sdk.jar
nimbus-jose-jwt-8.10.jar
commons-codec-1.9.jar
commons-io-2.2.jar
commons-logging-1.1.1.jar
```

### 认证授权

```java
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="org.maxkey.client.oauth.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.api.MaxkeyApi20" %>
<%@ page language="java" import="org.maxkey.client.oauth.model.Token" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";

String callback="http://oauth.demo.maxkey.top:8080/demo-oauth/oauth20callback.jsp";
OAuthService service = new ServiceBuilder()
     .provider(MaxkeyApi20.class)
     .apiKey("b32834accb544ea7a9a09dcae4a36403")
     .apiSecret("E9UO53P3JH52aQAcnLP2FlLv8olKIB7u")
     .callback(callback)
     .build();
Token EMPTY_TOKEN = null;
String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

request.getSession().setAttribute("oauthv20service", service);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>OAuth 2.0 SSO</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
    <a href="<%=authorizationUrl%>&approval_prompt=auto">oauth 2.0 sso</a>
  </body>
</html>
```

### 获取令牌及用户信息

```java
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="org.maxkey.client.oauth.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.api.MaxkeyApi20" %>
<%@ page language="java" import="org.maxkey.client.oauth.model.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.domain.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

OAuthService service = (OAuthService)request.getSession().getAttribute("oauthv20service");
if(service == null){
	String callback="http://oauth.demo.maxkey.top:8080/demo-oauth/oauth20callback.jsp";
	service = new ServiceBuilder()
     .provider(MaxkeyApi20.class)
     .apiKey("b32834accb544ea7a9a09dcae4a36403")
     .apiSecret("E9UO53P3JH52aQAcnLP2FlLv8olKIB7u")
     .callback(callback)
     .build();
}

Token EMPTY_TOKEN = null;
Verifier verifier = new Verifier(request.getParameter("code"));
Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
 
OAuthClient restClient=new OAuthClient("http://sso.maxkey.top/sign/api/oauth/v20/me");
UserInfo userInfo=restClient.getUserInfo(accessToken.getAccess_token());

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
   <title>OAuth V2.0 Demo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="OAuth V2.0 Demo">
	<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>/images/favicon.ico"/>
	<script type="text/javascript" src="<%=basePath %>/jquery-3.5.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/jsonformatter.js"></script>
	<link   type="text/css" rel="stylesheet"  href="<%=basePath %>/demo.css"/>
  </head>
  <body>
  		<div class="container">
	  		<table class="datatable">
	  			<tr>
	  				<td colspan="2" class="title">OAuth V2.0 Demo</td>
	  			</tr>
	  			<tr>
	  				<td width="50%">OAuth V2.0 Logo</td>
	  				<td width="50%"> <img src="<%=basePath %>/images/oauth-2-sm.png"  width="124px" height="124px"/></td>
	  			</tr>
	  			<tr>
	  				<td>Login</td>
	  				<td><%=userInfo.getUsername() %></td>
	  			</tr>
	  			<tr>
	  				<td>DisplayName</td>
	  				<td><%=userInfo.getDisplayName() %></td>
	  			</tr>
	  			<tr>
	  				<td>Department</td>
	  				<td><%=userInfo.getDepartment() %></td>
	  			</tr>
	  			<tr>
	  				<td>JobTitle</td>
	  				<td><%=userInfo.getJobTitle() %></td>
	  			</tr>
	  			<tr>
	  				<td>email</td>
	  				<td><%=userInfo.getEmail() %></td>
	  			</tr> 
	  			<tr>
	  				<td>ResponseString</td>
	  				<td  style="word-wrap: break-word;">
						<textarea cols="68" rows="20" v-model="text2"><%=userInfo.getResponseString() %></textarea>
					</td>
	  			</tr>
	  		</table>
			<script type="text/javascript">
				FormatTextarea();
			</script>
  		</div>
  </body>
</html>
```

## OAuth2 PASSWORD模式

本文使用JAVA 程序为例

```java
package org.maxkey.client.oauth.test;

import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.builder.api.MaxkeyPasswordApi20;
import org.maxkey.client.oauth.model.OAuthConfig;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.oauth.oauth.OAuthPasswordService;

public class MaxkeyPasswordDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String accessTokenUrl="http://sso.maxkey.top/sign/authz/oauth/v20/token";
		String clientId = "b32834accb544ea7a9a09dcae4a36403";
		String clientSerect = "E9UO53P3JH52aQAcnLP2FlLv8olKIB7u";
		
		String callback = "http://oauth.demo.maxkey.top:8080/demo-oauth/oauth20callback.jsp";
		String responseType ="token";
		String approvalprompt = "auto";
		
		OAuthConfig oauthServiceConfig=new OAuthConfig(clientId,clientSerect,callback);
		MaxkeyPasswordApi20	passwordApi20=new MaxkeyPasswordApi20(accessTokenUrl);
		OAuthPasswordService oAuthPasswordService=new OAuthPasswordService(oauthServiceConfig,passwordApi20);
		Token accessToken = null;
		Response response = null;
		accessToken = oAuthPasswordService.getAccessToken("admin", "maxkey"); 
	}
}
```


### 详细见请参考

https://github.com/MaxKeyTop/MaxKey-Client-sdk/blob/master/src/test/java/org/maxkey/client/oauth/test/MaxkeyPasswordDemo.java