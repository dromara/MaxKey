<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="org.maxkey.client.oauth.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.api.ConnsecApi20" %>
<%@ page language="java" import="org.maxkey.client.oauth.model.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.domain.*" %>
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

OAuthService service = (OAuthService)request.getSession().getAttribute("oauthv20service");

if(service==null){
	String callback="http://oauthdemo.maxkey.org:8080/oauthdemo/oidc10callback.jsp";
	service = new ServiceBuilder()
     .provider(ConnsecApi20.class)
     .apiKey("ae20330a-ef0b-4dad-9f10-d5e3485ca2ad")
     .apiSecret("JQygMDgwMTIwMTUyMjU5NTgyNTc2RD")
     .callback(callback)
     .build();
}

Token EMPTY_TOKEN = null;
Verifier verifier = new Verifier(request.getParameter("code"));
Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);

//JWTClaimsSet idClaims = JWTClaimsSet.parse(accessToken.getId_token());
SignedJWT signedJWT=null;

//JWKSetKeyStore jwkSetKeyStore=new JWKSetKeyStore();

File jwksFile=new File(PathUtils.getInstance().getClassPath()+"jwk.jwks");
JWKSet jwkSet=JWKSet.load(jwksFile);

RSASSAVerifier rsaSSAVerifier = new RSASSAVerifier(((RSAKey) jwkSet.getKeyByKeyId("connsec_rsa")).toRSAPublicKey());
try {
    signedJWT = SignedJWT.parse(accessToken.getId_token());
} catch (java.text.ParseException e) {
    // Invalid signed JWT encoding
}
;

OAuthClient restClient=new OAuthClient("http://login.connsec.com/maxkey/api/connect/v10/userinfo",accessToken.getToken());
 
 OIDCUserInfo userInfo=restClient.getOIDCUserInfo(accessToken.getToken());
 
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
   <title>OpenID Connect 1.0 Demo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="OpenID Connect 1.0 Demo">
	<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>/images/favicon.ico"/>
	
	<style type="text/css">
		body{
			margin: 0;
			margin-top: 0px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 0 0 0px;
			font-size: 12px;
			text-align:center;
			float:center;
			font-family: "Arial", "Helvetica", "Verdana", "sans-serif";
		}
		.container {
			width: 990px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 10px
		}
		table.datatable {
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			width: 100%;
			table-layout:fixed;
		}
		
		table.datatable th{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		
		table.datatable td{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		table.datatable td.title{
			text-align: center;
			font-size: 20px;
			font-weight: bold;
		}
	</style>
  </head>
  
  <body>
  		<div class="container">
	  		<table class="datatable">
	  			<tr>
	  				
	  				<td colspan="2" class="title">OpenID Connect 1.0 Demo</td>
	  			</tr>
	  			
	  			<tr>
	  				<td>OpenID Connect 1.0 Logo</td>
	  				<td> <img src="<%=basePath %>/images/openid.png"  width="124px" height="124px"/></td>
	  			</tr>
	  			<tr>
	  				<td>Login</td>
	  				<td><%=userInfo.getSub() %></td>
	  			</tr>
	  			<tr>
	  				<td>DisplayName</td>
	  				<td><%=userInfo.getName()%></td>
	  			</tr>
	  			<tr>
	  				<td>Department</td>
	  				<td><%=userInfo.getGender() %></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>email</td>
	  				<td><%=userInfo.getEmail() %></td>
	  			</tr>
	  			<tr>
	  				<td>ResponseString</td>
	  				<td style="word-wrap: break-word;"><%=userInfo.getResponseString() %></td>
	  			</tr>
	  			<tr>
	  				<td>Id_token</td>
	  				<td style="word-wrap: break-word;"><%=accessToken.getId_token() %></td>
	  			</tr>
	  			<tr>
	  				<td>Issuer</td>
	  				<td style="word-wrap: break-word;"><%=signedJWT.getJWTClaimsSet().getIssuer() %></td>
	  			</tr>
	  			<tr>
	  				<td>JWTClaims</td>
	  				<td style="word-wrap: break-word;"><%=signedJWT.getPayload() %></td>
	  			</tr>
	  			<tr>
	  				<td>Verify</td>
	  				<td style="word-wrap: break-word;"><%=signedJWT.verify(rsaSSAVerifier) %></td>
	  			</tr>
	  		</table>
  		</div> 
  </body>
</html>
