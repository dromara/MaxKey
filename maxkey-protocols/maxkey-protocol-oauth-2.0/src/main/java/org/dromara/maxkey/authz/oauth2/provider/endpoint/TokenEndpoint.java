/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.maxkey.authz.oauth2.provider.endpoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.oauth2.common.DefaultOAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidClientException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidRequestException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.dromara.maxkey.authz.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.dromara.maxkey.authz.oauth2.common.util.OAuth2Utils;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestValidator;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;
import org.dromara.maxkey.authz.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.util.StringGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * <p>
 * Endpoint for token requests as described in the OAuth2 spec. Clients post requests with a <code>grant_type</code>
 * parameter (e.g. "authorization_code") and other parameters as determined by the grant type. Supported grant types are
 * handled by the provided {@link #setTokenGranter(org.dromara.maxkey.authz.oauth2.provider.TokenGranter) token
 * granter}.
 * </p>
 * 
 * <p>
 * Clients must be authenticated using a Spring Security {@link Authentication} to access this endpoint, and the client
 * id is extracted from the authentication token. The best way to arrange this (as per the OAuth2 spec) is to use HTTP
 * basic authentication for this endpoint with standard Spring Security support.
 * </p>
 * 
 * @author Dave Syer
 * 
 */
@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class TokenEndpoint extends AbstractEndpoint {

	private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();

	private Set<HttpMethod> allowedRequestMethods = new HashSet<HttpMethod>(Arrays.asList(HttpMethod.POST,HttpMethod.GET));

	/**
	 * must use HTTP POST Method to get token
	 * HTTP GET is not Supported
	 * @param principal
	 * @param parameters
	 * @return OAuth2AccessToken Entity
	 * @throws HttpRequestMethodNotSupportedException
	 */
	@Operation(summary = "OAuth 2.0 获取AccessToken接口", description = "传递参数token等",method="GET")
	@RequestMapping(value = {
								OAuth2Constants.ENDPOINT.ENDPOINT_TOKEN,
								OAuth2Constants.ENDPOINT.ENDPOINT_TENCENT_IOA_TOKEN
							}, 
					method=RequestMethod.GET)
	public ResponseEntity<OAuth2AccessToken> getAccessToken(@RequestParam
	Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
		if (!allowedRequestMethods.contains(HttpMethod.GET)) {
			throw new HttpRequestMethodNotSupportedException("GET");
		}
		return postAccessToken(parameters);
	}
	
	@Operation(summary = "OAuth 2.0 获取AccessToken接口", description = "传递参数token等",method="POST")
	@RequestMapping(value = {
								OAuth2Constants.ENDPOINT.ENDPOINT_TOKEN,
								OAuth2Constants.ENDPOINT.ENDPOINT_TENCENT_IOA_TOKEN
							}, 
					method=RequestMethod.POST)
	public ResponseEntity<OAuth2AccessToken> postAccessToken(@RequestParam
	Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
		// TokenEndpointAuthenticationFilter
		OAuth2AccessToken token = null;
	    try {
		    Object principal = AuthorizationUtils.getAuthentication();
	
			if (!(principal instanceof Authentication)) {
				throw new InsufficientAuthenticationException(
						"There is no client authentication. Try adding an appropriate authentication.");
			}
	
			String clientId = getClientId((Authentication)principal);
			ClientDetails authenticatedClient = getClientDetailsService().loadClientByClientId(clientId,true);
	
			TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);
	
			if (clientId != null && !clientId.equals("")) {
				// Only validate the client details if a client authenticated during this
				// request.
				if (!clientId.equals(tokenRequest.getClientId())) {
					// double check to make sure that the client ID in the token request is the same as that in the
					// authenticated client
					throw new InvalidClientException("Given client ID does not match authenticated client");
				}
			}
			if (authenticatedClient != null) {
				oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);
			}
			if (!StringUtils.hasText(tokenRequest.getGrantType())) {
				throw new InvalidRequestException("Missing grant type");
			}
			if (tokenRequest.getGrantType().equals(OAuth2Constants.PARAMETER.GRANT_TYPE_IMPLICIT)) {
				throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
			}
	
			if (isAuthCodeRequest(parameters)) {
				// The scope was requested or determined during the authorization step
				if (!tokenRequest.getScope().isEmpty()) {
					logger.debug("Clearing scope of incoming token request");
					tokenRequest.setScope(Collections.<String> emptySet());
				}
			}
			
			logger.debug("request parameters " + parameters);
			// The scope was requested or determined during the authorization step
			/**crystal.sea
			 * code must uuid format
			 */
			 if (parameters.get(OAuth2Constants.PARAMETER.CODE) != null 
					 &&!StringGenerator.uuidMatches(parameters.get(OAuth2Constants.PARAMETER.CODE))) {
			    	throw new InvalidRequestException("The code is not valid format .");
			}
			 
			if (isRefreshTokenRequest(parameters)) {
				// A refresh token has its own default scopes, so we should ignore any added by the factory here.
				tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Constants.PARAMETER.SCOPE)));
			}
			//granter grant access token
			token = getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
			if (token == null) {
				throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
			}
	    }catch(OAuth2Exception oauth2Exception) {
	    	token = new DefaultOAuth2AccessToken(oauth2Exception); 
	    }catch(InsufficientAuthenticationException authenticationException) {
	    	token = new DefaultOAuth2AccessToken(new OAuth2Exception(authenticationException.getMessage())); 
	    }
		return getResponse(token);

	}

	/**
	 * @param principal the currently authentication principal
	 * @return a client id if there is one in the principal
	 */
	protected String getClientId(Authentication principal) {
		Authentication client = (Authentication) principal;
		if (!client.isAuthenticated()) {
			throw new InsufficientAuthenticationException("The client is not authenticated.");
		}
		String clientId = client.getPrincipal().toString();
		if (client instanceof OAuth2Authentication) {
			// Might be a client and user combined authentication
			clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
		}
		if (client instanceof UsernamePasswordAuthenticationToken) {
		    clientId = ((SignPrincipal)client.getPrincipal()).getUsername();
		}
		return clientId;
	}
	
	
	/**
	 * @param principal the currently authentication principal
	 * @return a client id if there is one in the principal
	 
	protected String getClientId(Principal principal) {
		Authentication client = (Authentication) principal;
		if (!client.isAuthenticated()) {
			throw new InsufficientAuthenticationException("The client is not authenticated.");
		}
		String clientId = client.getName();
		if (client instanceof OAuth2Authentication) {
			// Might be a client and user combined authentication
			clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
		}
		return clientId;
	}
*/



	private ResponseEntity<OAuth2AccessToken> getResponse(OAuth2AccessToken accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cache-Control", "no-store");
		headers.set("Pragma", "no-cache");
		return new ResponseEntity<OAuth2AccessToken>(accessToken, headers, HttpStatus.OK);
	}

	private boolean isRefreshTokenRequest(Map<String, String> parameters) {
		return OAuth2Constants.PARAMETER.GRANT_TYPE_REFRESH_TOKEN.equals(parameters.get(OAuth2Constants.PARAMETER.GRANT_TYPE)) 
				&& parameters.get(OAuth2Constants.PARAMETER.GRANT_TYPE_REFRESH_TOKEN) != null;
	}

	private boolean isAuthCodeRequest(Map<String, String> parameters) {
		return OAuth2Constants.PARAMETER.GRANT_TYPE_AUTHORIZATION_CODE.equals(parameters.get(OAuth2Constants.PARAMETER.GRANT_TYPE)) 
				&& parameters.get(OAuth2Constants.PARAMETER.CODE) != null;
	}

	public void setOAuth2RequestValidator(OAuth2RequestValidator oAuth2RequestValidator) {
		this.oAuth2RequestValidator = oAuth2RequestValidator;
	}

	public void setAllowedRequestMethods(Set<HttpMethod> allowedRequestMethods) {
		this.allowedRequestMethods = allowedRequestMethods;
	}

}
