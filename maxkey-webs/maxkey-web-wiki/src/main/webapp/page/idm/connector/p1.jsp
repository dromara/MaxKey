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
 	<jsp:param value="idm-connector" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>Connector集成</h1>
    <div class="text-section">
     <p class="section">Connector即连接器,用于本地连接/同步数据到其他服务。ConnSec针对于成熟的产品提供集成的连接器，用户无需再开发，开箱即用，快速完成集成的工作。</p>
    </div><!-- 一段描述结束 -->

    <h3>ConnSec提供连接器</h3>
 <p class="section">
 <ol>
  <li><p class="section">ActiveDirectory Connector</p></li>
  <li><p class="section">标准LDAP V3 Connector</p></li>
  <li><p class="section">微信 Connector</p></li>
  <li><p class="section">钉钉 Connector</p></li>
  <li><p class="section">RTX Connector</p></li>
  <li><p class="section">腾讯企业邮箱 Connector</p></li>
  <li><p class="section">Liferay Portal Connector</p></li>
  <li><p class="section">IBM TAM Connector</p></li>
  <li><p class="section">Vagi SSO Connector</p></li>
  
</ol>
</p>

    <h3>连接器Connector使用</h3>
     <p class="section">
     	请参照详细的Connector的相关文档，联系support@connsec.com。
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
