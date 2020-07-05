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
package org.maxkey.authz.endpoint;

import javax.servlet.http.HttpServletRequest;


import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.domain.apps.Apps;
import org.maxkey.persistence.service.AppsCasDetailsService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
@Controller
public class AuthorizeEndpoint extends AuthorizeBaseEndpoint{
	@Autowired
	AppsCasDetailsService casDetailsService;
	
	//all single sign on url
	@RequestMapping("/authz/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		
		ModelAndView modelAndView=null;
		
		Apps  application=getApp(id);
		WebContext.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, id);
		
		if(application.getProtocol().equalsIgnoreCase(ConstantsProtocols.EXTEND_API)){
			modelAndView=WebContext.forward("/authz/api/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.FORMBASED)){
			 modelAndView=WebContext.forward("/authz/formbased/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.OAUTH20)){
			 modelAndView=WebContext.forward("/authz/oauthv20/"+application.getId());
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.OPEN_ID_CONNECT)){
			// modelAndView=new ModelAndView("openid connect");
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.SAML20)){
			 modelAndView=WebContext.forward("/authz/saml20/idpinit/"+application.getId());
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.TOKENBASED)){
			modelAndView=WebContext.forward("/authz/tokenbased/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.CAS)){
			modelAndView=WebContext.forward("/authz/cas/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.DESKTOP)){
			modelAndView=WebContext.forward("/authz/desktop/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(ConstantsProtocols.BASIC)){
			modelAndView=WebContext.redirect(application.getLoginUrl());
		}
		
		_logger.debug(modelAndView.getViewName());
		
		return modelAndView;
	}

	@RequestMapping("/authz/oauth10a/{id}")
	public ModelAndView authorizeOAuth10a(
			@PathVariable("id") String id){
		
		 String redirec_uri=getApp(id).getLoginUrl();
		return WebContext.redirect(redirec_uri);
		
	}
	
}
