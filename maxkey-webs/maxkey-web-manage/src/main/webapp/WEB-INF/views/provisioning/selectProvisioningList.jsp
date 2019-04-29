<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">	
	function statusFormatter(value, options, rData){
 			return value==1?'<s:Locale code="common.text.status.11" />':'<s:Locale code="common.text.status.12" />';
	};
	
	function typeFormatter(value, options, rData){
		if(value==0){
			return '<s:Locale code="provisioning.type.jit" />';
		}else if(value==1){
			return '<s:Locale code="provisioning.type.schedule" />';
		}else if(value==2){
			return '<s:Locale code="provisioning.type.manual" />';
		}
 			
	};
	
	$(function () {
		$("#selectBtn").on("click",function(){
			var seldata=$.gridRowData("#list",$.gridSel("#list")); 
			$("#provisioningId", window.parent.document).val(seldata.id);
			$("#provisioningName", window.parent.document).val(seldata.name);
			$.closeWindow();
		 			
		});
	});
</script>

<div>

</div>
	<div id="tool_box">
 		<div id="tool_box_left">
 			<form id="basic_search_form">
	 			<s:Locale code="provisioning.name"/>:<input type="text" name="name" style ="width:150px">
	 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
	 		</form>
 		</div>
		 <div id="tool_box_right"  style="width: auto;">
			<input class="button"   id="selectBtn" type="button" value="<s:Locale code="button.text.select"/>" >
		</div>
 	</div>
	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/provisioning/grid?status=1" multiselect="false" resize="false" rowLimit="10">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="400" field="name" title="provisioning.name"/>
			<s:Column width="150" field="type" title="provisioning.type" formatter="typeFormatter"/>
			<s:Column width="200" field="description" title="common.text.description" hidden="true"/>
			<s:Column width="100" field="status" title="common.text.status"   formatter="statusFormatter"/>
			<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
			<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
			<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
			<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
		</s:Grid>
			
	</div>