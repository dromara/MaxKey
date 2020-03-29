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
<#if  Session["current_user"].gridList==0 >
<table  class="table">
	<#list appList as app>
		<#if (app_index)%4==0>
			<tr>
		</#if>
			<td align="left" nowrap style="width:20%">
	  				<table  class="none" style="width:100%; border-spacing: 0;border-collapse: collapse;">
	  				<tr><td style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">
	  					<#if "Desktop"==app.protocol>
	  						<a href="javascript:void(0);" title="${app.name}" 
	  						onclick="window.open('<@base/>/authz/${app.id}');">
	  							<img src="<@base/>/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
	  						</a>
	  					</#if>
	  					<#if "Desktop"!=app.protocol>
	  						<a href="<@base/>/authz/${app.id}" target="_blank" title="${app.name}" >
	  							<img src="<@base/>/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
	  						</a>
	  					</#if>
	  				</td></tr>
	  				<tr><td style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">${app.name}</td></tr>
	  				</table>
			</td>
		<#if (app_index +1)%4==0>
		</tr>
		</#if>
	</#list>
  	<#if (appList?size)%4!=0>
  		</tr>
  	</#if>
</table>
<#else>

<table  class="table">
	<tr>
		<td>
			<@locale code="apps.number"/>
		</td>
		<td>
			<@locale code="apps.icon"/>
		</td>
		<td><@locale code="apps.name"/></td>
		<!--<td><@locale code="apps.protocol"/></td>-->
		<td><@locale code="apps.category"/></td>
		<td>
			<@locale code="button.text.action"/>
		</td>
	</tr>
<#list appList as app>
	<tr>
		<td>${app_index +1 }</td>
		<td>
			<#if app.protocol?contains("Desktop")>
				<a href="javascript:void(0);"  title="${app.name}" 
				onclick="window.open('<@base/>/authz/${app.id}');">
					<img src="<@base/>/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
				</a>
			<#else>
				<a href="<@base/>/authz/${app.id}" target="_blank" title="${app.name}" >
					<img src="<@base/>/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
				</a>
			</#if>
		</td>
		<td>${app.name}</td>
		<!--<td>${app.protocol}</td>-->
		<td>${app.category}</td>
		<td>
			<#if app.protocol ?contains("Desktop")>
				<a href="javascript:void(0);" title="${app.name}" 
				onclick="window.open('<@base/>/authz/${app.id}');">
					<@locale code="button.text.visit"/>
				</a>
			<#else>
				<a href="<@base/>/authz/${app.id}" target="_blank" title="${app.name}" >
					<@locale code="button.text.visit"/>
				</a>
			</#if>
		</td>
	</tr>
</#list>
</table>
</#if>
</div>
<div id="footer">
	<#include  "../layout/footer.ftl"/>
</div>
<body>
</html>