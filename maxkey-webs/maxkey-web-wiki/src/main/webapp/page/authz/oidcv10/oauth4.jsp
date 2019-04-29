<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth4_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-oidc10-oidc4" name="pageType"/>
 </jsp:include>
 <!-- treeView end -->
  <div class="content">

  	<h1>通过应用本身的认证流程</h1>
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
    	<div class="imgtxt-txt">
        <h5>Web应用的验证授权：</h5>
        <table class="basisTable" cellspacing="1">
        	<thead>
	        	<th width="70px;">序号 </th>
			    <th>原有认证和 SSO 认证并存</th>
			    <th>SSO 认证 </th>
        	</thead>
		  <tr>
		    <td>1</td>
		    <td colspan="2"> 用户访问 App 应用 app.ConnSec.com </td>
		  </tr>
		  <tr>
		    <td>2</td>
		    <td colspan="2"> App 检查系统用户认证的 Session 是否存在，如果存在表示用户已经登录，认证结束；否则进入下一步。该部分由 APP 系统自行开发完成 </td>
		  </tr>
		  <tr>
		    <td>3</td>
		    <td width="50%"> 进入 APP 用户登录界面，界面有本地登录方式和 SSO 认证链接 (https://login.connsec.com) ；若用户使用本地认证， App 自行认证；否则点击 SSO 认证链接跳转到https://login.connsec.com </td>
		    <td> 应用直接跳转到 SSO 认证链接 (https://login.connsec.com) </td>
		  </tr>
		  <tr>
		    <td>4</td> 
		    <td colspan="2"> 进入 <strong> ” ConnSec OAuth2.0 认证流程 ” </strong> </td>
		  </tr>
		  <tr>
		    <td> 特点 </td>
		    <td> <strong>优势 </strong>： 应用具有高度独立性，不依赖认证中心，又可用使用认证中心的认证，实现单点登录；但用户需要记录两 套密码，包括原应用密码和认证中心密码 </td>
		    <td> <strong>缺点 </strong>：认证中心故障，应用无法登陆 。 </td>
		  </tr>
		 </table>
    </div>
    建议：应用本地登录作为应急方案
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
