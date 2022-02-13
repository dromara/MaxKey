<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
	<#setting number_format="#">
<script type="text/javascript">
<!--
$(function(){	
	
	$("select[name='credential']").on("click",function(){
		if($(this).val()=="3"){
			$("#sharedconfigure").hide();
			$("#systemconfigure").hide();
		}else if($(this).val()=="1"){
			$("#sharedconfigure").hide();
			$("#systemconfigure").show();
		}else if($(this).val()=="2"){
			$("#sharedconfigure").show();
			$("#systemconfigure").hide();
		}
	});
});
//-->
</script>
</head>
<body>
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
<form id="actionForm_app"  method="post" type="label" autoclose="true"    closeWindow="true"
			action="<@base/>/apps/formbased/update"
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
			   <table  class="table table-bordered" >
				<tbody>
				
				<tr>
					<td colspan=4><h5><@locale code="apps.formbased.info"/></h5></td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.redirectUri"/></th>
					<td colspan="3">
						<input type="text" class="form-control" id="redirectUri" name="redirectUri"  title="" value="${model.redirectUri!}"  required=""   />
					</td>
					
				</tr> 
				<tr>
                    <th><@locale code="apps.formbased.authorizeView"/></th>
                    <td colspan =3>
                        <input type="text" class="form-control" id="authorizeView" name="authorizeView"  title="" value="${model.authorizeView!}"/>
                    </td>
                </tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.formbased.usernameMapping"/></th>
					<td style="width:35%;">
						<input type="text" class="form-control" id="usernameMapping" name="usernameMapping"  title="" value="${model.usernameMapping}"  required=""   />
					</td>
					<th style="width:15%;"><@locale code="apps.formbased.passwordMapping"/></th>
					<td style="width:35%;">
						<input type="text" class="form-control" id="passwordMapping" name="passwordMapping"  title="" value="${model.passwordMapping}"  required=""   />
	
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.credential"/></th>
					<td >
						<select id="credential" name="credential"  class="form-control  form-select" >
							<option value="3"   <#if  3==model.credential >selected</#if> >
								<@locale code="apps.credential.user-defined"/>
							</option>
							<option value="2"   <#if  2==model.credential >selected</#if> >
								<@locale code="apps.credential.shared"/>
							</option>
							<option value="1"   <#if  1==model.credential >selected</#if> >
								<@locale code="apps.credential.system"/>
							</option>
						</select>	
						
					</td>
					<th><@locale code="apps.formbased.passwordAlgorithm"/></th>
					<td>
					   <select id="passwordAlgorithm" name="passwordAlgorithm"  class="form-control  form-select" >
					        <option value="NONE"     <#if  'NONE'==model.passwordAlgorithm >selected</#if> >NONE</option>
                            <option value="MD5"      <#if  'MD5'==model.passwordAlgorithm >selected</#if> >MD5</option>
                            <option value="SHA"      <#if  'SHA'==model.passwordAlgorithm >selected</#if> >SHA</option>
                            <option value="SHA-1"    <#if  'SHA-1'==model.passwordAlgorithm >selected</#if>   >SHA-1</option>
                            <option value="SHA-256"   <#if 'SHA-256'==model.passwordAlgorithm >selected</#if> >SHA-256</option>
                            <option value="SHA-384"   <#if 'SHA-384'==model.passwordAlgorithm >selected</#if> >SHA-384</option>
                            <option value="SHA-512"   <#if 'SHA-512'==model.passwordAlgorithm >selected</#if> >SHA-512</option>
                            <option value="MD5-HEX"      <#if  'MD5-HEX'==model.passwordAlgorithm >selected</#if> >MD5-HEX</option>
                            <option value="SHA-HEX"      <#if  'SHA-HEX'==model.passwordAlgorithm >selected</#if> >SHA-HEX</option>
                            <option value="SHA-1-HEX"    <#if  'SHA-1-HEX'==model.passwordAlgorithm >selected</#if>  >SHA-1-HEX</option>
                            <option value="SHA-256-HEX"  <#if 'SHA-256-HEX'==model.passwordAlgorithm >selected</#if> >SHA-256-HEX</option>
                            <option value="SHA-384-HEX"  <#if 'SHA-384-HEX'==model.passwordAlgorithm >selected</#if> >SHA-384-HEX</option>
                            <option value="SHA-512-HEX"  <#if 'SHA-512'==model.passwordAlgorithm >selected</#if> >SHA-512-HEX</option>
                        </select>
					</td>
				</tr>
				
				<tr id="systemconfigure"  <#if 1!=model.credential> style="display:none"</#if> >
					<th><@locale code="apps.systemUserAttr"/></th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr" class="form-control  form-select">
							<option value="userId"   <#if 'userId'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.id"/></option>
							<option value="employeeNumber" <#if 'employeeNumber'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.employeeNumber"/></option>
							<option value="username" <#if 'username'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.username"/></option>
							<option value="email" <#if 'email'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.email"/></option>
							<option value="mobile" <#if 'mobile'==model.systemUserAttr>selected</#if> >
                                <@locale code="userinfo.mobile"/></option>
							<option value="windowsaccount" <#if 'windowsaccount'==model.systemUserAttr>selected</#if> >
								<@locale code="userinfo.windowsAccount"/></option>
						</select>
					</td>
				</tr>
				<tr id="sharedconfigure"  <#if 2!=model.credential> style="display:none"</#if>>
					<th><@locale code="apps.credential.sharedUsername"/></th>
					<td>
						<input type="text" class="form-control" id="sharedUsername" name="sharedUsername" value="${model.sharedUsername}" />
			
					</td>
					<th><@locale code="apps.credential.sharedPassword"/></th>
					<td>
						<input type="text"  class="form-control" id="sharedPassword" name="sharedPassword" value="${model.sharedPassword}" />

					</td>
				</tr>
				<tbody >
					<tr>
						<td colspan =4>
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