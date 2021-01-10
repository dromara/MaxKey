<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "layout/header.ftl"/>
	<#include  "layout/common.cssjs.ftl"/>
	<script type="text/javascript"> 
	$(function(){
		<#if true==isCaptcha>
			$('#j_captchaimg').click(function () {//
		           $(this).attr("src", "<@base />/captcha?"+(new Date()).getTime()); 
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
							<form class="form-horizontal m-t-20 needs-validation" id="loginForm" name="loginForm" action="<@base />/logon.do" method="post"  novalidate>
								<div class="form-group">
								    <span class="input-group">
                                        <div class="input-group-prepend">
                                                      <span class="input-group-text fa fa-user"></span>
                                        </div>
                                        <input id='j_username' name='username' value="admin" class="form-control" type="text" required="" placeholder="<@locale code="login.text.username"/>">
                                    </span>
									
								</div>
								<div class="form-group">
								    <span class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text fa fa-key"></span>
                                        </div>
                                        <input id='j_password' name='password' class="form-control" type="password" required="" placeholder="<@locale code="login.text.password"/>">
									</span>
								</div>
								<#if true==isCaptcha> 
								<div class="form-group">
								    <span class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text fa fa-refresh"></span>
                                        </div>
                                        <input id="j_captcha" name="captcha" class="form-control" value="" type="text" required="" placeholder="<@locale code="login.text.captcha"/>"> 
                                        <img id="j_captchaimg" src="<@base/>/captcha" />
                                    </span>
									
								</div>
								</#if>
								<div class="form-group text-center m-t-20">
									<input type="hidden" name="authType" value="basic" /> 
									<input type='hidden' id="sessionid" name="sessionId" value="${sessionid}" />
									<button id="loginSubmit" class="button btn-primary btn btn-common btn-block" type="submit">
										<@locale code="login.button.login" />
									</button>
								</div>
								<div class="form-group">
									
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<#include  "layout/footer.ftl"/>
	</div>
	<div id="preloader">
		<div class="loader" id="loader-1"></div>
	</div>
</body>
</html>
