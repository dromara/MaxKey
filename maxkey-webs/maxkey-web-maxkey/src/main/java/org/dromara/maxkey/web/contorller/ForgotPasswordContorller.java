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

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.configuration.EmailConfig;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsRegex;
import org.dromara.maxkey.constants.ConstsAct;
import org.dromara.maxkey.constants.ConstsActResult;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.cnf.CnfPasswordPolicy;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.MailOtpAuthnService;
import org.dromara.maxkey.password.sms.SmsOtpAuthnService;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.CnfPasswordPolicyService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = { "/forgotpassword" })
public class ForgotPasswordContorller {
    private static Logger logger = LoggerFactory.getLogger(ForgotPasswordContorller.class);

    @Autowired
    EmailConfig emailConfig;
    
    public class ForgotType{
        public static final  int NOTFOUND 			= 1;
        public static final  int EMAIL 				= 2;
        public static final  int MOBILE 			= 3;
        public static final  int CAPTCHAERROR 		= 4;
    }
    
    public class PasswordResetResult{
        public static final  int SUCCESS 			= 1;
        public static final  int CAPTCHAERROR 		= 2;
        public static final  int PASSWORDERROR 		= 3;
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
	HistorySystemLogsService historySystemLogsService;

	@Autowired
	CnfPasswordPolicyService passwordPolicyService;

	@GetMapping(value={"/passwordpolicy"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<CnfPasswordPolicy> passwordpolicy(){
		CnfPasswordPolicy passwordPolicy = passwordPolicyService.get(WebContext.getInst().getId());
		//构建密码强度说明
		passwordPolicy.buildMessage();
		return new Message<>(passwordPolicy);
	}

	@GetMapping(value = { "/validateCaptcha" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<ChangePassword> validateCaptcha(
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
				return new Message<>(Message.FAIL);
			}
			return new Message<>(Message.SUCCESS);
		}
		return new Message<>(Message.FAIL);
	}

	@GetMapping(value = { "/produceOtp" }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<ChangePassword> produceOtp(
    			@RequestParam String mobile,
    			@RequestParam String state,
    			@RequestParam String captcha) {
        logger.debug("forgotpassword  /forgotpassword/produceOtp.");
        logger.debug(" Mobile {}: " ,mobile);
        if (!authTokenService.validateCaptcha(state,captcha)) {    
        	logger.debug("login captcha valid error.");
        	return new Message<>(Message.FAIL);
        }
        
    	ChangePassword change = null;
    	logger.debug("Mobile Regex matches {}",ConstsRegex.MOBILE_PATTERN.matcher(mobile).matches());
    	if(StringUtils.isNotBlank(mobile) && ConstsRegex.MOBILE_PATTERN.matcher(mobile).matches()) {
    		UserInfo userInfo = userInfoService.findByEmailMobile(mobile);
    		if(userInfo != null) {
	    		change = new ChangePassword(userInfo);
	            change.clearPassword();
	        	AbstractOtpAuthn smsOtpAuthn = smsOtpAuthnService.getByInstId(userInfo.getInstId());
	        	smsOtpAuthn.produce(userInfo);
	        	return new Message<>(change);
    		}
        }
            
        return new Message<>(Message.FAIL);
    }
    
	@GetMapping(value = { "/produceEmailOtp" }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<ChangePassword> produceEmailOtp(
    			@RequestParam String email,
    			@RequestParam String state,
    			@RequestParam String captcha) {
        logger.debug("/forgotpassword/produceEmailOtp Email {} : " , email);
        if (!authTokenService.validateCaptcha(state,captcha)) {
        	logger.debug("captcha valid error.");
        	return new Message<>(Message.FAIL);
        }
        
    	ChangePassword change = null;
    	if(StringUtils.isNotBlank(email) && ConstsRegex.EMAIL_PATTERN.matcher(email).matches()) {
    		UserInfo userInfo = userInfoService.findByEmailMobile(email);
    		if(userInfo != null) {
	    		change = new ChangePassword(userInfo);
	            change.clearPassword();
	            AbstractOtpAuthn mailOtpAuthn =  mailOtpAuthnService.getMailOtpAuthn(userInfo.getInstId());
	            mailOtpAuthn.produce(userInfo);
	        	return new Message<>(change);
    		}
    	}
        return new Message<>(Message.FAIL);
    }

    @GetMapping({ "/setpassword" })
    public Message<ChangePassword> setPassWord(
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
	                	historySystemLogsService.insert(
	        					ConstsEntryType.USERINFO,
	        					changePassword,
	        					ConstsAct.FORGOT_PASSWORD,
	        					ConstsActResult.SUCCESS,
	        					loadedUserInfo);
	                	return new Message<>(Message.SUCCESS);
	                }else {
	                	return new Message<>(Message.FAIL);
	                }
	            } else {
	            	return new Message<>(Message.FAIL);
	            }
	        } 
        }
        return new Message<>(Message.FAIL);
    }
}
