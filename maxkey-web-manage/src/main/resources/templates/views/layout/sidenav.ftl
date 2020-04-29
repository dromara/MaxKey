<div  dir="rtl">
<ul class="metismenu" id="side-nav-menu" >
	<li>
		<a class="side-nav-menu" href="<@base />/main/">
       		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.home"/>
    	</a>
	</li>
   	<li>
     	<a class="side-nav-menu" href="<@base />/orgs/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.orgs"/>
     	</a>
   	</li>
   	<li>
     	<a class="side-nav-menu" href="<@base />/userinfo/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.users"/>
     	</a>
   	</li>
   	<li>
     	<a class="side-nav-menu" href="<@base />/apps/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.apps"/>
     	</a>
   	</li>
   	
 	<li>
     	<a class="side-nav-menu" href="<@base />/app/accounts/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.accounts"/>
     	</a>
   	</li>  	
   	<li>
     	<a class="side-nav-menu has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.groups"/>
     	</a>
     	<ul>
     		<li>
	         <a class="side-nav-menu" href="<@base />/groups/list/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	<@locale code="navs.groups"/>	
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu"  href="<@base />/groupMember/list/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	<@locale code="navs.groups.member"/>
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu" href="<@base />/groupPrivileges/list/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 <@locale code="navs.groups.privileges"/>
	         </a>
	       </li>
	    </ul>
   	</li> 
   	<li>
     	<a class="side-nav-menu has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.conf"/>
     	</a>
     	<ul>
	       <li>
             <a class="side-nav-menu" href="<@base />/roles/list/">
               <span class="fa fa-fw fa-code-fork"></span>
                <@locale code="navs.roles"/>  
             </a>
           </li>
           <li>
                 <a class="side-nav-menu" href="<@base />/resources/list/">
                   <span class="fa fa-fw fa-code-fork"></span>
                    <@locale code="navs.resources"/>
                 </a>
            </li>
           <li>
             <a class="side-nav-menu" href="<@base />/permissions/list/">
               <span class="fa fa-fw fa-code-fork"></span>
                 <@locale code="navs.role.permissions"/>
             </a>
           </li>
           <li>
             <a class="side-nav-menu" href="<@base />/config/passwordpolicy/forward/">
               <span class="fa fa-fw fa-code-fork"></span>
                <@locale code="navs.conf.passwordpolicy"/>
             </a>
           </li>
	    </ul>
	</li>
   	<li>
     	<a class="side-nav-menu has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.audit"/>
     	</a>
     	<ul>
	       <li>
	         <a class="side-nav-menu" href="<@base />/logs/loginHistoryList/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	<@locale code="navs.audit.login"/>
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu" href="<@base />/logs/loginAppsHistoryList/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 <@locale code="navs.audit.loginapps"/>
	         </a>
	       </li>
	       <li>
	         <a class="side-nav-menu" href="<@base />/logs/list/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	  <@locale code="navs.audit.operate"/>
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