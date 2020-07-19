<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<script type="text/javascript"> 

function onClick (event, treeId, treeNode) {

    $("#parentId").val(treeNode.id);
    $.cookie("select_res_id", treeNode.id, { path: '/' });
    $.cookie("select_app_id", $("#appId").val(), { path: '/' });
    $.cookie("select_res_name", treeNode.name,{ path: '/' });
    $("#searchBtn").click();
    
     
}


$(function () {
 $("#changTreeBtn").click(function(){
        var treeSettings={
            element  :  "resourcesTree",
            rootId  :  "1",
            checkbox  :  null,
            onClick  :  onClick,
            onDblClick  :  null,
            url  :  "<@base/>/resources/tree/"
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
                    check   :   {
                        enable      :   treeSettings.checkbox
                    },
                    async   :   {
                        enable      :   true,
                        url         :   treeSettings.url,
                        autoParam   :   ["id", "name=n", "level=lv"],
                        otherParam  :   {
                                            "otherParam":"zTreeAsyncTest",
                                            id:treeSettings.rootId,
                                            "appId":$("#appId").val(),
                                            "appName":$("#appName").val(),
                                            }
                    },
                    data            :   {
                        simpleData  :   {
                            enable  :   true
                        }
                    },
                    callback: {
                        onClick         :   treeSettings.onClick,
                        onDblClick      :   treeSettings.onDblClick,
                        beforeAsync     :   function(treeId, treeNode){
                            $.loading();
                        },
                        onAsyncSuccess  :   function(event, treeId, treeNode, msg){
                            $.unloading();
                        },
                        //beforeExpand  :   beforeExpand,
                        onExpand        :   function onExpand(event, treeId, treeNode) {
                            curExpandNode = treeNode;
                        }
                    }
                }
            );//end tree
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
					<h4 class="page-title"><@locale code="navs.resources"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="active">/ <@locale code="navs.resources"/></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-12 grid-margin">
				<div class="card">
					<div class="card-body">
		
			<table  class="table table-bordered">
 				<tr>
		 			<td width="120px"><@locale code="apps.name"/>:</td>
		 			<td width="450px">
		 				<form id="basic_search_form">
			 				<input class="form-control appId" id="appId" name="appId" value="" type="hidden"  >
			 				<input class="form-control" id="parentId" name="parentId" value="" type="hidden"  >
                            <input class="form-control appName"    style="width:200px;float: left;" value=""    id="appName" name="appName" type="text" >
                            <input class="button btn btn-success mr-3 window" style="float: left;" id="selectBtn" type="button" value="<@locale code="button.text.select"/>" 
                                    wurl="<@base/>/apps/select"
                                    wwidth="700"
                                    wheight="500"
                                    target="window">
			 				<input  class="button btn btn-primary mr-3"    id="changTreeBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			<!--<input  class="button btn btn-secondary"  id="advancedSearchExpandBtn" type="button" size="50"  value="<@locale code="button.text.expandsearch"/>" expandValue="<@locale code="button.text.expandsearch"/>"  collapseValue="<@locale code="button.text.collapsesearch"/>">
					 		-->
					 		<input style="display:none"  class="button btn btn-primary mr-3"    id="searchBtn" type="button"/>
					 	</form>
		 			</td>
		 			<td colspan="2">
		 				<div id="tool_box_right" style="width:350px;">
		 					 <input class="button btn btn-success mr-3" id="addBtn" type="button" value="<@locale code="button.text.add"/>" 
						 		    wurl="<@base/>/resources/forwardAdd"
						 		    wwidth="500"
						 		    wheight="550"
					 		    	target="window"/>	    	
					 		    	
					 	<input class="button btn btn-info mr-3 " id="modifyBtn" type="button" value="<@locale code="button.text.edit"/>" 
					 				wurl="<@base/>/resources/forwardUpdate"
					 				wwidth="500"
						 		    wheight="550"
					 		    	target="window"/> 
					 		    	
					 	<input class="button btn btn-danger mr-3 "  id="deleteBtn" type="button" value="<@locale code="button.text.delete"/>"
					 				wurl="<@base/>/resources/delete" />
						</div>
		 			</td>
		 		</tr>
		 	</table>
		
		 		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
	 		
	 	</form>
 	</div>
 	 <table class="datatable"   width="100%" >
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;width:400px;">
        <div id="resourcesTree" class="ztree"></div>
         
      </td>
      <td  valign="top"  class="td_1" style="vertical-align: top;">
		<table  data-url="<@base/>/resources/grid"
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
				<th data-field="appName"><@locale code="apps.name"/></th>
				<th data-field="name"><@locale code="resource.name"/></th>
				<th data-field="resourceType"><@locale code="resource.resourceType"/></th>
				<th data-field="sortIndex"><@locale code="common.text.sortindex"/></th>
				<th data-field="resourceAction"><@locale code="resource.resourceAction"/></th>
				<th data-field="description"><@locale code="common.text.description"/></th>
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