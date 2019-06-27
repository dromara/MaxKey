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
   	
	$(function () {
		$("#insertAppAccountBtn").on("click",function(){
			var settings={
					url		:	"<s:Base/>/app/accounts/forwardSelect/"+$("#appId").val(),//window url
					title	:	"New",//title
					width	:	"700",//width
					height	:	"500"//height
				};
			$.window(settings);//open window
		});
		
		$("#deleteGroupAppsBtn").on("click",function(){
			var selectIds = $("#list").jqGrid("getGridParam", "selarrrow");
			if(selectIds == null || selectIds == "") {
				$.alert({content:$.platform.messages.select.alertText});
				return false;
			}
			$("#appId").val(selectIds);
			$("#submitBtn").click();
		});
		
		$("#appSearchBtn").on("click",function(){
			var postData=$("#advanced_search_app_form").serializeObject();
			$("#list").jqGrid('setGridParam',{postData: postData}).trigger("reloadGrid");
		});
	});
</script>

	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="apps.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input class="appId" id="appId" name="appId" type="hidden" >
				 			<input class="appName"   style="width:200px"   id="appName" name="appName" type="text" >
				 			<s:Dialog text="button.text.select" title="Groups" url="/apps/select"  width="700" height="550"  />
				 			
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 			
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
				 		<input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
						
						<input class="button"  id="insertAppAccountBtn" type="button" value="<s:Locale code="button.text.add"/>">
						<input class="button"   id="modifyBtn" type="button" value="<s:Locale code="button.text.edit"/>"  target="window"
						 			wurl="<s:Base/>/app/accounts/forwardUpdate" wwidth="500px"  wheight="300px">
						 		    
						 	<input class="button"   id="deleteBtn" type="button" value="<s:Locale code="button.text.delete"/>"
						 			wurl="<s:Base/>/app/accounts/delete" />
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	

 	<div id="advanced_search">
 		<form id="advanced_search_form">
	 		<table   class="datatable">
	 			<tr>
		 			<td width="120px"><s:Locale code="userinfo.username"/></td>
		 			<td width="360px">
		 				<input type="text" name="username" >
		 			</td>
		 			<td width="120px"><s:Locale code="userinfo.appaccouts.relatedUsername"/></td>
		 			<td width="360px">
		 				<input type="text" name="relatedUsername" >
		 			</td>
	 			</tr>
	 		</table>
	 	</form>
 	</div>
	<div class="mainwrap" id="main">
			<s:Grid id="list" url="/app/accounts/grid" multiselect="false" resize="true">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="100" field="uid" title="userinfo.id" hidden="true"/>
				<s:Column width="100" field="username" title="userinfo.username"/>
				<s:Column width="100" field="displayName" title="userinfo.displayName"/>
				<s:Column width="100" field="appId" title="apps.id" hidden="true"/>
				<s:Column width="100" field="appName" title="apps.name"/>
				<s:Column width="100" field="relatedUsername" title="userinfo.appaccouts.relatedUsername"/>
			</s:Grid>
	</div>