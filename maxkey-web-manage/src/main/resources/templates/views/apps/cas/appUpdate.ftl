<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
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
});
//-->
</script>
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  closeWindow="true"
			action="<@base/>/apps/cas/update"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table   class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
				 			<table width="960"   class="table table-bordered"  >
								<tbody>
								<tr>
									<th colspan=4><@locale code="apps.cas.info"/></th>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.cas.service"/>：</th>
									<td  colspan=3>
										<input type="text" class="form-control" id="service" name="service"  title="" value="${model.service}"  required=""   />
									</td>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.cas.callbackUrl"/>：</th>
									<td  colspan=3>
										<input type="text" class="form-control" id="callbackUrl" name="callbackUrl"  title="" value="${model.callbackUrl}"  required="" />
							
									</td>
								</tr>
								<tr>
									<td  colspan=4>
										<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
										<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>		  
									</td>
								</tr>
								
								</tbody>
							  </table>
			  </td>
				</tr>
				</tbody>
				</table>
    		
</form>
</body>
</html>