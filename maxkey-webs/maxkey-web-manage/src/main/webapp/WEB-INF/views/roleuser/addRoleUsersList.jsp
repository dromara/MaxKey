<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">	
	
	function afterSubmit(data){
		$("#list").trigger('reloadGrid');
	}
	
	
	$(function () {
		$("#insertGroupUserBtn").on("click",function(){
			var selectIds = $("#list").jqGrid("getGridParam", "selarrrow");
			if(selectIds == null || selectIds == "") {
				$.alert({content:$.platform.messages.select.alertText});
				return false;
			}
			var memberName="";
			for(var i=0;i<selectIds.length;i++){
				memberName+=$("#list").jqGrid("getRowData",selectIds[i]).username+",";
			}
			
			$("#uid").val(selectIds);
			$("#userName").val(memberName);
			$("#submitBtn").click();
			
		});
	
	});
</script>
<div style="display:none">
	<form id="actionForm" method="post" action="<s:Base/>/roleUser/insertRoleUser">
		<table>
			<tr><td></td><td><input type="text" id="roleId" name="roleId" value="${roleId}"/></td></tr>
			<tr><td></td><td><input type="text" id="roleName" name="roleName" value="${roleName}"/></td></tr>
			<tr><td></td><td><input type="text" id="uid" name="uid" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="userName" name="userName" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitBtn" type="button" value="submit"></input></td></tr>
		</table>
	</form>
</div>
	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="userinfo.username"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input type="text" name="name" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 			</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right"  style="width: auto;">
				 		<input class="button"  id="insertGroupUserBtn" type="button" value="<s:Locale code="button.text.add"/>">
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	
	<div class="mainwrap" id="main">
			<s:Grid id="list" url="/roleUser/roleUserNotInRoleGrid?roleId=${roleId}" rowLimit="10" multiselect="true" resize="false">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="200" field="username" title="userinfo.username"/>
				<s:Column width="225" field="displayName" title="userinfo.displayName" />
				<s:Column width="200" field="department" title="userinfo.department"/>
				<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
				<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
				<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
				<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
			</s:Grid>
	</div>