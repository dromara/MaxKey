<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div class="container">
	<div class="row">
		<div class="col-md-12">
				<@locale code="login.totp.title" />
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
				<img id="captchaimg" src="<@base/>/image/${id}" /><br>
				支持<b>Google Authenticator等</b>
		</div>
		<div class="col-md-6">
			<table  class="table table-bordered"   >
				<tr>
					<th style="width:30%;"><@locale code="userinfo.displayName" /> :</th>
					<td>
						<input readonly type="text" class="required form-control" title="" value="${userInfo.displayName}"/>
						
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.username" /> :</th>
					<td>
						<input readonly type="text" class="required form-control" title="" value="${userInfo.username}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="login.totp.sharedSecret" />(BASE32) :</th>
					<td>
					<input readonly type="text" class="required form-control" title="" value="${sharedSecret}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="login.totp.sharedSecret" />(HEX) :</th>
					<td>
						<input readonly type="text" class="required form-control" title="" value="${hexSharedSecret}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="login.totp.period" />:</th>
					<td>
						<input readonly type="text" class="required form-control" title="" value="${format.period}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="login.totp.digits" />:</th>
					<td>
						<input readonly type="text" class="required form-control" title="" value="${format.digits}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="login.totp.crypto" />:</th>
					<td>
						<input readonly type="text" class="required form-control" title="" value="${format.crypto}"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2"  class="center">
			    		<input class="button forward btn  btn-primary" style="width:100px" wurl="<@base/>/safe/otp/gen/timebased"  type="button"    id="forward" value="<@locale code="login.totp.generate" />"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="footer">
		<#include   "../layout/footer.ftl"/>
	</div>
<body>
</html>