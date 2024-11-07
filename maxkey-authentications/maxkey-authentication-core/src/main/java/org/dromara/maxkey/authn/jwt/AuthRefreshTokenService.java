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

import org.dromara.maxkey.configuration.AuthJwkConfig;
import org.dromara.maxkey.crypto.jwt.Hmac512Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.nimbusds.jose.JOSEException;

public class AuthRefreshTokenService extends AuthJwtService{
	private static final  Logger _logger = LoggerFactory.getLogger(AuthRefreshTokenService.class);
	
	AuthJwkConfig authJwkConfig;
	
	public AuthRefreshTokenService(AuthJwkConfig authJwkConfig) throws JOSEException {
		this.authJwkConfig = authJwkConfig;
		
		this.hmac512Service = new Hmac512Service(authJwkConfig.getRefreshSecret());
	}
	
	/**
	 * JWT Refresh Token with Authentication
	 * @param authentication
	 * @return
	 */
	public String genRefreshToken(Authentication authentication) {
		_logger.trace("generate Refresh JWT Token");
		return genJwt( 
				 authentication,
				 authJwkConfig.getIssuer(),
				 authJwkConfig.getRefreshExpires());
	}
}
