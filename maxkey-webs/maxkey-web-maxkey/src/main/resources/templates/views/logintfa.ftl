<form id="tfaLoginForm" name="tfaLoginForm" action="<@base />/logon.do" method="post"  class="needs-validation" novalidate>
	<input type="hidden" name="authType" value="tfa"/>
	<table  class="login_form_table">
		<tr class="loginErrorMessage" <#if ''==loginErrorMessage>style="display:none;"</#if>>
			<td  colspan="2" style="color:red;">
				${loginErrorMessage!}
			</td>
		</tr>
		<tr>
			<!--<td><@locale code="login.text.username"/></td>-->
			<td>
				<div  class="wrapper">
					<i class="fa fa-user"></i>
					<input required="" class="form-control"  type='text' id='tfa_j_username'  name='username' value="" tabindex="1"  placeholder='<@locale code="login.text.username"/>'/>
				</div>
			</td>
		</tr>
		<tr> 
			<!--<td><@locale code="login.text.password"/></td>-->
			<td>
				<div  class="wrapper">
					<i class="fa fa-key fa-2" style="color: #FFD700;"></i>
					<input required="" class="form-control"  type='password' id='tfa_j_password'  name='password' value=""  tabindex="2"  placeholder='<@locale code="login.text.password"/>'/>
				</div>
			</td>
		</tr>
		<#if true==isMfa >
		<tr>
			<!--<td><@locale code="login.text.captcha"/></td>-->
			<td>
				<div  class="wrapper">
					<i class="fa fa-lock fa-2"></i>
					<input required="" class="form-control"  type='text' id="tfa_j_otp_captcha" name="otpCaptcha"  tabindex="3"  value=""   style="float: left;"  placeholder='<@locale code="login.text.captcha"/>'/>
					<button  class="btn  btn-outline-secondary"  id="tfa_j_otp_button"  tabindex="5"  type="button" >
					   <@locale code="login.text.login.twofactor.obtain"/>
					</button>
				</div>
			</td>
		</tr>
		</#if>
		<#if true==isRemeberMe>
		<tr> 
			<td colspan="2">
			     <div class="col-sm-6 float-left" style="line-height: 30px;">
                     <span class="form_checkbox_label">
                        <input type='checkbox' id="tfa_remeberMe" name="remeberMe"  class="checkbox"   tabindex="4"  value="remeberMe" />
                        <@locale code="login.text.remeberme"/>
                    </span>
                </div>
                <div class="col-sm-6  float-left"  style="line-height: 30px;">
                     <a href="<@base />/forgotpassword/forward"><@locale code="login.text.forgotpassword"/></a>
                </div>
			</td>								
		</tr>
		</#if>
		<tr   style="display:none">
			<td>sessionid：</td>
			<td><input class="form-control"  type='text' id="tfa_sessionid" name="sessionId" value="${sessionid}" /></td>
			
		</tr>
		<tr >
			<td colspan="2">
			 <input type="submit" id="tfaLoginSubmitButton" style="display: none;" />
			<input   id="tfaLoginSubmit" type="button" style="width: 100%;" tabindex="5" class="doLoginSubmit button btn btn-lg btn-primary btn-block"  value="<@locale code="login.button.login"/>"/></td>
			
		</tr>
	</table>
	<div class="clear"></div>
    </form>