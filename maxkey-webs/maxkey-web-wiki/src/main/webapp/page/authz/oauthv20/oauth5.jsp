<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth5_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-oauth20-oauth5" name="pageType"/>
 </jsp:include>
 <!-- treeView end -->
  <div class="content">

  	<h1>Web应用的验证授权</h1>
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
    <div class="imgtxt">
    	<div class="imgtxt-txt">
        <p class="section">采用Authorization Code获取Access Token的授权验证流程又被称为Web Server Flow，适用于所有Server端的应用。其调用流程示意图如下：</p>
        </div>
        <div class="imgtxt-img">
        <h5>流程说明图</h5><img src="<%=path %>/images/img/img-oauth4.png" alt="ConnSec OAuth2.0协议流程图">
        <p class="section_org">对于应用而言，其流程由获取Authorization Code和通过Authorization Code获取Access Token这2步组成。</p>
        <p class="section_title">1.引导需要授权的用户到如下地址：
        <pre> https://login.connsec.com/sec/oauth/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI </pre>
        </p>
         <p class="section_title">2.页面跳转至 YOUR_REGISTERED_REDIRECT_URI/?code=CODEsss
        </p>
         <p class="section_title">3. 换取Access Token
         <pre>https://login.connsec.com/sec/oauth/token?client_id=YOUR_CLIENT_ID&client_secret=YOUR _SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE</pre>
        </p>
        <p class="section_title">返回值<br>
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
