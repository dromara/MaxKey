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
 	<jsp:param value="authz-cas" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>CAS介绍</h1>
    <div class="text-section">
     <p class="section">CAS的全称是 Central Authentication Service;CAS 是 Yale 大学发起的一个开源项目，旨在为 Web 应用系统提供一种可靠的单点登录方法，CAS 在 2004 年 12 月正式成为 JA-SIG 的一个项目。</p>
    <p class="section">
  		CAS的目标是允许用户访问多个应用程序只提供一次用户凭据（如用户名和密码）。它还允许Web应用程序对用户进行身份验证，而不必获取用户的安全凭证，比如密码。
  	</p>
    </div><!-- 一段描述结束 -->

    <h3>CAS优点</h3>
 <p class="section">
 CAS支持企业级的单点登录，具有以下优势：
 <ol>
  <li><p class="section">一个开放的，文档齐全的协议。</p></li>
  <li><p class="section">开源的JAVA服务器组件。</p></li>
  <li><p class="section">CAS Client 支持非常多的客户端(这里指单点登录系统中的各个 Web 应用)，包括 Java, .Net, PHP, Perl, Apache, uPortal, Ruby 等。</p></li>
  <li><p class="section">与uPortal, BlueSocket, TikiWiki, Mule, Liferay, Moodle等等能很好集成。</p></li>
  <li><p class="section">文档社区化和实现的支持。</p></li>
  <li><p class="section">具有广泛的客户群的支持。</p></li>
</ol>
</p>
<h3>CAS原理和协议</h3>
<p class="section">
从结构上看，CAS 包含两个部分： CAS Server 和 CAS Client。CAS Server 需要独立部署，主要负责对用户的认证工作；CAS Client 负责处理对客户端受保护资源的访问请求，需要登录时，重定向到 CAS Server。图1 是 CAS 最基本的协议过程：
</p>
 CAS 基础协议：
<div class="imgtxt-img">

 <img src="<%=path %>/images/cas/cas1.jpg" alt="CAS集成协议">
</div>
<p class="section">
CAS Client 与受保护的客户端应用部署在一起，以 Filter 方式保护受保护的资源。对于访问受保护资源的每个 Web 请求，CAS Client 会分析该请求的 Http 请求中是否包含 Service Ticket，如果没有，则说明当前用户尚未登录，于是将请求重定向到指定好的 CAS Server 登录地址，并传递 Service （也就是要访问的目的资源地址），以便登录成功过后转回该地址。用户在第 3 步中输入认证信息，如果登录成功，CAS Server 随机产生一个相当长度、唯一、不可伪造的 Service Ticket，并缓存以待将来验证，之后系统自动重定向到 Service 所在地址，并为客户端浏览器设置一个 Ticket Granted Cookie（TGC），CAS Client 在拿到 Service 和新产生的 Ticket 过后，在第 5，6 步中与 CAS Server 进行身份合适，以确保 Service Ticket 的合法性。
</p>
<p class="section">
在该协议中，所有与 CAS 的交互均采用 SSL 协议，确保，ST 和 TGC 的安全性。协议工作过程中会有 2 次重定向的过程，但是 CAS Client 与 CAS Server 之间进行 Ticket 验证的过程对于用户是透明的。
</p>
<p class="section">
另外，CAS 协议中还提供了 Proxy （代理）模式，以适应更加高级、复杂的应用场景，具体介绍可以参考 CAS 官方网站上的相关文档。
</p>

<h3>代码实现</h3>
<pre><code class="html,xml">
&lt;form method="post" action="/login"&gt;
  &lt;input type="text" name="username" required&gt;
  &lt;input type="password" name="password" required&gt;
  &lt;input type="submit" value="Login"&gt;
&lt;/form&gt;
</code></pre>

<h5>Tips：</h5>
<p>
	如果您想对CAS进行扩展阅读，请参看：官方技术说明<a href="https://www.apereo.org/cas"  title="https://www.apereo.org/cas" target="_blank" rel="nofollow">CAS官方网站（en） </a> | <a href="http://en.wikipedia.org/wiki/Central_Authentication_Service"  title="http://en.wikipedia.org/wiki/Central_Authentication_Service" target="_blank" rel="nofollow">CAS维基百科（en）</a> 
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
