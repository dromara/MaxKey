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
package org.maxkey.authn.support.socialsignon;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.authn.support.socialsignon.service.SocialsAssociateService;
import org.maxkey.web.WebContext;
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
	final static Logger _logger = LoggerFactory.getLogger(AbstractSocialSignOnEndpoint.class);

	protected final static String SOCIALSIGNON_SESSION_REDIRECT_URI="socialsignon_session_redirect_uri";
	
	protected final static String SOCIALSIGNON_REDIRECT_URI="redirect_uri";
	
	public  final static String SOCIALSIGNON_TYPE_SESSION="socialsignon_type_session";
	
	public  final static String SOCIALSIGNON_OAUTH_SERVICE_SESSION="socialsignon_oauth_service_session";
	
	public  final static String SOCIALSIGNON_PROVIDER_SESSION="socialsignon_provider_session";
	
	
	public final static class SOCIALSIGNON_TYPE{
		public  final static String SOCIALSIGNON_TYPE_LOGON="socialsignon_type_logon";
		public  final static String SOCIALSIGNON_TYPE_BIND="socialsignon_type_bind";
	}
	
	
	protected SocialSignOnProvider socialSignOnProvider;
	
	protected AuthRequest authRequest;
	
	protected String accountJsonString;
	
	protected String accountId;
	
	protected String provider;
	
	@Autowired
	protected SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	protected SocialsAssociateService socialsAssociateService;
	
	@Autowired
    @Qualifier("authenticationProvider")
	AbstractAuthenticationProvider authenticationProvider ;
 	
  	protected AuthRequest buildAuthRequest(String provider){
  		
		SocialSignOnProvider socialSignOnProvider = socialSignOnProviderService.get(provider);
		_logger.debug("socialSignOn Provider : "+socialSignOnProvider);
		
		if(socialSignOnProvider!=null){
			authRequest=socialSignOnProviderService.getAuthRequest(provider);
			WebContext.setAttribute(SOCIALSIGNON_OAUTH_SERVICE_SESSION, authRequest);
			WebContext.setAttribute(SOCIALSIGNON_PROVIDER_SESSION, socialSignOnProvider);
			return authRequest;
		}
		return null;
	}
    	
	protected String  authCallback() {
 		authRequest=(AuthRequest)WebContext.getAttribute(SOCIALSIGNON_OAUTH_SERVICE_SESSION);
 		socialSignOnProvider=(SocialSignOnProvider)WebContext.getAttribute(SOCIALSIGNON_PROVIDER_SESSION);
  		WebContext.removeAttribute(SOCIALSIGNON_OAUTH_SERVICE_SESSION);
  		WebContext.removeAttribute(SOCIALSIGNON_PROVIDER_SESSION);
  		
  		AuthCallback authCallback=new AuthCallback();
  		authCallback.setCode(WebContext.getRequest().getParameter("code"));
  		authCallback.setAuth_code(WebContext.getRequest().getParameter("auth_code"));
  		authCallback.setOauth_token(WebContext.getRequest().getParameter("oauthToken"));
  		authCallback.setAuthorization_code(WebContext.getRequest().getParameter("authorization_code"));
  		authCallback.setOauth_verifier(WebContext.getRequest().getParameter("oauthVerifier"));
  		authCallback.setState(WebContext.getRequest().getParameter("state"));
  		
  		AuthResponse<?> authResponse=authRequest.login(authCallback);
  		_logger.debug("Response  : "+authResponse);
  		accountId=socialSignOnProviderService.getAccountId(socialSignOnProvider.getProvider(), authResponse);
 		
 		_logger.debug("getAccountId : "+accountId);
 		return accountId;
 	}
  	

}
