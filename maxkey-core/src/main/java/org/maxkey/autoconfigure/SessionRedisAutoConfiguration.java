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
 

package org.maxkey.autoconfigure;

import org.maxkey.constants.ConstantsProperties;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@ConditionalOnProperty(value = "spring.session.store-type", havingValue = "redis", matchIfMissing = false)
@EnableRedisHttpSession
@PropertySource(ConstantsProperties.applicationPropertySource)
public class SessionRedisAutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(SessionRedisAutoConfiguration.class);
    
    private final RedisConnectionFactory redisConnectionFactory;

    public SessionRedisAutoConfiguration(ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory.getIfAvailable();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        _logger.debug("CookieSerializer Default .");
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID"); 
        serializer.setCookiePath("/"); 
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
        return serializer;
    }
    
   

 
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
