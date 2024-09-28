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
 

package org.dromara.maxkey.autoconfigure;

import java.time.Duration;

import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPoolConfig;

@AutoConfiguration
public class RedisAutoConfiguration {
    static final  Logger _logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);

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
    RedisConnectionFactory redisConnFactory(
            @Value("${spring.redis.host}")
            String host,
            @Value("${spring.redis.port:6379}")
            int port,
            @Value("${spring.redis.timeout:10000}")
            int timeout,
            @Value("${spring.redis.password}")
            String password,
            @Value("${spring.redis.lettuce.pool.max-active:-1}")
            int maxActive,
            @Value("${spring.redis.jedis.pool.max-wait:1000}")
            int maxWait,
            @Value("${spring.redis.jedis.pool.max-idle:100}")
            int maxIdle,
            @Value("${spring.redis.lettuce.pool.min-idle:0}")
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
        poolConfig.setMaxWait(Duration.ofMillis(maxWait));
        
        factory.setPoolConfig(poolConfig);
        
        return factory;
    }

}
