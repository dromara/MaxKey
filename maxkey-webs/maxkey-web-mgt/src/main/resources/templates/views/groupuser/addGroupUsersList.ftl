<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
    <link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
<script type="text/javascript">	
    function genderFormatter(value, row, index){
        if(value==1){
            return '<@locale code="userinfo.gender.female" />';
        }else{
            return '<@locale code="userinfo.gender.male" />';
        }
    };
    	
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
	<form id="actionForm" method="post" action="<@base/>/groupMember/insert"  class="needs-validation" novalidate>
		<table>
			<tr><td></td><td><input type="text" id="groupId" name="groupId" value="${group.id}"/></td></tr>
			<tr><td></td><td><input type="text" id="groupName" name="groupName" value="${group.name}"/></td></tr>
			<tr><td></td><td><input type="text" id="memberId" name="memberId" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="memberName" name="memberName" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitBtn" type="submit" value="submit"></input></td></tr>
		</table>
	</form>
</div>

	<div id="tool_box">
	 		<table   class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="userinfo.username"/></td>
		 			<td width="300px">
		 				<form id="basic_search_form">
		 				     <div class="input-group" style="vertical-align: middle;">  
				 			    <input class="form-control" type="text" name="username">
				 			    <input class="button btn btn-primary mr-3" id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 		     </div>
				 		</form>
		 			</td>
				 	<td colspan="2"> 
					 	<div id="tool_box_right">
							<input class="button btn btn-primary mr-3"   id="insertGroupUserBtn" type="button" value="<@locale code="button.text.confirm"/>">
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
                <th data-field="gender"  data-formatter="genderFormatter" ><@locale code="userinfo.gender"/></th>
                <th data-field="userType"><@locale code="userinfo.userType"/></th>
                <th data-field="jobTitle"><@locale code="userinfo.jobTitle"/></th>
                <th data-field="department"><@locale code="userinfo.department"/></th>
	
			</tr>
		</thead>
	</table>
	</div>
</body>
</html>