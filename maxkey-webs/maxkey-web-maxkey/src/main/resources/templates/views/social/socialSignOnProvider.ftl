<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div  id="main"  class="container">
    <table data-toggle="table">
      <thead>
        <tr>
          <!--<th><@locale code="login.social.sortorder" /></th>-->
          <th><@locale code="login.social.icon" /></th>
          <th><@locale code="login.social.provider" /></th>
          <th><@locale code="login.social.bindtime" /></th>
          <th><@locale code="login.social.lastLoginTime" /></th>
          <th><@locale code="login.social.action" /></th>
        </tr>
      </thead>
      <tbody>
      	<#list listSocialSignOnProvider as socialSignOnProvider>
        <tr>
          <!--<td>${socialSignOnProvider.sortIndex}</td>-->
          <td><img src="<@base />/static/${socialSignOnProvider.icon}" title="${socialSignOnProvider.providerName}" width="32px;" height="32px;"/></td>
          <td>${socialSignOnProvider.providerName}</td>
          <td>${socialSignOnProvider.bindTime!}</td>
          <td>${socialSignOnProvider.lastLoginTime!}</td>
          <td>
			<#if false==socialSignOnProvider.userBind>
				<a  href="<@base/>/logon/oauth20/bind/${socialSignOnProvider.provider}"><@locale code="login.social.link" /></a>
			</#if>
			<#if true==socialSignOnProvider.userBind>
				<a  href="<@base />/logon/oauth20//unbind/${socialSignOnProvider.provider}"><@locale code="login.social.unlink" /></a>
			</#if>
		</td>
        </tr>
        </#list>
      </tbody>
    </table>
	
</div >
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
<body>
</html>