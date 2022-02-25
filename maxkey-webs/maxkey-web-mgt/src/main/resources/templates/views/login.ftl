<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "layout/header.ftl"/>
	<#include  "layout/common.cssjs.ftl"/>
</head>
<body >
    <#include  "layout/nologintop.ftl"/>
	<div class="wrapper-page">
		<div class="container">
			<div class="row justify-content-center">
			    <div class="col-sm-4"></div>
				<div class="col-lg-4 col-md-6 col-xs-6">
					<div class="card">
                        <div class="card-header">
							&nbsp;<!--<@locale code="login.text.login.normal" />-->
						</div>
						<div class="card-body">
						    <main class ="form-signin">
							<form class="form-horizontal m-t-20 needs-validation" id="loginForm" name="loginForm" action="<@base />/logon.do" method="post"  novalidate>
							<div class="row g-4">
								<div class="">
								    <div class="input-group">
                                        <span class="input-group-text fa fa-user d-flex justify-content-center"></span>
                                        <input id='j_username' name='username' value="admin" class="form-control" type="text" required="" placeholder="<@locale code="login.text.username"/>">
                                    </div>
									
								</div>
								<div class="">
								    <div class="input-group">
                                        <span class="input-group-text fa fa-key d-flex justify-content-center"></span>
                                        <input id='j_password' name='password' class="form-control" type="password" required="" placeholder="<@locale code="login.text.password"/>">
									    <i class="passwdeye fa fa-eye-slash fa-2" style="left: 270px; color: gainsboro;" refid="j_password" ></i>
									</div>
								</div>
								<#if true==isCaptcha> 
								<div class="">
								    <div class="input-group">
                                        <span class="input-group-text fa fa-shield d-flex justify-content-center"></span>
                                        <input id="j_captcha" name="captcha" class="form-control" value="" type="text" required="" placeholder="<@locale code="login.text.captcha"/>"> 
                                        <img id="j_captchaimg" class="captcha-image" src="<@base/>/captcha?captcha=${captcha}" />
                                    </div>
									
								</div>
								</#if>
								<div class="form-group text-center m-t-20">
									<input type="hidden" name="authType" value="normal" /> 
									<input type='hidden' id="sessionid" name="sessionId" value="${sessionid}" />
									<button id="loginSubmit" class="w-100 btn btn-lg btn-primary" type="submit">
										<@locale code="login.button.login" />
									</button>
								</div>
								<#if ''!=loginErrorMessage >
								<div class="form-group">
									<div class="error" ><span>${loginErrorMessage!''}</span></div>
								</div>
								</#if>
							</div>
							</form>
							</main>
						</div>
					</div>
				</div>
				<div class="col-sm-4"></div>
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
