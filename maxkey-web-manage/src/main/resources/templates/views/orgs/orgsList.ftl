<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>

<script type="text/javascript">

		
function onClick (event, treeId, treeNode) {
	  $("#actionForm").clearForm();
	  $("#actionForm").json2form({data:treeNode.data});
	  $("#_method").val("put");
	  $("#status").val("1");
	 
}
	
$(function () {

			var treeSettings={
				element  :  "orgsTree",
				rootId  :  "1",
			 	checkbox  :  null,
			 	onClick  :  null,
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
		
	$("#addChildBtn").click(function(){
		var nodes = $.fn.zTree.getZTreeObj("orgsTree").getSelectedNodes();
		if (nodes.length == 0) {
			//$.alert({content:"<s:Locale code="system.menus.alert.select.pmenu" />"});
			return;
		}
		$("#actionForm").clearForm();
		$("#pId").val(nodes[0].data.id);
	  	$("#pName").val(nodes[0].data.name);
	  	$("#sortOrder").val(1);
	  	$("#status").val("1");
	    $("#_method").val("post");
	});	
	
	
	$("#saveBtn").click(function(){
		if($("#_method").val()=="put"){
			$("#actionForm").attr("action",'<s:Base/>/orgs/update');
		}else{
			$("#actionForm").attr("action",'<s:Base/>/orgs/add');
			var nodedata =	$.fn.zTree.getZTreeObj("orgsTree").getSelectedNodes()[0].data;
			$("#xNamePath").val(nodedata.xNamePath+"/"+$("#name").val());
			$("#xPath").val(nodedata.xPath+"/"+$("#id").val());
		}
		
		if($("#fullName").val()==""){
			$("#fullName").val($("#name").val());
		}
		if($("#_method").val()=="post"){
			var node=$("#actionForm").serializeObject();
			node.data=$("#actionForm").serializeObject();
			delete node['url'];
			$.fn.zTree.getZTreeObj("orgsTree").addNodes(
				$.fn.zTree.getZTreeObj("orgsTree").getSelectedNodes()[0],node);
		}else{
			var node=$("#actionForm").serializeObject();
			node.data=$("#actionForm").serializeObject();
			node=$.extend( $.fn.zTree.getZTreeObj("orgsTree").getSelectedNodes()[0],node);
			delete node['url'];
			$.fn.zTree.getZTreeObj("orgsTree").updateNode(node);
		}
		$('#actionForm').submit(); 
	});	
		
		
	$("#deleteBtn").click(function(){
		$.post('<@base/>/orgs/delete',{ id:$("#id").val(),_method:"delete"}, function(data) {
			$.fn.zTree.getZTreeObj("orgsTree").removeNode($.fn.zTree.getZTreeObj("orgsTree").getSelectedNodes()[0]);
 				$.alert({content:data.message});
		});
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
							<div class="card-header border-bottom">
								<h4 class="card-title"><@locale code="login.passwordpolicy"/></h4>
							</div>
							<div class="card-body">
								 
	     <!-- content -->  
 <table class="datatable"   width="100%" >
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;">
      	<div id="orgsTree" class="ztree"></div>
         
      </td>
      <td  valign="top"  class="td_1" style="vertical-align: top;">
         <div id="orgsTable" style="PADDING:0;MARGIN: 0;width:650px"></div>
         <form  id="actionForm" action='<s:Base/>/orgs/add' method="post">
         	<table>
         		<tr>
         			<td>
						<ul class="switch_tab"  style="width:100%" >
							<li id="switch_common" value="table_switch_common" style="width:49%" class="switch_tab_class switch_tab_current"><a href="javascript:void(0);"><s:Locale code="org.tab.basic" /></a></li>
							<li id="switch_extra"  value="table_switch_extra" style="width:49%" class="switch_tab_class"><a href="javascript:void(0);"><s:Locale code="org.tab.extra" /></a></li>
						</ul>
					</td>
         		</tr>
         		<tr><td>
         	<table id="table_switch_common"  class="datatable"  width="600px">
               <tr style="display:none">
                  <th ><input type="text" id="status" type="hidden" name="status"  value="1"/>
                  <input type="text" id="_method" type="hidden" name="_method"  value="put"/></th>
                  <td></td>
               </tr>
               <tr >
                  <th ><s:Locale code="org.pid" />：</th>
                  <td><span class="intspan"><input type="text" readonly id="pId" name="pId" size="80" class="int"/></span></td>
               </tr>
               <tr>
                  <th  width="200px"><s:Locale code="org.pname" />：</th>
                  <td><span class="intspan"><input type="text" readonly  id="pName" name="pName" size="80"   class="int"/></span></td>
               </tr>
               <tr >
                  <th ><s:Locale code="org.id" />：</th>
                  <td><span class="intspan"><input type="text" id="id" name="id" size="80"   class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.name" />：</th>
                  <td><span class="intspan"><input type="text"  id="name" name="name"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.fullname" />：</th>
                  <td><span class="intspan"><input type="text"  id="fullName" name="fullName"  size="80"  class="int"/></span></td>
               </tr>
                <tr >
                  <th ><s:Locale code="org.xpath" /> ：
                  </th>
                  <td><span class="intspan"><input type="text"  id="xPath" name="xPath" size="80"   class="int"/></span></td>
               </tr>
               
                <tr >
                  <th ><s:Locale code="org.xnamepath" /> ：
                  </th>
                  <td><span class="intspan"><input type="text"  id="xNamePath" name="xNamePath" size="80"   class="int"/></span></td>
               </tr>
               
               <tr>
                  <th ><s:Locale code="org.type" />：</th>
                  <td><input type="text"  id="type" name="type"  size="80"  class="int"/></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.division" />：</th>
                  <td><span class="intspan"><input type="text"  id="division" name="division"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th >
                     <s:Locale code="common.text.sortorder" /> ：
                  </th>
                  <td><span class="intspan"><input type="text"  id="sortOrder" name="sortOrder"  size="80"  class="int"/></span></td>
               </tr>
              
               <tr>
                  <th ><s:Locale code="common.text.description" />：</th>
                  <td><span class="intspan"><input type="text"  id="description" name="description"  size="80"  class="int"/></span></td>
               </tr>
            </table>
            <table id="table_switch_extra"  class="datatable"  width="600px" style="display:none">
            	<tr>
                  <th ><s:Locale code="org.contact" />：</th>
                  <td><span class="intspan"><input type="text"  id="contact" name="contact"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th  width="200px"><s:Locale code="org.phone" />：</th>
                  <td><span class="intspan"><input type="text"  id="phone" name="phone"  size="80"  class="int"/></span></td>
               </tr>
                <tr>
                  <th ><s:Locale code="org.email" />：</th>
                  <td><span class="intspan"><input type="text"  id="email" name="email"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.fax" />：</th>
                  <td><span class="intspan"><input type="text"  id="fax" name="fax"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.country" />：</th>
                  <td><span class="intspan"><input type="text"  id="country" name="country"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.region" />：</th>
                  <td><span class="intspan"><input type="text"  id="region" name="region"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.locality" />：</th>
                  <td><span class="intspan"><input type="text"  id="locality" name="locality"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.street" />：</th>
                  <td><span class="intspan"><input type="text"  id="street" name="street"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.address" />：</th>
                  <td><span class="intspan"><input type="text"  id="address" name="address"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="org.postalcode" />：</th>
                  <td><span class="intspan"><input type="text"  id="postalCode" name="postalCode"  size="80"  class="int"/></span></td>
               </tr>
            </table>
         		</td></tr>
         		<tr><td nowrap class="center">
                              <input id="addChildBtn" class="button"   type="button" style="width:120px"  value="<@locale code="button.text.add" />"/>
                          
                              <input id="saveBtn" class="button"      type="button"   style="width:100px"  value="<@locale code="button.text.save" />"/>
                           
                              <input id="deleteBtn"  class="button"   type="button"   style="width:100px"  value="<@locale code="button.text.delete" />"/>
                           
         		</td></tr>
         	</table>

         </form>
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
