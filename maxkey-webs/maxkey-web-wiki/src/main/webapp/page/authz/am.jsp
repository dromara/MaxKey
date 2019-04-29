<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml1_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-intros" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>认证集成介绍</h1>
    <div class="text-section">
     <p class="section">单点登录概述英文全称Single Sign On(SSO)。SSO是在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统。包括可以将这次主要的登录映射到其他应用中用于同一个用户的登录的机制。
	<br>
	主要功能：
	<ol>
		<li><p class="section">一次登录，处处访问</p></li>
	  	<li><p class="section">所有应用系统共享一个身份认证系统</p></li>
	  	<li><p class="section">所有应用系统能够识别和提取Ticket信息</p></li>
	</ol>
     		</p>
      </div><!-- 一段描述结束 -->
    
	<div class="imgtxt-img">
		<h3>认证管理蓝图</h3>
	     <img src="<%=path %>/images/am/am_blueprint.jpg" alt="认证管理蓝图">
	</div>
	
    <h3>认证集成主要协议</h3>
	 <p class="section">
	 <ol>
	  <li><p class="section">OAuth 1.0a</p></li>
	  <li><p class="section">OAuth 2.0</p></li>
	  <li><p class="section">SAML 1.1</p></li>
	  <li><p class="section">SAML 2.0</p></li>
	  <li><p class="section">OpenID Connect</p></li>
	  <li><p class="section">CAS 2.0</p></li>
	  <li><p class="section">CAS 3.0</p></li>
	  <li><p class="section">JWT(JSON Web Token)</p></li>
	  <li><p class="section">WsFederation</p></li>
	  <li><p class="section">Form-Based(B/S;C/S)</p></li>
	  <li><p class="section">Token-Based</p></li>
	  <li><p class="section">Kerberos</p></li>
	  <li><p class="section">LTPA Cookie</p></li>
	  <li><p class="section">Desktop SSO</p></li>
	  <li><p class="section">自定义认证</p></li>
	</ol>
	</p>
	
	<h3>集成开发工具包(Integration  SDK)</h3>
	 <p class="section">
	 <ol>
	  <li><p class="section">JAVA SDK</p></li>
	  <li><p class="section">C# SDK</p></li>
	</ol>
	</p>
	
	<h3>适配器(Adapter)</h3>
	 <p class="section">
	 <ol>
	  <li><p class="section">IBM WebSphere Adapter</p></li>
	  <li><p class="section">Oracle WebLogic Adapter</p></li>
	  <li><p class="section">MS IIS Adapter</p></li>
	  <li><p class="section">Liferay Portal Adapter</p></li>
	</ol>
	</p>

 </div>
 <!-- content end -->
 <!-- //content end -->
<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
