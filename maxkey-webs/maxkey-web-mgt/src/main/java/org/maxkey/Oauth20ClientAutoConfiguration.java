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
 

package org.maxkey;

import javax.sql.DataSource;

import org.maxkey.authz.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.maxkey.authz.oauth2.provider.client.JdbcClientDetailsService;
import org.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.maxkey.authz.oauth2.provider.token.TokenStore;
import org.maxkey.authz.oauth2.provider.token.store.InMemoryTokenStore;
import org.maxkey.authz.oauth2.provider.token.store.RedisTokenStore;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * like Oauth20AutoConfiguration for mgmt
 * @author Crystal.Sea
 *
 */
@Configuration
public class Oauth20ClientAutoConfiguration  implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(Oauth20ClientAutoConfiguration.class);
    
    @Bean(name = "oauth20JdbcClientDetailsService")
    public JdbcClientDetailsService jdbcClientDetailsService(
                DataSource dataSource,PasswordEncoder passwordReciprocal) {
	    JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
	    clientDetailsService.setPasswordEncoder(passwordReciprocal);
	    _logger.debug("JdbcClientDetailsService inited.");
        return clientDetailsService;
    }
	
    /**
     * TokenStore. 
     * @param persistence int
     * @return oauth20TokenStore
     */
    @Bean(name = "oauth20TokenStore")
    public TokenStore oauth20TokenStore(
            @Value("${maxkey.server.persistence}") int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory jedisConnectionFactory) {
        TokenStore tokenStore = null;
        if (persistence == 2) {
            tokenStore = new RedisTokenStore(jedisConnectionFactory);
            _logger.debug("RedisTokenStore");
        }else {
            tokenStore = new InMemoryTokenStore();
            _logger.debug("InMemoryTokenStore"); 
        }
        
        return tokenStore;
    }
    
    /**
     * clientDetailsUserDetailsService. 
     * @return oauth20TokenServices
     */
    @Bean(name = "oauth20TokenServices")
    public DefaultTokenServices defaultTokenServices(
            JdbcClientDetailsService oauth20JdbcClientDetailsService,
            TokenStore oauth20TokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(oauth20JdbcClientDetailsService);
        tokenServices.setTokenStore(oauth20TokenStore);
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }
    
    /**
     * ProviderManager. 
     * @return oauth20ClientAuthenticationManager
     */
    @Bean(name = "oauth20ClientAuthenticationManager")
    public ProviderManager oauth20ClientAuthenticationManager(
            JdbcClientDetailsService oauth20JdbcClientDetailsService,
            PasswordEncoder passwordReciprocal
            ) {
        
        ClientDetailsUserDetailsService cientDetailsUserDetailsService = 
                new ClientDetailsUserDetailsService(oauth20JdbcClientDetailsService);
        
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordReciprocal);
        daoAuthenticationProvider.setUserDetailsService(cientDetailsUserDetailsService);
        ProviderManager authenticationManager = new ProviderManager(daoAuthenticationProvider);
        _logger.debug("OAuth 2 Client Authentication Manager init.");
        return authenticationManager;
    }
  
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

}
