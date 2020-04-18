<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<#include  "../layout/header.ftl">
<#include  "../layout/common.cssjs.ftl">
</head>
<body  >
<div id="top">
	<#include "../layout/nologintop.ftl">
</div>
<div class="container">	
  <#if 1 == forgotType>
  	<@locale code="forgotpassword.resetpwd.notfound.prefix"/> 
  	<b>${emailMobile} </b>
  	<@locale code="forgotpassword.resetpwd.notfound.suffix"/>
  	<a href="javascript:history.go(-1);"><@locale code="forgotpassword.backstep"/></a >
  </#if>
  <#if 2 == forgotType || 3 == forgotType >

	<form action="<@base/>/forgotpassword/setpassword" method="post">
		<table  class="table table-bordered">
			<tr>
				<td><@locale code="forgotpassword.emailmobile"/>
					<input type='hidden' id="text" name="userId" value="${userId}" />
					<input type='hidden' id="text" name="forgotType" value="${forgotType}" />
					<input type='hidden' id="text" name="username" value="${username}" />
				</td>
				<td>${emailMobile}</td>
			</tr>
			<tr>
				<td><@locale code="login.password.newPassword"/></td>
				<td><input  class="form-control"   type='password' id="password" name="password"  tabindex="1"  value="" /></td>
			</tr>
			<tr>
				<td><@locale  code="login.password.confirmPassword"/></td>
				<td><input  class="form-control"   type='password' id="confirmpassword" name="confirmpassword"  tabindex="2"  value="" /></td>
			</tr>
			<tr>
				<td><@locale code="login.text.captcha"/>：</td>
				<td><input class="form-control"  type='text'  name="captcha"  tabindex="3"  value="" /></td>
					
			</tr>
			<tr>
				<td  colspan="2"><input id="registerBtn" class="button btn btn-lg btn-primary btn-block" type="submit" value="<@locale code="forgotpassword.nextstep" />"/></td>
			</tr>
			
		</table>
	</form>
	</#if> 
</div>
<div id="footer">
	<#include "../layout/footer.ftl">
</div>
</body>
</html>  