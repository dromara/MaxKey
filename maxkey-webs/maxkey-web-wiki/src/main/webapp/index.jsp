<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String lang = "zh_CN";
if(request.getParameter("language")!=null){
	lang = request.getParameter("language");
}
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>  

<body>
<jsp:include page="/common/top.jsp"></jsp:include>

<div id="container">
	<jsp:include page="/common/left.jsp">
		<jsp:param value="home" name="pageType" />
	</jsp:include>
<% if(lang.equals("zh_CN")){%>	
	<div id="content_CN" class="content" >
	  	<h1>平台概述</h1>
	   <!--  
	    <h2>Connsec 应用集成平台是做什么的？</h2>-->
	    <div class="text-section">
		     <p class="section">ConnSec是基于SOA架构的开放的<em style="  font-weight: bold;">用户账号管理和身份认证平台</em>（Open Identity & Access Platform）。</p>
		     
		     <ol>
				  <li><p class="section">提供用户的全生命周期的管理，含了用户的创建、账号的分配和授权、用户的访问审计、身份的清除及终止、用户身份的变更等；</p></li>
				  <li><p class="section">提供基于OpenID Connect、OAuth 2.0、SAML 2.0、CAS、JWT等标准化的开放协议，实现与应用系统之间的单点登录(用户只需要登录认证中心一次就可以访问所有相互信任的应用系统，无需再次登录)。</p></li>
				  <li><p class="section">能够轻松的完成与AD域、 Kerberos/SPNEGO/NTLM、ADFS、RADIUS等登录集成。</p></li>
				  <li><p class="section">提供动态验证码和一次性口令的双因素认证。</p></li>
				  <li><p class="section">支持微信、QQ、新浪微博、Google、Facebook等社交账号的集成。</p></li>
				  <li><p class="section">提供标准的认证接口以便于其他应用集成SSO。</p></li>
				  <li><p class="section">认证中心具有平台无关性、环境多样性，支持Web、手机、移动设备等, 如Apple iOS，Andriod等，将认证能力从B/S到移动应用全面覆盖。</p></li>
				  <li><p class="section">应用集成灵活性高，提供大量的Adapter进行认证的集成，提供的SDK即可完成应用集成,。</p></li>
				  <li><p class="section">提供Connector进行组织、身份账号、用户组的集成；提供定制的开放接口便于用户自定义和扩展,。</p></li>
				  <li><p class="section">两种认证机制并存，各应用系统可保留原有认证机制，同时集成认证中心的认证；应用具有高度独立性，不依赖认证中心，又可用使用认证中心的认证，实现单点登录。</p></li>
			</ol>
	    </div><!-- 一段描述结束 -->
	    <div class="imgtxt-img">
		<h3>身份认证管理蓝图</h3>
	     <img src="<%=path %>/images/iam_blueprint.jpg" alt="身份认证管理蓝图">
		</div>
	
	    <h2>编制目的</h2>
	    <div class="text-section">
	     <p class="section">介绍ConnSec SSO认证平台，提供ConnSec SSO应用集成的技术方法，对系统集成人员进行技术指导与规范。</p>
	    </div><!-- 一段描述结束 -->
	    
	    
	        <h2>适用范围</h2>
	    <div class="text-section">
	     <p class="section">本文档是ConnSec SSO项目中，指导应用进行SSO集成。</p>
	    </div><!-- 一段描述结束 -->
	    
	       <h2>阅读对象</h2>
	    <div class="text-section">
	     <p class="section">本文档阅读对象为应用集成方项目经理、开发人员、测试人员等。</p>
	    </div><!-- 一段描述结束 -->
	 </div>
	 <!-- content_CN end -->
 
<%} else { %> 
	 <div id="content_EN" class="content" >
	  	<h1>Platform Overview</h1>
	   <!--  
	    <h2>Connsec 应用集成平台是做什么的？</h2>-->
	    <div class="text-section">
	     <p class="section">ConnSec open authentication center, the open authentication platform based on SOA framework, provides the standardized open protocol based on OAuth 2.0 and SAML 2.0, achieve the single sign on between application systems (user can access all the mutual trusted application systems with one sign on of the authentication center without login again); provides the standard authentication interface to facilitate the integration of SSO to other applications. </p>
	     <p class="section">The platform independency, environment diversity of the authentication center supports Web, cell phone, mobile devices as Apple iOS, Andriod, which enhance the authentication capacity from B/S to the full coverage of mobile application.</p>
	     <p class="section">Higher flexibility of the application integration enables the OAuth SDK provided by the authentication center to complete the application integration.</p>
	     <p class="section">The two kinds of authentication mechanism coexist, while each application system can retain the original authentication mechanism as well as integrate the authentication of the authentication center; the application possesses higher independency, it can achieve single sign on by either independent from the authentication center or utilize the authentication of the authentication center.</p>
	    </div><!-- 一段描述结束 -->
	    
	    <h2>Purpose</h2>
	    <div class="text-section">
	     <p class="section">Introduce the ConnSec SSO; provide the technical method of ConnSec SSO application integration to provide technical guidance and standard for the system application technician.</p>
	    </div><!-- 一段描述结束 -->
	    
	    
	        <h2>Scope of Application</h2>
	    <div class="text-section">
	     <p class="section">This document instructs the application to carry out SSO integration on the ConnSec SSO project.</p>
	    </div><!-- 一段描述结束 -->
	    
	       <h2>Target Reader</h2>
	    <div class="text-section">
	     <p class="section">The target reader of this document shall be the project manager, developer and testing personnel of the application integration party.</p>
	    </div><!-- 一段描述结束 -->
	    
	 </div>
	 <!-- content_EN end -->
 <%} %>
	<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
