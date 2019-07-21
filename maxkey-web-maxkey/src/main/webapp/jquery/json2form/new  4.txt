/*	jQuery json2form Plugin 
 *	version: 1.0 (2011-3-01)
 *
 * 	Copyright (c) 2011, Crystal, shimingxy@163.com
 * 	Dual licensed under the MIT and GPL licenses:
 * 	http://www.opensource.org/licenses/mit-license.php
 * 	http://www.gnu.org/licenses/gpl.html
 * 	Date: 2011-3-01 rev 1
 */
 ;(function ($) {
$.json2form = $.json2form||{};
$.fn.json2form = function(config ) { 

	var config=$.extend({
			url	    : null,//remote url for ajax data
			elem    : this.attr("id"),//id
			type    : "POST",//remote data method type ,GET or POST default is POST
		}, config || {});
	if(config.url){
		$.ajax({type: config.type,url: config.url,dataType: "json",async: false,
			success: function(data){
				config.data=data;
			}
		});
	}
	alert();
	if(config.init){
		//init
		for (var elem in config.init){
		   if(typeof(elem) != 'function'){
				var arrayObject=config.init[elem];
				if($("#"+config.elem+" input[name='"+elem+"']")){
					var elemtype=$("#"+config.elem+" input[name='"+elem+"']").attr("type");
					var elemName=$("#"+config.elem+" input[name='"+elem+"']").attr("name");
					var initElem=$("#"+config.elem+" input[name='"+elem+"']");
					switch(elemtype){
						case "checkbox":
							for (var initelem in arrayObject){
								initElem.after('<input type="checkbox"  name="'+elemName+'" value="'+arrayObject[initelem].value+'" />'+arrayObject[initelem].display);
							}
							initElem.remove();
							break;
						case "radio":
							for (var initelem in arrayObject){
								initElem.after('<input type="radio"  name="'+elemName+'" value="'+arrayObject[initelem].value+'" />'+arrayObject[initelem].display);
							}
							initElem.remove();
							break;
					}
				} 
				if($("#"+config.elem+" select[name='"+elem+"']")){
					for (var initelem in arrayObject){
						$("#"+config.elem+" select[name='"+elem+"']").append("<option value='"+arrayObject[initelem].value+"'>"+arrayObject[initelem].display+"</option>");
					}
				}
			}
		}
	}
	
	if(config.data){
		//input text password hidden button reset submit checkbox radio
		$("#"+config.elem+" input").each(function(){
			
			var elemtype=$(this).attr("type");
			
			if($(this).attr("action")){
				var elemName=$(this).attr("name");
				switch(elemtype){
					case "checkbox":
						var checkbox =this;
						$.ajax({type: "POST",url: $(this).attr("action"),dataType: "json",async: false,success: function(data){
							for (var elem in data){
		  						$(checkbox).after('<input type="checkbox"  name="'+elemName+'" value="'+data[elem].value+'" />'+data[elem].display);
		  					}
							$(checkbox).remove();
							}
						});
						break;
					case "radio":
						var radio =this;
						$.ajax({type: "POST",url: $(this).attr("action"),dataType: "json",async: false,success: function(data){
							for (var elem in data){
		  						$(radio).after('<input type="radio"  name="'+elemName+'" value="'+data[elem].value+'" />'+data[elem].display);
		  					}
							$(radio).remove();
							}
						});
						break;
				}
			}
			
			switch(elemtype){
				case "text":
				case "password":
				case "hidden":
				case "button":
				case "reset":
				case "submit":{
					for (var elem in config.data){
					   if(typeof(elem) != 'function'){
					   		if(($(this).attr("name"))==elem){
					   			$(this).val(config.data[elem]);
					   		}
						}
					}
					break;
				}
				case "checkbox":
				case "radio":{
					for (var elem in config.data){
					   if(typeof(elem) != 'function'){
					   		if(($(this).attr("name"))==elem&&($(this).val())==$(this).val()){
					   			$(this).attr("checked",true);
					   		}
						}
					}
					break;
				}
			}
		});
		//select
		$("#"+config.elem+" select").each(function(){
			var select =this;
			if($(this).attr("action")){
				$.ajax({type: "POST",url: $(this).attr("action"),dataType: "json",async: false,success: function(data){
					for (var elem in data){
  						$(select).append("<option value='"+data[elem].value+"'>"+data[elem].display+"</option>");
  					}
					}
				});
			}
			
			for (var elem in config.data){
			   if(typeof(elem) != 'function'){
			   		if(($(this).attr("name"))==elem){
			   			$(this).val(config.data[elem]);
			   		}
				}
			}
			
		});
		
		//textarea
		$("#"+config.elem+" textarea").each(function(){
			for (var elem in config.data){
			   if(typeof(elem) != 'function'){
			   		if(($(this).attr("name"))==elem){
			   			$(this).val(config.data[elem]);
			   		}
				}
			}
		});
	}
	
	if(config.label){
		//label
		$("#"+config.elem+" label").each(function(){
			for (var elem in config.label){
			   if(typeof(elem) != 'function'){
			   		if(($(this).attr("for"))==elem){
			   			$(this).html(config.label[elem]);
			   		}
				}
			}
		});
	}
};
})(jQuery);