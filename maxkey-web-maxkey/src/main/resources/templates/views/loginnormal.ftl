<form id="normalLoginForm" name="normalLoginForm" action="<@base />/logon.do" method="post" class="needs-validation" novalidate>
	<input type="hidden" name="authType" value="normal"/>
	<table  class="table login_form_table">
		<tr  class="loginErrorMessage"  <#if ''==loginErrorMessage>style="display:none;"</#if>>
			<td  colspan="2" style="color:red;">
				${loginErrorMessage!}
			</td>
		</tr>
		<tr>
			<td><@locale code="login.text.username"/>：</td>
			<td>
			 	<div  class="wrapper">
                	<i class="fa fa-user"></i>
					<input required="" class="form-control" type='text' id='j_username'  name='username' value="admin" tabindex="1"/>
				</div >
			</td>
		</tr>
		<tr>
			<td><@locale code="login.text.password"/>：</td>
			<td>
				<div  class="wrapper">
                	<i class="fa fa-key fa-2" style="color: #FFD700;"></i>
					<input required="" class="form-control"  type='password' id='j_password'  name='password' value="maxkey"  tabindex="2"/>
				</div >
			</td>
		</tr>
		<#if true==isCaptcha> 
		<tr>
			<td><@locale code="login.text.captcha"/>：</td>
			<td>
				<div  class="wrapper">
                	<i class="fa fa-lock fa-2"></i>
					<input required="" class="form-control "  type='text' id="j_captcha" name="captcha"  tabindex="3"  value="" style="float: left;"/><img id="j_captchaimg" class="captcha-image" src="<@base/>/captcha"/>
				</div >
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
								<input type='checkbox' id="remeberMe" name="remeberMe"  class="checkbox"   tabindex="4"  value="remeberMe" />
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