<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'register.jsp' starting page</title>
    
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
<div class="container">	
	<table border="0"  style="width:100%;">
		<tr>
			<td width="630px">
				
			</td>
			<td>
				<form action="<s:Base />/registration/register" method="post">
					<table  class="datatable">
						<tr>
							<td><s:Locale code="register.lastname"/></td>
							<td><input type='text' id="lastName" name="lastName" value="" /></td>
						</tr>
						<tr>
							<td><s:Locale code="register.firstname"/></td>
							<td><input type='text' id="firstName" name="firstName" value="" /></td>
						</tr>
						<tr>
							<td><s:Locale code="register.workemail"/></td>
							<td><input type='text' id="workEmail" name="workEmail" value="" /></td>
						</tr>
						<tr>
							<td><s:Locale code="register.company"/></td>
							<td><input type='text' id="company" name="company" value="" /></td>
						</tr>
						<tr>
							<td><s:Locale code="register.workphone"/></td>
							<td><input type='text' id="workPhone" name="workPhone" value="" /></td>
						</tr>
						
						<tr style="display:none">
							<td><s:Locale code="register.users"/></td>
							<td><input type='text' id="users" name="users" value="0" /></td>
						</tr>
						<tr>
							<td  colspan="2"><input id="registerBtn" class="button" type="submit" value="<s:Locale code="register.button.register" />"/></td>
						</tr>
						
					</table>
				</form>
			</td>
		</tr>
	</table>
</div>
  </body>
</html>
