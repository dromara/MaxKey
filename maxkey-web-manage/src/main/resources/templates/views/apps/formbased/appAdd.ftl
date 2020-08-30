<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>

<script type="text/javascript">
<!--
$(function(){		
	$(".credential").on("click",function(){
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
<form id="actionForm_app"  method="post" type="label" autoclose="true"   closeWindow="true" 
			action="<@base/>/apps/formbased/add"
			forward="<@base/>/apps/list"  
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
			<table  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table class="datatable" width="100%" >
				<tbody>
				
				<tr>
					<td colspan=4><@locale code="apps.formbased.info"/></td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.redirectUri"/>：</th>
					<td colspan="3">
						<input type="text"  class="form-control"  id="redirectUri" name="redirectUri"  title="" value=""  required="" />
						<b class="orange">*</b><label for="redirectUri"></label>
					</td>
					
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.formbased.usernameMapping"/>：</th>
					<td style="width:35%;">
						<input type="text"  class="form-control"  id="usernameMapping" name="usernameMapping"  title="" value="username"  required="" />
						<b class="orange">*</b><label for="usernameMapping"></label>
					</td>
					<th style="width:15%;"><@locale code="apps.formbased.passwordMapping"/>：</th>
					<td style="width:35%;">
						<input type="text"  class="form-control" id="passwordMapping" name="passwordMapping"  title="" value="password"  required=""  />
						<b class="orange">*</b><label for="passwordMapping"></label>
					</td>
				</tr>
				
				<tr>
					<th width="140px"><@locale code="apps.credential"/>：</th>
					<td>
						<input type="radio" id="credential-user-defined" name="credential" class="credential" value="3"  checked />
						<@locale code="apps.credential.user-defined"/>
						<input type="radio" id="credential-shared" name="credential" class="credential"  value="2" />
						<@locale code="apps.credential.shared"/>
						<input type="radio" id="credential-system" name="credential" class="credential"  value="1"  />
						<@locale code="apps.credential.system"/>
						<b class="orange">*</b><label for="credential"></label>
					</td>
					<th></th>
					<td>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.authorizeView"/>：</th>
					<td colspan =3>
						<input type="text"  class="form-control"  id="authorizeView" name="authorizeView"  title="" value=""/>
					</td>
				</tr>
				<tr id="systemconfigure"  style="display:none">
					<th><@locale code="apps.systemUserAttr"/>：</th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr"  class="form-control" >
							<option value="uid">
								<@locale code="userinfo.id"/></option>
							<option value="employeeNumber">
								<@locale code="userinfo.employeeNumber"/></option>
							<option value="username"  selected>
								<@locale code="userinfo.username"/></option>
							<option value="email">
								<@locale code="userinfo.email"/></option>
							<option value="windowsaccount">
								<@locale code="userinfo.windowsAccount"/></option>
						</select>
						<b class="orange">*</b><label for="systemUserAttr"></label>
					</td>
				</tr>
				<tr id="sharedconfigure"  style="display:none">
					<th><@locale code="apps.credential.sharedUsername"/>：</th>
					<td>
						<input type="text" id="sharedUsername"  class="form-control"  name="sharedUsername" value="" />
						<b class="orange">*</b><label for="sharedUsername"></label>
					</td>
					<th><@locale code="apps.credential.sharedPassword"/>：</th>
					<td>
						<input type="text" id="sharedPassword"  class="form-control"  name="sharedPassword" value="" />
						<b class="orange">*</b><label for="sharedPassword"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.extendAttr"/>：</th>
					<td colspan="3">
						<input type="checkbox" id="isExtendAttr" name="isExtendAttr" value="1"/>
						<@locale code="apps.isExtendAttr"/>
						<span  id="showExtendAttr" style="display:none">
							<input  class="button"  type="button"  value='<@locale code="button.text.add"/>' id="addExtendAttr"/>
						</span>
					</td>
				</tr>
				</tbody>
				<tbody >
					<tr>
						<td colspan =4>
							<input  id="status" type="hidden" name="status"  value="1"/>
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