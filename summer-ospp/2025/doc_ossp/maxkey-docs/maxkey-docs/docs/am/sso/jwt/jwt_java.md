---
sidebar_position: 2
title: JWT Java客户端集成
---

## JWT Java客户端集成

本文使用JAVA WEB程序为例

### 引入客户端所需包

```java
gson-2.2.4.jar
maxkey-client-sdk.jar
nimbus-jose-jwt-8.10.jar
commons-codec-1.9.jar
commons-io-2.2.jar
commons-logging-1.1.1.jar
```


### 获取令牌和用户信息及验证签名

```java
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="org.maxkey.client.oauth.model.*" %>
<%@ page language="java" import="org.maxkey.client.utils.*" %>
<%@ page language="java" import="com.nimbusds.jwt.JWTClaimsSet" %>
<%@ page language="java" import="com.nimbusds.jose.*" %>
<%@ page language="java" import="com.nimbusds.jwt.*" %>
<%@ page language="java" import="com.connsec.oidc.jose.keystore.*" %>
<%@ page language="java" import="com.nimbusds.jose.jwk.*" %>
<%@ page language="java" import="java.io.File" %>
<%@ page language="java" import="com.nimbusds.jose.crypto.*" %>
<%@ page language="java" import="com.google.gson.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String token=request.getParameter("jwt");
System.out.println("jwt "+token);
SignedJWT signedJWT=null;
File jwksFile=new File(PathUtils.getInstance().getClassPath()+"jwk.jwks");
JWKSet jwkSet=JWKSet.load(jwksFile);
RSASSAVerifier rsaSSAVerifier = new RSASSAVerifier(((RSAKey) jwkSet.getKeyByKeyId("maxkey_rsa")).toRSAPublicKey());
try {
    signedJWT = SignedJWT.parse(token);
} catch (java.text.ParseException e) {
    // Invalid signed JWT encoding
}
System.out.println("signedJWT "+signedJWT);
JWTClaimsSet jwtClaims =signedJWT.getJWTClaimsSet();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
   <title>JWT 1.0 Demo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="JWT 1.0 Demo">
	<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>/images/favicon.ico"/>
	<script type="text/javascript" src="<%=basePath %>/jquery-3.5.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/jsonformatter.js"></script>
	<link   type="text/css" rel="stylesheet"  href="<%=basePath %>/demo.css"/>	
  </head>
  <body>
  		<div class="container">
	  		<table class="datatable">
	  			<tr>
	  				<td colspan="2" class="title">JSON Web Token (JWT) 1.0 Demo</td>
	  			</tr>
	  			<tr>
	  				<td>JWT 1.0 Logo</td>
	  				<td> <img src="<%=basePath %>/images/jwt.png"  width="124px" height="124px"/></td>
	  			</tr>
	  			<tr>
	  				<td>Issuer</td>
	  				<td><%=jwtClaims.getIssuer() %></td>
	  			</tr>
	  			<tr>
	  				<td>Subject</td>
	  				<td><%=jwtClaims.getSubject()%></td>
	  			</tr>
	  			<tr>
	  				<td>Audience</td>
	  				<td><%=jwtClaims.getAudience() %></td>
	  			</tr>
	  			<tr>
	  				<td>ExpirationTime</td>
	  				<td><%=jwtClaims.getExpirationTime() %></td>
	  			</tr>
	  			<tr>
	  				<td>JWTID</td>
	  				<td style="word-wrap: break-word;"><%=jwtClaims.getJWTID() %></td>
	  			</tr>
	  			<tr>
	  				<td>IssueTime</td>
	  				<td style="word-wrap: break-word;"><%=jwtClaims.getIssueTime() %></td>
	  			</tr>
				<tr>
	  				<td>Verify</td>
	  				<td style="word-wrap: break-word;"><%=signedJWT.verify(rsaSSAVerifier) %></td>
	  			</tr>
	  			<tr>
	  				<td>JWTClaims</td>
	  				<td style="word-wrap: break-word;">
						<textarea cols="68" rows="20" v-model="text2"><%=signedJWT.getPayload() %></textarea>
					</td>
	  			</tr>
	  		</table>
  		</div> 
		<script type="text/javascript">
			FormatTextarea();
		</script>
  </body>
</html>
```
