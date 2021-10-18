<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>

<script type="text/javascript">

function onClick(event, treeId, treeNode) {
	//alert(treeNode.name+"|"+treeNode.id);
	$("#departmentId", window.parent.document).val(treeNode.id);
	$("#department", window.parent.document).val(treeNode.name);
	$.closeWindow();
 			
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
	
});
</script>
</head>
<body>	 
<!-- content -->  
<table border="0" cellpadding="0" cellspacing="0"  width="100%" class="th_atleft">
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;">
         <div id="orgsTree" class="ztree"></div>
      </td>
   </tr>
</table>
</body>
</html>