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
<div  id="main"  class="container">	
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-8">
				<form id="actionForm" 
					action="<@base/>/signup/registeron"
					forward="<@base/>/login"
					method="post"   class="needs-validation" novalidate>
					<table  class="table table-bordered">
						<tr>
							<td><@locale code="forgotpassword.emailmobile"/></td>
							<td><input  required="" type="text" id="emailMobile" name="emailMobile" class="form-control"  title="" value=""/></td>
						</tr>
						<tr>
								<td><@locale code="login.text.captcha"/></td>
								<td><input  required="" class="form-control"  type='text' id="j_captcha" name="captcha"  tabindex="3"  value="" style="float: left;"/><img id="j_captchaimg" class="captcha-image" src="<@base/>/captcha"/></td>
								
						</tr>
						<tr>
								<td><@locale code="userinfo.displayName"/></td>
								<td><input required="" class="form-control" type='text' id='displayName'  name='displayName' tabindex="1"/></td>
						</tr>
						<tr>
								<td><@locale code="userinfo.username"/></td>
								<td><input required="" class="form-control" type='text' id='username'  name='username' tabindex="1"/></td>
						</tr>
						<tr>
							<td><@locale code="login.text.password"/></td>
							<td><input required=""  class="form-control"   type='password' id="password" name="password"  tabindex="1"  value="" /></td>
						</tr>
						<tr>
							<td><@locale  code="login.password.confirmPassword"/></td>
							<td><input  required="" class="form-control"   type='password' id="confirmpassword" name="confirmpassword"  tabindex="2"  value="" /></td>
						</tr>
						<tr>
							<td  colspan="2"><input   class="button btn btn-lg btn-primary btn-block" type="submit" value="<@locale code="login.text.register" />"/></td>
						</tr>					
					</table>
				</form>
</div>
<div class="col-md-2"></div>
</div >
</div>
<div id="footer">
	<#include "../layout/footer.ftl">
</div>
</body>
</html>