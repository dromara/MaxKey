<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/samlv20/saml4.jsp?language=zh_CN");	
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
 	<jsp:param value="saml_4" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
    <h1>AML Safety Authentication Configuration</h1>
     <table class="basisTable" cellspacing="1">
    <thead>
      <th>SAML Configuration</th><th>URL</th>
    </thead>
    <tbody>
      <tr>
    	<td>SAML SSO authentication address</td>
        <td>https://login.connsec.com/sec/saml/authorize/appid</td>
    </tr>
     <tr>
    	<td>SAML IDP metadata XML address</td>
        <td>https://login.connsec.com/sec/saml/metadata/appid</td>
    </tr>
     <tr>
        <td colspan="2">Note: fulfill the mutual trust of SP and IDP through the import of metadata</td>
    </tr>
    </tbody>
    </table>
    
    <div id="chapters">
   ConnSec SAML and self-development application authentication configuration
  	<ol>
    	<li><a href="#001">generate certificate</a></li>
        <li><a href="#002">register SAML application</a></li>
        <li><a href="#003">fulfill certificate mutual trust</a></li>
    </ol>
    
    <div id="chapters">
 		<a href="#004">ConnSec SAML and Saas Application Authentication Configuration</a>
  	<!-- 
  	<ol>
    	<li><a href="">ConnSec SAML与Saas应用认证配置</a></li>
    </ol>
     -->
  </div>

    <!-- 所有图片加文字描述的样式 div=imgtxt -->
    <div class="imgtxt">
    	<div class="imgtxt-txt">
		<h4><a name="001">1. Generate Certificate</a></h4>
        <h5>THE TRUST BETWEEN SAML IDP AND SP REQUIRES CERTIFICATE CONFIGURATION; THE AUTHENTICATION INFORMATION WILL BE ENCRYPTED AND DECRYPTED THROUGH CERTIFICATE, THE FORMATE OF CERTIFICATE IS X.509 CERTIFICATE; THE INTRODUCTION BELWO IS ABOUT USING JDK KEYTOOL TOOL TO GENERATE CERTIFICATE.</h5>
       <pre>
		        Cmd enters the keytool of jdk to install directory, input command: keytool -genkey –alias certificate alias-keypass alias password -keyalg RSA -keysize 1024 -validity 365 –keystore certificate storage path and name  -storepass certificate key
       </pre>
        <div class="imgtxt-img">
        <h5>THE STEPS DISPLAY AS THE FOLLOWING CHARTS:</h5>
        <img src="../../images/saml/saml4_1.png" alt="JDK keytool generate certificate">
        </div>
        
        
        <div class="imgtxt-img">
      <pre>Input command: keytool -list -v -keystore d:\ConnSecsp.keystore -storepass secret in cmd.exe, if the result is the same as the following chart shows, the certificate has been successfully generated.</pre>
        <img src="../../images/saml/saml4_2.png" alt="JDK keytool generate certificate">
        </div>
    </div>    
    </div>
    <!--  // end 所有图片加文字描述的样式 div=imgtxt -->
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
   

    <div class="imgtxt">
    	<div class="imgtxt-txt">
		<h4><a name="002">2. Register SAML Application</a></h4>
        <ul>
        <li>
		       <h5>
		       WHEN UTILIZE SAML, ONLINE SERVICE PROVIDER CAN CONTACT AN INDEPENDENT NETWORK IDENTITY AUTHENTICATION PROVIDER TO CARRY OUT IDENTITY VERIFICATION ON THE USER WHO ATTEMPTS TO ACCESS THE PROTECTED CONTENTS. THIS FULFILLMENT OF THIS PROCESS REQUIRES THE REGISTRATION OF THE RELATED SAML INFORMATION OF SERVICE PROVIDER TO THE IDENTITY AUTHENTICATION PROVIDER TO FACILITATE THE IDENTITY AUTHENTICATION PROVIDER TO REQUEST JUDGEMENT AND HANDLING, E.G. VERIFY WHETHER THE REQUEST HAS BEEN MADE BY THE VALID SERVICE PROVIDER. ITS CONFIGURATE SHALL BE AS FOLLOWS:
		       </h5>
        </li>
        </ul>
        <div class="imgtxt-img">
        <h5>THE STEPS DISPLAY AS THE FOLLOWING CHARTS:</h5>
        <img src="../../images/saml/saml4_3.png" alt="generate config">
        </div>
        
<table class="basisTable" cellspacing="1">
    <thead>
      <th>Contents</th><th>Function</th>
    </thead>
    <tbody>
	    <tr>
	    	<td>Service provider certificate</td>
	        <td>SP applied certificate, the purpose is to make IDP trust this certificate</td>
	    </tr>
	    <tr>
	    	<td>Authentication token URL</td>
	        <td>As IDP successfully authenticated, skip to SP URL</td>
	    </tr>
	    <tr>
	    	<td>Enable SAML authentication</td>
	        <td>Whether SAML authentication enabled, if yes, authenticate with SAML protocol</td>
	    </tr>
    </tbody>
</table>
    </div>    
    </div>
 <div id="chapters">
 		<a name="003">3.Fulfill Certificate Mutual Trust</a>
  </div>
	<div class="text-section">
		In the course of SAML authentication, IDP and SP shall both judge whether the request party and the corresponding party trusted. Thus, the certificate is trust certificate for the fulfillment of SSO by using SAML protocol. The steps are:
        <ol>
        <li> Download IDP certificate</li>
        <li>Add the IDP certificate to SP certificate to make the latter trust the former.</li>
        </ol>
          <pre>Cmd enters the keytool of jdk to install directory, input command: keytool - -import -alias certificate alias (unique alias, otherwise the export errot) -file d:\ConnSecidp.cer -keystore d:\ConnSecsp.keystore -storepass certificate key, this step is to import ConnSecidp certificate to ConnSecsp.keystore so that the ConnSecsp.keystore trusts ConnSecidp.cer certificate.</pre>
	</div>




   <div class="imgtxt">
    	<div class="imgtxt-txt">
		<h4><a name="004">ConnSec SAML and Saas Application Authentication Configuration</a></h4>
		 <ol>
        <li>Download Saas applied certificate.</li>
        <li>Register SAML application, and upload the downloaded Saas certificate to the information configuration.</li>
        <li>Add the Saas applied certificate to the ConnSec SAML certificate to make the Saas to be trusted by the self-developed certificate of ConnSec SAML</li>
        </ol>
		</div>
   </div>		



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
