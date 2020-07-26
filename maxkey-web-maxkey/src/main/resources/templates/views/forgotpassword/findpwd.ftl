<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<#include  "../layout/header.ftl">
<#include  "../layout/common.cssjs.ftl">
<script type="text/javascript"> 
$(function(){

	$('#j_captchaimg').click(function () {//
           $(this).attr("src", "<@base />/captcha"); 
	}); 
});
</script>
</head>
<body  >
<div id="top">
	<#include "../layout/nologintop.ftl">
</div>
<div class="container">	
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-8">
				<form action="<@base/>/forgotpassword/emailmobile" method="post"   class="needs-validation" novalidate>
					<table  class="table table-bordered">
						<tr>
							<td><@locale code="forgotpassword.emailmobile"/></td>
							<td><input  required="" type="text" id="emailMobile" name="emailMobile" class="form-control"  title="" value=""/></td>
						</tr>
						<tr>
								<td><@locale code="login.text.captcha"/>：</td>
								<td><input  required="" class="form-control"  type='text' id="j_captcha" name="captcha"  tabindex="3"  value="" style="float: left;"/><img id="j_captchaimg" src="<@base/>/captcha"/></td>
								
						</tr>
						<tr>
							<td  colspan="2"><input id="forgotpwdBtn"  class="button btn btn-lg btn-primary btn-block" type="submit" value="<@locale code="forgotpassword.nextstep" />"/></td>
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