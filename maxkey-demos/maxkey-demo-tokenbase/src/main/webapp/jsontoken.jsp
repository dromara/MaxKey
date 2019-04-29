<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="org.maxkey.client.tokenbase.*"%>
<%@ page language="java" import="org.maxkey.client.crypto.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String token =request.getParameter("token");
System.out.println("token : "+token);
String tokenString=TokenUtils.decode(token, "lEWhDLTo", ReciprocalUtils.Algorithm.DES);
Map tokenMap=TokenUtils.parseJsonBasedToken(tokenString);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
   <title>JsonBasedToken Demo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="JsonBasedToken Demo">
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
	  				
	  				<td colspan="2" class="title">JsonBasedToken Demo</td>
	  			</tr>
	  			
	  			<tr>
	  				<td>JsonBasedToken Logo</td>
	  				<td> <img src="<%=basePath %>images/json.png" width="124px" height="124px"/></td>
	  			</tr>
	  			<tr>
	  				<td>UID</td>
	  				<td><%=tokenMap.get("uid") %></td>
	  			</tr>
	  			<tr>
	  				<td>UserName</td>
	  				<td><%=tokenMap.get("username") %></td>
	  			</tr>
	  			<tr>
	  				<td>Department</td>
	  				<td><%=tokenMap.get("department") %></td>
	  			</tr>
	  			<tr>
	  				<td>Email</td>
	  				<td><%=tokenMap.get("email") %></td>
	  			</tr>
	  			<tr>
	  				<td>Authentication at Time</td>
	  				<td><%=tokenMap.get("at")%></td>
	  			</tr>
	  			<tr>
	  				<td>Expires</td>
	  				<td><%=tokenMap.get("expires")%></td>
	  			</tr>
	  		</table>
  		</div>
  </body>
</html>
