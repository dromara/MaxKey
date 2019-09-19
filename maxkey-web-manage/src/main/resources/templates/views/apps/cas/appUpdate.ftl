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
});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/cas/update"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table   class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appUpdateCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
				 			<table width="960"  class="datatable" >
								<tbody>
								
								<tr>
									<th colspan=4><@locale code="apps.cas.info"/></th>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.cas.service"/>：</th>
									<td  colspan=3>
										<input type="text" id="service" name="service"  title="" value="${model.service}"/>
										<b class="orange">*</b><label for="service"></label>
									</td>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.cas.validation"/>：</th>
									<td  colspan=3>
										<input type="text" id="redirectUri" name="validation"  title="" value="${model.validation}"/>
										<b class="orange">*</b><label for="validation"></label>
									</td>
								</tr>
								</tbody>
							  </table>
			  </td>
				</tr>
				</tbody>
				</table>
			  
  	      
    		<input class="button" id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	  
</form>