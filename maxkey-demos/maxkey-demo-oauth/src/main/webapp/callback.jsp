<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="org.maxkey.client.oauth.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.builder.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.model.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.*" %>
<%@ page language="java" import="org.maxkey.client.oauth.domain.*" %>


<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

OAuthService service = (OAuthService)request.getSession().getAttribute("oauthv10aservice");
Verifier verifier = new Verifier(request.getParameter("oauth_verifier"));
Token requestToken = (Token)request.getSession().getAttribute("requestToken");
Token accessToken = service.getAccessToken(requestToken, verifier);


System.out.println("================================");
OAuthClient restClient=new OAuthClient("http://login.connsec.com/maxkey/api/oauth/v10a/me");
 
 UserInfo userInfo=restClient.getUserInfo(accessToken.getAccess_token());
	    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
   <title>OAuth V1.0a Demo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="OAuth V1.0a Demo">
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
	  				
	  				<td colspan="2" class="title">OAuth V1.0a Demo</td>
	  			</tr>
	  			
	  			<tr>
	  				<td>OAuth V1.0a Logo</td>
	  				<td> <img src="<%=basePath %>/images/oauth10.jpg" width="124px" height="124px"/></td>
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
	  				<td><%=userInfo.getResponseString() %></td>
	  			</tr>
	  		</table>
  		</div>
  </body>
</html>
