<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
<script type="text/javascript">
<!--
$(function(){	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/oauth20", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
	
	$("input[name='credential']").on("click",function(){
		if($(this).val()=="3"){
			$("#sharedconfigure").hide();
			$("#systemconfigure").hide();
		}else if($(this).val()=="1"){
			$("#sharedconfigure").hide();
			$("#systemconfigure").show();
		}else if($(this).val()=="2"){
			$("#sharedconfigure").show();
			$("#systemconfigure").hide();
		}
	});
});
//-->
</script>
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/formbased/update"
			forward="<@base/>/apps/list"  
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table   class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table  class="table table-bordered" >
				<tbody>
				
				<tr>
					<td colspan=4><@locale code="apps.formbased.info"/></td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.redirectUri"/>：</th>
					<td colspan="3">
						<input type="text" class="form-control" id="redirectUri" name="redirectUri"  title="" value="${model.redirectUri}"/>
					</td>
					
				</tr>
				
				<tr>
					<th style="width:15%;"><@locale code="apps.formbased.usernameMapping"/>：</th>
					<td style="width:35%;">
						<input type="text" class="form-control" id="usernameMapping" name="usernameMapping"  title="" value="${model.usernameMapping}"/>
					</td>
					<th style="width:15%;"><@locale code="apps.formbased.passwordMapping"/>：</th>
					<td style="width:35%;">
						<input type="text" class="form-control" id="passwordMapping" name="passwordMapping"  title="" value="${model.passwordMapping}"/>
	
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.credential"/>：</th>
					<td >
							<input type="radio" id="credential1" name="credential" class="credential" value="3"  <#if 3==model.credential>checked</#if> />
							<@locale code="apps.credential.user-defined"/>
							<input type="radio" id="credential3" name="credential" class="credential" value="2"  <#if 2==model.credential>checked</#if> />
							<@locale code="apps.credential.shared"/>
							<input type="radio" id="credential2" name="credential" class="credential" value="1"  <#if 1==model.credential>checked</#if> />
							<@locale code="apps.credential.system"/>
						
					</td>
					<th><@locale code="apps.isAdapter"/>：</th>
					<td  >
						<select  id="isAdapter" name="isAdapter"  class="form-control">
							<option value="0"  <#if 0==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.no"/></option>
							<option value="1"  <#if 1==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.adapter"/>：</th>
					<td colspan =3>
						<input type="text" class="form-control" id="adapter" name="adapter"  title="" value="${model.adapter!}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.authorizeView"/>：</th>
					<td colspan =3>
						<input type="text" class="form-control" id="authorizeView" name="authorizeView"  title="" value="${model.authorizeView!}"/>
					</td>
				</tr>
				<tr id="systemconfigure"  <#if 1!=model.credential> style="display:none"</#if> >
					<th><@locale code="apps.systemUserAttr"/>：</th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr" class="form-control">
							<option value="uid"   <#if 'uid'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.id"/></option>
							<option value="employeeNumber" <#if 'employeeNumber'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.employeeNumber"/></option>
							<option value="username" <#if 'username'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.username"/></option>
							<option value="email" <#if 'email'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.email"/></option>
							<option value="windowsaccount" <#if 'windowsaccount'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.windowsAccount"/></option>
						</select>
					</td>
				</tr>
				<tr id="sharedconfigure"  <#if 2!=model.credential> style="display:none"</#if>>
					<th><@locale code="apps.credential.sharedUsername"/>：</th>
					<td>
						<input type="text" class="form-control" id="sharedUsername" name="sharedUsername" value="${model.sharedUsername}" />
			
					</td>
					<th><@locale code="apps.credential.sharedPassword"/>：</th>
					<td>
						<input type="text"  class="form-control" id="sharedPassword" name="sharedPassword" value="${model.sharedPassword}" />

					</td>
				</tr>
				<tbody >
					<tr>
						<td colspan =4>
				    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
									<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	 
						</td>
					</tr>
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>	  
</form>
</body>
</html>