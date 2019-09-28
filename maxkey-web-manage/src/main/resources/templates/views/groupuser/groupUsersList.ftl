<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="s"	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">	
	
	function afterSubmit(data){
		$("#list").trigger('reloadGrid');
	}
	
	
	$(function () {
		$("#insertGroupUserBtn").on("click",function(){		
			var settings={
							url		:	"<s:Base/>/groupMember/addGroupAppsList/"+$("#groupId").val(),//window url
							title	:	"New",//title
							width	:	"700",//width
							height	:	"500"//height
						};
					$.window(settings);//open window
			
		});
		
		$("#deleteGroupAppsBtn").on("click",function(){			
			var list2selectIds = $("#list").jqGrid("getGridParam", "selarrrow");
			if(list2selectIds == null || list2selectIds == "") {
				$.alert({content:$.platform.messages.select.alertText});
				return false;
			}
			var memberName="";
			var memberId="";
			for(var i=0;i<list2selectIds.length;i++){
				memberName+=$("#list").jqGrid("getRowData",list2selectIds[i]).username+",";
				memberId+=$("#list").jqGrid("getRowData",list2selectIds[i]).id+",";
			}
			
			$("#memberId").val(memberId);
			$("#memberName").val(memberName);
			$("#submitBtn").click();
		});
	
	});
</script>
<div style="display:none">
	<form id="actionForm" method="post" action="<s:Base/>/groupMember/delete">
		<table>
			<tr><td></td><td><input type="text"  class="groupId" name="groupId" value=""/></td></tr>
			<tr><td></td><td><input type="text"  class="groupName" name="groupName" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="memberId" name="memberId" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="memberName" name="memberName" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitBtn" type="button" value="submit"></input></td></tr>
		</table>
	</form>
</div>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="group.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input class="groupId" id="groupId" name="groupId" value="" type="hidden" >
				 			<input class="groupName"   style="width:200px"   id="groupName" name="groupName" type="text" >
				 			<s:Dialog text="button.text.select" title="Groups" url="/groups/selectGroupsList" width="700" height="500" />
				 			
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
				 		<input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
				 		
						<input class="button"  id="insertGroupUserBtn" type="button" value="<s:Locale code="button.text.add.member"/>">
						<input class="button"  id="deleteGroupAppsBtn" type="button" value="<s:Locale code="button.text.delete.member"/>">
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	    
 	    <div id="advanced_search">
 			<form id="advanced_search_form">
		 		<table  class="datatable">
		 			<tr>
			 			<td width="120px"><s:Locale code="apps.protocol"/></td>
			 			<td width="360px">
			 			</td>
			 			<td width="120px"><s:Locale code="apps.protocol"/></td>
			 			<td width="360px">
			 			</td>
			 		</tr>
			 		<tr>
			 			<td width="120px"><s:Locale code="apps.protocol"/></td>
			 			<td width="360px">
			 			</td>
			 			<td width="120px"><s:Locale code="apps.protocol"/></td>
			 			<td width="360px">
			 			</td>
			 		</tr>
			 	</table>
		 	</form>
		 </div>
		<div class="mainwrap" id="main">
			<s:Grid id="list" url="/groupMember/gridUserMemberInGroup" multiselect="true" resize="true">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="200" field="username" title="userinfo.username"/>
				<s:Column width="200" field="displayName" title="userinfo.displayName" />
				<s:Column width="100" field="department" title="userinfo.department"/>
				<s:Column width="100" field="email" title="userinfo.email"/>
				<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
				<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
				<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
				<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
			</s:Grid>
		</div>