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

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.sql.DataSource;

import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2UserDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.approval.TokenApprovalStore;
import org.dromara.maxkey.authz.oauth2.provider.approval.endpoint.OAuth20UserApprovalHandler;
import org.dromara.maxkey.authz.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.client.JdbcClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.code.AuthorizationCodeServices;
import org.dromara.maxkey.authz.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.dromara.maxkey.authz.oauth2.provider.code.RedisAuthorizationCodeServices;
import org.dromara.maxkey.authz.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.dromara.maxkey.authz.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.dromara.maxkey.authz.oauth2.provider.token.TokenStore;
import org.dromara.maxkey.authz.oauth2.provider.token.store.InMemoryTokenStore;
import org.dromara.maxkey.authz.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.dromara.maxkey.authz.oauth2.provider.token.store.RedisTokenStore;
import org.dromara.maxkey.authz.oidc.idtoken.OIDCIdTokenEnhancer;
import org.dromara.maxkey.configuration.oidc.OIDCProviderMetadataDetails;
import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.dromara.maxkey.crypto.jwt.encryption.service.impl.DefaultJwtEncryptionAndDecryptionService;
import org.dromara.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.persistence.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;

import jakarta.servlet.Filter;

@AutoConfiguration
@ComponentScan(basePackages = {
        "org.dromara.maxkey.authz.oauth2.provider.endpoint",
        "org.dromara.maxkey.authz.oauth2.provider.userinfo.endpoint",
        "org.dromara.maxkey.authz.oauth2.provider.approval.controller",
        "org.dromara.maxkey.authz.oauth2.provider.wellknown.endpoint"
})
public class Oauth20AutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(Oauth20AutoConfiguration.class);

    @Bean
    FilterRegistrationBean<Filter> tokenEndpointAuthenticationFilter() {
        _logger.debug("TokenEndpointAuthenticationFilter init ");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TokenEndpointAuthenticationFilter());
        registration.addUrlPatterns(
        							OAuth2Constants.ENDPOINT.ENDPOINT_TOKEN + "/*",
        							OAuth2Constants.ENDPOINT.ENDPOINT_TENCENT_IOA_TOKEN + "/*");
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
    OIDCProviderMetadataDetails oidcProviderMetadata(
            @Value("${maxkey.oidc.metadata.issuer}")
            String issuer,
            @Value("${maxkey.oidc.metadata.authorizationEndpoint}")
            URI authorizationEndpoint,
            @Value("${maxkey.oidc.metadata.tokenEndpoint}")
            URI tokenEndpoint,
            @Value("${maxkey.oidc.metadata.userinfoEndpoint}")
            URI userinfoEndpoint) {
        _logger.debug("OIDC Provider Metadata Details init .");
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
    JWKSetKeyStore jwkSetKeyStore() {
        JWKSetKeyStore jwkSetKeyStore = new JWKSetKeyStore();
        ClassPathResource classPathResource = new ClassPathResource("/config/keystore.jwks");
        jwkSetKeyStore.setLocation(classPathResource);
        _logger.debug("JWKSet KeyStore init.");
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
    DefaultJwtSigningAndValidationService jwtSignerValidationService(
            @Qualifier("jwkSetKeyStore")
            JWKSetKeyStore jwkSetKeyStore)
            throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        DefaultJwtSigningAndValidationService jwtSignerValidationService = 
                new DefaultJwtSigningAndValidationService(jwkSetKeyStore);
        jwtSignerValidationService.setDefaultSignerKeyId("maxkey_rsa");
        jwtSignerValidationService.setDefaultSigningAlgorithmName("RS256");
        _logger.debug("JWT Signer and Validation Service init.");
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
    DefaultJwtEncryptionAndDecryptionService jwtEncryptionService(
            @Qualifier("jwkSetKeyStore")
            JWKSetKeyStore jwkSetKeyStore)
            throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        DefaultJwtEncryptionAndDecryptionService jwtEncryptionService = 
                new DefaultJwtEncryptionAndDecryptionService(jwkSetKeyStore);
        jwtEncryptionService.setDefaultAlgorithm(JWEAlgorithm.RSA_OAEP_256);//RSA1_5
        jwtEncryptionService.setDefaultDecryptionKeyId("maxkey_rsa");
        jwtEncryptionService.setDefaultEncryptionKeyId("maxkey_rsa");
        _logger.debug("JWT Encryption and Decryption Service init.");
        return jwtEncryptionService;
    }

    /**
     * tokenEnhancer.
     * @return
     */
    @Bean(name = "tokenEnhancer")
    OIDCIdTokenEnhancer tokenEnhancer(
            OIDCProviderMetadataDetails oidcProviderMetadata,
            ClientDetailsService oauth20JdbcClientDetailsService) {
        OIDCIdTokenEnhancer tokenEnhancer = new OIDCIdTokenEnhancer();
        tokenEnhancer.setClientDetailsService(oauth20JdbcClientDetailsService);
        tokenEnhancer.setProviderMetadata(oidcProviderMetadata);
        _logger.debug("OIDC IdToken Enhancer init.");
        return tokenEnhancer;
    }

    //以上部分为了支持OpenID Connect 1.0
    
    
    /**
     * AuthorizationCodeServices. 
     * @param persistence int
     * @return oauth20AuthorizationCodeServices
     */
    @Bean(name = "oauth20AuthorizationCodeServices")
    AuthorizationCodeServices oauth20AuthorizationCodeServices(
            @Value("${maxkey.server.persistence}") int persistence,
            RedisConnectionFactory redisConnFactory) {  
        _logger.debug("OAuth 2 Authorization Code Services init.");
        AuthorizationCodeServices authorizationCodeServices = null;
        if (persistence == ConstsPersistence.REDIS) {
            authorizationCodeServices = new RedisAuthorizationCodeServices(redisConnFactory);
            _logger.debug("RedisAuthorizationCodeServices");
        }else {
        	authorizationCodeServices = new InMemoryAuthorizationCodeServices();
            _logger.debug("InMemoryAuthorizationCodeServices");
        }
        return authorizationCodeServices;
    }

    /**
     * TokenStore. 
     * @param persistence int
     * @return oauth20TokenStore
     */
    @Bean(name = "oauth20TokenStore")
    TokenStore oauth20TokenStore(
            @Value("${maxkey.server.persistence}") int persistence,
            RedisConnectionFactory redisConnFactory) {
        _logger.debug("OAuth 2 TokenStore init.");
        TokenStore tokenStore = null;
        if (persistence == ConstsPersistence.REDIS) {
            tokenStore = new RedisTokenStore(redisConnFactory);
            _logger.debug("RedisTokenStore");
        }else {
        	 tokenStore = new InMemoryTokenStore();
             _logger.debug("InMemoryTokenStore");
        }
        return tokenStore;
    }

    /**
     * jwtAccessTokenConverter. 
     * @return converter
     */
    @Bean(name = "converter")
    JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        _logger.debug("OAuth 2 Jwt AccessToken Converter init.");
        return jwtAccessTokenConverter;
    }

    /**
     * clientDetailsService. 
     * @return oauth20JdbcClientDetailsService
     */
    @Bean(name = "oauth20JdbcClientDetailsService")
    JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource, @Qualifier("passwordReciprocal") PasswordEncoder passwordReciprocal) {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        //clientDetailsService.setPasswordEncoder(passwordReciprocal);
        _logger.debug("OAuth 2 Jdbc ClientDetails Service init.");
        return clientDetailsService;
    }

    /**
     * clientDetailsUserDetailsService. 
     * @return oauth20TokenServices
     */
    @Bean(name = "oauth20TokenServices")
    DefaultTokenServices defaultTokenServices(
            JdbcClientDetailsService oauth20JdbcClientDetailsService,
            TokenStore oauth20TokenStore,
            OIDCIdTokenEnhancer tokenEnhancer,
            AppsService appsService,
            SessionManager sessionManager) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(oauth20JdbcClientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer);
        tokenServices.setTokenStore(oauth20TokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAppsService(appsService);
        tokenServices.setSessionManager(sessionManager);
        _logger.debug("OAuth 2 Token Services init.");
        return tokenServices;
    }


    /**
     * TokenApprovalStore. 
     * @return oauth20ApprovalStore
     */
    @Bean(name = "oauth20ApprovalStore")
    TokenApprovalStore tokenApprovalStore(
            TokenStore oauth20TokenStore) {
        TokenApprovalStore tokenApprovalStore = new TokenApprovalStore();
        tokenApprovalStore.setTokenStore(oauth20TokenStore);
        _logger.debug("OAuth 2 Approval Store init.");
        return tokenApprovalStore;
    }


    /**
     * OAuth2RequestFactory. 
     * @return oAuth2RequestFactory
     */
    @Bean(name = "oAuth2RequestFactory")
    DefaultOAuth2RequestFactory oauth2RequestFactory(
            JdbcClientDetailsService oauth20JdbcClientDetailsService) {
        DefaultOAuth2RequestFactory oauth2RequestFactory = 
                new DefaultOAuth2RequestFactory(oauth20JdbcClientDetailsService);
        _logger.debug("OAuth 2 Request Factory init.");
        return oauth2RequestFactory;
    }

    /**
     * OAuth20UserApprovalHandler. 
     * @return oauth20UserApprovalHandler
     */
    @Bean(name = "oauth20UserApprovalHandler")
    OAuth20UserApprovalHandler oauth20UserApprovalHandler(
            JdbcClientDetailsService oauth20JdbcClientDetailsService,
            DefaultOAuth2RequestFactory oAuth2RequestFactory,
            TokenApprovalStore oauth20ApprovalStore
    ) {
        OAuth20UserApprovalHandler userApprovalHandler = new OAuth20UserApprovalHandler();
        userApprovalHandler.setApprovalStore(oauth20ApprovalStore);
        userApprovalHandler.setRequestFactory(oAuth2RequestFactory);
        userApprovalHandler.setClientDetailsService(oauth20JdbcClientDetailsService);
        _logger.debug("OAuth 2 User Approval Handler init.");
        return userApprovalHandler;
    }

    /**
     * ProviderManager. 
     * @return oauth20UserAuthenticationManager
     */
    @Bean(name = "oauth20UserAuthenticationManager")
    ProviderManager oauth20UserAuthenticationManager(
            @Qualifier("passwordEncoder")
            PasswordEncoder passwordEncoder,
            LoginService loginRepository
    ) {
        
        OAuth2UserDetailsService userDetailsService =new OAuth2UserDetailsService();
        userDetailsService.setLoginRepository(loginRepository);
        
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        ProviderManager authenticationManager = new ProviderManager(daoAuthenticationProvider);
        _logger.debug("OAuth 2 User Authentication Manager init.");
        return authenticationManager;
    }

    /**
     * ProviderManager. 
     * @return oauth20ClientAuthenticationManager
     */
    @Bean(name = "oauth20ClientAuthenticationManager")
    ProviderManager oauth20ClientAuthenticationManager(
            JdbcClientDetailsService oauth20JdbcClientDetailsService,
            @Qualifier("passwordReciprocal")
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
