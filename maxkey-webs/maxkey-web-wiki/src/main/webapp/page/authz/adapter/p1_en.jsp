<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/samlv20/saml1.jsp?language=zh_CN");	
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
 	<jsp:param value="saml_1" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>SAML Introduction</h1>
 	<h3> 	ConnSec authentication center provides a SAML based SSO service serving as the identity provider to control user name, password and other information, a web application for identification, ID verification and user authorization. Notes: SAML application integration requires the completion of application integration application; see SAML related contents for details.
  	</h3>
    <div class="text-section">
     <p class="section">SAML, full name Security Assertion Markup Language, is a XML based standard used for the user identity verification and authorization data exchange between different security domains. SAML standard defines the identity provider and service provider, which consists of the aforesaid different security domains. SAML is the product of Security Services Technical Committee of OASIS organization. The official technical specification can refer to OASIS Security Services (SAML) TC.</p>
     <p class="section">The online service provider can use SAML to contact independent network identity authentication provider, who conducts identity verification on the user attempting to access the protected contents.</p>
     <p class="section">Federation shall mean the group consisting of two or more business partners. It allows the user of the federal cooperation partner (Member Company) to access seamlessly in a safe and reliable manner into the resources of another cooperative partner abide by the business and technical agreement. In the federal business model (in which, the service is federal or be shared with the business cooperation partner), the identity of the user of one company, based on the agreement between the related entities, will be converted to access the web site of the other company legally, and the other company shall not need to learn about the original identity of the user.</p>
     <p class="section">The federal identity authentication of ConnSec and other cooperative partner can be fulfilled through SAML.</p>
    </div><!-- 一段描述结束 -->
   <div class="imgtxt-img">
    <h5>PROCESS DIAGRAM</h5>
    <img src="../../images/saml/saml1.png" alt="saml协议流程图">
   </div>
    <h3>Responsibility for each party to fulfill federal identity authentication through SAML </h3>
     <table class="basisTable" cellspacing="1">
    <thead>
      <th>ConnSec Authentication Center(Identity Provider/IDP)</th><th>Cooperative Partner(Service Provider/SP)</th>
    </thead>
    <tbody>
	    <tr>
	        <td>User identity authentication</td>
	        <td>Security assertion decision</td>
	    </tr>
	    <tr>
	        <td>Federal identity security assertion</td>
	        <td>Federal identity maintain</td>
	    </tr>
	    <tr>
	        <td>User account administration</td>
	        <td>Service offering and access control</td>
	    </tr>
    
    </tbody>
    </table>
    </br>
    <div class="text-section">
     <p class="section">IDP and SP complete mutual trust configuration of certificate in advance, the SAML authentication based on the encryption of certificate, the course of delivery is safe, only the holder of certificate can analyze the assertion.</p>
     <p class="section">Important Notice: SAML SSO solution can only apply to Web application.</p>
    </div><!-- 一段描述结束 -->
    
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
