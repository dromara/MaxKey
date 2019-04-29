<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml3_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-adapter-adapter3" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>MS IIS Adapter</h1>
    <div class="text-section">
     <p class="section">Internet Information Services（IIS，互联网信息服务），是由微软公司提供的基于运行Microsoft Windows的互联网基本服务。IIS是一种Web（网页）服务组件，其中包括Web服务器、FTP服务器、NNTP服务器和SMTP服务器，分别用于网页浏览、文件传输、新闻服务和邮件发送等方面。</p>
     <p class="section">MS IIS Adapter为基于IIS的应用服务提供认证集成方案，方便用户能够快速完成集成。</p>
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
	如果您想对IIS进行扩展阅读，请参看：官方技术说明<a href="http://www.iis.net"  title="http://www.iis.net" target="_blank" rel="nofollow">The Official Microsoft IIS Site‎</a> 
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
