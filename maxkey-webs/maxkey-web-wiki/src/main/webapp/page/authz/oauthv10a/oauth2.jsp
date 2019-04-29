<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth2_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-oauth10a" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
	<h1>OAuth1.0介绍</h1>
    <h5>OAuth1.0（开放授权）</h5>
	<div class="text-section">
		<p>OAuth（开放授权）是一个开放标准，允许用户授权第三方网站访问他们存储在另外的服务提供者上的信息，而不需要将用户名和密码提供给第三方网站或分享他们数据的所有内容。OAuth是OpenID的一个补充，但是完全不同的服务。。</p>
		<p>Access Token：用户身份认证和基本授权的凭证。第三方应用在调用ConnSec开放API之前，首先需要获取Access Token。</p>
		
		<p>ConnSec支持OAuth1.0协议，为了保护ConnSec用户的数据，所有第三方网站都需要通过OAuth认证机制来获得用户的授权</p>
		<p>备注：OAuth1.0的应用集成需完成应用集成申请，详见6节OAuth相关内容。</p>
		<h5>Tips：</h5>
		<p>
			如果您想对OAuth开放标准进行扩展阅读，请参看：官方技术说明<a href="http://oauth.net/"  title="http://oauth.net/" target="_blank" rel="nofollow">OAuth标准（英文） </a> | <a href="http://zh.wikipedia.org/zh/OAuth"  title="http://zh.wikipedia.org/zh/OAuth" target="_blank" rel="nofollow">OAuth维基百科（中文）</a> 
		</p>
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
