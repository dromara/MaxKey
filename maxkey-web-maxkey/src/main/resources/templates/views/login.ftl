<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<#include  "layout/header.ftl">
<#include  "layout/common.cssjs.ftl">
<#if true==isKerberos> 
	<@browser name="MSIE">
		<script type="text/javascript"> 
			/**configuration AD Domain Authentication**/
		    var WinNetwork = new ActiveXObject("WScript.Network");
		    var userDomains=${userDomainUrlJson};
		    for(var iter = 0; iter < userDomains.length; iter++){
		    	if(WinNetwork.UserDomain==userDomains[iter].userDomain){
		    		//alert("Kerberos redirect Uri is "+userDomains[iter].redirectUri);
		    		document.location.href=userDomains[iter].redirectUri;
		    	}
		    }
		</script>
	</@browser>
</#if>

<script type="text/javascript"> 
<#--resend captcha code Interval-->
var captchaCountTimer;
var captchaCount=60;
function getCaptchaCount(){
	$("#tfa_j_otp_captcha_button").val("<@locale code="login.text.login.twofactor.obtain.valid"/>("+captchaCount+")<@locale code="login.text.login.twofactor.obtain.valid.unit"/>");
	
	
	captchaCount--;
	if(captchaCount==0){
		$("#tfa_j_otp_captcha_button").val("<@locale code="login.text.login.twofactor.obtain"/>");
		captchaCount=60;
		clearInterval(captchaCountTimer);
	}
}
<#--current datetime-->
var currentDate= new Date('${.now}');
var fullYear=currentDate.getFullYear();
var month=currentDate.getMonth()+1;
var date=currentDate.getDate();

var hours=currentDate.getHours();
var minutes=currentDate.getMinutes();
var seconds=currentDate.getSeconds();
var strTime="";
function formatTime(){
	strTime=fullYear+"-";
	strTime+=(month<10?"0"+month:month)+"-";
	strTime+=(date<10?"0"+date:date)+" ";
	strTime+=(hours<10?"0"+hours:hours)+":";
	strTime+=(minutes<10?"0"+minutes:minutes)+":";
	strTime+=(seconds<10?"0"+seconds:seconds);
}

function currentTime(){
	seconds++;
	if(seconds>59){
		minutes++;
		seconds=0;
	}
	if(minutes>59){
		hours++;
		minutes=0;
	}
	if(hours>23){
		date++;
		hours=0;
	}
	formatTime();
	//for timebase token
	getTimeBaseCount();
	
	$("#currentTime").val(strTime);
}
<#--timeBase Token  Interval default is 30s-->
var timeBaseCount;
function getTimeBaseCount(){
	if(seconds<30){
		timeBaseCount=30-seconds;
	}else{
		timeBaseCount=30-(seconds-30);
	}
	$("#tfa_j_otp_captcha_button").val("<@locale code="login.text.login.twofactor.validTime"/>("+timeBaseCount+")<@locale code="login.text.login.twofactor.validTime.unit"/>");
};

var currentSwitchTab="div_commonLogin";
<#--submit form-->		
function doLoginSubmit(){
	if(currentSwitchTab=="div_commonLogin"){
		$.cookie("username", $("#loginForm input[name=j_username]").val(), { expires: 7 });
		$.cookie("switch_form", 1, { expires: 7 });
		$("#loginForm").submit();
	}else{
		$.cookie("username", $("#tfaLoginForm input[name=j_username]").val(), { expires: 7 });
		$.cookie("switch_form", 2, { expires: 7 });
		$("#tfaLoginForm").submit();
	}
};

<#--switch LoginForm && tfaLoginForm-->
function switchTab(id){
	if($("#"+id+" input[name=j_username]").val()==""){
		$("#"+id+" input[name=j_username]").focus();
	}else{
		$("#"+id+" input[name=j_password]").focus();
	}
	currentSwitchTab=id;
}
<#-- when press ENTER key,do form submit-->
document.onkeydown=function(event){
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode==13){ 
		doLoginSubmit();
	};
};
	
$(function(){
	setInterval("currentTime()", 1000);
	<#--on captcha image click ,new a captcha code-->
	<#if true==isCaptcha>
	$('#j_captchaimg').click(function () {//
           $(this).attr("src", "<@base />/captcha"); 
	}); 
	</#if>

	<#--submit loginForme-->
	$("#loginSubmit").on("click",function(){
		doLoginSubmit();
	});
	<#--submit tfaLoginForme-->	
	$("#tfa_loginSubmit").on("click",function(){
		doLoginSubmit();
	});
	
	<#--read username cookie for login e-->		
	if($.cookie("username")!=undefined&&$.cookie("username")!=""){
		$("input[name=j_username]").val($.cookie("username")==undefined?"":$.cookie("username"));
		$("input[name=j_password]").val("");
		var switch_tab=$.cookie("switch_tab")==undefined?1:$.cookie("switch_tab");
		if(switch_tab==2){
			switchTab("switch_tfaLogin");
		}else{
			$("#div_commonLogin input[name=j_password]").focus();
		}
			
	}else{
		$("#div_commonLogin input[name=j_username]").focus();
	}
	<#--resend  captchae-->	
	$("#tfa_j_otp_captcha_button").on("click",function(){	
		if(captchaCount<60){
			return;
		}
		<#--todo:send captcha-->
		captchaCountTimer=setInterval("getCaptchaCount()", 1000);
	});
	
});
</script>
</head>
<body  >
<div id="top">
	<#include "layout/nologintop.ftl">
</div>
<div class="container">	
	<table border="0">
		<tr>
			<td width="630px">
				
			</td>
			<td>
	<table id="tableform">
		<tr>
			<td>
				<ul id="switch_tab" class="switch_tab">
					<li id="switch_commonLogin" value="div_commonLogin" class="switch_tab_class switch_tab_current"><a href="javascript:void(0);">
						<@locale code="login.text.login.normal"/></a></li>
					<li id="switch_tfaLogin"  value="div_tfaLogin"  class="switch_tab_class"><a href="javascript:void(0);">
						<@locale code="login.text.login.twofactor"/></a></li>
				</ul>
			</td>
		</tr>
		<tr>
			<td>
				<div id="div_commonLogin" >
					<form id="loginForm" name="loginForm" action="<@base />/logon.do" method="post">
						<input type="hidden" name="j_auth_type" value="basic"/>
						<table  class="table login_form_table">
							<tr>
								<td><@locale code="login.text.username"/>：</td>
								<td><input class="form-control" type='text' id='j_username'  name='j_username' value="admin" tabindex="1"/></td>
							</tr>
							<tr>
								<td><@locale code="login.text.password"/>：</td>
								<td><input class="form-control"  type='password' id='j_password'  name='j_password' value="admin"  tabindex="2"/></td>
							</tr>
							<#if true==isCaptcha> 
							<tr>
								<td><@locale code="login.text.captcha"/>：</td>
								<td><input class="form-control"  type='text' id="j_captcha" name="j_captcha"  tabindex="3"  value="" style="float: left;"/><img id="j_captchaimg" src="<@base/>/captcha"/></td>
								
							</tr>
							</#if>
							<#if true==isRemeberMe>
							<tr>
								<td colspan="2">
									<table  style="width:100%">
										<tr>
											<td style="width:50%">
												<span class="form_checkbox_label">
													<input type='checkbox' id="remeberMe" name="j_remeberme"  class="checkbox"   tabindex="4"  value="remeberMe" />
													<@locale code="login.text.remeberme"/>
												</span>
											</td>
											<td style="width:50%"><a href="<@base />/forgotpassword/forward"><@locale code="login.text.forgotpassword"/></a></td>
										</tr>
									</table>
								</td>								
							</tr>
							</#if>
							<tr   style="display:none">
								<td>sessionid：</td>
								<td><input  class="form-control"  type='text' id="sessionid" name="j_sessionid" value="${sessionid}" /></td>
								
							</tr>
							<tr >
								<td colspan="2"><input id="loginSubmit" type="button"  tabindex="5"  style="width: 100%;" class="button btn btn-lg btn-primary btn-block"  value="<@locale code="login.button.login"/>"/></td>
								
							</tr>
						</table>
						<div class="clear"></div>
					    </form>
					</div>
					<div id="div_tfaLogin" >
					<form id="tfaLoginForm" name="tfaLoginForm" action="<@base />/logon.do" method="post">
						<input type="hidden" name="j_auth_type" value="tfa"/>
						<table  class="login_form_table">
							<tr>
								<td><@locale code="login.text.username"/>：</td>
								<td><input class="form-control"  type='text' id='tfa_j_username'  name='j_username' value="" tabindex="1"/></td>
							</tr>
							<tr> 
								<td><@locale code="login.text.password"/>：</td>
								<td><input class="form-control"  type='password' id='tfa_j_password'  name='j_password' value=""  tabindex="2" /></td>
							</tr>
							<#if true==isOneTimePwd>
							<tr>
								<td><@locale code="login.text.currenttime"/>：</td>
								<td>
									<input class="form-control"  readonly type='text' id="currentTime" name="currentTime"  tabindex="3"  value="" />
								</td>
							</tr>
							<tr>
								<td><@locale code="login.text.captcha"/>：</td>
								<td>
									<input class="form-control"  type='text' id="tfa_j_otp_captcha" name="j_otp_captcha"  tabindex="3"  value=""   style="float: left;"/>
									<input class="form-control"  id="tfa_j_otp_captcha_button" type="button"  tabindex="5" class="button"  value="获取动态验证码"/>
									
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<div id="currentTime"></div>
								</td>
							</tr>
							</#if>
							<#if true==isRemeberMe>
							<tr> 
								<td colspan="2">
									<table  style="width:100%">
										<tr>
											<td style="width:50%">
												<span class="form_checkbox_label">
													<input type='checkbox' id="tfa_remeberMe" name="j_remeberme"  class="checkbox"   tabindex="4"  value="remeberMe" />
													<@locale code="login.text.remeberme"/>
												</span>
											</td>
											<td style="width:50%"><a href="<@base />/forgotpassword/forward"><@locale code="login.text.forgotpassword"/></a></td>
										</tr>
									</table>
								</td>								
							</tr>
							</#if>
							<tr   style="display:none">
								<td>sessionid：</td>
								<td><input class="form-control"  type='text' id="tfa_sessionid" name="j_sessionid" value="${sessionid}" /></td>
								
							</tr>
							<tr >
								<td colspan="2"><input id="tfa_loginSubmit" type="button" style="width: 100%;" tabindex="5" class="button btn btn-lg btn-primary btn-block"  value="<@locale code="login.button.login"/>"/></td>
								
							</tr>
						</table>
						<div class="clear"></div>
					    </form>
					</div>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr >
						<td ><@locale code="global.change.language"/> :</td>
						<td >
							<div > 
								<a href="<@basePath />/login?language=en">
									<@locale code="global.change.language.en"/>
								</a>|
								<a href="<@basePath />/login?language=zh_CN">
									<@locale code="global.change.language.zh"/>
								</a>/ Locale : <@locale/>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							 <div>
						      	<table width="100%" frame="void" cellpadding="8px;" cellspacing="8px;">
						      		<tr>
						      			<td align="left"><@locale code="login.text.otherlogins"/>：</td>
						      		</tr>
						      		
						      		<#list ssopList as ssop>
						      			<#if (ssop_index)%3==0>
							      			<tr>
							      		</#if>
							      			<td align="right" nowrap>
									      			<a href="<@base />/logon/oauth20/authorize/${ssop.provider}"  title="${ssop.providerName}" >
									      				<img src="<@base />/static/${ssop.icon}" title="${ssop.providerName}"  style="width=:32px;height:32px;border:0;"/>
									      			</a>&nbsp;&nbsp;
							      			</td>
							      		<#if (ssop_index +1)%3==0>
							      		</tr>
							      		</#if>
							      	</#list>
							      	<#if (ssopList?size)%3!=0>
							      		</tr>
							      	</#if>
						      	</table>
					  		</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td id="register"><@locale code="login.text.register"/></td>
		</tr>
	</table>
			</td>
		</tr>
	</table>
</div>
<div id="footer">
	<#include "layout/footer.ftl">
</div>
</body>
</html>
