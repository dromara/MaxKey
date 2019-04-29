<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>


<script type="text/javascript">

function onClick (event, treeId, treeNode) {
		$("#actionForm").clearForm();
		$("#resType").selecter("destroy");
		$("#actionForm").json2form({data:treeNode.data});
		$("#_method").val("put");
		$("#status").val("1");
		$("#resType").find("option").attr("selected",false);
		$("#resType").find("option[value='"+treeNode.data.resType+"']").attr("selected",true);
		$("#resType").selecter();
}
	
$(function () {
	$("#addChildBtn").click(function(){
		var nodes = $.fn.zTree.getZTreeObj("resourcesTree").getSelectedNodes();
		if (nodes.length == 0) {
			$.alert({content:"<s:Locale code="system.menus.alert.select.pmenu" />"});
			return;
		}
		$("#actionForm").clearForm();
		$("#pId").val(nodes[0].data.id);
	  	$("#pName").val(nodes[0].data.name);
	  	$("#sortOrder").val(1);
	  	$("#status").val("1");
	    $("#_method").val("post");
	});	
	
	
	$("#saveResourcesBtn").click(function(){
		if($("#_method").val()=="put"){
			$("#actionForm").attr("action",'<s:Base/>/resources/update');
		}else{
			$("#actionForm").attr("action",'<s:Base/>/resources/add');
			var nodedata =	$.fn.zTree.getZTreeObj("resourcesTree").getSelectedNodes()[0].data;
			$("#xNamePath").val(nodedata.xNamePath+"/"+$("#name").val());
			$("#xpath").val(nodedata.xpath+"/"+$("#id").val());
		}
		
		if($("#fullName").val()==""){
			$("#fullName").val($("#name").val());
		}
		if($("#_method").val()=="post"){
			var node=$("#actionForm").serializeObject();
			node.data=$("#actionForm").serializeObject();
			delete node['url'];
			$.fn.zTree.getZTreeObj("resourcesTree").addNodes(
				$.fn.zTree.getZTreeObj("resourcesTree").getSelectedNodes()[0],node);
		}else{
			var node=$("#actionForm").serializeObject();
			node.data=$("#actionForm").serializeObject();
			node=$.extend( $.fn.zTree.getZTreeObj("resourcesTree").getSelectedNodes()[0],node);
			delete node['url'];
			$.fn.zTree.getZTreeObj("resourcesTree").updateNode(node);
		}
		$('#actionForm').submit(); 
	});	
		
		
	$("#deleteResourcesBtn").click(function(){
		$.post('<s:Base/>/resources/delete',{ id:$("#id").val(),_method:"delete"}, function(data) {
			$.fn.zTree.getZTreeObj("resourcesTree").removeNode($.fn.zTree.getZTreeObj("resourcesTree").getSelectedNodes()[0]);
 				$.alert({content:data.message});
		});
	});
});
</script>
	 
	     <!-- content -->  
 <table class="datatable"   width="100%" >
   <tr>
      <td valign="top"  class="td_1" style="vertical-align: top;">
          <s:Tree rootId="1" url="/resources/tree" id="resourcesTree" onClick="onClick"/>
      </td>
      <td  valign="top"  class="td_1" style="vertical-align: top;">
         <div id="orgsTable" style="PADDING:0;MARGIN: 0;width:650px"></div>
         <form  id="actionForm" action='<s:Base/>/resources/add' method="post">
         	<table>
         		<tr>
         			<td>
						<s:Locale code="org.tab.basic" />
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
                  <th ><s:Locale code="resources.pid" />：</th>
                  <td colspan="3"><span class="intspan"><input type="text" readonly id="pId" name="pId" size="80" class="int"/></span></td>
               </tr>
               <tr>
                  <th  width="200px"><s:Locale code="resources.pname" />：</th>
                  <td colspan="3"><span class="intspan"><input type="text" readonly  id="pName" name="pName" size="80"   class="int"/></span></td>
               </tr>
               <tr >
                  <th ><s:Locale code="resources.id" />：</th>
                  <td colspan="3"><span class="intspan"><input type="text" id="id" name="id" size="80"   class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="resources.name" />：</th>
                  <td colspan="3"><span class="intspan"><input type="text"  id="name" name="name"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="resources.resAction" />：</th>
                  <td colspan="3"><span class="intspan">
                  	<input type="text"  id="resAction" name="resAction"  size="80"  class="int"/>
					</span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="resources.resTarget" />：</th>
                  <td colspan="3"><span class="intspan"><input type="text"  id="resTarget" name="resTarget"  size="80"  class="int"/></span></td>
               </tr>
               <tr>
                  <th ><s:Locale code="resources.resType" />：</th>
                  <td><span class="intspan">
                  	<select  id="resType" name="resType" >
                  			<option value="--"   	 >--</option>
							<option value="SYSTEM"   ><s:Locale code="resources.type.sys" /></option>
							<option value="MENU"     ><s:Locale code="resources.type.menu" /></option>
							<option value="DATA"     ><s:Locale code="resources.type.data" /></option>
							<option value="MODULE"   ><s:Locale code="resources.type.module" /></option>
							<option value="LINK"     ><s:Locale code="resources.type.link" /></option>
							<option value="BUTTON"   ><s:Locale code="resources.type.button" /></option>
						</select>
                  </span></td>
              	  <th >
                     <s:Locale code="common.text.sortorder" /> ：
                  </th>
                  <td><span class="intspan"><input type="text"  id="sortOrder" name="sortOrder"  size="80"  class="int"/></span></td>
               
               </tr>
                <tr >
                  <th><s:Locale code="resources.xpath" /> ：
                  </th>
                  <td colspan="3"><span class="intspan"><input type="text"  id="xpath" name="xpath" size="80"   class="int"/></span></td>
               </tr>
                <tr >
                  <th width="100px"><s:Locale code="resources.width" /> ：
                  </th>
                  <td><span class="intspan"><input type="text"  id="width" name="width" size="80"   class="int"/></span></td>
                   <th  width="100px"><s:Locale code="resources.height" />：</th>
                   <td><input type="text"  id="height" name="height"  size="80"  class="int"/></td>
               </tr>
              
               <tr>
                  <th ><s:Locale code="common.text.description" />：</th>
                  <td colspan="3"><span class="intspan"><input type="text"  id="description" name="description"  size="80"  class="int"/></span></td>
               </tr>
            </table>
          
         		</td></tr>
         		<tr><td nowrap class="center">
                              <input id="addChildBtn" class="button"   type="button" style="width:120px"  value="<s:Locale code="button.text.add" />"/>
                          
                              <input id="saveResourcesBtn" class="button"      type="button"   style="width:100px"  value="<s:Locale code="button.text.save" />"/>
                           
                              <input id="deleteResourcesBtn"  class="button"   type="button"   style="width:100px"  value="<s:Locale code="button.text.delete" />"/>
                           
         		</td></tr>
         	</table>

         </form>
      </td>
   </tr>
</table>
