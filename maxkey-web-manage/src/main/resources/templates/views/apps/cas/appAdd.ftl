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
		$.post("<s:Base/>/apps/generate/secret/"+$(this).val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
			
		});
	}); 

});
//-->
</script>
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/cas/add"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960"  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
						<table width="960"  class="table table-bordered" >
						<tbody>
							<tr>
								<td colspan=4><@locale code="apps.cas.info"/></td>
							</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.cas.service"/>：</th>
								<td  colspan=3>
									<input type="text" id="service" class="form-control" name="service"  title="" value=""/>
									<b class="orange">*</b><label for="service"></label>
								</td>
							</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.cas.validation"/>：</th>
								<td  colspan=3>
									<input type="text" class="form-control" id="validation" name="validation"  title="" value=""/>
									<b class="orange">*</b><label for="validation"></label>
								</td>
							</tr>
							<tr>
								<td  colspan=4>
									<input class="button"  id="status" type="hidden" name="status"  value="1"/>
    								<input class="button"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
									<input class="button"  id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	  
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