<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
    <link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>

<script type="text/javascript">
	function genderFormatter(value, row, index){
   		if(value==1){
   			return '<@locale code="userinfo.gender.female" />';
   		}else{
   			return '<@locale code="userinfo.gender.male" />';
   		}
	};
	$(function () {	
		$("#winClose").on("click",function(){
			$.closeWindow();
		});
	});
	</script>
</head>
<body>
 <div style="float: right;">
 	<input class="button btn btn-primary mr-3" id="addBtn" type="button" value="<@locale code="button.text.add"/>" 
	 		    wurl="<@base/>/useradjoint/forwardAdd/${userId!}"
 		    	target="forward">	    	
 		    	
 	<input class="button btn btn-primary mr-3 " id="modifyBtn" type="button" value="<@locale code="button.text.edit"/>" 
 				wurl="<@base/>/useradjoint/forwardUpdate"
 		    	target="forward"> 
 	<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete"/>"
 				wurl="<@base/>/useradjoint/delete" />

 	<input class="button btn btn-primary mr-3"  id="winClose" type="button" value="<@locale code="button.text.close" />" >
 </div>
     <!-- content -->  
  <table class="datatable"   width="100%" >
   <tr>
      <td  valign="top"  class="td_1" style="vertical-align: top;">
	 	<table  data-url="<@base/>/useradjoint/grid?userId=${userId!}"
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
				<th data-sortable="true" data-field="id"   data-visible="false"><@locale code="userinfo.id"/></th>
				<th data-field="organization"><@locale code="userinfo.organization"/></th>
				<th data-field="department"><@locale code="userinfo.department"/></th>
				<th data-field="jobTitle"><@locale code="userinfo.jobTitle"/></th>
				<th data-field="jobLevel"><@locale code="userinfo.jobLevel"/></th>
				
				</tr>
			</thead>
		</table>
	     </td>
	   </tr>
	</table>
</body>
</html>