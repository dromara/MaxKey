/**
 * 
 */
package org.maxkey.authz.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
@Controller
public class AuthorizeProtectedEndpoint{

	@RequestMapping("/authz/protected/forward")
	public ModelAndView forwardProtectedForward(
			HttpServletRequest request ){
		String redirect_uri=request.getAttribute("redirect_uri").toString();
		ModelAndView modelAndView=new ModelAndView("authorize/protected/forward");
		modelAndView.addObject("redirect_uri", redirect_uri);
		return modelAndView;
	}
	
	@RequestMapping("/authz/protected")
	public ModelAndView authorizeProtected(
			@RequestParam("password") String password,
			@RequestParam("redirect_uri") String redirect_uri){
		 UserInfo userInfo=WebContext.getUserInfo();
		if( userInfo.getAppLoginPassword().equals(ReciprocalUtils.encode(password))){
			WebContext.setAttribute(WebConstants.CURRENT_SINGLESIGNON_URI, redirect_uri);
			return WebContext.redirect(redirect_uri);
		}
		
		ModelAndView modelAndView=new ModelAndView("authorize/protected/forward");
		modelAndView.addObject("redirect_uri", redirect_uri);
		return modelAndView;
	}
			
}
