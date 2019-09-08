<div class="header-container">
	<div class="nav-logo">
		<a href="<@base/>/main"> 
			<IMG SRC="<@base/>/static/images/logo.jpg" alt="" style="width:40px;heigth:40px">	
		</a>
		<span class="logo">
		
		</span>
	</div>
	<ul class="nav-left"  style="letter-spacing:2px;font-size:20px;font-weight:bolder;margin-top: 14px;">
		<@locale code="global.application"/>
	</ul>
	<ul class="nav-right">
		<li style="font-size: 18px; margin-top: 10px;">
				<@locale code="global.text.welcome"/>：<b>
				<#if  Session["current_user"]?exists>
					${Session["current_user"].displayName}
				</#if>
				(
					<#if  Session["current_user"]?exists>
						${Session["current_user"].username} 
					</#if>
				)&nbsp;&nbsp;</b>
		 </li>
		<li class="scale-left">
			<a class="sidenav-fold-toggler" href="javascript:void(0);"> 
				<img  src="<@base/>/static/images/menu-left.png" alt="" style="width: 30px; height: 40px; padding-top: 10px;">
			</a>
		</li>
		<li class="scale-left">
			 &nbsp;
		</li>
		<li class="scale-left"> 
			<a  href="<@base/>/logout?reLoginUrl=login">
				<IMG SRC="<@base/>/static/images/exit4.png" alt="Exit" style="width: 40px; height: 45px; padding-top: 8px;">	
			</a>
		</li>
	</ul>
</div>