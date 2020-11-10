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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource(ConstantsProperties.applicationPropertySource)
public class RedisAutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);
    
    /**
     * RedisConnectionFactory. 
     * @param host String
     * @param port int
     * @param timeout int
     * @param password String
     * @param maxActive int
     * @param maxWait int
     * @param maxIdle int
     * @param minIdle int
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnFactory(
            @Value("${spring.redis.host}")
            String host,
            @Value("${spring.redis.port}")
            int port,
            @Value("${spring.redis.timeout}")
            int timeout,
            @Value("${spring.redis.password}")
            String password,
            @Value("${spring.redis.lettuce.pool.max-active}")
            int maxActive,
            @Value("${spring.redis.jedis.pool.max-wait}")
            int maxWait,
            @Value("${spring.redis.jedis.pool.max-idle}")
            int maxIdle,
            @Value("${spring.redis.lettuce.pool.min-idle}")
            int minIdle) {
        _logger.debug("redisConnFactory init .");
        RedisConnectionFactory factory = new RedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeOut(timeout); 
        factory.setPassword(password);
        
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);
        
        factory.setPoolConfig(poolConfig);
        
        return factory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
