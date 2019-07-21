<%@ page 	contentType="text/html; charset=UTF-8"%>
<%@ page 	import="org.maxkey.domain.*"%> 
<%@ page 	import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page 	import="org.maxkey.web.*"%>
<%@ taglib  prefix="s"   		uri="http://sso.maxkey.org/tags" %>
<%@ taglib  prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML >
<html>
<head>
	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/common.cssjs.jsp"></jsp:include>
	<script type="text/javascript" src="<s:Base/>/jquery/jsonformatter.js"></script>
</head>
<body>
<jsp:include page="../layout/top.jsp"></jsp:include>
<jsp:include page="../layout/nav_primary.jsp"></jsp:include>

<div class="container">
	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td  width="120px">
			 		 <s:Locale code="apps.name"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input  class="form-control"  name="appName" type="text" style ="width:150px;float:left;">
				 			<input class="button btn btn-primary mr-3"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 			<input class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
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
	 				<td width="120px"><s:Locale code="common.text.startdate"/></td>
		 			<td width="360px">
		 				<input  class="form-control datetimepicker"  name="startDate" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="common.text.enddate"/></td>
		 			<td width="360px">
						<input  class="form-control datetimepicker"  style="width:70%" type="text" id="endDate" name="endDate"  title="" value=""/>
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>

<div  class="mainwrap" id="main">
		<table 	data-url="<s:Base />/logs/loginAppsHistory/grid"
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
					<th data-sortable="true" data-field="id"  data-visible="false"><s:Locale code="log.loginappshistory.id" /></th>
					<th data-field="sessionId" ><s:Locale code="log.loginappshistory.sessionId" /></th>
					<th data-field="uid"   data-visible="false"><s:Locale	code="log.loginappshistory.uid" /></th>
					<th data-field="username" ><s:Locale	code="log.loginappshistory.username" /></th>
					<th data-field="displayName" ><s:Locale	code="log.loginappshistory.displayName" /></th>
					<th data-field="appId"   data-visible="false"><s:Locale	code="log.loginappshistory.appId" /></th>
					<th data-field="appName" ><s:Locale	code="log.loginappshistory.appName" /></th>
					<th data-field="loginTime" ><s:Locale	code="log.loginappshistory.loginTime" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
</div>
<div id="footer">
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</div>
</body>
</html>