<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
<script type="text/javascript">
<!--
$(function(){	
	$("#algorithm").change(function(){
	   if($(this).val()=="NONE"){
	       $("#algorithmKey").html("");
	   }else{
		  $.post("<@base/>/apps/generate/secret/"+$(this).val(), {id:$("#id").val(),_method:"post",currTime:(new Date()).getTime()}, function(data) {
		      $("#algorithmKey").html(data+"");
		  });
	   }
	}); 
	
	$("#signature").change(function(){
	   if($(this).val()=="NONE"){
           $("#signatureKey").html("");
       }else{
            $.post("<@base/>/apps/generate/secret/"+$(this).val(), {id:$("#id").val(),_method:"post",currTime:(new Date()).getTime()}, function(data) {
                $("#signatureKey").html(data+"");
            });
        }
    }); 
	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/random", {id:$("#id").val(),_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
	
	$("#iconFileImg").on("click",function(){
  		if(!$("#iconFileImg").hasClass("appended")){
  			$("#iconFileImg").after('<input  type="file" id="iconFile" name="iconFile"  title="" value=""/>');
  			$("#iconFileImg").addClass("appended");
  		}
  	});
});
//-->
</script>
