<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="c"		    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	    uri="http://www.springframework.org/tags" %>
<script type="text/javascript">	
	
	$(function () {
	
		//add button
		$("#updateAccountBtn").click(function(){
			
			var selectId=$.gridSelIds("#list");
			
			$.post($(this).attr("wurl"), 
					{_method:"delete",currTime:(new Date()).getTime(),id:selectId}, 
					function(data) {
					//alert delete result
					$.alert({content:data.message,type:$.platform.messages.messageType[data.messageType]});
			}); 
		});
		
	});
	
	
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
</script>
	<div id="tool_box">
 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="provisioning.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
		 					<input id="provisioningId" name="provisioningId" value="${provisioning.id}" type="hidden" >
				 			<input  style="width:200px"   id="provisioningName" name="provisioningName" type="text" value="${provisioning.name}" >
				 			<s:Dialog text="button.text.select" title="Provisioning" url="/provisioning/selectProvisioningList" width="700" height="500" />
				 			
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>"/>
				 			
					 	</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
						 <input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
						 		   
						 <input class="button window" id="addAccountBtn" type="button" value="<s:Locale code="button.text.create"/>" 
						 		wurl="<s:Base/>/provisioning/account/forwardAdd"
						 		wwidth="700"  wheight="500"
					 		    target="window"  ref="provisioningId"/>
					 		    
					 	<input class="button"  id="updateAccountBtn" type="button" value="<s:Locale code="button.text.update"/>" 
					 			wurl="<s:Base/>/provisioning/account/update" /> 
					 	<input class="button"  id="deleteBtn" type="button" value="<s:Locale code="button.text.delete"/>"
					 			wurl="<s:Base/>/provisioning/account/delete" />
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table   class="datatable">
	 			<tr>
				 	<td width="120px"><s:Locale code="userinfo.username"/></td>
				 	<td width="374px">
				 		<input name="entryName" type="text">
				 	</td>
				 	<td width="120px"></td>
		 			<td width="374px">
			 			
			 		</td>
			 </tr>
			
			</table>
 		</form>
 	</div>
 	
	<s:Grid id="list" url="/provisioning/account/grid" multiselect="true">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="0" field="entryId" title="entryId" hidden="true"/>
			<s:Column width="100" field="provisioningId" title="provisioning.provisioningId" hidden="true"/>
			<s:Column width="100" field="provisioningName" title="provisioning.name"/>
			<s:Column width="100" field="entryType" title="provisioningentry.entryType"  formatter="entryTypeformatter"/>
			<s:Column width="100" field="entryName" title="provisioningentry.entryName"/>
			<s:Column width="100" field="lastProvisioningTime" title="provisioningentry.lastProvisioningTime"/>
			<s:Column width="100" field="provisioning" title="provisioningentry.displayName" hidden="true"/>
	</s:Grid>
	
	