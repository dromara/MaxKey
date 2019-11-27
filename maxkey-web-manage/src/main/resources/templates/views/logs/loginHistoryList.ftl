<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">	
		function onSelectRow(id){		
	   		$("#changepwdBtn").attr("wurl","<@base/>/users/forwardChangePassword/"+$.gridRowData("#list",id).id);
	   	}
	   	
	   	function genderFormatter(value, options, rData){
	   		if(value==1){
	   			return '<@locale code="userinfo.gender.female" />';
	   		}else{
	   			return '<@locale code="userinfo.gender.male" />';
	   		}
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
								<h4 class="page-title">Dashboard 2</h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="<@base/>/main">Dashboard</a></li>
									<li class="active">/ Dashboard 2</li>
								</ol>
							</div>
						</div>

					</div>
					<div class="container-fluid">

					<div class="col-12 grid-margin">
						<div class="card">
							<div class="card-body">
							
	<div id="tool_box">
		<table   class="table table-bordered">
			<tr>
				<td  width="120px">
			 		 <@locale code="userinfo.username"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input  class="form-control"  name="username" type="text" style ="width:150px;float:left;">
				 			<input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			<input  class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
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
	 				<td width="120px"><@locale code="userinfo.displayName"/></td>
		 			<td width="360px">
		 				<input  class="form-control"  name="displayName" type="text" >
		 			</td>
		 			<td width="120px"><@locale code="userinfo.employeeNumber"/></td>
		 			<td width="360px">
						<input class="form-control"  type="text" id="employeeNumber" name="employeeNumber"  title="" value=""/>
			 			</td>
		 			
			 </tr>
			 <tr>
					<td width="120px"><@locale code="userinfo.department"/></td>
		 			<td width="360px">
			 			<input  class="form-control"  type="text" style="display:none;" id="departmentId" name="departmentId"  title="" value=""/>
						<input  class="form-control"  style="width:70%;;float:left;"  type="text" id="department" name="department"  title="" value=""/>
			 			<input class="window button btn btn-secondary mr-3 "  type="button"  size="50" value="<@locale code="button.text.select"/>" title="department" wurl="/orgs/orgsSelect/deptId/department" wwidth="300" wheight="400" />
			 		</td>
	 				<td width="120px"><@locale code="userinfo.userType"/></td>
		 			<td width="360px">
		 				<input class="form-control"  class="userTypeId" name="userType" type="text" style="display:none;"  >
		 				<input class="form-control"  class="userTypeName" name="userTypeName" type="text" style="width:70%;;float:left;"  >
		 				<input class="window button btn btn-secondary mr-3 " type="button"   size="50" value="<@locale code="button.text.select"/>" title="UserType" wurl="/usertype//selectUserTypeList" wwidth="700" wheight="500" />
		 			</td>
		 			
			 </tr>
			</table>
 		</form>
 	</div>
 	<table  data-url="<@base />/logs/loginHistory/grid"
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
				<th data-sortable="true" data-field="id"   data-visible="false"><@locale code="log.loginhistory.id"/></th>
				<th data-field="sessionId"><@locale code="log.loginhistory.sessionId"/></th>
				<th data-field="username"><@locale code="log.loginhistory.username"/></th>
				<th data-field="displayName"><@locale code="log.loginhistory.displayName"/></th>
				<th data-field="provider"><@locale code="log.loginhistory.provider"/></th>
				<th data-field="message"><@locale code="log.loginhistory.message"/></th>
				<th data-field="loginType"><@locale code="log.loginhistory.loginType"/></th>
				<th data-field="sourceIp"><@locale code="log.loginhistory.sourceIp"/></th>
				<th data-field="browser"><@locale code="log.loginhistory.browser"/></th>
				<th data-field="loginTime"><@locale code="log.loginhistory.loginTime"/></th>
				<th data-field="logoutTime"><@locale code="log.loginhistory.logoutTime"/></th>
				<th data-field="platform"><@locale code="log.loginhistory.platform"/></th>
				<th data-field="application"><@locale code="log.loginhistory.application"/></th>
				<th data-field="loginUrl"><@locale code="log.loginhistory.loginUrl"/></th>
				<th data-field="code"><@locale code="log.loginhistory.code"/></th>
				<th data-field="rpUserInfo"><@locale code="log.loginhistory.rpUserInfo"/></th>
	
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

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>