<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">	
	
	
	$(function () {
	
	});
</script>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="group.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input type="text" name="name" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
						<input class="button"   id="addBtn" type="button" value="<s:Locale code="button.text.add"/>"  target="window"
				 		   	 	wurl="<s:Base/>/groups/forwardAdd" wheight="150px" >
					 	<input class="button"   id="modifyBtn" type="button" value="<s:Locale code="button.text.edit"/>"  target="window"
					 			wurl="<s:Base/>/groups/forwardUpdate" wheight="150px" > 
					 		    
					 	<input class="button"   id="deleteBtn" type="button" value="<s:Locale code="button.text.delete"/>"
					 			wurl="<s:Base/>/groups/delete" />
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	
	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/groups/grid" multiselect="false" resize="true">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="50" field="name" title="group.name"/>
			<s:Column width="50" field="description" title="common.text.description"/>
			<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
			<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
			<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
			<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
		</s:Grid>
			
	</div>