<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>

<div id="main" class="container">
	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td  width="120px">
			 		 <@locale code="apps.name"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input  class="form-control"  name="appName" type="text" style ="width:150px;float:left;">
				 			<input class="button btn btn-primary mr-3"  id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			<input class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
					 	</form>
				</td>
				<td colspan="2"> 
					 
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table   class="datatable">
			 <tr>
	 				<td width="120px"><@locale code="common.text.startdate"/></td>
		 			<td width="360px">
		 				<input  class="form-control datetimepicker"  name="startDate" type="text" >
		 			</td>
		 			<td width="120px"><@locale code="common.text.enddate"/></td>
		 			<td width="360px">
						<input  class="form-control datetimepicker"  style="width:70%" type="text" id="endDate" name="endDate"  title="" value=""/>
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>

<div  class="mainwrap" id="main">
		<table 	data-url="<@base />/historys/loginAppsList/grid"
				id="datagrid"
				data-toggle="table"
				data-classes="table table-bordered table-hover table-striped"
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
					<th data-sortable="true" data-field="id"  data-visible="false"><@locale code="log.loginappshistory.id" /></th>
					<th data-field="sessionId" ><@locale code="log.loginappshistory.sessionId" /></th>
					<th data-field="uid"   data-visible="false"><@locale	code="log.loginappshistory.uid" /></th>
					<th data-field="username" ><@locale	code="log.loginappshistory.username" /></th>
					<th data-field="displayName" ><@locale	code="log.loginappshistory.displayName" /></th>
					<th data-field="appId"   data-visible="false"><@locale	code="log.loginappshistory.appId" /></th>
					<th data-field="appName" ><@locale	code="log.loginappshistory.appName" /></th>
					<th data-field="loginTime" ><@locale  code="log.loginappshistory.loginTime" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
</div>
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
</body>
</html>