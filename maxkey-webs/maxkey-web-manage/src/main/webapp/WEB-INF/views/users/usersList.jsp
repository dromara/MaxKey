<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="c"		    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	    uri="http://www.springframework.org/tags" %>
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
	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td  width="120px">
			 		 <s:Locale code="userinfo.username"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input name="username" type="text" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 			<input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
					 	</form>
				</td>
				<td colspan="2"> 
					 <div id="tool_box_right">
					 	<input class="button window" id="changepwdBtn" type="button" value="<s:Locale code="button.text.changepassword"/>" 
						 		    wurl="<s:Base/>/users/forwardChangePassword" wwidth="600px" wheight="250px" />
						 		    
						 <input class="button" id="addBtn" type="button" value="<s:Locale code="button.text.add"/>" 
						 		    wurl="<s:Base/>/users/forwardSelectUserType"
						 		    wwidth="960"
						 		    wheight="600"
					 		    	target="window">	    	
					 		    	
					 	<input class="button"  id="modifyBtn" type="button" value="<s:Locale code="button.text.edit"/>" 
					 				wurl="<s:Base/>/users/forwardUpdate"
					 				wwidth="960"
						 		    wheight="600"
					 		    	target="window"> 
					 		    	
					 	<input class="button"  id="deleteBtn" type="button" value="<s:Locale code="button.text.delete"/>"
					 				wurl="<s:Base/>/users/delete" />
					</div>
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table   class="datatable">
	 			<tr>
	 				<td width="120px"><s:Locale code="userinfo.displayName"/></td>
		 			<td width="360px">
		 				<input name="displayName" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="userinfo.department"/></td>
		 			<td width="360px">
			 			<input type="text" style="display:none;" id="departmentId" name="departmentId"  title="" value=""/>
						<input style="width:70%"  type="text" id="department" name="department"  title="" value=""/>
			 			<s:Dialog text="button.text.select" title="department" url="/orgs/orgsSelect/deptId/department" width="300" height="400" />
			 		</td>
			 </tr>
			 <tr>
					<td width="120px"><s:Locale code="userinfo.employeeNumber"/></td>
		 			<td width="360px">
						<input type="text" id="employeeNumber" name="employeeNumber"  title="" value=""/>
			 			</td>
	 				<td width="120px"><s:Locale code="userinfo.userType.name"/></td>
		 			<td width="360px">
		 				<input class="userTypeId" name="userType" type="text" style="display:none;"  >
		 				<input class="userTypeName" name="userTypeName" type="text" style="width:70%"  >
		 				<s:Dialog text="button.text.select" title="UserType" url="/usertype//selectUserTypeList" width="700" height="500" />
		 			</td>
		 			
			 </tr>
			</table>
 		</form>
 	</div>
 	
	<s:Grid id="list" url="/users/grid" multiselect="false" onSelect="onSelectRow">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="60" field="username" title="userinfo.username"/>
			<s:Column width="100" field="displayName" title="userinfo.displayName"/>
			<s:Column width="100" field="employeeNumber" title="userinfo.employeeNumber"/>
			<s:Column width="100" field="organization" title="userinfo.organization"/>
			<s:Column width="100" field="department" title="userinfo.department"/>
			<s:Column width="100" field="jobTitle" title="userinfo.jobTitle"/>
			<s:Column width="70" field="mobile" title="userinfo.mobile" hidden="true"/>
			<s:Column width="100" field="email" title="userinfo.email" hidden="true"/>				
			<s:Column width="30" field="gender" title="userinfo.gender" formatter="genderFormatter" />
		</s:Grid>