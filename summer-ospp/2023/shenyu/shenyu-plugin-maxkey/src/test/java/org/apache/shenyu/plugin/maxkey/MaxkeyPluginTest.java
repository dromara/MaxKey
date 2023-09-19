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

import org.apache.shenyu.common.dto.PluginData;
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.common.enums.PluginEnum;
import org.apache.shenyu.common.utils.Singleton;
import org.apache.shenyu.plugin.api.ShenyuPluginChain;
import org.apache.shenyu.plugin.api.result.DefaultShenyuResult;
import org.apache.shenyu.plugin.api.result.ShenyuResult;
import org.apache.shenyu.plugin.api.utils.SpringBeanUtils;
import org.apache.shenyu.plugin.maxkey.config.MaxkeyConfig;
import org.apache.shenyu.plugin.maxkey.handle.MaxkeyPluginDataHandler;
import org.apache.shenyu.plugin.maxkey.service.MaxkeyService;
import org.apache.shenyu.plugin.maxkey.service.MaxkeyUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MaxkeyPluginTest {

    @Spy
    private MaxKeyPlugin maxkeyPluginTest;

    @Spy
    private MaxkeyPluginDataHandler maxkeyPluginDataHandlerTest;

    private ServerWebExchange exchange;

    @Mock
    private ShenyuPluginChain chain;

    @Mock
    private SelectorData selector;

    @Mock
    private RuleData rule;

    @BeforeEach
    void setup() {
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        when(context.getBean(ShenyuResult.class)).thenReturn(new DefaultShenyuResult());
        SpringBeanUtils springBeanUtils = SpringBeanUtils.getInstance();
        springBeanUtils.setApplicationContext(context);
        MockitoAnnotations.openMocks(this);
        // 模拟请求
        exchange = MockServerWebExchange
                .from(MockServerHttpRequest
                .get("localhost")
                .header(HttpHeaders.AUTHORIZATION, "25c8d6a6-ad3a-4767-8bfb-d80641b8dfdc")
                .build());
    }

    @Test
    void doExecute() {
        final PluginData pluginData = new PluginData(
                "pluginId",
                "pluginName",
                "{\n"
                        + "\"clientId\": \"ae20330a-ef0b-4dad-9f10-d5e3485ca2ad\",\n"
                        + "\"clientSecret\": \"KQY4MDUwNjIwMjAxNTE3NTM1OTEYty\",\n"
                        + "\"authorizationEndpoint\": \"http://192.168.1.16/sign/authz/oauth/v20/authorize\",\n"
                        + "\"scope\": \"openid\",\n"
                        + "\"responseType\": \"code\",\n"
                        + "\"redirectUrl\": \"http://192.168.1.5:9195/http/shenyu/client/hello\",\n"
                        + "\"realm\": \"1\",\n"
                        + "\"grantType\": \"authorization_code\",\n"
                        + "\"tokenEndpoint\": \"http://192.168.1.16/sign/authz/oauth/v20/token\",\n"
                        + "\"bearerOnly\": \"true\",\n"
                        + "\"introspectionEndpoint\": \"http://192.168.1.16/sign/authz/oauth/v20/introspect\",\n"
                        + "\"setUserInfoHeader\": \"true\",\n"
                        + "\"userInfoEndpoint\": \"http://192.168.1.16/sign/api/connect/v10/userinfo\",\n"
                        + "\"introspectionEndpointAuthMethodsSupported\": \"client_secret_basic\",\n"
                        + "\"discovery\": \"http://192.168.1.16/sign/authz/oauth/v20/1/.well-known/openid-configuration\"\n"
                        + "}",
                "0",
                false,
                null);
        // 测试数据同步
        maxkeyPluginDataHandlerTest.handlerPlugin(pluginData);

        // 模拟服务
        MaxkeyService maxkeyService = mock(MaxkeyService.class);
        MaxkeyConfig maxkeyConfig = mock(MaxkeyConfig.class);
        when(maxkeyService.getMaxkeyConfig()).thenReturn(maxkeyConfig);

        exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("localhost")
                .queryParam("state", "state")
                .queryParam("code", "code")
                .header(HttpHeaders.AUTHORIZATION, "token")
                .build());

        // 先测试bearerOnly模式和getUserInfo模式
        final String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // 处理 MaxkeyUser
        MaxkeyUser maxkeyUser = new MaxkeyUser();
        maxkeyUser.setAddress(new MaxkeyUser.Address());
        Mockito.when(maxkeyService.getMaxkeyUser(token)).thenReturn(maxkeyUser);

        // 模拟 Maxkey认证服务执行
        Singleton.INST.single(MaxkeyService.class, maxkeyService);
        // 认证执行之后 返回一个异步任务
        when(this.chain.execute(any())).thenReturn(Mono.empty());
        Mono<Void> mono = maxkeyPluginTest.doExecute(exchange, chain, selector, rule);
        StepVerifier.create(mono).expectSubscription().verifyComplete();

        // 再测试code模式
        maxkeyService = Singleton.INST.get(MaxkeyService.class);
        maxkeyConfig = maxkeyService.getMaxkeyConfig();
        maxkeyConfig.setBearerOnly(false);

        exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("localhost")
                .queryParam("state", "state")
                .queryParam("code", "code")
                .build());
        Mockito.when(maxkeyService.getOAuthToken("code")).thenReturn(token);
        Singleton.INST.single(MaxkeyService.class, maxkeyService);
        mono = maxkeyPluginTest.doExecute(exchange, chain, selector, rule);
        StepVerifier.create(mono).expectSubscription().verifyComplete();
    }

    @Test
    public void testNamed() {
        final String result = maxkeyPluginTest.named();
        Assertions.assertEquals(PluginEnum.MAXKEY.getName(), result);
    }

    @Test
    public void testGetOrder() {
        final int result = maxkeyPluginTest.getOrder();
        Assertions.assertEquals(PluginEnum.MAXKEY.getCode(), result);
    }

    @Test
    public void skipTest() {
        Assumptions.assumeFalse(maxkeyPluginTest.skip(exchange));
    }

}
