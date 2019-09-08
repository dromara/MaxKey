/*
 * crystal.sea
 */

/* for datagrid  queryParams*/
function dataGridQueryParams(params) {
	var postData={};
	if($("#basic_search_form")){//o.length>0
		postData=$("#basic_search_form").serializeObject();
	}
	
	if($("#advanced_search")){//o.length>0
		postData=$.extend(postData,$("#advanced_search_form").serializeObject()||{});
	}
	
	params=$.extend(params,postData);
	
    return params;
}

 //jquery begin
$(function(){

	//document forward
	$.forward=function(config){
		if(config.url){//to url
			document.location.href=config.url;
		}else if(config.href){//to href
			document.location.href=config.href;
		}else if(config.elem){//to elem by url attr
			if($("#"+config.elem).attr("wurl")){
				document.location.href=$("#"+config.elem).attr("wurl");
			}
		}else{
			document.location.href=config;
		}
	};	
	
	//ajax loading...,mask with loading icon
	$.loading=function(){
		$.blockUI({ 
				message: "<div class='ajaxloading'></div>", 
				css: { 
					top		:  	($(window).height() - 80) /2 + 'px', 
					left	: 	($(window).width() - 80) /2 + 'px', 
					width	: 	'78px',
					border	:	0,
					backgroundColor	:	'transparent'
					},
			overlayCSS:  { 
				opacity	:	0
			}
		}); 
	};
	//cancel $.loading()'s mask
	$.unloading=function(){
		$.unblockUI();
	};
	
	//document mask
	$.mask=function(){
		$.blockUI({ 
			message: "", 
			css: { 
               top		:  	($(window).height()) /2 + 'px', 
               left		: 	($(window).width()) /2 + 'px', 
               border	:	0.1,
               backgroundColor	:	'transparent'
			},
			overlayCSS:  { 
				opacity	:	0
			}
		}); 
	};
	//unmask $.mask()'s mask
	$.unmask=function(){
		$.unblockUI();
	};
	
	//define message
	$.extend($.platform.messages, {
		messageType : {
			information		:	"information",
			error			:	"error",
			question		:	"question",
			warning			:	"warning",
			succeed			:	"succeed",
			//
			success			:	"succeed",
			fail			:	"error",
			info			:	"information",
			prompt			:	"information"
		}
	});
	
	$(".checkbox").each(function(){
	    $(this).after("<span class='checkboxspan icon_checkbox' for='"+this.id+"'></span>");
	    $(this).hide();
	});
	
	//add button
	$(".form_checkbox_label").click(function(){
		var checkboxElem=$(this).find(".checkboxspan");
		var forElem=checkboxElem.attr("for");
		if(checkboxElem.hasClass("icon_checkbox")){
			$("#"+forElem).prop("checked",true);
			checkboxElem.removeClass("icon_checkbox");
			checkboxElem.addClass("icon_checkbox_selected");
			
		}else{
			checkboxElem.removeClass("icon_checkbox_selected");
			checkboxElem.addClass("icon_checkbox");
			$("#"+forElem).prop("checked",false);
		}
	});	
	
	//window
	$.window=function(settings){
		var settings=$.extend({
			title		:	$.platform.messages.window.title,
			width		:	400,
			height		:	340,
			lock		: 	true,  
			background	: 	"eeeeee",
			opacity		: 	0.1	,
			init: function () {
				$.mask();
			},
			close: function(){
				$.unmask();
			}
		}, settings || {});

		var winContent="<iframe " +
							"scrolling='no' " +
							"frameborder='0' " +
							"width='"+settings.width+"' " +
							"height='"+settings.height+"' " +
							"src='"+settings.url+"'" +
							"></iframe>";
		
		$.dialog({//open iframe
		    title		: 	settings.title,
		    content		: 	winContent,
		    width		:	settings.width,
		    height		:	settings.height,
		    icon 		:	settings.type,
		    init		:	settings.init,
		    lock		: 	true,  
		    background	: 	"eeeeee",
		    padding		: 	'0px 25px',
		    opacity		: 	0.1,	
		    ok			:	function () {},
		    cancel		: 	function () {},
		    button		: 	[{
		    				value		:	settings.closeText,
							callback	: 	settings.callback,
							disabled	:	false,
							focus		:	true
							}]
		});
		$(".d-footer").hide();
	};
	//close window
	$.closeWindow=function(){
		$(".d-buttons", window.parent.document).find("[value=cancel]").click();
	};
	 
	//alert dialog
	$.alert=function(settings){
	 	var settings=$.extend({
			title		:	$.platform.messages.alert.title,
			type		:	"warning",//alert type
			width		:	300,
			height		:	80,
			callback	:	function(){},
			init		:	null,
			closeText	:	$.platform.messages.alert.closeText//close text
		}, settings || {});

	 	$.dialog({//open
		    title		: 	settings.title,
		    content		: 	settings.content,
		    width		: 	settings.width,
		    height		: 	settings.height,
		    icon 		:	settings.type,
		    init		:	settings.init,
		    lock		: 	true,  
		    background	: 	"eeeeee",
		    opacity		: 	0.1,	
		    okValue		:	null,
		    cancelValue	:	null,
		    button		: 	[{
		    				value		:	settings.closeText,
							callback	: 	settings.callback,
							disabled	:	false,
							focus		:	true
							}]
		});
	 	$(".d-footer").show();
	};
	//conform dialog
	$.conform=function(settings){
	 	var settings=$.extend({
	 		title		:	$.platform.messages.alert.title,
	 		content		:	"",
			type		:	"question",//default type
			width		:	300,
			height		:	80,
			callback	:	function (){},//callback function
			init		:	null//init function
		}, settings || {});

	 	$.dialog({
				title		: 	settings.title,
			    content		: 	settings.content,
			    width		: 	settings.width,
			    height		: 	settings.height,
			    icon		: 	settings.type,
			    lock		: 	true,  
			    background	: 	"eeeeee",
			    opacity		: 	0.1,	
			    okValue		:	$.platform.messages.conform.yes,//yes
			    cancelValue	:	$.platform.messages.conform.no,//no
			    ok			: 	settings.callback,
			    cancel		: 	function (){}
			});
	 	$(".d-footer").show();
	};
	
	//window open by element is window style
	$(".window").on("click",function(){
		if (typeof(beforeWindow) == "function"){
			beforeWindow();///before open action
		}
		
		var url=$(this).attr("wurl");
		
		if($(this).attr("ref")){
			url=url+"/"+$("#"+$(this).attr("ref")).val();
		}
		
		var settings={
				url		:	url,//window url
				title	:	$(this).attr("wtitle"),//title
				width	:	$(this).attr("wwidth"),//width
				height	:	$(this).attr("wheight")//height
			};
		$.window(settings);//open window
	});
	
	//forward to url, by forward style
	$(".forward").on("click",function(){
		var settings={
				url		:	$(this).attr("wurl"),//current element url
				href	:	$(this).attr("wurl")//current element href
			};
		$.forward(settings);
	});
	
	//advanced_search toggle
	$("#advancedSearchExpandBtn").click(function(){
		$("#advanced_search").toggle();
		if($("#advanced_search").is(":visible")){
			$(this).val($(this).attr("collapseValue"));
		}else{
			$(this).val($(this).attr("expandValue"));
			$("#advanced_search_form").resetForm();
		}
	});
	
	//search button
	$("#searchBtn").on("click",function(){
		$("#list_pager").show();
		if (typeof(beforeSearch) == "function"){
			beforeSearch();///before Search action
		}
		
		//query for grid list
		 $('#datagrid').bootstrapTable('refresh',  {});
		
		if (typeof(afterSearch) == "function"){
			afterSearch();//call back 
		}
	});

	//close button
	$("#closeBtn").click(function(){
		$.closeWindow();
	});
	
	//add button
	$("#addBtn").click(function(){
		if (typeof(addAction) == "function"){
				document.location.href=addAction(this);//user define forward
		}else {
			if (typeof(beforeAdd) == "function"){
				beforeAdd(this);
			}
			if($(this).attr("target")&&$(this).attr("target")=="forward"){
				if($(this).attr("ref")){
					if($("#"+$(this).attr("ref")+"").val()==""){
						$.forward($(this).attr("wurl"));
					}else{
						$.forward($(this).attr("wurl")+"/"+$("#"+$(this).attr("ref")+"").val());
					}
					
				}else{
					$.forward($(this).attr("wurl"));
				}
			}else{
				var settings={
						url		:	$(this).attr("wurl"),//window url
						title	:	$(this).attr("wtitle"),//title
						width	:	$(this).attr("wwidth"),//width
						height	:	$(this).attr("wheight")//height
					};
				if($(this).attr("ref")){
					if($("#"+$(this).attr("ref")+"").val()==""){
						settings.url=$(this).attr("wurl");
					}else{
						settings.url=$(this).attr("wurl")+"/"+$("#"+$(this).attr("ref")+"").val();
					}
					
				}else{
					settings.url=$(this).attr("wurl");
				}
				$.window(settings);//open window
			}
		}
	});
	
	//modify button
	$("#modifyBtn").click(function(){
		if (typeof(updateAction) == "function"){
			document.location.href=updateAction(this);//自定义跳转
		}else {
			if (typeof(beforeUpdate) == "function"){
				beforeUpdate(this);
			}
			
			var selectId=null;
			if($("#list2").length>0){//get grid list selected ids
					selectId=$("#list2").jqGrid("getGridParam", "selrow");
					var rowData = $("#list2").jqGrid("getRowData", selectId);
					selectId=rowData.id;	
			}else if($("#list").length>0){//get grid list selected ids
					selectId=$("#list").jqGrid("getGridParam", "selrow");
					var rowData = $("#list").jqGrid("getRowData", selectId);
					selectId=rowData.id;
			}
			
			if(selectId == null || selectId == "") {
				$.alert({content:$.platform.messages.select.alertText});
				return;
			}
			
			
			if($(this).attr("target")&&$(this).attr("target")=="forward"){
				$.forward($(this).attr("wurl")+"/"+selectId);	
			}else{
				var settings={
						url		:	$(this).attr("wurl")+"/"+selectId,//window url
						title	:	$(this).attr("wtitle"),//title
						width	:	$(this).attr("wwidth"),//width
						height	:	$(this).attr("wheight")//height
					};
				$.window(settings);//open window
			};
		};
		
	});

	//view button
	$("#viewBtn").click(function(){
		if (typeof(viewAction) == "function"){
			document.location.href=viewAction(this);//自定义跳转
		}else {
			if (typeof(beforeView) == "function"){
				beforeView(this);//自定义跳转
			}
			var selectId="";
			if($("#list2").length>0){//get grid list selected ids
				selectId=$("#list2").jqGrid("getGridParam", "selrow");
				if(selectId ==	null ||	selectId	==	""){
					$.alert({content:$.platform.messages.select.alertText});
					return;
				}
				var rowData = $("#list2").jqGrid("getRowData", selectId);
				selectId=rowData.id;
			}else if($("#list").length>0){//get grid list selected ids
				selectId=$("#list").jqGrid("getGridParam", "selrow");
				if(selectId ==	null ||	selectId	==	""){
					$.alert({content:$.platform.messages.select.alertText});
					return;
				}
				var rowData = $("#list").jqGrid("getRowData", selectId);
				selectId=rowData.id;
			}
			if($(this).attr("target")&&$(this).attr("target")=="forward"){
				$.forward($(this).attr("wurl")+"/"+selectId);	
			}else{
				var settings={
						url		:	$(this).attr("wurl")+"/"+selectId,//window url
						title	:	$(this).attr("wtitle"),//title
						width	:	$(this).attr("wwidth"),//width
						height	:	$(this).attr("wheight")//height
					};
				$.window(settings);//open window
			}
		}
	});

	//delete and batch delete button
	$("#deleteBtn").click(function(){
		if (typeof(beforeDelete) == "function"){
			beforeDelete(this);//before function
		}
		var selectIds=null;
		if($("#list2").length>0){//get grid list selected ids
			if(list2_gridSettings.multiselect==true){
				selectIds = $("#list2").jqGrid("getGridParam", "selarrrow");
				for (var i = 0; i < selectIds.length; i++){
					var rowData = $("#list2").jqGrid("getRowData", selectIds[i]);
					selectIds[i]=rowData.id;
				}
			}else{
				selectIds=$("#list2").jqGrid("getGridParam", "selrow");
				var rowData = $("#list2").jqGrid("getRowData", selectIds);
				selectIds=rowData.id;
			}			
		}else if($("#list").length>0){//get grid list selected ids
			if(list_gridSettings.multiselect==true){
				selectIds = $("#list").jqGrid("getGridParam", "selarrrow");
				for (var i = 0; i < selectIds.length; i++){
					var rowData = $("#list").jqGrid("getRowData", selectIds[i]);
					selectIds[i]=rowData.id;
				}
			}else{
				selectIds=$("#list").jqGrid("getGridParam", "selrow");
				var rowData = $("#list").jqGrid("getRowData", selectIds);
				selectIds=rowData.id;
			}
		}
		
		if(selectIds == null || selectIds == "") {
			$.alert({content:$.platform.messages.select.alertText});
			return;
		}
		
		var _this=this;
		$.conform({//conform action
		    content		:	$.platform.messages.del.conformText,
		    callback	: 	function () {
				//delete action post to url with ids
				$.post($(_this).attr("wurl")+"?id="+selectIds, {_method:"delete",currTime:(new Date()).getTime()}, function(data) {
					if (typeof(afterDelete) == "function"){
						afterDelete(data);//call back action
					}
					//alert delete result
					$.alert({content:data.message,type:$.platform.messages.messageType[data.messageType]});
					//refresh grid list
					if($("#list2").length>0){
						$("#list2").jqGrid('setGridParam').trigger("reloadGrid");
					}else if($("#list").length>0){
						$("#list").jqGrid('setGridParam').trigger("reloadGrid");
					}
			 	}); 
		    }
		});
	});
	
	//back button
	$("#backBtn").click(function(){
		//is need auto close
		if($("#actionForm").attr("autoclose")) {
			// try to refresh parent grid list
			if($.dialog.parent) {
				try {
					$.dialog.parent.$("#list").jqGrid('setGridParam').trigger("reloadGrid");
				}catch(e){}
				$.dialog.close();
				return;
			}
		}	
		
		if($("#actionForm")){//#actionForm attr forward
			if($("#actionForm").attr("forward")){
	    		document.location.href=$("#actionForm").attr("forward");
	    		return;
	    	}
		}
		
		if($("form")){//#actionForm attr forward
			if($("form").attr("forward")){
	    		document.location.href=$("form").attr("forward");
	    		return;
	    	}
		}
		
		if (typeof(beforeBack) == "function"){
			document.location.href=beforeBack();//call back
		}else {
			document.location.href=$(this).attr("wurl");//back #actionForm attr url 
		}
		
	});
	
	
	//submit button
	$("#submitBtn").click(function(){
		var canSubmit = true;
		if (typeof(beforeAction) == "function"){
			canSubmit = beforeAction();//before submit
		}
		if($("#actionForm").attr("validate") && $("#actionForm").attr("validate")=="false"){//是否通过验证和自定义验证，validate属性
			return false;
		}
		if(canSubmit) {
			$("#actionForm").submit();//submit
		}
	});
	
	//form submit form define
	if($("#actionForm")){//actionForm exist
		if($("#actionForm").attr("loadaction")){//init form
	    	$("#actionForm").json2form({url	:	$("#actionForm").attr("loadaction")});//init #actionForm with loadaction url
	    	$("#actionForm").removeAttr('loadaction'); //is need init
		}
	
		$("#actionForm").validate({//validate
			submitHandler	:	function(form) { 
				$.conform({
					content		:	$.platform.messages.submit.conformText,
					callback	:	function () {//validate success，form action callback
					$(form).ajaxSubmit({//form ajax submit
							dataType	:	'json',//json type
							success		:	function(data) { //success return 
								$.unloading();
								if (typeof(afterSubmit) == "function"){
									afterSubmit(data);//call back
									return;
								}
								
								var formErrorType=$("#actionForm").attr("type");//error alert type
								
								if(data.errors	&&	formErrorType){//have error field return 
									if(formErrorType=="alert"){//alert dialog
										var errorMessage=data.message+"<br>";
										for (var elem in data.errors){
											errorMessage+=data.errors[elem].message+"<br>";
										}
										$.alert({content:errorMessage,type:"error"});
									}else{//label tip
										for (var elem in data.errors){
											$("label[for='"+data.errors[elem].field+"']").html(data.errors[elem].message);
										}
										if(formErrorType!="label"){
											$("#"+formErrorType).show();
										}
									}
									return;
								} else {//no error,alert result message
									$.alert({content:data.message,type:$.platform.messages.messageType[data.messageType],
										callback:function(){
											if($("#actionForm").attr("autoclose")) {//auto close button
												if($("#backBtn").attr("id")){
													$("#backBtn").click();
												}else{
													$.closeWindow();
												}
												return;
											}				        		
											if($("#actionForm").attr("forward")){//auto forwar to actionForm forward attr
												document.location.href=$("#actionForm").attr("forward");
											}
										}
									});
								}
							},
							beforeSubmit:	function(arr, $form, options) { //before submit
								$.loading();//loading icon
								if (typeof(beforeSubmit) == "function"){
									return beforeSubmit();//callback 
								}
							},
							error		:	function(a, b, c) {//submit error
								$.unloading();
								$.alert({content:$.platform.messages.submit.errorText,type:"error"});
							}
						}); 
					}
				});
			}
		});	
	};

	//define grid
	$.platform.gridRefesh={};
	$.extend($.platform.gridRefesh, {
		gridRefesh:{
			mask		:	true,//is unmask
			grid		:	"list",//grid list id
			subGrid		:	null,//grid's sub id
			callback	:	null//callbak 
		}
	});
	
	//$.window open,refresh callback
	$.windowGridRefresh=function(){
		var settings=$.extend({
			mask		:	true,//is unmask
			grid		:	"list",//grid list id
			subGrid		:	null,//grid's sub id
			callback	:	null//callbak 
		},  $.platform.gridRefesh || {});
		settings.subGrid=null;
		$.gridRefresh(settings);
	};
	//$.window open,refresh sub callback
	$.windowSubGridRefresh=function(){
		var settings=$.extend({
			mask		:	true,//is unmask
			grid		:	"list",//grid list id
			subGrid		:	null,//grid's sub id
			callback	:	null//callbak 
		},  $.platform.gridRefesh || {});
		$.gridRefresh(settings);
	};
	
	 //grid refresh
	$.gridRefresh=function (settings){
		var settings=$.extend({
			mask		:	false,//mask
			grid		:	"list",//grid list id
			subGrid		:	null,//grid's sub id
			callback	:	null//callbak
		},  settings || {});
		
		if(settings.subGrid){//sub grid refresh
			$("#"+settings.subGrid+" .ui-icon-refresh").click();
			//$("#"+settings.subGrid).jqGrid('setGridParam').trigger("reloadGrid");
		}else{//refresh grid
			$("#"+settings.grid).jqGrid('setGridParam').trigger("reloadGrid");
		}
		
		if(settings.mask==true){//unmask
			$.unmask();
		}
		if(settings.callback){//callback
			settings.callback();
		}
	};
	
	$.gridRowData=function(listId,rowId){
		return $(listId).jqGrid("getRowData",rowId);
	};
	
	$.gridSel=function (listId){
		var selectIds=null;
		var gridIds="";
		if(typeof($(listId))=="object"){//get grid list selected ids
			//alert("typeof"+(typeof($(listId))=="object"));
			if($(listId).jqGrid("getGridParam", "multiselect")){
				selectIds = $(listId).jqGrid("getGridParam", "selarrrow");
			}else{
				selectIds=$(listId).jqGrid("getGridParam", "selrow");
			}
			gridIds=selectIds;
			if(gridIds ==	null ||	gridIds	==	""){
				$.alert({content:$.platform.messages.select.alertText});
				return null;
			}else{
				return gridIds;
			}
			
		}
	};
	
	$.gridSelIds=function (listId){
		var selectIds=null;
		var gridIds="";
		if(typeof($(listId))=="object"){//get grid list selected ids
			//alert("typeof"+(typeof($(listId))=="object"));
			if($(listId).jqGrid("getGridParam", "multiselect")){
				selectIds = $(listId).jqGrid("getGridParam", "selarrrow");
				for (var i = 0; i < selectIds.length; i++){
					var rowData = $(listId).jqGrid("getRowData", selectIds[i]);
					if(i==0){gridIds=rowData.id;}else{gridIds=gridIds+","+rowData.id;}
				}
			}else{
				selectIds=$(listId).jqGrid("getGridParam", "selrow");
				var rowData = $(listId).jqGrid("getRowData", selectIds);
				gridIds=rowData.id;
			}
			
			if(gridIds ==	null ||	gridIds	==	""){
				$.alert({content:$.platform.messages.select.alertText});
				return null;
			}else{
				return gridIds;
			}
			
		}
	};
	
	$.grid=function (gridSettings){
		var columnNameWidth="";
		if(gridSettings.resize==true){
			var cumulativeWidth=0; 
			var cumulativeVisible=0; 
			for (var i=0;i<gridSettings.colModel.length;i++){
				
				var col = gridSettings.colModel[i];
				if(col["hidden"]==false){
					cumulativeVisible++;
					var colWidth=0;
					if(cumulativeVisible<gridSettings.visibleColumnCount){
						colWidth=Math.round((col["width"]/gridSettings.visibleColumnWidth)*gridSettings.columnWidth);
						cumulativeWidth+=colWidth;
					}else{
						colWidth=gridSettings.columnWidth-cumulativeWidth;
					}
					gridSettings.colModel[i]["width"]=colWidth;	
					columnNameWidth+=col["name"]+":"+colWidth+"/";
				}
			}
		}else{
			gridSettings["width"]=gridSettings.visibleColumnWidth+"px";
		}
		
		var postData={};
		if(typeof($("#basic_search_form"))=="object"){
			postData=$("#basic_search_form").serializeObject();
		}
		
		var settings=$.extend({
			url			:	$("#"+gridSettings.element).attr("url"),//ajax post data url   
			datatype	: 	"json",//type json
			mtype		: 	'POST',//ajax method POST
			loadtext	:	"",//loading text
			//loadtext	:	$.platform.messages.grid.loadtext,
			height		: 	320,
			jsonReader	: 	{ repeatitems : false, id: "0" },
			pager		: 	jQuery("#"+gridSettings.element+"_pager"),//pager id
			rowNum		:	10,//rowNum 
			rowList		:	[10,50,100],
			width		: 	"990px",
			multiselect	: 	true && (typeof (gridSettings.subGirdSettings) == 'object' ? false : true),   //check box and multi select
			altRows		:	true,
			altclass	:	"jqgridclass",
			postData	:	postData,
			hoverrows	: 	true,
			subGrid		:	typeof (gridSettings.subGirdSettings) == 'object'||false,//is has subGirdSettings parameter
			rownumbers	: 	true,//row numbers display
			viewrecords	: 	true,
			onPaging    :   function(but){ 
				//$("#queryBtn").click();
				$("#"+gridSettings.element+"_pager").show();
				//alert($("#"+gridSettings.element).getGridParam("page")+"==="+but+"==="+$("#"+gridSettings.element).jqGrid("getGridParam", "page"));
				//$(".norecords").hide();
				//$("#"+gridSettings.element).jqGrid('setGridParam',{postData: $.extend($("#searchForm").serializeObject(),$("#adSearchForm").serializeObject())}).trigger("reloadGrid");
			},
			ondblClickRow	:	function(subgrid_id, row_id) {//double click event
		    	if(typeof (gridSettings.subGirdSettings) == 'object'){
		    		$("#"+gridSettings.element).toggleSubGridRow(subgrid_id);
		    		$.platform.gridRefesh.subGrid=subgrid_id;
		    	}
			},
			onSelectRow		:	function(id){//select row
				$.platform.gridRefesh.subGrid=gridSettings.element+"_"+id;
			},
			subGridRowExpanded: function(subgrid_id, row_id) {//Expanded current select row
				$("#"+gridSettings.element).setSelection(row_id);
				var subgrid_table_id, pager_id;
				$.platform.gridRefesh.subGrid=subgrid_id;
				subgrid_table_id = subgrid_id+"_t";
				pager_id = "p_"+subgrid_table_id;
				for (var col in gridSettings.subGirdSettings.colModel){//no index attr,name=index
					if(gridSettings.subGirdSettings.colModel[col]["index"]){
					}else{
						gridSettings.subGirdSettings.colModel[col]["index"]=gridSettings.subGirdSettings.colModel[col]["name"];
					}
				}
				var subGirdSettings=$.extend({  
					datatype	: 	"json",
					mtype		: 	'POST',
					loadtext	:	"",
					//loadtext	:	$.platform.messages.grid.loadtext,
					height		: 	160,
					jsonReader	: 	{ repeatitems : false, id: "0" },
					pager		: 	pager_id,
					rowNum		:	5,
					rowList		:	[5],
					width		: 	"98%",
					multiselect	: 	false,   //check box and multi select
					altRows		:	true,
					altclass	:	"jqgridclass",
					postData	:	$("#"+gridSettings.element).jqGrid('getRowData',row_id),
					hoverrows	: 	true,
					rownumbers	: 	true,
					viewrecords	: 	true,
					loadComplete: 	function(){
						var sub_records = $("#"+subgrid_table_id).getGridParam('records');
						if(sub_records == 0 || sub_records == null){
							if($(".subnorecords").html() == null){
								$("#"+subgrid_table_id).parent().append("<div class=\"subnorecords\" align=\"center\">"+$.platform.messages.grid.loadnodata+"</div>");
							}
							$("#"+pager_id).hide();
							$(".subnorecords").show();
						}else{
							$("#"+pager_id).show();
							$(".subnorecords").hide();
						}
						$("#"+subgrid_table_id).trigger("resize");
						if($.browser.version=="7.0"||$.browser.version=="8.0"){//msie 7.0/8.0
							$("#"+subgrid_table_id+" .ui-jqgrid-hdiv").width($("#"+subgrid_table_id+" .ui-jqgrid-hbox").width());
						}
	
					}
				}, gridSettings.subGirdSettings || {});

				$("#"+gridSettings.element).setGridHeight('auto'); //click '+'，set list height auto
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
				$("#"+subgrid_table_id).jqGrid(subGirdSettings).navGrid("#"+pager_id,{edit:false,add:false,del:false,search:false });
			},
			subGridRowColapsed: function(subgrid_id, row_id) {//close sub grid
				var list_records = $("#"+gridSettings.element).getGridParam('records');
				if(($("table[id*=list_]").length > 1 && list_records != null && list_records > 5)
						|| (list_records != null && list_records > 10 && $("#"+gridSettings.element).getGridParam('rowNum') > 10)
						|| $("table[id*=list_]").length > 2) {
					$("#"+gridSettings.element).setGridHeight('auto');
				} else {
					$("#"+gridSettings.element).setGridHeight('495'); //restore list height
				}
				$.platform.gridRefesh.subGrid=null;
				// this function is called before removing the data
				//var subgrid_table_id;
				//subgrid_table_id = subgrid_id+"_t";
				//jQuery("#"+subgrid_table_id).remove();
			},
			loadComplete: 	function(){//load Complete
				var re_records = $("#"+gridSettings.element).getGridParam('records');
				if(re_records != null && re_records > 0){
					$("#"+gridSettings.element).setGridHeight(""+(35*$("#"+gridSettings.element).getGridParam('rowNum')));
				}else{
					
				}

				$("#gbox_"+gridSettings.element).attr("gridWidth",gridSettings.visibleColumnWidth);
				$("#gbox_"+gridSettings.element).attr("columnNameWidth",columnNameWidth);

				if($("#list").height()>$(".ui-jqgrid .ui-jqgrid-bdiv").height()){
					$(".ui-jqgrid .ui-jqgrid-bdiv").height($("#list").height()+20);
				}
				
				$(".forward").on("click",function(){
					var settings={
							url		:	$(this).attr("url"),//current element url
							href	:	$(this).attr("href")//current element href
						};
					$.forward(settings);
				});
			}
		}, gridSettings || {});
	
		$("#"+gridSettings.element).jqGrid(settings).navGrid("#"+gridSettings.element+"_pager",{edit:false,add:false,del:false,search:false });
	};
	
	var curExpandNode = null;
	
	$.tree=function (treeSettings){
		
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
	    	);
		};//end tree
		
});//jquery end
