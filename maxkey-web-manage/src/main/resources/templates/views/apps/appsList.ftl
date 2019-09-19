<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<script type="text/javascript">				
	function iconFormatter(value, options, rData){
  			return "<img width='30' height='30' border='0px' src='<@base/>/image/"+rData["id"]+"'/>";
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
			
			$.forward({url:"<@base/>/apps/"+parserProtocolPath(selData["protocol"])+"/forwardUpdate/"+selData["id"]});
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
					var deleteUrl="<@base/>/apps/"+parserProtocolPath(selData["protocol"])+"/delete/"+selData["id"];
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
</head>
<body> 
<div class="app header-default side-nav-dark">
<div class="layout">
	<div class="header navbar">
		<#include  "../layout/top.ftl"/>
	</div>
	
	<div class="col-md-3 sidebar-nav side-nav" >
 		<#include  "../layout/sidenav.ftl"/>
	</div>
	<div class="page-container">
	
	<div class="main-content">
					<div class="container-fluid">

						<div class="breadcrumb-wrapper row">
							<div class="col-12 col-lg-3 col-md-6">
								<h4 class="page-title">Dashboard 2</h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="index.html">Dashboard</a></li>
									<li class="active">/ Dashboard 2</li>
								</ol>
							</div>
						</div>

					</div>


					<div class="col-12 grid-margin">
						<div class="card">
							<div class="card-body">
		
			<table  class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="apps.name"/>:</td>
		 			<td width="375px">
		 				<form id="basic_search_form">
			 				<input class="form-control" type="text" name="name"  style ="width:150px;float:left;">
			 				<input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			<input  class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
					 	</form>
		 			</td>
		 			<td colspan="2">
		 				<div id="tool_box_right">
							<a  class="button btn btn-success mr-3" id="addApps" style="width: 70px;">
								<span class="ui-button-icon-secondary ui-icon ui-icon-triangle-1-s" style="margin-top: 10px;float: left;"></span>
								<@locale code="button.text.add"/>
							</a>
							<div id="menu" class="select-menu-modal " style="width: 150px;">
							     <div class="select-menu-item"><a href="<@base/>/apps/formbased/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.formbased" /></div></a></div>
							     <div class="select-menu-item"><a href="<@base/>/apps/desktop/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.desktop" /></div></a></div>
							     <div class="select-menu-item"><a href="<@base/>/apps/tokenbased/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.tokenbased" /></div></a></div>
							     <div class="select-menu-item"><a href="<@base/>/apps/oauth20/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.oauth2.0" /></div></a></div>
							     <div class="select-menu-item"><a href="<@base/>/apps/saml20/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.saml2.0" /></div></a></div>
							     <div class="select-menu-item"><a href="<@base/>/apps/ltpa/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.ltpa" /></div></a></div>
							     <div class="select-menu-item"><a href="<@base/>/apps/cas/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.cas" /></div></a></div>
						 		 <div class="select-menu-item"><a href="<@base/>/apps/extendapi/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.extendapi" /></div></a></div>
						 		 <div class="select-menu-item"><a href="<@base/>/apps/basic/forwardAdd"><div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<@locale code="apps.protocol.basic" /></div></a></div>
							</div>
						 	<input class="button btn btn-info mr-3 " id="modifyApps" type="button" value="<@locale code="button.text.edit"/>" />
						 	<input class="button btn btn-danger mr-3 "   id="deleteApps" type="button" value="<@locale code="button.text.delete"/>" />
				 		</div>
		 			</td>
		 		</tr>
		 	</table>
		
		 		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
	 		<table  class="datatable">
	 			<tr>
		 			<td width="120px"><@locale code="apps.protocol"/></td>
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
		 			<td width="120px"><@locale code="apps.vendor"/></td>
		 			<td width="374px">
		 				<input   id="vendor" name="vendor" type="text" size="50" value="">
	 			</tr>
	 		</table>
	 	</form>
 	</div>
		<table  data-url="<@base/>/apps/grid"
			id="datagrid"
			data-toggle="table"
			data-classes="table table-bordered table-hover table-striped"
			data-pagination="true"
			data-total-field="records"
			data-page-list="[10, 25, 50, 100]"
			data-search="false"
			data-locale="zh-CN"
			data-query-params="dataGridQueryParams"
			data-query-params-type="pageSize"
			data-side-pagination="server">
		<thead>
			<tr>
				<th data-sortable="true" data-field="id"   data-visible="false">Id</th>
				<th data-field="sessionId"><@locale code="apps.icon"/></th>
				<th data-field="name"><@locale code="apps.name"/></th>
				<th data-field="protocol"><@locale code="apps.protocol"/></th>
				<th data-field="category"><@locale code="apps.category"/></th>
				<th data-field="vendor"><@locale code="apps.vendor"/></th>
				<th data-field="loginUrl"><@locale code="log.loginhistory.loginUrl"/></th>
	
			</tr>
		</thead>
	</table>
	
	
</div>
					</div>
					<footer class="content-footer">
		<#include  "../layout/footer.ftl"/>
	</footer>

	</div>
	
	</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>