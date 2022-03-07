<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">	
	$(function () {
		$(".configUser").on("click",function(){
			$.window({url:$(this).attr("url"),width:480,height:200});
		});
			
		$(".configProtected").on("click",function(){
			$.window({url:$(this).attr("url"),width:480,height:200});
		});
	});
	</script>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div  id="main"  class="container">
<table  class="table">
	<tr>
		<td>
			<@locale code="apps.number"/>
		</td>
		<td>
			<@locale code="apps.icon"/>
		</td>
		<td><@locale code="apps.name"/></td>
		<td><@locale code="apps.account"/></td>
	</tr>
	<#list appList as app>
	<tr>
		<td>${app_index +1 }</td>
		<td>
			<img src="${app.iconBase64}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
		</td>
		<td>${app.name}</td>
		<td>
			<#if 3==app.credential>
			
			<input class="configUser button btn btn-primary mr-2"  type="button" 
  						value="<@locale code="apps.account" />" 
			 		    url="<@base/>/forward/appUserConfig/${app.protocol}/${app.credential}/${app.id}"
			 		    target="window">
			 </#if>
		</td>
	</tr>
	</#list>
</table>
</div>
<div id="footer">
	<#include  "../layout/footer.ftl"/>
</div>
<body>
</html>