<!DOCTYPE HTML>
<html   lang="en">
<head>
<#include  "layout/header.ftl">
<#include  "layout/common.cssjs.ftl">

	<style>
		.wrapper { position: relative; }
		i.fa { position: absolute; top: 5px; left: 5px; font-size: 22px; color: gray;}
		.wrapper input { text-indent: 20px;}
	</style>

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
		$("#mobile_j_otp_captcha_button").val("<@locale code="login.text.login.mobile.obtain.valid"/>("+captchaCount+")<@locale code="login.text.login.mobile.obtain.valid.unit"/>");
		
		
		captchaCount--;
		if(captchaCount==0){
			$("#mobile_j_otp_captcha_button").val("<@locale code="login.text.login.mobile.obtain"/>");
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
	
	<#if true==isMfa && "TOPT"==otpType>
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
		if(seconds<${otpInterval}){
			timeBaseCount=${otpInterval}-seconds;
		}else{
			timeBaseCount=${otpInterval}-(seconds-${otpInterval});
		}
		$("#tfa_j_otp_captcha_button").val("<@locale code="login.text.login.twofactor.validTime"/>("+timeBaseCount+")<@locale code="login.text.login.twofactor.validTime.unit"/>");
	};
	</#if>
	var currentSwitchTab="normalLogin";
	<#--submit form-->		
	function doLoginSubmit(){
		$.cookie("login_username", $("#"+currentSwitchTab+"Form input[name=username]").val(), { expires: 7 });
		$("#"+currentSwitchTab+"SubmitButton").click();
		$.cookie("login_switch_tab", currentSwitchTab, { expires: 7 });
	};
	
	<#--switch Login Form-->
	function switchTab(id){
		if($("#"+id+"Form  input[name=username]").val()==""){
			$("#"+id+"Form input[name=username]").focus();
		}else{
			$("#"+id+"Form  input[name=password]").focus();
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
		<#if true==isMfa && "TOPT"==otpType>
		setInterval("currentTime()", 1000);
		</#if>
	
		<#--submit loginForme-->
		$(".doLoginSubmit").on("click",function(){
				doLoginSubmit();
		});
		var cookieLoginUsername = $.cookie("login_username");
		<#--read username cookie for login e-->		
		if(cookieLoginUsername != undefined && cookieLoginUsername != ""){
			var switch_tab=$.cookie("switch_tab")==undefined?"normalLogin":$.cookie("login_switch_tab");
			$("#"+switch_tab).click();
			$("#"+switch_tab+"Form input[name=username]").val(cookieLoginUsername ==undefined ? "" : cookieLoginUsername);
			$("#div_"+switch_tab+" input[name=password]").focus();
		}else{
			$("#div_normalLogin input[name=username]").focus();
		}
		<#--resend  captchae-->	
		$("#mobile_j_otp_captcha_button").on("click",function(){	
			if(captchaCount<60){
				return;
			}
			var loginName = $("#mobile_j_username").val();
			if(loginName == ""){
				return;
			}
			$.get("<@base />/login/sendsms/"+loginName,function(data,status){
	    		//alert("Data: " + data + "\nStatus: " + status);
	  		});
			
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
	<div class="row">
		<div class="col-sm-6"></div>
        <div class="col-md-4 col-md-offset-4 col-lg-offset-4col-xl-offset-4">
            <div class="panel panel-default">
				<table border="0">
					<tr>
						<td >
							
						</td>
						<td>
				<table id="tableform">
					<tr>
						<td>
							<ul id="switch_tab" class="switch_tab" style="width: 360px;">
								<li id="normalLogin" class="switch_tab_class switch_tab_current col-md-4">
									<a href="javascript:void(0);">
										<@locale code="login.text.login.normal"/>
									</a>
								</li>
								<!--
								<li id="tfaLogin"  class="switch_tab_class col-md-4">
									<a href="javascript:void(0);">
									<@locale code="login.text.login.twofactor"/>
									</a>
								</li>-->
								<!---->
								<li id="mobileLogin"   class="switch_tab_class col-md-4">
									<a href="javascript:void(0);">
									<@locale code="login.text.login.mobile"/>
									</a>
								</li>
								<li id="qrcodelogin"   class="switch_tab_class col-md-4">
                                    <a href="javascript:void(0);">
                                    <@locale code="login.text.login.qrcode"/>
                                    </a>
                                </li>
							</ul>
						</td>
					</tr>
					<tr>
						<td>
							<div id="div_normalLogin" >
								<#include "loginnormal.ftl">
							</div>
							<div id="div_tfaLogin" >
								<#include "logintfa.ftl">
							</div>
							<div id="div_mobileLogin" >
								<#include "loginmobile.ftl">
							</div>
							<div id="div_qrcodelogin">
							     <#include "loginworkweixin.ftl"> 
							     <#-- <#include "logindingtalk.ftl">-->
							</div>
						</td>
					</tr>
					<tr>
						<td>
							
					      	<table id="otherlogins" width="100%">
					      		<tr>
					      			<td colspan="3" align="left"><@locale code="login.text.otherlogins"/>：</td>
					      		</tr>
					      		
					      		<#list ssopList as ssop>
					      			<#if (ssop_index)%4==0>
						      			<tr>
						      		</#if>
						      			<td align="center" nowrap style="height: 40px;">
								      			<a href="<@base />/logon/oauth20/authorize/${ssop.provider}"  title="${ssop.providerName}" >
								      				<img src="<@base />/static/${ssop.icon}" title="${ssop.providerName}"  style="width=:32px;height:32px;border:0;"/>
								      			</a>&nbsp;&nbsp;
						      			</td>
						      		<#if (ssop_index +1)%4==0>
						      		</tr>
						      		</#if>
						      	</#list>
						      	<#if (ssopList?size)%4!=0>
						      		</tr>
						      	</#if>
					      	</table>
						</td>
					</tr>
					<tr>
						<td id="register"><a href="<@base />/registration/forward"><div><@locale code="login.text.register"/></div></a></td>
					</tr>
				</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="col-sm-2"></div>
	</div>
</div>
<div id="footer">
	<#include "layout/footer.ftl">
</div>
</body>
</html>
