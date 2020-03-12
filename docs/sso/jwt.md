<h2>JWT应用集成</h2>
本文介绍JWT应用如何与MaxKey进行集成。

<h2>应用注册</h2>

应用在MaxKey管理系统进行注册，注册的配置信息如下

<img src="{{ "/images/sso/sso_jwt_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>JWT客户端集成</h2>

本文使用JAVA WEB程序为例

jar包依赖如下

commons-codec-1.9.jar

commons-io-2.2.jar

commons-logging-1.1.1.jar

gson-2.2.4.jar

json-smart-2.3.jar

log4j-1.2.17.jar

maxkey-client-sdk.jar

nimbus-jose-jwt-8.8.jar

jcip-annotations-1.0.jar

asm-1.0.2.jar


JSP实现Code

<pre><code class="jsp hljs"> 
&lt;%@ page language="java" import="java.util.*" pageEncoding="utf-8"%&gt;
&lt;%@ page language="java" import="org.maxkey.client.oauth.oauth.*" %&gt;
&lt;%@ page language="java" import="org.maxkey.client.oauth.builder.*" %&gt;
&lt;%@ page language="java" import="org.maxkey.client.oauth.builder.api.ConnsecApi20" %&gt;
&lt;%@ page language="java" import="org.maxkey.client.oauth.model.*" %&gt;
&lt;%@ page language="java" import="org.maxkey.client.utils.*" %&gt;
&lt;%@ page language="java" import="com.nimbusds.jwt.JWTClaimsSet" %&gt;
&lt;%@ page language="java" import="com.nimbusds.jose.*" %&gt;
&lt;%@ page language="java" import="com.nimbusds.jwt.*" %&gt;
&lt;%@ page language="java" import="com.connsec.oidc.jose.keystore.*" %&gt;
&lt;%@ page language="java" import="com.nimbusds.jose.jwk.*" %&gt;
&lt;%@ page language="java" import="java.io.File" %&gt;
&lt;%@ page language="java" import="com.nimbusds.jose.crypto.*" %&gt;
&lt;%@ page language="java" import="com.google.gson.*" %&gt;



&lt;%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String token=request.getParameter("jwt");
System.out.println("jwt "+token);
SignedJWT signedJWT=null;

//JWKSetKeyStore jwkSetKeyStore=new JWKSetKeyStore();

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
 
%&gt;

&lt;!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"&gt;
&lt;html&gt;
  &lt;head&gt;
    &lt;base href="&lt;%=basePath%&gt;"&gt;
    
   &lt;title&gt;JWT 1.0 Demo&lt;/title&gt;
	&lt;meta http-equiv="pragma" content="no-cache"&gt;
	&lt;meta http-equiv="cache-control" content="no-cache"&gt;
	&lt;meta http-equiv="expires" content="0"&gt;    
	&lt;meta http-equiv="keywords" content="keyword1,keyword2,keyword3"&gt;
	&lt;meta http-equiv="description" content="JWT 1.0 Demo"&gt;
	&lt;link rel="shortcut icon" type="image/x-icon" href="&lt;%=basePath %&gt;/images/favicon.ico"/&gt;
	
	&lt;style type="text/css"&gt;
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
	&lt;/style&gt;
  &lt;/head&gt;
  
  &lt;body&gt;
  		&lt;div class="container"&gt;
	  		&lt;table class="datatable"&gt;
	  			&lt;tr&gt;
	  				
	  				&lt;td colspan="2" class="title"&gt;JSON Web Token (JWT) 1.0 Demo&lt;/td&gt;
	  			&lt;/tr&gt;
	  			
	  			&lt;tr&gt;
	  				&lt;td&gt;JWT 1.0 Logo&lt;/td&gt;
	  				&lt;td&gt; &lt;img src="&lt;%=basePath %&gt;/images/jwt.png"  width="124px" height="124px"/&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Issuer&lt;/td&gt;
	  				&lt;td&gt;&lt;%=jwtClaims.getIssuer() %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Subject&lt;/td&gt;
	  				&lt;td&gt;&lt;%=jwtClaims.getSubject()%&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Audience&lt;/td&gt;
	  				&lt;td&gt;&lt;%=jwtClaims.getAudience() %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			
	  			&lt;tr&gt;
	  				&lt;td&gt;ExpirationTime&lt;/td&gt;
	  				&lt;td&gt;&lt;%=jwtClaims.getExpirationTime() %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;JWTID&lt;/td&gt;
	  				&lt;td style="word-wrap: break-word;"&gt;&lt;%=jwtClaims.getJWTID() %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;IssueTime&lt;/td&gt;
	  				&lt;td style="word-wrap: break-word;"&gt;&lt;%=jwtClaims.getIssueTime() %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;JWTClaims&lt;/td&gt;
	  				&lt;td style="word-wrap: break-word;"&gt;&lt;%=signedJWT.getPayload() %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Verify&lt;/td&gt;
	  				&lt;td style="word-wrap: break-word;"&gt;&lt;%=signedJWT.verify(rsaSSAVerifier) %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  		&lt;/table&gt;
  		&lt;/div&gt; 
  &lt;/body&gt;
&lt;/html&gt;
</code></pre>

