
<div id="nav_primary" >
<div  class="container row">
	<div class="col-sm-8">
		<ul class="navMenu">
			<li  id="nav_primay_11"    xpath="">
				<!--我的应用-->
				<a   href="<@base/>/appList"><@locale code="navs.mypps"/></a>
			</li>
            <!--会话-->
            <li id="nav_second_12" >
                <a   href="<@base/>/session/sessionList"><@locale code="navs.audit.loginsession"/></a>
            </li>
			<li  id="nav_primay_13"    xpath="">
				<!--安全设置-->
				<a   href="<@base/>/safe/forward/setting"><@locale code="navs.setting"/></a>
				<ul id="nav_child_1301"  class="dropdown" >
					<!--安全设置-->
					<li id="nav_second_1301" >
						<a   href="<@base/>/safe/forward/setting"><@locale code="navs.setting.security"/></a>
					</li>
					<!--认证关联-->
					<li id="nav_second_1301" >
						<a   href="<@base/>/socialsignon/list"><@locale code="navs.setting.sociallink"/></a>
					</li>
					<!--密码修改-->
					<li id="nav_second_1302" >
						<a   href="<@base/>/safe/forward/changePasswod"><@locale code="navs.setting.changepassword"/></a>
					</li>
					<!--应用配置-->
					<li id="nav_second_1304" >
						<a   href="<@base/>/appConfigList"><@locale code="navs.setting.appaccount"/></a>
					</li>
					<!--时间令牌-->
					<li id="nav_second_1305" >
						<a   href="<@base/>/safe/otp/timebased"><@locale code="navs.setting.timetoken"/></a>
					</li>
				</ul>
			</li>
			<!--日志审计-->
			<li  id="nav_primay_15"    xpath="">
				<a   href="<@base/>/historys/loginList"><@locale code="navs.audit"/></a>
				<ul  class="dropdown">
					<!--登录日志-->
					<li id="nav_second_1502" >
						<a   href="<@base/>/historys/loginList"><@locale code="navs.audit.login"/></a>
					</li>
					<!--访问日志-->
					<li id="nav_second_1503" >
						<a   href="<@base/>/historys/loginAppsList"><@locale code="navs.audit.signon"/></a>
					</li>
				
					<!--操作日志-->
					<li id="nav_second_1504" >
						<a   href="<@base/>/historys/systemLogsList"><@locale code="navs.audit.operation"/></a>
					</li>
				</ul>
			</li>
			<#if  Session["current_authentication"].principal.roleAdministrators==true >
            <li  id="nav_primay_16"    xpath="">
                <!--管理-->
                <a target="_blank"   href="<@base/>/authz/maxkey_mgt"><@locale code="global.text.manage"/></a>
            </li>
            </#if>
		</ul>
	</div>
	</div>
</div>
<div class="container row">
    <div id="nav_second"  class ="col-sm-8" style="clear: left"></div>
</div>
