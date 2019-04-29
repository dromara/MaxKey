<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth3_en.jsp?language=en_US");	
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>

<body>
<jsp:include page="/common/top.jsp"/>
<div id="container">
 <jsp:include page="/common/left.jsp">
 	<jsp:param value="authz-oauth10a-oauth3" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>通过SSO的认证流程</h1>

   <div class="imgtxt-img">
    <h5>流程示意图</h5>
    <img src="<%=path %>/images/auth/auth1.png" alt="流程示意图">
   </div>

<h5>流程说明：</h5>
  	<div class="text-section">
   <ol>
 				<li>用户访问app.connsec.com。</li>
 				<li>系统跳转到login.connsec.com请求认证。</li>
 				<li>用户输入用户名密码，认证中心完成认证。</li>
 				<li>认证中心创建OAuth code，并带OAuth code返回应用OAuth 地址。</li>
 				<li>应用获取OAuth code并请求校验，获取OAuth Access Token。</li>
 				<li>认证中心返回Access Token。</li>
 				<li>应用校验Access Token的合法性，校验正确，进入下一步；错误则认证失败，流程结束。校验方式参见token接口，返回的数据包含access_token且不为空。</li>
 				<li>应用通过OAuth Access Token调用用户接口获取用户信息。</li>
 				<li>认证中心返回用户信息。</li>
 				<li>应用判断用户是否存在。</li>
 				<li>用户存在则认证成功，进入应用访问；不存在认证错误，流程结束。</li>
 				<li>流程结束。</li>
 	</ol>
 	</div>
 	</div>
 	</div>		
 <div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>	