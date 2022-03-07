<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<script type="text/javascript">				
	function iconFormatter(value, row, index){
  			return "<img height='30' border='0px' src='"+value+"'";
	};
	
	function vendorFormatter(value, options, rData){
  			if(value!=null&&value!=""){
			return "<a href='"+rData["vendorUrl"]+"' target='_blank'>"+value+"</a>";
		}else{
			return value==""?"":value;
		}
	};
	var protocolArray = new Array();	
	protocolArray["OAuth_v2.0"]="oauth20";
	protocolArray["OAuth_v2.1"]="oauth20";
	protocolArray["OpenID_Connect_v1.0"]="oauth20";
	protocolArray["SAML_v2.0"]="saml20";
	protocolArray["Token_Based"]="tokenbased";
	protocolArray["Form_Based"]="formbased";
	protocolArray["Extend_API"]="extendapi";
	protocolArray["CAS"]="cas";
	protocolArray["Basic"]="basic";
	protocolArray["JWT"]="jwt";
	
	$(function () {
		$("#modifyApps").on("click",function(){
			var seldata=$.dataGridSelRowsData("#datagrid"); 
			if(!seldata.length){
				$.alert({content:$.platform.messages.select.alertText}); 
				return; 
			} 
			
			$.forward({url:"<@base/>/apps/"+protocolArray[seldata[0]["protocol"]]+"/forwardUpdate/"+seldata[0]["id"],target:"_blank"});
		});
		
			//delete and batch delete button
		$("#deleteApps").click(function(){
			var seldata=$.dataGridSelRowsData("#datagrid"); 
			if(!seldata.length){
				$.alert({content:$.platform.messages.select.alertText}); 
				return; 
			} 
			var _this=this;
			$.conform({//conform action
			    content		:	$.platform.messages.del.conformText,
			    callback	: 	function () {
					//delete action post to url with ids
					var deleteUrl="<@base/>/apps/"+protocolArray[seldata[0]["protocol"]]+"/delete/"+seldata[0]["id"];
					$.post(deleteUrl, {_method:"delete",currTime:(new Date()).getTime()}, function(data) {
						//alert delete result
						$.alert({content:data.message,type:$.platform.messages.messageType[data.messageType]});
						//refresh grid list
						$("#searchBtn").click();
				 	}); 
			    }
			});
		});
		
		
		$( "#addApps" ).click(function() {
	          var menu = $("#menu").show().position({
	            my: "top",
	            at: "bottom",
	            of: this
	          });
	         
	          return false;
	        });
	        
	  $( document ).click( function() {
	       	  $("#menu").hide();
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
					<h4 class="page-title"><@locale code="navs.apps"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="active">/ <@locale code="navs.apps"/></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="content-wrapper row">
			<div class="col-12 grid-margin">
				<div class="card">
					<div class="card-body">
		    <div id="tool_box">
			<table  class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="apps.name"/></td>
		 			<td width="375px">
		 				<form id="basic_search_form">
                            <div class="input-group" style="vertical-align: middle;">
			 				    <input class="form-control" type="text" name="name"  >
			 				    <input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			    <input  class="button btn btn-primary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
					 	    </div> 
					 	</form>
		 			</td>
		 			<td colspan="2">
		 				<div id="tool_box_right">
		 					<div  class="btn-group" style="width: 90px;">
							 	<button class="btn btn-primary  dropdown-toggle  mr-3" type="button" id="dropdownMenuButton"  data-bs-toggle="dropdown" aria-expanded="false">
							     	<@locale code="button.text.add"/>
							  	</button>
								  <ul  class="dropdown-menu" style="" aria-labelledby="dropdownMenuButton">
								 	 <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/oauth20/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.oauth2.0" /></a></li>
								     <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/saml20/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.saml2.0" /></a></li>
								     <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/cas/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.cas" /></a></li>
								     <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/formbased/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.formbased" /></a></li>
								     <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/jwt/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.jwt" /></a></li>
								     <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/tokenbased/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.tokenbased" /></a></li>
							 		 <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/extendapi/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.extendapi" /></a></li>
							 		 <li><a class="dropdown-item"  target="_blank"  href="<@base/>/apps/basic/forwardAdd">&nbsp;&nbsp;<@locale code="apps.protocol.basic" /></a></li>
								  </ul>
							</div>
						 	<input  class="button btn btn-primary mr-3 " id="modifyApps" type="button" value="<@locale code="button.text.edit"/>" />
						 	<input  class="button btn btn-danger mr-3 "   id="deleteApps" type="button" value="<@locale code="button.text.delete"/>" />
				 		</div>
		 			</td>
		 		</tr>
		 	</table>
		 	
            <div id="advanced_search">
                <form id="advanced_search_form">
                    <table  class="datatable">
                        <tr>
                            <td width="120px"><@locale code="apps.protocol"/></td>
                            <td width="374px">
                                <select name="protocol" class="form-control  form-select">
                                    <option value=""  selected>Select</option>
                                    <option value="OAuth_v2.0"><@locale code="apps.protocol.oauth2.0" /></option>
                                    <option value="SAML_v2.0"><@locale code="apps.protocol.saml2.0" /></option>
                                    <option value="CAS"><@locale code="apps.protocol.cas" /></option>
                                    <option value="JWT"><@locale code="apps.protocol.jwt" /></option>
                                    <option value="Token_Based"><@locale code="apps.protocol.tokenbased" /></option>
                                    <option value="Extend_API"><@locale code="apps.protocol.extendapi" /></option>
                                    <option value="Form_Based"><@locale code="apps.protocol.formbased" /></option>
                                </select>
                            </td>
                            <td width="120px"><@locale code="apps.vendor"/></td>
                            <td width="374px">
                                <input   id="vendor" name="vendor" class="form-control" type="text"  value="">
                        </tr>
                    </table>
                </form>
            </div>
            
		    </div>
		    
		    
		   <table  data-url="<@base/>/apps/grid"
                id="datagrid"
                    data-toggle="table"
                    data-classes="table table-bordered table-hover table-striped"
                    data-click-to-select="true"
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
                    <th data-checkbox="true"></th>
                    <th data-sortable="true" data-field="id"   data-visible="false">Id</th>
                    <th data-field="iconBase64" data-formatter="iconFormatter"><@locale code="apps.icon"/></th>
                    <th data-field="name"><@locale code="apps.name"/></th>
                    <th data-field="protocol"><@locale code="apps.protocol"/></th>
                    <th data-field="category"><@locale code="apps.category"/></th>
                    <th data-field="vendor"><@locale code="apps.vendor"/></th>
                    <th data-field="loginUrl" data-visible="false"><@locale code="log.loginhistory.loginUrl"/></th>
        
                </tr>
            </thead>
        </table> 
		 		
 	</div>
 	

		
	
	
</div>
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