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
 
package org.dromara.maxkey.authn.support.cas;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/login")
public class HttpTrustEntryPoint {
	private static final Logger _logger = LoggerFactory.getLogger(HttpTrustEntryPoint.class);
    
	@Autowired
  	ApplicationConfig applicationConfig;
    
  	@Autowired
    AbstractAuthenticationProvider authenticationProvider ;
  	
  	@Autowired
	AuthTokenService authTokenService;
  	
  	 @Autowired
 	CasTrustLoginService casTrustLoginService;
 	
 	@GetMapping(value={"/trust"}, produces = {MediaType.APPLICATION_JSON_VALUE})
 	public Message<AuthJwt> trust(@RequestParam(value = WebConstants.CAS_TICKET_PARAMETER, required = true) String ticket) {
 		try {
 			//for ticket Login
 			_logger.debug("ticket : {}" , ticket);
 	
 			 String username = casTrustLoginService.buildLoginUser(ticket);
 			 
 			 if(username != null) {
 				 LoginCredential loginCredential =new LoginCredential(username,"",ConstsLoginType.CAS);
 				 Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);
 				_logger.debug("CAS Logined in , username {}" , username);
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
	
}
