<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	
	<#if noticesVisible >
	<!--notices -->
	<script>
		window.open('<@base/>/lastedNotices','<@locale code="home.notices"/>','width=300,height=300');
	</script>
	<!--notices end-->
	</#if>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div  id="main"  class="container">
<#if  Session["current_user"].gridList==0 >
	<#list appList as app>
		<#if (app_index)%4==0>
		<div class="row">
		</#if>
			<div class="col-3" style="min-width: 160px;">
	  				<table  class="none" style="width: 100%; min-height: 120px;border-spacing: 0;border-collapse: collapse;">
	  					<tr><td style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">
	  						<a  target="_blank" title="${app.name}"
	  							<#if "SP"==app.inducer>
	  								href="${app.loginUrl}"
	  							</#if>
	  							<#if "IDP"==app.inducer>
	  								href="<@base/>/authz/${app.id}"
	  							</#if>  >
	  							<img src="<@base/>/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
	  						</a><br>
	  						${app.name}
	  					</td></tr>
	  				</table>
	  		</div>
		<#if (app_index +1)%4==0>
		</div>
		</#if>
	</#list>
  	<#if (appList?size)%4!=0>
  		</div >
  	</#if>

<#else>
<div class="row">
<div class="col-12">
<table  class="table">
	<tr>
		<td>
			<@locale code="apps.number"/>
		</td>
		<td>
			<@locale code="apps.icon"/>
		</td>
		<td style="word-wrap:break-word;"><@locale code="apps.name"/></td>
		<td>
			<@locale code="button.text.action"/>
		</td>
	</tr>
<#list appList as app>
	<tr>
		<td>${app_index +1 }</td>
		<td>

				<a 
					<#if "SP"==app.inducer>
						href="${app.loginUrl}"
					</#if>
					<#if "IDP"==app.inducer>
						href="<@base/>/authz/${app.id}"
					</#if>
					 target="_blank" title="${app.name}" >
					<img src="<@base/>/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
				</a>
		</td>
		<td style="word-wrap:break-word;vertical-align: middle;">${app.name}</td>
		<td>
			<a 
				<#if "SP"==app.inducer>
					href="${app.loginUrl}"
				</#if>
				<#if "IDP"==app.inducer>
					href="<@base/>/authz/${app.id}"
				</#if>
				 target="_blank" title="${app.name}" >
				<@locale code="button.text.visit"/>
			</a>
		</td>
	</tr>
</#list>
</table>
</div>
</#if>
</div>

</div>
<div id="footer">
	<#include  "../layout/footer.ftl"/>
</div>
<body>
</html>