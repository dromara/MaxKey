<!DOCTYPE html>
<html>
<head>
    <title>Token-Based Single Sign-On</title>
    <#include  "authorize_common.ftl">
</head>

<body  onload="document.forms[0].submit()"  style="display:none">
<form id="tokenbasedsubmit" name="tokenbasedsubmit" action="${action}" method="post">
		<table style="width:100%">
			<tr>
				<td>token</td>
				<td><input type="text" id="tokenbased_token" name="token" value="${token}" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
			</tr>
		</table>
	</form>
</body>
</html>
