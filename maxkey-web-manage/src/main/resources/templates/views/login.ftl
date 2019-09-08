<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "layout/header.ftl"/>
	<#include  "layout/common.cssjs.ftl"/>
	<script type="text/javascript"> 
	$(function(){
		<#if true==isCaptcha>
			$('#j_captchaimg').click(function () {//
		           $(this).attr("src", "<@base />/captcha"); 
			}); 
		 </#if>
	});
	</script>
</head>
<body >
<#include  "layout/nologintop.ftl"/>
	<div class="wrapper-page">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-5 col-md-12 col-xs-12">
					<div class="card">
						<div class="card-header border-bottom text-center">
							<h4 class="card-title">
								<locale code="login.text.login.normal" />
							</h4>
						</div>
						<div class="card-body">
							<form class="form-horizontal m-t-20" id="loginForm" name="loginForm" action="<@base />/logon.do" method="post">
								<div class="form-group">
									<span class="input-group-addon"><i class="fa fa-user"></i></span>
									<input id='j_username' name='j_username' value="admin" class="form-control" type="text" required="" placeholder="<@locale code="login.text.username"/>">
								</div>
								<div class="form-group">
									<span class="input-group-addon"><i class="fa fa-key"></i></span>
									<input id='j_password' name='j_password' class="form-control" type="password" required=""	placeholder="<@locale code="login.text.password"/>">
								</div>
								<div class="form-group">
									<input id="j_captcha" name="j_captcha" class="form-control" value="" type="text" required="" placeholder="<@locale code="login.text.captcha"/>" style="float: left; width: 70%;"> 
									<img id="j_captchaimg" src="<@base/>/captcha" />
								</div>
								<div class="form-group text-center m-t-20">
									<input type="hidden" name="j_auth_type" value="basic" /> 
									<input type='hidden' id="sessionid" name="j_sessionid" value="${sessionid}" />
									<button id="loginSubmit" class="button btn-primary btn btn-common btn-block" type="submit">
										<@locale code="login.button.login" />
									</button>
								</div>
								<div class="form-group">
									<div class="float-right">
										Locale : <@locale/>
										<a href="<@basePath />/login?language=en">
											<@locale code="global.change.language.en" />
										</a>
										|
										<a href="<@basePath />/login?language=zh_CN">
											<@locale code="global.change.language.zh" />
										</a>
									</div>
									<div class="float-left">
										<@locale code="global.change.language" />：

									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="preloader">
		<div class="loader" id="loader-1"></div>
		<#include  "layout/footer.ftl"/>
	</div>
</body>
</html>
