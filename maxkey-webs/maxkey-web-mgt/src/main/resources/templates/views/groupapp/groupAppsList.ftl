<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
<script type="text/javascript">	
	
	function iconFormatter(value, row, index){
  			return "<img height='30' border='0px' src='"+value+"'";
	};
   	
	$(function () {
		$("#addGroupAppsBtn").on("click",function(){
			if($("#groupId").val()==""){
				$.alert({content:$.platform.messages.select.alertText});	
				return;
			}
			var settings={
					url		:	"<@base/>/groupPrivileges/addGroupAppsList/"+$("#groupId").val(),//window url
					title	:	"New",//title
					width	:	"700",//width
					height	:	"560"//height
				};
			$.window(settings);//open window
		});
	});
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
								<h4 class="page-title"><@locale code="navs.groups.privileges"/></h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
									<li class="inactive" >/ <@locale code="navs.groups"/></li>
									<li class="active">/ <@locale code="navs.groups.privileges"/></li>
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
		 			<td width="120px"><@locale code="group.name"/></td>
		 			<td  width="450px">
		 				<form id="basic_search_form">
		 				    <div class="input-group" style="vertical-align: middle;">
				 			    <input class="groupId" id="groupId" name="groupId" type="hidden" >
				 			    <input class="form-control groupName "    id="groupName" name="groupName" type="text" >
				 			
				 			    <input class="window button btn btn-primary mr-3" type="button" value="<@locale code="button.text.select"/>" 
						 		    wurl="<@base/>/groups/selectGroupsList"
						 		    wwidth="700"
						 		    wheight="560"
					 		    	target="window">
					 		  	
				 			    <input class="button btn btn-primary mr-3"   id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			    <input class="button btn btn-primary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
						      </div>  
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right" >
				 		<input class="button btn btn-primary mr-3" id="addGroupAppsBtn" type="button" value="<@locale code="button.text.add.member"/>">
						<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete.member"/>"
							wurl="<@base/>/groupPrivileges/delete" />
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	

 	<div id="advanced_search">
 		<form id="advanced_search_app_form">
	 		<table   class="datatable">
	 			<tr>
		 			<td width="120px"><@locale code="apps.name"/></td>
		 			<td width="360px">
		 				<input class="form-control" type="text" name="name" style ="width:150px">
		 			</td>
		 			<td width="120px"></td>
		 			<td width="360px">
		 			</td>
	 			</tr>
	 		</table>
	 	</form>
 	</div>
	<div class="mainwrap" id="main">			
		<table  data-url="<@base/>/groupPrivileges/queryAppsInGroup"
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
				<th data-field="iconBase64" data-formatter="iconFormatter"><@locale code="apps.icon"/></th>
				<th data-field="name"><@locale code="apps.name"/></th>
				<th data-field="protocol"  data-visible="false"><@locale code="apps.protocol"/></th>
				<th data-field="category"><@locale code="apps.category"/></th>
				<th data-field="vendor"    data-visible="false"><@locale code="apps.vendor"/></th>
				<th data-field="loginUrl" data-visible="false"><@locale code="log.loginhistory.loginUrl"/></th>
	
			</tr>
		</thead>
	</table>
	</div>
</div>
					</div>
					<footer class="content-footer">
		<#include  "../layout/footer.ftl"/>
	</footer>
</div>
	</div>
	
	</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>