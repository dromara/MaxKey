<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
<script type="text/javascript">	
	
	function afterSubmit(data){
		//$("#list").trigger('reloadGrid');
	}
	
	
	$(function () {
		$("#insertGroupUserBtn").on("click",function(){
			var selectIds = "";
			var memberName="";
			var seldata=$.dataGridSelRowsData("#datagrid"); 
			for(var arrayIndex in seldata){
				selectIds=seldata[arrayIndex].id+","+selectIds;
				memberName=seldata[arrayIndex].displayName+","+memberName;
			}
			$("#memberId").val(selectIds);
			$("#memberName").val(memberName);
			$("#submitBtn").click();
			
		});
	
	});
</script>
</head>
<body>

<div style="display:none">
	<form id="actionForm" method="post" action="<@base/>/groupMember/insert">
		<table>
			<tr><td></td><td><input type="text" id="groupId" name="groupId" value="${group.id}"/></td></tr>
			<tr><td></td><td><input type="text" id="groupName" name="groupName" value="${group.name}"/></td></tr>
			<tr><td></td><td><input type="text" id="memberId" name="memberId" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="memberName" name="memberName" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitBtn" type="button" value="submit"></input></td></tr>
		</table>
	</form>
</div>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><@locale code="userinfo.username"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input type="text" name="name" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> 
					 	<div >
							<input class="button"  id="insertGroupUserBtn" type="button" value="<@locale code="button.text.add"/>">
					 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>

 	    
 	   
	<div class="mainwrap" id="main">
		<table  data-url="<@base/>/groupMember/queryMemberNotInGroup?groupId=${groupId}"
			id="datagrid"
				data-toggle="table"
				data-classes="table table-bordered table-hover table-striped"
				data-click-to-select="true"
				data-pagination="true"
				data-total-field="records"
				data-page-list="[10, 25, 50, 100]"
				data-search="false"
				data-locale="zh-CN"
				data-query-params="dataGridQueryParams"
				data-query-params-type="pageSize"
				data-side-pagination="server">
		<thead>
			<tr>
				<th data-checkbox="true"></th>
				<th data-sortable="true" data-field="id"   data-visible="false">Id</th>
				<th data-field="username"><@locale code="userinfo.username"/></th>
				<th data-field="displayName"><@locale code="userinfo.displayName"/></th>
				<th data-field="createdBy"><@locale code="common.text.createdby"/></th>
				<th data-field="createdDate"><@locale code="common.text.createddate"/></th>
				<th data-field="modifiedBy"><@locale code="common.text.modifiedby"/></th>
				<th data-field="modifiedDate"><@locale code="common.text.modifieddate"/></th>
	
			</tr>
		</thead>
	</table>
	</div>
</body>
</html>