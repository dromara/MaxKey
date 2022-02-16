<!DOCTYPE HTML>
<html   lang="en">
<head>
<#include  "layout/header.ftl">
<#include  "layout/common.cssjs.ftl">
    <script src ="<@base />/static/javascript/login.js" type="text/javascript" ></script>

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
</head>
<body  >
<div id="top">
	<#include "layout/nologintop.ftl">
</div>
<div id="main" class="container">	
	<div class="row">
		<div class="d-none d-sm-block   col-sm-6 col-md-6">

        </div>
        <div class="col-md-4 col-md-offset-4 col-lg-offset-4col-xl-offset-4">
            <div class="panel panel-default">
				<table border="0">
					<tr>
						<td >
							
						</td>
						<td>
				<table class="logintableform">
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
								<!---->
								<li id="qrcodelogin"   class="switch_tab_class col-md-4">
                                    <a href="javascript:void(0);">
                                    <i class="fa fa-qrcode" aria-hidden="true"></i>
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
							     <#if sspLogin.workWeixinLogin != 'none'>
							         <#include "loginscanworkweixin.ftl"> 
							     </#if>  
							     <#if sspLogin.dingTalkLogin != 'none'>  
							         <#include "loginscandingtalk.ftl">
							     </#if>  
							     <#if sspLogin.feiShuLogin != 'none'>  
                                     <#include "loginscanfeishu.ftl">
                                 </#if>  
                                 <#if sspLogin.weLinkLogin != 'none'>  
                                     <#include "loginscanwelink.ftl">
                                 </#if>  
							</div>
						</td>
					</tr>
					<tr>
						<td>
							
					      	<table id="otherlogins" width="100%">
					      	    <!--
					      		<tr>
					      			<td colspan="3" align="left"><@locale code="login.text.otherlogins"/>：</td>
					      		</tr>
					      		 -->
					      		<#list sspLogin.socialSignOnProviders as ssop>
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
						      	<#if (sspLogin.socialSignOnProviders?size)%4!=0>
						      		</tr>
						      	</#if>
					      	</table>
						</td>
					</tr>
					<tr>
						<td id="register"><a href="<@base />/signup/forward"><div><@locale code="login.text.register"/></div></a></td>
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
