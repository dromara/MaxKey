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
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
   action="<s:Base/>/apps/extendapi/add"  
   forward="<s:Base/>/apps/list"
   enctype="multipart/form-data">
   <!-- content -->    
   <!--table-->
			<table  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table  class="table table-bordered" >
				<tbody>
				<tr>
					<th  ><@locale code="apps.principal"/>：</th>
					<td  >
						<input  class="form-control" type="text" id="principal" name="principal"  title="" value=""/>
						<b class="orange">*</b><label for="principal"></label>
					</td>
					<th  ><@locale code="apps.credentials"/>：</th>
					<td  >
						<input class="form-control" type="text" id="credentials" name="credentials"  title="" value=""/>
						<b class="orange">*</b><label for="credentials"></label>
					</td>
				</tr>
				<tr>
					<th  style="width:15%;"><@locale code="apps.credential"/>：</th>
					<td  style="width:35%;">
						<input type="radio" id="credential-user-defined" name="credential" class="credential" value="3"  checked />
						<@locale code="apps.credential.user-defined"/>
						<input type="radio" id="credential-shared" name="credential" class="credential"  value="2" />
						<@locale code="apps.credential.shared"/>
						<input type="radio" id="credential-system" name="credential" class="credential"  value="1"  />
						<@locale code="apps.credential.system"/>
						<b class="orange">*</b><label for="credential"></label>
					</td>
					<th  style="width:15%;"><@locale code="apps.isAdapter"/>：</th>
					<td  style="width:35%;">
						<select  class="form-control"  id="isAdapter" name="isAdapter" >
							<option value="0"  selected><@locale code="apps.isAdapter.no"/></option>
							<option value="1"><@locale code="apps.isAdapter.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.adapter"/>：</th>
					<td colspan =3>
						<input  class="form-control" type="text" id="adapter" name="adapter"  title="" value=""/>
					</td>
				</tr>
				<tr id="systemconfigure"  style="display:none">
					<th><@locale code="apps.systemUserAttr"/>：</th>
					<td colspan="3">
						<select  class="form-control" id="systemUserAttr" name="systemUserAttr">
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
						<input  class="form-control" type="text" id="sharedUsername" name="sharedUsername" value="" />
						<b class="orange">*</b><label for="sharedUsername"></label>
					</td>
					<th><@locale code="apps.credential.sharedPassword"/>：</th>
					<td>
						<input  class="form-control" type="text" id="sharedPassword" name="sharedPassword" value="" />
						<b class="orange">*</b><label for="sharedPassword"></label>
					</td>
				</tr>
				</tbody>
				<tr>
								<td colspan =4>
									<input class="button"  id="status" type="hidden" name="status"  value="1"/>
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