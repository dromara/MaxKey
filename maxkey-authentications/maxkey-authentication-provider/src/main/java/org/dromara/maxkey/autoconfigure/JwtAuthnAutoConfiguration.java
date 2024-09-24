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

import com.nimbusds.jose.JOSEException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.dromara.maxkey.authn.support.jwt.JwtLoginService;
import org.dromara.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.dromara.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;


@AutoConfiguration
public class JwtAuthnAutoConfiguration  {
    private static final  Logger _logger = LoggerFactory.getLogger(JwtAuthnAutoConfiguration.class);

    /**
     * jwt Login JwkSetKeyStore.
     * @return
     */
    @Bean
    JWKSetKeyStore jwtLoginJwkSetKeyStore() {
        JWKSetKeyStore jwkSetKeyStore = new JWKSetKeyStore();
        ClassPathResource classPathResource = new ClassPathResource("/config/loginjwkkeystore.jwks");
        jwkSetKeyStore.setLocation(classPathResource);
        _logger.debug("JWT Login JwkSet KeyStore init.");
        return jwkSetKeyStore;
    }

    /**
     * jwt Login ValidationService.
     * @return
     * @throws JOSEException
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     */
    @Bean
    DefaultJwtSigningAndValidationService jwtLoginValidationService(
            @Qualifier("jwtLoginJwkSetKeyStore") JWKSetKeyStore jwtLoginJwkSetKeyStore)
            throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        DefaultJwtSigningAndValidationService jwtSignerValidationService = 
                new DefaultJwtSigningAndValidationService(jwtLoginJwkSetKeyStore);
        jwtSignerValidationService.setDefaultSignerKeyId("maxkey_rsa");
        jwtSignerValidationService.setDefaultSigningAlgorithmName("RS256");
        _logger.debug("JWT Login Signing and Validation init.");
        return jwtSignerValidationService;
    }

    /**
     * Jwt LoginService.
     * @return
     */
    @Bean
    JwtLoginService jwtLoginService(
            @Value("${maxkey.login.jwt.issuer}")
            String issuer,
            @Qualifier("jwtLoginValidationService")
            DefaultJwtSigningAndValidationService jwtLoginValidationService) {
        JwtLoginService jwtLoginService = new JwtLoginService(
                    jwtLoginValidationService,
                    issuer
                );
        _logger.debug("JWT Login Service init.");
        return jwtLoginService;
    }
}
