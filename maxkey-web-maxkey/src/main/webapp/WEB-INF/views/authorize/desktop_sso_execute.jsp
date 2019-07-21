<!DOCTYPE html>
<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="s"  uri="http://sso.maxkey.org/tags" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Desktop SSO Execute</title>
  <link rel="shortcut icon" type="image/x-icon" href="<s:Base />/images/favicon.ico"/>
  <link type="text/css" rel="stylesheet" href="<s:Base />/css/base.css"/>
</head>


<body>
		<applet 
			class="body" 
			code="com/connsec/desktop/login/DesktopSSOApplet.class"
			archive="<s:BasePath/>/desktopSSOApplet_signed.jar,<s:BasePath/>/json-simple-1.1.1.jar" 
			width="600" 
			height="400">
				<param name="encoderParam" value="${encoderParam}">
			</applet>
</body>
</html>
