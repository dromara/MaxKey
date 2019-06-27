<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">	
	function statusFormatter(value, options, rData){
		if(value==3){
			return '<s:Locale code="common.text.status.3" />';
		}else{
			return '<s:Locale code="common.text.status.4" />';
		}
	};
	
	$(function () {
		$("#selectBtn").on("click",function(){
			var selectIds = $.gridSel("#list");
			var rowData = $("#list").jqGrid("getRowData", selectIds);
			selectIds=rowData.id;
			if(selectIds == null || selectIds == "") {
				$.alert({content:$.platform.messages.select.alertText});
				return false;
			}
		 	$.forward("<s:Base/>/users/forwardAdd/"+selectIds);	
		});
	});
</script>

<div>

</div>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="userinfo.userType.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input type="text" name="name" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right"  style="width: auto;">
							<input class="button"   id="selectBtn" type="button" value="<s:Locale code="button.text.select"/>" >
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>

	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/usertype/grid" multiselect="false" resize="true" >	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="300" field="name" title="userinfo.userType.name"/>
			<s:Column width="300" field="description" title="common.text.description"/>
			<s:Column width="50" field="status" title="common.text.status"  formatter="statusFormatter"/>
			<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
			<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
			<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
			<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
		</s:Grid>
			
	</div>