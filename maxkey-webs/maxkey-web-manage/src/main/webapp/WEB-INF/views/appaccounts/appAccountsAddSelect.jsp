<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">	
	
	function afterSubmit(data){
		$("#list").trigger('reloadGrid');
	}
	
	
	$(function () {
		$("#selectUserBtn").on("click",function(){
			var selData=$.gridRowData("#list",$.gridSel("#list"));
			if(selData!=null){
				$("#uid").val(selData.id);
				$("#username").val(selData.username);
				$("#displayName").val(selData.displayName);
				$("#submitFormBtn").click();
			}
			
		});
	
	});
</script>
<div style="display:none">
	<form  method="post" action="<s:Base/>/app/accounts/forwardAdd">
		<table>
			<tr><td></td><td><input type="text" id="appId" name="appId" value="${appId}"/></td></tr>
			<tr><td></td><td><input type="text" id="uid" name="uid" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="username" name="username" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="displayName" name="displayName" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitFormBtn" type="submit" value="submit"></input></td></tr>
		</table>
	</form>
</div>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="userinfo.username"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input type="text" name="username" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 			
				 		</form>
		 			</td>
				 	<td colspan="2"> <div >
				 		<input class="button"  id="selectUserBtn" type="button" value="<s:Locale code="button.text.select"/>">
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>

	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/users/grid" resize="false" rowLimit="10" rowList="[10]">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="200" field="username" title="userinfo.username"/>
			<s:Column width="250" field="displayName" title="userinfo.displayName" />
			<s:Column width="200" field="department" title="userinfo.department"/>
			<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
			<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
			<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
			<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
		</s:Grid>
	</div>