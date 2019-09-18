<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"        uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib  prefix="fn" 	  uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
<!--
$(function(){	

	$("#generateSecret").on("click",function(){
		$.post("<s:Base/>/apps/generate/secret/oauth20", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#clientSecret").val(data+"");
			$("#clientSecret_text").html(data+"");
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/oauth20/update"
			forward="<s:Base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960"  class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appUpdateCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"  class="datatable" >
				<tbody>
				
				<tr>
					<td colspan=4><s:Locale code="apps.oauth.v2.0.info" /></td>
				</tr>
				<tr>
					<th style="width:15%;"><s:Locale code="apps.oauth.v2.0.clientId" />：</th>
					<td style="width:35%;">
						<span id="clientId_text">${model.clientId}</span>
						<input type="hidden" id="clientId" name="clientId"  title="" value="${model.clientId}"/>
						
					</td>
					<th style="width:15%;"><s:Locale code="apps.oauth.v2.0.clientSecret" />：</th>
					<td style="width:35%;">
						<span id="clientSecret_text">${model.clientSecret}</span>
						<input type="hidden" id="clientSecret" name="clientSecret"  title="" value="${model.clientSecret}"/>
						
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.oauth.scope" />：</th>
					<td  colspan="3">
						<table  class="hidetable"  style="width:100%;">
							<tr>
								<td>read<input type="checkbox" id="scope_trust" name="scope" value="read"  <c:if test="${fn:contains(model.scope, 'read')}">checked</c:if> /></td>
								<td>write<input type="checkbox" id="scope_write" name="scope" value="write" <c:if test="${fn:contains(model.scope, 'write')}">checked</c:if>/></td>
								<td>trust<input type="checkbox" id="scope_trust" name="scope" value="trust" <c:if test="${fn:contains(model.scope, 'trust')}">checked</c:if>/></td>
								<td>openid<input type="checkbox" id="scope_openid" name="scope" value="openid" <c:if test="${fn:contains(model.scope, 'openid')}">checked</c:if>/></td>
								<td>profile<input type="checkbox" id="scope_profile" name="scope" value="profile" <c:if test="${fn:contains(model.scope, 'profile')}">checked</c:if>/></td>
								<td>email<input type="checkbox" id="scope_email" name="scope" value="email" <c:if test="${fn:contains(model.scope, 'email')}">checked</c:if>/></td>
								<td>phone<input type="checkbox" id="scope_phone" name="scope" value="phone" <c:if test="${fn:contains(model.scope, 'phone')}">checked</c:if>/></td>
								<td>address<input type="checkbox" id="scope_address" name="scope" value="address" <c:if test="${fn:contains(model.scope, 'address')}">checked</c:if>/></td>
								<td>all<input type="checkbox" id="scope_all" name="scope" value="all" <c:if test="${fn:contains(model.scope, 'all')}">checked</c:if>/></td>
								<td><b class="orange">*</b><label for="scope"></label></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.oauth.GrantTypes" />：</th>
					<td colspan="3">
						<table  class="hidetable"  style="width:100%;">
							<tr>
								<td>authorization_code | code<input <c:if test="${fn:contains(model.authorizedGrantTypes, 'authorization_code')}">checked</c:if>  type="checkbox" id="grantTypes_authorization_code" name="authorizedGrantTypes" value="authorization_code"/></td>
								<td>password<input <c:if test="${fn:contains(model.authorizedGrantTypes, 'password')}">checked</c:if>  type="checkbox" id="grantTypes_trust" name="authorizedGrantTypes" value="password"/></td>
								<td>implicit<input <c:if test="${fn:contains(model.authorizedGrantTypes, 'implicit')}">checked</c:if>  type="checkbox" id="grantTypes_implicit" name="authorizedGrantTypes" value="implicit"/></td>
								<td>refresh_token<input <c:if test="${fn:contains(model.authorizedGrantTypes, 'refresh_token')}">checked</c:if>  type="checkbox" id="grantTypes_refresh_token" name="authorizedGrantTypes" value="refresh_token"/></td>
								<td>id_token<input <c:if test="${fn:contains(model.authorizedGrantTypes, 'id_token')}">checked</c:if>  type="checkbox" id="grantTypes_id_token" name="authorizedGrantTypes" value="id_token"/></td>
								<td>token<input <c:if test="${fn:contains(model.authorizedGrantTypes, 'token')}">checked</c:if>  type="checkbox" id="grantTypes_token" name="authorizedGrantTypes" value="token"/></td>
								
								<td><b class="orange">*</b><label for="authorizedGrantTypes"></label></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.oauth.registeredRedirectUris" />：</th>
					<td colspan=3>
						<textarea  id="registeredRedirectUris" name="registeredRedirectUris" rows="4" cols="60">${model.registeredRedirectUris}</textarea>
						<b class="orange">*</b><label for="registeredRedirectUris"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.oauth.accessTokenValiditySeconds" />：</th>
					<td >
						<input type="text" id="accessTokenValiditySeconds" name="accessTokenValiditySeconds"  title="" value="${model.accessTokenValiditySeconds}"/>
						<b class="orange">*</b><label for="accessTokenValiditySeconds"></label>
					</td>
					<th><s:Locale code="apps.oauth.refreshTokenValiditySeconds" />：</th>
					<td>
						<input type="text" id="refreshTokenValiditySeconds" name="refreshTokenValiditySeconds"  title="" value="${model.refreshTokenValiditySeconds}"/>
						<b class="orange">*</b><label for="refreshTokenValiditySeconds"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.connect.idTokenSigningAlgorithm" />：</th>
					<td >
						<select  id="idTokenSigningAlgorithm" name="idTokenSigningAlgorithm" >
							<option value="none"   <c:if test="${'none' ==model.idTokenSigningAlgorithm}">selected</c:if>>No digital signature</option>
							<option value="HS256"  <c:if test="${'HS256'==model.idTokenSigningAlgorithm}">selected</c:if>>HMAC using SHA-256 hash algorithm</option>
							<option value="HS384"  <c:if test="${'HS384'==model.idTokenSigningAlgorithm}">selected</c:if>>HMAC using SHA-384 hash algorithm</option>
							<option value="HS512"  <c:if test="${'HS512'==model.idTokenSigningAlgorithm}">selected</c:if>>HMAC using SHA-512 hash algorithm</option>
							<option value="RS256"  <c:if test="${'RS256'==model.idTokenSigningAlgorithm}">selected</c:if>>RSASSA using SHA-256 hash algorithm</option>
							<option value="RS384"  <c:if test="${'RS384'==model.idTokenSigningAlgorithm}">selected</c:if>>RSASSA using SHA-384 hash algorithm</option>
							<option value="RS512"  <c:if test="${'RS256'==model.idTokenSigningAlgorithm}">selected</c:if>>RSASSA using SHA-512 hash algorithm</option>
							<option value="ES256"  <c:if test="${'ES256'==model.idTokenSigningAlgorithm}">selected</c:if>>ECDSA using P-256 curve and SHA-256 hash algorithm</option>
							<option value="ES384"  <c:if test="${'ES384'==model.idTokenSigningAlgorithm}">selected</c:if>>ECDSA using P-384 curve and SHA-384 hash algorithm</option>
							<option value="ES512"  <c:if test="${'ES512'==model.idTokenSigningAlgorithm}">selected</c:if>>ECDSA using P-512 curve and SHA-512 hash algorithm</option>
						</select>
					</td>
					<th><s:Locale code="apps.connect.userInfoSigningAlgorithm" />：</th>
					<td >
						<select  id="userInfoSigningAlgorithm" name="userInfoSigningAlgorithm" >
							<option value="none"   <c:if test="${'none' ==model.userInfoSigningAlgorithm}">selected</c:if>>No digital signature</option>
							<option value="HS256"  <c:if test="${'HS256'==model.userInfoSigningAlgorithm}">selected</c:if>>HMAC using SHA-256 hash algorithm</option>
							<option value="HS384"  <c:if test="${'HS384'==model.userInfoSigningAlgorithm}">selected</c:if>>HMAC using SHA-384 hash algorithm</option>
							<option value="HS512"  <c:if test="${'HS512'==model.userInfoSigningAlgorithm}">selected</c:if>>HMAC using SHA-512 hash algorithm</option>
							<option value="RS256"  <c:if test="${'RS256'==model.userInfoSigningAlgorithm}">selected</c:if>>RSASSA using SHA-256 hash algorithm</option>
							<option value="RS384"  <c:if test="${'RS384'==model.userInfoSigningAlgorithm}">selected</c:if>>RSASSA using SHA-384 hash algorithm</option>
							<option value="RS512"  <c:if test="${'RS256'==model.userInfoSigningAlgorithm}">selected</c:if>>RSASSA using SHA-512 hash algorithm</option>
							<option value="ES256"  <c:if test="${'ES256'==model.userInfoSigningAlgorithm}">selected</c:if>>ECDSA using P-256 curve and SHA-256 hash algorithm</option>
							<option value="ES384"  <c:if test="${'ES384'==model.userInfoSigningAlgorithm}">selected</c:if>>ECDSA using P-384 curve and SHA-384 hash algorithm</option>
							<option value="ES512"  <c:if test="${'ES512'==model.userInfoSigningAlgorithm}">selected</c:if>>ECDSA using P-512 curve and SHA-512 hash algorithm</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.connect.jwksUri" />：</th>
					<td colspan =3>
						<input type="text" id="jwksUri" name="jwksUri"  title="" value="${model.jwksUri}"/>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.connect.idTokenEncryptedAlgorithm" />：</th>
					<td >
						<select  id="idTokenEncryptedAlgorithm" name="idTokenEncryptedAlgorithm" >
							<option value="none" <c:if test="${'none'==model.idTokenEncryptedAlgorithm}">selected</c:if> >No encryption</option>
							<option value="RSA1_5" <c:if test="${'RSA1_5'==model.idTokenEncryptedAlgorithm}">selected</c:if> >RSAES-PKCS1-V1_5</option>
							<option value="RSA-OAEP" <c:if test="${'RSA-OAEP'==model.idTokenEncryptedAlgorithm}">selected</c:if>>RSAES using Optimal Asymmetric Encryption Padding (OAEP)</option>
							<option value="A128KW" <c:if test="${'A128KW'==model.idTokenEncryptedAlgorithm}">selected</c:if>>AES Key Wrap Algorithm using 128 bit keys </option>
							<option value="A256KW" <c:if test="${'A256KW'==model.idTokenEncryptedAlgorithm}">selected</c:if>>AES Key Wrap Algorithm using 256 bit keys</option>
							<option value="dir" <c:if test="${'dir'==model.idTokenEncryptedAlgorithm}">selected</c:if>>Direct use of a shared symmetric key as the Content Master Key (CMK) for the block encryption step</option>
							<option value="ECDH-ES" <c:if test="${'ECDH-ES'==model.idTokenEncryptedAlgorithm}">selected</c:if>>Elliptic Curve Diffie-Hellman Ephemeral Static key agreement using the Concat KDF, with the agreed-upon key being used directly as the Content Master Key (CMK)</option>
							<option value="ECDH-ES+A128KW" <c:if test="${'ECDH-ES+A128KW'==model.idTokenEncryptedAlgorithm}">selected</c:if>>Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A128KW function</option>
							<option value="ECDH-ES+A256KW" <c:if test="${'ECDH-ES+A256KW'==model.idTokenEncryptedAlgorithm}">selected</c:if>>Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A256KW function</option>
						
						</select>
					</td>
					<th><s:Locale code="apps.connect.userInfoEncryptedAlgorithm" />：</th>
					<td >
						<select  id="userInfoEncryptedAlgorithm" name="userInfoEncryptedAlgorithm" >
							<option value="none" <c:if test="${'none'==model.userInfoEncryptedAlgorithm}">selected</c:if> >No encryption</option>
							<option value="RSA1_5" <c:if test="${'RSA1_5'==model.userInfoEncryptedAlgorithm}">selected</c:if> >RSAES-PKCS1-V1_5</option>
							<option value="RSA-OAEP" <c:if test="${'RSA-OAEP'==model.userInfoEncryptedAlgorithm}">selected</c:if>>RSAES using Optimal Asymmetric Encryption Padding (OAEP)</option>
							<option value="A128KW" <c:if test="${'A128KW'==model.userInfoEncryptedAlgorithm}">selected</c:if>>AES Key Wrap Algorithm using 128 bit keys </option>
							<option value="A256KW" <c:if test="${'A256KW'==model.userInfoEncryptedAlgorithm}">selected</c:if>>AES Key Wrap Algorithm using 256 bit keys</option>
							<option value="dir" <c:if test="${'dir'==model.userInfoEncryptedAlgorithm}">selected</c:if>>Direct use of a shared symmetric key as the Content Master Key (CMK) for the block encryption step</option>
							<option value="ECDH-ES" <c:if test="${'ECDH-ES'==model.userInfoEncryptedAlgorithm}">selected</c:if>>Elliptic Curve Diffie-Hellman Ephemeral Static key agreement using the Concat KDF, with the agreed-upon key being used directly as the Content Master Key (CMK)</option>
							<option value="ECDH-ES+A128KW" <c:if test="${'ECDH-ES+A128KW'==model.userInfoEncryptedAlgorithm}">selected</c:if>>Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A128KW function</option>
							<option value="ECDH-ES+A256KW" <c:if test="${'ECDH-ES+A256KW'==model.userInfoEncryptedAlgorithm}">selected</c:if>>Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per ECDH-ES and Section 4.7, but where the agreed-upon key is used to wrap the Content Master Key (CMK) with the A256KW function</option>
						
						</select>
					</td>
				</tr>
				
				<tr>
					<th><s:Locale code="apps.connect.idTokenEncryptionMethod" />：</th>
					<td >
						<select  id="idTokenEncryptionMethod" name="idTokenEncryptionMethod" >
							<option value="none" <c:if test="${'none'==model.idTokenEncryptionMethod}">selected</c:if>>No encryption</option>
							<option value="A128CBC+HS256" <c:if test="${'A128CBC+HS256'==model.idTokenEncryptionMethod}">selected</c:if>>Composite Authenticated Encryption algorithm using AES in Cipher Block Chaining (CBC) mode with PKCS #5 padding with an integrity calculation using HMAC SHA-256, using a 256 bit CMK (and 128 bit CEK)</option>
							<option value="A256CBC+HS512" <c:if test="${'A256CBC+HS512'==model.idTokenEncryptionMethod}">selected</c:if>>Composite Authenticated Encryption algorithm using AES in CBC mode with PKCS #5 padding with an integrity calculation using HMAC SHA-512, using a 512 bit CMK (and 256 bit CEK)</option>
							<option value="A128GCM" <c:if test="${'A128GCM'==model.idTokenEncryptionMethod}">selected</c:if>>AES GCM using 128 bit keys</option>
							<option value="A256GCM" <c:if test="${'A256GCM'==model.idTokenEncryptionMethod}">selected</c:if>>AES GCM using 256 bit keys</option>
					</select>
					</td>
					<th><s:Locale code="apps.connect.userInfoEncryptionMethod" />：</th>
					<td >
						<select  id="userInfoEncryptionMethod" name="userInfoEncryptionMethod" >
							<option value="none" <c:if test="${'none'==model.userInfoEncryptionMethod}">selected</c:if>>No encryption</option>
							<option value="A128CBC+HS256" <c:if test="${'A128CBC+HS256'==model.userInfoEncryptionMethod}">selected</c:if>>Composite Authenticated Encryption algorithm using AES in Cipher Block Chaining (CBC) mode with PKCS #5 padding with an integrity calculation using HMAC SHA-256, using a 256 bit CMK (and 128 bit CEK)</option>
							<option value="A256CBC+HS512" <c:if test="${'A256CBC+HS512'==model.userInfoEncryptionMethod}">selected</c:if>>Composite Authenticated Encryption algorithm using AES in CBC mode with PKCS #5 padding with an integrity calculation using HMAC SHA-512, using a 512 bit CMK (and 256 bit CEK)</option>
							<option value="A128GCM" <c:if test="${'A128GCM'==model.userInfoEncryptionMethod}">selected</c:if>>AES GCM using 128 bit keys</option>
							<option value="A256GCM" <c:if test="${'A256GCM'==model.userInfoEncryptionMethod}">selected</c:if>>AES GCM using 256 bit keys</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.oauth.approvalPrompt" />：</th>
					<td >
						<select  id="approvalPrompt" name="approvalPrompt" >
							<option value="force"  <c:if test="${null==model.approvalPrompt}">selected</c:if>>
								<s:Locale code="apps.oauth.approvalPrompt.force" /></option>
							<option value="auto"  <c:if test="${'auto'==model.approvalPrompt}">selected</c:if>>
								<s:Locale code="apps.oauth.approvalPrompt.auto" /></option>
						</select>
					</td>
					<th><s:Locale code="apps.isAdapter" />：</th>
					<td >
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  <c:if test="${0==model.isAdapter}">selected</c:if> >
								<s:Locale code="apps.isAdapter.no" /></option>
							<option value="1"  <c:if test="${1==model.isAdapter}">selected</c:if> >
								<s:Locale code="apps.isAdapter.yes" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.adapter" />：</th>
					<td colspan =3>
						<input type="text" id="adapter" name="adapter"  title="" value="${model.adapter}"/>
					</td>
				</tr>
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>
    		<input class="button" id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>