package org.maxkey;

import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:/application.properties")
public class RedisAutoConfiguration {
    
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
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
}
