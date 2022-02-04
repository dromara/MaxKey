<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<script type="text/javascript">
   <!--
      $(function(){	
      	$("#picture").on("click",function(){
      		$("#pictureFile").click();
      	});
      });
      //-->
</script>
<link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
</head>
<body>
<form  id="actionForm"
	method="post"
	type="alert"  
	action="<@base/>/useradjoint/update" 
	forward="<@base/>/useradjoint/list/${model.userId}"
	class="needs-validation" novalidate>
	 <div class="" style="width:100%;">

	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->    
  	      	<!--table-->

 <table  class="table table-bordered"   id="table_switch_company" width="980">
	<tbody>				
		<tr>
			<td style="width:15%;"><@locale code="userinfo.organization" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="organization" name="organization"  title="" value="${model.organization!""}"/>
			</td>
			<td style="width:15%;"><@locale code="userinfo.division" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="division" name="division"  title="" value="${model.division!""}"/>
			</td>
			
		</tr>
		
		<tr>
			<td><@locale code="userinfo.department" /></td>
			<td>
				<input class="form-control"  type="hidden" id="departmentId" name="departmentId"  title="" value="${model.departmentId!""}"/>
				<input class="form-control"  type="text" style="width:70%"  id="department" name="department"  title="" value="${model.department!""}"/>
				<s:Dialog text="button.text.select" title="department" url="/orgs/orgsSelect/deptId/department" width="300" height="400" />
			</td>
			<td><@locale code="userinfo.costCenter" /></td>
			<td>
				<input class="form-control"  type="text" id="costCenter" name="costCenter"  title="" value="${model.costCenter!""}"/>
			</td>
			
		</tr>
		<tr>
			<td><@locale code="userinfo.jobTitle" /></td>
			<td>
				<input class="form-control"  type="text" id="jobTitle" name="jobTitle"  title="" value="${model.jobTitle!""}"/>
			</td>
			<td><@locale code="userinfo.jobLevel" /></td>
			<td>
				<input class="form-control"  type="text" id="jobLevel" name="jobLevel"  title="" value="${model.jobLevel!""}"/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.manager" /></td>
			<td>
				<input class="form-control"  type="hidden" id="managerId" name="managerId"  title="" value="${model.managerId!""}"/>
				<input class="form-control"  type="text" id="manager" name="manager"  title="" value="${model.manager!""}"/>
			</td>
			<td><@locale code="userinfo.assistant" /></td>
			<td>
				<input class="form-control"  type="hidden" id="assistantId" name="assistantId"  title="" value="${model.assistantId!""}"/>
				<input class="form-control"   type="text" id="assistant" name="assistant"  title="" value="${model.assistant!""}"/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.entryDate" /></td>
			<td>
				<input class="form-control"  type="text" id="entryDate" name="entryDate"  title="" value="${model.entryDate!""}"/>
			</td>
			<td><@locale code="userinfo.quitDate" /></td>
			<td>
				<input class="form-control"   type="text" id="quitDate" name="quitDate"  title="" value="${model.quitDate!""}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>
		<tr>
			<td><@locale code="userinfo.workCountry" /></td>
			<td nowrap >
				<input class="form-control"   type="text" id="workCountry" name="workCountry"  title="" value="${model.workCountry!""}"/>
			</td>
			<td><@locale code="userinfo.workRegion" /></td>
			<td>
				<input class="form-control"  type="text" id="workRegion" name="workRegion"  title="" value="${model.workRegion!""}"/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.workLocality" /></td>
			<td>
				<input class="form-control"  type="text" id="workLocality" name="workLocality"  title="" value="${model.workLocality!""}"/>
			</td>
			<td><@locale code="userinfo.workStreetAddress" /></td>
			<td>
				<input class="form-control"  type="text" id="workStreetAddress" name="workStreetAddress"  title="" value="${model.workStreetAddress!""}"/>
			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.workPostalCode" /></td>
			<td>
				<input class="form-control"  type="text" id="workPostalCode" name="workPostalCode"  title="" value="${model.workPostalCode!""}"/>
			</td>
			<td><@locale code="userinfo.workFax" /></td>
			<td>
				<input class="form-control"  type="text" id="workFax" name="workFax"  title="" value="${model.workFax!""}"/>
			</td>
		</tr>

		<tr>
			<td><@locale code="userinfo.workPhoneNumber" /></td>
			<td>
				<input class="form-control"  type="text" id="workPhoneNumber" name="workPhoneNumber"  title="" value="${model.workPhoneNumber!""}"/>
			</td>
			<td><@locale code="userinfo.workEmail" /></td>
			<td>
				<input class="form-control"  type="text" id="workEmail" name="workEmail"  title="" value="${model.workEmail!""}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>					
	</tbody>
</table>

  	        <div class="clear"></div>
		</div>
		</div>
			<div >
				<div >
					<input class="form-control"  type="hidden" id="userId" name="userId"  title="" value="${model.userId}"/>
					<input class="form-control"  type="hidden" id="userId" name="id"  title="" value="${model.id}"/>
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input id="submitBtn" class="button btn btn-primary mr-3" type="submit" value="<@locale code="button.text.save" />"/>
				</div>
			</div>
	 </div> 
</form>
</body>
</html>