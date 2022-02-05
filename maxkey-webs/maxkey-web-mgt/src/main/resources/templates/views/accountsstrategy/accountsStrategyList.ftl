<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">	
		function dynamicFormatter(value, row, index){
            return value=='0'? '<@locale code="common.text.no" />':'<@locale code="common.text.yes" />';
        };
        function createTypeFormatter(value, row, index){
            return value=='manual'? 
                '<@locale code="accounts.strategy.createType.manual" />'
                :'<@locale code="accounts.strategy.createType.automatic" />';
        };
        
        function iconFormatter(value, row, index){
            return "<img width='30'  border='0px' src='<@base/>/image/"+value+"'/>";
        };
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
					<h4 class="page-title"><@locale code="navs.roles"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="active">/ <@locale code="navs.roles"/></li>
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
			<table  class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="accounts.strategy.name"/></td>
		 			<td width="375px">
		 				<form id="basic_search_form">
		 				     <div class="input-group" style="vertical-align: middle;">
			 				    <input class="form-control" type="text" name="name" >
			 				    <input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			    <!--<input  class="button btn btn-primary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
					 		    -->
					 		 </div>
					 	</form>
		 			</td>
		 			<td colspan="2">
		 				<div id="tool_box_right">
		 					 <input class="button btn btn-primary mr-3" id="addBtn" type="button" value="<@locale code="button.text.add"/>" 
						 		    wurl="<@base/>/accountsstrategy/forwardAdd"
						 		    wwidth="800"
						 		    wheight="580"
					 		    	target="window">	    	
					 		    	
					 	<input class="button btn btn-primary mr-3 " id="modifyBtn" type="button" value="<@locale code="button.text.edit"/>" 
					 				wurl="<@base/>/accountsstrategy/forwardUpdate"
					 				wwidth="800"
						 		    wheight="550"
					 		    	target="window"> 
					 		    	
					 	<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete"/>"
					 				wurl="<@base/>/accountsstrategy/delete" />
						</div>
		 			</td>
		 		</tr>
		 	</table>
		
    		 <div id="advanced_search">
                <form id="advanced_search_form">
                    
                </form>
            </div>
        </div>
            <table  data-url="<@base/>/accountsstrategy/grid"
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
                    <th data-field="name"><@locale code="accounts.strategy.name"/></th>
                    <th data-field="id"  data-formatter="iconFormatter"><@locale code="apps.icon"/></th>
                    <th data-field="appId" data-visible="false"><@locale code="apps.id"/></th>
                    <th data-field="appName" ><@locale code="apps.name"/></th>
                    <th data-field="createType"  data-formatter="createTypeFormatter" ><@locale code="accounts.strategy.createType" /></th>
                    <th data-field="status"  data-formatter="dynamicFormatter"><@locale code="common.text.status"/></th>
                    <th data-field="description"   data-visible="false"><@locale code="common.text.description"/></th>
                    <th data-field="createdBy"    data-visible="false"><@locale code="common.text.createdby"/></th>
                    <th data-field="createdDate"  data-visible="false"><@locale code="common.text.createddate"/></th>
                    <th data-field="modifiedBy"   data-visible="false"><@locale code="common.text.modifiedby"/></th>
                    <th data-field="modifiedDate" data-visible="false"><@locale code="common.text.modifieddate"/></th>
        
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