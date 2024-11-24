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
package org.dromara.maxkey.authn.support.socialsignon;

import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.dromara.maxkey.authn.support.socialsignon.service.SocialsAssociateService;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.entity.SocialsAssociate;
import org.dromara.maxkey.entity.SocialsProvider;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;

/**
 * @author Crystal.Sea
 *
 */
public class AbstractSocialSignOnEndpoint {
	static final  Logger _logger = LoggerFactory.getLogger(AbstractSocialSignOnEndpoint.class);
	
	protected AuthRequest authRequest;
	
	protected String accountJsonString;
	
	@Autowired
	protected SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	protected SocialsAssociateService socialsAssociateService;
	
	@Autowired
    @Qualifier("authenticationProvider")
	AbstractAuthenticationProvider authenticationProvider ;
	
	@Autowired
	AuthTokenService authTokenService;
	
	@Autowired
	ApplicationConfig applicationConfig;
 	
  	protected AuthRequest buildAuthRequest(String instId,String provider,String baseUrl){
  		try {
			SocialsProvider socialSignOnProvider = socialSignOnProviderService.get(instId,provider);
			_logger.debug("socialSignOn Provider : "+socialSignOnProvider);
			
			if(socialSignOnProvider != null){
				authRequest = socialSignOnProviderService.getAuthRequest(instId,provider,baseUrl);
				return authRequest;
			}
  		}catch(Exception e) {
  			_logger.debug("buildAuthRequest Exception ",e);
  		}
		return null;
	}
    	
	protected SocialsAssociate  authCallback(String instId,String provider,String baseUrl)  throws Exception {
		SocialsAssociate socialsAssociate = null;
	    AuthCallback authCallback=new AuthCallback();
        authCallback.setCode(WebContext.getRequest().getParameter("code"));
        authCallback.setAuth_code(WebContext.getRequest().getParameter("auth_code"));
        authCallback.setOauth_token(WebContext.getRequest().getParameter("oauthToken"));
        authCallback.setAuthorization_code(WebContext.getRequest().getParameter("authorization_code"));
        authCallback.setOauth_verifier(WebContext.getRequest().getParameter("oauthVerifier"));
        authCallback.setState(WebContext.getRequest().getParameter("state"));
        _logger.debug("Callback OAuth code {}, auth_code {}, oauthToken {}, authorization_code {}, oauthVerifier {} , state {}", 
                authCallback.getCode(),
                authCallback.getAuth_code(),
                authCallback.getOauth_token(),
                authCallback.getAuthorization_code(),
                authCallback.getOauth_verifier(),
                authCallback.getState());
        
  		if(authRequest == null) {//if authRequest is null renew one
  		    authRequest=socialSignOnProviderService.getAuthRequest(instId,provider,baseUrl);  		    
  		    _logger.debug("session authRequest is null , renew one");
  		}
  		
  		//State time out, re set
  		if(authCallback.getState() != null) {
            authRequest.authorize(WebContext.getRequest().getSession().getId());
        }
  		
  		AuthResponse<?> authResponse=authRequest.login(authCallback);
  		_logger.debug("Response  : {}" , authResponse.getData());
  		String socialUserId = socialSignOnProviderService.getAccountId(provider, authResponse);
  		socialsAssociate =new SocialsAssociate();
		socialsAssociate.setProvider(provider);
		socialsAssociate.setSocialUserId(socialUserId);
		socialsAssociate.setInstId(instId);
		
 		return socialsAssociate;
 	}
  	
}
