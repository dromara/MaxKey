<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Token-Based SSO Submit</title>
  <link rel="shortcut icon" type="image/x-icon" href="<@base />/static/images/favicon.ico"/>
  <link type="text/css" rel="stylesheet" href="<@base />/static/css/base.css"/>
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
