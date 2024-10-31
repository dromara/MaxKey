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
 

/**
 * 
 */
package org.dromara.maxkey.authz.oidc.idtoken;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.oauth2.common.DefaultOAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.token.TokenEnhancer;
import org.dromara.maxkey.configuration.oidc.OIDCProviderMetadata;
import org.dromara.maxkey.crypto.jwt.encryption.service.impl.DefaultJwtEncryptionAndDecryptionService;
import org.dromara.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.joda.time.DateTime;

import com.nimbusds.jose.util.Base64URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * @author Crystal.Sea
 *
 */
public class OIDCIdTokenEnhancer implements TokenEnhancer {
	private static final  Logger _logger = LoggerFactory.getLogger(OIDCIdTokenEnhancer.class);
	
	public  static final  String ID_TOKEN_SCOPE="openid";

	private OIDCProviderMetadata providerMetadata;
	
	private ClientDetailsService clientDetailsService;

	public void setProviderMetadata(OIDCProviderMetadata providerMetadata) {
		this.providerMetadata = providerMetadata;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		OAuth2Request  request=authentication.getOAuth2Request();
		if (request.getScope().contains(ID_TOKEN_SCOPE)) {//Enhance for OpenID Connect
			ClientDetails clientDetails = 
					clientDetailsService.loadClientByClientId(authentication.getOAuth2Request().getClientId(),true);
			
			DefaultJwtSigningAndValidationService jwtSignerService = null;
			JWSAlgorithm signingAlg = null;
			String signerKeyId = clientDetails.getClientId() + "_sig";
			try {//jwtSignerService
				if (StringUtils.isNotBlank(clientDetails.getSignature()) && !clientDetails.getSignature().equalsIgnoreCase("none")) {
					jwtSignerService = new DefaultJwtSigningAndValidationService(
							clientDetails.getSignatureKey(),
							signerKeyId,
							clientDetails.getSignature()
						);

					signingAlg = jwtSignerService.getDefaultSigningAlgorithm();
				}
			}catch(Exception e) {
				_logger.error("Couldn't create Jwt Signing Service",e);
			}
			
			JWTClaimsSet.Builder builder=new JWTClaimsSet.Builder();
			builder.subject(authentication.getName())
		      .expirationTime(accessToken.getExpiration())
		      .issuer(clientDetails.getIssuer())
		      .issueTime(new Date())
		      .audience(Arrays.asList(authentication.getOAuth2Request().getClientId()))
		      .jwtID(UUID.randomUUID().toString());
			
			/**
			 * https://self-issued.me
			 * @see http://openid.net/specs/openid-connect-core-1_0.html#SelfIssuedDiscovery
			 *     7.  Self-Issued OpenID Provider
			 */
			if(clientDetails.getIssuer()!=null 
					&& jwtSignerService != null
					&& clientDetails.getIssuer().equalsIgnoreCase("https://self-issued.me") 
					){
				builder.claim("sub_jwk", jwtSignerService.getAllPublicKeys().get(signerKeyId));
			}
			
			// if the auth time claim was explicitly requested OR if the client always wants the auth time, put it in
			if (request.getExtensions().containsKey("max_age")
					|| (request.getExtensions().containsKey("idtoken")) // parse the ID Token claims (#473) -- for now assume it could be in there
					) {
				DateTime loginDate =new  DateTime(AuthorizationUtils.getUserInfo().getLastLoginTime());
				builder.claim("auth_time",  loginDate.getMillis()/1000);
			}
			
			String nonce = request.getRequestParameters().get("nonce");
			_logger.debug("getRequestParameters nonce {}",nonce);
			if (!Strings.isNullOrEmpty(nonce)) {
				builder.claim("nonce", nonce);
			}
			//add at_hash
			if(jwtSignerService != null) {
				SignedJWT signed = new SignedJWT(new JWSHeader(signingAlg), builder.build());
				Set<String> responseTypes = request.getResponseTypes();
	
				if (responseTypes.contains("token")) {
					// calculate the token hash
					Base64URL at_hash = IdTokenHashUtils.getAccessTokenHash(signingAlg, signed);
					builder.claim("at_hash", at_hash);
				}
				_logger.debug("idClaims {}",builder.build());
			}
			String idTokenString = "";
			if (StringUtils.isNotBlank(clientDetails.getSignature()) 
					&& !clientDetails.getSignature().equalsIgnoreCase("none")) {
				try {
					builder.claim("kid", signerKeyId);
					// signed ID token
					JWSHeader jwsHeader =   new JWSHeader.Builder(signingAlg).keyID(signerKeyId).build();
					JWT idToken = new SignedJWT(jwsHeader, builder.build());
					// sign it with the server's key
					jwtSignerService.signJwt((SignedJWT) idToken);
					idTokenString = idToken.serialize();
					_logger.debug("idToken {}",idTokenString);
				}catch(Exception e) {
					_logger.error("Couldn't create Jwt Signing Exception",e);
				}
			}else if (StringUtils.isNotBlank(clientDetails.getAlgorithm()) 
					&& !clientDetails.getAlgorithm().equalsIgnoreCase("none")) {
				try {
					String encryptionKeyId = clientDetails.getClientId()  + "_enc";
					DefaultJwtEncryptionAndDecryptionService jwtEncryptionService = 
								new DefaultJwtEncryptionAndDecryptionService(
										clientDetails.getAlgorithmKey(),
										encryptionKeyId,
										clientDetails.getAlgorithm()
									);
					Payload payload = builder.build().toPayload();
					// Example Request JWT encrypted with RSA-OAEP-256 and 128-bit AES/GCM
					//JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.RSA1_5, EncryptionMethod.A128GCM);
					JWEHeader jweHeader = new JWEHeader(
							jwtEncryptionService.getDefaultAlgorithm(clientDetails.getAlgorithm()), 
							jwtEncryptionService.parseEncryptionMethod(clientDetails.getEncryptionMethod())
						);
					JWEObject jweObject = new JWEObject(
						    new JWEHeader.Builder(jweHeader)
						        .contentType("JWT") // required to indicate nested JWT
						        .keyID(encryptionKeyId)
						        .build(),
						        payload);
					
					jwtEncryptionService.encryptJwt(jweObject);
					idTokenString = jweObject.serialize();
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
					_logger.error("Couldn't create Jwt Encryption Exception", e);
				}
			}else {
				//not need a PlainJWT idToken
				//JWT idToken = new PlainJWT(builder.build());
				//idTokenString = idToken.serialize();
			}
			
			accessToken = new DefaultOAuth2AccessToken(accessToken);
			if(StringUtils.isNotBlank(idTokenString)){
				accessToken.getAdditionalInformation().put("id_token", idTokenString);
			}
		}
		return accessToken;
	}

}
