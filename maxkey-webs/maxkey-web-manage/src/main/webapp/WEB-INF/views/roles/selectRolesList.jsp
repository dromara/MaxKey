<%@ page   contentType="text/html; charset=UTF-8"  import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"  uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">		
	$(function () {
		$("#selectBtn").on("click",function(){
			var seldata=$.gridRowData("#list",$.gridSel("#list")); 
			$(".roleId", window.parent.document).val(seldata.id);
			$(".roleName", window.parent.document).val(seldata.name);
			$.closeWindow();
		});
	});
</script>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="role.name"/>:</td>
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
		<s:Grid id="list" url="/roles/grid" multiselect="false" resize="false">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="350" field="name" title="role.name"/>
				<s:Column width="300" field="description" title="common.text.description"/>
				<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
				<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
				<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
				<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
			</s:Grid>
			
</div>