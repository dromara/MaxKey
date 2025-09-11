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

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.dromara.mybatis.jpa.IJpaMapper;

/**
 * UserPasskey Mapper 接口
 * 提供用户 Passkey 凭据的数据库操作方法
 * 
 * @author MaxKey Team
 */
public interface UserPasskeyMapper extends IJpaMapper<UserPasskey> {
    
    /**
     * 根据用户ID查询所有Passkey凭据
     * 
     * @param userId 用户ID
     * @return Passkey凭据列表
     */
    @Select("SELECT * FROM mxk_user_passkeys WHERE user_id = #{userId} AND status = 1 ORDER BY created_date DESC")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "credential_id", property = "credentialId"),
        @Result(column = "public_key", property = "publicKey"),
        @Result(column = "signature_count", property = "signatureCount"),
        @Result(column = "aaguid", property = "aaguid"),
        @Result(column = "display_name", property = "displayName"),
        @Result(column = "device_type", property = "deviceType"),
        @Result(column = "created_date", property = "createdDate"),
        @Result(column = "last_used_date", property = "lastUsedDate"),
        @Result(column = "status", property = "status"),
        @Result(column = "inst_id", property = "instId")
    })
    List<UserPasskey> findByUserId(String userId);
    
    /**
     * 根据凭据ID查询Passkey
     * 
     * @param credentialId 凭据ID（Base64编码）
     * @return UserPasskey对象
     */
    @Select("SELECT * FROM mxk_user_passkeys WHERE credential_id = #{credentialId} AND status = 1")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "credential_id", property = "credentialId"),
        @Result(column = "public_key", property = "publicKey"),
        @Result(column = "signature_count", property = "signatureCount"),
        @Result(column = "aaguid", property = "aaguid"),
        @Result(column = "display_name", property = "displayName"),
        @Result(column = "device_type", property = "deviceType"),
        @Result(column = "created_date", property = "createdDate"),
        @Result(column = "last_used_date", property = "lastUsedDate"),
        @Result(column = "status", property = "status"),
        @Result(column = "inst_id", property = "instId")
    })
    UserPasskey findByCredentialId(String credentialId);
    
    /**
     * 根据用户ID和凭据ID查询Passkey
     * 
     * @param userId 用户ID
     * @param credentialId 凭据ID
     * @return UserPasskey对象
     */
    @Select("SELECT * FROM mxk_user_passkeys WHERE user_id = #{userId} AND credential_id = #{credentialId} AND status = 1")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "credential_id", property = "credentialId"),
        @Result(column = "public_key", property = "publicKey"),
        @Result(column = "signature_count", property = "signatureCount"),
        @Result(column = "aaguid", property = "aaguid"),
        @Result(column = "display_name", property = "displayName"),
        @Result(column = "device_type", property = "deviceType"),
        @Result(column = "created_date", property = "createdDate"),
        @Result(column = "last_used_date", property = "lastUsedDate"),
        @Result(column = "status", property = "status"),
        @Result(column = "inst_id", property = "instId")
    })
    UserPasskey findByUserIdAndCredentialId(String userId, String credentialId);
    
    /**
     * 更新签名计数器
     * 
     * @param credentialId 凭据ID
     * @param signatureCount 新的签名计数
     * @return 更新的记录数
     */
    @Update("UPDATE mxk_user_passkeys SET signature_count = #{signatureCount}, last_used_date = NOW() WHERE credential_id = #{credentialId}")
    int updateSignatureCount(String credentialId, Long signatureCount);
    
    /**
     * 物理删除Passkey
     * 
     * @param userId 用户ID
     * @param credentialId 凭据ID
     * @return 删除的记录数
     */
    @Delete("DELETE FROM mxk_user_passkeys WHERE user_id = #{userId} AND credential_id = #{credentialId}")
    int deleteByUserIdAndCredentialId(String userId, String credentialId);
    
    /**
     * 物理删除过期的Passkey记录
     * 
     * @return 删除的记录数
     */
    @Delete("DELETE FROM mxk_user_passkeys WHERE status = 0 AND created_date < DATE_SUB(NOW(), INTERVAL 30 DAY)")
    int cleanExpiredPasskeys();
    
    /**
     * 统计用户的Passkey数量
     * 
     * @param userId 用户ID
     * @return Passkey数量
     */
    @Select("SELECT COUNT(*) FROM mxk_user_passkeys WHERE user_id = #{userId} AND status = 1")
    int countByUserId(String userId);
    
    /**
     * 检查凭据ID是否已存在
     * 
     * @param credentialId 凭据ID
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) > 0 FROM mxk_user_passkeys WHERE credential_id = #{credentialId} AND status = 1")
    boolean existsByCredentialId(String credentialId);
}