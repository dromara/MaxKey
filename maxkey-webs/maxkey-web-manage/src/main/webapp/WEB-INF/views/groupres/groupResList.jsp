<%@ page   contentType="text/html; charset=UTF-8"%>
<%@ page   import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s"  uri="http://www.connsec.com/tags" %>
<script type="text/javascript">

		function onSelectRow(id){
				$.loading();
				var groupId=$.gridSelIds("#list");
				$("#groupId").val(groupId);
				$.fn.zTree.getZTreeObj("tree").checkAllNodes(false);
				$.post('<c:url value="/privileges/queryPrivileges"/>/'+groupId, {currTime:(new Date()).getTime()}, function(data) {
					if(data&&data.length){
						var allNodes	=	$.fn.zTree.getZTreeObj("tree").transformToArray($.fn.zTree.getZTreeObj("tree").getNodes());
						for (var i=0; i<allNodes.length; i++) {
							for (var j=0; j<data.length; j++) {
								if(allNodes[i].data.id == data[j].id){
									$.fn.zTree.getZTreeObj("tree").checkNode(allNodes[i], true);
								}
							}
						}
					}
					$.unloading();
				});
		};
		
	$(function () {
			
			$("#groupResourcesSaveBtn").click(function(){
				var checkedNodes=$.fn.zTree.getZTreeObj("tree").getCheckedNodes(true);
				if (checkedNodes.length == 0) {
					return;
				}
				var strCheckNodes="";
				for (var i=0; i<checkedNodes.length; i++) {
					if(i>0){
						strCheckNodes	+=	","+checkedNodes[i].data.id;
					}else{
						strCheckNodes	=	checkedNodes[i].data.id;
					}
						
				}
				$("#resourceId").val(strCheckNodes);
				
				if($("#groupId").val()=="")return;
				
		    	$("#submitBtn").click();
			});
			
		 var tempScrollTop, currentScrollTop = 0;
		 $(window).scroll(function () { 
			currentScrollTop = $(window).scrollTop();
			if (tempScrollTop < currentScrollTop ){//scrolling down
				
			}else if (tempScrollTop > currentScrollTop ){//scrolling up
				tempScrollTop = currentScrollTop;
			}
			if(currentScrollTop<20){
				$("#navsTable").css("display","none");
			}else{
				$("#navsTable").css("display","block");
				$("#navsTable").css("height",currentScrollTop>45?currentScrollTop-105:currentScrollTop-40);
			}
		});
	
	});
</script>

<div class="main">
    <div class="mainin">			 
 	        <!-- content -->  
	<div id="tool_box">
	 		<table   class="datatable">
 				<tr>
		 			<td width="120px"><s:Locale code="group.name"/>:</td>
		 			<td width="374px">
		 				<form id="basic_search_form">
				 			<input type="text" name="name" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 		</form>
		 			</td>
				 	<td colspan="2"> <div id="tool_box_right">
							<input  class="button" id="groupResourcesSaveBtn" type="button" value="<s:Locale code="button.text.save"/>">
				 	</div>
				 	</td>
				</tr>
			
			</table>
 	</div>
 	
		<table  class="datatable"  width="100%">
			<tbody>
				<tr>
					<td valign="top"  class="td_1" width="30%" style="vertical-align: top;">
						<div id="navsTable" style="PADDING:0;MARGIN: 0;"></div>
						<s:Grid id="list" url="/groups/grid" multiselect="false" resize="false" onSelect="onSelectRow">	
							<s:Column width="0" field="id" title="id" hidden="true"/>
							<s:Column width="250" field="name" title="group.name"/>
							<s:Column width="200" field="description" title="common.text.description" hidden="true"/>
							<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
							<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
							<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
							<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
						</s:Grid>
					</td>
					<td valign="top"  class="td_1" width="70%" style="vertical-align: top;">
						
						<s:Tree rootId="1" url="/resources/tree" id="tree" checkbox="true"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div style="display:none">
	<form id="actionForm" method="post" action="<c:url value='/privileges/insert'/>">
		<table>
			<tr><td></td><td><input type="text" id="groupId" name="groupId" value=""/></td></tr>
			<tr><td></td><td><input type="text" id="resourceId" name="resourceId" value=""/></td></tr>
			<tr><td colspan="2"><input id="submitBtn" type="button" value="submitBtn"></input></td></tr>
		</table>
		
	</form>
</div>
