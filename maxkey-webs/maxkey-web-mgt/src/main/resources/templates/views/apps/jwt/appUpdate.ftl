<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
	<#setting number_format="#">
</head>
<body>
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
<form id="actionForm_app"  method="post" type="label" autoclose="true"   closeWindow="true" 
			action="<@base/>/apps/jwt/update"  
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960"  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
				 			<table width="960"   class="table table-bordered" >
								<tbody>
								
								<tr>
									<td colspan=4><h5><@locale code="apps.jwt.info" /></h5></td>
								</tr>
								<tr>
									<th style="width:15%;"><@locale code="apps.jwt.redirectUri" /></th>
									<td  colspan=3>
										<input type="text" id="redirectUri" class="form-control" name="redirectUri"  title="" value="${model.redirectUri}"  required="" />
									</td>
								</tr>
								<tr>
									<th ><@locale code="apps.jwt.tokenType" /></th>
									<td >
										<select id="tokenType" name="tokenType"  class="form-control  form-select">
											<option value="POST" <#if 'POST'==model.tokenType>selected</#if> >安全令牌(TOKEN POST)</option>
											<option value="LTPA" <#if 'LTPA'==model.tokenType>selected</#if> >轻量级认证(LTPA COOKIE)</option>
										</select>
									</td>
									<th ><@locale code="apps.jwt.jwtName" /></th>
									<td >
										<input type="text" class="form-control" id="jwtName" name="jwtName"  title="" value="${model.jwtName!}"/>
										
									</td>
								</tr>
								<tr>
                                    <th ><@locale code="apps.jwt.subject" /></th>
                                    <td >
                                        <select id="subject" name="subject"  class="form-control  form-select">
                                            <option value="userId"   <#if 'userId'==model.subject>selected</#if> >
                                                <@locale code="userinfo.id"/></option>
                                            <option value="employeeNumber" <#if 'employeeNumber'==model.subject>selected</#if> >
                                                <@locale code="userinfo.employeeNumber"/></option>
                                            <option value="username" <#if 'username'==model.subject>selected</#if> >
                                                <@locale code="userinfo.username"/></option>
                                            <option value="email" <#if 'email'==model.subject>selected</#if> >
                                                <@locale code="userinfo.email"/></option>
                                             <option value="mobile" <#if 'mobile'==model.subject>selected</#if> >
                                                <@locale code="userinfo.mobile"/></option>
                                            <option value="windowsaccount" <#if 'windowsaccount'==model.subject>selected</#if> >
                                                <@locale code="userinfo.windowsAccount"/></option>
                                        </select>
                                    </td>
                                    <th ><@locale code="apps.jwt.issuer" /></th>
                                    <td >
                                        <input type="text" class="form-control" id="issuer" name="issuer"  title="" value="${model.issuer!}" required=""/>
                                    </td>
                            </tr>
                            <tr>
                                    <th ><@locale code="apps.jwt.audience" /></th>
                                    <td >
                                         <input type="text" class="form-control" id="audience" name="audience"  title="" value="${model.audience!}" required=""/>
                                    </td>
                                    <th ><@locale code="apps.jwt.expires" /></th>
                                    <td >
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="expires" name="expires"  title="" value="${model.expires!}"  required="" />
                                            <span class="input-group-text">Seconds</span>
                                        </div>
                                    </td>
                            </tr>
								
								<tr>
                                <th ><@locale code="apps.jwt.signature" /></th>
                                <td >
                                    <select id="signature" name="signature"   class="form-control  form-select">
                                        <option value="NONE"  <#if 'NONE'==model.signature> selected</#if>         >NONE</option>
                                        <option value="RS256" <#if 'RS256'==model.signature>selected</#if>        >RS256</option>
                                        <option value="RS384" <#if 'RS384'==model.signature>selected</#if>        >RS384</option>
                                        <option value="RS512" <#if 'RS512'==model.signature>selected</#if>        >RS512</option>
                                        <option value="HS256" <#if 'HS256'==model.signature>selected</#if>        >HS256</option>
                                        <option value="HS384" <#if 'HS384'==model.signature>selected</#if>        >HS384</option>
                                        <option value="HS512" <#if 'HS512'==model.signature>selected</#if>        >HS512</option>
                                    </select>
                                </td>
                                <th ></th>
                                <td >
                                    
                                </td>
                                </tr>
                                <tr>
                                    <th ><@locale code="apps.jwt.signatureKey" /></th>
                                    <td colspan ="3">
                                        <textarea class="form-control" id="signatureKey" name="signatureKey">${model.signatureKey!}</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <th style="width:15%;"><@locale code="apps.jwt.algorithm" /></th>
                                    <td style="width:35%;">
                                        <select id="algorithm" name="algorithm" class="form-control  form-select" >
                                            <option value="NONE"         <#if 'NONE'==model.algorithm>selected</#if> >NONE</option>
                                            <option value="RSA1_5"       <#if 'RSA1_5'==model.algorithm>selected</#if> >RSA1_5</option>
                                            <option value="RSA_OAEP"     <#if 'RSA_OAEP'==model.algorithm>selected</#if>>RSA_OAEP</option>
                                            <option value="RSA-OAEP-256" <#if 'RSA-OAEP-256'==model.algorithm>selected</#if>>RSA-OAEP-256</option>
                                            <option value="A128KW"       <#if 'A128KW'==model.algorithm>selected</#if>>A128KW</option>
                                            <option value="A192KW"       <#if 'A192KW'==model.algorithm>selected</#if>>A192KW</option>
                                            <option value="A256KW"       <#if 'A256KW'==model.algorithm>selected</#if>>A256KW</option>
                                            <option value="A128GCMKW"    <#if 'A128GCMKW'==model.algorithm>selected</#if>>A128GCMKW</option>
                                            <option value="A192GCMKW"    <#if 'A192GCMKW'==model.algorithm>selected</#if>>A192GCMKW</option>
                                            <option value="A256GCMKW"    <#if 'A256GCMKW'==model.algorithm>selected</#if>>A256GCMKW</option>
                                        </select>
                                    </td>
                                    <th width="140px"><@locale code="apps.jwt.encryptionMethod" /></th>
                                    <td width="340px">
                                        <select id="encryptionMethod" name="encryptionMethod"   class="form-control  form-select">
                                            <option value="A128GCM"        <#if 'A128GCM'==model.encryptionMethod>selected</#if>       >A128GCM</option>
                                            <option value="A192GCM"        <#if 'A192GCM'==model.encryptionMethod>selected</#if>       >A192GCM</option>
                                            <option value="A256GCM"        <#if 'A256GCM'==model.encryptionMethod>selected</#if>       >A256GCM</option>
                                            
                                            <option value="A128CBC-HS256"  <#if 'A128CBC-HS256'==model.encryptionMethod>selected</#if> >A128CBC-HS256</option>
                                            <option value="A192CBC-HS384"  <#if 'A192CBC-HS384'==model.encryptionMethod>selected</#if> >A192CBC-HS384</option>
                                            <option value="A256CBC-HS512"  <#if 'A256CBC-HS512'==model.encryptionMethod>selected</#if> >A256CBC-HS512</option>
                                            
                                            <option value="XC20P"          <#if 'XC20P'==model.encryptionMethod>selected</#if>         >XC20P</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th ><@locale code="apps.jwt.algorithmKey" /></th>
                                    <td colspan ="3">
                                        <textarea class="form-control" id="algorithmKey" name="algorithmKey">${model.algorithmKey!}</textarea>
                                    </td>
                                </tr>
								<tr>
									<th><@locale code="apps.jwt.content" /></th>
									<td >
										<#include  "../userPropertys.ftl"/>
									</td>
									<th></th>
									<td></td>
								</tr>
								
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