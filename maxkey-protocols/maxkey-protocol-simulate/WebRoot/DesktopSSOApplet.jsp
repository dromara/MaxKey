<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>ClientSSOApplet</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

	</head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
//request.getRequestDispatcher( "/WEB-INF/Test.jsp").forward(request,response);
%>
	<body>
	<%
		String typeCode="\t\t\t\t\t\t\t\t1046\tse\n";
		String execPath="{execPath:\"tttt\"}";
		String type="BOTH";//SIMULATION,PARAMETER,BOTH
		String encoderParam=(new sun.misc.BASE64Encoder()).encode( execPath.getBytes() );
	%>

		<applet 
			class="body" 
			code="com/connsec/desktop/login/DesktopSSOApplet.class"
			archive="<%=basePath %>desktopSSOApplet_signed.jar" 
			width="600" 
			height="400">
				<PARAM name="encoderParam" value="<%=encoderParam %>">
			</applet>

	</body>
</html>
