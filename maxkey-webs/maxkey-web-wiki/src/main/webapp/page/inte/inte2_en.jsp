<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/inte/inte2.jsp?language=zh_CN");	
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
 	<jsp:param value="inte_2" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>Application Integration Application</h1>
    <div class="text-section">
     <p class="section">The application integrated by ConnSec authentication center must apply for registration first, which can be OAuth application registration and SAML application registration.</p>
    </div><!-- 一段描述结束 -->
    <h3>SAML application integration registration requires the following information:</h3>
     <table class="basisTable" cellspacing="1">
    <thead>
      <th>No.</th><th>SAML Application Registration Information</th><th>Description</th>
    </thead>
    <tbody>
	    <tr>
	        <td>1</td>
	        <td>Application id</td>
	        <td>appId</td>
	    </tr>
	    <tr>
	    	<td>2</td>
	    	<td>Application certificate</td>
	    	<td>The certificate to authenticate X.509 shall be provided by application</td>
	    </tr>
	    <tr>
	    	<td>3</td>
	    	<td>Application access url</td>
	    	<td>url that application accesses directly</td>
	    </tr>
	    <tr>
	    	<td>4</td>
	    	<td>Application SP url</td>
	    	<td>url that SP assertion analyzed as authentication completed</td>
	    </tr>
	    
	     <tr>
	    	<td>5</td>
	    	<td>Application description</td>
	    	<td>Information describing the application functions</td>
	    </tr>
	    <tr>
	    	<td>6</td>
	    	<td>Applicant</td>
	    	<td>Name of applicant</td>
	    </tr>
	    <tr>
	    	<td>7</td>
	    	<td>Email of Applicant</td>
	    	<td>Email of Applicant</td>
	    </tr>
		
    </tbody>
    </table>
   <span class="remark">Configuration of SAML IDP shall refer to SAML 2.0 application integration guide, while the ConnSec certificate can be downloaded from web guide, and Metadata can be downloaded from login.connsec.com.</span>
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
