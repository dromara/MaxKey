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

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.jwt.endpoint.adapter.JwtAdapter;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsBoolean;
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
		if(ConstsBoolean.isTrue(jwtDetails.getIsAdapter())){
			Object jwtAdapter = Instance.newInstance(jwtDetails.getAdapter());
			try {
				BeanUtils.setProperty(jwtAdapter, "jwtDetails", jwtDetails);
			} catch (IllegalAccessException | InvocationTargetException e) {
				_logger.error("setProperty error . ", e);
			}
			adapter = (AbstractAuthorizeAdapter)jwtAdapter;
		}else{
			JwtAdapter jwtAdapter =new JwtAdapter(jwtDetails);
			adapter = (AbstractAuthorizeAdapter)jwtAdapter;
		}
		
		adapter.setAuthentication((SigninPrincipal)WebContext.getAuthentication().getPrincipal());
		adapter.setUserInfo(WebContext.getUserInfo());
		
		adapter.generateInfo();
		//sign
		adapter.sign(null,jwtDetails.getSignatureKey(), jwtDetails.getSignature());
		//encrypt
		adapter.encrypt(null, jwtDetails.getAlgorithmKey(), jwtDetails.getAlgorithm());
		
		if(jwtDetails.getTokenType().equalsIgnoreCase("POST")) {
			return adapter.authorize(modelAndView);
		}else {
			_logger.debug("Cookie Name : {}" , jwtDetails.getJwtName());
			
			Cookie cookie= new Cookie(jwtDetails.getJwtName(),adapter.serialize());
			
			Integer maxAge = Integer.parseInt(jwtDetails.getExpires()) * 60;
			_logger.debug("Cookie Max Age : {} seconds." , maxAge);
			cookie.setMaxAge(maxAge);
			
			cookie.setPath("/");
			//
			//cookie.setDomain("."+applicationConfig.getBaseDomainName());
			//tomcat 8.5
			cookie.setDomain(applicationConfig.getBaseDomainName());
			
			_logger.debug("Sub Domain Name : .{}",applicationConfig.getBaseDomainName());
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
