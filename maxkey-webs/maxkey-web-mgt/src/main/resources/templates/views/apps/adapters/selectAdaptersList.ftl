<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
<script type="text/javascript">	
	
	$(function () {
		$("#selectBtn").on("click",function(){
			var seldata=$.dataGridSelRowsData("#datagrid"); 
			console.log(seldata[0].id+" - "+seldata[0].name);
			$("#adapterId", window.parent.document).val(seldata[0].id);
			$("#adapterName", window.parent.document).val(seldata[0].name);
			$("#adapter", window.parent.document).val(seldata[0].adapter);
			$.closeWindow();
		 			
		});
	});
</script>
</head>
<body>
	<div id="tool_box">
	 		<table   class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="apps.adapter.name"/></td>
		 			<td width="300px">
		 				<form id="basic_search_form">
		 				     <div class="input-group" style="vertical-align: middle;">  
				 			    <input class="form-control" type="text" name="name">
				 			    <input class="form-control" type="hidden" name="protocol" style ="width:150px;float: left;" value="${protocol!}">
				 			    <input class="button btn btn-primary mr-3"  id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 		     </div>
				 		</form>
		 			</td>
				 	<td colspan="2"> 
					 	<div id="tool_box_right" style="width: auto;">
							<input  class="button btn btn-primary mr-3"   id="selectBtn" type="button" value="<@locale code="button.text.confirm"/>" >
						</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	
	<div class="mainwrap" id="main">
		<table  data-url="<@base/>/apps/adapters/grid"
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
				<th data-sortable="true" data-field="id"   data-visible="false"><@locale code="common.text.id"/></th>
				<th data-field="name"><@locale code="apps.adapter.name"/></th>
				<th data-field="protocol" ><@locale code="apps.adapter.protocol"/></th>
				<th data-field="adapter"       data-visible="false"><@locale code="apps.adapter.adapter"/></th>
				<th data-field="description"><@locale code="common.text.description"/></th>
				<th data-field="createdBy"     data-visible="false"><@locale code="common.text.createdby"/></th>
				<th data-field="createdDate"   data-visible="false"><@locale code="common.text.createddate"/></th>
				<th data-field="modifiedBy"    data-visible="false"><@locale code="common.text.modifiedby"/></th>
				<th data-field="modifiedDate"  data-visible="false"><@locale code="common.text.modifieddate"/></th>
	
			</tr>
		</thead>
	</table>
			
	</div>
	
</body>
</html>