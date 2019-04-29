<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page 	import="org.maxkey.constants.*"%>
<script type="text/javascript">
<!--
$(function(){		
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
		var html = '<tr  id="extendTr_' + attrIndex + '"><th><s:Locale code="common.text.parameter"/>：';   
		 html += '<input  class="button delExtendTr"  type="button" name="delExtendTr" attrTrId="extendTr_'+attrIndex+'" value="<s:Locale code="button.text.delete" />"/>';
		html += '</th><td>';   
		html += '<input type="text" id="attribute_' + attrIndex + '" name="attribute" class="int" title="" value="'+attribute+'"/>';   
        html += '</span></td><th><s:Locale code="common.text.parameter.value"/>：</th>	<td><span class="intspan">';
        html += '<input type="text" id="attributeValue_' + attrIndex + '" name="attributeValue" class="int" title="" value="'+attributeValue+'"/>';
        html += '</span>';
       
        html += '</td></tr>'; 
		$('#extendAttrBody').append(html);
		attrIndex++;
	}
	
	$("#addExtendAttr").on('click',function(){
		addExtendAttr("","");
	});	
				
	$("#extendAttrBody").delegate(".delExtendTr",'click',function(){
		$("#"+$(this).attr("attrTrId")).remove();
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
			action="<s:Base/>/apps/formbased/add"
			forward="<s:Base/>/apps/list"  
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appAddCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
			   <table class="datatable" >
				<tbody>
				
				<tr>
					<td colspan=4><s:Locale code="apps.formbased.info"/></td>
				</tr>
				<tr>
					<th><s:Locale code="apps.formbased.redirectUri"/>：</th>
					<td colspan="3">
						<input type="text" id="redirectUri" name="redirectUri"  title="" value=""/>
						<b class="orange">*</b><label for="redirectUri"></label>
					</td>
					
				</tr>
				<tr>
					<th style="width:15%;"><s:Locale code="apps.formbased.usernameMapping"/>：</th>
					<td style="width:35%;">
						<input type="text" id="usernameMapping" name="usernameMapping"  title="" value="username"/>
						<b class="orange">*</b><label for="usernameMapping"></label>
					</td>
					<th style="width:15%;"><s:Locale code="apps.formbased.passwordMapping"/>：</th>
					<td style="width:35%;">
						<input type="text" id="passwordMapping" name="passwordMapping"  title="" value="password"/>
						<b class="orange">*</b><label for="passwordMapping"></label>
					</td>
				</tr>
				
				<tr>
					<th width="140px"><s:Locale code="apps.formbased.credential"/>：</th>
					<td>
						<input type="radio" id="credential-user-defined" name="credential" class="credential" value="3"  checked />
						<s:Locale code="apps.formbased.credential.user-defined"/>
						<input type="radio" id="credential-shared" name="credential" class="credential"  value="2" />
						<s:Locale code="apps.formbased.credential.shared"/>
						<input type="radio" id="credential-system" name="credential" class="credential"  value="1"  />
						<s:Locale code="apps.formbased.credential.system"/>
						<b class="orange">*</b><label for="credential"></label>
					</td>
					<th><s:Locale code="apps.isAdapter"/>：</th>
					<td>
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  selected><s:Locale code="apps.isAdapter.no"/></option>
							<option value="1"><s:Locale code="apps.isAdapter.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.adapter"/>：</th>
					<td colspan =3>
						<input type="text" id="adapter" name="adapter"  title="" value=""/>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.formbased.authorizeView"/>：</th>
					<td colspan =3>
						<input type="text" id="authorizeView" name="authorizeView"  title="" value=""/>
					</td>
				</tr>
				<tr id="systemconfigure"  style="display:none">
					<th><s:Locale code="apps.formbased.systemUserAttr"/>：</th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr">
							<option value="uid">
								<s:Locale code="userinfo.uid"/></option>
							<option value="employeeNumber">
								<s:Locale code="userinfo.employeeNumber"/></option>
							<option value="username"  selected>
								<s:Locale code="userinfo.username"/></option>
							<option value="email">
								<s:Locale code="userinfo.email"/></option>
							<option value="windowsaccount">
								<s:Locale code="userinfo.windowsAccount"/></option>
						</select>
						<b class="orange">*</b><label for="systemUserAttr"></label>
					</td>
				</tr>
				<tr id="sharedconfigure"  style="display:none">
					<th><s:Locale code="apps.formbased.sharedUsername"/>：</th>
					<td>
						<input type="text" id="sharedUsername" name="sharedUsername" value="" />
						<b class="orange">*</b><label for="sharedUsername"></label>
					</td>
					<th><s:Locale code="apps.formbased.sharedPassword"/>：</th>
					<td>
						<input type="text" id="sharedPassword" name="sharedPassword" value="" />
						<b class="orange">*</b><label for="sharedPassword"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.formbased.extendAttr"/>：</th>
					<td colspan="3">
						<input type="checkbox" id="isExtendAttr" name="isExtendAttr" value="1"/>
						<s:Locale code="apps.formbased.isExtendAttr"/>
						<span  id="showExtendAttr" style="display:none">
							<input  class="button"  type="button"  value='<s:Locale code="button.text.add.parameter"/>' id="addExtendAttr"/>
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

			<input  id="status" type="hidden" name="status"  value="1"/>
    		<input class="button" id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>