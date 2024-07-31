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
 

package org.dromara.maxkey.authz.jwt.endpoint.adapter;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.crypto.jwt.encryption.service.impl.DefaultJwtEncryptionAndDecryptionService;
import org.dromara.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.dromara.maxkey.entity.apps.AppsJwtDetails;
import org.dromara.maxkey.web.WebConstants;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;

public class JwtAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(JwtAdapter.class);

	AppsJwtDetails jwtDetails;
	
	JWT jwtToken;
	
	JWEObject jweObject;
	
	JWTClaimsSet jwtClaims;
	
	public JwtAdapter() {

	}

	public JwtAdapter(AppsJwtDetails jwtDetails) {
		this.jwtDetails = jwtDetails;
	}

	@Override
	public Object generateInfo() {
		DateTime currentDateTime = DateTime.now();
		Date expirationTime = currentDateTime.plusSeconds(jwtDetails.getExpires()).toDate();
		_logger.debug("expiration Time : {}" , expirationTime);
		String subject = getValueByUserAttr(userInfo,jwtDetails.getSubject());
		_logger.trace("jwt subject : {}" , subject);
		
		jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(jwtDetails.getIssuer())
				.subject(subject)
				.audience(Arrays.asList(jwtDetails.getId()))
				.jwtID(UUID.randomUUID().toString())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.claim("email", userInfo.getWorkEmail())
				.claim("name", userInfo.getUsername())
				.claim("user_id", userInfo.getId())
				.claim("external_id", userInfo.getId())
				.claim("locale", userInfo.getLocale())
				.claim(WebConstants.ONLINE_TICKET_NAME, principal.getSessionId())
				.claim("kid", jwtDetails.getId()+ "_sig")
				.claim("institution", userInfo.getInstId())
				.build();
		
		_logger.trace("jwt Claims : {}" , jwtClaims);
		
		jwtToken = new PlainJWT(jwtClaims);
			
		return jwtToken;
	}

	@Override
	public Object sign(Object data,String signatureKey,String signature) {
		if(!jwtDetails.getSignature().equalsIgnoreCase("none")) {
			try {
				DefaultJwtSigningAndValidationService jwtSignerService = 
							new DefaultJwtSigningAndValidationService(
									jwtDetails.getSignatureKey(),
									jwtDetails.getId() + "_sig",
									jwtDetails.getSignature()
								);
				
				jwtToken = new SignedJWT(
								new JWSHeader(jwtSignerService.getDefaultSigningAlgorithm()), 
								jwtClaims
							);
				// sign it with the server's key
				jwtSignerService.signJwt((SignedJWT) jwtToken);
				return jwtToken;
			} catch (NoSuchAlgorithmException e) {
				_logger.error("NoSuchAlgorithmException", e);
			} catch (InvalidKeySpecException e) {
				_logger.error("InvalidKeySpecException", e);
			} catch (JOSEException e) {
				_logger.error("JOSEException", e);
			}
		}
		return data;
	}

	@Override
	public Object encrypt(Object data, String algorithmKey, String algorithm) {
		if(!jwtDetails.getAlgorithm().equalsIgnoreCase("none")) {
			try {
				DefaultJwtEncryptionAndDecryptionService jwtEncryptionService = 
							new DefaultJwtEncryptionAndDecryptionService(
									jwtDetails.getAlgorithmKey(),
									jwtDetails.getId()  + "_enc",
									jwtDetails.getAlgorithm()
								);

				Payload payload;
				if(jwtToken instanceof SignedJWT) {
					payload = ((SignedJWT)jwtToken).getPayload();
				}else {
					payload = ((PlainJWT)jwtToken).getPayload();
				}
				// Example Request JWT encrypted with RSA-OAEP-256 and 128-bit AES/GCM
				//JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.RSA1_5, EncryptionMethod.A128GCM);
				JWEHeader jweHeader = new JWEHeader(
						jwtEncryptionService.getDefaultAlgorithm(jwtDetails.getAlgorithm()), 
						jwtEncryptionService.parseEncryptionMethod(jwtDetails.getEncryptionMethod())
						);
				jweObject = new JWEObject(
					    new JWEHeader.Builder(jweHeader)
					        .contentType("JWT") // required to indicate nested JWT
					        .build(),
					        payload);
				
				jwtEncryptionService.encryptJwt(jweObject);
				
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
				_logger.error("Encrypt Exception", e);
			}
		}
		return data;
	}
	
	@Override
	public ModelAndView authorize(ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/jwt_sso_submint");
		modelAndView.addObject("action", jwtDetails.getRedirectUri());
		
		modelAndView.addObject("token",serialize());
		modelAndView.addObject("jwtName",jwtDetails.getJwtName());
		
		modelAndView.addObject("tokenType",jwtDetails.getTokenType().toLowerCase());
		
		return modelAndView;
	}

	public void setJwtDetails(AppsJwtDetails jwtDetails) {
		this.jwtDetails = jwtDetails;
	}

	@Override
	public String serialize() {
		String tokenString = "";
		if(jweObject != null) {
			tokenString = jweObject.serialize();
		}else {
			tokenString = jwtToken.serialize();
		}
		_logger.debug("jwt Token : {}" , tokenString);
		return tokenString;
	}

}
