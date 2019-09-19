
<script type="text/javascript">
<!--
$(function(){	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/oauth20", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
	
	$("#isExtendAttr").on('click',function(){
		if(this.checked){
				$("#showExtendAttr").show();
			} else {
				$("#showExtendAttr").hide();
				$('#extendAttrBody').empty();
			}
			
	});
	
	var attrIndex = 0;
	
	function addExtendAttr(attribute,attributeValue){
		var html = '<tr  id="extendTr_' + attrIndex + '"><th><@locale code="common.text.parameter"/>：';   
		 html += '<input  class="button delExtendTr"  type="button" name="delExtendTr"  attrTrId="extendTr_'+attrIndex+'" value="<@locale code="button.text.delete" />"/>';
		html += '</th><td>';   
		html += '<input type="text" id="attribute_' + attrIndex + '" name="attribute" class="int" title="" value="'+attribute+'"/>';   
        html += '</span></td><th><@locale code="common.text.parameter.value"/>：</th>	<td><span class="intspan">';
        html += '<input type="text" id="attributeValue_' + attrIndex + '" name="attributeValue" class="int" title="" value="'+attributeValue+'"/>';
        html += '</span>';
       
        html += '</td></tr>'; 
		$('#extendAttrBody').append(html);
		attrIndex++;
	}
	
	<c:if test="${1==model.isExtendAttr}">
	var extendAttrJson = eval("("+'${model.extendAttr}'+")");
	for(extendAttrIndex in extendAttrJson){
		addExtendAttr(extendAttrJson[extendAttrIndex].attr,extendAttrJson[extendAttrIndex].value);
	};
	</c:if>
	$("#addExtendAttr").on('click',function(){
		addExtendAttr("","");
	});	
				
	$("#extendAttrBody").delegate(".delExtendTr",'click',function(){
		$("#"+$(this).attr("attrTrId")).remove();
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
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/formbased/update"
			forward="<s:Base/>/apps/list"  
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table   class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appUpdateCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
			   <table  class="datatable">
				<tbody>
				
				<tr>
					<td colspan=4><@locale code="apps.formbased.info"/></td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.redirectUri"/>：</th>
					<td colspan="3">
						<input type="text" id="redirectUri" name="redirectUri"  title="" value="${model.redirectUri}"/>
						<b class="orange">*</b><label for="redirectUri"></label>
					</td>
					
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.formbased.usernameMapping"/>：</th>
					<td style="width:35%;">
						<input type="text" id="usernameMapping" name="usernameMapping"  title="" value="${model.usernameMapping}"/>
						<b class="orange">*</b><label for="submit"></label>
					</td>
					<th style="width:15%;"><@locale code="apps.formbased.passwordMapping"/>：</th>
					<td style="width:35%;">
						<input type="text" id="passwordMapping" name="passwordMapping"  title="" value="${model.passwordMapping}"/>
						<b class="orange">*</b><label for="algorithmKey"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.credential"/>：</th>
					<td >
							<input type="radio" id="credential1" name="credential" class="credential" value="3"  <c:if test="${3==model.credential}">checked</c:if> />
							<@locale code="apps.formbased.credential.user-defined"/>
							<input type="radio" id="credential3" name="credential" class="credential" value="2"  <c:if test="${2==model.credential}">checked</c:if> />
							<@locale code="apps.formbased.credential.shared"/>
							<input type="radio" id="credential2" name="credential" class="credential" value="1"  <c:if test="${1==model.credential}">checked</c:if> />
							<@locale code="apps.formbased.credential.system"/>
						<b class="orange">*</b><label for="credential"></label>
					</td>
					<th><@locale code="apps.isAdapter"/>：</th>
					<td  >
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  <c:if test="${0==model.isAdapter}">selected</c:if> ><@locale code="apps.isAdapter.no"/></option>
							<option value="1"  <c:if test="${1==model.isAdapter}">selected</c:if> ><@locale code="apps.isAdapter.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.adapter"/>：</th>
					<td colspan =3>
						<input type="text" id="adapter" name="adapter"  title="" value="${model.adapter}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.authorizeView"/>：</th>
					<td colspan =3>
						<input type="text" id="authorizeView" name="authorizeView"  title="" value="${model.authorizeView}"/>
					</td>
				</tr>
				<tr id="systemconfigure"  <c:if test="${1!=model.credential}"> style="display:none"</c:if> >
					<th><@locale code="apps.formbased.systemUserAttr"/>：</th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr">
							<option value="uid"   <c:if test="${'uid'==model.systemUserAttr}">selected</c:if> >
								<@locale code="userinfo.uid"/></option>
							<option value="employeeNumber" <c:if test="${'employeeNumber'==model.systemUserAttr}">selected</c:if> >
								<@locale code="userinfo.employeeNumber"/></option>
							<option value="username" <c:if test="${'username'==model.systemUserAttr}">selected</c:if> >
								<@locale code="userinfo.username"/></option>
							<option value="email" <c:if test="${'email'==model.systemUserAttr}">selected</c:if> >
								<@locale code="userinfo.email"/></option>
							<option value="windowsaccount" <c:if test="${'windowsaccount'==model.systemUserAttr}">selected</c:if> >
								<@locale code="userinfo.windowsAccount"/></option>
						</select>
						<b class="orange">*</b><label for="systemUserAttr"></label>
					</td>
				</tr>
				<tr id="sharedconfigure"  <c:if test="${2!=model.credential}"> style="display:none"</c:if>>
					<th><@locale code="apps.formbased.sharedUsername"/>：</th>
					<td>
						<input type="text" id="sharedUsername" name="sharedUsername" value="${model.sharedUsername}" />
						<b class="orange">*</b><label for="sharedUsername"></label>
					</td>
					<th><@locale code="apps.formbased.sharedPassword"/>：</th>
					<td>
						<input type="text" id="sharedPassword" name="sharedPassword" value="${model.sharedPassword}" />
						<b class="orange">*</b><label for="sharedPassword"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.extendAttr"/>：</th>
					<td colspan="3">
						<input type="checkbox" id="isExtendAttr" name="isExtendAttr" value="1"  <c:if test="${1==model.isExtendAttr}">checked</c:if>  />
						<@locale code="apps.formbased.isExtendAttr"/>
						<span  id="showExtendAttr"  <c:if test="${0==model.isExtendAttr}">style="display:none"</c:if>>
							<input class="button" type="button"  value='<@locale code="button.text.add.parameter"/>' id="addExtendAttr"/>
						</span>
					</td>
				</tr>
				</tbody>
				<tbody id="extendAttrBody">
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>
    		<input class="button" id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	  
</form>