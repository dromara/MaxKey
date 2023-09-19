/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.plugin.maxkey.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MaxkeyConfigTest {

    @Test
    public void maxkeyConfig() {

        MaxkeyConfig maxkeyConfig = new MaxkeyConfig("a", "b", "c", "d", "e", "f", "g", "h", "i", false, "j", false, "k", "l", "m");
        assertEquals("a", maxkeyConfig.getClientId());
        assertEquals("b", maxkeyConfig.getClientSecret());
        assertEquals("c", maxkeyConfig.getAuthorizationEndpoint());
        assertEquals("d", maxkeyConfig.getScope());
        assertEquals("e", maxkeyConfig.getResponseType());
        assertEquals("f", maxkeyConfig.getRedirectUrl());
        assertEquals("g", maxkeyConfig.getRealm());
        assertEquals("h", maxkeyConfig.getGrantType());
        assertEquals("i", maxkeyConfig.getTokenEndpoint());
        assertFalse(maxkeyConfig.isBearerOnly());
        assertEquals("j", maxkeyConfig.getIntrospectionEndpoint());
        assertFalse(maxkeyConfig.isSetUserInfoHeader());
        assertEquals("k", maxkeyConfig.getUserInfoEndpoint());
        assertEquals("l", maxkeyConfig.getIntrospectionEndpointAuthMethodsSupported());
        assertEquals("m", maxkeyConfig.getDiscovery());

        MaxkeyConfig maxkeyConfig1 = new MaxkeyConfig();
        maxkeyConfig1.setClientId("a");
        maxkeyConfig1.setClientSecret("b");
        maxkeyConfig1.setAuthorizationEndpoint("c");
        maxkeyConfig1.setScope("d");
        maxkeyConfig1.setResponseType("e");
        maxkeyConfig1.setRedirectUrl("f");
        maxkeyConfig1.setRealm("g");
        maxkeyConfig1.setGrantType("h");
        maxkeyConfig1.setTokenEndpoint("i");
        maxkeyConfig1.setBearerOnly(false);
        maxkeyConfig1.setIntrospectionEndpoint("j");
        maxkeyConfig1.setSetUserInfoHeader(false);
        maxkeyConfig1.setUserInfoEndpoint("k");
        maxkeyConfig1.setIntrospectionEndpointAuthMethodsSupported("l");
        maxkeyConfig1.setDiscovery("m");
        assertEquals("a", maxkeyConfig.getClientId());
        assertEquals("b", maxkeyConfig.getClientSecret());
        assertEquals("c", maxkeyConfig.getAuthorizationEndpoint());
        assertEquals("d", maxkeyConfig.getScope());
        assertEquals("e", maxkeyConfig.getResponseType());
        assertEquals("f", maxkeyConfig.getRedirectUrl());
        assertEquals("g", maxkeyConfig.getRealm());
        assertEquals("h", maxkeyConfig.getGrantType());
        assertEquals("i", maxkeyConfig.getTokenEndpoint());
        assertFalse(maxkeyConfig.isBearerOnly());
        assertEquals("j", maxkeyConfig.getIntrospectionEndpoint());
        assertFalse(maxkeyConfig.isSetUserInfoHeader());
        assertEquals("k", maxkeyConfig.getUserInfoEndpoint());
        assertEquals("l", maxkeyConfig.getIntrospectionEndpointAuthMethodsSupported());
        assertEquals("m", maxkeyConfig.getDiscovery());
        assertEquals(
                "MaxkeyConfig{clientId='a', "
                + "clientSecret='b', "
                + "authorizationEndpoint='c', "
                + "scope='d', "
                + "responseType='e', "
                + "redirectUrl='f', "
                + "realm='g', "
                + "grantType='h', "
                + "tokenEndpoint='i', "
                + "bearerOnly=false, "
                + "introspectionEndpoint='j', "
                + "setUserInfoHeader=false, "
                + "userInfoEndpoint='k', "
                + "introspectionEndpointAuthMethodsSupported='l', "
                + "discovery='m'}",
                maxkeyConfig1.toString());
    }
}
