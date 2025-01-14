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
 

package org.dromara.maxkey.authn.jwt;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.crypto.jwt.Hmac512Service;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebContext;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class AuthJwtService {
	private static final  Logger _logger = LoggerFactory.getLogger(AuthJwtService.class);
	
	Hmac512Service hmac512Service;
	
	/**
	 * JWT with Authentication
	 * @param authentication
	 * @return
	 */
	public String genJwt(Authentication authentication,String issuer,int expires) {
		SignPrincipal principal = ((SignPrincipal)authentication.getPrincipal());
		UserInfo userInfo = principal.getUserInfo();
		DateTime currentDateTime = DateTime.now();
		String subject = principal.getUsername();
		Date expirationTime = currentDateTime.plusSeconds(expires).toDate();
		_logger.trace("jwt subject : {} , expiration Time : {}" , subject,expirationTime);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(issuer)
				.subject(subject)
				.jwtID(principal.getSessionId())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.claim("locale", userInfo.getLocale())
				.claim("kid", Hmac512Service.MXK_AUTH_JWK)
				.claim("institution", userInfo.getInstId())
				.build();
		
		return signedJWT(jwtClaims);
	}
	
	/**
	 * JWT with subject
	 * @param subject subject
	 * @return
	 */
	public String genJwt(String subject,String issuer,int expires) {
		DateTime currentDateTime = DateTime.now();
		Date expirationTime = currentDateTime.plusSeconds(expires).toDate();
		_logger.trace("jwt subject : {} , expiration Time : {}" , subject,expirationTime);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(issuer)
				.subject(subject)
				.jwtID(WebContext.genId())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.build();
		 
		return signedJWT(jwtClaims);
	}
	
	/**
	 * Random JWT
	 * @return
	 */
	public String genRandomJwt(int expires) {
		Date expirationTime = DateTime.now().plusSeconds(expires).toDate();
		_logger.trace("expiration Time : {}" , expirationTime);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.jwtID(WebContext.genId())
				.expirationTime(expirationTime)
				.build();
		
		return signedJWT(jwtClaims);
	}
	
	public String signedJWT(JWTClaimsSet jwtClaims) {
		_logger.trace("jwt Claims : {}" , jwtClaims);
		SignedJWT  jwtToken = new SignedJWT(
				new JWSHeader(JWSAlgorithm.HS512), 
				jwtClaims);
		return hmac512Service.sign(jwtToken.getPayload());
	}
	
	/**
	 * Verify with HMAC512 and check ExpirationTime
	 * 
	 * @param authToken
	 * @return true or false
	 */
	public boolean validateJwtToken(String authToken) {
		if(StringUtils.isNotBlank(authToken) && authToken.length() > 20) {
			try {
				JWTClaimsSet claims = resolve(authToken);
				boolean isExpiration = claims.getExpirationTime().after(DateTime.now().toDate());
				boolean isVerify = hmac512Service.verify(authToken);
				boolean isValidate = isVerify && isExpiration;
				_logger.trace("JWT Validate {} " , isValidate);
				_logger.debug("HMAC Verify {} , now {} , ExpirationTime {} , is not Expiration : {}" , 
						isVerify,DateTime.now().toDate(),claims.getExpirationTime(),isExpiration);
				return isValidate;
			} catch (ParseException e) {
				_logger.error("authToken {}",authToken);
				_logger.error("ParseException ",e);
			}
		}
		return false;
	}
	
	public  JWTClaimsSet resolve(String authToken) throws ParseException {
		SignedJWT signedJWT = SignedJWT.parse(authToken);
		_logger.trace("jwt Claims : {}" , signedJWT.getJWTClaimsSet());
		return signedJWT.getJWTClaimsSet();
	}
	
	public String resolveJWTID(String authToken) throws ParseException {
		JWTClaimsSet claims = resolve(authToken); 
		return claims.getJWTID();
	}
}
