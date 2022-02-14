<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#setting number_format="#">
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>

<script type="text/javascript">
<!--
$(function(){	
	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/secret", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
});
//-->
</script>
</head>
<body>
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
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
									<th colspan=4><h5><@locale code="apps.cas.info"/></h5></th>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.cas.service"/></th>
									<td  colspan=3>
										<input type="text" class="form-control" id="service" name="service"  title="" value="${model.service}"  required=""   />
									</td>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.cas.callbackUrl"/></th>
									<td  colspan=3>
										<input type="text" class="form-control" id="callbackUrl" name="callbackUrl"  title="" value="${model.callbackUrl}"  required="" />
							
									</td>
								</tr>
								<tr>
								    <th style="width:15%;"><@locale code="apps.cas.casUser" /></th>
                                    <td  style="width:35%;">
                                        <select id="casUser" name="casUser"  class="form-control  form-select">
                                            <option value="userId"   <#if 'userId'==model.casUser>selected</#if> >
                                                <@locale code="userinfo.id"/></option>
                                            <option value="employeeNumber" <#if 'employeeNumber'==model.casUser>selected</#if> >
                                                <@locale code="userinfo.employeeNumber"/></option>
                                            <option value="username" <#if 'username'==model.casUser>selected</#if> >
                                                <@locale code="userinfo.username"/></option>
                                            <option value="email" <#if 'email'==model.casUser>selected</#if> >
                                                <@locale code="userinfo.email"/></option>
                                            <option value="mobile" <#if 'mobile'==model.casUser>selected</#if> >
                                                <@locale code="userinfo.mobile"/></option>
                                            <option value="windowsaccount" <#if 'windowsaccount'==model.casUser>selected</#if> >
                                                <@locale code="userinfo.windowsAccount"/></option>
                                        </select>
                                    </td>
									<th style="width:15%;"><@locale code="apps.cas.expires"/></th>
									<td style="width:35%;">
									   <div class="input-group">
									       <input type="text" class="form-control" id="expires" name="expires"  title="" value="${model.expires}"  required="30"  />
									       <span class="input-group-text">Seconds</span>
									   </div>
									</td>
								</tr>
								<tr>
									<td  colspan=4>
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