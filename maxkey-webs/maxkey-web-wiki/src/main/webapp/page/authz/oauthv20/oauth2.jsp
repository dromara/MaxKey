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
 	<jsp:param value="authz-oauth20" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
	<h1>OAuth2.0介绍</h1>
    <h5>OAuth2.0（开放授权）</h5>
	<div class="text-section">
		<p>OAuth2.0是一种国际通用的授权方式，是一个开放标准，用户授权后，第三方应用无需获取用户的用户名和密码就可以访问该用户在某一网站上存储的私密的资源（如照片，视频，联系人列表）。</p>
		<p>Access Token：用户身份认证和基本授权的凭证。第三方应用在调用ConnSec开放API之前，首先需要获取Access Token。</p>
		
		<p>ConnSec OAuth2.0支持OAuth2.0标准协议来进行用户身份验证和获取用户授权，相对于之前的OAuth1.0协议，其认证流程更简单和安全。
			因此，请使用OAuth 1.0协议的网站升级为OAuth 2.0协议</p>
		<p>备注：OAuth2.0的应用集成需完成应用集成申请，详见6节OAuth相关内容。</p>
		<h5>Tips：</h5>
		<p>
		如果您想对OAuth开放标准进行扩展阅读，请参看：官方技术说明<a href="http://oauth.net/2/"  title="http://oauth.net/2/" target="_blank" rel="nofollow">OAuth标准（英文） </a> | <a href="http://zh.wikipedia.org/zh/OAuth"  title="http://zh.wikipedia.org/zh/OAuth" target="_blank" rel="nofollow">OAuth维基百科（中文）</a> </p>
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
