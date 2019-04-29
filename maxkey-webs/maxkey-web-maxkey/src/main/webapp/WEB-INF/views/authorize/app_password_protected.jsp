<!DOCTYPE html>
<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="s"  uri="http://www.connsec.com/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  	<title>Second Protected</title>
 	<link type="text/css" rel="stylesheet" href="<s:Base />/css/base.css"/>
  	<link rel="shortcut icon" type="image/x-icon" href="<s:Base />/images/favicon.ico"/>
	<base href="<s:BasePath/>"/>

  	<script type="text/javascript" src="<s:Base/>/jquery/jquery-1.11.2.min.js"></script>

</head>

<body>
	<form id="apppasswodprotectedsubmit" name="apppasswodprotectedsubmit" action="<s:Base/>/authz/protected" method="post">
		<table  style="margin: auto;width:50%">
			<tr>
				<td><s:Locale code="userinfo.appLoginPassword" /></td>
				<td><input type="password" id="password" name="password" value="" /></td>
			</tr>

			<tr style="display:none">
				<td>redirect uri</td>
				<td><input type="text" id="redirect_uri" name="redirect_uri" value="${redirect_uri}" /></td>
			</tr>
			<tr>
				<td colspan="2"><input class="button" type="submit" style="width: 100px" id="apppasswodprotectedsubmitbutton"value="<s:Locale code="button.text.continue" />"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
