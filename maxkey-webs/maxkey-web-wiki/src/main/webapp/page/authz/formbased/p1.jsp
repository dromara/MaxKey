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
 	<jsp:param value="authz-formbased" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>FormBased介绍</h1>
    <div class="text-section">
     <p class="section">HTTP+HTML FormBased(基于表单)的认证，目前一般简单的基于表单的认证，是一种登录技术，即一个网站使用一个Web表单收集，并随后进行身份验证；认证的凭证信息来源于用户代理，通常web浏览器。 （请注意，短语“基于表单的认证”是不明确的。请参阅进一步解释基于表单的认证。）</p>
    </div><!-- 一段描述结束 -->

    <h3>交互概要</h3>
 <p class="section">
 该技术的实现步骤是：
 <ol>
  <li><p class="section">一个未经身份验证的用户代理通过HTTP协议从网站请求一个网页。</p></li>
  <li><p class="section">该网站返回一个HTML网页的未经验证的用户代理。该网页包含提示用户为他们的用户名和密码，以及标有“登录”或“提交”按钮基于HTML的Web表单最低限度。</p></li>
  <li><p class="section">用户填写自己的用户名和密码，然后按下提交按钮。</p></li>
  <li><p class="section">所述用户代理发送的web表单数据（包括用户名和密码）到Web服务器。</p></li>
  <li><p class="section">网站实现中，Web服务器上运行时，执行对网络的形式的数据部分的验证和确认操作。如果成功，该网站考虑用户代理进行认证。</p></li>
</ol>
</p>
<h3>采纳建议</h3>
<p class="section">
HTTP + HTML基于表单的认证，可以说是万维网上采用当今最流行的用户认证技术。几乎所有维基，论坛，银行/财经网站，电子商务网站，网络搜索引擎，门户网站，和其他常见的Web服务器应用程序都选择了这种认证技术。
</p>
<p class="section">
这种普及显然是由于网站管理员或他们的雇主想要细粒度地控制征求用户凭据的表现和行为，而默认弹出对话框（用于HTTP基本访问身份验证或摘要接入认证），许多Web浏览器提供不允许精确的剪裁。所需的精确度可以通过公司的要求（如品牌）或实施问题的动机（如网站之类的软件对于MediaWiki，phpBB的，Drupal的，WordPress的默认配置）。无论理由，任何企业品牌或用户体验的调整不能从这个认证过程的几个安全考虑分散。
</p>
<h3>安全方面注意事项</h3>
 <ol>
  <li><p class="section">用户凭据传递了密文到web网站，除非采取诸如就业传输层安全（TLS）的监听。</p></li>
  <li><p class="section">该技术基本上是特设在于有效地没有任何用户代理和所述网络服务器之间的交互，除HTTP之外的与HTML本身是标准化。通过该网站所使用的实际的认证机制是，默认，未知的用户和用户代理。形式本身，包括可编辑字段的数量，和期望的内容物，完全实现和部署相关的。</p></li>
  <li><p class="section">这种技术本身临时的，否则犯罪分子极易伪装成可信任方在认证过程中。</p></li>
</ol>
<h3>代码实现</h3>
<pre  class="brush: html;">
&lt;form method="post" action="/login"&gt;
  &lt;input type="text" name="username" required&gt;
  &lt;input type="password" name="password" required&gt;
  &lt;input type="submit" value="Login"&gt;
&lt;/form&gt;
</pre>
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
