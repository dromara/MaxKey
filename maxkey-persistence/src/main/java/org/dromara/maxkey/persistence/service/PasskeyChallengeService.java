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

package org.dromara.maxkey.persistence.service;

import org.dromara.maxkey.entity.passkey.PasskeyChallenge;
import org.dromara.mybatis.jpa.IJpaService;

/**
 * PasskeyChallenge Service 接口
 * 提供 Passkey 挑战数据的业务操作方法
 * 
 * @author MaxKey Team
 */
public interface PasskeyChallengeService extends IJpaService<PasskeyChallenge> {
    
    /**
     * 根据挑战ID查询挑战数据
     * 
     * @param challengeId 挑战ID
     * @return PasskeyChallenge对象
     */
    PasskeyChallenge findByChallengeId(String challengeId);
    
    /**
     * 根据用户ID和挑战类型查询最新的挑战
     * 
     * @param userId 用户ID
     * @param challengeType 挑战类型（REGISTRATION/AUTHENTICATION）
     * @return PasskeyChallenge对象
     */
    PasskeyChallenge findLatestByUserIdAndType(String userId, String challengeType);
    
    /**
     * 保存挑战数据
     * 
     * @param challenge 挑战对象
     * @return 是否成功
     */
    boolean saveChallenge(PasskeyChallenge challenge);
    
    /**
     * 删除指定挑战ID的记录
     * 
     * @param challengeId 挑战ID
     * @return 是否成功
     */
    boolean deleteByChallengeId(String challengeId);
    
    /**
     * 清理过期的挑战记录
     * 
     * @return 清理的记录数
     */
    int cleanExpiredChallenges();
    
    /**
     * 清理指定用户的所有挑战记录
     * 
     * @param userId 用户ID
     * @return 清理的记录数
     */
    int deleteByUserId(String userId);
    
    /**
     * 清理指定用户和类型的挑战记录
     * 
     * @param userId 用户ID
     * @param challengeType 挑战类型
     * @return 清理的记录数
     */
    int deleteByUserIdAndType(String userId, String challengeType);
    
    /**
     * 统计指定用户的挑战数量
     * 
     * @param userId 用户ID
     * @return 挑战数量
     */
    int countByUserId(String userId);
    
    /**
     * 检查挑战ID是否存在且未过期
     * 
     * @param challengeId 挑战ID
     * @return 是否存在且有效
     */
    boolean existsValidChallenge(String challengeId);
    
    /**
     * 验证并消费挑战（验证后删除）
     * 
     * @param challengeId 挑战ID
     * @param expectedType 期望的挑战类型
     * @return 挑战对象，如果无效则返回null
     */
    PasskeyChallenge validateAndConsumeChallenge(String challengeId, String expectedType);
}