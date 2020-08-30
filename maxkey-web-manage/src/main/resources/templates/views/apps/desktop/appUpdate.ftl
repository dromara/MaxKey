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
<form id="actionForm_app"  method="post" type="label" autoclose="true"  closeWindow="true"  
			action="<@base/>/apps/desktop/update"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
			<table   class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			  <table   class="table table-bordered" >
				<tbody>
				<tr>
					<td colspan=4><@locale code="apps.desktop.info" /></td>
				</tr>
				<tr>
					<th><@locale code="apps.desktop.programPath" />：</th>
					<td colspan="3">
						<textarea class="form-control" id="programPath" name="programPath" rows="4" cols="60"  required="" >${model.programPath}</textarea>
						
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.desktop.parameter" />：</th>
					<td colspan="3">
						<textarea class="form-control" id="parameter" name="parameter" rows="4" cols="60">${model.parameter}</textarea>
						
					</td>
				</tr>
				<tr>
					<th ><@locale code="apps.desktop.usernameType" />：</th>
					<td >
						<select  id="usernameType" name="usernameType" class="form-control">
							<option value="SIMULATION"  <#if 'SIMULATION'==model.usernameType>selected</#if> >SIMULATION</option>
							<option value="PARAMETER"   <#if 'PARAMETER'==model.usernameType>selected</#if> >PARAMETER</option>
						</select>
						
					</td>
					<th  class="usernameParameter" <#if 'SIMULATION'==model.usernameType>style="display:none"</#if> >
						<@locale code="apps.desktop.usernameParameter" />：</th>
					<td  class="usernameParameter" <#if 'SIMULATION'==model.usernameType>style="display:none"</#if> >
						<input type="text" class="form-control" id="usernameParameter" name="usernameParameter"  title="" value="${model.usernameParameter}"  />
						
					</td>
					
					<th class="preUsername" <#if 'PARAMETER'==model.usernameType>style="display:none"</#if> >
						<@locale code="apps.desktop.preUsername" />：</th>
					<td class="preUsername" <#if 'PARAMETER'==model.usernameType>style="display:none"</#if> >
						<input type="text" class="form-control" id="preUsername" name="preUsername"  title="" value="${model.preUsername}"/>
						
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.desktop.passwordType" />：</th>
					<td >
						<select  id="passwordType" name="passwordType" class="form-control">
							<option value="SIMULATION"   <#if 'SIMULATION'==model.passwordType>selected</#if> >SIMULATION</option>
							<option value="PARAMETER"    <#if 'PARAMETER'==model.passwordType>selected</#if> >PARAMETER</option>
						</select>
						
					</td>
					<th   class="passwordParameter" <#if 'SIMULATION'==model.passwordType>style="display:none"</#if>  >
						<@locale code="apps.desktop.passwordParameter" />：</th>
					<td   class="passwordParameter" <#if 'SIMULATION'==model.passwordType>style="display:none"</#if> >
						<input type="text" class="form-control" id="passwordParameter" name="passwordParameter"  title="" value="${model.passwordParameter}"  />
						
					</td>
					
					<th  class="prePassword" <#if 'PARAMETER'==model.passwordType>style="display:none"</#if> >
						<@locale code="apps.desktop.prePassword" />：</th>
					<td  class="prePassword" <#if 'PARAMETER'==model.passwordType>style="display:none"</#if> >
						<input type="text" class="form-control" id="prePassword" name="prePassword"  title="" value="${model.prePassword}"/>
						
					</td>
					
				</tr>
				
				<tr>
					<th><@locale code="apps.desktop.submitType" />：</th>
					<td >
						<select  id="submitType" name="submitType" class="form-control">
							<option value="Enter" <#if 'Enter'==model.submitType>selected</#if>  >Enter</option>
							<option value="Key"   <#if 'Key'==model.submitType>selected</#if> >Key</option>
							<option value="None"  <#if 'None'==model.submitType>selected</#if> >None</option>
						</select>
						<input  <#if 'Key'!=model.submitType>style="display:none"</#if> type="text" id="submitKey" name="submitKey" size="3"  title="" value="${model.submitKey}"/>
						
					</td>
					<th  class="preSubmit"  <#if 'None'==model.submitType>style="display:none"</#if> >
						<@locale code="apps.desktop.preSubmit" />：</th>
					<td  class="preSubmit"  <#if 'None'==model.submitType>style="display:none"</#if> >
						<input type="text"  class="form-control" id="preSubmit" name="preSubmit"  title="" value="${model.preSubmit}"/>
						
					</td>
					
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.credential" />：</th>
					<td style="width:35%;">
						<input type="radio" id="credential-user-defined" name="credential" class="credential" value="3"  <#if 3==model.credential>checked</#if> />
						<@locale code="apps.credential.user-defined" />
						<input type="radio" id="credential-shared" name="credential" class="credential" value="2"  <#if 2==model.credential>checked</#if> />
						<@locale code="apps.credential.shared" />
						<input type="radio" id="credential-system" name="credential" class="credential" value="1"  <#if 1==model.credential>checked</#if> />
						<@locale code="apps.credential.system" />
						
					</td>
					<th style="width:15%;"></th>
					<td style="width:35%;">
					</td>
				</tr>
				<tr id="systemconfigure"  <#if 1!=model.credential> style="display:none"</#if> >
					<th><@locale code="apps.credential.system" />：</th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr" class="form-control">
							<option value="uid"   <#if 'uid'==model.systemUserAttr>selected</#if> ><@locale code="userinfo.id" /></option>
							<option value="employeeNumber" <#if 'employeeNumber'==model.systemUserAttr>selected</#if> ><@locale code="userinfo.employeeNumber" /></option>
							<option value="username" <#if 'username'==model.systemUserAttr>selected</#if> ><@locale code="userinfo.username" /></option>
							<option value="email" <#if 'email'==model.systemUserAttr>selected</#if> ><@locale code="userinfo.email" /></option>
							<option value="windowsaccount" <#if 'windowsaccount'==model.systemUserAttr>selected</#if> ><@locale code="userinfo.windowsAccount" /></option>
						</select>
					</td>
				</tr>
				<tr id="sharedconfigure"  <#if 2!=model.credential> style="display:none"</#if>>
					<th><@locale code="apps.credential.sharedUsername" />：</th>
					<td>
						<input type="text" class="form-control" id="sharedUsername" name="sharedUsername" value="${model.sharedUsername}" />
						
					</td>
					<th  ><@locale code="apps.credential.sharedPassword" />：</th>
					<td>
						<input type="text" class="form-control" id="sharedPassword" name="sharedPassword" value="${model.sharedPassword}" />
					</td>
				</tr>
				</tbody>
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