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
package org.dromara.maxkey.authz.oauth2.provider.token;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidTokenException;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Queries the /check_token endpoint to obtain the contents of an access token.
 * 
 * If the endpoint returns a 400 response, this indicates that the token is invalid.
 * 
 * @author Dave Syer
 * @author Luke Taylor
 * 
 */
public class RemoteTokenServices implements ResourceServerTokenServices {

    protected final Log logger = LogFactory.getLog(getClass());

    private RestOperations restTemplate;

    private String checkTokenEndpointUrl;

    private String clientId;

    private String clientSecret;

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    public RemoteTokenServices() {
        restTemplate = new RestTemplate();
        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getStatusCode().value() != 400) {
                    super.handleError(response);
                }
            }
        });
    }

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.tokenConverter = accessTokenConverter;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("token", accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));
        Map<String, Object> map = postForMap(checkTokenEndpointUrl, formData, headers);

        if (map.containsKey("error")) {
            logger.debug("check_token returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        }

        Assert.state(map.containsKey("client_id"), "Client id must be present in response from auth server");
        return tokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private String getAuthorizationHeader(String clientId, String clientSecret) {
        String creds = String.format("%s:%s", clientId, clientSecret);
        try {
            return "Basic " + new String(Base64.getEncoder().encodeToString(creds.getBytes("UTF-8")));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Could not convert String");
        }
    }

    private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) {
        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        @SuppressWarnings("rawtypes")
        Map map = restTemplate.exchange(path, HttpMethod.POST,
                new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
        @SuppressWarnings("unchecked")
        Map<String, Object> result = map;
        return result;
    }

}
