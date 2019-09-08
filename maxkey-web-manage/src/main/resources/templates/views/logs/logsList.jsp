<%@ page 	contentType="text/html; charset=UTF-8"%>
<%@ page 	import="org.maxkey.domain.*"%> 
<%@ page 	import="java.util.Map,java.util.LinkedHashMap"%>
<%@ taglib  prefix="s"   uri="http://www.connsec.com/tags" %>
<%@ taglib  prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	function viewformatter (value, options, rData){
		return "<a href='javascript:void(0);' selid='"+rData["id"]+"' class='viewJsonObject' title='view more' >view more</a>";
	}
				
	$(".viewJsonObject").on("click",function(){
			var content=$("#list").getRowData($(this).attr("selid")+"")["content"]; 
			var jsonHtml='<textarea name="jsondata" id="formatteddata" rows="20" cols="70">';
				jsonHtml+=FormatJSON(eval("("+content+")"));
				jsonHtml+='</textarea>';
			$.alert({
				title		: 	"JSON Data View",
				type		:	null,
			    content		: 	jsonHtml,
			    okVal		:	null,
			    cancelVal	:	$.platform.messages.alert.no,
			    ok			: 	null,
			    cancel		: 	function (){}
			});
		});

</script>
	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td width="120px"><s:Locale code="logs.servicename"/>:</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input  style="width:200px"   id="serviceName" name="serviceName" type="text" value="" >
				 			
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>"/>
				 			
					 	</form>
				</td>
				<td colspan="2"> 
					 <input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
					 	
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table   class="datatable">
	 			<tr>
	 				<td width="120px"><s:Locale code="logs.message"/></td>
		 			<td width="360px">
		 				<input name="message" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="logs.operatetype"/></td>
		 			<td width="360px">
		 				<select id="operateType" name="operateType"  >
		 					<option value=""  selected>All</option>
							<option value="add"     >Add</option>
							<option value="update"  >Update</option>
							<option value="delete"  >Delete</option>
						</select>
			 		</td>
			 </tr>
			 <tr>
	 				<td width="120px"><s:Locale code="common.text.startdate"/></td>
		 			<td width="360px">
		 				<input id="datepickerstart" name="startDate" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="common.text.enddate"/></td>
		 			<td width="360px">
						<input id="datepickerend"  type="text" id="endDate" name="endDate"  title="" value=""/>
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>
<div class="mainwrap" id="main">
	
	<s:Grid id="list" url="/logs/grid" multiselect="false">	
		<s:Column width="0" field="id" title="id" hidden="true"/>
		<s:Column width="100" field="serviceName" title="logs.servicename"/>
		<s:Column width="100" field="message" title="logs.message"/>
		<s:Column width="100" field="view" title="logs.content" formatter="viewformatter"/>
		<s:Column width="100" field="messageType" title="logs.messagetype"/>				
		<s:Column width="100" field="operateType" title="logs.operatetype" />
		<s:Column width="100" field="username" title="userinfo.username" />
		<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
		<s:Column width="100" field="createdDate" title="common.text.createddate" hidden="false"/>
		<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
		<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
	</s:Grid>
	
</div>