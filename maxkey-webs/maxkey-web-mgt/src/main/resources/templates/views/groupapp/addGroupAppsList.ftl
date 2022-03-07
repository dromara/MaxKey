<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
    <link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
    <style>
    .bootstrap-table .fixed-table-container .table th, .bootstrap-table .fixed-table-container .table td{
        padding: .2rem;
    }
   </style>
<script type="text/javascript">	
			function afterSubmit(data){
				//$("#list").trigger('reloadGrid');
			}
			
			function iconFormatter(value, row, index){
  			   return "<img height='30' border='0px' src='"+value+"'";
			};
		   	
			$(function () {
				$("#addGroupAppsBtn").on("click",function(){
					var selectIds = "";
					var seldata=$.dataGridSelRowsData("#datagrid"); 
					for(var arrayIndex in seldata){
						selectIds=seldata[arrayIndex].id+","+selectIds;
					}
					$("#appId").val(selectIds);
					$("#actionForm").attr("action","<@base/>/groupPrivileges/insert");
					$("#submitBtn").click();
				});
			
			});
		</script>
</head>
<body>
<div style="display:none">
	<form id="actionForm" method="post" action="<@base/>/groupPrivileges/insert"  class="needs-validation" novalidate>
		<table>
			<tr><td></td><td><input type="text" id="groupId" name="groupId" value="${groupId}"/></td></tr>
			<tr><td></td><td><input type="text" id="appId" name="appId" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitBtn" type="submit" value="submit"></input></td></tr>
		</table>
	</form>
</div>

	<div id="tool_box">
	 		<table   class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="apps.name"/></td>
		 			<td width="300px" nowrap>
		 				<form id="basic_search_form">
                            <div class="input-group" style="vertical-align: middle;">  
				 			    <input class="form-control" type="text" name="name" >
				 			    <input class="button btn btn-primary mr-3"  id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">		
				 		     </div>
				 		</form>
		 			</td>
				 	<td colspan="2"> 
					 	<div id="tool_box_right">
							<input class="button btn btn-primary mr-3"  id="addGroupAppsBtn" type="button" value="<@locale code="button.text.confirm" />">
					 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	<div id="advanced_search">
		<form id="advanced_search_form">
 		<table   class="datatable">
	 			<tr>
		 			<td width="120px"><@locale code="apps.name"/></td>
		 			<td width="360px">
		 				
		 			</td>
		 			<td width="120px"><@locale code="apps.protocol"/></td>
		 			<td width="360px">
		 				<select name="protocol" class="select_protocol">
		 					<option value=""  selected>Select</option>
		 					<option value="<%=PROTOCOLS.FORMBASED%>"><%=PROTOCOLS.FORMBASED%></option>
		 					<option value="<%=PROTOCOLS.OPEN_ID_CONNECT%>"><%=PROTOCOLS.OPEN_ID_CONNECT%></option>
		 					<option value="<%=PROTOCOLS.OAUTH10A%>"><%=PROTOCOLS.OAUTH10A%></option>
		 					<option value="<%=PROTOCOLS.OAUTH20%>"><%=PROTOCOLS.OAUTH20%></option>
		 					<option value="<%=PROTOCOLS.SAML11%>"><%=PROTOCOLS.SAML11%></option>
		 					<option value="<%=PROTOCOLS.SAML20%>"><%=PROTOCOLS.SAML20%></option>
		 					<option value="<%=PROTOCOLS.COOKIEBASED%>"><%=PROTOCOLS.COOKIEBASED%></option>
		 					<option value="<%=PROTOCOLS.TOKENBASED%>"><%=PROTOCOLS.TOKENBASED%></option>
		 					<option value="<%=PROTOCOLS.DESKTOP%>"><%=PROTOCOLS.DESKTOP%></option>
		 					<option value="<%=PROTOCOLS.BASIC%>"><%=PROTOCOLS.BASIC%></option>
		 					
		 				</select>
		 			</td>
	 			</tr>
	 		</table>
 		
 		</form>
 	</div>

	<div class="mainwrap" id="main">
	<table  data-url="<@base/>/groupPrivileges/queryAppsNotInGroup?groupId=${groupId}"
			id="datagrid"
				data-toggle="table"
				data-classes="table table-bordered table-hover table-striped"
				data-click-to-select="true"
				data-pagination="true"
				data-total-field="records"
				data-page-list="[5, 10, 25, 50]"
				data-search="false"
				data-sort-name="name"
				data-locale="zh-CN"
				data-query-params="dataGridQueryParams"
				data-query-params-type="pageSize"
				data-side-pagination="server">
		<thead>
			<tr>
				<th data-checkbox="true"></th>
				<th data-sortable="true" data-field="id"   data-visible="false">Id</th>
				<th data-field="iconBase64" data-formatter="iconFormatter"><@locale code="apps.icon"/></th>
				<th data-field="name"><@locale code="apps.name"/></th>
				<th data-field="protocol"  data-visible="false"><@locale code="apps.protocol"/></th>
				<th data-field="category"><@locale code="apps.category"/></th>
				<th data-field="vendor"  data-visible="false"><@locale code="apps.vendor"/></th>
				<th data-field="loginUrl" data-visible="false"><@locale code="log.loginhistory.loginUrl"/></th>
	
			</tr>
		</thead>
	</table>
	</div>
</body>
</html>