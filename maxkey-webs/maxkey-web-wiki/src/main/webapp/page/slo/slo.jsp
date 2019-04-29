<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/slo/slo.jsp");	
}
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>

<body>
<jsp:include page="/common/top.jsp"></jsp:include>
<div id="container">
 <jsp:include page="/common/left.jsp">
 	<jsp:param value="slo" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>单点注销-Single Logout</h1>
    <div class="text-section">
    	<p class="section">
    		单点注销是指用户在一个系统退出后，其所能单点登录访问的所有系统都同时退出。单点注销主要是为提高安全性，避免用户忘记退出所有应用而造成信息的泄密。
    	</p>
    	<p class="section">
			ConnSec支持单点注销(SLO),即用户不仅仅从认证中心注销，同时也注销从认证中心访问的应用系统。	
		</p>
		<p class="section">
			其实现方式也非常简单，由于SSO和单点登录的应用都是分开的，使用不同的域名，只是通过认证中心在多个应用系统中传递身份和登录系统。因此，首先注销单点登录应用，然后修改每个应用系统都使用SSO的单点注销页面，SSO的退出页面会将用户登录的Session注销掉。
		</p>
		<h2>单点注销配置</h2>
		<p class="section">
			1,应用系统先完成本系统注销，注销完成后调用认证中心的单点注销地址。
		</p>
		<p class="section">
			2,修改应用单点注销完成后的地址为http://login.connsec.com/sec/logout,参数reLoginUrl为注销完成后访问地址。
		</p>
    </div><!-- 一段描述结束 -->
    <h2>单点注销机制</h2>
     <table class="basisTable" cellspacing="1">
    	<thead>
      		<th style="width:50px" >序号</th><th>认证中心(ShortName)</th><th>单点登录应用(英文)</th><th>单点登录功能(中文名称)</th>
    	</thead>
	    <tbody>
		    <tr>
		        <td>1</td>
		        <td>退出</td>
		        <td>退出</td>
		        <td>失效</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>退出</td>
		        <td>不退出</td>
		        <td>失效</td>
		    </tr>
		    <tr>
		        <td>3</td>
		        <td>不退出</td>
		        <td>退出</td>
		        <td>正常</td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>不退出</td>
		        <td>不退出</td>
		        <td>正常</td>
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
