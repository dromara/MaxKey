/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.authz.oauth2.provider.userinfo.endpoint;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.dromara.maxkey.constants.ContentType;
import org.dromara.maxkey.crypto.jwt.encryption.service.impl.DefaultJwtEncryptionAndDecryptionService;
import org.dromara.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.util.RequestTokenUtils;
import org.dromara.maxkey.util.StringGenerator;
import org.dromara.maxkey.web.HttpResponseAdapter;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;
import com.nimbusds.jwt.PlainJWT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.nimbusds.jwt.SignedJWT;

@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class UserInfoOIDCEndpoint {
	static final  Logger _logger = LoggerFactory.getLogger(UserInfoOIDCEndpoint.class);	
	@Autowired
	@Qualifier("oauth20JdbcClientDetailsService")
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	@Qualifier("oauth20TokenServices")
	private DefaultTokenServices oauth20tokenServices;
	
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	protected AppsService appsService;
	
	OAuthDefaultUserInfoAdapter defaultOAuthUserInfoAdapter=new OAuthDefaultUserInfoAdapter();
	
    @Autowired
    protected HttpResponseAdapter httpResponseAdapter;
		
    @Operation(summary = "OIDC 用户信息接口", description = "请求参数access_token , header Authorization , token ",method="GET")
	@RequestMapping(value=OAuth2Constants.ENDPOINT.ENDPOINT_OPENID_CONNECT_USERINFO, method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String connect10aUserInfo(HttpServletRequest request, 
									 HttpServletResponse response) {
    	String access_token =  RequestTokenUtils.resolveAccessToken(request);
    	_logger.debug("access_token {}" , access_token);
		if (!StringGenerator.uuidMatches(access_token)) {
			return JsonUtils.gsonToString(accessTokenFormatError(access_token));
		}
		
		String principal = "";
		OAuth2Authentication oAuth2Authentication = null;
		try{
			 oAuth2Authentication = oauth20tokenServices.loadAuthentication(access_token);
			 
			 principal=((SignPrincipal)oAuth2Authentication.getPrincipal()).getUsername();
			 
			 Set<String >scopes = oAuth2Authentication.getOAuth2Request().getScope();
			 ClientDetails clientDetails = 
					 clientDetailsService.loadClientByClientId(oAuth2Authentication.getOAuth2Request().getClientId(),true);
			 
			 UserInfo userInfo = queryUserInfo(principal);
			 String userJson = "";
			 Builder jwtClaimsSetBuilder= new JWTClaimsSet.Builder();
			 
			 SignPrincipal oauthPrincipal = (SignPrincipal)oAuth2Authentication.getUserAuthentication().getPrincipal();
			 
			 String subject = AbstractAuthorizeAdapter.getValueByUserAttr(userInfo, clientDetails.getSubject());
			 _logger.debug("userId : {} , username : {} , displayName : {} , subject : {}" , 
					 userInfo.getId(),
					 userInfo.getUsername(),
					 userInfo.getDisplayName(),
					 subject);
			 
			 jwtClaimsSetBuilder.claim("sub", subject);
			 jwtClaimsSetBuilder.claim("institution", userInfo.getInstId());
			 jwtClaimsSetBuilder.claim(WebConstants.ONLINE_TICKET_NAME, oauthPrincipal.getSessionId());
			 
		 	if(scopes.contains("profile")){
		 		jwtClaimsSetBuilder.claim("userId", userInfo.getId());
		 		jwtClaimsSetBuilder.claim("user", userInfo.getUsername());
		 		jwtClaimsSetBuilder.claim("name", userInfo.getDisplayName());
		 		jwtClaimsSetBuilder.claim("preferred_username", userInfo.getDisplayName());
		 		jwtClaimsSetBuilder.claim("given_name", userInfo.getGivenName());
		 		jwtClaimsSetBuilder.claim("family_name", userInfo.getFamilyName());
		 		jwtClaimsSetBuilder.claim("middle_name", userInfo.getMiddleName());
		 		jwtClaimsSetBuilder.claim("nickname", userInfo.getNickName());
		 		jwtClaimsSetBuilder.claim("profile", "profile");
		 		jwtClaimsSetBuilder.claim("picture", "picture");
		 		//jwtClaimsSetBuilder.claim("website", userInfo.getWebSite());
		 		jwtClaimsSetBuilder.claim("displayName", userInfo.getDisplayName());
		 		
		 		jwtClaimsSetBuilder.claim("departmentId", userInfo.getDepartmentId());
		 		jwtClaimsSetBuilder.claim("department", userInfo.getDepartment());
				
				String gender;
				 switch(userInfo.getGender()){
				 	case UserInfo.GENDER.MALE  :
				 		gender="male";break;
				 	case UserInfo.GENDER.FEMALE  :
				 		gender="female";break;
				 	default:
				 		gender="unknown";
				 }
				jwtClaimsSetBuilder.claim("gender", gender);
				jwtClaimsSetBuilder.claim("zoneinfo", userInfo.getTimeZone());
				jwtClaimsSetBuilder.claim("locale", userInfo.getLocale());
				jwtClaimsSetBuilder.claim("updated_time", userInfo.getModifiedDate());
				jwtClaimsSetBuilder.claim("birthdate", userInfo.getBirthDate());
		 	}
		 	
		 	if(scopes.contains("email")){
		 		jwtClaimsSetBuilder.claim("email", userInfo.getWorkEmail());
		 		jwtClaimsSetBuilder.claim("email_verified", false);
		 	}
		 	
			if(scopes.contains("phone")){
				jwtClaimsSetBuilder.claim("phone_number", userInfo.getWorkPhoneNumber());
				jwtClaimsSetBuilder.claim("phone_number_verified", false);
			}
			
			if(scopes.contains("address")){
				HashMap<String, String> addressFields = new HashMap<String, String>();
				addressFields.put("country", userInfo.getWorkCountry());
				addressFields.put("region", userInfo.getWorkRegion());
				addressFields.put("locality", userInfo.getWorkLocality());
				addressFields.put("street_address", userInfo.getWorkStreetAddress());
				addressFields.put("formatted", userInfo.getWorkAddressFormatted());
				addressFields.put("postal_code", userInfo.getWorkPostalCode());
				
				jwtClaimsSetBuilder.claim("address", addressFields);
			}
			
			jwtClaimsSetBuilder
					.jwtID(UUID.randomUUID().toString())// set a random NONCE in the middle of it
					.audience(Arrays.asList(clientDetails.getClientId()))
					.issueTime(new Date())
					.expirationTime(new Date(new Date().getTime() + clientDetails.getAccessTokenValiditySeconds() * 1000));
			
			//default ContentType
			response.setContentType(ContentType.APPLICATION_JWT_UTF8);
			
			JWTClaimsSet userInfoJWTClaims = jwtClaimsSetBuilder.build();
			JWT userInfoJWT=null;
			
			if(clientDetails.getUserInfoResponse().equalsIgnoreCase("NORMAL")) {
				response.setContentType(ContentType.APPLICATION_JSON_UTF8);
				userJson = userInfoJWTClaims.toString();
			}else if (StringUtils.isNotBlank(clientDetails.getSignature()) 
			        && !clientDetails.getSignature().equalsIgnoreCase("none")
			        && clientDetails.getUserInfoResponse().equalsIgnoreCase("ENCRYPTION")) {
			    //需要签名  signed ID token
				DefaultJwtSigningAndValidationService jwtSignerService = null;
				try {
					jwtSignerService = new DefaultJwtSigningAndValidationService(
							clientDetails.getSignatureKey(),
							clientDetails.getClientId() + "_sig",
							clientDetails.getSignature());
				}catch(Exception e) {
					_logger.error("Couldn't create Jwt Signing Service",e);
				}
				
				JWSAlgorithm signingAlg = jwtSignerService.getDefaultSigningAlgorithm();
				userInfoJWTClaims = new JWTClaimsSet
						.Builder(userInfoJWTClaims)
						.claim("kid", jwtSignerService.getDefaultSignerKeyId())
						.build();

				userInfoJWT = new SignedJWT(new JWSHeader(signingAlg), userInfoJWTClaims);
				// sign it with the server's key
				jwtSignerService.signJwt((SignedJWT) userInfoJWT);
				
				userJson = userInfoJWT.serialize();
			}else if (StringUtils.isNotBlank(clientDetails.getAlgorithm())
			        && !clientDetails.getAlgorithm().equalsIgnoreCase("none")
			        && clientDetails.getUserInfoResponse().equalsIgnoreCase("SIGNING")
					) {
			    // 需要加密
				try {
					DefaultJwtEncryptionAndDecryptionService jwtEncryptionService = 
								new DefaultJwtEncryptionAndDecryptionService(
										clientDetails.getAlgorithmKey(),
										clientDetails.getClientId()  + "_enc",
										clientDetails.getAlgorithm());
					
					Payload payload = userInfoJWTClaims.toPayload();
					
					// Example Request JWT encrypted with RSA-OAEP-256 and 128-bit AES/GCM
					//JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.RSA1_5, EncryptionMethod.A128GCM);
					JWEHeader jweHeader = new JWEHeader(
							jwtEncryptionService.getDefaultAlgorithm(clientDetails.getAlgorithm()), 
							jwtEncryptionService.parseEncryptionMethod(clientDetails.getEncryptionMethod()));
					
					JWEObject jweObject = new JWEObject(
						    new JWEHeader.Builder(jweHeader)
						        .contentType("JWT") // required to indicate nested JWT
						        .build(),
						        payload);
					
					jwtEncryptionService.encryptJwt(jweObject);
					userJson = jweObject.serialize();
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
					_logger.error("Couldn't create Jwt Encryption Exception", e);
				}
			}else {
			    //不需要加密和签名  unsigned ID token
                userInfoJWT = new PlainJWT(userInfoJWTClaims);
                userJson = userInfoJWT.serialize();
            }
			
			_logger.trace("OpenID Connect Response {}",userJson);
			return userJson;
			 
		}catch(OAuth2Exception e){
			HashMap<String,Object>authzException=new HashMap<String,Object>();
			authzException.put(OAuth2Exception.ERROR, e.getOAuth2ErrorCode());
			authzException.put(OAuth2Exception.DESCRIPTION,e.getMessage());
			return JsonUtils.toString(authzException);
		}
	}
	
	public HashMap<String,Object> accessTokenFormatError(String access_token){
		HashMap<String,Object>atfe=new HashMap<String,Object>();
		atfe.put(OAuth2Exception.ERROR, "token Format Invalid");
		atfe.put(OAuth2Exception.DESCRIPTION, "access Token Format Invalid , access_token : "+access_token);
		
		return atfe;
	}

	public  UserInfo queryUserInfo(String userId){
		return (UserInfo) userInfoService.findByUsername(userId);
	}


	public void setOauth20tokenServices(DefaultTokenServices oauth20tokenServices) {
		this.oauth20tokenServices = oauth20tokenServices;
	}
	


	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
}
