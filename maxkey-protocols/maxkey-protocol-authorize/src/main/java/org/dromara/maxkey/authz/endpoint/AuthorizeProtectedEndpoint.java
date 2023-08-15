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
 

/**
 * 
 */
package org.dromara.maxkey.authz.endpoint;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

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
			@RequestParam("redirect_uri") String redirect_uri,
			@CurrentUser UserInfo currentUser){
		if( currentUser.getAppLoginPassword().equals(PasswordReciprocal.getInstance().encode(password))){
			WebContext.setAttribute(WebConstants.CURRENT_SINGLESIGNON_URI, redirect_uri);
			return WebContext.redirect(redirect_uri);
		}
		
		ModelAndView modelAndView=new ModelAndView("authorize/protected/forward");
		modelAndView.addObject("redirect_uri", redirect_uri);
		return modelAndView;
	}
			
}
