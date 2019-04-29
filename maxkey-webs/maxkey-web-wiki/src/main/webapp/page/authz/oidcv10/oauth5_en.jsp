<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth5.jsp?language=zh_CN");	
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
 	<jsp:param value="oauth_5" name="pageType"/>
 </jsp:include>
 <!-- treeView end -->
  <div class="content">

  	<h1>WEB Applied Verification and authorization</h1>
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
    <div class="imgtxt">
    	<div class="imgtxt-txt">
        <p class="section">The authorization and verification procedure adopting Authorization Code to obtain Access Token has also been referred to as Web Server Flow, which can be applied to all the Server application. Its call process diagram is as follows:</p>
        </div>
        <div class="imgtxt-img">
        <h5>Process Diagram</h5><img src="../../images/img/img-oauth4.png" alt="ConnSec OAuth2.0 Protocol Process Diagram">
        <p class="section_org">For the application, its process is consisting of the two steps of obtaining Authorization Code and obtaining Access Token through Authorization Code.</p>
        <p class="section_title">1. Guide the user requiring authorization to the following address:
        <pre> https://login.connsec.com/sec/oauth/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI </pre>
        </p>
         <p class="section_title">2.Page skips to YOUR_REGISTERED_REDIRECT_URI/?code=CODEsss
        </p>
         <p class="section_title">3. Get Access Token
         <pre>https://login.connsec.com/sec/oauth/token?client_id=YOUR_CLIENT_ID&client_secret=YOUR _SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE</pre>
        </p>
        <p class="section_title">Returned Value<br>
        { "access_token":"SlAV32hkKG", "remind_in ":3600, "expires_in":3600 }</p>
        </div>
    </div>
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
