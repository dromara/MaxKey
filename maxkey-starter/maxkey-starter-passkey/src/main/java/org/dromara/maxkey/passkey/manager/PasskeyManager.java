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

package org.dromara.maxkey.passkey.manager;

import org.dromara.maxkey.passkey.service.PasskeyService;
import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Passkey管理器
 * 负责协调Passkey相关的业务逻辑和WebAuthn操作
 */
@Component
public class PasskeyManager {
    private static final Logger _logger = LoggerFactory.getLogger(PasskeyManager.class);
    
    @Autowired
    private PasskeyService passkeyService;
    
    /**
     * 开始Passkey注册流程
     * @param userId 用户ID
     * @param username 用户名
     * @param displayName 显示名称
     * @return 注册选项
     */
    public Map<String, Object> beginRegistration(String userId, String username, String displayName) {
        _logger.debug("Beginning Passkey registration for user: {}", userId);
        
        try {
            // 检查用户是否已有Passkey
            List<UserPasskey> existingPasskeys = passkeyService.getUserPasskeys(userId);
            if (existingPasskeys.size() >= 5) { // 限制每个用户最多5个Passkey
                _logger.warn("User {} already has maximum number of Passkeys", userId);
                throw new RuntimeException("用户已达到Passkey数量上限");
            }
            
            return passkeyService.generateRegistrationOptions(userId, username, displayName);
            
        } catch (Exception e) {
            _logger.error("Error beginning registration for user: {}", userId, e);
            throw new RuntimeException("开始注册失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 完成Passkey注册
     * @param userId 用户ID
     * @param registrationResponse 注册响应
     * @return 注册成功时返回新创建的UserPasskey对象，失败时返回null
     */
    public UserPasskey finishRegistration(String userId, Map<String, Object> registrationResponse) {
        _logger.debug("Finishing Passkey registration for user: {}", userId);
        
        try {
            UserPasskey newPasskey = passkeyService.verifyRegistrationResponse(userId, registrationResponse);
            
            if (newPasskey != null) {
                _logger.info("Passkey registration completed successfully for user: {}", userId);
            } else {
                _logger.warn("Passkey registration failed for user: {}", userId);
            }
            
            return newPasskey;
            
        } catch (Exception e) {
            _logger.error("Error finishing registration for user: {}", userId, e);
            return null;
        }
    }
    
    /**
     * 开始Passkey认证流程
     * @param userId 用户ID（可选，为空时支持无用户名登录）
     * @return 认证选项
     */
    public Map<String, Object> beginAuthentication(String userId) {
        _logger.debug("Beginning Passkey authentication for user: {}", userId);
        
        try {
            // 如果指定了用户ID，检查用户是否有可用的Passkey
            if (userId != null && !userId.isEmpty()) {
                List<UserPasskey> userPasskeys = passkeyService.getUserPasskeys(userId);
                if (userPasskeys.isEmpty()) {
                    _logger.warn("No active Passkeys found for user: {}", userId);
                    throw new RuntimeException("用户没有可用的Passkey");
                }
            }
            
            return passkeyService.generateAuthenticationOptions(userId);
            
        } catch (Exception e) {
            _logger.error("Error beginning authentication for user: {}", userId, e);
            throw new RuntimeException("开始认证失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 完成Passkey认证
     * @param authenticationResponse 认证响应
     * @return 认证结果，包含用户信息
     */
    public Map<String, Object> finishAuthentication(Map<String, Object> authenticationResponse) {
        _logger.debug("Finishing Passkey authentication");
        
        try {
            Map<String, Object> result = passkeyService.verifyAuthenticationResponse(authenticationResponse);
            
            if (result != null && Boolean.TRUE.equals(result.get("success"))) {
                String userId = (String) result.get("userId");
                _logger.info("Passkey authentication completed successfully for user: {}", userId);
            } else {
                _logger.warn("Passkey authentication failed");
            }
            
            return result;
            
        } catch (Exception e) {
            _logger.error("Error finishing authentication", e);
            return null;
        }
    }
    
    /**
     * 获取用户的所有Passkey
     * @param userId 用户ID
     * @return Passkey列表
     */
    public List<UserPasskey> getUserPasskeys(String userId) {
        _logger.debug("Getting Passkeys for user: {}", userId);
        return passkeyService.getUserPasskeys(userId);
    }
    
    /**
     * 删除用户的Passkey
     * @param userId 用户ID
     * @param credentialId 凭据ID
     * @return 是否成功
     */
    public boolean deleteUserPasskey(String userId, String credentialId) {
        _logger.debug("Deleting Passkey for user: {}, credentialId: {}", userId, credentialId);
        
        try {
            boolean success = passkeyService.deletePasskey(userId, credentialId);
            
            if (success) {
                _logger.info("Passkey deleted successfully for user: {}", userId);
            } else {
                _logger.warn("Failed to delete Passkey for user: {}", userId);
            }
            
            return success;
            
        } catch (Exception e) {
            _logger.error("Error deleting Passkey for user: {}", userId, e);
            return false;
        }
    }
    
    /**
     * 检查用户是否支持Passkey
     * @param userId 用户ID
     * @return 是否支持
     */
    public boolean isPasskeySupported(String userId) {
        // 这里可以添加更多的检查逻辑，比如用户设置、设备能力等
        return true;
    }
    
    /**
     * 获取Passkey统计信息
     * @param userId 用户ID
     * @return 统计信息
     */
    public Map<String, Object> getPasskeyStats(String userId) {
        List<UserPasskey> passkeys = passkeyService.getUserPasskeys(userId);
        
        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalCount", passkeys.size());
        stats.put("maxAllowed", 5);
        stats.put("canAddMore", passkeys.size() < 5);
        
        return stats;
    }
    
    /**
     * 清理过期的挑战
     * @return 清理的数量
     */
    public int cleanExpiredChallenges() {
        _logger.debug("Cleaning expired challenges");
        return passkeyService.cleanExpiredChallenges();
    }
    
    /**
     * 检查系统中是否有任何注册的 Passkey
     * @return 如果系统中有注册的 Passkey 返回 true，否则返回 false
     */
    public boolean hasAnyRegisteredPasskeys() {
        try {
            return passkeyService.hasAnyRegisteredPasskeys();
        } catch (Exception e) {
            _logger.error("Error checking for any registered passkeys", e);
            return false;
        }
    }
}