<!DOCTYPE html>
<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="s"  uri="http://www.connsec.com/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  	<title>Form-Based SSO Submit</title>
 	<link type="text/css" rel="stylesheet" href="<s:Base />/css/base.css"/>
  	<link rel="shortcut icon" type="image/x-icon" href="<s:Base />/images/favicon.ico"/>
	<base href="<s:BasePath/>"/>

  	<script type="text/javascript" src="<s:Base/>/jquery/jquery-1.11.2.min.js"></script>
  
  	<script type="text/javascript">
	function redirect2link( ){
		window.top.location.href="${loginUrl}";
	};

	setTimeout("redirect2link()", 5000);
</script> 
</head>

<body style="display:none"  >
	<a href="${loginUrl}">click here to login</a>
	<br>
	submitType:${submitType}
	<br>
	<iframe src="<s:Base />/authz/formbased/${id}"></iframe>
</body>
</html>
