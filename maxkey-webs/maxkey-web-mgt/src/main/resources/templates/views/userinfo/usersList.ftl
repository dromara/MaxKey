<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">	
	function genderFormatter(value, row, index){
   		if(value==1){
   			return '<@locale code="userinfo.gender.female" />';
   		}else{
   			return '<@locale code="userinfo.gender.male" />';
   		}
	};
	
	function statusFormatter(value, row, index){
   		if(value==1){
   			return '<@locale code="userinfo.status.active" />';
   		}else if(value==2){
   			return '<@locale code="userinfo.status.inactive" />';
   		}else if(value==5){
   			return '<@locale code="userinfo.status.lock" />';
   		}else if(value==9){
   			return '<@locale code="userinfo.status.delete" />';
   		}else {
   			return '<@locale code="userinfo.status.inactive" />';
   		}
	};
		
function onClick (event, treeId, treeNode) {
	$("#departmentId").val(treeNode.data.id)
	$.cookie("select_org_id", treeNode.data.id, { path: '/' });
	$.cookie("select_org_name", treeNode.data.name,{ path: '/' });
	$("#searchBtn").click();
	 
}
	
$(function () {
		
			var treeSettings={
				element  :  "orgsTree",
				rootId  :  "1",
			 	checkbox  :  null,
			 	onClick  :  onClick,
			 	onDblClick  :  null,
			 	url  :  "<@base/>/orgs/tree"
			};
			
		function singlePath(newNode) {
			if (newNode === curExpandNode) return;
			if (curExpandNode && curExpandNode.open==true) {
				var zTree = $.fn.zTree.getZTreeObj(treeSettings.element);
				if (newNode.parentTId === curExpandNode.parentTId) {
					zTree.expandNode(curExpandNode, false);
				} else {
					var newParents = [];
					while (newNode) {
						newNode = newNode.getParentNode();
						if (newNode === curExpandNode) {
							newParents = null;
							break;
						} else if (newNode) {
							newParents.push(newNode);
						}
					}
					if (newParents!=null) {
						var oldNode = curExpandNode;
						var oldParents = [];
						while (oldNode) {
							oldNode = oldNode.getParentNode();
							if (oldNode) {
								oldParents.push(oldNode);
							}
						}
						if (newParents.length>0) {
							for (var i = Math.min(newParents.length, oldParents.length)-1; i>=0; i--) {
								if (newParents[i] !== oldParents[i]) {
									zTree.expandNode(oldParents[i], false);
									break;
								}
							}
						} else {
							zTree.expandNode(oldParents[oldParents.length-1], false);
						}
					}
				}
			}
			curExpandNode = newNode;
		};


		function beforeExpand(treeId, treeNode) {
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			var zTree = $.fn.zTree.getZTreeObj(""+treeSettings.element);
			for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
				if (treeNode !== treeNodeP.children[i]) {
					zTree.expandNode(treeNodeP.children[i], false);
				}
			}
			while (pNode) {
				if (pNode === treeNode) {
					break;
				}
				pNode = pNode.getParentNode();
			}
			if (!pNode) {
				singlePath(treeNode);
			}

		};
		
	    $.fn.zTree.init(
	    		$("#"+treeSettings.element), //element
	    		{//json object 
					check	: 	{
						enable		: 	treeSettings.checkbox
					},
					async	: 	{
						enable		: 	true,
						url			:	treeSettings.url,
						autoParam	:	["id", "name=n", "level=lv"],
						dataType    :   "json",
						otherParam	:	{"otherParam":"zTreeAsyncTest",id:treeSettings.rootId},
						dataFilter	: 	function (treeId, parentNode, childNodes) {
											if (!childNodes) return null;
											for (var i=0, l=childNodes.length; i<l; i++) {
												childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
											}
											return childNodes;
										}
					},
					data			: 	{
						simpleData	: 	{
							enable	: 	true
						}
					},
					callback: {
						onClick			: 	treeSettings.onClick,
						onDblClick		: 	treeSettings.onDblClick,
						beforeAsync		: 	function(treeId, treeNode){
							$.loading();
						},
						onAsyncSuccess	: 	function(event, treeId, treeNode, msg){
							$.unloading();
						},
						//beforeExpand	: 	beforeExpand,
						onExpand		: 	function onExpand(event, treeId, treeNode) {
							curExpandNode = treeNode;
						}
					}
	    		}
	    	);//end tree	
	
	$("#moreBtn").on("click",function(){
		var node=$("#tool_box_right_more");
		if(node.is(':hidden')){　　
		　　node.show();　
		}else{
		　　node.hide();
		}
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
								<h4 class="page-title"><@locale code="navs.users"/></h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
									<li class="active">/ <@locale code="navs.users"/></li>
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
		<table   class="table table-bordered">
			<tr>
				<td  width="120px">
			 		 <@locale code="userinfo.username"/>
				</td>
				<td  width="375px">
					<form id="basic_search_form">
					   <div class="input-group" style="float: left;vertical-align: middle;">
						  <input  class="form-control"   id="departmentId"  name="departmentId" type="hidden">
				 	      <input  class="form-control"  name="username" type="text" >
				 		  <input  class="button btn mr-3 btn-primary"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
                          <input  class="button btn mr-3 btn-primary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
                        
				 	  </form>
				 		
				</td>
				<td colspan="2"> 
					 <div id="tool_box_right">    
						  <input class="button btn btn-primary mr-3 window" id="changepwdBtn" type="button"
                         value="<@locale code="login.password.changepassword"/>" 
                                    wurl="<@base/>/userinfo/forwardChangePassword/" wwidth="600px" wheight="300px" ref="datagrid"/>
                        
                         <input class="button btn btn-primary mr-3 window" id="userAdjointBtn" type="button" 
                                value="<@locale code="button.text.adjunct"/>"  ref="datagrid"
                                    wurl="<@base/>/useradjoint/list" wwidth="900px" wheight="600px" />
                         <input  id="adjunctUserIdRef"      type="hidden" />                
                         <input class="button btn btn-primary mr-3 window" id="usersImportBtn" type="button" 
                                value="<@locale code="button.text.import"/>" 
                                    wurl="<@base/>/userinfo/import" wwidth="400px" wheight="250px" />
                        <input class="button btn btn-primary mr-3" id="addBtn" type="button" value="<@locale code="button.text.add"/>" 
                                    wurl="<@base/>/userinfo/forwardAdd"
                                    wwidth="960"
                                    wheight="620"
                                    target="window">            
                                    
                        <input class="button btn btn-primary mr-3 " id="modifyBtn" type="button" value="<@locale code="button.text.edit"/>" 
                                    wurl="<@base/>/userinfo/forwardUpdate"
                                    wwidth="960"
                                    wheight="620"
                                    target="window"> 
                        <input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete"/>"
                                    wurl="<@base/>/userinfo/delete" />
					</div>
				</td>
			</tr>
		</table>
		
		<div id="advanced_search">
            <form id="advanced_search_form">
                <table    class="table table-bordered">
                    <tr>
                        <td width="120px"><@locale code="userinfo.displayName"/></td>
                        <td width="360px">
                            <input  class="form-control"  name="displayName" type="text" >
                        </td>
                        <td width="120px"><@locale code="userinfo.employeeNumber"/></td>
                        <td width="360px">
                            <input class="form-control"  type="text" id="employeeNumber" name="employeeNumber"  title="" value=""/>
                            </td>
                 </tr>
                </table>
            </form>
        </div>
 	</div>
 	
 	
 	
 	<div  class="toolbox">

    </div>
 	     <!-- content -->  
 <table class="table table-bordered"   width="100%" >
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;">
      	<div id="orgsTree" class="ztree"></div>
         
      </td>
      <td  valign="top"  class="td_1" style="vertical-align: top;">
	 	<table  data-url="<@base/>/userinfo/grid"
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
				<th data-field="username"><@locale code="userinfo.username"/></th>
				<th data-field="displayName"><@locale code="userinfo.displayName"/></th>
				<th data-field="employeeNumber"><@locale code="userinfo.employeeNumber"/></th>
				<th data-field="organization"><@locale code="userinfo.organization"/></th>
				<th data-field="department"><@locale code="userinfo.department"/></th>
				<th data-field="jobTitle"><@locale code="userinfo.jobTitle"/></th>
				<th data-field="mobile"  data-visible="false"><@locale code="userinfo.mobile"/></th>
				<th data-field="email"   data-visible="false"><@locale code="userinfo.email"/></th>
				<th data-field="gender" data-formatter="genderFormatter" ><@locale code="userinfo.gender"/></th>
				<th data-field="status" data-formatter="statusFormatter" ><@locale code="userinfo.status"/></th>
				</tr>
			</thead>
		</table>
	     </td>
	   </tr>
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