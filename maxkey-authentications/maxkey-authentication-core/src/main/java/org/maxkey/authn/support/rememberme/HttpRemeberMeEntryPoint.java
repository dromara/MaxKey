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
 

package org.maxkey.authn.support.rememberme;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.LoginCredential;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsLoginType;
import org.maxkey.crypto.Base64Utils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;


public class HttpRemeberMeEntryPoint implements AsyncHandlerInterceptor {
	private static final Logger _logger = LoggerFactory.getLogger(HttpRemeberMeEntryPoint.class);
	
    boolean enable;
    
  	ApplicationConfig applicationConfig;
    
    AbstractAuthenticationProvider authenticationProvider ;
    
	AbstractRemeberMeService remeberMeService;
	
	 @Override
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 boolean isAuthenticated= WebContext.isAuthenticated();
		 Cookie readRemeberMeCookie = WebContext.readCookieByName(request,WebConstants.REMEBER_ME_COOKIE);
		 
		 if(!enable 
				 || isAuthenticated 
				 || readRemeberMeCookie==null 
				 || !applicationConfig.getLoginConfig().isRemeberMe()){
			 return true;
		 }
		 
		 _logger.trace("RemeberMe Login Start ...");
		 _logger.trace("Request url : "+ request.getRequestURL());
		 _logger.trace("Request URI : "+ request.getRequestURI());
		 _logger.trace("Request ContextPath : "+ request.getContextPath());
		 _logger.trace("Request ServletPath : "+ request.getServletPath());
		 _logger.trace("RequestSessionId : "+ request.getRequestedSessionId());
		 _logger.trace("isRequestedSessionIdValid : "+ request.isRequestedSessionIdValid());
		 _logger.trace("getSession : "+ request.getSession(false));
		 
		// session not exists，session timeout，recreate new session
		 if(request.getSession(false) == null) {
		    _logger.info("recreate new session .");
			request.getSession(true);
		 }
		 
		 _logger.trace("getSession.getId : "+ request.getSession().getId());

		_logger.debug("Try RemeberMe login ");
		String remeberMe = readRemeberMeCookie.getValue();
		 _logger.debug("RemeberMe : " + remeberMe);

        remeberMe = new String(Base64Utils.base64UrlDecode(remeberMe));

        remeberMe = PasswordReciprocal.getInstance().decoder(remeberMe);

        _logger.debug("decoder RemeberMe : " + remeberMe);
        RemeberMe remeberMeCookie = new RemeberMe();
        remeberMeCookie = (RemeberMe) JsonUtils.json2Object(remeberMe, remeberMeCookie);
        _logger.debug("Remeber Me Cookie : " + remeberMeCookie);

        RemeberMe storeRemeberMe = remeberMeService.read(remeberMeCookie);
        if (storeRemeberMe != null)  {
	        DateTime loginDate = new DateTime(storeRemeberMe.getLastLogin());
	        DateTime expiryDate = loginDate.plusSeconds(remeberMeService.getRemeberMeValidity());
	        DateTime now = new DateTime();
	        if (now.isBefore(expiryDate)) {
	            LoginCredential loginCredential =
	            		new LoginCredential(storeRemeberMe.getUsername(),"",ConstsLoginType.REMEBER_ME);
	            authenticationProvider.authentication(loginCredential,true);
	            remeberMeService.updateRemeberMe(remeberMeCookie, response);
	            _logger.debug("RemeberMe Logined in , username " + storeRemeberMe.getUsername());
	        }
        }
		
		 return true;
	}

	 public HttpRemeberMeEntryPoint() {
	        super();
	 }

    public HttpRemeberMeEntryPoint (boolean enable) {
        super();
        this.enable = enable;
    }

    public HttpRemeberMeEntryPoint(
			AbstractAuthenticationProvider authenticationProvider, AbstractRemeberMeService remeberMeService,
			ApplicationConfig applicationConfig,boolean enable) {
		super();
		this.enable = enable;
		this.applicationConfig = applicationConfig;
		this.authenticationProvider = authenticationProvider;
		this.remeberMeService = remeberMeService;
	}

	public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public void setAuthenticationProvider(AbstractAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	public void setRemeberMeService(AbstractRemeberMeService remeberMeService) {
		this.remeberMeService = remeberMeService;
	}
	 
	
}
