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
 

package org.maxkey.web.endpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.LoginCredential;
import org.maxkey.authn.support.kerberos.KerberosService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.entity.Institutions;
import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.password.onetimepwd.OtpAuthnService;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "1-1-登录接口文档模块")
@Controller
public class LoginEndpoint {
	private static Logger _logger = LoggerFactory.getLogger(LoginEndpoint.class);
	
	@Autowired
  	@Qualifier("applicationConfig")
  	ApplicationConfig applicationConfig;
 	
	@Autowired
	@Qualifier("authenticationProvider")
	AbstractAuthenticationProvider authenticationProvider ;
	
	@Autowired
	@Qualifier("socialSignOnProviderService")
	SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	@Qualifier("kerberosService")
	KerberosService kerberosService;
	
	@Autowired
	@Qualifier("userInfoService")
	UserInfoService userInfoService;
	
	@Autowired
    @Qualifier("tfaOtpAuthn")
    protected AbstractOtpAuthn tfaOtpAuthn;
	
	@Autowired
    @Qualifier("otpAuthnService")
    protected OtpAuthnService otpAuthnService;
	
	Pattern mobileRegex = Pattern.compile(
	            "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\\\d{8}$");
	
	/**
	 * init login
	 * @return
	 */
	@Operation(summary  = "登录接口", description  = "用户登录地址",method="GET")
 	@RequestMapping(value={"/login"})
	public ModelAndView login(HttpServletRequest request) {
		_logger.debug("LoginController /login.");
		
		boolean isAuthenticated= WebContext.isAuthenticated();
		
		if(isAuthenticated){
			return  WebContext.redirect("/forwardindex");
		}
		
		_logger.trace("Session Timeout MaxInactiveInterval " + WebContext.getRequest().getSession().getMaxInactiveInterval());
		
		//for normal login
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("isRemeberMe", applicationConfig.getLoginConfig().isRemeberMe());
		modelAndView.addObject("isKerberos", applicationConfig.getLoginConfig().isKerberos());
		modelAndView.addObject("isMfa", applicationConfig.getLoginConfig().isMfa());
		if(applicationConfig.getLoginConfig().isMfa()) {
		    modelAndView.addObject("otpType", tfaOtpAuthn.getOtpType());
		    modelAndView.addObject("otpInterval", tfaOtpAuthn.getInterval());
		}
		
		if( applicationConfig.getLoginConfig().isKerberos()){
			modelAndView.addObject("userDomainUrlJson", kerberosService.buildKerberosProxys());
		}
		Institutions inst = (Institutions)WebContext.getAttribute(WebConstants.CURRENT_INST);
		modelAndView.addObject("isCaptcha", inst.isTrueCaptchaSupport());
		modelAndView.addObject("captcha", inst.getCaptcha());
		modelAndView.addObject("sessionid", WebContext.getSession().getId());
		//modelAndView.addObject("jwtToken",jwtLoginService.buildLoginJwt());
		//load Social Sign On Providers
		modelAndView.addObject("sspLogin", socialSignOnProviderService.loadSocialsProviders(inst.getId()));
		
		Object loginErrorMessage=WebContext.getAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE);
        modelAndView.addObject("loginErrorMessage", loginErrorMessage==null?"":loginErrorMessage);
        WebContext.removeAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE);
		return modelAndView;
	}
 	
 	@RequestMapping(value={"/logon.do"})
	public ModelAndView logon(
	                    HttpServletRequest request,
	                    HttpServletResponse response,
	                    @ModelAttribute("loginCredential") LoginCredential loginCredential) throws ServletException, IOException {

        authenticationProvider.authenticate(loginCredential);

        if (WebContext.isAuthenticated()) {
            return WebContext.redirect("/forwardindex");
        } else {
            return WebContext.redirect("/login");
        }
 		
 	}
	
 	
 	@RequestMapping("/login/{username}")
	@ResponseBody
	public HashMap <String,Object> queryLoginUserAuth(@PathVariable("username") String username) {
 		UserInfo userInfo=userInfoService.findByUsername(username);
 		
 		HashMap <String,Object> authnType=new HashMap <String,Object>();
 		authnType.put("authnType", userInfo.getAuthnType());
 		authnType.put("appLoginAuthnType", userInfo.getAppLoginAuthnType());
 		
 		return authnType;
 	}
 	
 	@RequestMapping("/login/sendsms/{mobile}")
    @ResponseBody
    public String produceOtp(@PathVariable("mobile") String mobile,HttpServletRequest request) {
        UserInfo queryUserInfo=userInfoService.findByEmailMobile(mobile);
        if(queryUserInfo!=null) {
        	otpAuthnService.getByInstId(WebContext.getInst(request)).produce(queryUserInfo);
            return "ok";
        }
        
        return "fail";
    }

}
