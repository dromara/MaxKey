<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/inte/inte2_en.jsp?language=en_US");	
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
 	<jsp:param value="inte-inte2" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>应用集成申请</h1>
    <div class="text-section">
     <p class="section">在ConnSec认证中心集成的应用必须先申请注册，应用注册可以分为OAuth应用注册和SAML应用注册。</p>
    </div><!-- 一段描述结束 -->
    <h3>SAML应用集成注册,需要如下信息:</h3>
     <table class="basisTable" cellspacing="1">
    <thead>
      <th>序号</th><th>SAML应用注册信息</th><th>描述</th>
    </thead>
    <tbody>
	    <tr>
	        <td>1</td>
	        <td>应用id</td>
	        <td>appId</td>
	    </tr>
	    <tr>
	    	<td>2</td>
	    	<td>应用证书</td>
	    	<td>认证X.509的证书，需要应用提供</td>
	    </tr>
	    <tr>
	    	<td>3</td>
	    	<td>应用访问地址</td>
	    	<td>应用直接访问的地址</td>
	    </tr>
	    <tr>
	    	<td>4</td>
	    	<td>应用SP地址</td>
	    	<td>认证完成后，SP断言解析的地址</td>
	    </tr>
	    
	     <tr>
	    	<td>5</td>
	    	<td>应用描述</td>
	    	<td>描述应用功能等信息</td>
	    </tr>
	    <tr>
	    	<td>6</td>
	    	<td>申请人</td>
	    	<td>申请人姓名</td>
	    </tr>
	    <tr>
	    	<td>7</td>
	    	<td>申请人email地址</td>
	    	<td>申请人email</td>
	    </tr>
		
    </tbody>
    </table>
   <span class="remark">SAML IDP的配置见SAML2.0应用集成指引，另外ConnSec证书可以从web指引上下载,Metadata可以从login.connsec.com上下载。</span>
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
