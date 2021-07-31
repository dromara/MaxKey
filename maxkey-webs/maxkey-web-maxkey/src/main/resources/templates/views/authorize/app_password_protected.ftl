<!DOCTYPE html>
<html >
<head>
    <#include  "authorize_common.ftl">
</head>

<body>
	<form id="apppasswodprotectedsubmit" name="apppasswodprotectedsubmit" action="<@base />/authz/protected" method="post">
		<table  style="margin: auto;width:50%">
			<tr>
				<td><@locale code="userinfo.appLoginPassword" /></td>
				<td><input type="password" id="password" name="password" value="" /></td>
			</tr>

			<tr style="display:none">
				<td>redirect uri</td>
				<td><input type="text" id="redirect_uri" name="redirect_uri" value="${redirect_uri}" /></td>
			</tr>
			<tr>
				<td colspan="2"><input class="button" type="submit" style="width: 100px" id="apppasswodprotectedsubmitbutton"value="<s:Locale code="button.text.continue" />"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
