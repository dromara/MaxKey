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

	function onClick(event, treeId, treeNode) {
		  $("#departmentId").val(treeNode.id);
  		  $("#searchBtn").click();
	}
				
	$(function () {	
		$("#winClose").on("click",function(){
			var seldata=$.dataGridSelRowsData("#datagrid"); 
			console.log(seldata[0].id+" - "+seldata[0].fullName);
			$(".username", window.parent.document).val(seldata[0].username);
			$(".displayName", window.parent.document).val(seldata[0].displayName);
			$(".userId", window.parent.document).val(seldata[0].id);
			$.closeWindow();
		});

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
	
});
	</script>
</head>
<body>
 <div>
    <table   class="table table-bordered">
            <tr>
                <td  width="120px">
                     <@locale code="userinfo.username"/>
                </td>
                <td  width="300px">
                    <form id="basic_search_form">
                        <div class="input-group" style="vertical-align: middle;">
                            <input  class="form-control"   id="departmentId"  name="departmentId" type="hidden">
                            <input  class="form-control"  name="username" type="text" >
                            <input  class="button btn mr-3 btn-primary"    id="searchBtn" type="button" value="<@locale code="button.text.search"/>">
                         </div>
                    </form>
                </td>
                <td colspan="2"> 
                     <div id="tool_box_right">    
                         <input  class="button btn mr-3 btn-primary"    id="winClose" type="button" value="<@locale code="button.text.confirm" />" >
                    </div>
                </td>
            </tr>
        </table>
 	
 </div>
     <!-- content -->  
  <table class="table table-bordered"   width="100%" >
   <tr>
      <td valign="top"  style="vertical-align: top;min-width: 200px;">
      	<div id="orgsTree" class="ztree"></div>
         
      </td>
      <td  valign="top"  style="vertical-align: top;">
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
				</tr>
			</thead>
		</table>
	     </td>
	   </tr>
	</table>
</body>
</html>