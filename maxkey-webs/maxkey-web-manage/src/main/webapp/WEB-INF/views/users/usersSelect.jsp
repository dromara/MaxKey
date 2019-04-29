<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	    uri="http://www.springframework.org/tags" %>
<script type="text/javascript">


	function onClick(event, treeId, treeNode) {
		  $("#deptId").val(treeNode.id);
		  $("#list").setGridParam({ postData: { deptId: treeNode.id} });
   		  $("#list").trigger('reloadGrid', [{page:1}]);
  			
	}
				
	$(function () {	
		$("#winClose").on("click",function(){
			var selData= $("#list").getRowData($("#list").jqGrid("getGridParam", "selarrrow")[0]+"");
			$("#${username}", window.parent.document).val(selData["fullName"]);
			$("#${uid}", window.parent.document).val(selData["id"]);
			$.closeWindow();
		});
	});
	</script>
 <div>
 	<input class="button" id="winClose" type="button" value="winClose">
 </div>
     <!-- content -->  
 <table border="1" cellpadding="0" cellspacing="0"  width="100%" class="th_atleft">
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;">
         <s:Tree rootId="<%=WebContext.getSystemNavRootId()%>" url="/orgs/tree" id="tree" onClick="onClick"/>
      </td>
      <td  valign="top"  class="td_1" style="vertical-align: top;">
		<s:Grid id="list" url="/users/grid" multiselect="true" resize="false">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="100" field="name" title="userinfo.name"/>
				<s:Column width="100" field="department" title="userinfo.department"/>
				<s:Column width="100" field="username" title="userinfo.username"/>
				<s:Column width="100" field="fullName" title="userinfo.fullname"/>
				<s:Column width="100" field="email" title="userinfo.email" />
				<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
				<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
				<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
				<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
			</s:Grid>
                 
      </td>
   </tr>
</table>
