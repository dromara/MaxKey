<%@ page 	contentType="text/html; charset=UTF-8"%>
<%@ page 	import="org.maxkey.domain.*"%> 
<%@ page 	import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page 	import="org.maxkey.web.*"%>
<%@ taglib  prefix="s"   		uri="http://sso.maxkey.org/tags" %>
<%@ taglib  prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML >
<html>
<head>
	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/common.cssjs.jsp"></jsp:include>
	<script type="text/javascript" src="<s:Base/>/jquery/jsonformatter.js"></script>
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
</head>
<body>
<jsp:include page="../layout/top.jsp"></jsp:include>
<jsp:include page="../layout/nav_primary.jsp"></jsp:include>

<div class="container">

<div class="mainwrap" id="main">
	
	<table  data-url="<s:Base />/logs/grid"
			id="datagrid"
			data-toggle="table"
			data-classes="table table-bordered table-hover table-striped"
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
				<th data-sortable="true" data-field="id"   data-visible="false">id</th>
				<th data-field="serviceName"><s:Locale code="log.operate.servicename"/></th>
				<th data-field="message"><s:Locale code="log.operate.message"/></th>
				<th data-field="view"><s:Locale code="log.operate.content"/></th>
				<th data-field="messageType"><s:Locale code="log.operate.messageType"/></th>
				<th data-field="operateType"><s:Locale code="log.operate.operateType"/></th>
				<th data-field="username"><s:Locale code="log.operate.username"/></th>
				<th data-field="createdBy"><s:Locale code="common.text.createdby"/></th>
				<th data-field="createdDate"><s:Locale code="common.text.createddate"/></th>
				<th data-field="modifiedBy"><s:Locale code="common.text.modifiedby"/></th>
				<th data-field="modifiedDate"><s:Locale code="common.text.modifieddate"/></th>
			</tr>
		</thead>
	</table>
	
</div>
</div>
<div id="footer">
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</div>
</body>
</html>