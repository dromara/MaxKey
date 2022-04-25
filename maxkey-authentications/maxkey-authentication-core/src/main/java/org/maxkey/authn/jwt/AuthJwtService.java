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

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.configuration.AuthJwkConfig;
import org.maxkey.crypto.jwt.HMAC512Service;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.MomentaryService;
import org.maxkey.web.WebContext;
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
	
	CongressService congressService;
	
	MomentaryService momentaryService;

	public AuthJwtService(AuthJwkConfig authJwkConfig) throws JOSEException {
		this.authJwkConfig = authJwkConfig;
		
		this.hmac512Service = new HMAC512Service(authJwkConfig.getSecret());
	}
	
	public AuthJwtService(AuthJwkConfig authJwkConfig,CongressService congressService,MomentaryService momentaryService) throws JOSEException {
		this.authJwkConfig = authJwkConfig;
		
		this.congressService = congressService;
		
		this.momentaryService = momentaryService;
		
		this.hmac512Service = new HMAC512Service(authJwkConfig.getSecret());
		
		
	}
	
	/**
	 * create AuthJwt use Authentication JWT
	 * @param authentication
	 * @return AuthJwt
	 */
	public AuthJwt genAuthJwt(Authentication authentication) {
		if(authentication != null) {
			return new AuthJwt(genJwt(authentication), authentication);
		}
		return null;
	}
	
	/**
	 * JWT with Authentication
	 * @param authentication
	 * @return
	 */
	public String genJwt(Authentication authentication) {
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
				.jwtID(principal.getOnlineTicket().getTicketId())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.claim("locale", userInfo.getLocale())
				.claim("kid", HMAC512Service.MXK_AUTH_JWK)
				.claim("institution", userInfo.getInstId())
				.build();
		
		return signedJWT(jwtClaims);
	}
	
	/**
	 * JWT with subject
	 * @param subject subject
	 * @return
	 */
	public String genJwt(String subject) {
		DateTime currentDateTime = DateTime.now();
		Date expirationTime = currentDateTime.plusSeconds(authJwkConfig.getExpires()).toDate();
		_logger.debug("expiration Time : {}" , expirationTime);
		_logger.trace("jwt subject : {}" , subject);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(authJwkConfig.getIssuer())
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
	public String genJwt() {
		DateTime currentDateTime = DateTime.now();
		Date expirationTime = currentDateTime.plusSeconds(authJwkConfig.getExpires()).toDate();
		_logger.debug("expiration Time : {}" , expirationTime);
		
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
	
	public boolean validateJwtToken(String authToken) {
		return hmac512Service.verify(authToken);
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
	
	public String createCongress(Authentication  authentication) {
		String congress = WebContext.genId();
		congressService.store(
				congress, 
				new AuthJwt(
						genJwt(authentication), 
						authentication)
			);
		return congress;
	}
	
	public AuthJwt consumeCongress(String congress) {
		AuthJwt authJwt = congressService.consume(congress);
		return authJwt;
	}
	
	public boolean validateCaptcha(String state,String captcha) {
    	try {
			String jwtId = resolveJWTID(state);
			if(StringUtils.isNotBlank(jwtId) &&StringUtils.isNotBlank(captcha)) {
				Object momentaryCaptcha = momentaryService.get("", jwtId);
		        _logger.debug("captcha : {}, momentary Captcha : {}" ,captcha, momentaryCaptcha);
		        if (!StringUtils.isBlank(captcha) && captcha.equals(momentaryCaptcha.toString())) {
		        	momentaryService.remove("", jwtId);
		        	return true;
		        }
			}
		} catch (ParseException e) {
			 _logger.debug("Exception ",e);
		}
    	 return false;
    }
	
}
