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
 	<jsp:param value="authz-TokenBased" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>TokenBased介绍</h1>
    <div class="text-section">
     <p class="section">TokenBased(基于令牌)的认证，是一种简单的令牌的认证，即认证中心和应用共享凭证或者数字证书，认证中心使用HTTP POST的方式提交令牌到应用系统，应用系统并随后进行身份验证；</p>
    </div><!-- 一段描述结束 -->

    <h3>交互概要</h3>
 <p class="section">
 该技术的实现步骤是：
 <ol>
  <li><p class="section">一个未经身份验证的用户通过浏览器访问应用系统。</p></li>
  <li><p class="section">应用系统跳转到认证中心，请求认证。</p></li>
  <li><p class="section">用户填写自己的用户名和密码，然后按下提交按钮。</p></li>
  <li><p class="section">认证中心完成用户认证，生成令牌并提交到应用系统认证地址。</p></li>
  <li><p class="section">应用系统使用共享凭证或者数字证书验证令牌，从令牌中获取用户认证信息。</p></li>
  <li><p class="section">应用系统完成系统登录。</p></li>
</ol>
</p>
<h3>令牌加密或者签名方式</h3>
<p class="section">
 <ol>
  <li><p class="section">加密方式：DES、DESede、AES、Blowfish，默认采用DES。</p></li>
  <li><p class="section">签名方式：服务端使用RSA数字证书私钥加密，客户端使用RSA数字证书公钥验证。</p></li>
</ol></p>
<h3>令牌格式</h3>
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
<h3>简单令牌</h3>
<p class="section">
认证用户名@@认证时间(UTC时间),例如：testUser@2010-01-01T01:01:01.001Z<br>
具体实现以参见<a href='http://login.connsec.com/wiki/page/authz/custom/p1.jsp'>定制认证集成</a>。
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
简单token加密结果：<br>
Y00jv2TCCuk365uB2-nDCUdboygeYFoUfETC7BNXr73dQWwFNRrfYltczDQ5iWg8NTO-GsP--VlR6L-JyNhZSg
</p>
<h3>令牌签名</h3>
<p class="section">
token的签名格式：BASE64URL(UTF8(data)).BASE64URL(UTF8(signature))，中间用"<em style='font-size: 30px;  font-style: normal;'>.</em>"分开，前半部分是数据，后半部分是签名书数据，例如：<br>
eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ<em style="font-size: 40px;  font-style: normal;">.</em>dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
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
