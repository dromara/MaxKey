<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn"     	 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 			uri="http://www.connsec.com/tags" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'activated.jsp' starting page</title>
    
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
  <c:if test="${null != model}">
	<table border="0"  style="width:100%;">
		<tr>
			<td width="630px">
				
			</td>
			<td>
				<form action="<s:Base />/registration/activate/${model.id}" method="post">
					<table  class="datatable">
						<tr>
							<td><s:Locale code="register.lastname"/></td>
							<td>${model.lastName}</td>
						</tr>
						<tr>
							<td><s:Locale code="register.firstname"/></td>
							<td>${model.firstName}</td>
						</tr>
						<tr>
							<td><s:Locale code="register.workemail"/></td>
							<td>${model.workEmail}</td>
						</tr>
						<tr>
							<td><s:Locale code="register.company"/></td>
							<td>${model.company}</td>
						</tr>
						<tr>
							<td><s:Locale code="register.workphone"/></td>
							<td>${model.workPhone}</td>
						</tr>
						<tr>
							<td><s:Locale code="register.password"/></td>
							<td><input type='password' id="password" name="password" value="" /></td>
						</tr>
						<tr>
							<td><s:Locale code="register.confirmpassword"/></td>
							<td><input type='password' id="confirmpassword" name="confirmpassword" value="" /></td>
						</tr>
						<tr style="display:none">
							<td><s:Locale code="register.users"/></td>
							<td><input type='text' id="users" name="users" value="0" /></td>
						</tr>
						<tr>
							<td  colspan="2"><input id="registerBtn" class="button" type="submit" value="<s:Locale code="button.text.enable" />"/></td>
						</tr>
						
					</table>
				</form>
			</td>
		</tr>
	</table>
	  </c:if>
    <c:if test="${null == model}">
    	url expired.
    </c:if>  
</div>
  </body>
</html>
