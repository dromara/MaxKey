<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml1_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-ltpa" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>LTPA介绍</h1>
    <div class="text-section">
     <p class="section">LTPA是Lightweight ThirdParty Authentication简称，轻量级第三方认证，支持在一个因特网域中的一组 Web 服务器之间使用单一登录的认证框架。</p>
     <p class="section">当服务器配置LTPA认证方式，用户通过浏览器成功登录，服务器会自动发送一个session cookie给浏览器，此cookie中包含一个加密和签名Security Token信息，应用服务器根据Security Token解析得到登录用户信息自动完成应用系统认证。</p>
    </div><!-- 一段描述结束 -->

    <h3>交互概要</h3>
 <p class="section">
 该技术的实现步骤是：
 <ol>
  <li><p class="section">一个未经身份验证的用户通过浏览器访问应用系统，应用系统跳转到认证中心。</p></li>
  <li><p class="section">认证中心完成用户登录，把Security Token发给浏览器，并跳转到应用系统。</p></li>
  <li><p class="section">应用系统解析Security Token，得出用户登录信息。</p></li>
  <li><p class="section">应用系统使用用户信息完成自身的登录。</p></li>
</ol>
</p>
<h3>令牌(Security Token)格式</h3>
<p class="section">
<pre  class="brush: jscript;">
{
	"randomId":"652ec5f5-fff2-4b8e-b88d-e7ff3a217bca",
	"uid":"29e82574-b37a-46ab-bac1-5fecbd24b24b",
	"username":"zhangs1020",
	"email":"zhangs1020@connsec.com",
	"windowsAccount":"ZHANGS1020",
	"employeeNumber":"ZHANGS1020",
	"departmentId":"1000212",
	"department":"IT信息中心",
	"displayName":"张三",
	"at":"2015-03-11T15:17:03.855Z",
	"expires":"2015-03-11T15:18:03.855Z"
}
</pre>
</p>
<p class="section">
<br>
randomId是即时生成的随机数<br>
at是当前认证的时间<br>
expires是过期的间隔<br>
其他的字段可在管理控制台配置
</p>
<h3>令牌加密</h3>
<p class="section">
加密步骤：
 <ol>
  <li><p class="section">申请公共的秘钥。</p></li>
  <li><p class="section">使用秘钥对产生的Token使用DES、DESede、AES、Blowfish进行加密，默认采用DES。</p></li>
  <li><p class="section">对加密的数据进行BASE64URL编码。</p></li>
</ol>
</p>
<p class="section">
Security Token加密结果：<br>
Y00jv2TCCuk365uB2-nDCUdboygeYFoUfETC7BNXr73dQWwFNRrfYltczDQ5iWg8NTO-GsP--VlR6L-JyNhZSg
</p>
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
