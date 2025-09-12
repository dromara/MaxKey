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

package org.dromara.maxkey.persistence.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.dromara.maxkey.persistence.mapper.UserPasskeyMapper;
import org.dromara.maxkey.persistence.service.UserPasskeyService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserPasskey Service 实现类
 * 提供用户 Passkey 凭据的数据库操作实现
 * 
 * @author MaxKey Team
 */
@Repository
@Transactional
public class UserPasskeyServiceImpl extends JpaServiceImpl<UserPasskeyMapper, UserPasskey> implements UserPasskeyService {
    
    private static final Logger _logger = LoggerFactory.getLogger(UserPasskeyServiceImpl.class);
    
    @Override
    public List<UserPasskey> findByUserId(String userId) {
        _logger.debug("Finding passkeys for user: {}", userId);
        try {
            return getMapper().findByUserId(userId);
        } catch (Exception e) {
            _logger.error("Error finding passkeys for user: {}", userId, e);
            throw new RuntimeException("Failed to find passkeys for user: " + userId, e);
        }
    }
    
    @Override
    public UserPasskey findByCredentialId(String credentialId) {
        _logger.debug("Finding passkey by credential ID: {}", credentialId);
        try {
            return getMapper().findByCredentialId(credentialId);
        } catch (Exception e) {
            _logger.error("Error finding passkey by credential ID: {}", credentialId, e);
            throw new RuntimeException("Failed to find passkey by credential ID: " + credentialId, e);
        }
    }
    
    @Override
    public UserPasskey findByUserIdAndCredentialId(String userId, String credentialId) {
        _logger.debug("Finding passkey for user: {} and credential ID: {}", userId, credentialId);
        try {
            return getMapper().findByUserIdAndCredentialId(userId, credentialId);
        } catch (Exception e) {
            _logger.error("Error finding passkey for user: {} and credential ID: {}", userId, credentialId, e);
            throw new RuntimeException("Failed to find passkey for user and credential ID", e);
        }
    }
    
    @Override
    public boolean saveOrUpdatePasskey(UserPasskey userPasskey) {
        _logger.debug("Saving or updating passkey for user: {}", userPasskey.getUserId());
        try {
            UserPasskey existing = findByCredentialId(userPasskey.getCredentialId());
            
            if (existing != null) {
                userPasskey.setId(existing.getId());
                userPasskey.setCreatedDate(existing.getCreatedDate());
                userPasskey.setLastUsedDate(new Date()); // 改为 Date
                return update(userPasskey);
            } else {
                userPasskey.setCreatedDate(new Date()); // 改为 Date
                return insert(userPasskey);
            }
        } catch (Exception e) {
            _logger.error("Error saving or updating passkey for user: {}", userPasskey.getUserId(), e);
            throw new RuntimeException("Failed to save or update passkey", e);
        }
    }
    
    @Override
    public boolean updateSignatureCount(String credentialId, Long signatureCount) {
        _logger.debug("Updating signature count for credential ID: {} to {}", credentialId, signatureCount);
        try {
            int result = getMapper().updateSignatureCount(credentialId, signatureCount);
            return result > 0;
        } catch (Exception e) {
            _logger.error("Error updating signature count for credential ID: {}", credentialId, e);
            throw new RuntimeException("Failed to update signature count", e);
        }
    }
    
    @Override
    public boolean deletePasskey(String userId, String credentialId) {
        _logger.debug("Deleting passkey for user: {} and credential ID: {}", userId, credentialId);
        try {
            int result = getMapper().deleteByUserIdAndCredentialId(userId, credentialId);
            return result > 0;
        } catch (Exception e) {
            _logger.error("Error deleting passkey for user: {} and credential ID: {}", userId, credentialId, e);
            throw new RuntimeException("Failed to delete passkey", e);
        }
    }
    
    @Override
    public int cleanExpiredPasskeys() {
        _logger.debug("Cleaning expired passkeys");
        try {
            int result = getMapper().cleanExpiredPasskeys();
            _logger.info("Cleaned {} expired passkeys", result);
            return result;
        } catch (Exception e) {
            _logger.error("Error cleaning expired passkeys", e);
            throw new RuntimeException("Failed to clean expired passkeys", e);
        }
    }
    
    @Override
    public int countByUserId(String userId) {
        _logger.debug("Counting passkeys for user: {}", userId);
        try {
            return getMapper().countByUserId(userId);
        } catch (Exception e) {
            _logger.error("Error counting passkeys for user: {}", userId, e);
            throw new RuntimeException("Failed to count passkeys for user: " + userId, e);
        }
    }
    
    @Override
    public boolean existsByCredentialId(String credentialId) {
        _logger.debug("Checking if credential ID exists: {}", credentialId);
        try {
            return getMapper().existsByCredentialId(credentialId);
        } catch (Exception e) {
            _logger.error("Error checking credential ID existence: {}", credentialId, e);
            throw new RuntimeException("Failed to check credential ID existence", e);
        }
    }
    
    @Override
    public Map<String, Object> getPasskeyStats(String userId) {
        _logger.debug("Getting passkey stats for user: {}", userId);
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // 获取用户的Passkey数量
            int count = countByUserId(userId);
            stats.put("count", count);
            
            // 获取用户的Passkey列表（用于显示设备信息）
            List<UserPasskey> passkeys = findByUserId(userId);
            stats.put("passkeys", passkeys);
            
            // 计算最后使用时间
            Date lastUsed = null;
            for (UserPasskey passkey : passkeys) {
                if (passkey.getLastUsedDate() != null) {
                    if (lastUsed == null || passkey.getLastUsedDate().after(lastUsed)) {
                        lastUsed = passkey.getLastUsedDate();
                    }
                }
            }
            stats.put("lastUsed", lastUsed);
            
            return stats;
        } catch (Exception e) {
            _logger.error("Error getting passkey stats for user: {}", userId, e);
            throw new RuntimeException("Failed to get passkey stats for user: " + userId, e);
        }
    }
}