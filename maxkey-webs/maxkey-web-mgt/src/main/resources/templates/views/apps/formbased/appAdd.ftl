<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>

<script type="text/javascript">
<!--
$(function(){		
	$("#credential").on("click",function(){
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
<form id="actionForm_app"  method="post" type="label" autoclose="true"   closeWindow="true" 
			action="<@base/>/apps/formbased/add"
			forward="<@base/>/apps/list"  
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
			<table  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table class="datatable" width="100%" >
				<tbody>
				
				<tr>
					<td colspan=4><h5><@locale code="apps.formbased.info"/></h5></td>
				</tr>
				<tr>
					<th><@locale code="apps.formbased.redirectUri"/></th>
					<td colspan="3">
						<input type="text"  class="form-control"  id="redirectUri" name="redirectUri"  title="" value=""  required="" />
					</td>
				</tr>
				<tr>
                    <th><@locale code="apps.formbased.authorizeView"/></th>
                    <td colspan =3>
                        <input type="text"  class="form-control"  id="authorizeView" name="authorizeView"  title="" value=""/>
                    </td>
                </tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.formbased.usernameMapping"/></th>
					<td style="width:35%;">
						<input type="text"  class="form-control"  id="usernameMapping" name="usernameMapping"  title="" value="username"  required="" />
					</td>
					<th style="width:15%;"><@locale code="apps.formbased.passwordMapping"/></th>
					<td style="width:35%;">
						<input type="text"  class="form-control" id="passwordMapping" name="passwordMapping"  title="" value="password"  required=""  />

					</td>
				</tr>
				
				<tr>
					<th width="140px"><@locale code="apps.credential"/></th>
					<td>
						<select id="credential" name="credential"  class="form-control  form-select" >
                            <option value="3"   selected >
                                <@locale code="apps.credential.user-defined"/>
                            </option>
                            <option value="2"   >
                                <@locale code="apps.credential.shared"/>
                            </option>
                            <option value="1"   >
                                <@locale code="apps.credential.system"/>
                            </option>
                        </select>   
					</td>
					<th><@locale code="apps.formbased.passwordAlgorithm"/></th>
                    <td>
                       <select id="passwordAlgorithm" name="passwordAlgorithm"  class="form-control  form-select" >
                            <option value="NONE"  selected      >NONE</option>
                            <option value="MD5"                 >MD5</option>
                            <option value="SHA"                 >SHA</option>
                            <option value="SHA-1"               >SHA-1</option>
                            <option value="SHA-256"             >SHA-256</option>
                            <option value="SHA-384"             >SHA-384</option>
                            <option value="SHA-512"             >SHA-512</option>
                            <option value="MD5-HEX"             >MD5-HEX</option>
                            <option value="SHA-HEX"             >SHA-HEX</option>
                            <option value="SHA-1-HEX"           >SHA-1-HEX</option>
                            <option value="SHA-256-HEX"         >SHA-256-HEX</option>
                            <option value="SHA-384-HEX"         >SHA-384-HEX</option>
                            <option value="SHA-512-HEX"         >SHA-512-HEX</option>
                        </select>
                    </td>
				</tr>
				
				<tr id="systemconfigure"  style="display:none">
					<th><@locale code="apps.systemUserAttr"/></th>
					<td colspan="3">
						<select id="systemUserAttr" name="systemUserAttr"  class="form-control  form-select" >
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
				</tr>
				<tr id="sharedconfigure"  style="display:none">
					<th><@locale code="apps.credential.sharedUsername"/></th>
					<td>
						<input type="text" id="sharedUsername"  class="form-control"  name="sharedUsername" value="" />

					</td>
					<th><@locale code="apps.credential.sharedPassword"/></th>
					<td>
						<input type="text" id="sharedPassword"  class="form-control"  name="sharedPassword" value="" />

					</td>
				</tr>
				</tbody>
				<tbody >
					<tr>
						<td colspan =4>
							<input  id="status" type="hidden" name="status"  value="1"/>
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