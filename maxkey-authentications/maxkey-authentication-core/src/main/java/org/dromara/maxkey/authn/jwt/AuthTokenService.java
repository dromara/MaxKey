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
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.configuration.AuthJwkConfig;
import org.dromara.maxkey.crypto.jwt.Hmac512Service;
import org.dromara.maxkey.persistence.cache.MomentaryService;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.nimbusds.jose.JOSEException;

public class AuthTokenService  extends AuthJwtService{
	private static final  Logger _logger = LoggerFactory.getLogger(AuthTokenService.class);
	
	AuthJwkConfig authJwkConfig;
	
	CongressService congressService;
	
	MomentaryService momentaryService;
	
	AuthRefreshTokenService refreshTokenService;
	
	public AuthTokenService(
				AuthJwkConfig authJwkConfig,
				CongressService congressService,
				MomentaryService momentaryService,
				AuthRefreshTokenService refreshTokenService) throws JOSEException {
		
		this.authJwkConfig = authJwkConfig;
		
		this.congressService = congressService;
		
		this.momentaryService = momentaryService;
		
		this.refreshTokenService = refreshTokenService;
		
		this.hmac512Service = new Hmac512Service(authJwkConfig.getSecret());
		
	}
	
	/**
	 * create AuthJwt use Authentication JWT
	 * @param authentication
	 * @return AuthJwt
	 */
	public AuthJwt genAuthJwt(Authentication authentication) {
		if(authentication != null) {
			String refreshToken = refreshTokenService.genRefreshToken(authentication);
			_logger.trace("generate JWT Token");
			String accessToken = genJwt(authentication);
			return new AuthJwt(
						accessToken,
						authentication,
						authJwkConfig.getExpires(),
						refreshToken);
		}
		return null;
	}
	
	public String genJwt(Authentication authentication) {
		return genJwt(
					authentication,
					authJwkConfig.getIssuer(),
					authJwkConfig.getExpires());
	}

	
	/**
	 * JWT with subject
	 * @param subject subject
	 * @return
	 */
	public String genJwt(String subject) {
		return genJwt(subject,authJwkConfig.getIssuer(),authJwkConfig.getExpires());
	}
	
	/**
	 * Random JWT
	 * @return
	 */
	public String genRandomJwt() {
		return genRandomJwt(authJwkConfig.getExpires());
	}
	
	public String createCongress(Authentication  authentication) {
		String congress = WebContext.genId();
		String refreshToken = refreshTokenService.genRefreshToken(authentication);
		congressService.store(
				congress, 
				new AuthJwt(
						genJwt(authentication), 
						authentication,
						authJwkConfig.getExpires(),
						refreshToken)
			);
		return congress;
	}
	
	public AuthJwt consumeCongress(String congress) {
		return congressService.consume(congress);
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
