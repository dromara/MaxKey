<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">


	</script>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
</head>
<body>
	 <!-- content -->  
	<form id="actionForm" name="credentialsubmit" action="<@base/>/appUserConfig" method="post" class="needs-validation" novalidate>
		<input type="hidden" id="protocol" name="protocol" value="${protocol}" />
		<input type="hidden" id="credential" name="credential" value="${credential}" />
		<table  class="table table-bordered">
			
			<tr <#if false==username>style="display:none"</#if>>
				<td><@locale code="account.relatedUsername" /></td>
				<td><input  class="form-control"  type="text" id="identity_username" name="identity_username" value="${identity_username!}"  required="" /></td>
			</tr>
			<tr <#if false==password> style="display:none"</#if> >
				<td><@locale code="account.relatedPassword" /></td>
				<td><input  class="form-control"  type="password" id="identity_password" name="identity_password" value="${identity_password!}"  required=""/></td>
			</tr>
			
			<tr style="display:none">
				<td>uid</td>
				<td><input  class="form-control"  type="text" id="userId" name="userId" value="${userId}"  /></td>
			</tr>
			<tr style="display:none">
				<td>appId</td>
				<td><input  class="form-control"  type="text" id="appId" name="appId" value="${appId}" /></td>
			</tr>
			<tr>
				<td colspan="2"><input  class="button btn btn-primary mr-3"  type="submit" style="width: 400px" id="credentialsubmitbutton"value="<@locale code="button.text.save" />"/></td>
			</tr>
		</table>
	</form> 
</body>
</html>