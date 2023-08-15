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
 

/*******************************************************************************
 *     Cloud Foundry 
 *     Copyright (c) [2009-2014] Pivotal Software, Inc. All Rights Reserved.
 *
 *     This product is licensed to you under the Apache License, Version 2.0 (the "License").
 *     You may not use this product except in compliance with the License.
 *
 *     This product includes a number of subcomponents with
 *     separate copyright notices and license terms. Your use of these
 *     subcomponents is subject to the terms and conditions of the
 *     subcomponent's license, as noted in the LICENSE file.
 *******************************************************************************/
package org.dromara.maxkey.authz.oauth2.provider.endpoint;

import java.security.Principal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * OAuth2 token services that produces JWT encoded token values.
 * 
 * @author Dave Syer
 * @author Luke Taylor
 * @author Joel D'sa
 */
@Controller
public class TokenKeyEndpoint {

    protected final Log logger = LogFactory.getLog(getClass());

    private JwtAccessTokenConverter converter;

 	public TokenKeyEndpoint(JwtAccessTokenConverter converter) {
		super();
		this.converter = converter;
	}

    /**
     * Get the verification key for the token signatures. The principal has to
     * be provided only if the key is secret
     * (shared not public).
     * 
     * @param principal the currently authenticated user if there is one
     * @return the key used to verify tokens
     */
    @RequestMapping(value = OAuth2Constants.ENDPOINT.ENDPOINT_TOKEN_KEY, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getKey(Principal principal) {
        if ((principal == null || principal instanceof AnonymousAuthenticationToken) && !converter.isPublic()) {
            throw new AccessDeniedException("You need to authenticate to see a shared key");
        }
        Map<String, String> result = converter.getKey();
        return result;
    }

}
