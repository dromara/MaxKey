/*
 * Copyright 2002-2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.dromara.maxkey.authz.oauth2.provider.endpoint;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidClientException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidRequestException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.dromara.maxkey.authz.oauth2.common.exceptions.RedirectMismatchException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.dromara.maxkey.authz.oauth2.common.util.OAuth2Utils;
import org.dromara.maxkey.authz.oauth2.provider.AuthorizationRequest;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestValidator;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;
import org.dromara.maxkey.authz.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.dromara.maxkey.authz.oauth2.provider.approval.UserApprovalHandler;
import org.dromara.maxkey.authz.oauth2.provider.code.AuthorizationCodeServices;
import org.dromara.maxkey.authz.oauth2.provider.implicit.ImplicitTokenRequest;
import org.dromara.maxkey.authz.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * Implementation of the Authorization Endpoint from the OAuth2 specification. Accepts authorization requests, and
 * handles user approval if the grant type is authorization code. The tokens themselves are obtained from the
 * {@link TokenEndpoint Token Endpoint}, except in the implicit grant type (where they come from the Authorization
 * Endpoint via <code>response_type=token</code>.
 * </p>
 * 
 * <p>
 * This endpoint should be secured so that it is only accessible to fully authenticated users (as a minimum requirement)
 * since it represents a request from a valid user to act on his or her behalf.
 * </p>
 * 
 * @author Dave Syer
 * @author Vladimir Kryachko
 * @author Crystal.sea 2022-04-14
 * 
 */
@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class AuthorizationEndpoint extends AbstractEndpoint {
	static final  Logger _logger = LoggerFactory.getLogger(AuthorizationEndpoint.class);
	
	private static final String OAUTH_V20_AUTHORIZATION_URL = "" + OAuth2Constants.ENDPOINT.ENDPOINT_AUTHORIZE + "?client_id=%s&response_type=code&redirect_uri=%s&approval_prompt=auto";
	
	private RedirectResolver redirectResolver = new DefaultRedirectResolver();

	private UserApprovalHandler userApprovalHandler = new DefaultUserApprovalHandler();

	private OAuth2RequestValidator oauth2RequestValidator = new DefaultOAuth2RequestValidator();

	private String userApprovalPage = "forward:" + OAuth2Constants.ENDPOINT.ENDPOINT_APPROVAL_CONFIRM;

	private String errorPage = "forward:" + OAuth2Constants.ENDPOINT.ENDPOINT_ERROR;
	
	private Object implicitLock = new Object();


	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	@Operation(summary = "OAuth 2.0 认证接口", description = "传递参数应用ID，自动完成跳转认证拼接",method="GET")
    @GetMapping(value = {OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/{id}"})
    public ModelAndView authorize(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("id") String id){
        ClientDetails  clientDetails =getClientDetailsService().loadClientByClientId(id,true);
        _logger.debug("clientDetails {}",clientDetails);
        String authorizationUrl = "";
        try {
            authorizationUrl = String.format(OAUTH_V20_AUTHORIZATION_URL, 
                            clientDetails.getClientId(), 
                            URLEncoder.encode(clientDetails.getRegisteredRedirectUri().toArray()[0].toString(),"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        _logger.debug("authorizationUrl {}" , authorizationUrl);
        
        return WebContext.redirect(authorizationUrl);
    }
	   
	@Operation(summary = "OAuth 2.0 认证接口", description = "传递参数client_id,response_type,redirect_uri等",method="GET")
	@GetMapping(value = {
								OAuth2Constants.ENDPOINT.ENDPOINT_AUTHORIZE,
								OAuth2Constants.ENDPOINT.ENDPOINT_TENCENT_IOA_AUTHORIZE
							})
	public ModelAndView authorize(
	            Map<String, Object> model, 
	            @RequestParam Map<String, String> parameters,
	            @CurrentUser UserInfo currentUser,
	            SessionStatus sessionStatus) {
	    
		 Principal principal=(Principal)AuthorizationUtils.getAuthentication();
		// Pull out the authorization request first, using the OAuth2RequestFactory. All further logic should
		// query off of the authorization request instead of referring back to the parameters map. The contents of the
		// parameters map will be stored without change in the AuthorizationRequest object once it is created.
		AuthorizationRequest authorizationRequest = getOAuth2RequestFactory().createAuthorizationRequest(parameters);

		Set<String> responseTypes = authorizationRequest.getResponseTypes();

		if (!responseTypes.contains(OAuth2Constants.PARAMETER.TOKEN) && !responseTypes.contains(OAuth2Constants.PARAMETER.CODE)) {
			throw new UnsupportedResponseTypeException("Unsupported response types: " + responseTypes);
		}

		if (authorizationRequest.getClientId() == null) {
			throw new InvalidClientException("A client id must be provided");
		}

		try {

			if (!(principal instanceof Authentication) || !((Authentication) principal).isAuthenticated()) {
				throw new InsufficientAuthenticationException(
						"User must be authenticated with Spring Security before authorization can be completed.");
			}

			ClientDetails client = getClientDetailsService().loadClientByClientId(authorizationRequest.getClientId(),true);

			// The resolved redirect URI is either the redirect_uri from the parameters or the one from
			// clientDetails. Either way we need to store it on the AuthorizationRequest.
			String redirectUriParameter = authorizationRequest.getRequestParameters().get(OAuth2Constants.PARAMETER.REDIRECT_URI);
			//URLDecoder for redirect_uri
			redirectUriParameter = URLDecoder.decode(redirectUriParameter,"UTF-8");
			String resolvedRedirect = redirectResolver.resolveRedirect(redirectUriParameter, client);
			if (!StringUtils.hasText(resolvedRedirect)) {
				logger.info("Client redirectUri "+resolvedRedirect);
				logger.info("Parameter redirectUri "+redirectUriParameter);
				
				throw new RedirectMismatchException(
						"A redirectUri must be either supplied or preconfigured in the ClientDetails");
			}
			authorizationRequest.setRedirectUri(resolvedRedirect);

			// We intentionally only validate the parameters requested by the client (ignoring any data that may have
			// been added to the request by the manager).
			oauth2RequestValidator.validateScope(authorizationRequest, client);

			// Some systems may allow for approval decisions to be remembered or approved by default. Check for
			// such logic here, and set the approved flag on the authorization request accordingly.
			authorizationRequest = userApprovalHandler.checkForPreApproval(authorizationRequest,
					(Authentication) principal);
			// is this call necessary?
			boolean approved = userApprovalHandler.isApproved(authorizationRequest, (Authentication) principal);
			authorizationRequest.setApproved(approved);

			// Validation is all done, so we can check for auto approval...
			if (authorizationRequest.isApproved()) {
				if (responseTypes.contains(OAuth2Constants.PARAMETER.TOKEN)) {
					return new ModelAndView(getImplicitGrantResponse(authorizationRequest));
				}
				if (responseTypes.contains(OAuth2Constants.PARAMETER.CODE)) {
					return new ModelAndView(getAuthorizationCodeResponse(authorizationRequest,
							(Authentication) principal));
				}
			}
			Apps  app = (Apps)WebContext.getAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP);
			//session中为空或者id不一致重新加载
            if (app == null || !app.getId().equalsIgnoreCase(authorizationRequest.getClientId())) {
                app = appsService.get(authorizationRequest.getClientId()); 
                WebContext.setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP, app);
            }
            
			// Place auth request into the model so that it is stored in the session
			// for approveOrDeny to use. That way we make sure that auth request comes from the session,
			// so any auth request parameters passed to approveOrDeny will be ignored and retrieved from the session.
            momentaryService.put(currentUser.getSessionId(), "authorizationRequest", authorizationRequest);
            
			return getUserApprovalPageResponse(model, authorizationRequest, (Authentication) principal);

		}catch(UnsupportedEncodingException e) {
			logger.info("URLDecoder Exception ",e);
			throw new RuntimeException("URLDecoder UnsupportedEncodingException");
		}catch (RuntimeException e) {
			sessionStatus.setComplete();
			throw e;
		}

	}

	//approval must post
	@PostMapping(value  = {OAuth2Constants.ENDPOINT.ENDPOINT_AUTHORIZE+"/approval"}, params = OAuth2Constants.PARAMETER.USER_OAUTH_APPROVAL)
	@ResponseBody
	public Message< String> authorizeApproveOrDeny(@RequestParam Map<String, String> approvalParameters,@CurrentUser UserInfo currentUser) {
		Principal principal = (Principal)AuthorizationUtils.getAuthentication();
		if (!(principal instanceof Authentication)) {
			throw new InsufficientAuthenticationException(
					"User must be authenticated with Spring Security before authorizing an access token.");
		}

		AuthorizationRequest authorizationRequest = (AuthorizationRequest) momentaryService.get(currentUser.getSessionId(), "authorizationRequest");

		if (authorizationRequest == null) {
			throw new InvalidRequestException("Cannot approve uninitialized authorization request.");
		}

		Set<String> responseTypes = authorizationRequest.getResponseTypes();

		authorizationRequest.setApprovalParameters(approvalParameters);
		authorizationRequest = userApprovalHandler.updateAfterApproval(authorizationRequest,(Authentication) principal);
		boolean approved = userApprovalHandler.isApproved(authorizationRequest, (Authentication) principal);
		authorizationRequest.setApproved(approved);

		if (authorizationRequest.getRedirectUri() == null) {
			throw new InvalidRequestException("Cannot approve request when no redirect URI is provided.");
		}

		if (!authorizationRequest.isApproved()) {
			return new Message<>(Message.FAIL,
					getUnsuccessfulRedirect(
				            authorizationRequest,
				            new UserDeniedAuthorizationException("User denied access"), 
				            responseTypes.contains(OAuth2Constants.PARAMETER.TOKEN)
				        )
					);
		}

		if (responseTypes.contains(OAuth2Constants.PARAMETER.TOKEN)) {
			return new Message<>(getImplicitGrantResponse(authorizationRequest));
		}

		return new Message<>(getAuthorizationCodeResponse(authorizationRequest, (Authentication) principal));
	}

	// We need explicit approval from the user.
	private ModelAndView getUserApprovalPageResponse(Map<String, Object> model,
			AuthorizationRequest authorizationRequest, Authentication principal) {
		logger.debug("Loading user approval page: " + userApprovalPage);
		model.putAll(userApprovalHandler.getUserApprovalRequest(authorizationRequest, principal));
		return new ModelAndView(userApprovalPage, model);
	}

	// We can grant a token and return it with implicit approval.
	private String  getImplicitGrantResponse(AuthorizationRequest authorizationRequest) {
		try {
			TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(authorizationRequest, "implicit");
			OAuth2Request storedOAuth2Request = getOAuth2RequestFactory().createOAuth2Request(authorizationRequest);

			OAuth2AccessToken accessToken = getAccessTokenForImplicitGrant(tokenRequest, storedOAuth2Request);
			if (accessToken == null) {
				throw new UnsupportedResponseTypeException("Unsupported response type: token");
			}
			return appendAccessToken(authorizationRequest, accessToken);
		}
		catch (OAuth2Exception e) {
			return getUnsuccessfulRedirect(authorizationRequest, e, true);
		}
	}

	private OAuth2AccessToken getAccessTokenForImplicitGrant(TokenRequest tokenRequest,
			OAuth2Request storedOAuth2Request) {
		OAuth2AccessToken accessToken = null;
		// These 1 method calls have to be atomic, otherwise the ImplicitGrantService can have a race condition where
		// one thread removes the token request before another has a chance to redeem it.
		synchronized (this.implicitLock) {
			accessToken = getTokenGranter().grant("implicit", new ImplicitTokenRequest(tokenRequest, storedOAuth2Request));
		}
		return accessToken;
	}
	
	// Authorization Code Response
	private String getAuthorizationCodeResponse(AuthorizationRequest authorizationRequest, Authentication authUser) {
		try {
			String  successfulRedirect = getSuccessfulRedirect(
					authorizationRequest,
					generateCode(authorizationRequest, authUser)
			);
			_logger.debug("successfulRedirect {}" , successfulRedirect);
			return successfulRedirect;
		}
		catch (OAuth2Exception e) {
			return getUnsuccessfulRedirect(authorizationRequest, e, false);
		}
	}

	private String appendAccessToken(AuthorizationRequest authorizationRequest, OAuth2AccessToken accessToken) {

		Map<String, Object> vars = new HashMap<String, Object>();

		String requestedRedirect = authorizationRequest.getRedirectUri();
		if (accessToken == null) {
			throw new InvalidRequestException("An implicit grant could not be made");
		}
		StringBuilder url = new StringBuilder(requestedRedirect);
		if (requestedRedirect.contains("#")) {
			url.append("&");
		}
		else {
			url.append("#");
		}

		url.append(templateUrlVar(OAuth2Constants.PARAMETER.ACCESS_TOKEN));
		url.append("&").append(templateUrlVar(OAuth2Constants.PARAMETER.TOKEN_TYPE));
		vars.put(OAuth2Constants.PARAMETER.ACCESS_TOKEN, accessToken.getValue());
		vars.put(OAuth2Constants.PARAMETER.TOKEN_TYPE, accessToken.getTokenType());
		String state = authorizationRequest.getState();

		if (state != null) {
			url.append("&").append(templateUrlVar(OAuth2Constants.PARAMETER.STATE));
			vars.put(OAuth2Constants.PARAMETER.STATE, state);
		}
		Date expiration = accessToken.getExpiration();
		if (expiration != null) {
			long expires_in = (expiration.getTime() - System.currentTimeMillis()) / 1000;
			url.append("&").append(templateUrlVar(OAuth2Constants.PARAMETER.EXPIRES_IN));
			vars.put(OAuth2Constants.PARAMETER.EXPIRES_IN, expires_in);
		}
		String originalScope = authorizationRequest.getRequestParameters().get(OAuth2Constants.PARAMETER.SCOPE);
		if (originalScope == null || !OAuth2Utils.parseParameterList(originalScope).equals(accessToken.getScope())) {
			url.append("&").append(templateUrlVar(OAuth2Constants.PARAMETER.SCOPE));
			vars.put(OAuth2Constants.PARAMETER.SCOPE, OAuth2Utils.formatParameterList(accessToken.getScope()));
		}
		Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
		for (String key : additionalInformation.keySet()) {
			Object value = additionalInformation.get(key);
			if (value != null) {
				url.append("&" + key + "={extra_" + key + "}");
				vars.put("extra_" + key, value);
			}
		}
		UriTemplate template = new UriTemplate(url.toString());
		// Do not include the refresh token (even if there is one)
		return template.expand(vars).toString();
	}
	
	public String templateUrlVar(String parameterName) {
		return parameterName + "={" + parameterName + "}";
	}

	private String generateCode(AuthorizationRequest authorizationRequest, Authentication authentication)
			throws AuthenticationException {

		try {

			OAuth2Request storedOAuth2Request = getOAuth2RequestFactory().createOAuth2Request(authorizationRequest);

			OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOAuth2Request, authentication);
			String code = authorizationCodeServices.createAuthorizationCode(combinedAuth);

			return code;

		}
		catch (OAuth2Exception e) {

			if (authorizationRequest.getState() != null) {
				e.addAdditionalInformation(OAuth2Constants.PARAMETER.STATE, authorizationRequest.getState());
			}

			throw e;

		}
	}
	// Successful Redirect
	private String getSuccessfulRedirect(AuthorizationRequest authorizationRequest, String authorizationCode) {

		if (authorizationCode == null) {
			throw new IllegalStateException("No authorization code found in the current request scope.");
		}

		Map<String, String> query = new LinkedHashMap<String, String>();
		query.put(OAuth2Constants.PARAMETER.CODE, authorizationCode);

		String state = authorizationRequest.getState();
		if (state != null) {
			query.put(OAuth2Constants.PARAMETER.STATE, state);
		}
		
		//this is for cas
		String service = authorizationRequest.getRequestParameters().get("service");
		if (service != null) {
			query.put("service", service);
		}

		return append(authorizationRequest.getRedirectUri(), query, false);
	}

	private String getUnsuccessfulRedirect(AuthorizationRequest authorizationRequest, OAuth2Exception failure,
			boolean fragment) {

		if (authorizationRequest == null || authorizationRequest.getRedirectUri() == null) {
			// we have no redirect for the user. very sad.
			throw new UnapprovedClientAuthenticationException("Authorization failure, and no redirect URI.", failure);
		}

		Map<String, String> query = new LinkedHashMap<String, String>();

		query.put("error", failure.getOAuth2ErrorCode());
		query.put("error_description", failure.getMessage());

		if (authorizationRequest.getState() != null) {
			query.put(OAuth2Constants.PARAMETER.STATE, authorizationRequest.getState());
		}

		if (failure.getAdditionalInformation() != null) {
			for (Map.Entry<String, String> additionalInfo : failure.getAdditionalInformation().entrySet()) {
				query.put(additionalInfo.getKey(), additionalInfo.getValue());
			}
		}

		return append(authorizationRequest.getRedirectUri(), query, fragment);

	}

	private String append(String base, Map<String, ?> query, boolean fragment) {
		return append(base, query, null, fragment);
	}

	private String append(String base, Map<String, ?> query, Map<String, String> keys, boolean fragment) {

		UriComponentsBuilder template = UriComponentsBuilder.newInstance();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base);
		URI redirectUri;
		try {
			// assume it's encoded to start with (if it came in over the wire)
			redirectUri = builder.build(true).toUri();
		}
		catch (Exception e) {
			// ... but allow client registrations to contain hard-coded non-encoded values
			redirectUri = builder.build().toUri();
			builder = UriComponentsBuilder.fromUri(redirectUri);
		}
		template.scheme(redirectUri.getScheme()).port(redirectUri.getPort()).host(redirectUri.getHost())
				.userInfo(redirectUri.getUserInfo()).path(redirectUri.getPath());
		if (StringUtils.hasLength(redirectUri.getFragment())) {
			StringBuilder values = new StringBuilder();
			if (redirectUri.getFragment() != null) {
				String append = redirectUri.getFragment();
				values.append(append);
			}
			for (String key : query.keySet()) {
				if (values.length() > 0) {
					if(values.indexOf("?") == -1) {
						values.append("?");
					}else {
						values.append("&");
					}
				}
				String name = key;
				if (keys != null && keys.containsKey(key)) {
					name = keys.get(key);
				}
				values.append(name + "={" + key + "}");
			}
			if (values.length() > 0) {
				template.fragment(values.toString());
			}
			UriComponents encoded = template.build().expand(query).encode();
			builder.fragment(encoded.getFragment());
		}
		else {
			for (String key : query.keySet()) {
				String name = key;
				if (keys != null && keys.containsKey(key)) {
					name = keys.get(key);
				}
				template.queryParam(name, "{" + key + "}");
			}
			template.fragment(redirectUri.getFragment());
			UriComponents encoded = template.build().expand(query).encode();
			builder.query(encoded.getQuery());
		}

		return builder.build().toUriString();

	}
	
	public void setUserApprovalPage(String userApprovalPage) {
		this.userApprovalPage = userApprovalPage;
	}

	public void setAuthorizationCodeServices(AuthorizationCodeServices authorizationCodeServices) {
		this.authorizationCodeServices = authorizationCodeServices;
	}

	public void setRedirectResolver(RedirectResolver redirectResolver) {
		this.redirectResolver = redirectResolver;
	}

	public void setUserApprovalHandler(UserApprovalHandler userApprovalHandler) {
		this.userApprovalHandler = userApprovalHandler;
	}

	public void setOAuth2RequestValidator(OAuth2RequestValidator oauth2RequestValidator) {
		this.oauth2RequestValidator = oauth2RequestValidator;
	}

}
