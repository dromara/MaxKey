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
 

package org.maxkey.web.contorller;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.LoginCredential;
import org.maxkey.authn.jwt.AuthJwt;
import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.support.kerberos.KerberosService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.entity.Institutions;
import org.maxkey.entity.Message;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "1-1-登录接口文档模块")
@Controller
@RequestMapping(value = "/login")
public class LoginEntryPoint {
	private static Logger _logger = LoggerFactory.getLogger(LoginEntryPoint.class);
	
	@Autowired
  	@Qualifier("authJwtService")
	AuthJwtService authJwtService;
	
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
	@RequestMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get() {
		_logger.debug("LoginController /get.");
		//for normal login
		HashMap<String , Object> model = new HashMap<String , Object>();
		model.put("isRemeberMe", applicationConfig.getLoginConfig().isRemeberMe());
		model.put("isKerberos", applicationConfig.getLoginConfig().isKerberos());
		if(applicationConfig.getLoginConfig().isMfa()) {
			model.put("otpType", tfaOtpAuthn.getOtpType());
			model.put("otpInterval", tfaOtpAuthn.getInterval());
		}
		
		if( applicationConfig.getLoginConfig().isKerberos()){
			model.put("userDomainUrlJson", kerberosService.buildKerberosProxys());
		}
		
		Institutions inst = (Institutions)WebContext.getAttribute(WebConstants.CURRENT_INST);
		model.put("inst", inst);
		if(applicationConfig.getLoginConfig().isCaptcha()) {
			model.put("captcha", "true");
		}else {
			model.put("captcha", inst.getCaptchaSupport());
			model.put("captchaType", inst.getCaptchaType());
		}
		model.put("state", authJwtService.genJwt());
		//load Social Sign On Providers
		model.put("socials", socialSignOnProviderService.loadSocials(inst.getId()));
		
		return new Message<HashMap<String , Object>>(model).buildResponse();
	}
 	

 	@RequestMapping(value={"/sendotp/{mobile}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> produceOtp(@PathVariable("mobile") String mobile) {
        UserInfo userInfo=userInfoService.findByEmailMobile(mobile);
        if(userInfo != null) {
        	otpAuthnService.getByInstId(WebContext.getInst().getId()).produce(userInfo);
        	return new Message<AuthJwt>(Message.SUCCESS).buildResponse();
        }
        
        return new Message<AuthJwt>(Message.FAIL).buildResponse();
    }
 	
 	/**
 	 * normal
 	 * @param loginCredential
 	 * @return
 	 */
 	@RequestMapping(value={"/signin"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> signin( @RequestBody LoginCredential loginCredential) {
 		Message<AuthJwt> authJwtMessage = new Message<AuthJwt>(Message.FAIL);
 		if(authJwtService.validateJwtToken(loginCredential.getState())){
 			String authType =  loginCredential.getAuthType();
 			 _logger.debug("Login AuthN Type  " + authType);
 	        if (StringUtils.isNotBlank(authType)){
		 		Authentication  authentication = authenticationProvider.authenticate(loginCredential);	 				
		 		if(authentication != null) {
		 			AuthJwt authJwt = authJwtService.genAuthJwt(authentication);
		 			if(WebContext.getAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE)!=null)
		 				authJwt.setPasswordSetType(
		 					(Integer)WebContext.getAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE));
		 			authJwtMessage = new Message<AuthJwt>(authJwt);
		 		}
 	        }else {
 	        	_logger.error("Login AuthN type must eq normal , tfa or mobile . ");
 	        }
 		}
 		return authJwtMessage.buildResponse();
 	}
 	
 	/**
 	 * for congress
 	 * @param loginCredential
 	 * @return
 	 */
 	@RequestMapping(value={"/congress"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> congress( @RequestBody LoginCredential loginCredential) {
 		if(StringUtils.isNotBlank(loginCredential.getCongress())){
 			AuthJwt authJwt = authJwtService.consumeCongress(loginCredential.getCongress());
 			if(authJwt != null) {
 				return new Message<AuthJwt>(authJwt).buildResponse();
 			}
 		}
 		return new Message<AuthJwt>(Message.FAIL).buildResponse();
 	}

}
