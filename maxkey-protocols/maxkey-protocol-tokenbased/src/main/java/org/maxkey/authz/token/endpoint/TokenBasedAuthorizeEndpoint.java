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
package org.maxkey.authz.token.endpoint;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.token.endpoint.adapter.TokenBasedDefaultAdapter;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.Boolean;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.AppsTokenBasedDetails;
import org.maxkey.persistence.service.AppsTokenBasedDetailsService;
import org.maxkey.util.Instance;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TokenBasedAuthorizeEndpoint  extends AuthorizeBaseEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(TokenBasedAuthorizeEndpoint.class);
	@Autowired
	AppsTokenBasedDetailsService tokenBasedDetailsService;
	
	TokenBasedDefaultAdapter defaultTokenBasedAdapter=new TokenBasedDefaultAdapter();
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@RequestMapping("/authz/tokenbased/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		ModelAndView modelAndView=new ModelAndView();
		
		
		AppsTokenBasedDetails tokenBasedDetails=null;
		tokenBasedDetails=tokenBasedDetailsService.getAppDetails(id);
		_logger.debug(""+tokenBasedDetails);
		
		Apps  application= getApp(id);
		tokenBasedDetails.setAdapter(application.getAdapter());
		tokenBasedDetails.setIsAdapter(application.getIsAdapter());
		
		AbstractAuthorizeAdapter adapter;
		if(Boolean.isTrue(tokenBasedDetails.getIsAdapter())){
			adapter =(AbstractAuthorizeAdapter)Instance.newInstance(tokenBasedDetails.getAdapter());
		}else{
			adapter =(AbstractAuthorizeAdapter)defaultTokenBasedAdapter;
		}
		
		String tokenData=adapter.generateInfo(
				WebContext.getUserInfo(), 
				tokenBasedDetails);
		
		String encryptTokenData=adapter.encrypt(
				tokenData, 
				tokenBasedDetails.getAlgorithmKey(), 
				tokenBasedDetails.getAlgorithm());
		
		String signTokenData=adapter.sign(
				encryptTokenData, 
				tokenBasedDetails);
		
		if(tokenBasedDetails.getTokenType().equalsIgnoreCase("POST")) {
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					tokenBasedDetails, 
					signTokenData, 
					modelAndView);
			
			return modelAndView;
		}else {
			
			String cookieValue="";
			cookieValue=signTokenData;
			
			_logger.debug("Cookie Name : "+tokenBasedDetails.getCookieName());
			
			Cookie cookie= new Cookie(tokenBasedDetails.getCookieName(),cookieValue);
			
			Integer maxAge=Integer.parseInt(tokenBasedDetails.getExpires())*60;
			_logger.debug("Cookie Max Age :"+maxAge+" seconds.");
			cookie.setMaxAge(maxAge);
			
			cookie.setPath("/");
			//
			//cookie.setDomain("."+applicationConfig.getSubDomainName());
			//tomcat 8.5
			cookie.setDomain(applicationConfig.getDomainName());
			
			_logger.debug("Sub Domain Name : "+"."+applicationConfig.getDomainName());
			response.addCookie(cookie);
			
			if(tokenBasedDetails.getRedirectUri().indexOf(applicationConfig.getDomainName())>-1){
				return WebContext.redirect(tokenBasedDetails.getRedirectUri());
			}else{
				_logger.error(tokenBasedDetails.getRedirectUri()+" not in domain "+applicationConfig.getDomainName());
				return null;
			}
		}
		
	}

}
