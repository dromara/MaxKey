/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.web.interceptor;

import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.persistence.service.AppsCasDetailsService;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SingleSignOnInterceptor  implements AsyncHandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SingleSignOnInterceptor.class);
    
    @Autowired
    ApplicationConfig applicationConfig;
    
    @Autowired
	SessionManager sessionManager;
    
    @Autowired
	AuthTokenService authTokenService ;
    
    @Autowired
    AppsService appsService;
    
    @Autowired
    AppsCasDetailsService casDetailsService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler)
            throws Exception {
    	logger.trace("Single Sign On Interceptor");
       
    	AuthorizationUtils.authenticateWithCookie(request,authTokenService,sessionManager);

        if(AuthorizationUtils.isNotAuthenticated()) {
        	String loginUrl = applicationConfig.getFrontendUri() + "/#/passport/login?redirect_uri=%s";
        	String redirect_uri = UrlUtils.buildFullRequestUrl(request);
        	String base64RequestUrl = Base64Utils.base64UrlEncode(redirect_uri.getBytes());
        	logger.debug("No Authentication ... Redirect to /passport/login , redirect_uri {} , base64 {}",
        					redirect_uri ,base64RequestUrl);
        	response.sendRedirect(String.format(loginUrl,base64RequestUrl));
        	return false;
        }
        
        //判断应用访问权限
        if(AuthorizationUtils.isAuthenticated()){
	        logger.debug("preHandle {}",request.getRequestURI());
	        Apps app = (Apps)WebContext.getAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP);
	        if(app == null) {
	        	
	        	String requestURI = request.getRequestURI();
	        	if(requestURI.contains("/authz/cas/login")) {//for CAS service
	        		app = casDetailsService.getAppDetails(
	        				request.getParameter(CasConstants.PARAMETER.SERVICE), true);
	        	}else if(requestURI.contains("/authz/jwt/")
	        			||requestURI.contains("/authz/api/")
	        			||requestURI.contains("/authz/formbased/")
	        			||requestURI.contains("/authz/tokenbased/")
	        			||requestURI.contains("/authz/saml20/consumer/")
	        			||requestURI.contains("/authz/saml20/idpinit/")
	        			||requestURI.contains("/authz/cas/")
	        	) {//for id end of URL
	        		String [] requestURIs = requestURI.split("/");
	        		String appId = requestURIs[requestURIs.length -1];
	        		logger.debug("appId {}",appId);
		        	app = appsService.get(appId,true);
	        	}else if(requestURI.contains("/authz/oauth/v20/authorize")) {//oauth
		        	app = appsService.get(request.getParameter(OAuth2Constants.PARAMETER.CLIENT_ID),true);
	        	}
	        }
	        
	        if(app == null) {
	        	logger.debug("preHandle app is not exist . ");
	        	return true;
	        }
	        
	        SignPrincipal principal = AuthorizationUtils.getPrincipal();
	        if(principal != null && app !=null) {
	            if(principal.getGrantedAuthorityApps().contains(new SimpleGrantedAuthority(app.getId()))) {
	                logger.trace("preHandle have authority access {}" , app);
	                return true;
	            }
	        }
	        logger.debug("preHandle not have authority access {}" , app);
	        response.sendRedirect(request.getContextPath()+"/authz/refused");
	        return false;
    	}
        return true;
    }

}
