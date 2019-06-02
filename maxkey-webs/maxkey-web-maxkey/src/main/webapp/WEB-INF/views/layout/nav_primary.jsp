<%@ page   language="java"     	import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   language="java"     	import="org.maxkey.web.*" %>
<%@ page   language="java"     	import="org.maxkey.domain.*"%>
<%@ page   language="java"     	import="org.maxkey.domain.userinfo.*"%>
<%@ taglib prefix="s"  			uri="http://sso.maxkey.org/tags" %>
<div id="nav_primary" >
<div id="nav_primary"  class="menuprimary">
	<ul >
		<li  id="nav_primay_10"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<s:Base/>/appList">我的应用</a>
		</li>
		<li  id="nav_primay_11"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<s:Base/>/appConfigList">应用配置</a>
		</li>
		<li  id="nav_primay_12"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<s:Base/>/socialsignon/list">认证关联</a>
		</li>
		<li  id="nav_primay_13"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<s:Base/>/safe/forward/setting">安全设置</a>
			<div id="nav_child_1301"  class="nav_second_child">
				<ul>
					<li id="nav_second_1301" class="nav_second_level">
						<a   href="<s:Base/>/safe/forward/setting">安全设置</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1302" class="nav_second_level">
						<a   href="<s:Base/>/safe/forward/changeAppLoginPasswod">应用登录密码</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1303" class="nav_second_level">
						<a   href="<s:Base/>/safe/otp/timebased">时间令牌</a>
					</li>
				</ul>
			</div>
		</li>
		
		<li  id="nav_primay_14"  class="nav_primay_level primaryleft"  xpath="">
			<a  href="<s:Base/>/profile/forwardBasic">我的资料</a>
			<div id="nav_child_1401"  class="nav_second_child">
				<ul>
					<li id="nav_second_1401" class="nav_second_level">
						<a   href="<s:Base/>/profile/forwardBasic">基本资料</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1402" class="nav_second_level">
						<a   href="<s:Base/>/profile/forwardCompany">组织信息</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1403" class="nav_second_level">
						<a   href="<s:Base/>/profile/forwardHome">个人资料</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1404" class="nav_second_level">
						<a   href="<s:Base/>/profile/forwardExtra">扩展信息</a>
					</li>
				</ul>
			</div>
		</li>
		<li  id="nav_primay_15"  class="nav_primay_level primaryleft"  xpath="">
			<a   href="<s:Base/>/logs/loginHistoryList">日志审计</a>
			<div id="nav_child_1501"  class="nav_second_child">
				<ul>
					<li id="nav_second_1501" class="nav_second_level">
						<a   href="<s:Base/>/logs/loginHistoryList">登录日志</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1502" class="nav_second_level">
						<a   href="<s:Base/>/logs/loginAppHistoryList">访问日志</a>
					</li>
				</ul>
				<ul>
					<li id="nav_second_1503" class="nav_second_level">
						<a   href="<s:Base/>/logs/list">操作日志</a>
					</li>
				</ul>
			</div>
		</li>
<%
	String menuId=request.getParameter("mnid")==null?"":request.getParameter("mnid").toString();
%>
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
		$(".menuprimary ul li").mouseover(function(){
			displaySecondNavs(this.id);
		});
		
	});
</script>