<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/samlv20/saml2.jsp?language=zh_CN");	
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
 	<jsp:param value="saml_2" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>SP-Init SSO Procedure</h1>
    <!--
    <div class="text-section">
     <p class="section">通过SAML实现ConnSec 与其他合作伙伴的联邦身份认证。</p>
    </div> 一段描述结束 -->
   <div class="imgtxt-img">
    <h5>PROCESS DIAGRAM</h5>
    <img src="../../images/saml/saml2_1.png" alt="SP-Init SSO流程图">
   </div>
	<h5>PROCEDURE DESCRIPTION:</h5>
  
   <div class="text-section">
   <ol>
            	<li>User attempts to access the cooperative partner application of ConnSec.</li>
                <li>Cooperative partner application generates one SAML identity verification request. SAML requests code and insert to SSO service of URL ConnSec. RelayState parameter contains the cooperative partner application of code, and the URL which user attempts to access also has been inserted in SSO URL. The RelayState parameter requires a non-transparent identifier without any modification or inspection return.</li>
   				<li>Cooperative partner sends redirect to user browser. In case redirect of URL code SAML identity verification request shall be submitted to SSO service of ConnSec.</li>
   				<li>SAML request of ConnSec conducts decoding, and extract the URL of two Google assertion consumption service and user goal URL (RelayState parameter)</li>
  				<li>User of ConnSec conducts identity verification. ConnSec can conduct identity verification through the obtaining of the valid login credential or inspection of the valid session.</li>	
  				<li>ConnSec generates one SAML response, including the user name of the user for identity verification. In line with SAML 2.0 standard, this kind of response is the DSA/RSA key digital signature of public and private cooperative partner.</li>
   				<li>ConnSec SAML responses and RelayState parameter conducts coding, and returns this information to the browser of user. ConnSec provides a mechanism to facilitate the browser to forward the information to the ACS of the cooperative partner.</li>
   				<li>ACS of the cooperative partner uses the ConnSec public key to verify SAML response. If the response verified successfully, ACS will redirect user goal URL.</li>
   				<li>Goal URL redirected to user and then recorded in the application of cooperative partner. </li>
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
