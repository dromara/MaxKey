/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * protected urls
 */
@AutoConfiguration
@EnableWebSecurity
public class MvcSecurityAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MvcSecurityAutoConfiguration.class);
    
    static final String [] protectedUrls = {"/actuator","/actuator/**","/swagger-ui","/swagger-ui/**"};
    
    @Value("${spring.security.enabled:true}")
    boolean securityEnabled;
    
    @Value("${spring.security.user.name:maxkey}")
    String username;
    
    @Value("${spring.security.user.password:password}")
    String password;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,@Qualifier("securityProviderManager")ProviderManager securityProviderManager) throws Exception {
        if(this.securityEnabled) {
            http.securityMatcher(protectedUrls)
                 .authenticationManager(securityProviderManager)
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers(protectedUrls).authenticated()
                         )
                 .httpBasic(Customizer.withDefaults());
        }else {
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers(protectedUrls).permitAll()
                );
        }
        logger.debug("init securityFilterChain");
        return http.build();
    }
    
    @Bean(name="securityUserDetailsService")                                                              
    UserDetailsService securityUserDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername(this.username).password(encoder.encode(this.password)).roles("ACTUATOR", "MONITOR").build());
        return userDetailsService;
    }
    
    @Bean(name="securityProviderManager")                                                              
    ProviderManager securityProviderManager(@Qualifier("securityUserDetailsService") UserDetailsService securityUserDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider(securityUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }
    
}
