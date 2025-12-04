/**
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

package org.dromara.maxkey.passkey.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.server.ServerProperty;
import org.apache.commons.codec.binary.Base64;
import org.dromara.maxkey.entity.passkey.PasskeyChallenge;
import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.dromara.maxkey.id.IdGenerator;
import org.dromara.maxkey.passkey.config.PasskeyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

/**
 * Passkey工具类
 * 提供通用的验证和构建方法
 */
public class PasskeyUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(PasskeyUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final IdGenerator idGenerator = new IdGenerator();
    
    /**
     * 从clientDataJSON中解析并验证origin
     */
    public static String parseAndValidateOrigin(String clientDataJSON, String expectedOrigin) {
        try {
            JsonNode clientData = objectMapper.readTree(clientDataJSON);
            String origin = clientData.get("origin").asText();
            
            if (!expectedOrigin.equals(origin)) {
                logger.warn("Origin mismatch. Expected: {}, Actual: {}", expectedOrigin, origin);
                throw new IllegalArgumentException("Origin validation failed");
            }
            
            return origin;
        } catch (Exception e) {
            logger.error("Failed to parse or validate origin from clientDataJSON", e);
            throw new RuntimeException("Origin validation failed", e);
        }
    }
    
    /**
     * 创建ServerProperty对象
     */
    public static ServerProperty createServerProperty(String origin, String rpId, byte[] challenge) {
        return new ServerProperty(
            Origin.create(origin),
            rpId,
            new DefaultChallenge(challenge),
            null
        );
    }
    
    /**
     * Base64解码
     */
    public static byte[] base64Decode(String encoded) {
        try {
            return Base64.decodeBase64(encoded);
        } catch (Exception e) {
            logger.error("Failed to decode base64 string: {}", encoded, e);
            throw new IllegalArgumentException("Invalid base64 encoding", e);
        }
    }
    
    /**
     * Base64编码
     */
    public static String base64Encode(byte[] data) {
        return Base64.encodeBase64URLSafeString(data);
    }
    
    /**
     * 验证字符串是否为空
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
    
    /**
     * 验证对象是否为空
     */
    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }
    
    /**
     * 生成挑战
     */
    public static PasskeyChallenge generateChallenge(String userId, String challengeType, int challengeLength) {
        byte[] challenge = new byte[challengeLength];
        secureRandom.nextBytes(challenge);
        String challengeBase64 = Base64.encodeBase64URLSafeString(challenge);
        
        String challengeId = idGenerator.generate();
        PasskeyChallenge passkeyChallenge = new PasskeyChallenge(challengeId, challengeBase64, challengeType);
        passkeyChallenge.setUserId(userId);
        
        return passkeyChallenge;
    }
    
    /**
     * 构建RP信息
     */
    public static Map<String, Object> buildRelyingPartyInfo(PasskeyProperties.RelyingParty relyingParty) {
        Map<String, Object> rp = new HashMap<>();
        rp.put("name", relyingParty.getName());
        rp.put("id", relyingParty.getId());
        if (relyingParty.getIcon() != null) {
            rp.put("icon", relyingParty.getIcon());
        }
        return rp;
    }
    
    /**
     * 构建用户信息
     */
    public static Map<String, Object> buildUserInfo(String userId, String username, String displayName) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", Base64.encodeBase64URLSafeString(userId.getBytes()));
        user.put("name", username);
        user.put("displayName", displayName);
        return user;
    }
    
    /**
     * 构建公钥凭据参数
     */
    public static List<Map<String, Object>> buildPublicKeyCredentialParams(List<PublicKeyCredentialParameters> parameters) {
        List<Map<String, Object>> pubKeyCredParams = new ArrayList<>();
        for (PublicKeyCredentialParameters param : parameters) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("type", param.getType().getValue());
            paramMap.put("alg", param.getAlg().getValue());
            pubKeyCredParams.add(paramMap);
        }
        return pubKeyCredParams;
    }
    
    /**
     * 构建认证器选择标准
     */
    public static Map<String, Object> buildAuthenticatorSelection(PasskeyProperties.Authenticator authenticator) {
        Map<String, Object> authenticatorSelection = new HashMap<>();
        authenticatorSelection.put("authenticatorAttachment", authenticator.getAttachment());
        authenticatorSelection.put("userVerification", authenticator.getUserVerification());
        authenticatorSelection.put("requireResidentKey", authenticator.isRequireResidentKey());
        return authenticatorSelection;
    }
    
    /**
     * 构建凭据列表
     */
    public static List<Map<String, Object>> buildCredentialList(List<UserPasskey> passkeys) {
        List<Map<String, Object>> credentialList = new ArrayList<>();
        
        for (UserPasskey passkey : passkeys) {
            Map<String, Object> credentialMap = new HashMap<>();
            credentialMap.put("type", "public-key");
            credentialMap.put("id", passkey.getCredentialId());
            credentialList.add(credentialMap);
        }
        
        return credentialList;
    }
    
    /**
     * 创建ServerProperty对象（重载方法）
     */
    public static ServerProperty createServerProperty(byte[] clientDataJSON, String challengeBase64, 
                                                     PasskeyProperties.RelyingParty relyingParty, 
                                                     ObjectConverter objectConverter) {
        try {
            String clientDataJSONString = new String(clientDataJSON, StandardCharsets.UTF_8);
            logger.debug("ClientDataJSON string: {}", clientDataJSONString);
            
            Map<String, Object> clientData = objectConverter.getJsonConverter().readValue(clientDataJSONString, Map.class);
            String actualOrigin = (String) clientData.get("origin");
            
            logger.debug("Actual origin from clientData: {}", actualOrigin);
            
            if (actualOrigin == null || actualOrigin.trim().isEmpty()) {
                logger.error("Origin is null or empty in clientDataJSON");
                return null;
            }
            
            // 验证origin
            List<String> allowedOrigins = relyingParty.getAllowedOrigins();
            if (!allowedOrigins.contains(actualOrigin)) {
                logger.warn("Origin {} not in allowed origins: {}", actualOrigin, allowedOrigins);
                return null;
            }
            
            Origin origin = new Origin(actualOrigin);
            String rpId = relyingParty.getId();
            Challenge challengeObj = new DefaultChallenge(Base64.decodeBase64(challengeBase64));
            
            return new ServerProperty(origin, rpId, challengeObj, null);
            
        } catch (Exception e) {
            logger.error("Failed to create ServerProperty: {}", e.getMessage(), e);
            return null;
        }
    }
}