<%@ page   language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="s" 			uri="http://sso.maxkey.org/tags" %> 
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<jsp:include page="layout/header.jsp"></jsp:include>
	<jsp:include page="layout/common.cssjs.jsp"></jsp:include>
	<script type="text/javascript"> 
	$(function(){
		<c:if test="${'true'==isCaptcha}">
			$('#j_captchaimg').click(function () {//
		           $(this).attr("src", "<s:Base />/captcha"); 
			}); 
		 </c:if>
	});
	</script>
</head>
<body >
	<div class="wrapper-page">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-5 col-md-12 col-xs-12">
					<div class="card">
						<div class="card-header border-bottom text-center">
							<h4 class="card-title">
								<s:Locale code="login.text.login.header" />
							</h4>
						</div>
						<div class="card-body">
							<form class="form-horizontal m-t-20" id="loginForm" name="loginForm" action="<s:Base />/logon.do" method="post">
								<div class="form-group">
									<span class="input-group-addon"><i class="fa fa-user"></i></span>
									<input id='j_username' name='j_username' value="admin" class="form-control" type="text" required="" placeholder="<s:Locale code="login.text.username"/>">
								</div>
								<div class="form-group">
									<span class="input-group-addon"><i class="fa fa-key"></i></span>
									<input id='j_password' name='j_password' class="form-control" type="password" required=""	placeholder="<s:Locale code="login.text.password"/>">
								</div>
								<div class="form-group">
									<input id="j_captcha" name="j_captcha" class="form-control" type="text" required="" placeholder="<s:Locale code="login.text.captcha"/>" style="float: left; width: 70%;"> 
									<img id="j_captchaimg" src="<c:url value="/captcha"/>" />
								</div>
								<div class="form-group text-center m-t-20">
									<input type="hidden" name="j_auth_type" value="basic" /> 
									<input type='hidden' id="sessionid" name="j_sessionid" value="${sessionid}" />
									<button id="loginSubmit" class="button btn-primary btn btn-common btn-block" type="submit">
										<s:Locale code="login.button.login" />
									</button>
								</div>
								<div class="form-group">
									<div class="float-right">
										Locale : ${pageContext.response.locale} 
										<a href="<s:BasePath />/login?language=en">
											<s:Locale code="global.change.language.en" />
										</a>
										|
										<a href="<s:BasePath />/login?language=zh_CN">
											<s:Locale code="global.change.language.zh" />
										</a>
									</div>
									<div class="float-left">
										<s:Locale code="global.change.language" />：

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
	</div>
</body>
</html>
