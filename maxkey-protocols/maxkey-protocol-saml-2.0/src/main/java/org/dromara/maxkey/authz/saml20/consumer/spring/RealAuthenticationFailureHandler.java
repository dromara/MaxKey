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
 


package org.dromara.maxkey.authz.saml20.consumer.spring;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles authn failures of the SAMLResponseAuthenticationProcessingFilter. 
 * 
 * If the AuthenticationException is of type IdentityProviderAuthenticationException,
 * retry the original request, which will cause a new authn attempt with the IDP.  This
 * is nearly identical to Spring's SavedRequestAwareAuthenticationSuccessHandler.
 * 
 * Any other exceptions will result in responding with forbidden (403).
 * 
 * A more sophisticated implementation might limit the number of attempts or
 * allow the user to cancel the authn attempt.
 * 
 * 
 * @author jcox
 *
 */
public class RealAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	private static final  Logger logger = LoggerFactory
	.getLogger(RealAuthenticationFailureHandler.class);

	private final RequestCache requestCache;
	
	
	public RealAuthenticationFailureHandler(RequestCache requestCache) {
		super();
		this.requestCache = requestCache;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authenticationException)
			throws IOException, ServletException {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		logger.debug("saved Request: {}", savedRequest);
		
		if( authenticationException instanceof IdentityProviderAuthenticationException && savedRequest != null) {
			
			logger.warn("Authn Failure reported by the IDP.", authenticationException);
			logger.debug("Retry original request of {}", savedRequest.getRedirectUrl());
			response.sendRedirect(savedRequest.getRedirectUrl());
		}

		else {
			logger.warn("Unrecoverable authn failure. Sending to Forbidden", authenticationException);
			response.sendError(HttpServletResponse.SC_FORBIDDEN);		
		}
	}
}
