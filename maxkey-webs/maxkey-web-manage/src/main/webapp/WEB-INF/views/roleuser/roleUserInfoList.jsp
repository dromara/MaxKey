<%@ page 	contentType="text/html; charset=UTF-8" 	import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib  prefix="s"  uri="http://www.connsec.com/tags" %>
<%@ taglib  prefix="spring"    uri="http://www.springframework.org/tags"%>
<%@ taglib  prefix="c"		   uri="http://java.sun.com/jsp/jstl/core" %>
	<script type="text/javascript">
	
		function afterSubmit(data){
			$("#list").trigger('reloadGrid');
		}
		
		function beforeWindow(data){ 
			$("#addRoleUsersBtn").attr("wurl",'<s:Base/>/roleUser/addRoleUsersList?roleId='+$("#roleId").val());
		}
		
		
		$(function () {
			$("#roleUsersSaveBtn").click(function(){
				var selectIds = $("#list").jqGrid("getGridParam", "selarrrow");
				if(selectIds == null || selectIds == "") {
					$.alert({content:$.platform.messages.select.alertText});
					return false;
				}
				$("#uid").val(selectIds);
				$("#submitBtn").click();
				//$("#uid").val($("#list").jqGrid("getGridParam", "selarrrow"));
			});
		});
	</script>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="role.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
		 					<input type="hidden" class="roleId"  name="roleId"   id="roleId" value=""  style ="width:150px">
				 			<input type="text" class="roleName"  name="roleName" style ="width:150px">
				 			<s:Dialog text="button.text.select" title="Roles" url="/roles/selectRolesList" width="700" height="500" />
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
							<input class="button window" id="addRoleUsersBtn" type="button" value="<s:Locale code="button.text.add.member"/>" 
			 		    		wurl="<s:Base/>/roleUser/addRoleUsersList" wwidth="700px" wheight="500px" />
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/roleUser/roleUserByRoleGrid" multiselect="true" resize="true">	
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