<div id="nav_primary" >
<div  class="container row">
	<div class="col-sm-8">
	<div id="nav_primary"  class="menuprimary">
		<ul >
			<li  id="nav_primay_11"  class="nav_primay_level primaryleft"  xpath="">
				<!--我的应用-->
				<a   href="<@base/>/appList"><@locale code="navs.mypps"/></a>
			</li>
			<li  id="nav_primay_13"  class="nav_primay_level primaryleft"  xpath="">
				<!--安全设置-->
				<a   href="<@base/>/safe/forward/setting"><@locale code="navs.setting"/></a>
				<div id="nav_child_1301"  class="nav_second_child">
					<ul>
						<!--安全设置-->
						<li id="nav_second_1301" class="nav_second_level">
							<a   href="<@base/>/safe/forward/setting"><@locale code="navs.setting.security"/></a>
						</li>
					</ul>
					<ul>
						<!--认证关联-->
						<li id="nav_second_1301" class="nav_second_level">
							<a   href="<@base/>/socialsignon/list"><@locale code="navs.setting.sociallink"/></a>
						</li>
					</ul>
					<ul>
						<!--密码修改-->
						<li id="nav_second_1302" class="nav_second_level">
							<a   href="<@base/>/safe/forward/changePasswod"><@locale code="navs.setting.changepassword"/></a>
						</li>
					</ul>
					<ul>
						<!--应用配置-->
						<li id="nav_second_1304" class="nav_second_level">
							<a   href="<@base/>/appConfigList"><@locale code="navs.setting.appaccount"/></a>
						</li>
					</ul>
					<ul>
						<!--时间令牌-->
						<li id="nav_second_1305" class="nav_second_level">
							<a   href="<@base/>/safe/otp/timebased"><@locale code="navs.setting.timetoken"/></a>
						</li>
					</ul>
				</div>
			</li>
			<!--我的资料-->
			<li  id="nav_primay_14"  class="nav_primay_level primaryleft"  xpath="">
				<a  href="<@base/>/profile/myProfile"><@locale code="navs.myprofile"/></a>
			</li>
			<!--日志审计-->
			<li  id="nav_primay_15"  class="nav_primay_level primaryleft"  xpath="">
				<a   href="<@base/>/historys/login"><@locale code="navs.audit"/></a>
				<div id="nav_child_1501"  class="nav_second_child">
					<ul>
						<!--登录日志-->
						<li id="nav_second_1501" class="nav_second_level">
							<a   href="<@base/>/historys/login"><@locale code="navs.audit.login"/></a>
						</li>
					</ul>
					<ul>
						<!--访问日志-->
						<li id="nav_second_1502" class="nav_second_level">
							<a   href="<@base/>/historys/loginApps"><@locale code="navs.audit.signon"/></a>
						</li>
					</ul>
					<ul>
						<!--操作日志-->
						<li id="nav_second_1503" class="nav_second_level">
							<a   href="<@base/>/historys/logs"><@locale code="navs.audit.operation"/></a>
						</li>
					</ul>
				</div>
			</li>
		</ul>
	</div>
	</div>
	</div>
</div>
<div class="container row">
<div id="nav_second"  class ="col-sm-8" style="clear: left"><div class='menusecond '></div><br style='clear: left' /></div>
</div>
<script>
	$(function(){
		function displaySecondNavs(menuId){
			if($("#"+menuId+" .nav_second_child").html()){
				$("#nav_second").html("<div class='menusecond'>"+$("#"+menuId+" .nav_second_child").html()+"</div><br style='clear: left' />");
			}else{
				$("#nav_second").html("");
			}
		}
		
		displaySecondNavs('${Request["mnid"]!"nav_primay_15"}');
		$(".menuprimary ul li").mouseover(function(){
			displaySecondNavs(this.id);
		});
		
	});
</script>