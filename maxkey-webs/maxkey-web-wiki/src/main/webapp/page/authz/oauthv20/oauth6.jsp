<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth6_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-oauth20-oauth6" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
	<h1>通过OAuth2.0 token调用API</h1>
	<div class="text-section">
		通过OAuth2.0 token调用API接口方式：
        <ul>
        <li>直接使用参数传递参数名为 access_token <a href="">https://login.connsec.com/sec/oauth/userinfo?access_token=abcd </a></li>
        </ul>
        <br>
        <p>其它接口参数正常传递即可。</p>
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
