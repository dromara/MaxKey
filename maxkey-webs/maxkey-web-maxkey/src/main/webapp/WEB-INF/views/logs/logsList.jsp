<%@ page 	contentType="text/html; charset=UTF-8"%>
<%@ page 	import="org.maxkey.domain.*"%> 
<%@ page 	import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page 	import="org.maxkey.web.*"%>
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

<div class="mainwrap" id="main">
	
	<s:Grid id="list" url="/logs/grid" multiselect="false">	
		<s:Column width="0" field="id" title="id" hidden="true"/>
		<s:Column width="100" field="serviceName" title="logs.servicename"/>
		<s:Column width="100" field="message" title="logs.message"/>
		<s:Column width="100" field="view" title="logs.content" formatter="viewformatter"/>
		<s:Column width="100" field="messageType" title="logs.messagetype"/>				
		<s:Column width="100" field="operateType" title="logs.operatetype" />
		<s:Column width="100" field="username" title="userinfo.username" />
		<s:Column width="100" field="tname" title="company.shortname" /> 
		<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
		<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
		<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
		<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
	</s:Grid>
	
</div>