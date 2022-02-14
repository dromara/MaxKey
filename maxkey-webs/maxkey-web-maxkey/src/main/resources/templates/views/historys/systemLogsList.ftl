<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
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
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>

<div class="container">

<div class="mainwrap" id="main">
	
	<table  data-url="<@base />/historys/systemLogsList/grid"
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
				<th data-field="serviceName"><@locale code="log.operate.servicename"/></th>
				<th data-field="message"><@locale code="log.operate.message"/></th>
				<th data-field="view"><@locale code="log.operate.content"/></th>
				<th data-field="messageType"><@locale code="log.operate.messageType"/></th>
				<th data-field="operateType"><@locale code="log.operate.operateType"/></th>
				<th data-field="username"><@locale code="log.operate.username"/></th>
				<th data-field="createdBy"  data-visible="false"><@locale code="common.text.createdby"/></th>
				<th data-field="createdDate"><@locale code="common.text.createddate"/></th>
				<th data-field="modifiedBy"  data-visible="false"><@locale code="common.text.modifiedby"/></th>
				<th data-field="modifiedDate"><@locale code="common.text.modifieddate"/></th>
			</tr>
		</thead>
	</table>
	
</div>
</div>
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
</body>
</html>