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
	$("#isAdapter").val("1");
	$("#adapter").val("org.maxkey.authz.cas.endpoint.adapter.CasDefaultAdapter");
});
//-->
</script>
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"    closeWindow="true"
			action="<@base/>/apps/cas/add"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
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
									<input type="text" id="service" class="form-control" name="service"  title="" value=""  required=""    />
								</td>
							</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.cas.callbackUrl"/>：</th>
								<td  colspan=3>
									<input type="text" class="form-control" id="callbackUrl" name="callbackUrl"  title="" value=""  required=""  />
								</td>
							</tr>
							<tr>
								<td  colspan=4>
									<input class="button"  id="status" type="hidden" name="status"  value="1"/>
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