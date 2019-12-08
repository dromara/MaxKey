<div id="nav_primary" >
<div id="nav_primary"  class="menuprimary">
	<ul >
		<li  id="nav_primay_11"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<@base/>/appList">我的应用</a>
		</li>
		<li  id="nav_primay_13"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<@base/>/safe/forward/setting">安全设置</a>
			<div id="nav_child_1301"  class="nav_second_child">
				<ul>
					<li id="nav_second_1301" class="nav_second_level">
						<a   href="<@base/>/safe/forward/setting">安全设置</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1301" class="nav_second_level">
						<a   href="<@base/>/socialsignon/list">认证关联</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1302" class="nav_second_level">
						<a   href="<@base/>/safe/forward/changePasswod">密码修改</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1303" class="nav_second_level">
						<a   href="<@base/>/safe/forward/changeAppLoginPasswod">应用登录密码</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1304" class="nav_second_level">
						<a   href="<@base/>/appConfigList">应用配置</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1305" class="nav_second_level">
						<a   href="<@base/>/safe/otp/timebased">时间令牌</a>
					</li>
				</ul>
			</div>
		</li>
		
		<li  id="nav_primay_14"  class="nav_primay_level primaryleft"  xpath="">
			<a  href="<@base/>/profile/myProfile">我的资料</a>
		</li>
		<li  id="nav_primay_15"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<@base/>/historys/login">日志审计</a>
			<div id="nav_child_1501"  class="nav_second_child">
				<ul>
					<li id="nav_second_1501" class="nav_second_level">
						<a   href="<@base/>/historys/login">登录日志</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1502" class="nav_second_level">
						<a   href="<@base/>/historys/loginApps">访问日志</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1503" class="nav_second_level">
						<a   href="<@base/>/historys/logs">操作日志</a>
					</li>
				</ul>
			</div>
		</li>
	</ul>
</div>
</div>
<div id="nav_second"  style="clear: left"><div class='menusecond'></div><br style='clear: left' /></div>
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