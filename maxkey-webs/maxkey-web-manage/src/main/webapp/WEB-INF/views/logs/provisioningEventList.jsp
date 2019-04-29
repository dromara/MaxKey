<%@ page 	contentType="text/html; charset=UTF-8"%>
<%@ page 	import="org.maxkey.domain.*"%> 
<%@ page 	import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page 	import="org.maxkey.constants.*"%>
<%@ taglib  prefix="s"   uri="http://www.connsec.com/tags" %>
<%@ taglib  prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
/*	function viewformatter (value, options, rData){
		return "<a href='javascript:void(0);' selid='"+rData["id"]+"' class='viewJsonObject' title='view more' >view more</a>";
	}
	
	*/
	
	function resultformatter (value, options, rData){
		var result="";
		if(value==11){
			result="In Progress";
		}else if(value==12){
			result="Error";
		}else{
			result="Finish";
		}
		return result;
	}
	
	function entryTypeformatter (value, options, rData){
		var entryType="";
		if(value==1||value==4){
			entryType="User";
		}else if(value==2){
			entryType="Organization";
		}else if(value==3){
			entryType="Group";
		}
		return entryType;
	}
	
	function actionformatter (value, options, rData){
		var action="";
		if(value==<%=OPERATEACTION.CREATE_ACTION%>){
			action="Create";
		}else if(value==<%=OPERATEACTION.DELETE_ACTION%>){
			action="Delete";
		}else if(value==<%=OPERATEACTION.UPDATE_ACTION%>){
			action="Update";
		}else if(value==<%=OPERATEACTION.CHANGE_PASSWORD_ACTION%>){
			action="Change Password";
		}else if(value==<%=OPERATEACTION.ADD_MEMBER_ACTION%>){
			action="Add Member";
		}else if(value==<%=OPERATEACTION.DELETE_MEMBER_ACTION%>){
			action="Delete Member";
		}else if(value==<%=OPERATEACTION.VIEW_ACTION%>){
			action="View";
		}
		return action;
	}
	
				


</script>
	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td width="120px"><s:Locale code="provisioning.name"/>:</td>
				<td  width="375px">
					<form id="basic_search_form">
		 					<input id="provisioningId" name="provisioningId" value="" type="hidden" >
				 			<input  style="width:200px"   id="provisioningName" name="provisioningName" type="text" value="" >
				 			<s:Dialog text="button.text.select" title="Provisioning" url="/provisioning/selectProvisioningList" width="700" height="500" />
				 			
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>"/>
				 			
					 	</form>
				</td>
				<td colspan="2"> 
					 <input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
					 	
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table   class="datatable">
	 			<tr>
	 				<td width="120px"><s:Locale code="provisioningentry.entryName"/></td>
		 			<td width="360px">
		 				<input name="entryName" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="provisioningentry.entryType"/></td>
		 			<td width="360px">
		 				<select id="entryType" name="entryType"  >
							<option value="1"  selected>User</option>
							<option value="2"  >Organization</option>
							<option value="3"  >Group</option>
						</select>
			 		</td>
			 </tr>
			 <tr>
	 				<td width="120px"><s:Locale code="common.text.startdate"/></td>
		 			<td width="360px">
		 				<input id="datepickerstart" name="startDate" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="common.text.enddate"/></td>
		 			<td width="360px">
						<input id="datepickerend"  type="text" id="endDate" name="endDate"  title="" value=""/>
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>

<div class="mainwrap" id="main">
	
	<s:Grid id="list" url="/logs/provisioningEvent/grid" multiselect="false">	
		<s:Column width="0" field="id" title="id" hidden="true"/>
		<s:Column width="100" field="provisioningId" title="provisioningentry.provisioningId"  hidden="true"/>
		<s:Column width="100" field="provisioningName" title="provisioningentry.provisioningName"/>
		<s:Column width="100" field="entryId" title="provisioningentry.entryId"   hidden="true"/>
		<s:Column width="100" field="entryName" title="provisioningentry.entryName"/>				
		<s:Column width="100" field="entryType" title="provisioningentry.entryType"  formatter="entryTypeformatter" />
		<s:Column width="100" field="action" title="provisioningentry.action"  formatter="actionformatter"/>
		<s:Column width="100" field="requestTime" title="provisioningentry.requestTime" />
		<s:Column width="100" field="provisioningTime" title="provisioningentry.provisioningTime" />
		<s:Column width="50" field="result" title="provisioningentry.result" formatter="resultformatter"/>
		<s:Column width="100" field="resultText" title="provisioningentry.resultText"/>
		<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
		<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
		<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
		<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
	</s:Grid>
	
</div>