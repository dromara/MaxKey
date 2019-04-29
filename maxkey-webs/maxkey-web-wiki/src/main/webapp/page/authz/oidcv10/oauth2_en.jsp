<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth2.jsp?language=zh_CN");	
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>

<body>
<jsp:include page="/common/top.jsp"></jsp:include>
<div id="container">
 <jsp:include page="/common/left.jsp">
 	<jsp:param value="oauth_2" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
	<h1>OAuth2.0 Introduction</h1>
    <h5>OAUTH2.0 (OPEN AUTHENTICATION)</h5>
	<div class="text-section">
		<p>OAuth2.0 is an internally applied authentication approach and an open standard. With the user authentication, the third party application needs no obtaining of the user name and password to access into the private resources (e.g. photos, video and contact list) stored in a certain website by the user. See http://oauth.net/2/ for the official technical specification.</p>
		<p>Access Token: the credential for user identification authentication and basic authentication; the third party application shall first obtain the Access Token prior to invoke ConnSec to open API.</p>
		<p>Notes: the OAuth2.0 application integration requires the completion of the application integration request; see the section 6 OAuth for related content.</p>
	</div>
    <!-- // 一段描述结束 -->
 </div>
 <!-- //content end -->
<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
