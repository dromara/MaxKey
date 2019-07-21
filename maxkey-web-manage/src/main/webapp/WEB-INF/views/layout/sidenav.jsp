<%@ page    language="java"     import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib  prefix="s"  uri="http://sso.maxkey.org/tags" %>
<div  dir="rtl">
<ul class="metismenu" id="side-nav-menu" >
	<li>
		<a class="" href="<s:Base />/main/">
       		<span class="fa fa-fw fa-github fa-lg"></span>
       		首&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页
    	</a>
	</li>
   	<li>
     	<a class="" href="<s:Base />/orgs/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		机构管理
     	</a>
   	</li>
   	<li>
     	<a class="" href="<s:Base />/userinfo/list/">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		用户管理
     	</a>
   	</li>
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		应用管理
     	</a>
     	<ul>
     		<li>
	         <a href="<s:Base />/apps/list/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	应用管理	
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/app/accounts/list">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	账号映射	
	         </a>
	       </li>
	    </ul>
   	</li>
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		组管理
     	</a>
     	<ul>
     		<li>
	         <a href="<s:Base />/groups/list/">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 组管理	
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/groupMember/list">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 成员管理	
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/groupApp/list">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 权限管理	
	         </a>
	       </li>
	    </ul>
   	</li>
   		
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		配置管理
     	</a>
     	<ul>
     		<li>
	         <a href="<s:Base />/config/passwordpolicy/forward">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 密码策略
	         </a>
	       </li>
	    </ul>
	</li>
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		日志审计
     	</a>
     	<ul>
	       <li>
	         <a href="<s:Base />/logs/loginHistoryList">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 登录日志
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/logs/loginAppsHistoryList">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 访问日志
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/logs/list">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	  操作日志
	         </a>
	       </li>
	    </ul>
   	</li>
   	<li>
     	<a class="has-arrow" href="#">
      		<span class="fa fa-fw fa-github fa-lg"></span>
       		统计报表
     	</a>
     	<ul>
	       <li>
	         <a href="<s:Base />/report/login/day">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	  日报表
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/report/login/month">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 月报表
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/report/login/year">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	  年报表
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/report/login/app">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	  应用登录报表
	         </a>
	       </li>
	       <li>
	         <a href="<s:Base />/report/login/browser">
	           <span class="fa fa-fw fa-code-fork"></span>
	         	 浏览器报表
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