<div id="topBar"  > 
	<div class="container">
		<div style="float:left;margin-left:20px;margin-top: 5px;"><IMG SRC="<@base/>/static/images/logo.jpg" style="width:55px;heigth:55px"></div>
		<div style="margin-top:20px;margin-left:10px;float:left">
			<div style="letter-spacing:2px;font-size:20px;font-weight:bolder;"><@locale code="global.application"/></div>
			
		</div>
		<div style="margin-top:25px;margin-right:10px;float:right;">
			<table  style="height: 31px;">
				<tr>
					<td>
						<@locale code="global.text.welcome"/>：
						<#if  Session["current_user"]?exists>
							${Session["current_user"].displayName}  ${Session["current_user"].username} 
						</#if>
						&nbsp;&nbsp;
					</td>
					<#if  Session["current_user"].gridList==0 >
					<td>
						<img src='<@base/>/static/images/grid_sel.png'  style="width=:31px;height:31px;border:0;">
					</td>
					<td>
						<a href="<@base/>/appList?mnid=110101020000&gridList=1"><img src='<@base/>/static/images/list.png'  style="width=:31px;height:31px;border:0;"></a>
					</td>
					<#else>
					<td>
						<a href="<@base/>/appList?mnid=110101020000&gridList=0" ><img src='<@base/>/static/images/grid.png'  style="width=:31px;height:31px;border:0;"></a>
					</td>
					<td>
						<img src='<@base/>/static/images/list_sel.png'  style="width=:31px;height:31px;border:0;">
					</td>
					</#if>
					<td id="changepassword" nowrap>
						<a  href="<@base/>/safe/forward/changePasswod">
							<div  style="float:right;" >&nbsp;&nbsp;<@locale code="login.password.changepassword"/>&nbsp;&nbsp;</div>
						</a>
					</td>
					<td id="manage" nowrap>
						<a target="_blank"  href="<@base/>/authz/maxkey_mgt">
							<div  style="float:right;" >&nbsp;&nbsp;<@locale code="global.text.manage"/>&nbsp;&nbsp;</div>
						</a>
					</td>
		
					<td id="logout" class="ui-widget-header" >
						<a  href="<@base/>/logout?reLoginUrl=login">
							<div  style="float:right;" >&nbsp;&nbsp;<@locale code="global.text.logout"/>&nbsp;&nbsp;</div>
						</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>