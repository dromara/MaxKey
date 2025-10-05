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

package org.dromara.maxkey.passkey.service.impl;

import org.dromara.maxkey.passkey.service.PasskeyService;
import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.dromara.maxkey.entity.passkey.PasskeyChallenge;
import org.dromara.maxkey.passkey.config.PasskeyProperties;
import org.dromara.maxkey.persistence.service.UserPasskeyService;
import org.dromara.maxkey.persistence.service.PasskeyChallengeService;
import org.dromara.maxkey.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

// WebAuthn4J imports
import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.*;
import com.webauthn4j.data.client.*;
import com.webauthn4j.data.attestation.*;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.data.RegistrationData;
import com.webauthn4j.data.RegistrationParameters;
import com.webauthn4j.data.AuthenticationData;
import com.webauthn4j.data.AuthenticationParameters;
import com.webauthn4j.verifier.exception.VerificationException;
import com.webauthn4j.credential.CredentialRecord;
import com.webauthn4j.credential.CredentialRecordImpl;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.authenticator.COSEKey;
import com.webauthn4j.data.attestation.authenticator.AAGUID;

// Passkey utility imports
import org.dromara.maxkey.passkey.util.PasskeyUtils;

import java.util.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.codec.binary.Base64;
import java.util.Objects;

/**
 * Passkey服务实现类 - 重构版本
 * 通过方法拆分和工具类提取，提高代码可维护性和可读性
 */
@Service
public class PasskeyServiceImpl implements PasskeyService {
    private static final Logger _logger = LoggerFactory.getLogger(PasskeyServiceImpl.class);
    
    // 常量定义
    private static final String CHALLENGE_TYPE_REGISTRATION = "REGISTRATION";
    private static final String CHALLENGE_TYPE_AUTHENTICATION = "AUTHENTICATION";
    private static final String CREDENTIAL_TYPE_PUBLIC_KEY = "public-key";
    private static final String DEFAULT_INST_ID = "1";
    private static final String DEFAULT_DEVICE_NAME = "Passkey 设备";
    
    @Autowired
    private WebAuthnManager webAuthnManager;
    
    @Autowired
    private ObjectConverter objectConverter;
    
    @Autowired
    private PasskeyProperties passkeyProperties;
    
    @Autowired
    private List<PublicKeyCredentialParameters> publicKeyCredentialParameters;
    
    @Autowired
    private UserPasskeyService userPasskeyService;
    
    @Autowired
    private PasskeyChallengeService passkeyChallengeService;
    
    private final SecureRandom secureRandom = new SecureRandom();
    private final IdGenerator idGenerator = new IdGenerator();
    
    @Override
    public Map<String, Object> generateRegistrationOptions(String userId, String username, String displayName) {
        _logger.debug("Generating registration options for user: {}", userId);
        
        try {
            // 生成并保存挑战
            String challengeId = generateAndSaveChallenge(userId, CHALLENGE_TYPE_REGISTRATION);
            String challengeBase64 = getChallenge(challengeId).getChallenge();
            
            // 构建注册选项
            Map<String, Object> options = buildRegistrationOptions(userId, username, displayName, challengeId, challengeBase64);
            
            _logger.debug("Registration options generated successfully for user: {}", userId);
            return options;
            
        } catch (Exception e) {
            _logger.error("Error generating registration options for user: {}", userId, e);
            return null;
        }
    }
    
    /**
     * 生成并保存挑战
     */
    private String generateAndSaveChallenge(String userId, String challengeType) {
        PasskeyChallenge passkeyChallenge = PasskeyUtils.generateChallenge(
            userId, challengeType, passkeyProperties.getChallenge().getLength());
        passkeyChallenge.setInstId(DEFAULT_INST_ID);
        passkeyChallengeService.saveChallenge(passkeyChallenge);
        
        return passkeyChallenge.getId();
    }
    
    /**
     * 构建注册选项
     */
    private Map<String, Object> buildRegistrationOptions(String userId, String username, String displayName, 
                                                         String challengeId, String challengeBase64) {
        Map<String, Object> options = new HashMap<>();
        options.put("challenge", challengeBase64);
        options.put("challengeId", challengeId);
        options.put("timeout", passkeyProperties.getChallenge().getTimeoutMs());
        options.put("attestation", passkeyProperties.getAuthenticator().getAttestation());
        
        // RP信息
        options.put("rp", buildRelyingPartyInfo());
        
        // 用户信息
        options.put("user", buildUserInfo(userId, username, displayName));
        
        // 公钥凭据参数
        options.put("pubKeyCredParams", buildPublicKeyCredentialParams());
        
        // 认证器选择标准
        options.put("authenticatorSelection", buildAuthenticatorSelection());
        
        // 排除凭据
        List<Map<String, Object>> excludeCredentials = buildExcludeCredentials(userId);
        if (!excludeCredentials.isEmpty()) {
            options.put("excludeCredentials", excludeCredentials);
        }
        
        return options;
    }
    
    /**
     * 构建RP信息
     */
    private Map<String, Object> buildRelyingPartyInfo() {
        return PasskeyUtils.buildRelyingPartyInfo(passkeyProperties.getRelyingParty());
    }
    
    /**
     * 构建用户信息
     */
    private Map<String, Object> buildUserInfo(String userId, String username, String displayName) {
        return PasskeyUtils.buildUserInfo(userId, username, displayName);
    }
    
    /**
     * 构建公钥凭据参数
     */
    private List<Map<String, Object>> buildPublicKeyCredentialParams() {
        return PasskeyUtils.buildPublicKeyCredentialParams(publicKeyCredentialParameters);
    }
    
    /**
     * 构建认证器选择标准
     */
    private Map<String, Object> buildAuthenticatorSelection() {
        return PasskeyUtils.buildAuthenticatorSelection(passkeyProperties.getAuthenticator());
    }
    
    /**
     * 构建排除凭据列表
     */
    private List<Map<String, Object>> buildExcludeCredentials(String userId) {
        List<UserPasskey> existingPasskeys = userPasskeyService.findByUserId(userId);
        return PasskeyUtils.buildCredentialList(existingPasskeys);
    }
    
    @Override
    public UserPasskey verifyRegistrationResponse(String userId, Map<String, Object> registrationResponse) {
        _logger.debug("Verifying registration response for user: {}", userId);
        
        try {
            // 验证挑战
            PasskeyChallenge challenge = validateChallenge(registrationResponse, CHALLENGE_TYPE_REGISTRATION);
            if (challenge == null) {
                _logger.warn("Invalid or expired registration challenge for user: {}", userId);
                return null;
            }
            
            // 解析注册响应数据
            RegistrationResponseData responseData = parseRegistrationResponse(registrationResponse);
            if (responseData == null) {
                _logger.warn("Failed to parse registration response for user: {}", userId);
                return null;
            }
            
            // 创建服务器属性
            ServerProperty serverProperty = createServerProperty(responseData.clientDataJSON, challenge.getChallenge());
            if (serverProperty == null) {
                return null;
            }
            
            // 执行WebAuthn验证
            RegistrationData registrationData = performRegistrationVerification(responseData, serverProperty);
            if (registrationData == null) {
                return null;
            }
            
            // 创建并保存Passkey
            UserPasskey userPasskey = createUserPasskey(userId, responseData.credentialIdBase64, registrationData);
            boolean saved = savePasskey(userPasskey);
            
            // 标记挑战为已使用
            challenge.setStatus(1);
            passkeyChallengeService.saveChallenge(challenge);
            
            _logger.debug("Registration verification completed for user: {}, result: {}", userId, saved);
            return saved ? userPasskey : null;
            
        } catch (VerificationException e) {
            _logger.error("WebAuthn validation failed for user: {}", userId, e);
            return null;
        } catch (Exception e) {
            _logger.error("Error verifying registration response for user: {}", userId, e);
            return null;
        }
    }
    
    /**
     * 注册响应数据结构
     */
    private static class RegistrationResponseData {
        String credentialIdBase64;
        String attestationObjectBase64;
        String clientDataJSONBase64;
        byte[] clientDataJSON;
    }
    
    /**
     * 验证挑战
     */
    private PasskeyChallenge validateChallenge(Map<String, Object> response, String expectedType) {
        String challengeId = (String) response.get("challengeId");
        _logger.debug("Validating challenge with ID: {} and expected type: {}", challengeId, expectedType);
        
        PasskeyChallenge challenge = passkeyChallengeService.findByChallengeId(challengeId);
        
        if (challenge == null) {
            _logger.warn("Challenge not found for ID: {}", challengeId);
            return null;
        }
        
        _logger.debug("Challenge found: {}", challenge.toString());
        _logger.debug("Challenge expired: {}, Challenge type: {}, Expected type: {}, Status: {}", 
                     challenge.isExpired(), challenge.getChallengeType(), expectedType, challenge.getStatus());
        
        if (challenge.isExpired()) {
            _logger.warn("Challenge expired for ID: {}", challengeId);
            return null;
        }
        
        if (!expectedType.equals(challenge.getChallengeType())) {
            _logger.warn("Challenge type mismatch for ID: {}. Expected: {}, Actual: {}", 
                        challengeId, expectedType, challenge.getChallengeType());
            return null;
        }
        
        if (challenge.getStatus() != null && challenge.getStatus() != 0) {
            _logger.warn("Challenge already used or expired for ID: {}. Status: {}", challengeId, challenge.getStatus());
            return null;
        }
        
        _logger.debug("Challenge validation successful for ID: {}", challengeId);
        return challenge;
    }
    
    /**
     * 解析注册响应
     */
    private RegistrationResponseData parseRegistrationResponse(Map<String, Object> registrationResponse) {
        String credentialIdBase64 = (String) registrationResponse.get("credentialId");
        String attestationObjectBase64 = (String) registrationResponse.get("attestationObject");
        String clientDataJSONBase64 = (String) registrationResponse.get("clientDataJSON");
        
        if (credentialIdBase64 == null || attestationObjectBase64 == null || clientDataJSONBase64 == null) {
            return null;
        }
        
        RegistrationResponseData data = new RegistrationResponseData();
        data.credentialIdBase64 = credentialIdBase64;
        data.attestationObjectBase64 = attestationObjectBase64;
        data.clientDataJSONBase64 = clientDataJSONBase64;
        data.clientDataJSON = Base64.decodeBase64(clientDataJSONBase64);
        
        return data;
    }
    
    /**
     * 创建服务器属性
     */
    private ServerProperty createServerProperty(byte[] clientDataJSON, String challengeBase64) {
        return PasskeyUtils.createServerProperty(
            clientDataJSON, challengeBase64, passkeyProperties.getRelyingParty(), objectConverter);
    }
    
    /**
     * 执行注册验证
     */
    private RegistrationData performRegistrationVerification(RegistrationResponseData responseData, ServerProperty serverProperty) {
        try {
            RegistrationParameters registrationParameters = new RegistrationParameters(
                serverProperty,
                publicKeyCredentialParameters,
                false, // userVerificationRequired
                true   // userPresenceRequired
            );
            
            String registrationResponseJSON = objectConverter.getJsonConverter().writeValueAsString(
                Map.of(
                    "id", responseData.credentialIdBase64,
                    "rawId", responseData.credentialIdBase64,
                    "response", Map.of(
                        "attestationObject", responseData.attestationObjectBase64,
                        "clientDataJSON", responseData.clientDataJSONBase64
                    ),
                    "type", CREDENTIAL_TYPE_PUBLIC_KEY
                )
            );
            
            RegistrationData registrationData = webAuthnManager.parseRegistrationResponseJSON(registrationResponseJSON);
            webAuthnManager.verify(registrationData, registrationParameters);
            
            return registrationData;
            
        } catch (Exception e) {
            _logger.error("Registration verification failed: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 创建UserPasskey对象
     */
    private UserPasskey createUserPasskey(String userId, String credentialIdBase64, RegistrationData registrationData) {
        UserPasskey userPasskey = new UserPasskey();
        userPasskey.setId(idGenerator.generate());
        userPasskey.setUserId(userId);
        userPasskey.setCredentialId(credentialIdBase64);
        
        // 保存公钥信息
        AttestedCredentialData attestedCredentialData = registrationData.getAttestationObject()
            .getAuthenticatorData().getAttestedCredentialData();
        if (attestedCredentialData != null) {
            try {
                userPasskey.setPublicKey(Base64.encodeBase64String(
                    objectConverter.getCborConverter().writeValueAsBytes(attestedCredentialData.getCOSEKey())
                ));
            } catch (Exception e) {
                _logger.error("Failed to encode public key: {}", e.getMessage(), e);
            }
        }
        
        userPasskey.setDisplayName(DEFAULT_DEVICE_NAME);
        userPasskey.setDeviceType(passkeyProperties.getAuthenticator().getAttachment());
        userPasskey.setInstId(DEFAULT_INST_ID);
        userPasskey.setCreatedDate(new Date());
        userPasskey.setLastUsedDate(new Date());
        userPasskey.setSignatureCount(registrationData.getAttestationObject()
            .getAuthenticatorData().getSignCount());
        
        return userPasskey;
    }
    
    @Override
    public Map<String, Object> generateAuthenticationOptions(String userId) {
        _logger.debug("Generating authentication options for usernameless authentication");
        
        try {
            // 生成挑战
            byte[] challenge = new byte[passkeyProperties.getChallenge().getLength()];
            secureRandom.nextBytes(challenge);
            String challengeBase64 = Base64.encodeBase64URLSafeString(challenge);
            
            // 保存挑战信息 - 仅支持无用户名登录
            String challengeId = new IdGenerator().generate();
            PasskeyChallenge passkeyChallenge = new PasskeyChallenge(challengeId, challengeBase64, "AUTHENTICATION");
            passkeyChallenge.setUserId(null); // 无用户名登录，userId 设为 null
            passkeyChallenge.setInstId("1");
            passkeyChallengeService.saveChallenge(passkeyChallenge);
            
            // 构建认证选项
            Map<String, Object> options = new HashMap<>();
            options.put("challenge", challengeBase64);
            options.put("challengeId", challengeId);
            options.put("timeout", passkeyProperties.getChallenge().getTimeoutMs());
            options.put("rpId", passkeyProperties.getRelyingParty().getId());
            options.put("userVerification", passkeyProperties.getAuthenticator().getUserVerification());
            
            // 无用户名登录：不设置 allowCredentials，让认证器自动选择
            _logger.debug("Generated options for usernameless authentication");
            
            return options;
            
        } catch (Exception e) {
            _logger.error("Error generating authentication options for usernameless authentication", e);
            return null;
        }
    }
    
    @Override
    public Map<String, Object> verifyAuthenticationResponse(Map<String, Object> authenticationResponse) {
        _logger.debug("Verifying authentication response");
        
        try {
            // 验证挑战
            PasskeyChallenge challenge = validateChallenge(authenticationResponse, CHALLENGE_TYPE_AUTHENTICATION);
            if (challenge == null) {
                _logger.warn("Invalid or expired authentication challenge");
                return null;
            }
            
            // 解析认证响应数据
            AuthenticationResponseData responseData = parseAuthenticationResponse(authenticationResponse);
            if (responseData == null) {
                _logger.warn("Failed to parse authentication response");
                return null;
            }
            
            // 获取Passkey凭据
            _logger.debug("Looking for passkey with credential ID: {}", responseData.credentialIdBase64);
            
            // 先查询所有的Passkey来调试credential ID和status问题
            List<UserPasskey> allPasskeys = userPasskeyService.findAll();
            _logger.debug("=== CREDENTIAL ID AND STATUS DEBUG ===");
            _logger.debug("Client sent credential ID: {}", responseData.credentialIdBase64);
            _logger.debug("Total passkeys in database: {}", allPasskeys.size());
            for (UserPasskey pk : allPasskeys) {
                _logger.debug("DB credential ID: {} (user: {}, status: {})", pk.getCredentialId(), pk.getUserId(), pk.getStatus());
                _logger.debug("Match check: {}", pk.getCredentialId().equals(responseData.credentialIdBase64));
            }
            _logger.debug("=== END CREDENTIAL ID AND STATUS DEBUG ===");
            
            UserPasskey passkey = userPasskeyService.findByCredentialId(responseData.credentialIdBase64);
            if (passkey == null) {
                _logger.warn("Passkey not found for credential ID: {}", responseData.credentialIdBase64);
                return null;
            }
            
            // 验证挑战与用户匹配
            if (!validateChallengeUserMatch(challenge, passkey)) {
                return null;
            }
            
            // 创建服务器属性
            ServerProperty serverProperty = createServerProperty(responseData.clientDataJSON, challenge.getChallenge());
            if (serverProperty == null) {
                return null;
            }
            
            // 执行WebAuthn认证验证
            Map<String, Object> result = performAuthenticationVerification(responseData, passkey, serverProperty);
            if (result == null) {
                return null;
            }
            
            // 标记挑战为已使用
            challenge.setStatus(1);
            passkeyChallengeService.validateAndConsumeChallenge(challenge.getId(),challenge.getChallengeType());
            
            _logger.debug("Authentication verification completed successfully");
            return result;
            
        } catch (VerificationException e) {
            _logger.error("WebAuthn validation failed", e);
            return null;
        } catch (Exception e) {
            _logger.error("Error verifying authentication response", e);
            return null;
        }
    }
    
    /**
     * 认证响应数据结构
     */
    private static class AuthenticationResponseData {
        String credentialIdBase64;
        String authenticatorDataBase64;
        String clientDataJSONBase64;
        String signatureBase64;
        String userHandleBase64;
        byte[] clientDataJSON;
    }
    
    /**
     * 解析认证响应
     */
    private AuthenticationResponseData parseAuthenticationResponse(Map<String, Object> authenticationResponse) {
        String credentialIdBase64 = (String) authenticationResponse.get("credentialId");
        String authenticatorDataBase64 = (String) authenticationResponse.get("authenticatorData");
        String clientDataJSONBase64 = (String) authenticationResponse.get("clientDataJSON");
        String signatureBase64 = (String) authenticationResponse.get("signature");
        String userHandleBase64 = (String) authenticationResponse.get("userHandle");
        
        if (credentialIdBase64 == null || authenticatorDataBase64 == null || 
            clientDataJSONBase64 == null || signatureBase64 == null) {
            return null;
        }
        
        _logger.info("=== AUTHENTICATION CREDENTIAL ID DEBUG ===");
        _logger.info("Received credentialIdBase64 from client: {}", credentialIdBase64);
        _logger.info("CredentialIdBase64 length: {}", credentialIdBase64.length());
        _logger.info("=== END AUTHENTICATION CREDENTIAL ID DEBUG ===");
        
        AuthenticationResponseData data = new AuthenticationResponseData();
        data.credentialIdBase64 = credentialIdBase64;
        data.authenticatorDataBase64 = authenticatorDataBase64;
        data.clientDataJSONBase64 = clientDataJSONBase64;
        data.signatureBase64 = signatureBase64;
        data.userHandleBase64 = userHandleBase64;
        data.clientDataJSON = Base64.decodeBase64(clientDataJSONBase64);
        
        return data;
    }
    
    /**
     * 验证挑战与用户匹配
     */
    private boolean validateChallengeUserMatch(PasskeyChallenge challenge, UserPasskey passkey) {
        if (challenge.getUserId() != null && !challenge.getUserId().equals(passkey.getUserId())) {
            _logger.warn("Challenge user mismatch: expected {}, found {}", challenge.getUserId(), passkey.getUserId());
            return false;
        }
        return true;
    }
    
    /**
     * 执行认证验证
     */
    private Map<String, Object> performAuthenticationVerification(AuthenticationResponseData responseData, 
                                                                  UserPasskey passkey, ServerProperty serverProperty) {
        try {
            // 解码数据
            byte[] credentialId = Base64.decodeBase64(responseData.credentialIdBase64);
            byte[] authenticatorData = Base64.decodeBase64(responseData.authenticatorDataBase64);
            byte[] signature = Base64.decodeBase64(responseData.signatureBase64);
            byte[] userHandle = responseData.userHandleBase64 != null ? Base64.decodeBase64(responseData.userHandleBase64) : null;
            
            // 从存储的凭据中重建CredentialRecord
            CredentialRecord credentialRecord = buildCredentialRecord(passkey, credentialId);
            if (credentialRecord == null) {
                _logger.error("Failed to build credential record");
                return null;
            }
            
            // 创建认证参数
            AuthenticationParameters authenticationParameters = new AuthenticationParameters(
                serverProperty,
                credentialRecord,
                false, // userVerificationRequired
                true   // userPresenceRequired
            );
            
            // 更新认证参数
            authenticationParameters = new AuthenticationParameters(
                serverProperty,
                credentialRecord,
                Arrays.asList(credentialId),
                false, // userVerificationRequired
                true   // userPresenceRequired
            );
            
            // 解析认证数据
            String authenticationResponseJSON = objectConverter.getJsonConverter().writeValueAsString(
                Map.of(
                    "id", responseData.credentialIdBase64,
                    "rawId", responseData.credentialIdBase64,
                    "response", Map.of(
                        "authenticatorData", responseData.authenticatorDataBase64,
                        "clientDataJSON", responseData.clientDataJSONBase64,
                        "signature", responseData.signatureBase64,
                        "userHandle", responseData.userHandleBase64 != null ? responseData.userHandleBase64 : ""
                    ),
                    "type", CREDENTIAL_TYPE_PUBLIC_KEY
                )
            );
            
            // 使用 WebAuthnManager 解析和验证认证
            AuthenticationData authenticationData = webAuthnManager.parseAuthenticationResponseJSON(authenticationResponseJSON);
            webAuthnManager.verify(authenticationData, authenticationParameters);
            
            // 验证成功，更新凭据信息
            passkey.setLastUsedDate(new Date());
            passkey.setSignatureCount(authenticationData.getAuthenticatorData().getSignCount());
            userPasskeyService.saveOrUpdatePasskey(passkey);
            
            // 返回认证结果
            return Map.of(
                "success", true,
                "userId", passkey.getUserId(),
                "credentialId", passkey.getCredentialId(),
                "displayName", passkey.getDisplayName() != null ? passkey.getDisplayName() : "Unknown Device"
            );
            
        } catch (Exception e) {
            _logger.error("Authentication verification failed: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 构建CredentialRecord
     */
    private CredentialRecord buildCredentialRecord(UserPasskey passkey, byte[] credentialId) {
        try {
            byte[] publicKeyBytes = Base64.decodeBase64(passkey.getPublicKey());
            COSEKey coseKey = objectConverter.getCborConverter().readValue(publicKeyBytes, COSEKey.class);

            AttestedCredentialData attestedCredentialData = new AttestedCredentialData(
                passkey.getAaguid() != null ? new AAGUID(Base64.decodeBase64(passkey.getAaguid())) : AAGUID.NULL,
                credentialId,
                coseKey
            );

            return new CredentialRecordImpl(
                null, // attestationStatement
                false, // uvInitialized
                false, // backupEligible  
                false, // backupState
                passkey.getSignatureCount(), // counter
                attestedCredentialData, // attestedCredentialData
                null, // authenticatorExtensions
                null, // clientData
                null, // clientExtensions
                null  // transports
            );
            
        } catch (Exception e) {
            _logger.error("Failed to build CredentialRecord: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<UserPasskey> getUserPasskeys(String userId) {
        return userPasskeyService.findByUserId(userId);
    }
    
    @Override
    public boolean deletePasskey(String userId, String credentialId) {
        UserPasskey passkey = userPasskeyService.findByCredentialId(credentialId);
        if (passkey != null && userId.equals(passkey.getUserId())) {
            return userPasskeyService.deletePasskey(userId, credentialId);
        }
        return false;
    }
    
    @Override
    public boolean hasAnyRegisteredPasskeys() {
        try {
            // 通过查询所有有效用户的 Passkey 数量来判断系统中是否有注册的 Passkey
            // 使用更高效的查询方法，只查询有效的 Passkey (status = 1)
            List<UserPasskey> allPasskeys = userPasskeyService.findAll();
            if (allPasskeys == null || allPasskeys.isEmpty()) {
                return false;
            }
            
            // 检查是否有任何有效的 Passkey (status = 1)
            for (UserPasskey passkey : allPasskeys) {
                if (passkey.getStatus() != null && passkey.getStatus() == 1) {
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            _logger.error("Error checking for any registered passkeys", e);
            return false;
        }
    }
    
    @Override
    public boolean savePasskey(UserPasskey userPasskey) {
        try {
            if (userPasskey == null || userPasskey.getId() == null) {
                _logger.warn("Cannot save null passkey or passkey with null ID");
                return false;
            }
            
            _logger.debug("Saving passkey: ID={}, userId={}, credentialId={}", 
                         userPasskey.getId(), userPasskey.getUserId(), userPasskey.getCredentialId());
            
            userPasskeyService.saveOrUpdatePasskey(userPasskey);
            
            _logger.debug("Passkey saved successfully");
            return true;
            
        } catch (Exception e) {
            _logger.error("Error saving passkey: {}", e.getMessage(), e);
            return false;
        }
    }
    

    
    @Override
    public UserPasskey getPasskeyByCredentialId(String credentialId) {
        _logger.debug("Looking for passkey with credentialId: {}", credentialId);
        
        UserPasskey result = userPasskeyService.findByCredentialId(credentialId);
        
        if (result != null) {
            _logger.debug("Found passkey for user: {}", result.getUserId());
            return result;
        }
        
        _logger.debug("No passkey found for credentialId: {}", credentialId);
        return null;
    }
    
    @Override
    public boolean updateSignatureCount(String credentialId, Long signatureCount) {
        UserPasskey passkey = userPasskeyService.findByCredentialId(credentialId);
        if (passkey != null) {
            passkey.setSignatureCount(signatureCount);
            userPasskeyService.saveOrUpdatePasskey(passkey);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean saveChallenge(PasskeyChallenge challenge) {
        try {
            passkeyChallengeService.saveChallenge(challenge);
            return true;
        } catch (Exception e) {
            _logger.error("Error saving challenge", e);
            return false;
        }
    }
    
    @Override
    public PasskeyChallenge getChallenge(String challengeId) {
        return passkeyChallengeService.findByChallengeId(challengeId);
    }
    
    @Override
    public int cleanExpiredChallenges() {
        return passkeyChallengeService.cleanExpiredChallenges();
    }
}