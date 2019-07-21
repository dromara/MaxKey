<!DOCTYPE html>
<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="s"  uri="http://sso.maxkey.org/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Token-Based SSO Submit</title>
  <link rel="shortcut icon" type="image/x-icon" href="<s:Base />/images/favicon.ico"/>
  <link type="text/css" rel="stylesheet" href="<s:Base />/css/base.css"/>
</head>

<body  onload="document.forms[0].submit()"  style="display:none">
<form id="tokenbasedsubmit" name="tokenbasedsubmit" action="${action}" method="post">
		<table style="width:100%">
			<tr>
				<td>token</td>
				<td><input type="text" id="tokenbased_token" name="jwt" value="${token}" /></td>
			</tr>
			
			<tr>
				<td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
			</tr>
		</table>
	</form>
</body>
</html>
