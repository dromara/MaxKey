<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.connsec.com/tags" %>
<%@ page 	import="org.maxkey.constants.*"%>
<style>
<!--
	.ui-jqgrid tr.jqgrow td {
		line-height: 10px;
	}
-->
</style>
		<script type="text/javascript">	
			function afterSubmit(data){
				$("#list").trigger('reloadGrid');
			}
			
			function iconFormatter(value, options, rData){
  				return "<img width='30' height='30' border='0px'  src='<s:Base/>/image/"+value+"'/>";
			};
		   	
			$(function () {
				$("#addGroupAppsBtn").on("click",function(){
					var selectIds =$.gridRowData("#list",$.gridSelIds("#list")).id;
					if(selectIds == null || selectIds == "") {
						$.alert({content:$.platform.messages.select.alertText});
						return false;
					}
					$("#appId").val(selectIds);
					$("#actionForm").attr("action","<s:Base/>/groupApp/insert");
					$("#submitBtn").click();
				});
			
			});
		</script>

<div style="display:none">
	<form id="actionForm" method="post" action="<s:Base/>/groupApp/insert">
		<table>
			<tr><td></td><td><input type="text" id="groupId" name="groupId" value="${groupId}"/></td></tr>
			<tr><td></td><td><input type="text" id="appId" name="appId" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitBtn" type="button" value="submit"></input></td></tr>
		</table>
	</form>
</div>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="app.name"/>:</td>
		 			<td width="374px" nowrap>
		 				<form id="basic_search_form">
				 			<input type="text" name="name" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">		
				 		</form>
		 			</td>
				 	<td colspan="2"> 
					 	<div>
							<input class="button" id="addGroupAppsBtn" type="button" value="<s:Locale code="button.text.add" />">
					 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	<div id="advanced_search">
		<form id="advanced_search_form">
 		<table   class="datatable">
	 			<tr>
		 			<td width="120px"><s:Locale code="apps.name"/></td>
		 			<td width="360px">
		 				
		 			</td>
		 			<td width="120px"><s:Locale code="apps.protocol"/></td>
		 			<td width="360px">
		 				<select name="protocol" class="select_protocol">
		 					<option value=""  selected>Select</option>
		 					<option value="<%=PROTOCOLS.FORMBASED%>"><%=PROTOCOLS.FORMBASED%></option>
		 					<option value="<%=PROTOCOLS.OPEN_ID_CONNECT%>"><%=PROTOCOLS.OPEN_ID_CONNECT%></option>
		 					<option value="<%=PROTOCOLS.OAUTH10A%>"><%=PROTOCOLS.OAUTH10A%></option>
		 					<option value="<%=PROTOCOLS.OAUTH20%>"><%=PROTOCOLS.OAUTH20%></option>
		 					<option value="<%=PROTOCOLS.SAML11%>"><%=PROTOCOLS.SAML11%></option>
		 					<option value="<%=PROTOCOLS.SAML20%>"><%=PROTOCOLS.SAML20%></option>
		 					<option value="<%=PROTOCOLS.COOKIEBASED%>"><%=PROTOCOLS.COOKIEBASED%></option>
		 					<option value="<%=PROTOCOLS.TOKENBASED%>"><%=PROTOCOLS.TOKENBASED%></option>
		 					<option value="<%=PROTOCOLS.DESKTOP%>"><%=PROTOCOLS.DESKTOP%></option>
		 					<option value="<%=PROTOCOLS.BASIC%>"><%=PROTOCOLS.BASIC%></option>
		 					
		 				</select>
		 			</td>
	 			</tr>
	 		</table>
 		
 		</form>
 	</div>

	<div class="mainwrap" id="main">
		<s:Grid id="list" url="/groupApp/appsNotInGroupGrid?groupId=${groupId}" multiselect="true" resize="false" rowLimit="10" rowList="[10]">	
			<s:Column width="0" field="id" title="id" hidden="true"/>
			<s:Column width="100" field="id" title="apps.icon" formatter="iconFormatter"/>
			<s:Column width="275" field="name" title="apps.name"/>
			<s:Column width="250" field="protocol" title="apps.protocol"/>
		</s:Grid>
	
	</div>