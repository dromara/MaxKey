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
 

package org.dromara.maxkey.web.contorller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsRegex;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.sms.SmsOtpAuthnService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletException;


@RestController
@RequestMapping(value={"/signup"})
public class RegisterController {
	private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	AuthTokenService authTokenService;
	
	@Autowired 
  	ApplicationConfig applicationConfig;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
    SmsOtpAuthnService smsOtpAuthnService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
 	
	@GetMapping(value = { "/produceOtp" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> produceOtp(@RequestParam String mobile) {
        logger.debug("/signup/produceOtp Mobile {}: " ,mobile);
 
    	logger.debug("Mobile Regex matches {}",ConstsRegex.MOBILE_PATTERN.matcher(mobile).matches());
    	if(StringUtils.isNotBlank(mobile) && ConstsRegex.MOBILE_PATTERN.matcher(mobile).matches()) {
    		UserInfo userInfo = new UserInfo();
    		userInfo.setUsername(mobile);
    		userInfo.setMobile(mobile);
        	AbstractOtpAuthn smsOtpAuthn = smsOtpAuthnService.getByInstId(WebContext.getInst().getId());
        	smsOtpAuthn.produce(userInfo);
        	return new Message<UserInfo>(userInfo);
        }
            
        return new Message<UserInfo>(Message.FAIL);
    }
	  
	//直接注册
 	@PostMapping(value={"/register"})
	public Message<?> register(@ModelAttribute UserInfo userInfo , @RequestParam String captcha) throws ServletException, IOException {
 		UserInfo validateUserInfo = new UserInfo();
 		validateUserInfo.setUsername(userInfo.getMobile());
 		validateUserInfo.setMobile(userInfo.getMobile());
 		AbstractOtpAuthn smsOtpAuthn = smsOtpAuthnService.getByInstId(WebContext.getInst().getId());
 		if (smsOtpAuthn !=null 
        				&& smsOtpAuthn.validate(validateUserInfo, captcha)){
	 		UserInfo temp = userInfoService.findByEmailMobile(userInfo.getEmail());
	 		
	 		if(temp != null) {
	 			return new Message<UserInfo>(Message.FAIL);
	 		}
	 		
	 		temp = userInfoService.findByUsername(userInfo.getUsername());
	 		if(temp != null) {
	 			return new Message<UserInfo>(Message.FAIL);
	 		}
	 		
	 		//default InstId
	 		if(StringUtils.isEmpty(userInfo.getInstId())) {
				userInfo.setInstId("1");
			}
	 		String password = userInfo.getPassword();
	 		userInfo.setDecipherable(PasswordReciprocal.getInstance().encode(password));
	 		password = passwordEncoder.encode(password );
			userInfo.setPassword(password);
	 		userInfo.setStatus(ConstsStatus.INACTIVE);
	 		
	 		if(userInfoService.insert(userInfo)) {
	 			return new Message<UserInfo>();
	 		}
 		}
 		return new Message<UserInfo>(Message.FAIL);
 	}

}
