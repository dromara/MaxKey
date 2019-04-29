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
 	<jsp:param value="authz-saml20" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>SAML 介绍</h1>
 	
    <div class="text-section">
     <p class="section">SAML即安全断言标记语言，英文全称是Security Assertion Markup Language。它是一个基于XML的标准，用于在不同的安全域(security domain)之间用户身份验证和授权数据交换。在SAML标准定义了身份提供者(identity provider)和服务提供者(service provider)，这两者构成了前面所说的不同的安全域。 SAML是OASIS组织安全服务技术委员会(Security Services Technical Committee)的产品。官方技术说明可参看OASIS Security Services (SAML) TC.</p>
    
     <p class="section">使用SAML，在线服务供应商可以联系一个独立的网络身份认证提供者，谁是试图访问受保护的内容的用户进行身份验证。</p>
     <p class="section">联邦是指两个或更多可信的业务合作伙伴组成的团体，其遵照的业务和技术协议允许来自联邦合作伙伴(成员公司)的用户以一种安全可靠的方式，无缝地访问另一家合作伙伴的资源。在联邦业务模型中(其中，服务是联邦化的，或可以与业务合作伙伴共享)，根据有关实体间达成的协议，一家公司的用户的身份将被转换，以合法访问另一家公司的Web站点，而另一家公司无需了解该用户的原始身份。</p>
     
     <p class="section">
     	ConnSec认证中心提供了一个基于SAML的单点登录（SSO）服务，作为身份提供者(Identity provider)，控制用户名、密码和其他信息，用于识别，身份验证和授权用户的Web应用程序。
  	</p>
  	 <p class="section">备注：SAML应用集成需完成应用集成申请，详见SAML相关内容。</p>
  	<p class="section">通过SAML实现ConnSec 与其他合作伙伴的联邦身份认证。</p>
    </div><!-- 一段描述结束 -->
   <div class="imgtxt-img">
    <h5>流程说明图</h5>
    <img src="<%=path %>/images/saml/saml1.png" alt="saml协议流程图">
   </div>
    <h3>SAML实现联邦身份认证各方职责</h3>
     <table class="basisTable" cellspacing="1">
    <thead>
      <th>ConnSec认证中心(Identity Provider/IDP)</th><th>合作伙伴(Service Provider/SP)</th>
    </thead>
    <tbody>
	    <tr>
	        <td>用户身份认证</td>
	        <td>安全断言判定</td>
	    </tr>
	    <tr>
	        <td>联邦身份安全断言</td>
	        <td>联邦身份维护</td>
	    </tr>
	    <tr>
	        <td>用户账号管理</td>
	        <td>服务提供和访问控制</td>
	    </tr>
    
    </tbody>
    </table>
    </br>
    <div class="text-section">
     <p class="section">IDP和SP预先完成证书的互信配置，SAML认证基于断言，断言基于证书的加密，传递过程是安全的，只有证书的持有者才能对断言进行解析</p>
     <p class="section">重要注意:SAML SSO解决方案仅适用于Web应用程序.</p>
     
    </div><!-- 一段描述结束 -->
    <h5>Tips：</h5>
	<p>
		如果您想对SAML 2.0开放标准进行扩展阅读，请参看：官方技术说明<a href="https://wiki.oasis-open.org/security/FrontPage"  title="https://wiki.oasis-open.org/security/FrontPage" target="_blank" rel="nofollow">SAML标准（英文） </a> | <a href="http://en.wikipedia.org/wiki/Security_Assertion_Markup_Language"  title="http://en.wikipedia.org/wiki/Security_Assertion_Markup_Language" target="_blank" rel="nofollow">SAML维基百科（中文）</a> 
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
