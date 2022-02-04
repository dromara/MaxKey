<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">	

	</script>
</head>
<body> 
<div class="app header-default side-nav-dark">
<div class="layout">
	<div class="header navbar">
		<#include  "../layout/top.ftl"/>
	</div>
	
	<div class="col-md-3 sidebar-nav side-nav" >
		<#include  "../layout/sidenav.ftl"/>
	</div>
	<div class="page-container">
	
	<div class="main-content">
					<div class="container-fluid">

						<div class="breadcrumb-wrapper row">
							<div class="col-12 col-lg-3 col-md-6">
								<h4 class="page-title"><@locale code="navs.audit.connector"/></h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
									<li class="inactive" >/ <@locale code="navs.audit"/></li>
									<li class="active">/ <@locale code="navs.audit.connector"/></li>
								</ol>
							</div>
						</div>

					</div>
					<div class="container-fluid">
					<div class="content-wrapper row">
					<div class="col-12 grid-margin">
						<div class="card">
							<div class="card-body">
							
	<div id="tool_box">
		<table   class="table table-bordered">
			<tr>
				<td  width="120px" class="align-middle">
			 		 <@locale code="log.connector.id"/>
				</td>
				<td  width="375px">
					<form id="basic_search_form">
					   <div class="input-group" style="vertical-align: middle;">
				 			<input  class="form-control"  name="id" type="text" >
				 			<input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			<input  class="button btn btn-primary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
				        </div>
				    </form>
				</td>
				<td colspan="2"> 
					 <div id="tool_box_right">
					 	
					</div>
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table    class="table table-bordered">
	 			<tr>
	 				<td width="120px"><@locale code="log.connector.objectName"/></td>
		 			<td width="360px">
		 				<input  class="form-control"  name="objectName" type="text" >
		 			</td>
		 			<td width="120px"><@locale code="log.connector.result"/></td>
		 			<td width="360px">
						<input class="form-control"  type="text" id="result" name="result"  title="" value=""/>
			 			</td>
		 			
			 </tr>
			 <tr>
					<td width="120px"><@locale code="common.text.startdate"/></td>
		 			<td width="360px">
			 			<input  class="form-control datetimepicker"    type="text" id="startDate" name="startDate"  title="" value=""/>
			 		</td>
	 				<td width="120px"><@locale code="common.text.enddate"/></td>
		 			<td width="360px">
		 				<input  class="form-control datetimepicker"    type="text" id="endDate" name="endDate"  title="" value=""/>
		 			</td>
		 			
			 </tr>
			</table>
 		</form>
 	</div>
 	<table 	data-url="<@base />/historys/connectorHistoryList/grid"
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
					<th data-sortable="true" data-field="id"  data-visible="false"><@locale code="log.connector.id" /></th>
					<th data-field="conName"  ><@locale	code="log.connector.conName" /></th>
					<th data-field="conType" ><@locale  code="log.connector.conType" /></th>
                    <th data-field="sourceId"  ><@locale    code="log.connector.sourceId" /></th>
                    <th data-field="syncName"  ><@locale    code="log.connector.sourceName" /></th>
					<th data-field="sourceName" ><@locale	code="log.connector.objectId" /></th>
					<th data-field="objectName" ><@locale	code="log.connector.objectName" /></th>
					<th data-field="syncTime" ><@locale	code="log.connector.syncTime" /></th>
					<th data-field="result" ><@locale  code="log.connector.result" /></th>
				</tr>
			</thead>
		</table>
</div>
	
</div>
</div>
					</div>
<footer class="content-footer">
					<#include  "../layout/footer.ftl"/>
</footer>

	</div>
	
	</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>