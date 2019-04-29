<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/inte/inte1_en.jsp?language=en_US");	
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
 	<jsp:param value="inte-inte" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>应用集成申请</h1>
    <div class="text-section">
     <p class="section">在ConnSec认证中心集成的应用必须先申请注册，应用注册可以分为OAuth应用注册和SAML应用注册。</p>
    </div><!-- 一段描述结束 -->
    <h3>OAuth应用集成注册,需要如下信息:</h3>
     <table class="basisTable" cellspacing="1">
    <thead>
      <th>序号</th><th>OAuth应用注册信息</th><th>描述</th>
    </thead>
    <tbody>
	    <tr>
	        <td>1</td>
	        <td>应用id</td>
	        <td>appId/client_id</td>
	    </tr>
	    <tr>
	    	<td>2</td>
	    	<td>应用secret</td>
	    	<td>appSecret /client_secret由系统自动生成，可重新生成</td>
	    </tr>
	    <tr>
	    	<td>3</td>
	    	<td>应用访问地址</td>
	    	<td>应用直接访问的地址</td>
	    </tr>
	    <tr>
	    	<td>4</td>
	    	<td>应用OAuth回调地址</td>
	    	<td>认证完成后，认证中心返回的地址，参数为code</td>
	    </tr>
	    
	     <tr>
	    	<td>5</td>
	    	<td>应用描述</td>
	    	<td>描述应用功能等信息</td>
	    </tr>
	    <tr>
	    	<td>6</td>
	    	<td>支持认证方式</td>
	    	<td>Authorization_Code/password,多选</td>
	    </tr>
	    <tr>
	    	<td>7</td>
	    	<td>申请人</td>
	    	<td>申请人姓名</td>
	    </tr>
		<tr>
			<td>8</td>
			<td>申请人email地址</td>
			<td>申请人email</td>
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
