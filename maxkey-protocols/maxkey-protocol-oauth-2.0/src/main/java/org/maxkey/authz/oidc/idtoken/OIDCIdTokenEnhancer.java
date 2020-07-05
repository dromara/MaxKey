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
package org.maxkey.authz.oidc.idtoken;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.authz.oauth2.common.DefaultOAuth2AccessToken;
import org.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.authz.oauth2.provider.OAuth2Request;
import org.maxkey.authz.oauth2.provider.token.TokenEnhancer;
import org.maxkey.configuration.oidc.OIDCProviderMetadata;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.jwt.encryption.service.JwtEncryptionAndDecryptionService;
import org.maxkey.crypto.jwt.encryption.service.impl.RecipientJwtEncryptionAndDecryptionServiceBuilder;
import org.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;
import org.maxkey.crypto.jwt.signer.service.impl.SymmetricSigningAndValidationServiceBuilder;
import org.maxkey.domain.apps.oauth2.provider.ClientDetails;
import org.maxkey.web.WebContext;

import com.nimbusds.jose.util.Base64URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;

/**
 * @author Crystal.Sea
 *
 */
public class OIDCIdTokenEnhancer implements TokenEnhancer {
	private final static Logger logger = LoggerFactory.getLogger(OIDCIdTokenEnhancer.class);
	
	public  final static String ID_TOKEN_SCOPE="openid";

	private OIDCProviderMetadata providerMetadata;
	
	private JwtSigningAndValidationService jwtSignerService;
	
	private JwtEncryptionAndDecryptionService jwtEnDecryptionService; 

	private ClientDetailsService clientDetailsService;
	
	private SymmetricSigningAndValidationServiceBuilder symmetricJwtSignerServiceBuilder
								=new SymmetricSigningAndValidationServiceBuilder();
	
	private RecipientJwtEncryptionAndDecryptionServiceBuilder recipientJwtEnDecryptionServiceBuilder
					=new RecipientJwtEncryptionAndDecryptionServiceBuilder();
	
	public void setProviderMetadata(OIDCProviderMetadata providerMetadata) {
		this.providerMetadata = providerMetadata;
	}

	public void setJwtSignerService(JwtSigningAndValidationService jwtSignerService) {
		this.jwtSignerService = jwtSignerService;
	}

	public void setJwtEnDecryptionService(
			JwtEncryptionAndDecryptionService jwtEnDecryptionService) {
		this.jwtEnDecryptionService = jwtEnDecryptionService;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		OAuth2Request  request=authentication.getOAuth2Request();
		if (request.getScope().contains(ID_TOKEN_SCOPE)) {//Enhance for OpenID Connect
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authentication.getOAuth2Request().getClientId());
			
			JWTClaimsSet.Builder builder=new JWTClaimsSet.Builder();
			builder.subject(authentication.getName())
		      .expirationTime(accessToken.getExpiration())
		      .claim(providerMetadata.getIssuer(), true)
		      .issueTime(new Date())
		      .audience(Arrays.asList(authentication.getOAuth2Request().getClientId()))
		      .jwtID(UUID.randomUUID().toString());
			
			/**
			 * https://self-issued.me
			 * @see http://openid.net/specs/openid-connect-core-1_0.html#SelfIssuedDiscovery
			 *     7.  Self-Issued OpenID Provider
			 */
			
			if(providerMetadata.getIssuer().equalsIgnoreCase("https://self-issued.me")){
				builder.claim("sub_jwk", jwtSignerService.getAllPublicKeys().get(jwtSignerService.getDefaultSignerKeyId()));
			}
			
			// if the auth time claim was explicitly requested OR if the client always wants the auth time, put it in
			if (request.getExtensions().containsKey("max_age")
					|| (request.getExtensions().containsKey("idtoken")) // TODO: parse the ID Token claims (#473) -- for now assume it could be in there
					) {
				DateTime loginDate=DateTime.parse(WebContext.getUserInfo().getLastLoginTime(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
				builder.claim("auth_time",  loginDate.getMillis()/ 1000);
			}
			
			String nonce = (String)request.getExtensions().get("nonce");
			if (!Strings.isNullOrEmpty(nonce)) {
				builder.claim("nonce", nonce);
			}
			
			JWSAlgorithm signingAlg = jwtSignerService.getDefaultSigningAlgorithm();
			SignedJWT signed = new SignedJWT(new JWSHeader(signingAlg), builder.build());
			Set<String> responseTypes = request.getResponseTypes();

			if (responseTypes.contains("token")) {
				// calculate the token hash
				Base64URL at_hash = IdTokenHashUtils.getAccessTokenHash(signingAlg, signed);
				builder.claim("at_hash", at_hash);
			}
			logger.debug("idClaims "+builder.build());
			
			JWT idToken=null;
			if (clientDetails.getIdTokenEncryptedAlgorithm() != null && !clientDetails.getIdTokenEncryptedAlgorithm().equals("none")
					&& clientDetails.getIdTokenEncryptionMethod() != null && !clientDetails.getIdTokenEncryptionMethod().equals("none")
					&&clientDetails.getJwksUri()!=null&&clientDetails.getJwksUri().length()>4) {

				JwtEncryptionAndDecryptionService recipientJwtEnDecryptionService =
						recipientJwtEnDecryptionServiceBuilder.serviceBuilder(clientDetails.getJwksUri());
				
				if (recipientJwtEnDecryptionService != null) {
					JWEAlgorithm jweAlgorithm=new JWEAlgorithm(clientDetails.getIdTokenEncryptedAlgorithm());
					EncryptionMethod encryptionMethod=new EncryptionMethod(clientDetails.getIdTokenEncryptionMethod());
					EncryptedJWT encryptedJWT = new EncryptedJWT(new JWEHeader(jweAlgorithm, encryptionMethod), builder.build());
					recipientJwtEnDecryptionService.encryptJwt(encryptedJWT);
					idToken=encryptedJWT;
				}else{
					logger.error("Couldn't create Jwt Encryption Service");
				}
			} else {
				if (signingAlg==null||signingAlg.equals("none")) {
					// unsigned ID token
					idToken = new PlainJWT(builder.build());
				} else {
					// signed ID token
					if (signingAlg.equals(JWSAlgorithm.HS256)
							|| signingAlg.equals(JWSAlgorithm.HS384)
							|| signingAlg.equals(JWSAlgorithm.HS512)) {
						// sign it with the client's secret
						String client_secret=ReciprocalUtils.decoder(clientDetails.getClientSecret());
						
						JwtSigningAndValidationService symmetricJwtSignerService =symmetricJwtSignerServiceBuilder.serviceBuilder(client_secret);
						if(symmetricJwtSignerService!=null){
							builder.claim("kid", "SYMMETRIC-KEY");
							idToken = new SignedJWT(new JWSHeader(signingAlg), builder.build());
							symmetricJwtSignerService.signJwt((SignedJWT) idToken);
						}else {
							logger.error("Couldn't create symmetric validator for client " + clientDetails.getClientId() + " without a client secret");
						}
					} else {
						builder.claim("kid", jwtSignerService.getDefaultSignerKeyId());
						idToken = new SignedJWT(new JWSHeader(signingAlg), builder.build());
						// sign it with the server's key
						jwtSignerService.signJwt((SignedJWT) idToken);
					}
				}
			}
			logger.debug("idToken "+idToken);
			
			accessToken = new DefaultOAuth2AccessToken(accessToken);
			if(idToken!=null){
				accessToken.getAdditionalInformation().put("id_token", idToken.serialize());
			}
		}
		return accessToken;
	}

}
