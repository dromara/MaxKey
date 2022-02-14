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
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
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
								<td colspan=4><h5><@locale code="apps.cas.info"/></h5></td>
							</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.cas.service"/></th>
								<td  colspan=3>
									<input type="text" id="service" class="form-control" name="service"  title="" value=""  required=""    />
								</td>
							</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.cas.callbackUrl"/></th>
								<td  colspan=3>
									<input type="text" class="form-control" id="callbackUrl" name="callbackUrl"  title="" value=""  required=""  />
								</td>
							</tr>
							<tr>
							    <th style="width:15%;"><@locale code="apps.cas.casUser" /></th>
                                <td style="width:35%;">
                                    <select id="casUser" name="casUser"  class="form-control  form-select">
                                        <option value="userId">
                                            <@locale code="userinfo.id"/></option>
                                        <option value="employeeNumber">
                                            <@locale code="userinfo.employeeNumber"/></option>
                                        <option value="username"  selected>
                                            <@locale code="userinfo.username"/></option>
                                        <option value="email">
                                            <@locale code="userinfo.email"/></option>
                                        <option value="mobile">
                                            <@locale code="userinfo.mobile"/></option>
                                        <option value="windowsaccount">
                                            <@locale code="userinfo.windowsAccount"/></option>
                                    </select>
                                </td>
								<th style="width:15%;"><@locale code="apps.cas.expires"/></th>
								<td style="width:35%;">
								    <div class="input-group">
									    <input type="text" class="form-control" id="expires" name="expires"  title="" value="180"  required=""  />
								        <span class="input-group-text">Seconds</span>
                                    </div>
								</td>
							</tr>
							
							<tr>
								<td  colspan=4>
									<input class="button"  id="status" type="hidden" name="status"  value="1"/>
    								<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
									<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.close" />"/>		  
								</td>
							</tr>
							</tbody>
						  </table>
					</td>
				</tr>
				</tbody>
				</table>
</form>
        </div>
    </div>
</div>
</body>
</html>