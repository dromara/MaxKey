package org.maxkey.autoconfigure;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.maxkey.authn.support.jwt.JwtLoginService;
import org.maxkey.config.oidc.OIDCProviderMetadataDetails;
import org.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.maxkey.crypto.jwt.encryption.service.impl.DefaultJwtEncryptionAndDecryptionService;
import org.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;


@Configuration
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/config/applicationConfig.properties")
public class JwtAuthnAutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(JwtAuthnAutoConfiguration.class);
    
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
        _logger.debug("RedisConnectionFactory init .");
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
            OIDCProviderMetadataDetails oidcProviderMetadata) {
        JwtLoginService jwkSetKeyStore = new JwtLoginService();
        jwkSetKeyStore.setJwtSignerValidationService(jwtSignerValidationService);
        jwkSetKeyStore.setJwtProviderMetadata(oidcProviderMetadata);
        return jwkSetKeyStore;
    }
    
 
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
