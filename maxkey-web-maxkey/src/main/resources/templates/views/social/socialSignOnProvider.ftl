<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div class="container">
<%if(WebContext.getUserInfo().getGridList()==0) {%>
<table  class="table">
	<c:forEach begin="1" end="${(fn:length(listSocialSignOnProvider)+3)/4}" var="num">
		<tr>
			<c:forEach items="${listSocialSignOnProvider}" var="socialSignOnProvider" begin="${(num-1)*4}" end="${4*num-1}">
			<td align="left" nowrap  style="width:25%">
				<c:if test="${null!=socialSignOnProvider.provider}">
	  				<table class="none"  style="width:100%;">
	  				<tr><td  style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">
	  					<img src="<s:Base />/${socialSignOnProvider.icon}" title="${socialSignOnProvider.providerName}" width="65px;" height="65px;"/>
	  				</td></tr>
	  				<tr><td  style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">${socialSignOnProvider.providerName}<div>
	  					<c:if test="${false==socialSignOnProvider.userBind}">
	  						<a  href="<s:Base/>/logon/oauth20/bind/${socialSignOnProvider.provider}"><s:Locale code="login.social.link" /></a>
						</c:if>
						<c:if test="${true==socialSignOnProvider.userBind}">
	  						<a  href="<s:Base/>/logon/oauth20//unbind/${socialSignOnProvider.provider}"><s:Locale code="login.social.unlink" /></a>
	  					</c:if>
	  				</div></td></tr>
	  				</table>
	  			</c:if>
			</td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>

<%}else{%>
<table  class="table">
	<tr>
			<td>
				<s:Locale code="login.social.icon"/>
			</td>
			<td><s:Locale code="login.social.provider"/></td>
			<td><s:Locale code="button.text.action"/></td>
		</tr>
	<c:forEach items="${listSocialSignOnProvider}" var="socialSignOnProvider">
	<c:if test="${null!=socialSignOnProvider.provider}">
		<tr>
			<td style="text-align: center;">
				<img src="<s:Base />/${socialSignOnProvider.icon}" title="${socialSignOnProvider.providerName}" width="60px;" height="64px;"/>
			</td>
			<td  style="text-align: center;">${socialSignOnProvider.providerName}</td>
			<td  style="text-align: center;">
				<c:if test="${false==socialSignOnProvider.userBind}">
 						<a  href="<s:Base/>/logon/oauth20/bind/${socialSignOnProvider.provider}"><s:Locale code="login.social.link" /></a>
				</c:if>
				<c:if test="${true==socialSignOnProvider.userBind}">
 						<a  href="<s:Base/>/logon/oauth20//unbind/${socialSignOnProvider.provider}"><s:Locale code="login.social.unlink" /></a>
 				</c:if>
			</td>
		</tr>
	</c:if>
	</c:forEach>
</table>
<%} %>
</div>
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
<body>
</html>