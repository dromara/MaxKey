<%@ page   contentType="text/html; charset=UTF-8" language="java"  %>
<%@ page   import="org.maxkey.domain.*"%> 
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"  	uri="http://sso.maxkey.org/tags" %>
<div class="header-container">
	<div class="nav-logo">
		<a href="<s:Base/>/main"> 
			<IMG SRC="<s:Base/>/images/logo.jpg" alt="" style="width:40px;heigth:40px">	
		</a>
		<span class="logo">
		
		</span>
	</div>
	<ul class="nav-left"  style="letter-spacing:2px;font-size:20px;font-weight:bolder;margin-top: 14px;">
		<s:Locale code="global.application"/>
	</ul>
	<ul class="nav-right">
		<li style="font-size: 18px; margin-top: 10px;">
				<s:Locale code="global.text.welcome"/>：<b><%=WebContext.getUserInfo()==null?"":WebContext.getUserInfo().getDisplayName()%>(<%=WebContext.getUserInfo()==null?"":WebContext.getUserInfo().getUsername()%>)&nbsp;&nbsp;</b>
		 </li>
		<li class="scale-left">
			<a class="sidenav-fold-toggler" href="javascript:void(0);"> 
				<img  src="<s:Base/>/images/menu-left.png" alt="" style="width: 30px; height: 40px; padding-top: 10px;">
			</a>
		</li>
		<li class="scale-left">
			 &nbsp;
		</li>
		<li class="scale-left"> 
			<a  href="<s:Base/>/logout?reLoginUrl=login">
				<IMG SRC="<s:Base/>/images/exit4.png" alt="Exit" style="width: 40px; height: 45px; padding-top: 8px;">	
			</a>
		</li>
	</ul>
</div>