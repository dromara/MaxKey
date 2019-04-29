<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth4.jsp?language=zh_CN");	
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
 	<jsp:param value="oauth_4" name="pageType"/>
 </jsp:include>
 <!-- treeView end -->
  <div class="content">

  	<h1>APP Certified Authentication Procedure</h1>
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
    	<div class="imgtxt-txt">
        <h5>WEB APPLIED VERIFICATION AND AUTHORIZATION:</h5>
        <table class="basisTable" cellspacing="1">
        	<thead>
	        	<th width="70px;">No. </th>
			    <th>Coexistence of Original Authentication and SSO Authentication</th>
			    <th>SSO Authentication</th>
        	</thead>
		  <tr>
		    <td>1</td>
		    <td colspan="2"> User accesses App to apply app.ConnSec.com </td>
		  </tr>
		  <tr>
		    <td>2</td>
		    <td colspan="2">App check whether the Session that the system user authenticated exists or not, if exist, it shows that the user logined, then the authentication finished; otherwise it will enter into the next step. This section will be done by the development of APP system itself.</td>
		  </tr>
		  <tr>
		    <td>3</td>
		    <td width="50%">Enter into APP user login interface, which offer the local login and SSO authentication link  (https://login.connsec.com); APP authenticates itself once the user utilizes local login; otherwise click on SSO authentication link to skip to https://login.connsec.com</td>
		    <td> Application directly skip to SSO authentication link (https://login.connsec.com) </td>
		  </tr>
		  <tr>
		    <td>4</td> 
		    <td colspan="2"> Access into  <strong> " ConnSec OAuth2.0 Authentication Procedure" </strong> </td>
		  </tr>
		  <tr>
		    <td> Features </td>
		    <td> <strong>Strength </strong>： the application possesses higher independency, it can achieve single sign on by either independent from the authentication center or utilize the authentication of the authentication center; however, user needs to remember two sets of password.</td>
		    <td> <strong>Weakness </strong>：the application will not login if the authentication fails. </td>
		  </tr>
		 </table>
    </div>
    Suggestion: apply the local login as emergency plan
    <!--  // end 所有图片加文字描述的样式 div=imgtxt -->
 </div>
 <!-- content end -->
<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
