/*
 * switch_tab
 */
 //jquery begin
$(function(){
	$(".switch_tab_class").on("click",function(){
		if($(".switch_tab_current").attr("id")==(this.id)){
			return;
		}
		
		$(".switch_tab .switch_tab_class").removeClass("switch_tab_current");
		$(this).addClass("switch_tab_current");
		$(".switch_tab li").each(function(){
		    $("#"+$(this).attr("value")).hide();
		});
		
		$("#"+$(this).attr("value")).show();
		if (typeof(switchTab) == "function"){
			switchTab($(this).attr("value"));//user define after switch Tab
		}
	});
});//jquery end