<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
    <link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
<script type="text/javascript">	
    function dynamicFormatter(value, row, index){
        return value=='0'? '<@locale code="common.text.no" />':'<@locale code="common.text.yes" />';
    };
	$(function () {
		$("#selectBtn").on("click",function(){
			var seldata=$.dataGridSelRowsData("#datagrid"); 
			console.log(seldata[0].id+" - "+seldata[0].name);
			$(".groupId", window.parent.document).val(seldata[0].id);
			$(".groupName", window.parent.document).val(seldata[0].name);
			$.closeWindow();
		 			
		});
	});
</script>
</head>
<body>
	<div id="tool_box">
	 		<table   class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="group.name"/></td>
		 			<td width="300px">
		 				<form id="basic_search_form">
		 				     <div class="input-group" style="vertical-align: middle;">
				 			    <input class="form-control" type="text" name="name" style ="width:150px;float: left;">
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
		<table  data-url="<@base/>/groups/grid"
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
				<th data-field="name"><@locale code="group.name"/></th>
				<th data-field="dynamic"  data-formatter="dynamicFormatter"><@locale code="group.dynamic"/></th>
				<th data-field="description"><@locale code="common.text.description"/></th>
				<th data-field="createdBy"    data-visible="false"><@locale code="common.text.createdby"/></th>
				<th data-field="createdDate"  data-visible="false"><@locale code="common.text.createddate"/></th>
				<th data-field="modifiedBy"   data-visible="false"><@locale code="common.text.modifiedby"/></th>
				<th data-field="modifiedDate" data-visible="false"><@locale code="common.text.modifieddate"/></th>
	
			</tr>
		</thead>
	</table>
			
	</div>
	
</body>
</html>