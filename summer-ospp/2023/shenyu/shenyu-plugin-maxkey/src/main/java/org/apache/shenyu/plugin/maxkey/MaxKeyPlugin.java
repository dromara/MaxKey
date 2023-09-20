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

package org.apache.shenyu.plugin.maxkey;

import cn.hutool.core.codec.Base64;
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.common.enums.PluginEnum;
import org.apache.shenyu.common.utils.GsonUtils;
import org.apache.shenyu.common.utils.Singleton;
import org.apache.shenyu.plugin.api.ShenyuPluginChain;
import org.apache.shenyu.plugin.api.result.ShenyuResultEnum;
import org.apache.shenyu.plugin.api.result.ShenyuResultWrap;
import org.apache.shenyu.plugin.api.utils.WebFluxResultUtils;
import org.apache.shenyu.plugin.base.AbstractShenyuPlugin;
import org.apache.shenyu.plugin.maxkey.config.MaxkeyConfig;
import org.apache.shenyu.plugin.maxkey.service.MaxkeyService;
import org.apache.shenyu.plugin.maxkey.service.MaxkeyUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * The type Maxkey plugin.
 */
public class MaxKeyPlugin extends AbstractShenyuPlugin {

    @Override
    protected Mono<Void> doExecute(final ServerWebExchange exchange, final ShenyuPluginChain chain, final SelectorData selector, final RuleData rule) {
        MaxkeyService maxkeyService = Singleton.INST.get(MaxkeyService.class);
        MaxkeyConfig config = maxkeyService.getMaxkeyConfig();
        ServerHttpRequest request = exchange.getRequest();

        // 这里处理需要token的逻辑
        if (config.isBearerOnly()) {
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            boolean isActive = maxkeyService.introspectAccessToken(token);
            if (isActive) {
                // 根据配置决定是否获取userInfo
                return chain.execute(handlerUserInfo(exchange, token, maxkeyService, config.isSetUserInfoHeader()));
            }
            Object error = ShenyuResultWrap.error(exchange, ShenyuResultEnum.ERROR_TOKEN);
            return WebFluxResultUtils.result(exchange, error);
        }

        // 走到这里说明没有token 需要处理code授权码的逻辑
        String code = request.getQueryParams().getFirst("code");
        String state = request.getQueryParams().getFirst("state");
        if (Objects.nonNull(code)) {
            String token = maxkeyService.getOAuthToken(code);
            // 根据配置决定是否获取userInfo
            return chain.execute(handlerUserInfo(exchange, token, maxkeyService, config.isSetUserInfoHeader()));
        }

        // 走到这里说明没有code 需要重定向至IdP服务获取code
        return maxkeyService.redirect(exchange, state);
    }

    @Override
    public int getOrder() {
        return PluginEnum.MAXKEY.getCode();
    }

    @Override
    public String named() {
        return PluginEnum.MAXKEY.getName();
    }

    @Override
    public boolean skip(final ServerWebExchange exchange) {
        return false;
    }

    // 判断直接传递token还是传递userInfo
    private ServerWebExchange handlerUserInfo(final ServerWebExchange exchange, final String token, final MaxkeyService maxkeyService, final boolean setUserInfo) {
        if (setUserInfo) {
            MaxkeyUser maxkeyUser = maxkeyService.getMaxkeyUser(token);
            return handleToken(exchange, maxkeyUser);
        } else {
            return handleToken(exchange, token);
        }
    }

    // 直接使用AccessToken访问收保护的资源
    private ServerWebExchange handleToken(final ServerWebExchange exchange, final String accessToken) {
        ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
        mutate.headers(httpHeaders -> httpHeaders.remove(HttpHeaders.ACCEPT_ENCODING));
        mutate.header(HttpHeaders.AUTHORIZATION, accessToken);
        return exchange.mutate().request(mutate.build()).build();
    }

    // 获取原始请求对象 根据MaxKey认证解析后的userInfo 重新构建请求头 访问收保护的资源
    private ServerWebExchange handleToken(final ServerWebExchange exchange, final MaxkeyUser maxkeyUser) {
        ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
        mutate.headers(httpHeaders -> httpHeaders.remove(HttpHeaders.ACCEPT_ENCODING));
        String maxkeyUserInfoJson = GsonUtils.getInstance().toJson(maxkeyUser);
        mutate.header("X-Userinfo", Base64.encode(maxkeyUserInfoJson));
        return exchange.mutate().request(mutate.build()).build();
    }

}
