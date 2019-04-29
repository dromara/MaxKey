<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="org.maxkey.client.oauth.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.api.ConnsecApi10a" %>
<%@ page language="java" import="org.maxkey.client.oauth.model.Token" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String callback="http://oauth.demo.connsec.com:8080/oauthdemo/callback.jsp";
OAuthService service = new ServiceBuilder()
                            .provider(ConnsecApi10a.class)
                            .apiKey("a08d486a-2007-4436-aeda-4310e9443ec7")
                            .apiSecret("k3I2MTQxMjIwMTQxMDMxNTM4NzQW27")
                            .callback(callback)
                            .build();
                            
Token requestToken = service.getRequestToken();

request.getSession().setAttribute("requestToken", requestToken);

request.getSession().setAttribute("oauthv10aservice", service);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <a href="<%=service.getAuthorizationUrl(requestToken) %>&approval_prompt=auto">oauth sso</a>
  </body>
</html>
