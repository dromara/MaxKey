<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>

<body>
	<div id="top">
		<#include  "../layout/nologintop.ftl"/>
	</div>
	<div class="container">	
		<#if 'oauth 2.0'==model.oauth_version>
			<!-- oauth 2.0 -->
		      <h2>Please Confirm OAuth 2.0</h2>
		
		      <p>You hereby authorize "${client.clientId}" to access your protected resources.</p>
		      <form id="confirmationForm" name="confirmationForm" action="<@base/>/oauth/v20/authorize" method="post">
		        <input name="user_oauth_approval" value="true" type="hidden"/>
		        	
			        <ul>
			        	<#list model.scopes as scope>
							<c:set var="approved">
								<#if scope.value> checked</#if>
							</c:set>
							<c:set var="denied">
								<#if test="${!scope.value}"> checked</#if>
							</c:set>
					        <li>
								${scope.key}: 
								<input type="radio" name="${scope.key}" value="true"${approved}>Approve</input>
								<input type="radio" name="${scope.key}" value="false"${denied}>Deny</input>
							</li> 
		        		</#list>
		       		 </ul>
		        <label><input name="authorize" value="Authorize" type="submit"/></label>
		      </form>
	    </#if>
    </div>
    <div id="footer">
		<#include  "../layout/footer.ftl"/>
	</div>
</body>
</html>
