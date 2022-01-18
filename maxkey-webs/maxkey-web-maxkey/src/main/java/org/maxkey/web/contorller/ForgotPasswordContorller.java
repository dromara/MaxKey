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

import java.util.regex.Pattern;

import org.maxkey.configuration.EmailConfig;
import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.password.onetimepwd.OtpAuthnService;
import org.maxkey.persistence.repository.PasswordPolicyValidator;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "/forgotpassword" })
public class ForgotPasswordContorller {
    private static Logger _logger = LoggerFactory.getLogger(ForgotPasswordContorller.class);

    Pattern emailRegex = Pattern.compile(
            "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    
    Pattern mobileRegex = Pattern.compile(
            "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\\\d{8}$");
    
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
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("mailOtpAuthn")
    protected AbstractOtpAuthn mailOtpAuthn;
    
    @Autowired
    @Qualifier("otpAuthnService")
    OtpAuthnService otpAuthnService;
    

    @RequestMapping(value = { "/forward" })
    public ModelAndView forwardreg() {
        _logger.debug("forgotpassword  /forgotpassword/forward.");
        return new ModelAndView("forgotpassword/findpwd");
    }

    @RequestMapping(value = { "/emailmobile" })
    public ModelAndView email(@RequestParam String emailMobile,@RequestParam String captcha) {
        _logger.debug("forgotpassword  /forgotpassword/emailmobile.");
        _logger.debug("emailMobile : " + emailMobile);
        int forgotType = ForgotType.NOTFOUND;
        UserInfo userInfo = null;
        if (captcha != null && captcha
                .equals(WebContext.getSession().getAttribute(
                                WebConstants.KAPTCHA_SESSION_KEY).toString())) {            
            if(mobileRegex.matcher(emailMobile).matches()) {
            	forgotType = ForgotType.MOBILE;
            }else if(emailRegex.matcher(emailMobile).matches()) {
            	forgotType = ForgotType.EMAIL;
            }else {
            	forgotType = ForgotType.EMAIL;
            	emailMobile =emailMobile + "@" + emailConfig.getSmtpHost().substring(emailConfig.getSmtpHost().indexOf(".")+1);
            }
            
            userInfo = userInfoService.findByEmailMobile(emailMobile);
            
            if(null != userInfo) {
	            if (forgotType == ForgotType.EMAIL ) {
	            	mailOtpAuthn.produce(userInfo);
	            }else if (forgotType == ForgotType.MOBILE) {
	            	AbstractOtpAuthn smsOtpAuthn = otpAuthnService.getByInstId(userInfo.getInstId());
	            	smsOtpAuthn.produce(userInfo);
	            }
            }
           
        }else {
            _logger.debug("login captcha valid error.");
            forgotType = ForgotType.CAPTCHAERROR;
        }
        
        ModelAndView modelAndView = new ModelAndView("forgotpassword/resetpwd");
        modelAndView.addObject("userId", userInfo==null ?"":userInfo.getId());
        modelAndView.addObject("username", userInfo==null ?"":userInfo.getUsername());
        modelAndView.addObject("emailMobile", emailMobile);
        modelAndView.addObject("forgotType", forgotType);
        
        return modelAndView;
    }

    @RequestMapping(value = { "/setpassword" })
    public ModelAndView setPassWord(
                        @RequestParam String userId, 
                        @RequestParam String username, 
                        @RequestParam int forgotType, 
                        @RequestParam String password,
                        @RequestParam String confirmpassword,
                        @RequestParam String captcha) {
        _logger.debug("forgotPassword  /forgotpassword/pwdreseted.");
        ModelAndView modelAndView = new ModelAndView("forgotpassword/pwdreseted");
        if (null != password && password.equals(confirmpassword)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userId);
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setDecipherable(password);
            UserInfo loadedUserInfo = userInfoService.findByUsername(username);
            AbstractOtpAuthn smsOtpAuthn = otpAuthnService.getByInstId(loadedUserInfo.getInstId());
            if ((forgotType == ForgotType.EMAIL && mailOtpAuthn.validate(userInfo, captcha)) ||
                    (forgotType == ForgotType.MOBILE && smsOtpAuthn.validate(userInfo, captcha))
                ) {
                if(userInfoService.changePassword(userInfo,true)) {
                	modelAndView.addObject("passwordResetResult", PasswordResetResult.SUCCESS);
                }else {
                	;
                	modelAndView.addObject("validate_result", WebContext.getAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT));
                	modelAndView.addObject("passwordResetResult", PasswordResetResult.PASSWORDERROR);
                }
            } else {
                modelAndView.addObject("passwordResetResult", PasswordResetResult.CAPTCHAERROR);
            }
        } else {
            modelAndView.addObject("passwordResetResult", PasswordResetResult.PASSWORDERROR);
        }
        return modelAndView;
    }
}
