<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	    uri="http://www.springframework.org/tags" %>
<script type="text/javascript">	
   	function genderFormatter(value, options, rData){
   		if(value==1){
   			return '<s:Locale code="userinfo.gender.female" />';
   		}else{
   			return '<s:Locale code="userinfo.gender.male" />';
   		}
	};
	function onlineFormatter(value, options, rData){
   		if(value==1){
   			return '<s:Locale code="userinfo.online.online" />';
   		}else{
   			return '<s:Locale code="userinfo.online.offline" />';
   		}
	};


	$(function () {
		
		$("#viewDetails").on("click",function(){
			var selectId = $.gridSelIds("#list");
			if(selectId == null || selectId == "") {
				$.alert({content:$.platform.messages.select.alertText});
				return false;
			}
			$.window({
					url		:	"<s:Base/>/contact/details/"+selectId,//window url
					title	:	"<s:Locale code="access.security.contact.businessCard"/>",//title
					width	:	600,//width
					height	:	350//height
				});//open window
		});
		
	});
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
					 	 <input class="button"   id="viewDetails" type="button" value="<s:Locale code="access.security.contact.businessCard"/>" />
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
			 			<input type="hidden" id="departmentId" name="departmentId"  title="" value=""/>
						<input style="width:70%"  type="text" id="department" name="department"  title="" value=""/>
			 			<s:Dialog text="button.text.select" title="department" url="/orgs/orgsSelect/deptId/department" width="300" height="400" />
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>
		<s:Grid id="list" url="/contact/grid" multiselect="false">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="70" field="username" title="userinfo.username"/>
				<s:Column width="80" field="employeeNumber" title="userinfo.employeeNumber"/>
				<s:Column width="100" field="displayName" title="userinfo.displayName"/>
				<s:Column width="100" field="department" title="userinfo.department"/>	
				<s:Column width="100" field="jobTitle" title="userinfo.jobTitle"/>	
				<s:Column width="50" field="gender" title="userinfo.gender" formatter="genderFormatter"/>	
				<s:Column width="100" field="workEmail" title="userinfo.workEmail" />
				<s:Column width="100" field="workPhoneNumber" title="userinfo.workPhoneNumber" />
				<s:Column width="100" field="mobile" title="userinfo.mobile" />
				<s:Column width="50" field="online" title="userinfo.online"  formatter="onlineFormatter"/>
			</s:Grid>