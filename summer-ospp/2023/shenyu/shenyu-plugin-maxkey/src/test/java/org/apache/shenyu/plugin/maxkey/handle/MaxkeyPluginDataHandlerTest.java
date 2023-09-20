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

package org.apache.shenyu.plugin.maxkey.handle;

import org.apache.shenyu.common.dto.PluginData;
import org.apache.shenyu.common.enums.PluginEnum;
import org.apache.shenyu.common.utils.Singleton;
import org.apache.shenyu.plugin.maxkey.service.MaxkeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxkeyPluginDataHandlerTest {

    private MaxkeyPluginDataHandler maxkeyPluginDataHandlerTest;

    @BeforeEach
    public void setup() {
        maxkeyPluginDataHandlerTest = new MaxkeyPluginDataHandler();
    }

    @Test
    public void handlerPlugin() {
        final PluginData pluginData = new PluginData(
                "pluginId",
                "pluginName",
                "{\n"
                        + "\t\"clientId\": \"ae20330a-ef0b-4dad-9f10-d5e3485ca2ad\",\n"
                        + "\t\"clientSecret\": \"KQY4MDUwNjIwMjAxNTE3NTM1OTEYty\",\n"
                        + "\t\"authorizationEndpoint\": \"http://192.168.1.16/sign/authz/oauth/v20/authorize\",\n"
                        + "\t\"scope\": \"openid\",\n"
                        + "\t\"responseType\": \"code\",\n"
                        + "\t\"redirectUrl\": \"http://192.168.1.5:9195/http/shenyu/client/hello\",\n"
                        + "\t\"realm\": \"1\",\n"
                        + "\t\"grantType\": \"authorization_code\",\n"
                        + "\t\"tokenEndpoint\": \"http://192.168.1.16/sign/authz/oauth/v20/token\",\n"
                        + "\t\"bearerOnly\": \"false\",\n"
                        + "\t\"introspectionEndpoint\": \"http://192.168.1.16/sign/authz/oauth/v20/introspect\",\n"
                        + "\t\"setUserInfoHeader\": \"false\",\n"
                        + "\t\"userInfoEndpoint\": \"http://192.168.1.16/sign/api/connect/v10/userinfo\",\n"
                        + "\t\"introspectionEndpointAuthMethodsSupported\": \"client_secret_basic\",\n"
                        + "\t\"discovery\": \"http://192.168.1.16/sign/authz/oauth/v20/1/.well-known/openid-configuration\"\n"
                        + "}\n",
                "0",
                false,
                null);
        maxkeyPluginDataHandlerTest.handlerPlugin(pluginData);
        MaxkeyService maxkeyService = Singleton.INST.get(MaxkeyService.class);
    }

    @Test
    public void testPluginNamed() {
        final String result = maxkeyPluginDataHandlerTest.pluginNamed();
        assertEquals(PluginEnum.MAXKEY.getName(), result);
    }
}
