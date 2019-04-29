<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml4_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-adapter-adapter4" name="pageType"/>
 </jsp:include> 
  <!-- treeView end -->
  <div class="content">
  	<h1>Liferay Portal Adapter</h1>
    <div class="text-section">
     <p class="section">Liferay是一个完整的门户解决方案，开源的Portal产品,提供对多个独立系统的内容集成，为企业信息、流程等的整合提供了一套完整的解决方案，和其他商业产品相比，Liferay有着很多优良的特性，而且免费。</p>
     <p class="section">Liferay Portal Adapter为基于Liferay Portal提供服务认证集成方案，方便用户能够快速完成集成。</p>
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
		        <td>auto.login.hooks</td>
		        <td>认证hooks值为com.connsec.adapter.liferay.OAuthV20AutoLogin</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>config.oauthv20.authType</td>
		        <td>认证类型，可选值EmailAddress,ScreenName,Id</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>3</td>
		        <td>config.oauthv20.companyId</td>
		        <td>公司编号</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>config.oauthv20.clientId</td>
		        <td>应用ID</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>5</td>
		        <td>config.oauthv20.clientSecret</td>
		        <td>应用密钥</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>6</td>
		        <td>config.oauthv20.redirectUri</td>
		        <td>认证成功后地址</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>7</td>
		        <td>config.oauthv20.web.domain</td>
		        <td>应用所在的域范围</td>
		        <td>可选</td>
		    </tr>
		    <tr>
		        <td>8</td>
		        <td>config.oauthv20.web.cookiename</td>
		        <td>cookie名称</td>
		        <td>可选</td>
		    </tr>
		</tbody>
	</table>
</p>
<h5>Tips：</h5>
<p>
	如果您想对liferay进行扩展阅读，请参看：官方技术说明<a href="http://www.liferay.com/zh/documentation/liferay-portal/6.2/user-guide"  title="http://www.liferay.com/zh/documentation/liferay-portal/6.2/user-guide" target="_blank" rel="nofollow">liferay-portal user-guide </a> 
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
