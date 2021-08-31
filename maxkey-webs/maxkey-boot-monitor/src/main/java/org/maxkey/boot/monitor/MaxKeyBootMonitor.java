package org.maxkey.boot.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@Configuration
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication(
        exclude={
                RedisAutoConfiguration.class,
                DruidDataSourceAutoConfigure.class,
                DataSourceAutoConfiguration.class
})
public class MaxKeyBootMonitor {
    
    private static final Logger _logger = LoggerFactory.getLogger(MaxKeyBootMonitor.class);
    
    public static void main(String[] args) {
        _logger.info("Start MaxKeyBootMonitor ...");
        SpringApplication.run(MaxKeyBootMonitor.class, args);
    }
}
