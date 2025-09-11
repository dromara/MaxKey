/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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

package org.dromara.maxkey.passkey.autoconfigure;

import org.dromara.maxkey.passkey.service.PasskeyService;
import org.dromara.maxkey.passkey.service.impl.PasskeyServiceImpl;
import org.dromara.maxkey.passkey.manager.PasskeyManager;
import org.dromara.maxkey.passkey.config.PasskeyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Passkey自动配置类
 */
@Configuration
@EnableConfigurationProperties(PasskeyProperties.class)
@EnableScheduling
@ComponentScan(basePackages = "org.dromara.maxkey.passkey")
@ConditionalOnProperty(prefix = "maxkey.passkey", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PasskeyAutoConfiguration {
    private static final Logger _logger = LoggerFactory.getLogger(PasskeyAutoConfiguration.class);
    
    @Bean
    @ConditionalOnMissingBean
    public PasskeyService passkeyService(PasskeyProperties passkeyProperties) {
        _logger.debug("Creating PasskeyService bean with properties: {}", passkeyProperties.isEnabled());
        return new PasskeyServiceImpl();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public PasskeyManager passkeyManager() {
        _logger.debug("Creating PasskeyManager bean");
        return new PasskeyManager();
    }
    
    /**
     * 初始化日志
     */
    public PasskeyAutoConfiguration() {
        _logger.info("MaxKey Passkey module is being initialized");
    }
}