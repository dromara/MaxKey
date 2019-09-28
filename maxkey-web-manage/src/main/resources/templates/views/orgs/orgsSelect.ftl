<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>


<script type="text/javascript">

function onClick(event, treeId, treeNode) {
	//alert(treeNode.name+"|"+treeNode.id);
	$("#departmentId", window.parent.document).val(treeNode.id);
	$("#department", window.parent.document).val(treeNode.name);
	$.closeWindow();
 			
}
</script>
	 
	     <!-- content -->  
<table border="0" cellpadding="0" cellspacing="0"  width="100%" class="th_atleft">
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;">
         <s:Tree rootId="<%=WebContext.getSystemNavRootId()%>" url="/orgs/tree" id="tree" onClick="onClick"/>
      </td>
   </tr>
</table>
