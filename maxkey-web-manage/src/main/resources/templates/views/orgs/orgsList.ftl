<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>

<script type="text/javascript">

		
function onClick (event, treeId, treeNode) {
	$("#pId").val(treeNode.data.id)
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
								<h4 class="page-title"><@locale code="navs.orgs"/></h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
									<li class="active">/ <@locale code="navs.orgs"/></li>
								</ol>
							</div>
						</div>

					</div>
					<div class="container-fluid">

					<div class="col-12 grid-margin">
						<div class="card">
							<div class="card-body">

								 <div id="tool_box">
		<table   class="table table-bordered">
			<tr>
				<td  width="120px">
			 		 <@locale code="org.name"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input  class="form-control"  name="name" type="text" style ="width:150px;float:left;">
				 			<input id="pId"  class="form-control"  name="parentId" type="hidden" style ="width:150px;float:left;">
				 			<input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			<input  class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
					 	</form>
				</td>
				<td colspan="2"> 
					 <div id="tool_box_right">	    
						 <input class="button btn btn-success mr-3" id="addBtn" type="button" value="<@locale code="button.text.add"/>" 
						 		    wurl="<@base/>/orgs/forwardAdd"
						 		    wwidth="620"
						 		    wheight="600"
					 		    	target="window">	    	
					 		    	
					 	<input class="button btn btn-info mr-3 " id="modifyBtn" type="button" value="<@locale code="button.text.edit"/>" 
					 				wurl="<@base/>/orgs/forwardUpdate"
					 				wwidth="620"
						 		    wheight="600"
					 		    	target="window"> 
					 		    	
					 	<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete"/>"
					 				wurl="<@base/>/orgs/delete" />
					</div>
				</td>
			</tr>
		</table>
 		
		
 	</div>
 									 
	     <!-- content -->  
 <table class="datatable"   width="100%" >
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;">
      	<div id="orgsTree" class="ztree"></div>
         
      </td>
      <td  valign="top"  class="td_1" style="vertical-align: top;">
         <table  data-url="<@base/>/orgs/pageresults"
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
			<th data-field="id"><@locale code="org.id"/></th>
			<th data-field="name"><@locale code="org.name"/></th>
			<th data-field="sortIndex"><@locale code="org.sortorder"/></th>
			<th data-field="division"   data-visible="false"><@locale code="org.division"/></th>
			<th data-field="description"><@locale code="org.description"/></th>
			</tr>
		</thead>
	</table>
      </td>
   </tr>
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
