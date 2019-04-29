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
		}else if(protocol=="<%=PROTOCOLS.EXTEND_API%>"){
			protocolPath="extendapi";
		}else if(protocol=="<%=PROTOCOLS.LTPA%>"){
			protocolPath="ltpa";
		}else if(protocol=="<%=PROTOCOLS.CAS%>"){
			protocolPath="cas";
		}
		return protocolPath;
	};
	
	$(function () {
		$("#modifyApps").on("click",function(){
			var selectIds = $("#list").jqGrid("getGridParam", "selrow");
			if(selectIds == null || selectIds == "") {
				$.alert({content:$.platform.messages.select.alertText});
				return false;
			}
			var selData= $("#list").jqGrid("getRowData",selectIds);
			
			$.forward({url:"<s:Base/>/apps/"+parserProtocolPath(selData["protocol"])+"/forwardUpdate/"+selData["id"]});
		});
		
			//delete and batch delete button
		$("#deleteApps").click(function(){
			var selectIds = $("#list").jqGrid("getGridParam", "selrow");
			if($("#list")){//get grid list selected ids
				if(selectIds == null || selectIds == "") {
					$.alert({content:$.platform.messages.select.alertText}); 
					return; 
				}
				
			}
			var selData= $("#list").getRowData(selectIds+"");
			var _this=this;
			$.conform({//conform action
			    content		:	$.platform.messages.del.conformText,
			    callback	: 	function () {
					//delete action post to url with ids
					var deleteUrl="<s:Base/>/apps/"+parserProtocolPath(selData["protocol"])+"/delete/"+selData["id"];
					$.post(deleteUrl, {_method:"delete",currTime:(new Date()).getTime()}, function(data) {
						//alert delete result
						$.alert({content:data.message,type:$.platform.messages.messageType[data.messageType]});
						//refresh grid list
						if($("#list")){
							$("#list").jqGrid('setGridParam').trigger("reloadGrid");
						}
				 	}); 
			    }
			});
		});
		
		
		$( "#addApps" ).click(function() {
	          var menu = $("#menu").show().position({
	            my: "left top",
	            at: "left bottom",
	            of: this
	          });
	          $( document ).on( "click", function() {
	           	 menu.hide();
	          });
	          return false;
	        });
	        
	        
	    $(".select-menu-item").mouseover(function() {
           	$( this ).addClass( 'select-menu-item-selected' );
     	 }).mouseout(function() {
           	$( this ).removeClass( 'select-menu-item-selected' );
	    });
	      
	});
</script>
	<div id="tool_box">
		
			<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="apps.name"/>:</td>
		 			<td width="375px">
		 				<form id="basic_search_form">
			 				<input type="text" name="name" style ="width:150px">
			 				<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
		 					<input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
		 				 </form>
		 			</td>
		 			<td colspan="2">
		 				<div id="tool_box_right">
							<a class="minibutton" id="addApps" style="width: 70px;"><span class="ui-button-icon-secondary ui-icon ui-icon-triangle-1-s" style="margin-top: 10px;float: left;"></span><s:Locale code="button.text.add"/></a>
								<div id="menu" class="select-menu-modal " style="width: 150px;">
								     <div class="select-menu-item"><a href="<s:Base/>/apps/formbased/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.formbased" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/desktop/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.desktop" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/tokenbased/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.tokenbased" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/oauth10a/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.oauth1.0a" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/oauth20/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.oauth2.0" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/saml11/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.saml1.1" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/saml20/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.saml2.0" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/ltpa/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.ltpa" /></div></a></div>
								     <div class="select-menu-item"><a href="<s:Base/>/apps/cas/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.cas" /></div></a></div>
							 		 <div class="select-menu-item"><a href="<s:Base/>/apps/extendapi/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.extendapi" /></div></a></div>
							 		 <div class="select-menu-item"><a href="<s:Base/>/apps/basic/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:Locale code="apps.protocol.basic" /></div></a></div>
								</div>
			
						 	<input class="button" id="modifyApps" type="button" value="<s:Locale code="button.text.edit"/>" />
						 	<input class="button"   id="deleteApps" type="button" value="<s:Locale code="button.text.delete"/>" />
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
			<s:Grid id="list" url="/apps/grid" multiselect="false" >	
				<s:Column width="0" field="id" title="id" hidden="true"/>
				<s:Column width="10" field="icon" title="apps.icon" formatter="iconFormatter"/>
				<s:Column width="20" field="name" title="apps.name"/>
				<s:Column width="20" field="protocol" title="apps.protocol"/>
				<s:Column width="20" field="loginUrl" title="apps.loginUrl"/>
				<s:Column width="15" field="category" title="apps.category"/>
				<s:Column width="5" field="sortOrder" title="common.text.sortorder" />
				<s:Column width="15" field="vendor" title="apps.vendor"  formatter="vendorFormatter"/>
				<s:Column width="0" field="vendorUrl" title="apps.vendor.url" hidden="true"/>
				<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
				<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
				<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
				<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
			</s:Grid>
		</div>