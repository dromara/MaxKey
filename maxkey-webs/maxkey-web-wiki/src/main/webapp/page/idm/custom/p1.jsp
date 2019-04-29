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
 	<jsp:param value="idm-custom" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>定制集成</h1>
    <div class="text-section">
     <p class="section">定制的认证集成是为了用户能够更好的对认证系统进行扩展，对系统定义了对应的扩展接口。
     <br>
     	1、基础的协议已经提供了默认的实现方法，用户仅需配置就可以完成单点登录的功能；<br>
     	2、部分应用比较特殊，使用当前的协议无法满足其认证的功能，需要开发人员进行扩展。
     	<br>
     	备注 ：实现基于JAVA平台</p>
    </div><!-- 一段描述结束 -->


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
