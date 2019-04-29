package org.maxkey.web.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.support.jwt.JwtLoginService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.config.ApplicationConfig;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  	protected ApplicationConfig applicationConfig;
 	
	
	@Autowired
	@Qualifier("remeberMeService")
	protected AbstractRemeberMeService remeberMeService;
	
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
			@RequestParam(value = WebConstants.JWT_TOKEN_PARAMETER, required = false) String jwt) {
 		
		_logger.debug("LoginController /login.");
		ModelAndView modelAndView = new ModelAndView();
		
		boolean isAuthenticated= WebContext.isAuthenticated();
		
		//for jwt Login
		if(!isAuthenticated){
			if(jwt!=null&&!jwt.equals("")){
				isAuthenticated=jwtLoginService.login(jwt, response);
			}
		}
				
		//for RemeberMe login
		if(!isAuthenticated){
			if(applicationConfig.getLoginConfig().isRemeberMe()&&remeberMe!=null&& !remeberMe.equals("")){
				isAuthenticated=remeberMeService.login(remeberMe,response);
			}
		}

		//for normal login
		if(!isAuthenticated){
			modelAndView.addObject("isRemeberMe", applicationConfig.getLoginConfig().isRemeberMe());
			
			modelAndView.addObject("isCaptcha", applicationConfig.getLoginConfig().isCaptcha());
			modelAndView.addObject("sessionid", WebContext.getSession().getId());
			modelAndView.addObject("jwtToken",jwtLoginService.buildLoginJwt());
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
		modelAndView.setViewName("login");
		return modelAndView;
	}
}
