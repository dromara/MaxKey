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
 	<jsp:param value="authz-cas-cas2" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>SP-Init SSO流程</h1>
    <!--
    <div class="text-section">
     <p class="section">通过SAML实现ConnSec 与其他合作伙伴的联邦身份认证。</p>
    </div> 一段描述结束 -->
   <div class="imgtxt-img">
    <h5>流程示意图</h5>
    <img src="../../images/saml/saml2_1.png" alt="SP-Init SSO流程图">
   </div>
	<h5>流程说明：</h5>
  
   <div class="text-section">
   <ol>
            	<li>用户试图访问ConnSec的合作伙伴应用。</li>
                <li>合作伙伴应用生成一个SAML身份验证请求。SAML请求编码并嵌入到URL ConnSec的SSO服务。RelayState参数包含编码的合作伙伴应用程序，用户尝试访问的URL也被嵌入在SSO URL。这的RelayState参数，就是要一个不透明的标识符，不作任何修改或检查传回的。</li>
   				<li>合作伙伴发送重定向到用户的浏览器。重定向URL编码SAML身份验证请求的，应提交到ConnSec的SSO服务。</li>
   				<li>ConnSec的SAML请求进行解码，并提取两个谷歌的断言消费服务（ACS）和用户的目标URL（RelayState参数）的URL。</li>
  				<li>ConnSec的用户进行身份验证。ConnSec可以通过要求有效的登录凭据，或通过检查有效的会话对用户进行身份验证。</li>	
  				<li>ConnSec生成一个SAML响应，其中包含身份验证的用户的用户名。按照SAML 2.0规范，这种反应是公共和私人合作伙伴的DSA / RSA密钥数字签名的</li>
   				<li>ConnSec SAML响应和RelayState参数进行编码，并将该信息返回到用户的浏览器。ConnSec提供了一种机制，使浏览器可以转发信息到合作伙伴的ACS。</li>
   				<li>合作伙伴的ACS使用ConnSec的公钥验证SAML响应。如果成功验证的响应，ACS将用户重定向的目标URL。</li>
   				<li>用户被重定向的目标URL，并记录在合作伙伴应用程序。</li>
   </ol>
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
