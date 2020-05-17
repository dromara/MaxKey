<!DOCTYPE html>
<html>
<head>
  <#include  "formbased_common.ftl">
  <title>Desktop SSO Execute</title>
</head>


<body>
		<applet 
			class="body" 
			code="com/connsec/desktop/login/DesktopSSOApplet.class"
			archive="<@basePath/>/desktopSSOApplet_signed.jar,<s:BasePath/>/json-simple-1.1.1.jar" 
			width="600" 
			height="400">
				<param name="encoderParam" value="${encoderParam}">
			</applet>
</body>
</html>
