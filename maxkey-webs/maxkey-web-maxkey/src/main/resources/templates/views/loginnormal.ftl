<form id="normalLoginForm" name="normalLoginForm" action="<@base />/logon.do" method="post" class="needs-validation" novalidate>
	<input type="hidden" name="authType" value="normal"/>
	<table  class="table login_form_table">
		<tr  class="loginErrorMessage"  <#if ''==loginErrorMessage>style="display:none;"</#if>>
			<td  colspan="2" style="color:red;">
				${loginErrorMessage!}
			</td>
		</tr>
		<tr>
			<!--<td><@locale code="login.text.username"/></td>-->
			<td>
			 	<div  class="wrapper">
			 	   <div class="input-group" >
                	   <i class="fa fa-user" ></i>
					   <input required="" class="form-control" type='text' id='j_username'  name='username' value="admin" tabindex="1"  placeholder='<@locale code="login.text.username"/>' />
				    </div >
				</div >
			</td>
		</tr>
		<tr>
			<!--<td><@locale code="login.text.password"/></td>-->
			<td>
				<div  class="wrapper">
				    <div class="input-group" >
                	   <i class="fa fa-key fa-2" style="color: #FFD700;"></i>
					   <input required="" class="form-control"  type='password' id='j_password'  name='password' value="maxkey"  tabindex="2" placeholder='<@locale code="login.text.password"/>'/>
				       <i class="passwdeye fa fa-eye-slash fa-2" style="left: 270px; color: gainsboro;" refid="j_password" ></i>
				    </div >
				</div >
			</td>
		</tr>
		<#if true==isCaptcha> 
		<tr>
			<!--<td><@locale code="login.text.captcha"/></td>-->
			<td>
				<div  class="wrapper">
                	
                	<div class="input-group" >
                	   <i class="fa fa-lock fa-2" ></i>
					   <input required="" class="form-control "  type='text' id="j_captcha" name="captcha"  tabindex="3"  value="" style="float: left;"  placeholder='<@locale code="login.text.captcha"/>'/>
					   <img id="j_captchaimg" class="captcha-image" src="<@base/>/captcha?captcha=${captcha}"/>
				    </div >
				</div >
			</td>
			
		</tr>
		</#if>
		<#if true==isRemeberMe>
		<tr>
			<td colspan="2" style ="height: 20px;">
			    <div class="col-sm-6 float-left" style="line-height: 20px;">
			         <span class="form_checkbox_label">
                        <input type='checkbox' id="remeberMe" name="remeberMe"  class="checkbox"   tabindex="4"  value="remeberMe" />
                        <@locale code="login.text.remeberme"/>
                    </span>
			    </div>
			    <div class="col-sm-6  float-left"  style="line-height: 20px;">
			         <a href="<@base />/forgotpassword/forward"><@locale code="login.text.forgotpassword"/></a>
                </div>
			</td>								
		</tr>
		</#if>
		<tr   style="display:none">
			<td>sessionid：</td>
			<td><input  class="form-control"  type='text' id="j_sessionid" name="sessionId" value="${sessionid}" /></td>
			
		</tr>
		<tr >
			<td colspan="2">
			 <input type="submit" id="normalLoginSubmitButton" style="display: none;" />
			 <input  id="normalLoginSubmit" type="button"  tabindex="5"  style="width: 100%;" class="doLoginSubmit button btn btn-lg btn-primary btn-block"  value="<@locale code="login.button.login"/>"/></td>
			
		</tr>
	</table>
	<div class="clear"></div>
    </form>