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
 
package org.dromara.maxkey.authn.support.jwt;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.jwt.AuthJwt;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jwt.SignedJWT;


@RestController
@RequestMapping(value = "/login")
public class HttpJwtEntryPoint {
	private static final Logger _logger = LoggerFactory.getLogger(HttpJwtEntryPoint.class);
    
	@Autowired
  	ApplicationConfig applicationConfig;
    
  	@Autowired
    AbstractAuthenticationProvider authenticationProvider ;
  	
  	@Autowired
	AuthTokenService authTokenService;
  	
    @Autowired
	JwtLoginService jwtLoginService;
	
	@RequestMapping(value={"/jwt"}, produces = {MediaType.APPLICATION_JSON_VALUE},method={RequestMethod.GET,RequestMethod.POST})
	public Message<AuthJwt> jwt(@RequestParam(value = WebConstants.JWT_TOKEN_PARAMETER, required = true) String jwt) {
		try {
			//for jwt Login
			 _logger.debug("jwt : {}" , jwt);
	
			 SignedJWT signedJWT = jwtLoginService.jwtTokenValidation(jwt);
			 
			 if(signedJWT != null) {
				 String username =signedJWT.getJWTClaimsSet().getSubject();
				 LoginCredential loginCredential =new LoginCredential(username,"",ConstsLoginType.JWT);
				 Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);
				 _logger.debug("JWT Logined in , username {}" , username);
				 AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
		 		 return new Message<>(authJwt);
			 }
		}catch(Exception e) {
			_logger.error("Exception ",e);
		}
		
		 return new Message<>(Message.FAIL);
	}
	
	/**
	 * trust same HS512
	 * @param jwt
	 * @return
	 */
	@RequestMapping(value={"/jwt/trust"}, produces = {MediaType.APPLICATION_JSON_VALUE},method={RequestMethod.GET,RequestMethod.POST})
	public Message<AuthJwt> jwtTrust(@RequestParam(value = WebConstants.JWT_TOKEN_PARAMETER, required = true) String jwt) {
		try {
			//for jwt Login
			 _logger.debug("jwt : {}" , jwt);

			 if(authTokenService.validateJwtToken(jwt)) {
				 String username =authTokenService.resolve(jwt).getSubject();
				 LoginCredential loginCredential =new LoginCredential(username,"",ConstsLoginType.JWT);
				 Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);
				 _logger.debug("JWT Logined in , username {}" , username);
				 AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
		 		 return new Message<>(authJwt);
			 }
		}catch(Exception e) {
			_logger.error("Exception ",e);
		}
		
		 return new Message<>(Message.FAIL);
	}


	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public void setAuthenticationProvider(AbstractAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	public void setJwtLoginService(JwtLoginService jwtLoginService) {
		this.jwtLoginService = jwtLoginService;
	}
	
}
