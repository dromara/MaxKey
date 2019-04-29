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
 	<jsp:param value="authz-oidc10" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
	<h1>OpenID Connect 1.0介绍</h1>
    <h5>OpenID Connect 1.0（开放身份连接）</h5>
	<div class="text-section">
		<p>
			OpenID Connect是一个基于OAuth 2.0授权协议的简单身份层,一套的轻量级规范，提供通过RESTful APIs进行身份交互的框架，在技术上，OpenID Connect定义基于HTTP的REST API,使用JSON的数据格式。
			它允许计算客户端验证基于认证的认证服务器的最终用户的身份，同时通过在一个互操作REST的方式获得最终用户的基本信息。
		</p>
		<p>
			OpenID Connect简单的部署，允许所有类型的客户端，包括基于浏览器、移动设备、JavaScript客户端，请求和接收有关身份的信息、当前已验证的会话。这套规范的可扩展性，允许参与者选择，同样也支持加密的身份数据，发现OpenID提供者，和高级的会话管理，包括注销。
		</p>
		<h5>Tips：</h5>
		<p>
		如果您想对OpenID Connect开放标准进行扩展阅读，请参看：官方技术说明<a href="http://openid.net/connect/"  title="http://openid.net/connect/" target="_blank" rel="nofollow">Connect标准（英文） </a> | <a href="http://en.wikipedia.org/wiki/OpenID_Connect"  title="http://en.wikipedia.org/wiki/OpenID_Connect" target="_blank" rel="nofollow">OpenID Connect维基百科（en）</a> </p>
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
