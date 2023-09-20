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
import org.apache.shenyu.common.utils.GsonUtils;
import org.apache.shenyu.common.utils.Singleton;
import org.apache.shenyu.plugin.base.handler.PluginDataHandler;
import org.apache.shenyu.plugin.maxkey.config.MaxkeyConfig;
import org.apache.shenyu.plugin.maxkey.service.MaxkeyService;

import java.util.Map;
import java.util.Optional;

public class MaxkeyPluginDataHandler implements PluginDataHandler {

    @Override
    public void handlerPlugin(final PluginData pluginData) {

        // 获取配置参数
        Map<String, String> configMap = GsonUtils.getInstance().toObjectMap(pluginData.getConfig(), String.class);

        final String clientId = Optional.ofNullable(configMap.get("clientId")).orElse("");
        final String clientSecret = Optional.ofNullable(configMap.get("clientSecret")).orElse("");
        final String authorizationEndpoint = Optional.ofNullable(configMap.get("authorizationEndpoint")).orElse("");
        final String scope = Optional.ofNullable(configMap.get("scope")).orElse("");
        final String responseType = Optional.ofNullable(configMap.get("responseType")).orElse("");
        final String redirectUrl = Optional.ofNullable(configMap.get("redirectUrl")).orElse("");
        final String realm = Optional.ofNullable(configMap.get("realm")).orElse("");
        final String grantType = Optional.ofNullable(configMap.get("grantType")).orElse("");
        final String tokenEndpoint = Optional.ofNullable(configMap.get("tokenEndpoint")).orElse("");
        final boolean bearerOnly = Optional.ofNullable(configMap.get("bearerOnly")).map(Boolean::parseBoolean).orElse(false);
        final String introspectionEndpoint = Optional.ofNullable(configMap.get("introspectionEndpoint")).orElse("");
        final String introspectionEndpointAuthMethodsSupported = Optional.ofNullable(configMap.get("introspectionEndpointAuthMethodsSupported")).orElse("");
        final boolean setUserInfoHeader = Optional.ofNullable(configMap.get("setUserInfoHeader")).map(Boolean::parseBoolean).orElse(false);
        final String userInfoEndpoint = Optional.ofNullable(configMap.get("userInfoEndpoint")).orElse("");
        final String discovery = Optional.ofNullable(configMap.get("discovery")).orElse("");

        // 获取MaxkeyConfig
        MaxkeyConfig maxkeyConfig = new MaxkeyConfig(
                clientId,
                clientSecret,
                authorizationEndpoint,
                scope,
                responseType,
                redirectUrl,
                realm,
                grantType,
                tokenEndpoint,
                bearerOnly,
                introspectionEndpoint,
                setUserInfoHeader,
                userInfoEndpoint,
                introspectionEndpointAuthMethodsSupported,
                discovery);

        // 根据参数实例化 MaxkeyService 鉴权服务
        MaxkeyService maxkeyService = new MaxkeyService(maxkeyConfig);
        Singleton.INST.single(MaxkeyService.class, maxkeyService);
    }

    @Override
    public String pluginNamed() {
        return PluginEnum.MAXKEY.getName();
    }

}
