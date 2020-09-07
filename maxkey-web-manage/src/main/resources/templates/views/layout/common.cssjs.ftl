	<#-- javascript js begin  -->
	<#-- jquery base -->
	<script src ="<@base />/static/jquery/jquery-3.5.0.min.js"  type="text/javascript"></script>
	<script src ="<@base />/static/jquery/popper.min.js" type="text/javascript" ></script>
	<#-- bootstrap-4.4.1 -->
    <link   href="<@base />/static/bootstrap-4.4.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script src ="<@base />/static/bootstrap-4.4.1/js/bootstrap.min.js" type="text/javascript" ></script>
	<#-- font-awesome-4.7.0 -->
	<link   href="<@base />/static/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
	<#-- metadata -->
	<script src ="<@base />/static/jquery/jquery.metadata.js" type="text/javascript" ></script>
	<#--bootstrap-table-1.16.0-->
    <link   href="<@base />/static/jquery/bootstrap-table-v1.16.0/bootstrap-table.css"      type="text/css" rel="stylesheet" />
    <script src ="<@base />/static/jquery/bootstrap-table-v1.16.0/bootstrap-table.min.js"   type="text/javascript" ></script> 
    <script src ="<@base />/static/jquery/bootstrap-table-v1.16.0/bootstrap-table-locale-all.min.js"    type="text/javascript" ></script>
	<#-- zTreev 3.5-->
	<link 	href="<@base />/static/jquery/zTree-v3.5.40/css/zTreeStyle/zTreeStyle.css"  type="text/css" rel="stylesheet"/>
	<script src ="<@base />/static/jquery/zTree-v3.5.40/js/jquery.ztree.core.js" type="text/javascript" ></script>
	<script src ="<@base />/static/jquery/zTree-v3.5.40/js/jquery.ztree.excheck.js" type="text/javascript" ></script>
	<#-- artDialog-5.0.4 -->
	<link 	href="<@base />/static/jquery/artDialog-5.0.4/skins/platform.css"  type="text/css" rel="stylesheet"/>
	<script src ="<@base />/static/jquery/artDialog-5.0.4/jquery.artDialog.min.js" type="text/javascript" ></script>
	<script src ="<@base />/static/jquery/artDialog-5.0.4/artDialog.plugins.min.js" type="text/javascript" ></script>
	<#-- datetimepicker-2.5.20 -->
	<link 	href="<@base />/static/jquery/datetimepicker-2.5.20/jquery.datetimepicker.css"  type="text/css" rel="stylesheet" />
	<script src ="<@base />/static/jquery/datetimepicker-2.5.20/build/jquery.datetimepicker.full.js" type="text/javascript" ></script>
	<script src ="<@base />/static/jquery/jquery.cookie.js" type="text/javascript" ></script>
	<#-- form -->
	<script src ="<@base />/static/jquery/jquery.form.js" type="text/javascript" ></script>
	<script src ="<@base />/static/jquery/json2form/json2form.js" type="text/javascript" ></script>
	<#-- blockUI -->
	<script src ="<@base />/static/jquery/jquery.blockUI.js" type="text/javascript" ></script>
	<#-- serializeObject -->
	<script src ="<@base />/static/jquery/jquery.serialize-object.min.js" type="text/javascript" ></script>
	
	<script src ="<@base />/static/jquery/jsonformatter.js" type="text/javascript" ></script>
	<script src ="<@base />/static/jquery/switchtab/switchtab.js" type="text/javascript" ></script>
	<link 	href="<@base />/static/jquery/switchtab/switchtab.css" type="text/css" rel="stylesheet" />
	<#-- metisMenu-v3.0.4 -->
	<link   href="<@base />/static/jquery/metisMenu-v3.0.6/css/mm-vertical.css"  rel="stylesheet" >
	<link   href="<@base />/static/jquery/metisMenu-v3.0.6/metisMenu.min.css"  rel="stylesheet" >
	<script src ="<@base />/static/jquery/metisMenu-v3.0.6/metisMenu.min.js" type="text/javascript" ></script>
	<script src ="<@base />/static/js/Chart.min.js" type="text/javascript" ></script>
	<#-- multiple-select-1.5.2 -->
	<script type="text/javascript" src="<@base />/static/jquery/multiple-select-1.5.2/multiple-select.min.js"></script>
	<script type="text/javascript" src="<@base />/static/jquery/multiple-select-1.5.2/locale/multiple-select-<@locale/>.js"></script>
	<link rel="stylesheet" href="<@base />/static/jquery/multiple-select-1.5.2/multiple-select.css" type="text/css"/>
	
	<#-- common script start -->
	<script type="text/javascript">
		$(function () {
			 $(".sidenav-fold-toggler").on("click",function(e) {
	        	   $(".app").toggleClass("side-nav-folded");
                   e.preventDefault();
	         });
	         
	         $('.side-nav-menu').each(function(){
                   var href = $(this).attr('href');
                   if(window.location.href.indexOf(href) > 0){
                        $(this).parents("li").addClass("mm-active");
                        
                   }
             });
	         
	        $.datetimepicker.setLocale('<@locale/>'.substring(0, 2));
			$(".datetimepicker").datetimepicker({format:'Y-m-d H:i'});
			$(".datepicker").datetimepicker({timepicker:false,format:'Y-m-d'});
			
			$(".multipleselect").multipleSelect({}); 
			
			$.platform = $.platform || {};
			$.platform.messages = $.platform.messages || {};
			$.extend($.platform.messages, {
				window:	{
					title		:	'<@locale code="common.window.title" />'
				},
				alert:	{
					title		:	'<@locale code="common.alert.title" />',
					closeText	:	'<@locale code="common.alert.closeText" />'
				},
				conform:{
					title		:	'<@locale code="common.conform.title" />',
					yes			:	'<@locale code="common.conform.yes" />',
					no			:	'<@locale code="common.conform.no" />'
				},
				select:{
					alertText	: 	'<@locale code="common.select.hintText" />'
				},
				del:{
					conformText	: 	'<@locale code="common.delete.hintText" />'
				},
				grid:{
					loadtext	:	'<@locale code="common.grid.loadtext" />',
					loadnodata	:	'<@locale code="common.grid.loadnodata" />'
				},
				submit:{
					conformText	:	'<@locale code="common.submit.hintText" />',
					errorText	:	'<@locale code="common.submit.errorText" />'
				}
			});
		});
	</script>
	<#-- common script end -->
	<#-- platform common script -->	
	<script src ="<@base />/static/jquery/platform.common.js" type="text/javascript" ></script>
	<#-- common js end  -->
	<#-- common css begin -->
	<#-- if browser is not msie 6.0,follow styles over ie 6.0 style -->
	<link type="text/css" rel="stylesheet"  href="<@base />/static/css/base.css"/>
	<link type="text/css" rel="stylesheet"  href="<@base />/static/css/menu.css"/>
	<link type="text/css" rel="stylesheet" href="<@base />/static/css/login.css"/>
	<#-- common css end -->