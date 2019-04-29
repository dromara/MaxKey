<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth6.jsp?language=zh_CN");	
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
 	<jsp:param value="oauth_6" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
	<h1>Invoke API through OAuth2.0 token</h1>
	<div class="text-section">
		The approach of invoking API interface through OAuth2.0 token:
        <ul>
        <li>Directly use parameter to deliver parameter name access_token <a href="">https://login.connsec.com/sec/oauth/userinfo?access_token=abcd </a></li>
        </ul>
        <br>
        <p>The other interface parameter shall deliver as normal.</p>
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
