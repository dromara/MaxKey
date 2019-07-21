<%@ page   language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="s" 			uri="http://sso.maxkey.org/tags" %> 
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/common.cssjs.jsp"></jsp:include>
	<script type="text/javascript">	
		function onSelectRow(id){		
	   		$("#changepwdBtn").attr("wurl","<s:Base/>/users/forwardChangePassword/"+$.gridRowData("#list",id).id);
	   	}
	   	
	   	function genderFormatter(value, options, rData){
	   		if(value==1){
	   			return '<s:Locale code="userinfo.gender.female" />';
	   		}else{
	   			return '<s:Locale code="userinfo.gender.male" />';
	   		}
		};
	</script>
</head>
<body> 
<div class="app header-default side-nav-dark">
<div class="layout">
	<div class="header navbar">
		<jsp:include page="../layout/top.jsp"></jsp:include>
	</div>
	
	<div class="col-md-3 sidebar-nav side-nav" >
 		<jsp:include page="../layout/sidenav.jsp"></jsp:include>
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
									<li><a href="index.html">Dashboard</a></li>
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
			 		 <s:Locale code="userinfo.username"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input  class="form-control"  name="username" type="text" style ="width:150px;float:left;">
				 			<input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 			<input  class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
					 	</form>
				</td>
				<td colspan="2"> 
					 <div id="tool_box_right">
					 	<input class="button btn btn-warning mr-3 window" id="changepwdBtn" type="button" value="<s:Locale code="button.text.changepassword"/>" 
						 		    wurl="<s:Base/>/users/forwardChangePassword" wwidth="600px" wheight="250px" />
						 		    
						 <input class="button btn btn-success mr-3" id="addBtn" type="button" value="<s:Locale code="button.text.add"/>" 
						 		    wurl="<s:Base/>/users/forwardSelectUserType"
						 		    wwidth="960"
						 		    wheight="600"
					 		    	target="window">	    	
					 		    	
					 	<input class="button btn btn-info mr-3 " id="modifyBtn" type="button" value="<s:Locale code="button.text.edit"/>" 
					 				wurl="<s:Base/>/users/forwardUpdate"
					 				wwidth="960"
						 		    wheight="600"
					 		    	target="window"> 
					 		    	
					 	<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<s:Locale code="button.text.delete"/>"
					 				wurl="<s:Base/>/users/delete" />
					</div>
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table    class="table table-bordered">
	 			<tr>
	 				<td width="120px"><s:Locale code="userinfo.displayName"/></td>
		 			<td width="360px">
		 				<input  class="form-control"  name="displayName" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="userinfo.employeeNumber"/></td>
		 			<td width="360px">
						<input class="form-control"  type="text" id="employeeNumber" name="employeeNumber"  title="" value=""/>
			 			</td>
		 			
			 </tr>
			 <tr>
					<td width="120px"><s:Locale code="userinfo.department"/></td>
		 			<td width="360px">
			 			<input  class="form-control"  type="text" style="display:none;" id="departmentId" name="departmentId"  title="" value=""/>
						<input  class="form-control"  style="width:70%;;float:left;"  type="text" id="department" name="department"  title="" value=""/>
			 			<input class="window button btn btn-secondary mr-3 "  type="button"  size="50" value="<s:Locale code="button.text.select"/>" title="department" wurl="/orgs/orgsSelect/deptId/department" wwidth="300" wheight="400" />
			 		</td>
	 				<td width="120px"><s:Locale code="userinfo.userType"/></td>
		 			<td width="360px">
		 				<input class="form-control"  class="userTypeId" name="userType" type="text" style="display:none;"  >
		 				<input class="form-control"  class="userTypeName" name="userTypeName" type="text" style="width:70%;;float:left;"  >
		 				<input class="window button btn btn-secondary mr-3 " type="button"   size="50" value="<s:Locale code="button.text.select"/>" title="UserType" wurl="/usertype//selectUserTypeList" wwidth="700" wheight="500" />
		 			</td>
		 			
			 </tr>
			</table>
 		</form>
 	</div>
 	<table  data-url="<s:Base />/userinfo/grid"
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
			<th data-sortable="true" data-field="id"   data-visible="false"><s:Locale code="log.loginhistory.id"/></th>
			<th data-field="icon"><s:Locale code="apps.icon"/></th>
			<th data-field="username"><s:Locale code="userinfo.username"/></th>
			<th data-field="displayName"><s:Locale code="userinfo.displayName"/></th>
			<th data-field="employeeNumber"><s:Locale code="userinfo.employeeNumber"/></th>
			<th data-field="organization"><s:Locale code="userinfo.organization"/></th>
			<th data-field="department"><s:Locale code="userinfo.department"/></th>
			<th data-field="jobTitle"><s:Locale code="userinfo.jobTitle"/></th>
			<th data-field="mobile"><s:Locale code="userinfo.mobile"/></th>
			<th data-field="email"><s:Locale code="userinfo.email"/></th>
			<th data-field="gender"><s:Locale code="userinfo.gender"/></th>
			</tr>
		</thead>
	</table>
</div>
	
</div>
					</div>
					<footer class="content-footer">
		<jsp:include page="../layout/footer.jsp"></jsp:include>
	</footer>

	</div>
	
	</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>