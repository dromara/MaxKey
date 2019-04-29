package org.maxkey.web.endpoint;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.support.jwt.JwtLoginService;
import org.maxkey.authn.support.kerberos.KerberosService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.authn.support.wsfederation.WsFederationConstants;
import org.maxkey.authn.support.wsfederation.WsFederationService;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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
	
	@Autowired
	@Qualifier("wsFederationService")
	WsFederationService wsFederationService;
	
	@Autowired
	@Qualifier("jwtLoginService")
	JwtLoginService jwtLoginService;
	
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
		ModelAndView modelAndView = new ModelAndView();
		
		boolean isAuthenticated= WebContext.isAuthenticated();
		//for RemeberMe login
		if(!isAuthenticated){
			if(applicationConfig.getLoginConfig().isRemeberMe()&&remeberMe!=null&& !remeberMe.equals("")){
				isAuthenticated=remeberMeService.login(remeberMe,response);
			}
		}
		//for Kerberos login
		if(!isAuthenticated){
			if(applicationConfig.getLoginConfig().isKerberos()&&
					kerberosUserDomain!=null&&!kerberosUserDomain.equals("")&&
					kerberosToken!=null && !kerberosToken.equals("")){
				isAuthenticated=kerberosService.login(kerberosToken,kerberosUserDomain);
			}
		}
		//for WsFederation login
		if(!isAuthenticated){
			if(applicationConfig.getLoginConfig().isWsFederation()&&
					StringUtils.isNotEmpty(wsFederationWA) && 
					wsFederationWA.equalsIgnoreCase(WsFederationConstants.WSIGNIN)){
				isAuthenticated=wsFederationService.login(wsFederationWA,wsFederationWResult,request);
			}
		}
				
		//for normal login
		if(!isAuthenticated){
			modelAndView.addObject("isRemeberMe", applicationConfig.getLoginConfig().isRemeberMe());
			modelAndView.addObject("isKerberos", applicationConfig.getLoginConfig().isKerberos());
			modelAndView.addObject("isOneTimePwd", applicationConfig.getLoginConfig().isOneTimePwd());
			if( applicationConfig.getLoginConfig().isKerberos()){
				modelAndView.addObject("userDomainUrlJson", kerberosService.buildKerberosProxys());
				
			}
			modelAndView.addObject("isCaptcha", applicationConfig.getLoginConfig().isCaptcha());
			modelAndView.addObject("sessionid", WebContext.getSession().getId());
			modelAndView.addObject("jwtToken",jwtLoginService.buildLoginJwt());
			//load Social Sign On Providers
			if(applicationConfig.getLoginConfig().isSocialSignOn()){
				modelAndView.addObject("ssopList", socialSignOnProviderService.getSocialSignOnProviders());
			}
		}
		//save  first protected url 
		SavedRequest  firstSavedRequest = (SavedRequest)WebContext.getAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER);
		if(firstSavedRequest==null){
			RequestCache requestCache = new HttpSessionRequestCache();
			SavedRequest  savedRequest =requestCache.getRequest(request, response);
			if(savedRequest!=null){
				_logger.debug("first request parameter "+savedRequest.getRedirectUrl());
				WebContext.setAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER, savedRequest);
			}
		}else {
			WebContext.setAttribute(WebConstants.SPRING_PROCESS_SAVED_REQUEST, firstSavedRequest);
		}
		if(isAuthenticated){
			modelAndView.setViewName("index");
		}else{
			modelAndView.setViewName("login");
		}
		return modelAndView;
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
}