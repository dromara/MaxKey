
<!--top-->
<div class="header-container">
	<div class="nav-logo">
		<a href="<@base/>/main"> 
			<IMG SRC="<@locale code="global.logo"/>" alt="" style="width:40px;heigth:40px">	
		</a>
		<span class="logo">
		
		</span>
	</div>
	<ul class="nav-left"  style="letter-spacing:2px;font-size:20px;font-weight:bolder;margin-top: 14px;">
		<@locale code="global.consoleTitle"/>
	</ul>
	<ul class="nav-right">
		<li style="font-size: 16px; margin-top: 10px;">
				<@locale code="global.text.welcome"/>:<b>
				<#if  Session["current_user"]?exists>
					 ${Session["current_user"].displayName}
					(${Session["current_user"].username}) 
				</#if>
				&nbsp;</b>
		 </li>
		<li class="scale-left" style="margin-top: 5px;">
			<a class="sidenav-fold-toggler" href="javascript:void(0);" > 
				<i class="fa fa-bars fa-2x" aria-hidden="true" style="border:0px"></i>
			</a>
		</li>
		<li class="scale-left" style="font-size: 18px; margin-top: 5px;"> 
			<a  href="<@base/>/logout?reLoginUrl=login">
				<i class="fa fa-sign-out fa-2x" aria-hidden="true" style="border:0px;color:#e22a6f"></i>
			</a>
		</li>
	</ul>
</div>
<!--top end-->
