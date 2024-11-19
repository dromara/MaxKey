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
 

package org.dromara.maxkey.authz.oauth2.provider.approval.endpoint;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.provider.AuthorizationRequest;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.approval.Approval;
import org.dromara.maxkey.authz.oauth2.provider.approval.ApprovalStore;
import org.dromara.maxkey.authz.oauth2.provider.approval.Approval.ApprovalStatus;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.cache.MomentaryService;
import org.dromara.maxkey.persistence.service.AppsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for retrieving the model for and displaying the confirmation page
 * for access to a protected resource.
 *
 * @author Ryan Heaton
 */
@Controller
public class OAuth20AccessConfirmationEndpoint {
	static final Logger _logger = LoggerFactory.getLogger(OAuth20AccessConfirmationEndpoint.class);
	 
    @Autowired
    protected AppsService appsService;
    
    @Autowired
    @Qualifier("oauth20JdbcClientDetailsService")
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("oauth20ApprovalStore")
    private ApprovalStore approvalStore;

    @Autowired
    @Qualifier("oauth20UserApprovalHandler")
    OAuth20UserApprovalHandler oauth20UserApprovalHandler;

    @Autowired 
	protected MomentaryService momentaryService;
    
    @Autowired 
    protected ApplicationConfig applicationConfig;
    
    @Autowired
	AuthTokenService authTokenService;
    
    /**
     * getAccessConfirmation.
     * @param model  Map
     * @return
     * throws Exception  
     */
    @RequestMapping(OAuth2Constants.ENDPOINT.ENDPOINT_APPROVAL_CONFIRM)
    public ModelAndView getAccessConfirmation(
            @RequestParam Map<String, Object> model,@CurrentUser UserInfo currentUser) {
    	try {
	        // Map<String, Object> model
	        AuthorizationRequest clientAuth = 
	        		(AuthorizationRequest) momentaryService.get(currentUser.getSessionId(), "authorizationRequest");
	        ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId(),true);
	        model.put("oauth_approval", authTokenService.genRandomJwt());
	        model.put("auth_request", clientAuth);
	        model.put("client", client);
	        model.put("oauth_version", "oauth 2.0");
	        Map<String, String> scopes = new LinkedHashMap<>();
	        for (String scope : clientAuth.getScope()) {
	            scopes.put(OAuth2Constants.PARAMETER.SCOPE_PREFIX + scope, "false");
	        }
	        String principal = AuthorizationUtils.getPrincipal().getUsername();
	        for (Approval approval : approvalStore.getApprovals(principal, client.getClientId())) {
	            if (clientAuth.getScope().contains(approval.getScope())) {
	                scopes.put(OAuth2Constants.PARAMETER.SCOPE_PREFIX + approval.getScope(),
	                        approval.getStatus() == ApprovalStatus.APPROVED ? "true" : "false");
	            }
	        }
	        
	        model.put("scopes", scopes);
	
	        if(!model.containsKey(OAuth2Constants.PARAMETER.APPROVAL_PROMPT)) {
	        	model.put(OAuth2Constants.PARAMETER.APPROVAL_PROMPT, client.getApprovalPrompt());
	        }
    	}catch(Exception e) {
    		 _logger.debug("OAuth Access Confirmation process error." ,e);
    	}
	        
        ModelAndView modelAndView = new ModelAndView("authorize/oauth_access_confirmation");
        _logger.trace("Confirmation details ");
        for (Object key : model.keySet()) {
            _logger.trace("key {} = {}" , key, model.get(key));
        }
        
        model.put("authorizeApproveUri", applicationConfig.getFrontendUri()+"/#/authz/oauth2approve");
        
        modelAndView.addObject("model", model);
        return modelAndView;
    }
    
    @RequestMapping(OAuth2Constants.ENDPOINT.ENDPOINT_APPROVAL_CONFIRM+"/get/{oauth_approval}")
    @ResponseBody
    public Message<Map<String, Object>> getAccess(
    			@PathVariable("oauth_approval") String oauthApproval,
    			@CurrentUser UserInfo currentUser) {
    	Map<String, Object> model = new HashMap<>();
    	if(authTokenService.validateJwtToken(oauthApproval)) {
	    	try {
		        AuthorizationRequest clientAuth = 
		        		(AuthorizationRequest) momentaryService.get(currentUser.getSessionId(), "authorizationRequest");
		        ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId(),true);

		        Apps  app = appsService.get(client.getClientId(),true);
		        app.transIconBase64();
		        
		        model.put("auth_request", clientAuth);
		        model.put("client", client);
		        model.put("clientId", clientAuth.getClientId());
		        model.put("appName", app.getAppName());
		        model.put("iconBase64", app.getIconBase64());
		        model.put("oauth_version", "oauth 2.0");
		        Map<String, String> scopes = new LinkedHashMap<>();
		        for (String scope : clientAuth.getScope()) {
		            scopes.put(OAuth2Constants.PARAMETER.SCOPE_PREFIX + scope, "false");
		        }
		        String principal = AuthorizationUtils.getPrincipal().getUsername();
		        for (Approval approval : approvalStore.getApprovals(principal, client.getClientId())) {
		            if (clientAuth.getScope().contains(approval.getScope())) {
		                scopes.put(OAuth2Constants.PARAMETER.SCOPE_PREFIX + approval.getScope(),
		                        approval.getStatus() == ApprovalStatus.APPROVED ? "true" : "false");
		            }
		        }
		        
		        model.put("scopes", scopes);
		
		        if(!model.containsKey(OAuth2Constants.PARAMETER.APPROVAL_PROMPT)) {
		        	model.put(OAuth2Constants.PARAMETER.APPROVAL_PROMPT, client.getApprovalPrompt());
		        }
	    	}catch(Exception e) {
	    		 _logger.debug("OAuth Access Confirmation process error." ,e);
	    	}
	    	
	        _logger.trace("Confirmation details ");
	        for (Object key : model.keySet()) {
	            _logger.trace("key {} = {}" ,key,model.get(key));
	        }
    	}
        return new Message<>(model);
    }

    /**
     * handleError.
     * @param model Map
     * @return
     * throws Exception
     */
    @RequestMapping(OAuth2Constants.ENDPOINT.ENDPOINT_ERROR)
    public String handleError(Map<String, Object> model) throws Exception {
        // We can add more stuff to the model here for JSP rendering. If the client was
        // a machine then
        // the JSON will already have been rendered.
        model.put("message", "There was a problem with the OAuth2 protocol");
        return "oauth_error";
    }
}
