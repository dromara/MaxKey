/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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

package org.dromara.maxkey.passkey.config;

import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialType;
// import com.webauthn4j.validator.WebAuthnRegistrationContextValidator;
// import com.webauthn4j.validator.WebAuthnAuthenticationContextValidator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * WebAuthn4J 配置类
 */
@Configuration
@EnableConfigurationProperties(PasskeyProperties.class)
public class WebAuthnConfig {

    /**
     * WebAuthn Manager Bean
     * 用于处理 WebAuthn 注册和认证的核心组件
     */
    @Bean
    WebAuthnManager webAuthnManager() {
        return WebAuthnManager.createNonStrictWebAuthnManager();
    }

    /**
     * ObjectConverter Bean
     * 用于 WebAuthn 数据的序列化和反序列化
     */
    @Bean
    ObjectConverter objectConverter() {
        return new ObjectConverter();
    }

    /**
     * 支持的公钥凭据参数
     * 定义支持的算法类型
     */
    @Bean
    List<PublicKeyCredentialParameters> publicKeyCredentialParameters() {
        return Arrays.asList(
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.ES256),
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.RS256),
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.PS256)
        );
    }
}