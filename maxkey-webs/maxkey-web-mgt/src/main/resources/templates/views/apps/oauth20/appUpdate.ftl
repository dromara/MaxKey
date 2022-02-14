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
			action="<@base/>/apps/oauth20/update"
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960px"   class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960px"   class="table table-bordered" >
				<tbody>
				
				<tr>
					<td colspan=4><h5><@locale code="apps.oauth.v2.0.info" /></h5></td>
				</tr>
				<tr class="d-none">
					<th style="width:15%;"><@locale code="apps.oauth.v2.0.clientId" /></th>
					<td style="width:35%;">
						<span id="clientId_text">${model.clientId}</span>
						<input type="hidden" class="form-control" id="clientId" name="clientId"  title="" value="${model.clientId}"/>
						
					</td>
					<th style="width:15%;"><@locale code="apps.oauth.v2.0.clientSecret" /></th>
					<td style="width:35%;">
						<span id="clientSecret_text">${model.clientSecret}</span>
						<input type="hidden" class="form-control" id="clientSecret" name="clientSecret"  title="" value="${model.clientSecret}"/>
						
					</td>
				</tr>
				<tr>
				    <th ><@locale code="apps.oauth.subject" /></th>
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
					<th  style="width:15%;"><@locale code="apps.oauth.scope" /></th>
					<td  >
						<table  class="hidetable"  style="width:100%;">
							<tr>
								<td>read<input class="form-check-input" type="checkbox" id="scope_trust" name="scope" value="read"  <#if   model.scope?contains("read") >checked</#if> /></td>
								<td>write<input class="form-check-input"  type="checkbox" id="scope_write" name="scope" value="write" <#if   model.scope?contains('write') >checked</#if>/></td>
								<td>trust<input class="form-check-input"  type="checkbox" id="scope_trust" name="scope" value="trust" <#if   model.scope?contains('trust') >checked</#if>/></td>
								<td>openid<input class="form-check-input"  type="checkbox" id="scope_openid" name="scope" value="openid" <#if   model.scope?contains('openid') >checked</#if>/></td>
								<td>profile<input class="form-check-input"  type="checkbox" id="scope_profile" name="scope" value="profile" <#if   model.scope?contains('profile') >checked</#if>/></td>
								<td>email<input class="form-check-input"  type="checkbox" id="scope_email" name="scope" value="email" <#if   model.scope?contains('email') >checked</#if>/></td>
								<td>phone<input class="form-check-input"  type="checkbox" id="scope_phone" name="scope" value="phone" <#if   model.scope?contains('phone') >checked</#if>/></td>
								<td>address<input class="form-check-input"  type="checkbox" id="scope_address" name="scope" value="address" <#if   model.scope?contains('address') >checked</#if>/></td>
								<td>all<input class="form-check-input"  type="checkbox" id="scope_all" name="scope" value="all" <#if   model.scope?contains('all') >checked</#if>/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.GrantTypes" /></th>
					<td colspan="3">
						<table  class="hidetable"  style="width:100%;">
							<tr>
								<td>authorization_code      <input  class="form-check-input"  <#if   model.authorizedGrantTypes?contains('authorization_code') >checked</#if>  type="checkbox" id="grantTypes_authorization_code" name="authorizedGrantTypes" value="authorization_code"/></td>
								<td>password                <input  class="form-check-input"  <#if   model.authorizedGrantTypes?contains('password') >checked</#if>  type="checkbox" id="grantTypes_trust" name="authorizedGrantTypes" value="password"/></td>
								<td>implicit                <input  class="form-check-input"  <#if   model.authorizedGrantTypes?contains('implicit') >checked</#if>  type="checkbox" id="grantTypes_implicit" name="authorizedGrantTypes" value="implicit"/></td>
								<td>client_credentials      <input  class="form-check-input"  <#if   model.authorizedGrantTypes?contains('client_credentials') >checked</#if>  type="checkbox" id="grantTypes_client_credentials" name="authorizedGrantTypes" value="client_credentials"/></td>
								<td>refresh_token           <input  class="form-check-input"  <#if   model.authorizedGrantTypes?contains('refresh_token') >checked</#if>  type="checkbox" id="grantTypes_refresh_token" name="authorizedGrantTypes" value="refresh_token"/></td>
								<td>id_token                <input  class="form-check-input"  <#if   model.authorizedGrantTypes?contains('id_token') >checked</#if>  type="checkbox" id="grantTypes_id_token" name="authorizedGrantTypes" value="id_token"/></td>
								<td>token                   <input  class="form-check-input"  <#if   model.authorizedGrantTypes?contains( 'token') >checked</#if>  type="checkbox" id="grantTypes_token" name="authorizedGrantTypes" value="token"/></td>
								
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.registeredRedirectUris" /></th>
					<td colspan=3>
						<textarea  class="form-control" id="registeredRedirectUris" name="registeredRedirectUris" rows="4" cols="60"  required="" >${model.registeredRedirectUris}</textarea>

					</td>
				</tr>
				
				<tr>
                    <th  style="width:15%;">PKCE</th>
                    <td  style="width:35%;">
                        <select  id="pkce" name="pkce" class="form-control  form-select">
                            <option value="yes"  <#if   "yes"==model.pkce?default("yes") >selected</#if>>
                                <@locale code="common.text.yes" /></option>
                            <option value="no"  <#if   'no'==model.pkce >selected</#if>>
                                <@locale code="common.text.no" /></option>
                        </select>
                    </td>
                    <th  style="width:15%;"><@locale code="apps.oauth.approvalPrompt" /></th>
                    <td  style="width:35%;">
                        <select  id="approvalPrompt" name="approvalPrompt" class="form-control  form-select">
                            <option value="force"  <#if   ""==model.approvalPrompt?default("") >selected</#if>>
                                <@locale code="apps.oauth.approvalPrompt.force" /></option>
                            <option value="auto"  <#if   'auto'==model.approvalPrompt >selected</#if>>
                                <@locale code="apps.oauth.approvalPrompt.auto" /></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><@locale code="apps.oauth.accessTokenValiditySeconds" /></th>
                    <td >
                        <div class="input-group">
                          <input type="text" class="form-control" id="accessTokenValiditySeconds" name="accessTokenValiditySeconds"  title="" value="${model.accessTokenValiditySeconds}"/>
                          <span class="input-group-text">Seconds</span>
                        </div>
                    </td>
                    <th><@locale code="apps.oauth.refreshTokenValiditySeconds" /></th>
                    <td>
                       <div class="input-group">
                          <input type="text" class="form-control" id="refreshTokenValiditySeconds" name="refreshTokenValiditySeconds"  title="" value="${model.refreshTokenValiditySeconds}"/>
                           <span class="input-group-text">Seconds</span>
                       </div>
                    </td>
                </tr>
                <tr>
                    <td colspan=4><h5><@locale code="apps.oauth.connect.info" /></h5></td>
                </tr>
                <tr>
                    <th ><@locale code="apps.oauth.connect.issuer" /></th>
                    <td >
                        <input type="text" class="form-control" id="issuer" name="issuer"  title="" value="${model.issuer!}" />
                    </td>
                    <th ><@locale code="apps.oauth.connect.audience" /></th>
                    <td >
                         <input type="text" class="form-control" id="audience" name="audience"  title="" value="${model.audience!}" />
                    </td>
                </tr>
                <tr>
                    <th ><@locale code="apps.oauth.connect.signature" /></th>
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
                    <th><@locale code="apps.oauth.connect.userInfoResponse" /></th>
                    <td>
                        <select id="userInfoResponse" name="userInfoResponse"   class="form-control  form-select">
                            <option value="NORMAL"              <#if 'NORMAL'==model.userInfoResponse> selected</#if>      >NORMAL</option>
                            <option value="SIGNING"             <#if 'SIGNING'==model.userInfoResponse> selected</#if>    >SIGNING</option>
                            <option value="ENCRYPTION"          <#if 'ENCRYPTION'==model.userInfoResponse> selected</#if>   >ENCRYPTION</option>
                            <option value="SIGNING_ENCRYPTION"  <#if 'SIGNING_ENCRYPTION'==model.userInfoResponse> selected</#if>  >SIGNING_ENCRYPTION</option>
                        </select>
                    </td>
                </tr>
				<tr>
                    <th ><@locale code="apps.oauth.connect.signatureKey" /></th>
                    <td colspan ="3">
                        <textarea class="form-control" id="signatureKey" name="signatureKey">${model.signatureKey!}</textarea>
                    </td>
                </tr>
                <tr>
                    <th style="width:15%;"><@locale code="apps.oauth.connect.algorithm" /></th>
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
                    <th width="140px"><@locale code="apps.oauth.connect.encryptionMethod" /></th>
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
                    <th ><@locale code="apps.oauth.connect.algorithmKey" /></th>
                    <td colspan ="3">
                        <textarea class="form-control" id="algorithmKey" name="algorithmKey">${model.algorithmKey!}</textarea>
                    </td>
                </tr>
				
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>
    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.close" />"/>		  
</form>
        </div>
    </div>
</div>
</body>
</html>