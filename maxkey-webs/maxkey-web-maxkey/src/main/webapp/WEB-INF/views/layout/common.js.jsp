<%@ page    language="java"     import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib  prefix="s"  uri="http://sso.maxkey.org/tags" %>
	<%-- javascript js begin  --%>
	<%-- jquery base --%>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery-ui-1.10.3.custom.min.js"></script>
	<%-- metadata --%>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery.metadata.js"></script>
	<%--free-jqGrid 4.13.4--%>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/jquery/jqGrid-4.13.4/css/ui.jqgrid.css"/>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/css/grid.css"/>
	<script type="text/javascript" src="<s:Base/>/jquery/jqGrid-4.13.4/js/i18n/grid.locale-<s:Locale />.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/jqGrid-4.13.4/js/jquery.jqgrid.src.js"></script> 
	
	<%-- zTreev 3.5--%>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/jquery/zTree-v3.5.24/css/zTreeStyle/zTreeStyle.css"/>
	<script type="text/javascript" src="<s:Base/>/jquery/zTree-v3.5.24/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/zTree-v3.5.24/js/jquery.ztree.excheck.js"></script>
	<%-- artDialog-5.0.4 --%>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/jquery/artDialog-5.0.4/skins/platform.css"  />
	<script type="text/javascript" src="<s:Base/>/jquery/artDialog-5.0.4/jquery.artDialog.min.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/artDialog-5.0.4/artDialog.plugins.min.js"></script>
	<%-- selecter-2.1.4 --%>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/jquery/selecter-3.2.3/jquery.fs.selecter.css"  />
	<script type="text/javascript" src="<s:Base/>/jquery/selecter-3.2.3/jquery.fs.selecter.js"></script>
	<%-- DateTimePicker-2.4.1 --%>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/jquery/DateTimePicker-2.4.1/jquery.datetimepicker.css"  />
	<script type="text/javascript" src="<s:Base/>/jquery/DateTimePicker-2.4.1/jquery.datetimepicker.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery.cookie.js"></script>
	<%-- form --%>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/json2form/json2form.js"></script>
	<%-- blockUI --%>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery.blockUI.js"></script>
	<%-- serializeObject --%>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery.serialize-object.min.js"></script>
	<%-- validation --%>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery-validation-1.11.1/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/jquery-validation-1.11.1/localization/messages_<s:Locale />.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/jsonformatter.js"></script>
	<script type="text/javascript" src="<s:Base/>/jquery/switchtab/switchtab.js"></script>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/jquery/switchtab/switchtab.css"/>
	<%-- common script start --%>
	<script type="text/javascript">
		$(function () {
			$(".datetimepicker").datetimepicker({format:'Y-m-d H:i',lang:'<s:Locale/>'});
			$(".datepicker").datetimepicker({timepicker:false,format:'Y-m-d',lang:'<s:Locale/>'});
			
			$.platform = $.platform || {};
			$.platform.messages = $.platform.messages || {};
			$.extend($.platform.messages, {
				window:	{
					title		:	'<s:Locale code="common.window.title" />'
				},
				alert:	{
					title		:	'<s:Locale code="common.alert.title" />',
					closeText	:	'<s:Locale code="common.alert.closeText" />'
				},
				conform:{
					title		:	'<s:Locale code="common.conform.title" />',
					yes			:	'<s:Locale code="common.conform.yes" />',
					no			:	'<s:Locale code="common.conform.no" />'
				},
				select:{
					alertText	: 	'<s:Locale code="common.select.hintText" />'
				},
				del:{
					conformText	: 	'<s:Locale code="common.delete.hintText" />'
				},
				grid:{
					loadtext	:	'<s:Locale code="common.grid.loadtext" />',
					loadnodata	:	'<s:Locale code="common.grid.loadnodata" />'
				},
				submit:{
					conformText	:	'<s:Locale code="common.submit.hintText" />',
					errorText	:	'<s:Locale code="common.submit.errorText" />'
				}
			});
		});
	</script>
	<%-- common script end --%>
	<%-- platform common script --%>	
	<script type="text/javascript" src="<s:Base/>/jquery/platform.common.js"></script>
	<%-- common js end  --%>