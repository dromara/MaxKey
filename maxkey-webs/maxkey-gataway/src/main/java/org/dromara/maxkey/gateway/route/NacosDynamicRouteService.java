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
 

/*
package org.gateway.route;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

@Component
public class NacosDynamicRouteService implements ApplicationEventPublisherAware {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String dataId = "alibaba-gateway-router";
 
    private String group = "DEFAULT_GROUP";
 
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;
 
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
 
    private ApplicationEventPublisher applicationEventPublisher;
 
    private static final List<String> ROUTE_LIST = new ArrayList<>();
 
    @PostConstruct
    public void dynamicRouteByNacosListener() {
        try {
            logger.info("serverAddr:{}",serverAddr);
            logger.info("namespace:{}",namespace);
            logger.info("dataId:{}",dataId);
            logger.info("group:{}",group);
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.put(PropertyKeyConst.NAMESPACE, namespace);

            ConfigService configService = NacosFactory.createConfigService(properties);
            configService.getConfig(dataId, group, 5000);

            // 程序首次启动, 并加载初始化路由配置
            String initConfigInfo = configService.getConfig(dataId, group, 5000);
            addAndPublishBatchRoute(initConfigInfo);
            publish();
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    clearRoute();
                    try {
                        List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
                        for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                            logger.info("routeDefinition:{}",routeDefinition);
                            addRoute(routeDefinition);
                        }
                        publish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            logger.error("error:{}",e);
        }
    }
 
    private void clearRoute() {
        for(String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    private void addAndPublishBatchRoute(String initConfigInfo){
        List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(initConfigInfo, RouteDefinition.class);
        for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
            logger.info("init routeDefinition:{}",routeDefinition);
            addRoute(routeDefinition);
        }
    }
    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_LIST.add(definition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }
 
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
*/



