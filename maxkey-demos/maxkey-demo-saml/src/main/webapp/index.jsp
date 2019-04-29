<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Service Provider Home(index) Page</title>
</head>
<body>

	<h1>Service Provider Home(index) Page</h1>

	<h3>This page is not secured.</h3>

	<a href="user.jsp">protected user page</a>  <br/>
	<a href="admin.jsp">protected admin page</a> <br/>
	<a href="j_spring_security_logout">End your session with the Service Provider</a> <i>Does not end your session with the IDP</i> <br/>

	<h3>Your current Spring Security Credentials are:</h3>

	<H4>Authentication Principal is: </H4> <p><sec:authentication property="principal"></sec:authentication></p>
	<H4>Authentication Credentials are: </H4><p><sec:authentication property="credentials"></sec:authentication></p>
	<H4>Authentication Details are: </H4><p><sec:authentication property="details"></sec:authentication></p>

</body>
</html>
