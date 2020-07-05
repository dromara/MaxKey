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
 

package org.maxkey.authz.oauth2.provider.approval.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.maxkey.authn.BasicAuthentication;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.oauth2.common.util.OAuth2Utils;
import org.maxkey.authz.oauth2.provider.AuthorizationRequest;
import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.authz.oauth2.provider.approval.Approval;
import org.maxkey.authz.oauth2.provider.approval.Approval.ApprovalStatus;
import org.maxkey.authz.oauth2.provider.approval.ApprovalStore;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.oauth2.provider.ClientDetails;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.web.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for retrieving the model for and displaying the confirmation page
 * for access to a protected resource.
 *
 * @author Ryan Heaton
 */
@Controller
@SessionAttributes("authorizationRequest")
public class OAuth20AccessConfirmationController {

    @Autowired
    @Qualifier("appsService")
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

    /**
     * getAccessConfirmation.
     * @param model  Map
     * @return
     * throws Exception  
     */
    @RequestMapping("/oauth/v20/approval_confirm")
    public ModelAndView getAccessConfirmation(
            @RequestParam Map<String, Object> model) throws Exception {
        model.remove("authorizationRequest");
        Map<String, String> modelRequest = new HashMap<String, String>();
        for (Object key : model.keySet()) {
            modelRequest.put(key.toString(), model.get(key).toString());
        }
        
        // Map<String, Object> model
        AuthorizationRequest clientAuth = 
                (AuthorizationRequest) WebContext.getAttribute("authorizationRequest");
        ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
        Apps  app = (Apps)WebContext.getAttribute(AuthorizeBaseEndpoint.class.getName());
        //session中为空或者id不一致重新加载
        if (app == null || !app.getId().equalsIgnoreCase(clientAuth.getClientId())) {
            app = appsService.get(clientAuth.getClientId()); 
            WebContext.setAttribute(AuthorizeBaseEndpoint.class.getName(), app);
            WebContext.setAttribute(app.getId(), app.getIcon());
        }
       
        model.put("auth_request", clientAuth);
        model.put("client", client);
        model.put("app", app);
        model.put("oauth_version", "oauth 2.0");
        Map<String, String> scopes = new LinkedHashMap<String, String>();
        for (String scope : clientAuth.getScope()) {
            scopes.put(OAuth2Utils.SCOPE_PREFIX + scope, "false");
        }
        String principal = 
                ((BasicAuthentication) WebContext.getAuthentication().getPrincipal()).getUsername();
        for (Approval approval : approvalStore.getApprovals(principal, client.getClientId())) {
            if (clientAuth.getScope().contains(approval.getScope())) {
                scopes.put(OAuth2Utils.SCOPE_PREFIX + approval.getScope(),
                        approval.getStatus() == ApprovalStatus.APPROVED ? "true" : "false");
            }
        }
        model.put("scopes", scopes);

        ModelAndView modelAndView = new ModelAndView("authorize/oauth_access_confirmation");
        modelAndView.addObject("model", model);
        return modelAndView;
    }

    /**
     * handleError.
     * @param model Map
     * @return
     * throws Exception
     */
    @RequestMapping("/oauth/v20/error")
    public String handleError(Map<String, Object> model) throws Exception {
        // We can add more stuff to the model here for JSP rendering. If the client was
        // a machine then
        // the JSON will already have been rendered.
        model.put("message", "There was a problem with the OAuth2 protocol");
        return "oauth_error";
    }
}
