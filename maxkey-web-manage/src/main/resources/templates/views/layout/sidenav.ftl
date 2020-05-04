<div  dir="rtl">
<ul class="metismenu" id="side-nav-menu" >
	<li>
		<a class="side-nav-menu" href="<@base />/main/">
       		<@locale code="navs.home"/>
       		<span class="fa fa-fw fa-home fa-lg"></span>
    	</a>
	</li>
   	<li>
     	<a class="side-nav-menu" href="<@base />/orgs/list/">
       		<@locale code="navs.orgs"/>
       		<span class="fa fa-fw fa-sitemap fa-lg"></span>
     	</a>
   	</li>
   	<li>
     	<a class="side-nav-menu" href="<@base />/userinfo/list/">
       		<@locale code="navs.users"/>
       		<span class="fa fa-fw fa-user fa-lg"></span>
     	</a>
   	</li>
   	<li>
     	<a class="side-nav-menu" href="<@base />/apps/list/">
       		<@locale code="navs.apps"/>
       		<span class="fa fa-fw fa-globe fa-lg"></span>
     	</a>
   	</li>
   	
 	<li>
     	<a class="side-nav-menu" href="<@base />/app/accounts/list/">
      		
       		<@locale code="navs.accounts"/>
       		<span class="fa fa-fw fa-vcard  fa-lg"></span>
     	</a>
   	</li>  	
   	<li>
     	<a class="side-nav-menu has-arrow" href="#">
       		<@locale code="navs.groups"/>
       		<span class="fa fa-fw fa-cubes fa-lg"></span>
     	</a>
     	<ul>
     		<li>
	         <a class="side-nav-menu" href="<@base />/groups/list/">
	         	<@locale code="navs.groups"/>	
	         	<span class="fa fa-fw fa-address-book"></span>
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu"  href="<@base />/groupMember/list/">
	         	<@locale code="navs.groups.member"/>
	         	<span class="fa fa-fw fa-podcast"></span>
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu" href="<@base />/groupPrivileges/list/">
	         	 <@locale code="navs.groups.privileges"/>
	         	 <span class="fa fa-fw fa-anchor"></span>
	         </a>
	       </li>
	    </ul>
   	</li> 
   	<li>
     	<a class="side-nav-menu has-arrow" href="#">
       		<@locale code="navs.conf"/>
       		<span class="fa fa-fw fa-cogs fa-lg"></span>
     	</a>
     	<ul>
	       <li>
             <a class="side-nav-menu" href="<@base />/roles/list/">
                <@locale code="navs.roles"/>  
                <span class="fa fa-fw fa-shield"></span>
             </a>
           </li>
           <li>
             <a class="side-nav-menu"  href="<@base />/rolemembers/list/">
                <@locale code="navs.role.member"/>
                <span class="fa fa-fw fa-user-md"></span>
             </a>
           </li>
           <li>
                 <a class="side-nav-menu" href="<@base />/resources/list/">
                    <@locale code="navs.resources"/>
                     <span class="fa fa-fw fa-paper-plane"></span>
                 </a>
            </li>
           <li>
             <a class="side-nav-menu" href="<@base />/permissions/list/">
                 <@locale code="navs.role.permissions"/>
                 <span class="fa fa-fw fa-check-square"></span>
             </a>
           </li>
           <li>
             <a class="side-nav-menu" href="<@base />/config/passwordpolicy/forward/">
                <@locale code="navs.conf.passwordpolicy"/>
                 <span class="fa fa-fw fa-balance-scale"></span>
             </a>
           </li>
	    </ul>
	</li>
   	<li>
     	<a class="side-nav-menu has-arrow" href="#">
       		<@locale code="navs.audit"/>
       		<span class="fa fa-fw fa-eye fa-lg"></span>
     	</a>
     	<ul>
	       <li>
	         <a class="side-nav-menu" href="<@base />/logs/loginHistoryList/">
	         	<@locale code="navs.audit.login"/>
	         	<span class="fa fa-fw fa-eraser"></span>
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu" href="<@base />/logs/loginAppsHistoryList/">
	         	 <@locale code="navs.audit.loginapps"/>
	         	 <span class="fa fa-fw fa-eraser"></span>
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu" href="<@base />/logs/list/">
	         	  <@locale code="navs.audit.operate"/>
	         	  <span class="fa fa-fw fa-eraser"></span>
	         </a>
	       </li>
	    </ul>
   	</li>
   	
 </ul>

<script type="text/javascript"> 
$(function(){
	$('#side-nav-menu').metisMenu();
});
</script>
</div>