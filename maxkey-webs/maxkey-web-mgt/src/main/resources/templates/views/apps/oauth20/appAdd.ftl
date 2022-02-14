<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
	<script type="text/javascript">
    <!--
    $(function(){ 
        if("OAuth_v2.0"=="${model.protocol!}") { 
            $("#app_protocol_control").html(
                '<select  id="protocol" name="protocol" class="form-control  form-select" >'+
                    '<option value="OAuth_v2.0" selected >OAuth_v2.0</option>'+
                    '<option value="OAuth_v2.1" >OAuth_v2.1</option>'+
                    '<option value="OpenID_Connect_v1.0" >OpenID_Connect_v1.0</option>'+
                '</select>'
            );
        }
    });
    //-->
    </script>
</head>
<body>
<div  class="container">   
    <div  class="row">
    <div class="col-md-12"> 
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
					<td colspan=4><h5><@locale code="apps.oauth.v2.0.info" /></h5></td>
				</tr>
				<tr  class="d-none">
					<th style="width:15%;"><@locale code="apps.oauth.v2.0.clientId" /></th>
					<td style="width:35%;">${model.clientId}
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
					<th><@locale code="apps.oauth.scope" /></th>
					<td >
						<table class="hidetable"  style="width:100%;">
							<tr>
								<td>read    <input  class="form-check-input" type="checkbox" id="scope_trust" name="scope" value="read"  checked /></td>
								<td>write   <input  class="form-check-input" type="checkbox" id="scope_write" name="scope" value="write"/></td>
								<td>trust   <input  class="form-check-input" type="checkbox" id="scope_trust" name="scope" value="trust"/></td>
								<td>openid  <input  class="form-check-input" type="checkbox" id="scope_openid" name="scope" value="openid"/></td>
								<td>profile <input  class="form-check-input" type="checkbox" id="scope_profile" name="scope" value="profile"/></td>
								<td>email   <input  class="form-check-input" type="checkbox" id="scope_email" name="scope" value="email"/></td>
								<td>phone   <input  class="form-check-input" type="checkbox" id="scope_phone" name="scope" value="phone"/></td>
								<td>address <input  class="form-check-input" type="checkbox" id="scope_address" name="scope" value="address"/></td>
								<td>all     <input  class="form-check-input" type="checkbox" id="scope_all" name="scope" value="all"/></td>
								
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.GrantTypes" /></th>
					<td  colspan="3">
						<table  class="hidetable"  style="width:100%;">
							<tr>
								<td>authorization_code  <input  class="form-check-input" type="checkbox" id="grantTypes_authorization_code" name="authorizedGrantTypes" value="authorization_code"  checked /></td>
								<td>password            <input  class="form-check-input" type="checkbox" id="grantTypes_trust" name="authorizedGrantTypes" value="password"/></td>
								<td>implicit            <input  class="form-check-input" type="checkbox" id="grantTypes_implicit" name="authorizedGrantTypes" value="implicit"/></td>
								<td>client_credentials  <input  class="form-check-input" type="checkbox" id="grantTypes_client_credentials" name="authorizedGrantTypes" value="client_credentials"/></td>
								<td>refresh_token       <input  class="form-check-input" type="checkbox" id="grantTypes_refresh_token" name="authorizedGrantTypes" value="refresh_token"/></td>
								<td>id_token            <input  class="form-check-input"  type="checkbox" id="grantTypes_id_token" name="authorizedGrantTypes" value="id_token"/></td>
								<td>token               <input  class="form-check-input" type="checkbox" id="grantTypes_token" name="authorizedGrantTypes" value="token"/></td>
								
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.oauth.registeredRedirectUris" /></th>
					<td colspan=3>
						<textarea  class="form-control"  id="registeredRedirectUris" name="registeredRedirectUris" rows="4" cols="60"></textarea>
						
					</td>
				</tr>
				
				<tr>
                    <th  style="width:15%;">PKCE</th>
                    <td  style="width:35%;">
                        <select  id="pkce" name="pkce" class="form-control  form-select">
                            <option value="yes"  >
                                <@locale code="common.text.yes" /></option>
                            <option value="no" selected>
                                <@locale code="common.text.no" /></option>
                        </select>
                    </td>
                    <th  style="width:15%;"><@locale code="apps.oauth.approvalPrompt" /></th>
                    <td  style="width:35%;">
                        <select  id="approvalPrompt" name="approvalPrompt"  class="form-control  form-select" >
                            <option value="force" selected>
                                <@locale code="apps.oauth.approvalPrompt.force" /></option>
                            <option value="auto"  >
                                <@locale code="apps.oauth.approvalPrompt.auto" /></option>
                        </select>
                    </td>
                    
                </tr>
                <tr>
                    <th><@locale code="apps.oauth.accessTokenValiditySeconds" /></th>
                    <td >
                       <div class="input-group">
                          <input type="text"  class="form-control" id="accessTokenValiditySeconds" name="accessTokenValiditySeconds"  title="" value="1800"/>
                          <span class="input-group-text">Seconds</span>
                        </div>
                    </td>
                    <th><@locale code="apps.oauth.refreshTokenValiditySeconds" /></th>
                    <td>
                       <div class="input-group">
                          <input type="text" class="form-control"  id="refreshTokenValiditySeconds" name="refreshTokenValiditySeconds"  title="" value="1800"/>
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
                        <input type="text" class="form-control" id="issuer" name="issuer"  title="" value="" />
                    </td>
                    <th ><@locale code="apps.oauth.connect.audience" /></th>
                    <td >
                         <input type="text" class="form-control" id="audience" name="audience"  title="" value="" />
                    </td>
                </tr>
				<tr>
                    <th style="width:15%;"><@locale code="apps.oauth.connect.signature" /></th>
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
                    <th><@locale code="apps.oauth.connect.userInfoResponse" /></th>
                    <td>
                        <select id="userInfoResponse" name="userInfoResponse"   class="form-control  form-select">
                            <option value="NORMAL" selected     >NORMAL</option>
                            <option value="SIGNING"             >SIGNING</option>
                            <option value="ENCRYPTION"          >ENCRYPTION</option>
                            <option value="SIGNING_ENCRYPTION"  >SIGNING_ENCRYPTION</option>
                        </select>
                    </td>
                </tr>
                <tr>
                        <th ><@locale code="apps.oauth.connect.signatureKey" /></th>
                        <td colspan ="3">
                            <textarea class="form-control" id="signatureKey" name="signatureKey"></textarea>
                        </td>
                </tr>
                <tr>
                    <th style="width:15%;"><@locale code="apps.oauth.connect.algorithm" /></th>
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
                    <th width="140px"><@locale code="apps.oauth.connect.encryptionMethod" /></th>
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
                        <th ><@locale code="apps.oauth.connect.algorithmKey" /></th>
                        <td colspan ="3">
                            <textarea class="form-control" id="algorithmKey" name="algorithmKey"></textarea>
                        </td>
                </tr>
				
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