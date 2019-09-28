<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page 	import="org.maxkey.constants.*"%>

<style>
<!--
	.ui-jqgrid tr.jqgrow td {
		line-height: 10px;
	}
-->
</style>

<script type="text/javascript">				
	function iconFormatter(value, options, rData){
  			return "<img width='30' height='30' border='0px' src='<s:Base/>/image/"+rData["id"]+"'/>";
	};
	
	function vendorFormatter(value, options, rData){
  			if(value!=null&&value!=""){
			return "<a href='"+rData["vendorUrl"]+"' target='_blank'>"+value+"</a>";
		}else{
			return value==""?"":value;
		}
	};
	
	function parserProtocolPath(protocol){
		if(protocol=="<%=PROTOCOLS.FORMBASED%>"){
			protocolPath="formbased";
		}else if(protocol=="<%=PROTOCOLS.TOKENBASED%>"){
			protocolPath="tokenbased";
		}else if(protocol=="<%=PROTOCOLS.OAUTH10A%>"){
			protocolPath="oauth10a";
		}else if(protocol=="<%=PROTOCOLS.OAUTH20%>"){
			protocolPath="oauth20";
		}else if(protocol=="<%=PROTOCOLS.SAML11%>"){
			protocolPath="saml11";
		}else if(protocol=="<%=PROTOCOLS.SAML20%>"){
			protocolPath="saml20";
		}else if(protocol=="<%=PROTOCOLS.DESKTOP%>"){
			protocolPath="desktop";
		}else if(protocol=="<%=PROTOCOLS.BASIC%>"){
			protocolPath="basic";
		}else if(protocol=="<%=PROTOCOLS.LTPA%>"){
			protocolPath="ltpa";
		}else if(protocol=="<%=PROTOCOLS.CAS%>"){
			protocolPath="cas";
		}
		return protocolPath;
	};
	
	$(function () {
		$("#selectBtn").on("click",function(){
			var seldata=$.gridRowData("#list",$.gridSel("#list")); 
			$(".appId", window.parent.document).val(seldata.id);
			$(".appName", window.parent.document).val(seldata.name);
			$.closeWindow();
		 			
		});
	      
	});
</script>
	<div id="tool_box">
		
			<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="apps.name"/>:</td>
		 			<td  width="374px" nowrap>
		 				<form id="basic_search_form">
			 				<input type="text" name="name" style ="width:150px">
			 				<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
		 					
		 				 </form>
		 			</td>
		 			<td colspan="2">
		 				<div >
		 					<input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
						 	<input class="button"   id="selectBtn" type="button" value="<s:Locale code="button.text.select"/>" />
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
		 			<td width="374px">
		 				<select name="protocol" class="select_protocol">
		 					<option value=""  selected>Select</option>
		 					<option value="<%=PROTOCOLS.FORMBASED%>"><%=PROTOCOLS.FORMBASED%></option>
		 					<option value="<%=PROTOCOLS.OPEN_ID_CONNECT%>"><%=PROTOCOLS.OPEN_ID_CONNECT%></option>
		 					<option value="<%=PROTOCOLS.OAUTH10A%>"><%=PROTOCOLS.OAUTH10A%></option>
		 					<option value="<%=PROTOCOLS.OAUTH20%>"><%=PROTOCOLS.OAUTH20%></option>
		 					<option value="<%=PROTOCOLS.SAML11%>"><%=PROTOCOLS.SAML11%></option>
		 					<option value="<%=PROTOCOLS.SAML20%>"><%=PROTOCOLS.SAML20%></option>
		 					<option value="<%=PROTOCOLS.TOKENBASED%>"><%=PROTOCOLS.TOKENBASED%></option>
		 					<option value="<%=PROTOCOLS.DESKTOP%>"><%=PROTOCOLS.DESKTOP%></option>
		 					<option value="<%=PROTOCOLS.BASIC%>"><%=PROTOCOLS.BASIC%></option>
		 					
		 				</select>
		 			</td>
		 			<td width="120px"><s:Locale code="apps.vendor"/></td>
		 			<td width="374px">
		 				<input   id="vendor" name="vendor" type="text" size="50" value="">
	 			</tr>
	 		</table>
	 	</form>
 	</div>
 	
		<div class="mainwrap" id="main">
			<s:Grid id="list" url="/apps/grid" multiselect="false"  resize="false" rowLimit="10" rowList="[10]">	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="100" field="icon" title="apps.icon" formatter="iconFormatter"/>
				<s:Column width="300" field="name" title="apps.name"/>
				<s:Column width="250" field="protocol" title="apps.protocol"/>
				<s:Column width="200" field="loginUrl" title="apps.loginUrl" hidden="true"/>
				<s:Column width="15" field="category" title="apps.category" hidden="true"/>
				<s:Column width="15" field="vendor" title="apps.vendor"  formatter="vendorFormatter" hidden="true"/>
				<s:Column width="0" field="vendorUrl" title="apps.vendor.url" hidden="true"/>
				<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
				<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
				<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
				<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
			</s:Grid>
		</div>