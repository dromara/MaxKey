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

package org.dromara.maxkey.passkey.endpoint;

import org.dromara.maxkey.passkey.manager.PasskeyManager;
import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.dromara.maxkey.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Passkey注册端点
 * 提供Passkey注册相关的REST API
 */
@RestController
@RequestMapping("/passkey/registration")
public class PasskeyRegistrationEndpoint {
    private static final Logger _logger = LoggerFactory.getLogger(PasskeyRegistrationEndpoint.class);
    
    @Autowired
    private PasskeyManager passkeyManager;
    
    /**
     * 开始Passkey注册
     * @param request 请求参数
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 注册选项
     */
    @PostMapping("/begin")
    public ResponseEntity<?> beginRegistration(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Begin Passkey registration request received");
        
        try {
            // 获取请求参数
            String userId = (String) request.get("userId");
            String username = (String) request.get("username");
            String displayName = (String) request.get("displayName");
            
            // 参数验证
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "用户ID不能为空"));
            }
            
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "用户名不能为空"));
            }
            
            if (displayName == null || displayName.trim().isEmpty()) {
                displayName = username; // 默认使用用户名作为显示名称
            }
            
            // 生成注册选项
            Map<String, Object> options = passkeyManager.beginRegistration(userId, username, displayName);
            
            _logger.info("Passkey registration options generated for user: {}", userId);
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "注册选项生成成功", options));
            
        } catch (Exception e) {
            _logger.error("Error beginning Passkey registration", e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "生成注册选项失败: " + e.getMessage()));
        }
    }
    
    /**
     * 完成Passkey注册
     * @param request 注册响应数据
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 注册结果
     */
    @PostMapping("/finish")
    public ResponseEntity<?> finishRegistration(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Finish Passkey registration request received");
        
        try {
            // 获取用户ID
            String userId = (String) request.get("userId");
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "用户ID不能为空"));
            }
            
            // 验证注册响应
            UserPasskey newPasskey = passkeyManager.finishRegistration(userId, request);
            
            if (newPasskey != null) {
                _logger.info("Passkey registration completed successfully for user: {}", userId);
                
                // 构建返回的Passkey信息
                Map<String, Object> passkeyInfo = new HashMap<>();
                passkeyInfo.put("id", newPasskey.getId());
                passkeyInfo.put("credentialId", newPasskey.getCredentialId());
                passkeyInfo.put("displayName", newPasskey.getDisplayName());
                passkeyInfo.put("deviceType", newPasskey.getDeviceType());
                passkeyInfo.put("signatureCount", newPasskey.getSignatureCount());
                passkeyInfo.put("createdDate", newPasskey.getCreatedDate());
                passkeyInfo.put("lastUsedDate", newPasskey.getLastUsedDate());
                passkeyInfo.put("status", newPasskey.getStatus());
                
                return ResponseEntity.ok(new Message<>(Message.SUCCESS, "Passkey注册成功", passkeyInfo));
            } else {
                _logger.warn("Passkey registration failed for user: {}", userId);
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "Passkey注册失败，请重试"));
            }
            
        } catch (Exception e) {
            _logger.error("Error finishing Passkey registration", e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "注册验证失败: " + e.getMessage()));
        }
    }
    
    /**
     * 获取用户的Passkey列表
     * @param userId 用户ID
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return Passkey列表
     */
    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getUserPasskeys(
            @PathVariable String userId,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Get user Passkeys request for user: {}", userId);
        
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "用户ID不能为空"));
            }
            
            List<UserPasskey> passkeys = passkeyManager.getUserPasskeys(userId);
            
            // 移除敏感信息
            passkeys.forEach(passkey -> {
                passkey.setPublicKey(null); // 不返回公钥信息
            });
            
            _logger.debug("Retrieved {} Passkeys for user: {}", passkeys.size(), userId);
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "获取成功", passkeys));
            
        } catch (Exception e) {
            _logger.error("Error getting user Passkeys for user: {}", userId, e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "获取Passkey列表失败: " + e.getMessage()));
        }
    }
    
    /**
     * 删除用户的Passkey
     * @param userId 用户ID
     * @param credentialId 凭据ID
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 删除结果
     */
    @DeleteMapping("/delete/{userId}/{credentialId}")
    public ResponseEntity<?> deletePasskey(
            @PathVariable String userId,
            @PathVariable String credentialId,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Delete Passkey request for user: {}, credentialId: {}", userId, credentialId);
        
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "用户ID不能为空"));
            }
            
            if (credentialId == null || credentialId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "凭据ID不能为空"));
            }
            
            boolean success = passkeyManager.deleteUserPasskey(userId, credentialId);
            
            if (success) {
                _logger.info("Passkey deleted successfully for user: {}", userId);
                return ResponseEntity.ok(new Message<>(Message.SUCCESS, "Passkey删除成功"));
            } else {
                _logger.warn("Failed to delete Passkey for user: {}", userId);
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "Passkey删除失败"));
            }
            
        } catch (Exception e) {
            _logger.error("Error deleting Passkey for user: {}", userId, e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "删除Passkey失败: " + e.getMessage()));
        }
    }
    
    /**
     * 获取Passkey统计信息
     * @param userId 用户ID
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 统计信息
     */
    @GetMapping("/stats/{userId}")
    public ResponseEntity<?> getPasskeyStats(
            @PathVariable String userId,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Get Passkey stats request for user: {}", userId);
        
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "用户ID不能为空"));
            }
            
            Map<String, Object> stats = passkeyManager.getPasskeyStats(userId);
            
            _logger.debug("Retrieved Passkey stats for user: {}", userId);
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "获取成功", stats));
            
        } catch (Exception e) {
            _logger.error("Error getting Passkey stats for user: {}", userId, e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "获取统计信息失败: " + e.getMessage()));
        }
    }
    
    /**
     * 检查用户是否支持Passkey
     * @param userId 用户ID
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 支持状态
     */
    @GetMapping("/support/{userId}")
    public ResponseEntity<?> checkPasskeySupport(
            @PathVariable String userId,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Check Passkey support request for user: {}", userId);
        
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "用户ID不能为空"));
            }
            
            boolean supported = passkeyManager.isPasskeySupported(userId);
            
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("supported", supported);
            
            _logger.debug("Passkey support check for user: {}, result: {}", userId, supported);
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "检查完成", result));
            
        } catch (Exception e) {
            _logger.error("Error checking Passkey support for user: {}", userId, e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "检查支持状态失败: " + e.getMessage()));
        }
    }
}