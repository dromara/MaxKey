<!DOCTYPE html>
<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="s"  uri="http://www.connsec.com/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  	<title>Form-Based SSO Submit</title>
 	<link type="text/css" rel="stylesheet" href="<s:Base />/css/base.css"/>
  	<link rel="shortcut icon" type="image/x-icon" href="<s:Base />/images/favicon.ico"/>
	<base href="<s:BasePath/>"/>

  	<script type="text/javascript" src="<s:Base/>/jquery/jquery-1.11.2.min.js"></script>
  
  	<script type="text/javascript">
			$(function(){
				window.top.location.href ="https://note.youdao.com/login/acc/login?username=${username}&password=${password}&app=web&product=YNOTE&tp=urstoken&cf=2&fr=1&systemName=&deviceType=&ru=http://note.youdao.com/web/&er=http://note.youdao.com/web/?&systemName=Windows&deviceType=WindowsPC&timestamp=${currentTime}";
			});
		</script>
</head>

<body style="display:none">
	<form class="bd" name="frmLogin" method="get" id="loginForm"  target="_top"
		action="https://note.youdao.com/login/acc/login?username=${username}&password=${password}&app=web&product=YNOTE&tp=urstoken&cf=2&fr=1&systemName=&deviceType=&ru=http://note.youdao.com/web/&er=http://note.youdao.com/web/?&systemName=Windows&deviceType=WindowsPC&timestamp=${currentTime}">
		<table>
			<tr>
				<td colspan="2"><input id="formbasedsubmitbutton" type="submit" value="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
