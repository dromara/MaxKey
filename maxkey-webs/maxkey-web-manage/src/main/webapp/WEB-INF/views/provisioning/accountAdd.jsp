<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="c"		    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	    uri="http://www.springframework.org/tags" %>
<script type="text/javascript">	
	function onSelectRow(id){
   		$("#entryId").val($("#list").jqGrid("getGridParam", "selarrrow")+"");
   	}
	   	
	$(function () {
		$("#addAccountBtn").click(function(){
			$("#submitBtn").click();
		});
	});
	
</script>
<div style="display:none">
	<form  id="actionForm" method="post" action="<c:url value='/provisioning/account/add'/>">
		<input id="provisioningId" name="provisioningId"  value="${provisioningId}" type="text" >
		<input id="entryId" name="entryId"  value="" type="text" >
		<input id="submitBtn" type="button" value="submitBtn">
		
	</form>
</div>
	<div id="tool_box">
 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="userinfo.username"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
		 					
				 			<input  style="width:200px"   id="username" name="username" type="text" >
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>"/>
				 			
					 	</form>
		 			</td>
				 	<td colspan="2"> 
				 		<div>
						 <input class="button" id="addAccountBtn" type="button" value="<s:Locale code="button.text.create"/>" />
					 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>

 	
		<s:Grid id="list" url="/users/grid" multiselect="true"  rowLimit="10"  resize="false"  onSelect="onSelectRow">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="200" field="username" title="userinfo.username"/>
				<s:Column width="225" field="displayName" title="userinfo.displayName"/>
				<s:Column width="200" field="department" title="userinfo.department"/>
		</s:Grid>