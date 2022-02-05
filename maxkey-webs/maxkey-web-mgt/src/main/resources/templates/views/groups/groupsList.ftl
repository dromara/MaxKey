<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">	
		function dynamicFormatter(value, row, index){
	  		return value=='0'? '<@locale code="common.text.no" />':'<@locale code="common.text.yes" />';
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
					<h4 class="page-title"><@locale code="navs.groups"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="active">/ <@locale code="navs.groups"/></li>
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
		 			<td width="120px"><@locale code="group.name"/></td>
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
						 		    wurl="<@base/>/groups/forwardAdd"
						 		    wwidth="550"
						 		    wheight="620"
					 		    	target="window">	    	
					 		    	
					 	<input class="button btn btn-primary mr-3 " id="modifyBtn" type="button" value="<@locale code="button.text.edit"/>" 
					 				wurl="<@base/>/groups/forwardUpdate"
					 				wwidth="550"
						 		    wheight="620"
					 		    	target="window"> 
					 		    	
					 	<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete"/>"
					 				wurl="<@base/>/groups/delete" />
						</div>
		 			</td>
		 		</tr>
		 	</table>
		      <div id="advanced_search">
                <form id="advanced_search_form">
                    
                </form>
            </div>
		 </div>		
		 
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