<!DOCTYPE html>
<html>
<head>
  	<#include  "authorize_common.ftl">
  	<#include  "../layout/common.cssjs.ftl">
  	<title>Single Sign-On Credential Initialize</title>
</head>

<body>
	<form id="credentialsubmit" name="credentialsubmit" action="<@base/>/authz/credential" method="post">
		<table  style="margin: auto;width:50%"  class="table table-bordered">
			<tr>
                <td><@locale code="account.appName" /></td>
                <td>${appName}</td>
            </tr>
			<tr>
				<td><@locale code="account.relatedUsername" /></td>
				<td><input required="" class="form-control" type='text'  id="identity_username" name="identity_username" value="" /></td>
			</tr>
			<tr>
				<td><@locale code="account.relatedPassword" /></td>
				<td><input required="" class="form-control"  type="password" id="identity_password" name="identity_password" value="" /></td>
			</tr>
			<tr  style="display:none">
				<td>userId</td>
				<td><input type="text" id="userId" name="userId" value="${userId}" /></td>
			</tr>
			<tr  style="display:none">
				<td>appId</td>
				<td><input type="text" id="appId" name="appId" value="${appId}" /></td>
			</tr>
			<tr style="display:none">
				<td>redirect uri</td>
				<td><input type="text" id="redirect_uri" name="redirect_uri" value="${redirect_uri}" /></td>
			</tr>
			<tr>
				<td colspan="2"><input class="button btn btn-primary mr-3" type="submit" style="width: 200px" id="credentialsubmitbutton"value="<@locale code="button.text.save" />"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
