<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">	
	
		function afterSubmit(data){
			$("#list").trigger('reloadGrid');
		}
		
		function beforeAdd(object){
			$(object).attr("wurl","<s:Base/>/userextra/forwardAdd/"+$(".userTypeId").val());
		}
		
		
	
	$(function () {
	
	});
</script>


	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="userinfo.userType.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
		 					
		 					<input type="hidden" class="userTypeId" name="userTypeId" style ="width:150px">
				 			<input type="text" class="userTypeName"  name="userTypeName" style ="width:150px">
				 			<s:Dialog text="button.text.select" title="UserType" url="/usertype/selectUserTypeList" width="700" height="500" />
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
							<input class="button"   id="addBtn" type="button" value="<s:Locale code="button.text.add"/>"  target="window"
								   	wurl="<s:Base/>/userextra/forwardAdd" wheight="400px">
						 	<input class="button"   id="modifyBtn" type="button" value="<s:Locale code="button.text.edit"/>"  target="window"
						 			wurl="<s:Base/>/userextra/forwardUpdate"  wheight="400px"> 
						 		    
						 	<input class="button"   id="deleteBtn" type="button" value="<s:Locale code="button.text.delete"/>"
						 			wurl="<s:Base/>/userextra/batchDelete" />
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	
 	
 	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/userextra/grid" multiselect="true" resize="true">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="200" field="userTypeName" title="userinfo.userType.name"/>
			<s:Column width="200" field="attributeName" title="userinfo.userType.extra.attributeName"/>
			<s:Column width="200" field="attribute" title="userinfo.userType.extra.attribute"/>
			<s:Column width="100" field="attributeType" title="userinfo.userType.extra.attributeType"/>
			<s:Column width="100" field="defaultValue" title="userinfo.userType.extra.defaultValue"/>
			<s:Column width="40" field="sortOrder" title="common.text.sortorder" />
			<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
			<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
			<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
			<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
		</s:Grid>
</div>