<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
<script type="text/javascript">
<!--
$(function(){		

	$("#credential").on("click",function(){
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
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
<form id="actionForm_app"  method="post" type="label" autoclose="true"    closeWindow="true"
   action="<@base/>/apps/extendapi/add"  
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
			   <table  class="table table-bordered" >
				<tbody>
				<tr>
					<th  ><@locale code="apps.principal"/></th>
					<td  >
						<input  class="form-control" type="text" id="principal" name="principal"  title="" value=""  required=""  />
					</td>
					<th  ><@locale code="apps.credentials"/></th>
					<td  >
						<input class="form-control" type="text" id="credentials" name="credentials"  title="" value=""  required=""   />
					</td>
				</tr>
				<tr>
					<th  style="width:15%;"><@locale code="apps.credential"/></th>
					<td  style="width:35%;">
						<select id="credential" name="credential"  class="form-control  form-select" >
                            <option value="3"   selected >
                                <@locale code="apps.credential.user-defined"/>
                            </option>
                            <option value="2"   >
                                <@locale code="apps.credential.shared"/>
                            </option>
                            <option value="1"   >
                                <@locale code="apps.credential.system"/>
                            </option>
                        </select> 
					</td>
					<th  style="width:15%;"></th>
					<td  style="width:35%;">
						
					</td>
				</tr>
				<tr id="systemconfigure"  style="display:none">
					<th><@locale code="apps.systemUserAttr"/></th>
					<td colspan="3">
						<select  class="form-control  form-select" id="systemUserAttr" name="systemUserAttr">
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
					</td>
				</tr>
				<tr id="sharedconfigure"  style="display:none">
					<th><@locale code="apps.credential.sharedUsername"/></th>
					<td>
						<input  class="form-control" type="text" id="sharedUsername" name="sharedUsername" value="" />
						
					</td>
					<th><@locale code="apps.credential.sharedPassword"/></th>
					<td>
						<input  class="form-control" type="text" id="sharedPassword" name="sharedPassword" value="" />
					</td>
				</tr>
				</tbody>
				<tr>
								<td colspan =4>
									<input class="button"  id="status" type="hidden" name="status"  value="1"/>
						    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
									<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.close" />"/>	
								</td>
							</tr>
							</tbody>
			  </table>
			  </td>
				</tr>
				</tbody>
				</table>  
</form>
        </div>
    </div>
</div>
</body>
</html>