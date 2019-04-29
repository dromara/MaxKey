<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth3.jsp?language=zh_CN");	
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
 	<jsp:param value="oauth_3" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
<div class="content">
  	<h1>SSO Certified Authentication Procedure</h1>

   <div class="imgtxt-img">
    <h5>PROCESS DIAGRAM</h5>
    <img src="../../images/auth/auth1.png" alt="PROCESS DIAGRAM">
   </div>

<h5>PROCESS DESCRIPTION:</h5>
  	<div class="text-section">
   <ol>
 				<li>User accesses into app.connsec.com.</li>
 				<li>System redirects to login.connsec.com to request for authentication.</li>
 				<li>User inputs user name and password, and the authentication center completes authentication.</li>
 				<li>The authentication center creates OAuth code and redirect OAuth code to access APP with OAuth address.</li>
 				<li>APP obtains OAuth code and requests for validation, OAuth Access Token obtained.</li>
 				<li> Authentication center return to Access Token.</li>
 				<li> The application verifies the validation of Access Token. If correct, access to next; the authentication fails if wrong and then the procedure suspended. The validation approach refers to token interface, the return data containing access-token and shall not be null.</li>
 				<li> APP calls user interface through OAuth Access Token to obtain user information.</li>
 				<li> Authentication center returns to user information.</li>
 				<li> APP judges whether user exists or not.</li>
 				<li> Authentication succeeds if user exists and then entering into the application access; authentication error occurs when user doesn’t exist and the procedure shall terminate.</li>
 				<li>Process finish. </li>
 	</ol>
 	</div>
 	</div>
 	</div>	
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>		