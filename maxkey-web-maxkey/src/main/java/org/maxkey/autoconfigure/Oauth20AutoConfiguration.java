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

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.support.jwt.JwtLoginService;
import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.authz.oauth2.provider.OAuth2UserDetailsService;
import org.maxkey.authz.oauth2.provider.approval.TokenApprovalStore;
import org.maxkey.authz.oauth2.provider.approval.controller.OAuth20UserApprovalHandler;
import org.maxkey.authz.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.maxkey.authz.oauth2.provider.client.JdbcClientDetailsService;
import org.maxkey.authz.oauth2.provider.code.AuthorizationCodeServices;
import org.maxkey.authz.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.maxkey.authz.oauth2.provider.code.RedisAuthorizationCodeServices;
import org.maxkey.authz.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.maxkey.authz.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.maxkey.authz.oauth2.provider.token.TokenStore;
import org.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.maxkey.authz.oauth2.provider.token.store.InMemoryTokenStore;
import org.maxkey.authz.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.maxkey.authz.oauth2.provider.token.store.RedisTokenStore;
import org.maxkey.authz.oidc.idtoken.OIDCIdTokenEnhancer;
import org.maxkey.configuration.oidc.OIDCProviderMetadataDetails;
import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.maxkey.crypto.jwt.encryption.service.impl.DefaultJwtEncryptionAndDecryptionService;
import org.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.maxkey.persistence.db.LoginService;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;

@Configuration
@ComponentScan(basePackages = {
        "org.maxkey.authz.oauth2.provider.endpoint",
        "org.maxkey.authz.oauth2.provider.userinfo.endpoint",
        "org.maxkey.authz.oauth2.provider.approval.controller"
})
@PropertySource(ConstantsProperties.applicationPropertySource)
@PropertySource(ConstantsProperties.maxKeyPropertySource)
public class Oauth20AutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(Oauth20AutoConfiguration.class);
    
    @Bean
    public FilterRegistrationBean<Filter> TokenEndpointAuthenticationFilter() {
        _logger.debug("TokenEndpointAuthenticationFilter init ");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setFilter(new TokenEndpointAuthenticationFilter());
        registration.addUrlPatterns("/oauth/v20/token/*");
        registration.setName("TokenEndpointAuthenticationFilter");
        registration.setOrder(1);
        return registration;
    }
    
    /**
     * OIDCProviderMetadataDetails. 
     * Self-issued Provider Metadata
     * http://openid.net/specs/openid-connect-core-1_0.html#SelfIssued 
     */
    @Bean(name = "oidcProviderMetadata")
    public OIDCProviderMetadataDetails OIDCProviderMetadataDetails(
            @Value("${config.oidc.metadata.issuer}")
            String issuer,
            @Value("${config.oidc.metadata.authorizationEndpoint}")
            URI authorizationEndpoint,
            @Value("${config.oidc.metadata.tokenEndpoint}")
            URI tokenEndpoint,
            @Value("${config.oidc.metadata.userinfoEndpoint}")
            URI userinfoEndpoint) {
        _logger.debug("OIDCProviderMetadataDetails init .");
        OIDCProviderMetadataDetails oidcProviderMetadata = new OIDCProviderMetadataDetails();
        oidcProviderMetadata.setIssuer(issuer);
        oidcProviderMetadata.setAuthorizationEndpoint(authorizationEndpoint);
        oidcProviderMetadata.setTokenEndpoint(tokenEndpoint);
        oidcProviderMetadata.setUserinfoEndpoint(userinfoEndpoint);
        return oidcProviderMetadata;
    }

    /**
     * jwtSetKeyStore.
     * @return
     */
    @Bean(name = "jwkSetKeyStore")
    public JWKSetKeyStore jwtSetKeyStore() {
        JWKSetKeyStore jwkSetKeyStore = new JWKSetKeyStore();
        ClassPathResource classPathResource = new ClassPathResource("/config/keystore.jwks");
        jwkSetKeyStore.setLocation(classPathResource);
        return jwkSetKeyStore;
    }
    
    /**
     * jwtSetKeyStore.
     * @return
     * @throws JOSEException
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     */
    @Bean(name = "jwtSignerValidationService")
    public DefaultJwtSigningAndValidationService jwtSignerValidationService(
            JWKSetKeyStore jwtSetKeyStore) 
                    throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        DefaultJwtSigningAndValidationService jwtSignerValidationService = 
                new DefaultJwtSigningAndValidationService(jwtSetKeyStore);
        jwtSignerValidationService.setDefaultSignerKeyId("maxkey_rsa");
        jwtSignerValidationService.setDefaultSigningAlgorithmName("RS256");
        return jwtSignerValidationService;
    }
    
    /**
     * jwtSetKeyStore.
     * @return
     * @throws JOSEException 
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     */
    @Bean(name = "jwtEncryptionService")
    public DefaultJwtEncryptionAndDecryptionService jwtEncryptionService(
            JWKSetKeyStore jwtSetKeyStore) 
                    throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        DefaultJwtEncryptionAndDecryptionService jwtEncryptionService = 
                new DefaultJwtEncryptionAndDecryptionService(jwtSetKeyStore);
        jwtEncryptionService.setDefaultAlgorithm(JWEAlgorithm.RSA1_5);//RSA1_5
        jwtEncryptionService.setDefaultDecryptionKeyId("maxkey_rsa");
        jwtEncryptionService.setDefaultEncryptionKeyId("maxkey_rsa");
        return jwtEncryptionService;
    }
    
    /**
     * JwtLoginService.
     * @return
     */
    @Bean(name = "jwtLoginService")
    public JwtLoginService jwtLoginService(
            DefaultJwtSigningAndValidationService jwtSignerValidationService,
            OIDCProviderMetadataDetails oidcProviderMetadata,
            AbstractAuthenticationProvider authenticationProvider) {
        
        JwtLoginService jwtLoginService = new JwtLoginService(
                authenticationProvider,
                oidcProviderMetadata,
                jwtSignerValidationService
                );
        return jwtLoginService;
    }
    
    
    /**
     * tokenEnhancer.
     * @return
     */
    @Bean(name = "tokenEnhancer")
    public OIDCIdTokenEnhancer tokenEnhancer(
            DefaultJwtSigningAndValidationService jwtSignerValidationService,
            DefaultJwtEncryptionAndDecryptionService jwtEncryptionService,
            OIDCProviderMetadataDetails oidcProviderMetadata,
            ClientDetailsService oauth20JdbcClientDetailsService) {
        OIDCIdTokenEnhancer tokenEnhancer = new OIDCIdTokenEnhancer();
        tokenEnhancer.setJwtSignerService(jwtSignerValidationService);
        tokenEnhancer.setJwtEnDecryptionService(jwtEncryptionService);
        tokenEnhancer.setClientDetailsService(oauth20JdbcClientDetailsService);
        tokenEnhancer.setProviderMetadata(oidcProviderMetadata);
        return tokenEnhancer;
    }
    //以上部分为了支持OpenID Connect 1.0
    
    
    /**
     * AuthorizationCodeServices. 
     * @param persistence int
     * @return oauth20AuthorizationCodeServices
     */
    @Bean(name = "oauth20AuthorizationCodeServices")
    public AuthorizationCodeServices oauth20AuthorizationCodeServices(
            @Value("${config.server.persistence}") int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
        AuthorizationCodeServices authorizationCodeServices = null;
        if (persistence == ConstantsPersistence.INMEMORY) {
            authorizationCodeServices = new InMemoryAuthorizationCodeServices();
            _logger.debug("InMemoryAuthorizationCodeServices");
        } else if (persistence == ConstantsPersistence.JDBC) {
            //authorizationCodeServices = new JdbcAuthorizationCodeServices(jdbcTemplate);
            _logger.debug("JdbcAuthorizationCodeServices not support "); 
        } else if (persistence == ConstantsPersistence.REDIS) {
            authorizationCodeServices = new RedisAuthorizationCodeServices(redisConnFactory);
            _logger.debug("RedisAuthorizationCodeServices");
        }
        return authorizationCodeServices;
    }
    
    /**
     * TokenStore. 
     * @param persistence int
     * @return oauth20TokenStore
     */
    @Bean(name = "oauth20TokenStore")
    public TokenStore oauth20TokenStore(
            @Value("${config.server.persistence}") int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
        TokenStore tokenStore = null;
        if (persistence == ConstantsPersistence.INMEMORY) {
            tokenStore = new InMemoryTokenStore();
            _logger.debug("InMemoryTokenStore");
        } else if (persistence == ConstantsPersistence.JDBC) {
            //tokenStore = new JdbcTokenStore(jdbcTemplate);
            _logger.debug("JdbcTokenStore not support "); 
        } else if (persistence == ConstantsPersistence.REDIS) {
            tokenStore = new RedisTokenStore(redisConnFactory);
            _logger.debug("RedisTokenStore");
        }
        return tokenStore;
    }
    
    /**
     * jwtAccessTokenConverter. 
     * @return converter
     */
    @Bean(name = "converter")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        return jwtAccessTokenConverter;
    }
    
    /**
     * clientDetailsService. 
     * @return oauth20JdbcClientDetailsService
     */
    @Bean(name = "oauth20JdbcClientDetailsService")
    public JdbcClientDetailsService clientDetailsService(DataSource dataSource,PasswordEncoder passwordReciprocal) {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordReciprocal);
        return clientDetailsService;
    }    
    
    /**
     * clientDetailsUserDetailsService. 
     * @return oauth20TokenServices
     */
    @Bean(name = "oauth20TokenServices")
    public DefaultTokenServices DefaultTokenServices(
            JdbcClientDetailsService oauth20JdbcClientDetailsService,
            TokenStore oauth20TokenStore,
            OIDCIdTokenEnhancer tokenEnhancer) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(oauth20JdbcClientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer);
        tokenServices.setTokenStore(oauth20TokenStore);
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }
    
    
    /**
     * TokenApprovalStore. 
     * @return oauth20ApprovalStore
     */
    @Bean(name = "oauth20ApprovalStore")
    public TokenApprovalStore tokenApprovalStore(
            TokenStore oauth20TokenStore) {
        TokenApprovalStore tokenApprovalStore = new TokenApprovalStore();
        tokenApprovalStore.setTokenStore(oauth20TokenStore);
        return tokenApprovalStore;
    }
    
    
    /**
     * OAuth2RequestFactory. 
     * @return oAuth2RequestFactory
     */
    @Bean(name = "oAuth2RequestFactory")
    public DefaultOAuth2RequestFactory oauth2RequestFactory(
            JdbcClientDetailsService oauth20JdbcClientDetailsService) {
        DefaultOAuth2RequestFactory oauth2RequestFactory = 
                new DefaultOAuth2RequestFactory(oauth20JdbcClientDetailsService);
        return oauth2RequestFactory;
    }
    
    /**
     * OAuth20UserApprovalHandler. 
     * @return oauth20UserApprovalHandler
     */
    @Bean(name = "oauth20UserApprovalHandler")
    public OAuth20UserApprovalHandler oauth20UserApprovalHandler(
            JdbcClientDetailsService oauth20JdbcClientDetailsService,
            DefaultOAuth2RequestFactory oAuth2RequestFactory,
            TokenApprovalStore oauth20ApprovalStore
            ) {
        OAuth20UserApprovalHandler userApprovalHandler = new OAuth20UserApprovalHandler();
        userApprovalHandler.setApprovalStore(oauth20ApprovalStore);
        userApprovalHandler.setRequestFactory(oAuth2RequestFactory);
        userApprovalHandler.setClientDetailsService(oauth20JdbcClientDetailsService);
        return userApprovalHandler;
    }
    
    /**
     * ProviderManager. 
     * @return oauth20UserAuthenticationManager
     */
    @Bean(name = "oauth20UserAuthenticationManager")
    public ProviderManager oauth20UserAuthenticationManager(
            PasswordEncoder passwordEncoder,
            LoginService loginService
            ) {
        
        OAuth2UserDetailsService userDetailsService =new OAuth2UserDetailsService();
        userDetailsService.setLoginService(loginService);
        
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        ProviderManager authenticationManager = new ProviderManager(daoAuthenticationProvider);
        return authenticationManager;
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
        return authenticationManager;
    }
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
