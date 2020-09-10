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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.BasicAuthentication;
import org.maxkey.authn.support.kerberos.KerberosService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.authn.support.wsfederation.WsFederationConstants;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author Crystal.Sea
 *
 */
@Controller
public class LoginEndpoint {
	private static Logger _logger = LoggerFactory.getLogger(LoginEndpoint.class);
	
	
	
	@Autowired
  	@Qualifier("applicationConfig")
  	ApplicationConfig applicationConfig;
 	
	@Autowired
	@Qualifier("socialSignOnProviderService")
	SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	@Qualifier("remeberMeService")
	AbstractRemeberMeService remeberMeService;
	
	@Autowired
	@Qualifier("kerberosService")
	KerberosService kerberosService;
	
	@Autowired
	@Qualifier("userInfoService")
	UserInfoService userInfoService;
	
	/*@Autowired
	@Qualifier("wsFederationService")
	WsFederationService wsFederationService;*/
	
	@Autowired
	@Qualifier("authenticationProvider")
	AbstractAuthenticationProvider authenticationProvider ;
	
	@Autowired
    @Qualifier("tfaOptAuthn")
    protected AbstractOptAuthn tfaOptAuthn;
	
	/*
	@Autowired
	@Qualifier("jwtLoginService")
	JwtLoginService jwtLoginService;
	*/
	/**
	 * init login
	 * @return
	 */
 	@RequestMapping(value={"/login"})
	public ModelAndView login(
			HttpServletRequest request,
			HttpServletResponse response,
			@CookieValue(value=WebConstants.REMEBER_ME_COOKIE,required=false) String remeberMe,
			@RequestParam(value=WebConstants.CAS_SERVICE_PARAMETER,required=false) String casService,
			@RequestParam(value=WebConstants.KERBEROS_TOKEN_PARAMETER,required=false) String kerberosToken,
			@RequestParam(value=WebConstants.KERBEROS_USERDOMAIN_PARAMETER,required=false) String kerberosUserDomain,
			@RequestParam(value=WsFederationConstants.WA,required=false) String wsFederationWA,
			@RequestParam(value=WsFederationConstants.WRESULT,required=false) String wsFederationWResult) {
 		
		_logger.debug("LoginController /login.");
		ModelAndView modelAndView = new ModelAndView("login");
		
		boolean isAuthenticated= WebContext.isAuthenticated();
		//for RemeberMe login
		if(!isAuthenticated){
			if(applicationConfig.getLoginConfig().isRemeberMe()&&remeberMe!=null&& !remeberMe.equals("")){
				_logger.debug("Try RemeberMe login ");
				isAuthenticated=remeberMeService.login(remeberMe,response);
			}
		}
		//for Kerberos login
		if(!isAuthenticated){
			if(applicationConfig.getLoginConfig().isKerberos()&&
					kerberosUserDomain!=null&&!kerberosUserDomain.equals("")&&
					kerberosToken!=null && !kerberosToken.equals("")){
				_logger.debug("Try Kerberos login ");
				isAuthenticated=kerberosService.login(kerberosToken,kerberosUserDomain);
			}
		}
		//for WsFederation login
		if(!isAuthenticated){
			if(applicationConfig.getLoginConfig().isWsFederation()&&
					StringUtils.isNotEmpty(wsFederationWA) && 
					wsFederationWA.equalsIgnoreCase(WsFederationConstants.WSIGNIN)){
				_logger.debug("Try WsFederation login ");
				//isAuthenticated=wsFederationService.login(wsFederationWA,wsFederationWResult,request);
			}
		}
				
		//for normal login
		if(!isAuthenticated){
			modelAndView.addObject("isRemeberMe", applicationConfig.getLoginConfig().isRemeberMe());
			modelAndView.addObject("isKerberos", applicationConfig.getLoginConfig().isKerberos());
			modelAndView.addObject("isMfa", applicationConfig.getLoginConfig().isMfa());
			if(applicationConfig.getLoginConfig().isMfa()) {
			    modelAndView.addObject("optType", tfaOptAuthn.getOptType());
			    modelAndView.addObject("optInterval", tfaOptAuthn.getInterval());
			}
			
			if( applicationConfig.getLoginConfig().isKerberos()){
				modelAndView.addObject("userDomainUrlJson", kerberosService.buildKerberosProxys());
				
			}
			modelAndView.addObject("isCaptcha", applicationConfig.getLoginConfig().isCaptcha());
			modelAndView.addObject("sessionid", WebContext.getSession().getId());
			//modelAndView.addObject("jwtToken",jwtLoginService.buildLoginJwt());
			//load Social Sign On Providers
			if(applicationConfig.getLoginConfig().isSocialSignOn()){
				_logger.debug("Load Social Sign On Providers ");
				modelAndView.addObject("ssopList", socialSignOnProviderService.getSocialSignOnProviders());
			}
		}
		
		
		if(isAuthenticated){
			return  WebContext.redirect("/forwardindex");
		}
		
		Object loginErrorMessage=WebContext.getAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE);
        modelAndView.addObject("loginErrorMessage", loginErrorMessage==null?"":loginErrorMessage);
        WebContext.removeAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE);
		return modelAndView;
	}
 	
 	@RequestMapping(value={"/logon.do"})
	public ModelAndView logon(
	                    HttpServletRequest request,
	                    HttpServletResponse response,
	                    @ModelAttribute("authentication") BasicAuthentication authentication) throws ServletException, IOException {

        authenticationProvider.authenticate(authentication);

        if (WebContext.isAuthenticated()) {
            return WebContext.redirect("/forwardindex");
        } else {
            return WebContext.redirect("/login");
        }
 		
 	}
	
 	
 	@RequestMapping("/login/{username}")
	@ResponseBody
	public HashMap <String,Object> queryLoginUserAuth(@PathVariable("username") String username) {
 		UserInfo userInfo=new UserInfo();
 		userInfo.setUsername(username);
 		userInfo=userInfoService.load(userInfo);
 		
 		HashMap <String,Object> authnType=new HashMap <String,Object>();
 		authnType.put("authnType", userInfo.getAuthnType());
 		authnType.put("appLoginAuthnType", userInfo.getAppLoginAuthnType());
 		
 		return authnType;
 	}
 	
 	@RequestMapping("/login/otp/{username}")
    @ResponseBody
    public String produceOtp(@PathVariable("username") String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        UserInfo queryUserInfo=userInfoService.loadByUsername(username);//(userInfo);
        if(queryUserInfo!=null) {
            tfaOptAuthn.produce(queryUserInfo);
            return "ok";
        }
        
        return "fail";
    }
}
