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
 	<jsp:param value="authz-adapter-adapter1" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>IBM WebSphere Adapter</h1>
    <div class="text-section">
    <p class="section">WebSphere 是 IBM 的软件平台。它包含了编写、运行和监视全天候的工业强度的随需应变 Web 应用程序和跨平台、跨产品解决方案所需要的整个中间件基础设施。</p>
    <p class="section">IBM WebSphere Adapter为基于IBM WebSphere Application Server的产品或者应用提供服务认证,基于Trust Association Interceptor信任关联拦截器(TAI) Adapter。</p>
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
		        <td>com.connsec.oauth.client.id</td>
		        <td>应用ID</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>com.connsec.oauth.client.secret</td>
		        <td>应用密钥</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>3</td>
		        <td>com.connsec.oauth.web.context</td>
		        <td>需要认证上下文</td>
		        <td>必须</td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>com.connsec.oauth.userregistry</td>
		        <td>使用WAS用户注册表认证</td>
		        <td>可选</td>
		    </tr>
		    <tr>
		        <td>5</td>
		        <td>com.connsec.oauth.web.domain</td>
		        <td>应用所在的域范围</td>
		        <td>可选</td>
		    </tr>
		    <tr>
		        <td>6</td>
		        <td>com.connsec.oauth.web.cookiename</td>
		        <td>cookie名称</td>
		        <td>可选</td>
		    </tr>
		    <tr>
		        <td>7</td>
		        <td>com.connsec.oauth.web.is.cookie</td>
		        <td>是否存储cookie</td>
		        <td>可选</td>
		    </tr>
		    
		</tbody>
	</table>
</p>
<h5>Tips：</h5>
<p>
	如果您想对Trust Association Interceptor进行扩展阅读，请参看：官方技术说明<a href="http://www-01.ibm.com/support/knowledgecenter/search/TrustAssociationInterceptor"  title="http://www-01.ibm.com/support/knowledgecenter/search/TrustAssociationInterceptor" target="_blank" rel="nofollow">IBM Knowledge Center </a> 
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
