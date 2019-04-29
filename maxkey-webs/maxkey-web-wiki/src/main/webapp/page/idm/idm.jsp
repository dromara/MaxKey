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
 	<jsp:param value="idm-intros" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>组织和账号集成介绍</h1>
    <div class="text-section">
     <p class="section">ConnSec提供对人员全生命周期的管理，作为身份管理的中心，是企业组织信息和账号的管理的核心系统。
     		整个生命周期包含了用户的创建、账号的分配和授权、用户的访问审计、身份的清除及终止、用户身份的变更等。<br>
     		</p>
     <p class="section">用户的信息一般来源于HR(人事管理系统),CRM(客户管理管理系统)等,身份管理能及时地管理报到(供应，即创建和更新帐户)和离职(取消供应，即删除用户帐户)的用户。</p>
    </div><!-- 一段描述结束 -->
    
	<div class="imgtxt-img">
		<h3>身份管理蓝图</h3>
	     <img src="<%=path %>/images/idm/idm_blueprint.jpg" alt="身份管理蓝图">
	</div>
	
    <h3>用户治理生命周期</h3>
	 <p class="section">
	 <ol>
	  <li><p class="section">用户创建</p></li>
	  <li><p class="section">账号的分配和授权</p></li>
	  <li><p class="section">用户的访问审计</p></li>
	  <li><p class="section">身份的清除及终止</p></li>
	  <li><p class="section">用户身份的变更</p></li>
	</ol>
	</p>
	<h3>用户供应规则</h3>
	<p class="section">
	 <ol>
	  <li><p class="section">即时供应(Just in Time)</p></li>
	  <li><p class="section">用户触发(User Manual)</p></li>
	  <li><p class="section">定时任务(CronTab)</p></li>
	</ol>
	</p>
	<h3>标准连接器Connector</h3>
	<p class="section">
	 <ol>
	  <li><p class="section">MS AD Connector</p></li>
	  <li><p class="section">LDAP V3 Connector</p></li>
	  <li><p class="section">REST Connector</p></li>
	  <li><p class="section">SPML Connector</p></li>
	  <li><p class="section">SCIM Connector</p></li>
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
