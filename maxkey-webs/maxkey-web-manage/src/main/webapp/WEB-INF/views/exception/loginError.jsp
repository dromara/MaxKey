<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>Login Error Page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function autoChangeTime() {
			var time = document.getElementById("time").childNodes[0].nodeValue;
			var delay = time - 1;
			if(delay >0) {
				document.getElementById("time").innerHTML = delay;
			} else {
				document.location.href = "${pageContext.request.contextPath}/login";
				return;
			}
	    }
		function init() {
			window.setInterval('autoChangeTime()','1000');
		}
	</script>
  </head>
  
  <body onload="init()">
  	<div>
	      登录失败,失败原因 : ${errorMsg}<br/>
	     系统将在<span id="time" style="color: red;">5</span>秒后,自动跳转到登录页面,您也可以通过点击<a href="${pageContext.request.contextPath}/login">登录</a>手动跳转
  	</div>
  </body>
</html>
