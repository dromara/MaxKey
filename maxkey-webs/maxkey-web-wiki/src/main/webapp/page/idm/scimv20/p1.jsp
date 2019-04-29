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
 	<jsp:param value="idm-scim" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>SCIM集成</h1>
    <div class="text-section">
     <p class="section">跨域身份管理（SCIM）规范设计，使基于云的应用和服务更容易的管理用户身份。规范套件，旨在建立在现有的架构和部署的经验，特别强调简单的开发和集成，同时运用现有的身份验证，授权和隐私模式。它的目的是通过提供一个通用的用户模式和推广模式，以及具有约束力的文件，使用标准协议交换架构提供模式，以降低成本和复杂性的用户管理操作。从本质上讲，它速度快，价格便宜，容易出来的，和周围的云，移动用户。
     </p>
    </div><!-- 一段描述结束 -->

    <h3>接口说明</h3>
    <div class="text-section">
    <p class="section">

	</p>
	</div>

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
