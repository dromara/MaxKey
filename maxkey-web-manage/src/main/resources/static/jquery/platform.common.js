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
		if(config.target){
			window.open(config.url,"_blank")
			return;
		}
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
	
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
	// For actionForm use ajax submit
    var forms = $(".needs-validation");
    // Loop over them and prevent submission
    Array.prototype.filter.call(forms, function (form) {
      form.addEventListener('submit', function (event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }else{
        	if($("#actionForm")[0]){//ajaxSubmit
            	$("#actionForm").ajaxSubmit({//form ajax submit
    				dataType	:	'json',//json type
    				success		:	function(data) { //success return 
    					$.unloading();
    					
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
    					// refresh datagrid after Submit
    					if($("#datagrid")[0]){
    						$("#datagrid").bootstrapTable("refresh");
    					}
    					//self define afterSubmit
    					if (typeof(afterSubmit) == "function"){
    						afterSubmit(data);//call back
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
            	event.preventDefault();
                event.stopPropagation();
        	}
        }
        form.classList.add('was-validated');
      }, false);
    });
    
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
			
			var selectId="";
			if($("#datagrid").length>0){//get grid list selected ids
				var selRows = $('#datagrid').bootstrapTable('getSelections');
				for (var i=0;i<selRows.length; i++){
					selectId=selectId+","+selRows[i].id;
					break;
				}
				selectId=selectId.substring(1);
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
		var selectIds="";
		if($("#datagrid").length>0){//get grid list selected ids
			var selRows = $('#datagrid').bootstrapTable('getSelections');
			for (var i=0;i<selRows.length; i++){
				selectIds=selectIds+","+selRows[i].id;
			}
			selectIds=selectIds.substring(1);
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
					if($("#datagrid")[0]){
						$("#datagrid").bootstrapTable("refresh");
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
				$.dialog.close();
				return;
			}
		}			
		
		if($("form")){//#Form attr forward
			if($("form").attr("closeWindow")){
				window.close(); 
				return;
			}
			
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
	
	//form json url init all ways not used
	if($("#actionForm")){//actionForm exist
		if($("#actionForm").attr("loadaction")){//init form
	    	$("#actionForm").json2form({url	:	$("#actionForm").attr("loadaction")});//init #actionForm with loadaction url
	    	$("#actionForm").removeAttr('loadaction'); //is need init
		}
	};
	
	$.dataGridSelRowsData=function(dataGridElement){
		return $(dataGridElement).bootstrapTable('getSelections');
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