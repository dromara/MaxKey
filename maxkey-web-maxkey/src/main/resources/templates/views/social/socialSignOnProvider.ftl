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

	<#list listSocialSignOnProvider as socialSignOnProvider>
		<#if (socialSignOnProvider_index)%4==0>
			<div class="row">
		</#if>
			<div class="col-3" style="min-width: 170px;">
				<#if socialSignOnProvider.provider?default("")!="">
	  				<table class="none"  style="min-width: 170px; min-height: 150px;">
	  				<tr><td  style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">
	  					<img src="<@base />/static/${socialSignOnProvider.icon}" title="${socialSignOnProvider.providerName}" width="65px;" height="65px;"/>
	  				</td></tr>
	  				<tr><td  style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">${socialSignOnProvider.providerName}<div>
	  					<#if false==socialSignOnProvider.userBind>
	  						<a  href="<@base/>/logon/oauth20/bind/${socialSignOnProvider.provider}"><@locale code="login.social.link" /></a>
						</#if>
						<#if true==socialSignOnProvider.userBind>
	  						<a  href="<@base />/logon/oauth20//unbind/${socialSignOnProvider.provider}"><@locale code="login.social.unlink" /></a>
	  					</#if>
	  				</div></td></tr>
	  				</table>
	  			</#if>
			</div>
		<#if (socialSignOnProvider_index +1)%4==0>
			</div>
		</#if>
	</#list>
</div >
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
<body>
</html>