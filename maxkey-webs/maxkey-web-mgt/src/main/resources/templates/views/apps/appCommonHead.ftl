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
		$.post("<@base/>/apps/generate/secret/"+$(this).val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#algorithmKey").val(data+"");
			$("#algorithmKey_text").html(data+"");
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		});
	}); 
	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/"+$("#algorithm").val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#algorithmKey").val(data+"");
			$("#algorithmKey_text").html(data+"");
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
