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

import org.dromara.maxkey.authn.support.socialsignon.service.JdbcSocialsAssociateService;
import org.dromara.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.dromara.maxkey.authn.support.socialsignon.token.RedisTokenStore;
import org.dromara.maxkey.entity.SocialsProvider;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
@ComponentScan(basePackages = {
        "org.maxkey.authn.support.socialsignon"
})
public class SocialSignOnAutoConfiguration{
    private static final  Logger _logger = LoggerFactory.getLogger(SocialSignOnAutoConfiguration.class);
    
    @Bean(name = "socialSignOnProviderService")
    @ConditionalOnClass(SocialsProvider.class)
    SocialSignOnProviderService socialSignOnProviderService(
            @Value("${maxkey.server.persistence}") int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
        SocialSignOnProviderService socialSignOnProviderService = new SocialSignOnProviderService(jdbcTemplate);
        //load default Social Providers from database
        socialSignOnProviderService.loadSocials("1");

        RedisTokenStore redisTokenStore = new RedisTokenStore();
        socialSignOnProviderService.setRedisTokenStore(redisTokenStore);

        _logger.debug("SocialSignOnProviderService inited.");
        return socialSignOnProviderService;
    }
    
    @Bean(name = "socialsAssociateService")
    JdbcSocialsAssociateService socialsAssociateService(JdbcTemplate jdbcTemplate) {
        JdbcSocialsAssociateService socialsAssociateService = new JdbcSocialsAssociateService(jdbcTemplate);
        _logger.debug("JdbcSocialsAssociateService inited.");
        return socialsAssociateService;
    }
   
}
