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
 	<jsp:param value="idm-msad" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>MSAD集成</h1>
    <div class="text-section">
     <p class="section">活动目录（Active Directory）是面向Windows Standard Server、Windows Enterprise Server以及 Windows Datacenter Server的目录服务。（Active Directory不能运行在Windows Web Server上，但是可以通过它对运行Windows Web Server的计算机进行管理。）Active Directory存储了有关网络对象的信息，并且让管理员和用户能够轻松地查找和使用这些信息。Active Directory使用了一种结构化的数据存储方式，并以此作为基础对目录信息进行合乎逻辑的分层组织。
     </p>
   	<p class="section">
   	Microsoft Active Directory 服务是Windows 平台的核心组件，它为用户管理网络环境各个组成要素的标识和关系提供了一种有力的手段。
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
		        <td>AD域地址</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>principal</td>
		        <td>具有AD管理员权限用户</td>
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
		    <tr>
		        <td>5</td>
		        <td>domain</td>
		        <td>AD域标识</td>
		    </tr>
		    <tr>
		        <td>6</td>
		        <td>trustStore</td>
		        <td>与AD域信任的证书文件</td>
		    </tr>
		    </tbody>
		</table>
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
