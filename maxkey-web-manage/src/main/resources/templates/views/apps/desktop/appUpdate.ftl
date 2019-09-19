<script type="text/javascript">
<!--
$(function(){	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/oauth20", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
	
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
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/desktop/update"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960" class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appUpdateCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"   class="datatable" >
				<tbody>
				<tr>
					<td colspan=4><@locale code="apps.desktop.info" /></td>
				</tr>
				<tr>
					<th><@locale code="apps.desktop.programPath" />：</th>
					<td colspan="3">
						<textarea  id="programPath" name="programPath" rows="4" cols="60">${model.programPath}</textarea>
						<b class="orange">*</b><label for="programPath"></label>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.desktop.parameter" />：</th>
					<td colspan="3">
						<textarea  id="parameter" name="parameter" rows="4" cols="60">${model.parameter}</textarea>
						
						<b class="orange">*</b><label for="parameter"></label>
					</td>
				</tr>
				<tr>
					<th ><@locale code="apps.desktop.usernameType" />：</th>
					<td >
						<select  id="usernameType" name="usernameType" >
							<option value="SIMULATION"  <c:if test="${'SIMULATION'==model.usernameType}">selected</c:if> >SIMULATION</option>
							<option value="PARAMETER"   <c:if test="${'PARAMETER'==model.usernameType}">selected</c:if> >PARAMETER</option>
						</select>
						<b class="orange">*</b><label for="usernameType"></label>
					</td>
					<th  class="usernameParameter" <c:if test="${'SIMULATION'==model.usernameType}">style="display:none"</c:if> >
						<@locale code="apps.desktop.usernameParameter" />：</th>
					<td  class="usernameParameter" <c:if test="${'SIMULATION'==model.usernameType}">style="display:none"</c:if> >
						<input type="text" id="usernameParameter" name="usernameParameter"  title="" value="${model.usernameParameter}"/>
						<b class="orange">*</b><label for="usernameParameter"></label>
					</td>
					
					<th class="preUsername" <c:if test="${'PARAMETER'==model.usernameType}">style="display:none"</c:if> >
						<@locale code="apps.desktop.preUsername" />：</th>
					<td class="preUsername" <c:if test="${'PARAMETER'==model.usernameType}">style="display:none"</c:if> >
						<input type="text" id="preUsername" name="preUsername"  title="" value="${model.preUsername}"/>
						<b class="orange">*</b><label for="preUsername"></label>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.desktop.passwordType" />：</th>
					<td >
						<select  id="passwordType" name="passwordType" >
							<option value="SIMULATION"   <c:if test="${'SIMULATION'==model.passwordType}">selected</c:if> >SIMULATION</option>
							<option value="PARAMETER"    <c:if test="${'PARAMETER'==model.passwordType}">selected</c:if> >PARAMETER</option>
						</select>
						<b class="orange">*</b><label for="passwordType"></label>
					</td>
					<th   class="passwordParameter" <c:if test="${'SIMULATION'==model.passwordType}">style="display:none"</c:if>  >
						<@locale code="apps.desktop.passwordParameter" />：</th>
					<td   class="passwordParameter" <c:if test="${'SIMULATION'==model.passwordType}">style="display:none"</c:if> >
						<input type="text" id="passwordParameter" name="passwordParameter"  title="" value="${model.passwordParameter}"/>
						<b class="orange">*</b><label for="passwordParameter"></label>
					</td>
					
					<th  class="prePassword" <c:if test="${'PARAMETER'==model.passwordType}">style="display:none"</c:if> >
						<@locale code="apps.desktop.prePassword" />：</th>
					<td  class="prePassword" <c:if test="${'PARAMETER'==model.passwordType}">style="display:none"</c:if> >
						<input type="text" id="prePassword" name="prePassword"  title="" value="${model.prePassword}"/>
						<b class="orange">*</b><label for="prePassword"></label>
					</td>
					
				</tr>
				
				<tr>
					<th><@locale code="apps.desktop.submitType" />：</th>
					<td >
						<select  id="submitType" name="submitType" >
							<option value="Enter" <c:if test="${'Enter'==model.submitType}">selected</c:if>  >Enter</option>
							<option value="Key"   <c:if test="${'Key'==model.submitType}">selected</c:if> >Key</option>
							<option value="None"  <c:if test="${'None'==model.submitType}">selected</c:if> >None</option>
						</select>
						<input  <c:if test="${'Key'!=model.submitType}">style="display:none"</c:if> type="text" id="submitKey" name="submitKey" size="3"  title="" value="${model.submitKey}"/>
						<b class="orange">*</b><label for="submitType"></label>
					</td>
					<th  class="preSubmit"  <c:if test="${'None'==model.submitType}">style="display:none"</c:if> >
						<@locale code="apps.desktop.preSubmit" />：</th>
					<td  class="preSubmit"  <c:if test="${'None'==model.submitType}">style="display:none"</c:if> >
						<input type="text" id="preSubmit" name="preSubmit"  title="" value="${model.preSubmit}"/>
						<b class="orange">*</b><label for="preSubmit"></label>
					</td>
					
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.credential" />：</th>
					<td style="width:35%;">
						<input type="radio" id="credential-user-defined" name="credential" class="credential" value="3"  <c:if test="${3==model.credential}">checked</c:if> />
						<@locale code="apps.credential.user-defined" />
						<input type="radio" id="credential-shared" name="credential" class="credential" value="2"  <c:if test="${2==model.credential}">checked</c:if> />
						<@locale code="apps.credential.shared" />
						<input type="radio" id="credential-system" name="credential" class="credential" value="1"  <c:if test="${1==model.credential}">checked</c:if> />
						<@locale code="apps.credential.system" />
						<b class="orange">*</b><label for="credential"></label>
					</td>
					<th style="width:15%;"><@locale code="apps.isAdapter" />：</th>
					<td style="width:35%;">
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  <c:if test="${0==model.isAdapter}">selected</c:if> ><@locale code="apps.isAdapter.no" /></option>
							<option value="1"  <c:if test="${1==model.isAdapter}">selected</c:if> ><@locale code="apps.isAdapter.yes" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.adapter" />：</th>
					<td colspan =3>
						<input type="text" id="adapter" name="adapter"  title="" value="${model.adapter}"/>
					</td>
				</tr>
				<tr id="systemconfigure"  <c:if test="${1!=model.credential}"> style="display:none"</c:if> >
					<th><@locale code="apps.credential.system" />：</th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr">
							<option value="uid"   <c:if test="${'uid'==model.systemUserAttr}">selected</c:if> ><@locale code="userinfo.uid" /></option>
							<option value="employeeNumber" <c:if test="${'employeeNumber'==model.systemUserAttr}">selected</c:if> ><@locale code="userinfo.employeeNumber" /></option>
							<option value="username" <c:if test="${'username'==model.systemUserAttr}">selected</c:if> ><@locale code="userinfo.username" /></option>
							<option value="email" <c:if test="${'email'==model.systemUserAttr}">selected</c:if> ><@locale code="userinfo.email" /></option>
							<option value="windowsaccount" <c:if test="${'windowsaccount'==model.systemUserAttr}">selected</c:if> ><@locale code="userinfo.windowsAccount" /></option>
						</select>
						<b class="orange">*</b><label for="systemUserAttr"></label>
					</td>
				</tr>
				<tr id="sharedconfigure"  <c:if test="${2!=model.credential}"> style="display:none"</c:if>>
					<th><@locale code="apps.credential.shared.sharedUsername" />：</th>
					<td>
						<input type="text" id="sharedUsername" name="sharedUsername" value="${model.sharedUsername}" />
						<b class="orange">*</b><label for="sharedUsername"></label>
					</td>
					<th  ><@locale code="apps.credential.shared.sharedPassword" />：</th>
					<td>
						<input type="text" id="sharedPassword" name="sharedPassword" value="${model.sharedPassword}" />
						<b class="orange">*</b><label for="sharedPassword"></label>
					</td>
				</tr>
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>
    		<input class="button" id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	  
</form>