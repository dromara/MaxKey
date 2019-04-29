<!DOCTYPE HTML>
<%@ page   language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="css" />
		<tiles:insertAttribute name="js" />	 
	</head>
	<body>
		<div id="top">
			<tiles:insertAttribute name="top" />
		</div>
		<div id="content">
			<tiles:insertAttribute name="content" />
		</div>
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>
