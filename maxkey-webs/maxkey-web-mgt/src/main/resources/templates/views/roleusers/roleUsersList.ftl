<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<script type="text/javascript">	
    function genderFormatter(value, row, index){
        if(value==1){
            return '<@locale code="userinfo.gender.female" />';
        }else{
            return '<@locale code="userinfo.gender.male" />';
        }
    };
    
	function afterSubmit(data){
		$("#list").trigger('reloadGrid');
	}
	
	
	$(function () {
		$("#insertGroupUserBtn").on("click",function(){		
			if($("#roleId").val()==""){
				$.alert({content:$.platform.messages.select.alertText});	
				return;
			}
			var settings={
							url		:	"<@base/>/rolemembers/addRoleAppsList/"+$("#roleId").val(),//window url
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
					<h4 class="page-title"><@locale code="navs.groups.member"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="inactive" >/ <@locale code="navs.roles"/></li>
						<li class="active">/ <@locale code="navs.role.member"/></li>
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
		 			<td width="120px"><@locale code="role.name"/></td>
		 			<td width="450px">
		 				<form id="basic_search_form" style="width:100%">
		 				    <div class="input-group " style="vertical-align: middle;">
    				 			<input class="roleId" id="roleId" name="roleId" value="" type="hidden" >
    				 			<input class="form-control roleName"     value=""    id="roleName" name="roleName" type="text" >
    				 			<input class="window button btn btn-primary mr-3"  type="button" value="<@locale code="button.text.select"/>" 
    						 		    wurl="<@base/>/roles/selectRolesList"
    						 		    wwidth="700"
    						 		    wheight="560"
    					 		    	target="window">
    					 	 
				 			    <input   class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			    <input class="button btn btn-primary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
				 		     </div>
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right"  >
				 		
						<input class="button btn btn-primary mr-3"  id="insertGroupUserBtn" type="button" value="<@locale code="button.text.add.member"/>">
						<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete.member"/>"
							wurl="<@base/>/rolemembers/delete"/>
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	    
 	    <div id="advanced_search">
 			<form id="advanced_search_form">
		 		<table  class="datatable">
                    <tr>
                        <td width="120px"><@locale code="userinfo.username" />：</td>
                        <td width="360px">
                             <input class="form-control"  type="text"  id="username" name="username"  title="" value=""/>
                        </td>
                        <td width="120px"><@locale code="userinfo.displayName" />：</td>
                        <td width="360px">
                             <input class="form-control"  type="text"  id="displayName" name="displayName"  title="" value=""/>
                        </td>
                    </tr>
                    
                </table>
		 	</form>
		 </div>
		<div class="mainwrap" id="main">			
			
        <table  data-url="<@base/>/rolemembers/queryMemberInRole"
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
                <th data-field="username"><@locale code="userinfo.username"/></th>
                <th data-field="displayName"><@locale code="userinfo.displayName"/></th>
                <th data-field="gender"  data-formatter="genderFormatter" ><@locale code="userinfo.gender"/></th>
                <th data-field="userType"><@locale code="userinfo.userType"/></th>
                <th data-field="jobTitle"><@locale code="userinfo.jobTitle"/></th>
                <th data-field="department"><@locale code="userinfo.department"/></th>
                <th data-field="createdDate"><@locale code="common.text.createddate"/></th>
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