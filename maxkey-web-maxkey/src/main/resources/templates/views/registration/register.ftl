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
	
	$('#btn_save').on('click',function(){
		if($('#emailMobile').val()==''){
		 	alert('<@locale code="forgotpassword.emailmobile"/>'+': empty');
		 	return false;
		}
		if($('#username').val()==''){
		 	alert('<@locale code="userinfo.username"/>'+': empty');
		 	return false;
		}
		if($('#displayName').val()==''){
		 	alert('<@locale code="userinfo.displayName"/>'+': empty');
		 	return false;
		}
		
		
		if($('#password').val()==''){
		 	alert('<@locale code="login.text.password"/>'+': empty');
		 	return false;
		}
		if($('#confirmpassword').val()==''){
		 	alert('<@locale code="login.password.confirmPassword"/>'+': empty');
		 	return false;
		}
		if($('#password').val()!=$('#confirmpassword').val()){
			alert('<@locale code="login.password.confirmPassword"/>'+'、'+'<@locale code="login.text.password"/>'+': error');
		 	return false;
		}
		formSubmit();
		
	})
	
	function formSubmit(){
		var uname=$('#username').val();
		var pwd=$('#password').val();
		var eMobile=$('#emailMobile').val();
		var dName=$('#displayName').val();
		$.ajax({ 
	        type:"POST", 
	        url:"<@base/>/registeron",
	        data:{
	        	username:uname,
	        	password:pwd,
	        	emailMobile:eMobile,
	        	displayName:dName
	        },
	        success:function(data){ 
	           if(data.code=='0'){ 
	               alert(data.message);
	               window.location.href="<@base/>/login";
	           }else{ 
	          	 alert(data.message);
	           } 
	        }, 
	        error:function(jqXHR){ 
	           alert("error："+ jqXHR.status); 
	        } 
		}); 
	}
	
	
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
				<form action="<@base/>/registration/registeron" method="post"   class="needs-validation" novalidate>
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
								<td><@locale code="userinfo.displayName"/>：</td>
								<td><input required="" class="form-control" type='text' id='displayName'  name='displayName' tabindex="1"/></td>
						</tr>
						<tr>
								<td><@locale code="userinfo.username"/>：</td>
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
							<td  colspan="2"><input id="btn_save"  class="button btn btn-lg btn-primary btn-block" type="button" value="<@locale code="login.text.register" />"/></td>
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