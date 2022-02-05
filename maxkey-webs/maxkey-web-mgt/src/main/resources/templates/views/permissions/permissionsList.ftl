<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<script type="text/javascript"> 

function onClick (event, treeId, treeNode) {

    $("#pid").val(treeNode.id);
    $.cookie("select_res_id", treeNode.id, { path: '/' });
    $.cookie("select_app_id", $("#appId").val(), { path: '/' });
    $.cookie("select_res_name", treeNode.name,{ path: '/' });
    $("#searchBtn").click();
    
     
}


$(function () {

 $("#savePermBtn").click(function(){
    
    var roleId="";
    if($("#datagrid").length>0){//get grid list selected ids
        var selRows = $('#datagrid').bootstrapTable('getSelections');
        for (var i=0;i<selRows.length; i++){
            roleId=roleId+","+selRows[i].id;
            break;
        }
        roleId=roleId.substring(1);
    }
    
    if(roleId == null || roleId == "") {
        $.alert({content:$.platform.messages.select.alertText});
        return;
    }
    
    var resIds="";
    var nodes = $.fn.zTree.getZTreeObj("resourcesTree").getCheckedNodes(true);
    for(var i=0;i<nodes.length;i++){
         resIds=resIds+","+nodes[i].id;
     }
     resIds=resIds.substring(1);
    
    $.post("<@base/>/permissions/savepermissions", 
            {
                appId:$("#appId").val(),
                roleId:roleId,
                resourceId:resIds,
                currTime:(new Date()).getTime()
            }, 
            function(data) {
        if (typeof(afterDelete) == "function"){
            afterDelete(data);//call back action
        }
        //alert delete result
        $.alert({content:data.message,type:$.platform.messages.messageType[data.messageType]});
        //refresh grid list
        
    }); 
 });
   
$('#datagrid').on('click-row.bs.table', function (row, element, field) {
     if($("#appId").val() == null || $("#appId").val() == "") {
        $.alert({content:$.platform.messages.select.alertText});
        return;
    }
    
    $.post("<@base/>/permissions/querypermissions", 
            {
                appId:$("#appId").val(),
                roleId:element.id,
                currTime:(new Date()).getTime()
            }, 
            function(data) {
                if (typeof(afterDelete) == "function"){
                    afterDelete(data);//call back action
                }
                var zTree = $.fn.zTree.getZTreeObj("resourcesTree");
                zTree.checkAllNodes(false);
                for(var permsData  of  data){
                    var node = zTree.getNodeByParam("id",permsData.resourceId);
                    zTree.checkNode(node, true, true);
                }
        
    }); 
});

 $("#changTreeBtn").click(function(){
        var treeSettings={
            element  :  "resourcesTree",
            rootId  :  "1",
            checkbox  :  true,
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
                        dataType    :   "json",
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
					<h4 class="page-title"><@locale code="navs.role.permissions"/></h4>
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
			<div class="content-wrapper row">
			<div class="col-12 grid-margin">
				<div class="card">
					<div class="card-body">
		<div id="tool_box">
			<table  class="table table-bordered">
 				<tr>
		 			<td style="width:80px;"><@locale code="role.name"/></td>
		 			<td style="width:350px;">
		 				<form id="basic_search_form">
		 				     <div class="input-group" style="vertical-align: middle;">
                                <input class="form-control"    value=""    id="name" name="name" type="text" >
                            
			 				    <input  class="button btn btn-primary mr-3"    id="searchBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
				 			</div>
					 	</form>
		 			</td>
		 			<td style="width:120px;"><@locale code="apps.name"/></td>
                    <td style="width:500px;">
                        <form id="resources_search_form">
                            <div class="input-group" style="vertical-align: middle;">
                                <input class="form-control appId" id="appId" name="appId" value="" type="hidden"  >
                                <input class="form-control" id="pid" name="pid" value="" type="hidden"  >
                                <input class="form-control appName"     value=""    id="appName" name="appName" type="text" >
                                <input class="button btn btn-primary mr-3 window"  id="selectBtn" type="button" value="<@locale code="button.text.select"/>" 
                                        wurl="<@base/>/apps/select"
                                        wwidth="700"
                                        wheight="560"
                                        target="window">
                            
                                <input  class="button btn btn-primary mr-3"    id="changTreeBtn" type="button" size="50" value="<@locale code="button.text.search"/>">
                           </div>
                        </form>
                    </td>
		 			<td >
		 				<div id="tool_box_right" >
					 	     <input class="button btn btn-primary mr-3 "  id="savePermBtn" type="button" value="<@locale code="button.text.save"/>"/>
						</div>
		 			</td>
		 		</tr>
		 	</table>
		<div id="advanced_search">
            <form id="advanced_search_form">
                
            </form>
        </div>
        </div>
       <table class="table table-bordered"   width="100%" >
       <tr>
          <td valign="top"  class="td_1" style="vertical-align: top;width:450px;">
                <table  data-url="<@base/>/roles/grid"
                        id="datagrid"
                            data-toggle="table"
                            data-classes="table table-bordered table-hover table-striped"
                            data-click-to-select="true"
                            data-pagination="true"
                            data-total-field="records"
                            data-page-list="[10, 25, 50, 100]"
                            data-search="false"
                            data-single-select="true"
                            data-locale="zh-CN"
                            data-query-params="dataGridQueryParams"
                            data-query-params-type="pageSize"
                            data-side-pagination="server">
                    <thead>
                        <tr>
                            <th data-checkbox="true"></th>
                            <th data-sortable="true" data-field="id"   data-visible="false">Id</th>
                            <th data-field="name"><@locale code="role.name"/></th>
                            <th data-field="description"><@locale code="common.text.description"/></th>
                
                        </tr>
                    </thead>
                </table>
          </td>
          <td  valign="top"  class="td_1" style="vertical-align: top;">
            <div id="resourcesTree" class="ztree"><b><@locale code="apps.select.tip"/></b></div>
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