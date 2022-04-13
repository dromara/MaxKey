/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.authn.jwt;

import java.text.ParseException;
import java.util.Date;
import org.joda.time.DateTime;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.configuration.AuthJwkConfig;
import org.maxkey.crypto.jwt.HMAC512Service;
import org.maxkey.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class AuthJwtService {
	private static final  Logger _logger = 
            LoggerFactory.getLogger(AuthJwtService.class);
	
	HMAC512Service hmac512Service;
	
	AuthJwkConfig authJwkConfig;

	public AuthJwtService(AuthJwkConfig authJwkConfig) throws JOSEException {
		this.authJwkConfig = authJwkConfig;
		
		this.hmac512Service = new HMAC512Service(authJwkConfig.getSecret());
	}
	
	public String generateToken(Authentication authentication) {
		String token = "";
		SigninPrincipal principal = ((SigninPrincipal)authentication.getPrincipal());
		UserInfo userInfo = principal.getUserInfo();
		DateTime currentDateTime = DateTime.now();
		Date expirationTime = currentDateTime.plusSeconds(authJwkConfig.getExpires()).toDate();
		_logger.debug("expiration Time : {}" , expirationTime);
		String subject = principal.getUsername();
		_logger.trace("jwt subject : {}" , subject);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(authJwkConfig.getIssuer())
				.subject(subject)
				.jwtID(principal.getOnlineTicket().getFormattedTicketId())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.claim("locale", userInfo.getLocale())
				.claim("kid", HMAC512Service.MXK_AUTH_JWK)
				.claim("institution", userInfo.getInstId())
				.build();
		
		_logger.trace("jwt Claims : {}" , jwtClaims);
		
		SignedJWT  jwtToken = new SignedJWT(
								new JWSHeader(JWSAlgorithm.HS512), 
								jwtClaims);
		
		token = hmac512Service.sign(jwtToken.getPayload());
		
		return token ;
	}
	
	public boolean validateJwtToken(String authToken) {
		return hmac512Service.verify(authToken);
	}
	
	public  JWTClaimsSet resolve(String authToken) throws ParseException {
		SignedJWT signedJWT = SignedJWT.parse(authToken);
		_logger.trace("jwt Claims : {}" , signedJWT.getJWTClaimsSet());
		return signedJWT.getJWTClaimsSet();
	}
	
	public String resolveTicket(String authToken) throws ParseException {
		JWTClaimsSet claims = resolve(authToken); 
		return claims.getJWTID();
	}
	
}
