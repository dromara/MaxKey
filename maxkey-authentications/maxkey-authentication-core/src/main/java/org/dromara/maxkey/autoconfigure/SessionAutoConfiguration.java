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
 

package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.session.impl.SessionManagerImpl;
import org.dromara.maxkey.authn.web.HttpSessionListenerAdapter;
import org.dromara.maxkey.authn.web.SavedRequestAwareAuthenticationSuccessHandler;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@AutoConfiguration
public class SessionAutoConfiguration  {
    private static final  Logger _logger = 
            LoggerFactory.getLogger(SessionAutoConfiguration.class);
    
    
    @Bean(name = "savedRequestSuccessHandler")
    SavedRequestAwareAuthenticationSuccessHandler 
            savedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
    
    @Bean
    SessionManager sessionManager(
            @Value("${maxkey.server.persistence}") int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory,
            @Value("${maxkey.auth.session.timeout:1800}") int timeout
            ) {
    	_logger.debug("session timeout {}" , timeout);
        return new SessionManagerImpl(
        		persistence, jdbcTemplate, redisConnFactory,timeout);
    }

    @Bean
    HttpSessionListenerAdapter httpSessionListenerAdapter() {
        return new HttpSessionListenerAdapter();
    }
    
}
