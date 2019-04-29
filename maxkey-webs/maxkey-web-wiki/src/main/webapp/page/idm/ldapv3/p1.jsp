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
 	<jsp:param value="idm-ldapv3" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>LDAP V3集成</h1>
    <div class="text-section">
     <p class="section">
		LDAP（轻量级目录访问协议，Lightweight Directory Access Protocol)是实现提供被称为目录服务的信息服务。目录服务是一种特殊的数据库系统，其专门针对读取，浏览和搜索操作进行了特定的优化。目录一般用来包含描述性的，基于属性的信息并支持精细复杂的过滤能力。目录一般不支持通用数据库针对大量更新操作操作需要的复杂的事务管理或回卷策略。而目录服务的更新则一般都非常简单。这种目录可以存储包括个人信息、web链结、jpeg图像等各种信息。为了访问存储在目录中的信息，就需要使用运行在TCP/IP 之上的访问协议—LDAP。
	 </p>
    </div><!-- 一段描述结束 -->

	<h3>配置参数集成</h3>
    <table class="basisTable" cellspacing="1">
    	<thead>
      		<th style="width:50px" >序号</th><th>参数</th><th>说明</th>
    	</thead>
	    <tbody>
		    <tr>
		        <td>1</td>
		        <td>providerUrl</td>
		        <td>LDAP地址</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>principal</td>
		        <td>管理员权限用户</td>
		    </tr>
		    <tr>
		        <td>3</td>
		        <td>credentials</td>
		        <td>principal用户的登录密码</td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>baseDN</td>
		        <td>管理目录</td>
		    </tr>
		    </tbody>
		</table>
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
