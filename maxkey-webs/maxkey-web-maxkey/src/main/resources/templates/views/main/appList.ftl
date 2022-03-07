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
			<div class="col-3 appListGrid">
	  				<table>
	  					<tr><td>
	  						<a  target="_blank" title="${app.name}"
	  							<#if "SP"==app.inducer>
	  								href="${app.loginUrl}"
	  							</#if>
	  							<#if "IDP"==app.inducer>
	  								href="<@base/>/authz/${app.id}"
	  							</#if>  >
	  							<img src="${app.iconBase64}" title="${app.name}" class="appListimage"/>
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
<table  class="table appListTable table-hover">
    <thead>
    	<tr>
    		<th>
    			<@locale code="apps.number"/>
    		</th>
    		<th>
    			<@locale code="apps.icon"/>
    		</th>
    		<th ><@locale code="apps.name"/></td>
    		<th>
    			<@locale code="button.text.action"/>
    		</th>
    	</tr>
    </thead>
<#list appList as app>
	<tr class="">
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
					<img src="${app.iconBase64}" title="${app.name}" class="appListimage"/>
				</a>
		</td>
		<td style="">${app.name}</td>
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