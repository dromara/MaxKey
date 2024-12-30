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
 

package org.dromara.maxkey.web.contorller;

import java.util.HashMap;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.jwt.AuthJwt;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.entity.Institutions;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Crystal.Sea
 *
 */
@RestController
@RequestMapping(value = "/login")
public class LoginEntryPoint {
	private static Logger logger = LoggerFactory.getLogger(LoginEntryPoint.class);
	
	@Autowired
	AuthTokenService authTokenService;
	
	@Autowired
  	ApplicationConfig applicationConfig;
 	
	@Autowired
	AbstractAuthenticationProvider authenticationProvider ;
	
	/**
	 * init login
	 * @return
	 */
 	@GetMapping("/get")
	public Message<?> get() {
		logger.debug("/login.");
		
		HashMap<String , Object> model = new HashMap<String , Object>();
		Institutions inst = (Institutions)WebContext.getAttribute(WebConstants.CURRENT_INST);
		model.put("inst", inst);
		if(applicationConfig.getLoginConfig().isCaptcha()) {
			model.put("captcha", applicationConfig.getLoginConfig().getCaptchaType());
		}else {
			model.put("captcha", "NONE");
		}
		model.put("state", authTokenService.genRandomJwt());
		return new Message<HashMap<String , Object>>(model);
	}
 	
 	@PostMapping("/signin")
	public Message<?> signin( @RequestBody LoginCredential loginCredential) {
 		Message<AuthJwt> authJwtMessage = new Message<AuthJwt>(Message.FAIL);
 		if(authTokenService.validateJwtToken(loginCredential.getState())){
	 		Authentication  authentication  = authenticationProvider.authenticate(loginCredential);
	 		if(authentication != null) {
	 			AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
	 			authJwtMessage = new Message<AuthJwt>(authJwt);
	 		}else {//fail
 				String errorMsg = WebContext.getAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE) == null ? 
						  "" : WebContext.getAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE).toString();
				authJwtMessage.setMessage(Message.FAIL,errorMsg);
				logger.debug("login fail , message {}",errorMsg);
	 		}
 		}
 		return authJwtMessage;
 	}
 	
}
