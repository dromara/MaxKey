<!DOCTYPE html>
<html >
<head>
    <title>Refuse To Access</title>
    <#include  "authorize_common.ftl">
</head>

<body>
<center>
    <form id="refuse_form" name="refuse_form" action="" method="get">
    
		<table style="width:400px">
			<tr>
				<td colspan='2'><@locale code="login.authz.refuse" /></td>
			</tr>
			<tr>
				<td><img src="${model.iconBase64!}"/></td><td>${model.appName!}</td>
			</tr>
			<tr style="display:none">
				<td>${model.id!}</td>
			</tr>
		</table>
	</form>
</center>
</body>
</html>
