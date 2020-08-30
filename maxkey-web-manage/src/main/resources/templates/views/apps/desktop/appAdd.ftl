<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
<script type="text/javascript">
<!--
$(function(){	
 	$("#usernameType").change(function(){
		if($(this).val()=="SIMULATION"){
			$(".usernameParameter").hide();
			$(".preUsername").show();
		}else{
			$(".usernameParameter").show();
			$(".preUsername").hide();
		}
	}); 
	
	$("#passwordType").change(function(){
		if($(this).val()=="SIMULATION"){
			$(".passwordParameter").hide();
			$(".prePassword").show();
		}else{
			$(".passwordParameter").show();
			$(".prePassword").hide();
		}
	}); 
	
	$("#submitType").change(function(){
		if($(this).val()=="Enter"){
			$("#submitKey").hide();
			$(".preSubmit").show();
			$(".preSubmit").show();
		}else if($(this).val()=="Key"){
			$("#submitKey").show();
			$(".preSubmit").show();
			$(".preSubmit").show();
		}else{
			$("#submitKey").hide();
			$(".preSubmit").hide();
			$(".preSubmit").hide();
		}
	}); 
	
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
			action="<@base/>/apps/desktop/add"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960"  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"   class="table table-bordered" >
				<tbody>
				<tr>
					<td colspan=4><@locale code="apps.desktop.info" /></td>
				</tr>
				<tr>
					<th ><@locale code="apps.desktop.programPath" />：</th>
					<td colspan="3">
						<textarea  id="programPath" name="programPath" rows="4" cols="60" class="form-control"  required=""  ></textarea>
						<b class="orange">*</b><label for="programPath"></label>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.desktop.parameter" />：</th>
					<td colspan="3">
						<textarea  id="parameter" name="parameter" rows="4" cols="60" class="form-control" ></textarea>
						
						<b class="orange">*</b><label for="parameter"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.desktop.usernameType" />：</th>
					<td >
						<select  id="usernameType" name="usernameType" class="form-control" >
							<option value="SIMULATION" selected>SIMULATION</option>
							<option value="PARAMETER">PARAMETER</option>
						</select>
						<b class="orange">*</b><label for="usernameType"></label>
					</td>
					<th class="usernameParameter"  style="display:none"><@locale code="apps.desktop.usernameParameter" />：</th>
					<td class="usernameParameter"  style="display:none">
						<input type="text" id="usernameParameter" name="usernameParameter"  title="" value="username" class="form-control"  />
						<b class="orange">*</b><label for="usernameParameter"></label>
					</td>
					
					<th class="preUsername"><@locale code="apps.desktop.preUsername" />：</th>
					<td class="preUsername">
						<input type="text" id="preUsername" name="preUsername"  title="" value="" class="form-control" />
						<b class="orange">*</b><label for="preUsername"></label>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.desktop.passwordType" />：</th>
					<td >
						<select  id="passwordType" name="passwordType" class="form-control" >
							<option value="SIMULATION" selected>SIMULATION</option>
							<option value="PARAMETER">PARAMETER</option>
						</select>
						<b class="orange">*</b><label for="passwordType"></label>
					</td>
					<th   class="passwordParameter" style="display:none"><@locale code="apps.desktop.passwordParameter" />：</th>
					<td   class="passwordParameter" style="display:none">
						<input type="text" id="passwordParameter" name="passwordParameter"  title="" value="password" class="form-control"    />
						<b class="orange">*</b><label for="passwordParameter"></label>
					</td>
					
					<th  class="prePassword" ><@locale code="apps.desktop.prePassword" />：</th>
					<td  class="prePassword"  >
						<input type="text" id="prePassword" name="prePassword"  title="" value="" class="form-control" />
						<b class="orange">*</b><label for="prePassword"></label>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.desktop.submitType" />：</th>
					<td >
						<select  id="submitType" name="submitType" class="form-control" >
							<option value="Enter" selected>Enter</option>
							<option value="Key">Key</option>
							<option value="None">None</option>
						</select>
						<input  style="display:none" type="text" id="submitKey" class="form-control"  name="submitKey" size="3"  title="" value=""/>
						<b class="orange">*</b><label for="submitType"></label>
					</td>
					<th  class="preSubmit"><@locale code="apps.desktop.preSubmit" />：</th>
					<td  class="preSubmit">
						<input type="text" id="preSubmit" name="preSubmit" class="form-control" title="" value=""/>
						<b class="orange">*</b><label for="preSubmit"></label>
					</td>
					
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.credential" />：</th>
					<td style="width:35%;">
						<input type="radio" id="credential-user-defined" name="credential" class="credential form-control" value="3"  checked  style="float:left;width: 30px;"/>
						<@locale code="apps.credential.user-defined" />
						<input type="radio" id="credential-shared" name="credential" class="credential form-control"  value="2" style="float:left;width: 30px;"/>
						<@locale code="apps.credential.shared" />
						<input type="radio" id="credential-system" name="credential" class="credential form-control"  value="1" style="float:left;width: 30px;" />
						<@locale code="apps.credential.system" />
						<b class="orange">*</b><label for="credential"></label>
					</td>
					<th style="width:15%;"></th>
					<td style="width:35%;" >

					</td>
				</tr>
>
				</tr>
				<tr id="systemconfigure"  style="display:none">
					<th><@locale code="apps.credential" />：</th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr" class="form-control" >
							<option value="uid"><@locale code="userinfo.id" /></option>
							<option value="employeeNumber"><@locale code="userinfo.employeeNumber" /></option>
							<option value="username"  selected><@locale code="userinfo.username" /></option>
							<option value="email"><@locale code="userinfo.email" /></option>
							<option value="windowsaccount"><@locale code="userinfo.windowsAccount" /></option>
						</select>
						<b class="orange">*</b><label for="systemUserAttr"></label>
					</td>
				</tr>
				<tr id="sharedconfigure"  style="display:none">
					<th><@locale code="apps.credential.sharedUsername" />：</th>
					<td  >
						<input type="text" id="sharedUsername" name="sharedUsername" value="" class="form-control" />
						<b class="orange">*</b><label for="sharedUsername"></label>
					</td>
					<th  ><@locale code="apps.credential.sharedPassword" />：</th>
					<td  >
						<input type="text" id="sharedPassword" name="sharedPassword" value="" class="form-control" />
						<b class="orange">*</b><label for="sharedPassword"></label>
					</td>
				</tr>
				
				</tbody>
			  </table>
			  </td>
				</tr>
				</tbody>
				</table>
			<input  id="status" type="hidden" name="status"  value="1"/>
    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>		  
</form>
</body>
</html>