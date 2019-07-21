<%@ page    language="java"     import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib  prefix="s"  uri="http://sso.maxkey.org/tags" %>
	<%-- javascript js begin  --%>
	<%-- jquery base --%>
	<script	src ="<s:Base/>/jquery/jquery-3.4.1.min.js"	type="text/javascript"></script>
	<script src ="<s:Base/>/jquery/popper.min.js" type="text/javascript" ></script>
	<%-- bootstrap-4.3.1 --%>
	<link 	href="<s:Base/>/bootstrap-4.3.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src ="<s:Base/>/bootstrap-4.3.1/js/bootstrap.min.js" type="text/javascript" ></script>
	<%-- metadata --%>
	<script src ="<s:Base/>/jquery/jquery.metadata.js" type="text/javascript" ></script>
	<%--bootstrap-table-1.14.2--%>
	<link 	href="<s:Base/>/jquery/bootstrap-table-1.14.2/bootstrap-table.css" 		type="text/css" rel="stylesheet" />
	<script src ="<s:Base/>/jquery/bootstrap-table-1.14.2/bootstrap-table.min.js"	type="text/javascript" ></script> 
	<script src ="<s:Base/>/jquery/bootstrap-table-1.14.2/bootstrap-table-locale-all.min.js"	type="text/javascript" ></script>
	<%-- zTreev 3.5--%>
	<link 	href="<s:Base/>/jquery/zTree-v3.5.40/css/zTreeStyle/zTreeStyle.css"  type="text/css" rel="stylesheet"/>
	<script src ="<s:Base/>/jquery/zTree-v3.5.40/js/jquery.ztree.core.js" type="text/javascript" ></script>
	<script src ="<s:Base/>/jquery/zTree-v3.5.40/js/jquery.ztree.excheck.js" type="text/javascript" ></script>
	<%-- artDialog-5.0.4 --%>
	<link 	href="<s:Base/>/jquery/artDialog-5.0.4/skins/platform.css"  type="text/css" rel="stylesheet"/>
	<script src ="<s:Base/>/jquery/artDialog-5.0.4/jquery.artDialog.min.js" type="text/javascript" ></script>
	<script src ="<s:Base/>/jquery/artDialog-5.0.4/artDialog.plugins.min.js" type="text/javascript" ></script>
	<%-- datetimepicker-2.5.20 --%>
	<link 	href="<s:Base/>/jquery/datetimepicker-2.5.20/jquery.datetimepicker.css"  type="text/css" rel="stylesheet" />
	<script src ="<s:Base/>/jquery/datetimepicker-2.5.20/build/jquery.datetimepicker.full.js" type="text/javascript" ></script>
	<script src ="<s:Base/>/jquery/jquery.cookie.js" type="text/javascript" ></script>
	<%-- form --%>
	<script src ="<s:Base/>/jquery/jquery.form.js" type="text/javascript" ></script>
	<script src ="<s:Base/>/jquery/json2form/json2form.js" type="text/javascript" ></script>
	<%-- blockUI --%>
	<script src ="<s:Base/>/jquery/jquery.blockUI.js" type="text/javascript" ></script>
	<%-- serializeObject --%>
	<script src ="<s:Base/>/jquery/jquery.serialize-object.min.js" type="text/javascript" ></script>
	<%-- validation --%>
	<script src ="<s:Base/>/jquery/jquery-validation-1.11.1/jquery.validate.min.js" type="text/javascript" ></script>
	<script src ="<s:Base/>/jquery/jquery-validation-1.11.1/localization/messages_<s:Locale />.js" type="text/javascript" ></script>
	<script src ="<s:Base/>/jquery/jsonformatter.js" type="text/javascript" ></script>
	<script src ="<s:Base/>/jquery/switchtab/switchtab.js" type="text/javascript" ></script>
	<link 	href="<s:Base/>/jquery/switchtab/switchtab.css" type="text/css" rel="stylesheet" />
	<%-- common script start --%>
	<script type="text/javascript">
		$(function () {
			$(".datetimepicker").datetimepicker({format:'Y-m-d H:i',lang:'<s:Locale/>'.substring(0, 2)});
			$(".datepicker").datetimepicker({timepicker:false,format:'Y-m-d',lang:'<s:Locale/>'.substring(0, 2)});
			
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
	<script src ="<s:Base/>/jquery/platform.common.js" type="text/javascript" ></script>
	<%-- common js end  --%>
	<%-- common css begin --%>
	<%-- if browser is not msie 6.0,follow styles over ie 6.0 style --%>
	<link type="text/css" rel="stylesheet"  href="<s:Base/>/css/base.css"/>
	<link type="text/css" rel="stylesheet"  href="<s:Base/>/css/menu.css"/>
	<link type="text/css" rel="stylesheet" href="<s:Base/>/css/login.css"/>
	<%-- common css end --%>