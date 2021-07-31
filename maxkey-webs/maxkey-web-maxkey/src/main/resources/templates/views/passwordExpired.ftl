<!DOCTYPE HTML >
<html>
	<head>
		<#include  "layout/header.ftl"/>
		<#include  "layout/common.cssjs.ftl"/>
      	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
      	<title><@locale code="login.password.expired" /></title>
  </head>
<body>
<div id="top">
	<#include "layout/nologintop.ftl"/>
</div>
<div id="content">
<div class="container">
<table width="100%">
  <tr>
    <td>
	<div>
<form   method="post" type="label" autoclose="true"  action="<@base/>/safe/changeExpiredPassword"> 
	
	  <table  class="table table-bordered"  >
			<tbody>
			<tr>
				<th  colspan="2">
					<@locale code="login.password.expired.tip" />
				</th>
			</tr>
			<tr <#if ''==errorMessage>style="display:none;"</#if>>
				<th  colspan="2" style="color:red;">
					${errorMessage!}
				</th>
			</tr>
			<tr>
				<th><@locale code="userinfo.displayName" /> :</th>
				<td>
					<input readonly type="text" id="displayName" name="displayName" class="form-control" title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" /> :</th>
				<td>
					<input readonly type="text" id="username" name="username" class="form-control" title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.newPassword" />:</th>
				<td>
					<input type="password" id="newPassword" name="newPassword" class="form-control" title="" value=""  required="" />
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.confirmPassword" />:</th>
				<td nowrap>
					<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" title="" value=""  required="" />
				</td>
			</tr>
			<tr>
				<td colspan="2" class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button btn btn-lg btn-primary" style="width:100px"  type="submit"    id="submitBtn" value="<@locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</div>
</td>
  </tr>
</table>
</div>
</div>
<div id="footer">
	<#include  "layout/footer.ftl"/>
</div>
</body>
</html>
