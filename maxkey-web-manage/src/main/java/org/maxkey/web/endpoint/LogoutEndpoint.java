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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutEndpoint {
	
	private static Logger _logger = LoggerFactory.getLogger(LogoutEndpoint.class);
	
	@Autowired
	@Qualifier("authenticationRealm")
	AbstractAuthenticationRealm authenticationRealm;
	
 	@RequestMapping(value={"/logout"})
 	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
 		ModelAndView modelAndView = new ModelAndView();
 		authenticationRealm.logout(response);
 		SavedRequest  firstSavedRequest = (SavedRequest)WebContext.getAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER);
 		String reLoginUrl=WebContext.getHttpContextPath()+"/login";
 		if(firstSavedRequest!=null){
 			reLoginUrl= firstSavedRequest.getRedirectUrl();
 		}
 		_logger.debug("re Login URL : "+ reLoginUrl);
 		modelAndView.addObject("reloginUrl",reLoginUrl);
 		request.getSession().invalidate();
	 		
 		modelAndView.setViewName("loggedout");
		return modelAndView;
 	}
 	
}
