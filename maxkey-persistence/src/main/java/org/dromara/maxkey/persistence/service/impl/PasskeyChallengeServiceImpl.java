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

import java.util.Date;

import org.dromara.maxkey.entity.passkey.PasskeyChallenge;
import org.dromara.maxkey.persistence.mapper.PasskeyChallengeMapper;
import org.dromara.maxkey.persistence.service.PasskeyChallengeService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * PasskeyChallenge Service 实现类
 * 提供 Passkey 挑战数据的数据库操作实现
 * 
 * @author MaxKey Team
 */
@Repository
@Transactional
public class PasskeyChallengeServiceImpl extends JpaServiceImpl<PasskeyChallengeMapper, PasskeyChallenge> implements PasskeyChallengeService {
    
    private static final Logger _logger = LoggerFactory.getLogger(PasskeyChallengeServiceImpl.class);
    
    @Override
    public PasskeyChallenge findByChallengeId(String challengeId) {
        _logger.debug("Finding challenge by ID: {}", challengeId);
        try {
            return getMapper().findByChallengeId(challengeId);
        } catch (Exception e) {
            _logger.error("Error finding challenge by ID: {}", challengeId, e);
            throw new RuntimeException("Failed to find challenge by ID: " + challengeId, e);
        }
    }
    
    @Override
    public PasskeyChallenge findLatestByUserIdAndType(String userId, String challengeType) {
        _logger.debug("Finding latest challenge for user: {} and type: {}", userId, challengeType);
        try {
            return getMapper().findLatestByUserIdAndType(userId, challengeType);
        } catch (Exception e) {
            _logger.error("Error finding latest challenge for user: {} and type: {}", userId, challengeType, e);
            throw new RuntimeException("Failed to find latest challenge for user and type", e);
        }
    }
    
    @Override
    public boolean saveChallenge(PasskeyChallenge challenge) {
        _logger.debug("Saving challenge for user: {} and type: {}", challenge.getUserId(), challenge.getChallengeType());
        try {
            // 移除重复设置创建时间的代码，因为构造函数中已经正确设置了创建时间和过期时间
            // challenge.setCreatedDate(new Date()); // 删除这行避免时间冲突
            
            // 清理该用户同类型的旧挑战（保持数据库整洁）
            deleteByUserIdAndType(challenge.getUserId(), challenge.getChallengeType());
            
            // 插入新挑战
            return insert(challenge);
        } catch (Exception e) {
            _logger.error("Error saving challenge for user: {}", challenge.getUserId(), e);
            throw new RuntimeException("Failed to save challenge", e);
        }
    }
    
    @Override
    public boolean deleteByChallengeId(String challengeId) {
        _logger.debug("Deleting challenge by ID: {}", challengeId);
        try {
            int result = getMapper().deleteByChallengeId(challengeId);
            return result > 0;
        } catch (Exception e) {
            _logger.error("Error deleting challenge by ID: {}", challengeId, e);
            throw new RuntimeException("Failed to delete challenge by ID: " + challengeId, e);
        }
    }
    
    @Override
    public int cleanExpiredChallenges() {
        _logger.debug("Cleaning expired challenges");
        try {
            int result = getMapper().cleanExpiredChallenges();
            _logger.info("Cleaned {} expired challenges", result);
            return result;
        } catch (Exception e) {
            _logger.error("Error cleaning expired challenges", e);
            throw new RuntimeException("Failed to clean expired challenges", e);
        }
    }
    
    @Override
    public int deleteByUserId(String userId) {
        _logger.debug("Deleting all challenges for user: {}", userId);
        try {
            int result = getMapper().deleteByUserId(userId);
            _logger.debug("Deleted {} challenges for user: {}", result, userId);
            return result;
        } catch (Exception e) {
            _logger.error("Error deleting challenges for user: {}", userId, e);
            throw new RuntimeException("Failed to delete challenges for user: " + userId, e);
        }
    }
    
    @Override
    public int deleteByUserIdAndType(String userId, String challengeType) {
        _logger.debug("Deleting challenges for user: {} and type: {}", userId, challengeType);
        try {
            int result = getMapper().deleteByUserIdAndType(userId, challengeType);
            _logger.debug("Deleted {} challenges for user: {} and type: {}", result, userId, challengeType);
            return result;
        } catch (Exception e) {
            _logger.error("Error deleting challenges for user: {} and type: {}", userId, challengeType, e);
            throw new RuntimeException("Failed to delete challenges for user and type", e);
        }
    }
    
    @Override
    public int countByUserId(String userId) {
        _logger.debug("Counting challenges for user: {}", userId);
        try {
            return getMapper().countByUserId(userId);
        } catch (Exception e) {
            _logger.error("Error counting challenges for user: {}", userId, e);
            throw new RuntimeException("Failed to count challenges for user: " + userId, e);
        }
    }
    
    @Override
    public boolean existsValidChallenge(String challengeId) {
        _logger.debug("Checking if valid challenge exists: {}", challengeId);
        try {
            return getMapper().existsValidChallenge(challengeId);
        } catch (Exception e) {
            _logger.error("Error checking valid challenge existence: {}", challengeId, e);
            throw new RuntimeException("Failed to check valid challenge existence", e);
        }
    }
    
    @Override
    public PasskeyChallenge validateAndConsumeChallenge(String challengeId, String expectedType) {
        _logger.debug("Validating and consuming challenge: {} with expected type: {}", challengeId, expectedType);
        try {
            // 查找挑战
            PasskeyChallenge challenge = findByChallengeId(challengeId);
            
            if (challenge == null) {
                _logger.warn("Challenge not found: {}", challengeId);
                return null;
            }
            
            // 检查是否过期
            if (challenge.getExpiresDate().before(new Date())) {
                _logger.warn("Challenge expired: {}", challengeId);
                // 删除过期的挑战
                deleteByChallengeId(challengeId);
                return null;
            }
            
            // 检查类型是否匹配
            if (!expectedType.equals(challenge.getChallengeType())) {
                _logger.warn("Challenge type mismatch. Expected: {}, Actual: {}", expectedType, challenge.getChallengeType());
                return null;
            }
            
            // 验证成功，删除挑战（消费）
            deleteByChallengeId(challengeId);
            
            _logger.debug("Challenge validated and consumed successfully: {}", challengeId);
            return challenge;
        } catch (Exception e) {
            _logger.error("Error validating and consuming challenge: {}", challengeId, e);
            throw new RuntimeException("Failed to validate and consume challenge", e);
        }
    }
}