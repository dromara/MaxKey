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
 	<jsp:param value="idm-spml" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>SPML集成</h1>
    <div class="text-section">
     <p class="section"> Service Provisioning Markup Language（服务配置标记语言，SPML）,SPML是解决身份管理的不同方面的资源配置的标准。它为那些实现业务活动的装备 IT 基础设施和支持基础设施这类普通但又容易出错的任务制定了标准。比如，当组织雇用新的员工时 SPML 可以实现配置工作流的自动化。
     <br>
     SPML的目标包括：
     	 <ol>
		  <li><p class="section">自动化 IT 配置任务：通过标准化配置工作，使其更容易封装配置系统的安全和审计需求，SPML 致力于推动配置的自动化。</p></li>
		  <li><p class="section">不同配置系统之间的互操作性：不同的配置系统可以公开标准的 SPML 接口，实现互操作。</p></li>
		</ol>
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
