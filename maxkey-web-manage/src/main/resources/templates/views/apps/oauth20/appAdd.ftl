<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"    closeWindow="true"
			action="<@base/>/apps/oauth20/add"
			forward="<@base/>/apps/list"  
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960"   class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"   class="table table-bordered" >
				<tbody>
				
				<tr>
					<td colspan=4><@locale code="apps.oauth.v2.0.info" /></td>
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.oauth.v2.0.clientId" />：</th>
					<td style="width:35%;">${model.clientId}
						<input type="hidden" class="form-control" id="clientId" name="clientId"  title="" value="${model.clientId}"/>
						
					</td>
					<th style="width:15%;"><@locale code="apps.oauth.v2.0.clientSecret" />：</th>
					<td style="width:35%;">
						<span id="clientSecret_text">${model.clientSecret}</span>
						<input type="hidden" class="form-control" id="clientSecret" name="clientSecret"  title="" value="${model.clientSecret}"/>
						
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.scope" />：</th>
					<td  colspan="3">
						<table class="hidetable"  style="width:100%;">
							<tr>
								<td>read<input type="checkbox" id="scope_trust" name="scope" value="read"  checked /></td>
								<td>write<input type="checkbox" id="scope_write" name="scope" value="write"/></td>
								<td>trust<input type="checkbox" id="scope_trust" name="scope" value="trust"/></td>
								<td>openid<input type="checkbox" id="scope_openid" name="scope" value="openid"/></td>
								<td>profile<input type="checkbox" id="scope_profile" name="scope" value="profile"/></td>
								<td>email<input type="checkbox" id="scope_email" name="scope" value="email"/></td>
								<td>phone<input type="checkbox" id="scope_phone" name="scope" value="phone"/></td>
								<td>address<input type="checkbox" id="scope_address" name="scope" value="address"/></td>
								<td>all<input type="checkbox" id="scope_all" name="scope" value="all"/></td>
								<td><b class="orange">*</b><label for="scope"></label></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.GrantTypes" />：</th>
					<td  colspan="3">
						<table  class="hidetable"  style="width:100%;">
							<tr>
								<td>authorization_code | code<input type="checkbox" id="grantTypes_authorization_code" name="authorizedGrantTypes" value="authorization_code"  checked /></td>
								<td>password<input type="checkbox" id="grantTypes_trust" name="authorizedGrantTypes" value="password"/></td>
								<td>implicit<input type="checkbox" id="grantTypes_implicit" name="authorizedGrantTypes" value="implicit"/></td>
								<td>refresh_token<input type="checkbox" id="grantTypes_refresh_token" name="authorizedGrantTypes" value="refresh_token"/></td>
								<td>id_token<input  type="checkbox" id="grantTypes_id_token" name="authorizedGrantTypes" value="id_token"/></td>
								<td>token<input type="checkbox" id="grantTypes_token" name="authorizedGrantTypes" value="token"/></td>
								<td><b class="orange">*</b><label for="authorizedGrantTypes"></label></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.registeredRedirectUris" />：</th>
					<td colspan=3>
						<textarea  class="form-control"  id="registeredRedirectUris" name="registeredRedirectUris" rows="4" cols="60"></textarea>
						<b class="orange">*</b><label for="registeredRedirectUris"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.accessTokenValiditySeconds" />：</th>
					<td >
						<input type="text"  class="form-control" id="accessTokenValiditySeconds" name="accessTokenValiditySeconds"  title="" value="180"/>
						<b class="orange">*</b><label for="accessTokenValiditySeconds"></label>
					</td>
					<th><@locale code="apps.oauth.refreshTokenValiditySeconds" />：</th>
					<td>
						<input type="text" class="form-control"  id="refreshTokenValiditySeconds" name="refreshTokenValiditySeconds"  title="" value="180"/>
						<b class="orange">*</b><label for="refreshTokenValiditySeconds"></label>
					</td>
				</tr>
				<tr>
					<td colspan=4><@locale code="apps.oauth.connect.info" /></td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.connect.idTokenSigningAlgorithm" />：</th>
					<td >
						<select  id="idTokenSigningAlgorithm" name="idTokenSigningAlgorithm"   class="form-control" >
							<option value="none"    selected>No digital signature</option>
							<option value="HS256"  >HMAC using SHA-256 hash algorithm</option>
							<option value="HS384"  >HMAC using SHA-384 hash algorithm</option>
							<option value="HS512"  >HMAC using SHA-512 hash algorithm</option>
							<option value="RS256"  >RSASSA using SHA-256 hash algorithm</option>
							<option value="RS384"  >RSASSA using SHA-384 hash algorithm</option>
							<option value="RS512"  >RSASSA using SHA-512 hash algorithm</option>
							<option value="ES256"  >ECDSA using P-256 curve and SHA-256 hash algorithm</option>
							<option value="ES384"  >ECDSA using P-384 curve and SHA-384 hash algorithm</option>
							<option value="ES512"  >ECDSA using P-512 curve and SHA-512 hash algorithm</option>
						</select>
					</td>
					<th><@locale code="apps.oauth.connect.userInfoSigningAlgorithm" />：</th>
					<td >
						<select  id="userInfoSigningAlgorithm" name="userInfoSigningAlgorithm"  class="form-control" >
							<option value="none"    selected>No digital signature</option>
							<option value="HS256"  >HMAC using SHA-256 hash algorithm</option>
							<option value="HS384"  >HMAC using SHA-384 hash algorithm</option>
							<option value="HS512"  >HMAC using SHA-512 hash algorithm</option>
							<option value="RS256"  >RSASSA using SHA-256 hash algorithm</option>
							<option value="RS384"  >RSASSA using SHA-384 hash algorithm</option>
							<option value="RS512"  >RSASSA using SHA-512 hash algorithm</option>
							<option value="ES256"  >ECDSA using P-256 curve and SHA-256 hash algorithm</option>
							<option value="ES384"  >ECDSA using P-384 curve and SHA-384 hash algorithm</option>
							<option value="ES512"  >ECDSA using P-512 curve and SHA-512 hash algorithm</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.connect.jwksUri" />：</th>
					<td colspan =3>
						<input type="text" class="form-control"  id="jwksUri" name="jwksUri"  title="" value="${model.jwksUri!}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.connect.idTokenEncryptedAlgorithm" />：</th>
					<td >
						<select  id="idTokenEncryptedAlgorithm" name="idTokenEncryptedAlgorithm"  class="form-control" >
							<option value="none" selected >No encryption</option>
							<option value="RSA1_5" >RSAES-PKCS1-V1_5</option>
							<option value="RSA-OAEP" >RSAES using Optimal Asymmetric Encryption Padding (OAEP)</option>
							<option value="A128KW">AES Key Wrap Algorithm using 128 bit keys </option>
							<option value="A256KW">AES Key Wrap Algorithm using 256 bit keys</option>
							<option value="dir" >Direct use of a shared symmetric key as the Content Master Key (CMK) for the block encryption step</option>
							<option value="ECDH-ES">Elliptic Curve Diffie-Hellman Ephemeral Static key agreement using the Concat KDF, with the agreed-upon key being used directly as the Content Master Key (CMK)</option>
							<option value="ECDH-ES+A128KW">Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A128KW function</option>
							<option value="ECDH-ES+A256KW">Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A256KW function</option>
						
						</select>
					</td>
					<th><@locale code="apps.oauth.connect.userInfoEncryptedAlgorithm" />：</th>
					<td >
						<select  id="userInfoEncryptedAlgorithm" name="userInfoEncryptedAlgorithm" class="form-control"  >
							<option value="none" selected >No encryption</option>
							<option value="RSA1_5" >RSAES-PKCS1-V1_5</option>
							<option value="RSA-OAEP" >RSAES using Optimal Asymmetric Encryption Padding (OAEP)</option>
							<option value="A128KW">AES Key Wrap Algorithm using 128 bit keys </option>
							<option value="A256KW">AES Key Wrap Algorithm using 256 bit keys</option>
							<option value="dir" >Direct use of a shared symmetric key as the Content Master Key (CMK) for the block encryption step</option>
							<option value="ECDH-ES">Elliptic Curve Diffie-Hellman Ephemeral Static key agreement using the Concat KDF, with the agreed-upon key being used directly as the Content Master Key (CMK)</option>
							<option value="ECDH-ES+A128KW">Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A128KW function</option>
							<option value="ECDH-ES+A256KW">Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A256KW function</option>
						
						</select>
					</td>
				</tr>
				
				<tr>
					<th><@locale code="apps.oauth.connect.idTokenEncryptionMethod" />：</th>
					<td >
						<select  id="idTokenEncryptionMethod" name="idTokenEncryptionMethod"  class="form-control" >
							<option value="none" selected>No encryption</option>
							<option value="A128CBC+HS256" >Composite Authenticated Encryption algorithm using AES in Cipher Block Chaining (CBC) mode with PKCS #5 padding with an integrity calculation using HMAC SHA-256, using a 256 bit CMK (and 128 bit CEK)</option>
							<option value="A256CBC+HS512" >Composite Authenticated Encryption algorithm using AES in CBC mode with PKCS #5 padding with an integrity calculation using HMAC SHA-512, using a 512 bit CMK (and 256 bit CEK)</option>
							<option value="A128GCM" >AES GCM using 128 bit keys</option>
							<option value="A256GCM" >AES GCM using 256 bit keys</option>
					</select>
					</td>
					<th><@locale code="apps.oauth.connect.userInfoEncryptionMethod" />：</th>
					<td >
						<select  id="userInfoEncryptionMethod" name="userInfoEncryptionMethod"  class="form-control" >
							<option value="none" selected>No encryption</option>
							<option value="A128CBC+HS256" >Composite Authenticated Encryption algorithm using AES in Cipher Block Chaining (CBC) mode with PKCS #5 padding with an integrity calculation using HMAC SHA-256, using a 256 bit CMK (and 128 bit CEK)</option>
							<option value="A256CBC+HS512" >Composite Authenticated Encryption algorithm using AES in CBC mode with PKCS #5 padding with an integrity calculation using HMAC SHA-512, using a 512 bit CMK (and 256 bit CEK)</option>
							<option value="A128GCM" >AES GCM using 128 bit keys</option>
							<option value="A256GCM" >AES GCM using 256 bit keys</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.approvalPrompt" />：</th>
					<td >
						<select  id="approvalPrompt" name="approvalPrompt"  class="form-control" >
							<option value="force" selected>
								<@locale code="apps.oauth.approvalPrompt.force" /></option>
							<option value="auto"  >
								<@locale code="apps.oauth.approvalPrompt.auto" /></option>
						</select>
					</td>
					<th></th>
					<td >
					</td>
				</tr>
				<tr>
					<td colspan =4>
						<input  id="status" type="hidden" name="status"  value="1"/>
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