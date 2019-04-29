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
	
		//add button
		$("#manageAccountBtn").click(function(){
			
			var selectId="";
			if($("#list").length>0){//get grid list selected ids
				selectId=$("#list").jqGrid("getGridParam", "selrow");
				if(selectId ==	null ||	selectId	==	""){
					$.alert({content:$.platform.messages.select.alertText});
					return;
				}
				
			}
			
			var wurl=""+$(this).attr("wurl")+"&provisioningId="+selectId;
			
			$.forward(wurl);
		});
	});
</script>

<div>

</div>
	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="provisioning.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input type="text" name="name" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
				 			<!--  <input class="button"   id="manageAccountBtn" type="button" value="<s:Locale code="button.text.account"/>"  target="forward"
					 		   	 	wurl="<s:Base/>/provisioning/account/list?mnid=120902000000">-->
							<input class="button"   id="addBtn" type="button" value="<s:Locale code="button.text.add"/>"  target="forward"
					 		   	 	wurl="<s:Base/>/provisioning/forwardAdd">
						 	<input class="button"   id="modifyBtn" type="button" value="<s:Locale code="button.text.edit"/>"  target="forward"
						 			wurl="<s:Base/>/provisioning/forwardUpdate" wwidth="800px" wheight="500px" > 
						 		    
						 	<input class="button"   id="deleteBtn" type="button" value="<s:Locale code="button.text.delete"/>"
						 			wurl="<s:Base/>/provisioning/delete" />
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/provisioning/grid" multiselect="false" resize="true">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="400" field="name" title="provisioning.name"/>
			<s:Column width="100" field="type" title="provisioning.type" formatter="typeFormatter"/>
			<s:Column width="200" field="description" title="common.text.description"/>
			<s:Column width="50" field="sortOrder" title="common.text.sortorder" />
			<s:Column width="100" field="status" title="common.text.status"   formatter="statusFormatter"/>
		</s:Grid>
			
	</div>