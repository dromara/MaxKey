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

import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.LoginCredential;
import org.maxkey.authn.jwt.AuthJwt;
import org.maxkey.authn.jwt.AuthTokenService;
import org.maxkey.authn.support.kerberos.KerberosService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeManager;
import org.maxkey.authn.support.rememberme.RemeberMe;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	Pattern mobileRegex = Pattern.compile("^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\\\d{8}$");
	
	@Autowired
	AuthTokenService authTokenService;
	
	@Autowired
  	ApplicationConfig applicationConfig;
 	
	@Autowired
	AbstractAuthenticationProvider authenticationProvider ;

	@Autowired
	SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	KerberosService kerberosService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
    AbstractOtpAuthn tfaOtpAuthn;
	
	@Autowired
    OtpAuthnService otpAuthnService;
	
	@Autowired
	AbstractRemeberMeManager remeberMeManager;
	
	/**
	 * init login
	 * @return
	 */
	@Operation(summary  = "登录接口", description  = "用户登录地址",method="GET")
	@RequestMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(
				@RequestParam(value = "remember_me", required = false) String rememberMeJwt) {
		_logger.debug("/get.");
		//Remember Me
		if(StringUtils.isNotBlank(rememberMeJwt)
				&& authTokenService.validateJwtToken(rememberMeJwt)) {
			try {
				RemeberMe remeberMe = remeberMeManager.resolve(rememberMeJwt);
				if(remeberMe != null) {
					LoginCredential credential = new LoginCredential();
					String remeberMeJwt = remeberMeManager.updateRemeberMe(remeberMe);
					credential.setUsername(remeberMe.getUsername());
					Authentication  authentication = authenticationProvider.authenticate(credential,true);
					if(authentication != null) {
			 			AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
			 			authJwt.setRemeberMe(remeberMeJwt);
			 			return new Message<AuthJwt>(authJwt).buildResponse();
					}
				}
			} catch (ParseException e) {
			}
		}
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
		model.put("state", authTokenService.genRandomJwt());
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
	public ResponseEntity<?> signin( HttpServletRequest request, HttpServletResponse response,
					@RequestBody LoginCredential credential) {
 		Message<AuthJwt> authJwtMessage = new Message<AuthJwt>(Message.FAIL);
 		if(authTokenService.validateJwtToken(credential.getState())){
 			String authType =  credential.getAuthType();
 			 _logger.debug("Login AuthN Type  " + authType);
 	        if (StringUtils.isNotBlank(authType)){
		 		Authentication  authentication = authenticationProvider.authenticate(credential);	 				
		 		if(authentication != null) {
		 			AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
		 			if(StringUtils.isNotBlank(credential.getRemeberMe())
		 					&&credential.getRemeberMe().equalsIgnoreCase("true")) {
		 				String remeberMe = remeberMeManager.createRemeberMe(authentication, request, response);
		 				authJwt.setRemeberMe(remeberMe);
			 		}
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
	public ResponseEntity<?> congress( @RequestBody LoginCredential credential) {
 		if(StringUtils.isNotBlank(credential.getCongress())){
 			AuthJwt authJwt = authTokenService.consumeCongress(credential.getCongress());
 			if(authJwt != null) {
 				return new Message<AuthJwt>(authJwt).buildResponse();
 			}
 		}
 		return new Message<AuthJwt>(Message.FAIL).buildResponse();
 	}

}
