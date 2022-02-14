<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div  id="main"  class="container">
	<div id="tool_box">
		<table  class="table table-bordered">
			<tr>
				<td  width="120px">
				<@locale code="log.loginhistory.sourceIp"/>
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input class="form-control" name="sourceIp" type="text" style ="width:150px;float:left;">
				 			<input class="button btn btn-primary mr-3"  id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			<input class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
					 	</form>
				</td>
				<td colspan="2"> 
				    <div id="tool_box_right">
					   <input id="deleteBtn" type="button" class="button btn btn-danger mr-3 "   
					       value="<@locale code="button.text.terminate"/>" 
					       wurl="<@base/>/session/terminate" />
				    </div>
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table   class="table table-bordered">
			 <tr>
	 				<td width="120px"><@locale code="common.text.startdate"/></td>
		 			<td width="360px">
		 				<input class="datetimepicker form-control" name="startDate" type="text" >
		 			</td>
		 			<td width="120px"><@locale code="common.text.enddate"/></td>
		 			<td width="360px">
						<input style="width:70%"  class="datetimepicker form-control"  type="text" id="endDate" name="endDate"  title="" value=""/>
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>
 	
<div class="mainwrap" id="main">

	<table  data-url="<@base />/session/sessionList/grid"
			id="datagrid"
			data-toggle="table"
			data-classes="table table-bordered table-hover table-striped"
			data-pagination="true"
			data-click-to-select="true"
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
				<th data-sortable="true" data-field="id"   data-visible="false"><@locale code="log.loginhistory.id"/></th>
				<th data-field="sessionId"><@locale code="log.loginhistory.sessionId"/></th>
				<th data-field="username"><@locale code="log.loginhistory.username"/></th>
				<th data-field="displayName"><@locale code="log.loginhistory.displayName"/></th>
				<th data-field="provider"><@locale code="log.loginhistory.provider"/></th>
				<th data-field="message"><@locale code="log.loginhistory.message"/></th>
				<th data-field="loginType"><@locale code="log.loginhistory.loginType"/></th>
				<th data-field="sourceIp"><@locale code="log.loginhistory.sourceIp"/></th>
				<th data-field="browser"  data-visible="false"><@locale code="log.loginhistory.browser"/></th>
				<th data-field="loginTime"><@locale code="log.loginhistory.loginTime"/></th>
				<th data-field="logoutTime"><@locale code="log.loginhistory.logoutTime"/></th>
				<th data-field="platform"  data-visible="false"><@locale code="log.loginhistory.platform"/></th>
				<th data-field="application" data-visible="false"><@locale code="log.loginhistory.application"/></th>
				<th data-field="loginUrl" data-visible="false"><@locale code="log.loginhistory.loginUrl"/></th>
				<th data-field="code" data-visible="false"><@locale code="log.loginhistory.code"/></th>
				<th data-field="rpUserInfo" data-visible="false"><@locale code="log.loginhistory.rpUserInfo"/></th>
	
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