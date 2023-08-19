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

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.configuration.EmailConfig;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.PasswordPolicy;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.MailOtpAuthnService;
import org.dromara.maxkey.password.sms.SmsOtpAuthnService;
import org.dromara.maxkey.persistence.service.PasswordPolicyService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/forgotpassword" })
public class ForgotPasswordContorller {
    private static Logger logger = LoggerFactory.getLogger(ForgotPasswordContorller.class);

    Pattern emailRegex = Pattern.compile(
            "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    
    Pattern mobileRegex = Pattern.compile(
            "^[1][3,4,5,7,8][0-9]{9}$");
    
    @Autowired
    EmailConfig emailConfig;
    
    public class ForgotType{
        public final static int NOTFOUND = 1;
        public final static int EMAIL = 2;
        public final static int MOBILE = 3;
        public final static int CAPTCHAERROR = 4;
    }
    
    public class PasswordResetResult{
        public final static int SUCCESS = 1;
        public final static int CAPTCHAERROR = 2;
        public final static int PASSWORDERROR = 3;
    }
    
    @Autowired
	AuthTokenService authTokenService;
    
    @Autowired
    UserInfoService userInfoService;
    
    @Autowired
    MailOtpAuthnService mailOtpAuthnService;
    
    @Autowired
    SmsOtpAuthnService smsOtpAuthnService;


	@Autowired
	private PasswordPolicyService passwordPolicyService;

	@RequestMapping(value={"/passwordpolicy"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> passwordpolicy(){
		PasswordPolicy passwordPolicy = passwordPolicyService.get(WebContext.getInst().getId());
		//构建密码强度说明
		passwordPolicy.buildMessage();
		return new Message<PasswordPolicy>(passwordPolicy).buildResponse();
	}


	@ResponseBody
	@RequestMapping(value = { "/validateCaptcha" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> validateCaptcha(
			@RequestParam String userId,
			@RequestParam String state,
			@RequestParam String captcha,
			@RequestParam String otpCaptcha) {
		logger.debug("forgotpassword  /forgotpassword/validateCaptcha.");
		logger.debug(" userId {}: " ,userId);
		UserInfo userInfo = userInfoService.get(userId);
		if(userInfo != null) {
			AbstractOtpAuthn smsOtpAuthn = smsOtpAuthnService.getByInstId(userInfo.getInstId());
			if (otpCaptcha == null || !smsOtpAuthn.validate(userInfo, otpCaptcha)) {
				return new Message<ChangePassword>(Message.FAIL).buildResponse();
			}
			return new Message<ChangePassword>(Message.SUCCESS).buildResponse();
		}
		return new Message<ChangePassword>(Message.FAIL).buildResponse();
	}


	@ResponseBody
	@RequestMapping(value = { "/produceOtp" }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> produceOtp(
    			@RequestParam String mobile,
    			@RequestParam String state,
    			@RequestParam String captcha) {
        logger.debug("forgotpassword  /forgotpassword/produceOtp.");
        logger.debug(" Mobile {}: " ,mobile);
        if (!authTokenService.validateCaptcha(state,captcha)) {    
        	logger.debug("login captcha valid error.");
        	return new Message<ChangePassword>(Message.FAIL).buildResponse();
        }
        
    	ChangePassword change = null;
    	logger.debug("Mobile Regex matches {}",mobileRegex.matcher(mobile).matches());
    	if(StringUtils.isNotBlank(mobile) && mobileRegex.matcher(mobile).matches()) {
    		UserInfo userInfo = userInfoService.findByEmailMobile(mobile);
    		if(userInfo != null) {
	    		change = new ChangePassword(userInfo);
	            change.clearPassword();
	        	AbstractOtpAuthn smsOtpAuthn = smsOtpAuthnService.getByInstId(userInfo.getInstId());
	        	smsOtpAuthn.produce(userInfo);
	        	return new Message<ChangePassword>(change).buildResponse();
    		}
        }
            
        return new Message<ChangePassword>(Message.FAIL).buildResponse();
    }
    
    @ResponseBody
	@RequestMapping(value = { "/produceEmailOtp" }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> produceEmailOtp(
    			@RequestParam String email,
    			@RequestParam String state,
    			@RequestParam String captcha) {
        logger.debug("/forgotpassword/produceEmailOtp Email {} : " , email);
        if (!authTokenService.validateCaptcha(state,captcha)) {
        	logger.debug("captcha valid error.");
        	return new Message<ChangePassword>(Message.FAIL).buildResponse();
        }
        
    	ChangePassword change = null;
    	if(StringUtils.isNotBlank(email) && emailRegex.matcher(email).matches()) {
    		UserInfo userInfo = userInfoService.findByEmailMobile(email);
    		if(userInfo != null) {
	    		change = new ChangePassword(userInfo);
	            change.clearPassword();
	            AbstractOtpAuthn mailOtpAuthn =  mailOtpAuthnService.getMailOtpAuthn(userInfo.getInstId());
	            mailOtpAuthn.produce(userInfo);
	        	return new Message<ChangePassword>(change).buildResponse();
    		}
    	}
        return new Message<ChangePassword>(Message.FAIL).buildResponse();
    }

    @RequestMapping(value = { "/setpassword" })
    public ResponseEntity<?> setPassWord(
    					@ModelAttribute ChangePassword changePassword,
    					@RequestParam String forgotType,
                        @RequestParam String otpCaptcha,
                        @RequestParam String state) {
        logger.debug("forgotPassword  /forgotpassword/setpassword.");
        if (StringUtils.isNotBlank(changePassword.getPassword() )
        		&& changePassword.getPassword().equals(changePassword.getConfirmPassword())) {
            UserInfo loadedUserInfo = userInfoService.get(changePassword.getUserId());
            if(loadedUserInfo != null) {
	            AbstractOtpAuthn smsOtpAuthn = smsOtpAuthnService.getByInstId(loadedUserInfo.getInstId());
	            AbstractOtpAuthn mailOtpAuthn =  mailOtpAuthnService.getMailOtpAuthn(loadedUserInfo.getInstId());
	            if (
	            		(forgotType.equalsIgnoreCase("email") 
	            				&& mailOtpAuthn !=null 
	            				&& mailOtpAuthn.validate(loadedUserInfo, otpCaptcha)) 
	            		||
	            		(forgotType.equalsIgnoreCase("mobile") 
	            				&& smsOtpAuthn !=null 
	            				&& smsOtpAuthn.validate(loadedUserInfo, otpCaptcha))
	               ) {
	            	
	                if(userInfoService.changePassword(changePassword,true)) {
	                	return new Message<ChangePassword>(Message.SUCCESS).buildResponse();
	                }else {
	                	return new Message<ChangePassword>(Message.FAIL).buildResponse();
	                }
	            } else {
	            	return new Message<ChangePassword>(Message.FAIL).buildResponse();
	            }
	        } 
        }
        return new Message<ChangePassword>(Message.FAIL).buildResponse();
    }
}
