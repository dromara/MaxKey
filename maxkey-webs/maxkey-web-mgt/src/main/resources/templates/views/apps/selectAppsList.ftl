<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
    <link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
  
<script type="text/javascript">				
	function iconFormatter(value, row, index){
  			return "<img height='30' border='0px' src='<@base/>/image/"+value+"'/>";
	};

	$(function () {
		$("#selectBtn").on("click",function(){
			var seldata=$.dataGridSelRowsData("#datagrid"); 
			console.log(seldata[0].id+" - "+seldata[0].name);
			$(".appId", window.parent.document).val(seldata[0].id);
			$(".appName", window.parent.document).val(seldata[0].name);
			$.closeWindow();
		 			
		});
	      
	});
</script>
</head>
<body>	
	<div>
			<table    class="table table-bordered" >
 				<tr>
		 			<td width="120px"><@locale code="apps.name"/>:</td>
		 			<td  width="300px" nowrap>
		 				<form id="basic_search_form">
			 				<input type="text" class="form-control" name="name" >
			 				<input type="hidden" class="form-control" name="accountMgmt" value="${accountMgmt}">
		 				 </form>
		 			</td>
		 			<td colspan="2">
		 				<div id="tool_box_right">    
		 				    <input class="button btn btn-primary mr-3"  id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
		 					<input class="button btn btn-success mr-3"   id="selectBtn" type="button" value="<@locale code="button.text.select"/>" />
				 		</div>
		 			</td>
		 		</tr>
		 	</table>
 	</div>
 	
 	<div id="advanced_search">
 		
 	</div>
	<table  data-url="<@base/>/apps/grid"
			id="datagrid"
				data-toggle="table"
				data-classes="table table-bordered table-hover table-striped"
				data-click-to-select="true"
				data-pagination="true"
				data-total-field="records"
				data-page-list="[5, 10, 25, 50]"
				data-search="false"
				data-locale="<@locale/>"
				data-query-params="dataGridQueryParams"
				data-query-params-type="pageSize"
				data-side-pagination="server">
		<thead>
			<tr>
				<th data-checkbox="true"></th>
				<th data-sortable="true" data-field="id"   data-visible="false">Id</th>
				<th data-field="id" data-formatter="iconFormatter"><@locale code="apps.icon"/></th>
				<th data-field="name"><@locale code="apps.name"/></th>
				<th data-field="protocol"  data-visible="false"><@locale code="apps.protocol"/></th>
				<th data-field="category"><@locale code="apps.category"/></th>
				<th data-field="vendor"  data-visible="false"><@locale code="apps.vendor"/></th>
				<th data-field="loginUrl" data-visible="false"><@locale code="log.loginhistory.loginUrl"/></th>
	
			</tr>
		</thead>
	</table>
</body>
</html>