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

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.jwt.endpoint.adapter.JwtDefaultAdapter;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.Boolean;
import org.maxkey.entity.apps.Apps;
import org.maxkey.entity.apps.AppsJwtDetails;
import org.maxkey.persistence.service.AppsJwtDetailsService;
import org.maxkey.util.Instance;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "2-5-JWT令牌接口")
@Controller
public class JwtAuthorizeEndpoint  extends AuthorizeBaseEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(JwtAuthorizeEndpoint.class);
	
	@Autowired
	AppsJwtDetailsService jwtDetailsService;
	
	JwtDefaultAdapter jwtDefaultAdapter=new JwtDefaultAdapter();
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@Operation(summary = "JWT应用ID认证接口", description = "应用ID",method="GET")
	@RequestMapping("/authz/jwt/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		ModelAndView modelAndView=new ModelAndView();
		
		
		AppsJwtDetails jwtDetails=null;
		jwtDetails=jwtDetailsService.getAppDetails(id);
		_logger.debug(""+jwtDetails);
		
		Apps  application= getApp(id);
		jwtDetails.setAdapter(application.getAdapter());
		jwtDetails.setIsAdapter(application.getIsAdapter());
		
		AbstractAuthorizeAdapter adapter;
		if(Boolean.isTrue(jwtDetails.getIsAdapter())){
			adapter =(AbstractAuthorizeAdapter)Instance.newInstance(jwtDetails.getAdapter());
		}else{
			adapter =(AbstractAuthorizeAdapter)jwtDefaultAdapter;
		}
		
		String tokenData=adapter.generateInfo(
		        (SigninPrincipal)WebContext.getAuthentication().getPrincipal(),
				WebContext.getUserInfo(), 
				jwtDetails);
		
		String encryptTokenData=adapter.encrypt(
				tokenData, 
				jwtDetails.getAlgorithmKey(), 
				jwtDetails.getAlgorithm());
		
		String signTokenData=adapter.sign(
				encryptTokenData, 
				jwtDetails);
		
		if(jwtDetails.getTokenType().equalsIgnoreCase("POST")) {
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					jwtDetails, 
					signTokenData, 
					modelAndView);
			
			return modelAndView;
		}else {
			
			String cookieValue="";
			cookieValue=signTokenData;
			
			_logger.debug("Cookie Name : "+jwtDetails.getCookieName());
			
			Cookie cookie= new Cookie(jwtDetails.getCookieName(),cookieValue);
			
			Integer maxAge=Integer.parseInt(jwtDetails.getExpires())*60;
			_logger.debug("Cookie Max Age :"+maxAge+" seconds.");
			cookie.setMaxAge(maxAge);
			
			cookie.setPath("/");
			//
			//cookie.setDomain("."+applicationConfig.getBaseDomainName());
			//tomcat 8.5
			cookie.setDomain(applicationConfig.getBaseDomainName());
			
			_logger.debug("Sub Domain Name : "+"."+applicationConfig.getBaseDomainName());
			response.addCookie(cookie);
			
			if(jwtDetails.getRedirectUri().indexOf(applicationConfig.getBaseDomainName())>-1){
				return WebContext.redirect(jwtDetails.getRedirectUri());
			}else{
				_logger.error(jwtDetails.getRedirectUri()+" not in domain "+applicationConfig.getBaseDomainName());
				return null;
			}
		}
		
	}

}
