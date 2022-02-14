<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
</head>
<body>
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
<form id="actionForm_app"  method="post" type="label" autoclose="true"    closeWindow="true"
			action="<@base/>/apps/jwt/add"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960"  class="table table-bordered"  >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
						<table width="960"  class="table table-bordered"  >
						<tbody>
						<tr>
							<td colspan=4><h5><@locale code="apps.jwt.info" /></h5></td>
							</tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.jwt.redirectUri" /></th>
								<td style="width:35%;" colspan=3>
									<input type="text" class="form-control" id="redirectUri" name="redirectUri"  title="" value=""  required="" />
								</td>
							</tr>
							<tr>
									<th ><@locale code="apps.jwt.tokenType" /></th>
									<td >
										<select id="tokenType" name="tokenType"  class="form-control  form-select">
											<option value="POST">安全令牌(TOKEN POST)</option>
											<option value="LTPA">轻量级认证(LTPA COOKIE)</option>
										</select>
									</td>
									<th ><@locale code="apps.jwt.jwtName" /></th>
									<td >
										<input type="text" class="form-control" id="jwtName" name="jwtName"  title="" value="jwt"/>
									</td>
							</tr>
							<tr>
                                    <th ><@locale code="apps.jwt.subject" /></th>
                                    <td >
                                        <select id="subject" name="subject"  class="form-control  form-select">
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
                                    <th ><@locale code="apps.jwt.issuer" /></th>
                                    <td >
                                        <input type="text" class="form-control" id="issuer" name="issuer"  title="" value="" required=""/>
                                    </td>
                            </tr>
                            <tr>
                                    <th ><@locale code="apps.jwt.audience" /></th>
                                    <td >
                                         <input type="text" class="form-control" id="audience" name="audience"  title="" value="" required=""/>
                                    </td>
                                    <th ><@locale code="apps.jwt.expires" /></th>
                                    <td >
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="expires" name="expires"  title="" value="180"  required="" />
                                            <span class="input-group-text">Seconds</span>
                                        </div>
                                    </td>
                            </tr>
                            <tr>
                                <th style="width:15%;"><@locale code="apps.jwt.signature" /></th>
                                <td style="width:35%;">
                                    <select id="signature" name="signature"   class="form-control  form-select">
                                        <option value="NONE" selected >NONE</option>
                                        <option value="RS256"         >RS256</option>
                                        <option value="RS384"         >RS384</option>
                                        <option value="RS512"         >RS512</option>
                                        <option value="HS256"         >HS256</option>
                                        <option value="HS384"         >HS384</option>
                                        <option value="HS512"         >HS512</option>
                                    </select>
                                </td>
                                <th></th>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                    <th ><@locale code="apps.jwt.signatureKey" /></th>
                                    <td colspan ="3">
                                        <textarea class="form-control" id="signatureKey" name="signatureKey"></textarea>
                                    </td>
                            </tr>
							<tr>
								<th style="width:15%;"><@locale code="apps.jwt.algorithm" /></th>
								<td style="width:35%;">
									<select id="algorithm" name="algorithm"   class="form-control  form-select">
									    <option value="NONE" selected >NONE</option>
										<option value="RSA1_5"       >RSA1_5</option>
										<option value="RSA_OAEP"     >RSA_OAEP</option>
										<option value="RSA-OAEP-256" >RSA-OAEP-256</option>
										<option value="A128KW"       >A128KW</option>
										<option value="A192KW"       >A192KW</option>
										<option value="A256KW"       >A256KW</option>
										<option value="A128GCMKW"    >A128GCMKW</option>
										<option value="A192GCMKW"    >A192GCMKW</option>
										<option value="A256GCMKW"    >A256GCMKW</option>
									</select>
								</td>
								<th width="140px"><@locale code="apps.jwt.encryptionMethod" /></th>
								<td width="340px">
									<select id="encryptionMethod" name="encryptionMethod"   class="form-control  form-select">
                                        <option value="A128GCM"   selected      >A128GCM</option>
                                        <option value="A192GCM"                 >A192GCM</option>
                                        <option value="A256GCM"                 >A256GCM</option>
                                        
                                        <option value="A128CBC-HS256"           >A128CBC-HS256</option>
                                        <option value="A192CBC-HS384"           >A192CBC-HS384</option>
                                        <option value="A256CBC-HS512"           >A256CBC-HS512</option>
                                        
                                        <option value="XC20P"                   >XC20P</option>
                                    </select>
								</td>
							</tr>
							<tr>
                                    <th ><@locale code="apps.jwt.algorithmKey" /></th>
                                    <td colspan ="3">
                                        <textarea class="form-control" id="algorithmKey" name="algorithmKey"></textarea>
                                    </td>
                            </tr>
							<tr>
								<th><@locale code="apps.jwt.content" /></th>
								<td>
									<#include  "../userPropertys.ftl"/>
								</td>
								<th></th>
								<td>
									
								</td>
							</tr>
							<tr>
								<td colspan =4>
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