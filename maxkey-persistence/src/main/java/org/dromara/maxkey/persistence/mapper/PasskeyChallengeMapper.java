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

package org.dromara.maxkey.persistence.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.dromara.maxkey.entity.passkey.PasskeyChallenge;
import org.dromara.mybatis.jpa.IJpaMapper;

/**
 * PasskeyChallenge Mapper 接口
 * 提供 Passkey 挑战数据的数据库操作方法
 * 
 * @author MaxKey Team
 */
public interface PasskeyChallengeMapper extends IJpaMapper<PasskeyChallenge> {
    
    /**
     * 根据挑战ID查询挑战数据
     * 
     * @param challengeId 挑战ID
     * @return PasskeyChallenge对象
     */
    @Select("SELECT * FROM mxk_passkey_challenges WHERE id = #{challengeId}")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "challenge", property = "challenge"),
        @Result(column = "challenge_type", property = "challengeType"),
        @Result(column = "session_id", property = "sessionId"),
        @Result(column = "created_date", property = "createdDate"),
        @Result(column = "expires_date", property = "expiresDate"),
        @Result(column = "status", property = "status"),
        @Result(column = "inst_id", property = "instId")
    })
    PasskeyChallenge findByChallengeId(String challengeId);
    
    /**
     * 根据用户ID和挑战类型查询最新的挑战
     * 
     * @param userId 用户ID
     * @param challengeType 挑战类型（REGISTRATION/AUTHENTICATION）
     * @return PasskeyChallenge对象
     */
    @Select("SELECT * FROM mxk_passkey_challenges WHERE user_id = #{userId} AND challenge_type = #{challengeType} ORDER BY created_date DESC LIMIT 1")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "challenge", property = "challenge"),
        @Result(column = "challenge_type", property = "challengeType"),
        @Result(column = "session_id", property = "sessionId"),
        @Result(column = "created_date", property = "createdDate"),
        @Result(column = "expires_date", property = "expiresDate"),
        @Result(column = "status", property = "status"),
        @Result(column = "inst_id", property = "instId")
    })
    PasskeyChallenge findLatestByUserIdAndType(@Param("userId") String userId, @Param("challengeType") String challengeType);
    
    /**
     * 删除指定挑战ID的记录
     * 
     * @param challengeId 挑战ID
     * @return 删除的记录数
     */
    @Delete("DELETE FROM mxk_passkey_challenges WHERE id = #{challengeId}")
    int deleteByChallengeId(String challengeId);
    
    /**
     * 清理过期的挑战记录
     * 
     * @return 清理的记录数
     */
    @Delete("DELETE FROM mxk_passkey_challenges WHERE expires_date < NOW()")
    int cleanExpiredChallenges();
    
    /**
     * 清理指定用户的所有挑战记录
     * 
     * @param userId 用户ID
     * @return 清理的记录数
     */
    @Delete("DELETE FROM mxk_passkey_challenges WHERE user_id = #{userId}")
    int deleteByUserId(String userId);
    
    /**
     * 清理指定用户和类型的挑战记录
     * 
     * @param userId 用户ID
     * @param challengeType 挑战类型
     * @return 清理的记录数
     */
    @Delete("DELETE FROM mxk_passkey_challenges WHERE user_id = #{userId} AND challenge_type = #{challengeType}")
    int deleteByUserIdAndType(@Param("userId") String userId, @Param("challengeType") String challengeType);
    
    /**
     * 统计指定用户的挑战数量
     * 
     * @param userId 用户ID
     * @return 挑战数量
     */
    @Select("SELECT COUNT(*) FROM mxk_passkey_challenges WHERE user_id = #{userId}")
    int countByUserId(String userId);
    
    /**
     * 检查挑战ID是否存在且未过期
     * 
     * @param challengeId 挑战ID
     * @return 是否存在且有效
     */
    @Select("SELECT COUNT(*) > 0 FROM mxk_passkey_challenges WHERE id = #{challengeId} AND expires_date > NOW()")
    boolean existsValidChallenge(String challengeId);
}