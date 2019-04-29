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
 	<jsp:param value="authz-adapter" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>Adapter集成介绍</h1>
    <div class="text-section">
     <p class="section">Adapter即适配器,用于增强服务的功能,提供额外的服务。ConnSec针对于成熟的产品提供集成的适配器，用户无需再开发，开箱即用，快速完成集成的工作。</p>
     <p class="section"></p>
    </div><!-- 一段描述结束 -->

    <h3>优势</h3>
 <p class="section">
 <ol>
  <li><p class="section">开箱即用。</p></li>
  <li><p class="section">快速部署。</p></li>
  <li><p class="section">降低成本。</p></li>
</ol>
</p>
    <h3>ConnSec提供适配器</h3>
 <p class="section">
 <ol>
  <li><p class="section">IBM WebSphere Adapter</p></li>
  <li><p class="section">Oracle WebLogic Adapter</p></li>
  <li><p class="section">MS IIS Adapter</p></li>
  <li><p class="section">Liferay Portal Adapter</p></li>
</ol>
</p>
    <h3>其他的适配器</h3>
 <p class="section">
 <ol>
  <li><p class="section">腾讯RTX Adapter</p></li>
  <li><p class="section">腾讯企业邮箱  Adapter</p></li>
  <li><p class="section">MS IIS Adapter</p></li>
  <li><p class="section">Liferay Portal Adapter</p></li>
</ol>
<br>
请参照详细的Adapter的相关文档，联系support@connsec.com。
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
