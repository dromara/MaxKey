<div  dir="rtl">
<ul class="metismenu" id="side-nav-menu" >
	<li>
		<a class="" href="<@base />/main/">
       		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.home"/>
    	</a>
	</li>
   	<li>
     	<a class="" href="<@base />/orgs/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.orgs"/>
     	</a>
   	</li>
   	<li>
     	<a class="" href="<@base />/userinfo/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.users"/>
     	</a>
   	</li>
   	<li>
     	<a class="" href="<@base />/apps/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.apps"/>
     	</a>
   	</li>
   	
 	<li>
     	<a class="" href="<@base />/app/accounts/list">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.accounts"/>
     	</a>
   	</li>  	
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.groups"/>
     	</a>
     	<ul>
     		<li>
	         <a href="<@base />/groups/list/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	<@locale code="navs.groups"/>	
	         </a>
	       </li>
	       <li>
	         <a href="<@base />/groupMember/list">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	<@locale code="navs.groups.member"/>
	         </a>
	       </li>
	       <li>
	         <a href="<@base />/groupPrivileges/list">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 <@locale code="navs.groups.privileges"/>
	         </a>
	       </li>
	    </ul>
   	</li>
   		
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.conf"/>
     	</a>
     	<ul>
     		<li>
	         <a href="<@base />/config/passwordpolicy/forward">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	<@locale code="navs.conf.passwordpolicy"/>
	         </a>
	       </li>
	    </ul>
	</li>
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		<@locale code="navs.audit"/>
     	</a>
     	<ul>
	       <li>
	         <a href="<@base />/logs/loginHistoryList">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	<@locale code="navs.audit.login"/>
	         </a>
	       </li>
	       <li>
	         <a href="<@base />/logs/loginAppsHistoryList">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 <@locale code="navs.audit.loginapps"/>
	         </a>
	       </li>
	       <li>
	         <a href="<@base />/logs/list">
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