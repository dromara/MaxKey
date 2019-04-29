<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml2_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-adapter-adapter2" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>Oracle WebLogic Adapter</h1>
    <div class="text-section">
     <p class="section">WebLogic是美国Oracle公司出品的一个application server，确切的说是一个基于JAVAEE架构的中间件，WebLogic是用于开发、集成、部署和管理大型分布式Web应用、网络应用和数据库应用的Java应用服务器。将Java的动态功能和Java Enterprise标准的安全性引入大型网络应用的开发、集成、部署和管理之中。</p>
     <p class="section">Oracle WebLogic Adapter为基于WebLogic应用系统提供服务认证集成方案，方便用户能够快速完成集成。</p>
    </div><!-- 一段描述结束 -->
<h3>配置参数</h3>
<p class="section">
    <table class="basisTable" cellspacing="1">
    	<thead>
      		<th style="width:50px" >序号</th><th>参数</th><th>作用(英文)</th><th>备注(中文名称)</th>
    	</thead>
	    <tbody>
		    <tr>
		        <td>1</td>
		        <td>config.oauthv20.clientId</td>
		        <td>应用ID</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>config.oauthv20.clientSecret</td>
		        <td>应用密钥</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>3</td>
		        <td>config.oauthv20.redirectUri</td>
		        <td>认证成功后地址</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>config.oauthv20.web.domain</td>
		        <td>应用所在的域范围</td>
		        <td>可选</td>
		    </tr>
		    <tr>
		        <td>5</td>
		        <td>config.oauthv20.web.cookiename</td>
		        <td>cookie名称</td>
		        <td>可选</td>
		    </tr>
		</tbody>
	</table>
</p>
<h5>Tips：</h5>
<p>
	如果您想对Weblogic进行扩展阅读，请参看：官方技术说明<a href="http://docs.oracle.com/en/middleware/"  title="http://docs.oracle.com/en/middleware/" target="_blank" rel="nofollow">Oracle Middleware Documentation </a> 
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
