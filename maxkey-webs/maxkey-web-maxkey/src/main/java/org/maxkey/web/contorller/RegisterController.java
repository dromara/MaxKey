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
 

package org.maxkey.web.contorller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.password.onetimepwd.OtpAuthnService;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/signup"})
public class RegisterController {
	private static Logger _logger = LoggerFactory.getLogger(RegisterController.class);
	
	Pattern mobileRegex = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
	
	@Autowired
	AuthJwtService authJwtService;
	
	@Autowired 
  	protected ApplicationConfig applicationConfig;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
    @Qualifier("otpAuthnService")
    OtpAuthnService otpAuthnService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
 	
	@ResponseBody
	@RequestMapping(value = { "/produceOtp" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> produceOtp(
	    			@RequestParam String mobile) {
        _logger.debug("/signup/produceOtp Mobile {}: " ,mobile);
 
    	_logger.debug("Mobile Regex matches {}",mobileRegex.matcher(mobile).matches());
    	if(StringUtils.isNotBlank(mobile) && mobileRegex.matcher(mobile).matches()) {
    		UserInfo userInfo = new UserInfo();
    		userInfo.setUsername(mobile);
    		userInfo.setMobile(mobile);
        	AbstractOtpAuthn smsOtpAuthn = otpAuthnService.getByInstId(WebContext.getInst().getId());
        	smsOtpAuthn.produce(userInfo);
        	return new Message<UserInfo>(userInfo).buildResponse();
        }
            
        return new Message<UserInfo>(Message.FAIL).buildResponse();
    }
	  
	//直接注册
 	@RequestMapping(value={"/register"})
 	@ResponseBody
	public ResponseEntity<?> register(
				@ModelAttribute UserInfo userInfo,
				@RequestParam String captcha) throws ServletException, IOException {
 		UserInfo validateUserInfo = new UserInfo();
 		validateUserInfo.setUsername(userInfo.getMobile());
 		validateUserInfo.setMobile(userInfo.getMobile());
 		AbstractOtpAuthn smsOtpAuthn = otpAuthnService.getByInstId(WebContext.getInst().getId());
 		if (smsOtpAuthn !=null 
        				&& smsOtpAuthn.validate(validateUserInfo, captcha)){
	 		UserInfo temp = userInfoService.findByEmailMobile(userInfo.getEmail());
	 		
	 		if(temp != null) {
	 			return new Message<UserInfo>(Message.FAIL).buildResponse();
	 		}
	 		
	 		temp = userInfoService.findByUsername(userInfo.getUsername());
	 		if(temp != null) {
	 			return new Message<UserInfo>(Message.FAIL).buildResponse();
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
	 			return new Message<UserInfo>().buildResponse();
	 		}
 		}
 		return new Message<UserInfo>(Message.FAIL).buildResponse();
 	}

}
