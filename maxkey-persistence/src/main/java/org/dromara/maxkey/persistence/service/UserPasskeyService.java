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

package org.dromara.maxkey.persistence.service;

import java.util.List;

import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.dromara.mybatis.jpa.IJpaService;

/**
 * UserPasskey Service 接口
 * 提供用户 Passkey 凭据的业务操作方法
 * 
 * @author MaxKey Team
 */
public interface UserPasskeyService extends IJpaService<UserPasskey> {
    
    /**
     * 根据用户ID查询所有Passkey凭据
     * 
     * @param userId 用户ID
     * @return Passkey凭据列表
     */
    List<UserPasskey> findByUserId(String userId);
    
    /**
     * 根据凭据ID查询Passkey
     * 
     * @param credentialId 凭据ID（Base64编码）
     * @return UserPasskey对象
     */
    UserPasskey findByCredentialId(String credentialId);
    
    /**
     * 根据用户ID和凭据ID查询Passkey
     * 
     * @param userId 用户ID
     * @param credentialId 凭据ID
     * @return UserPasskey对象
     */
    UserPasskey findByUserIdAndCredentialId(String userId, String credentialId);
    
    /**
     * 保存或更新Passkey凭据
     * 
     * @param userPasskey Passkey凭据对象
     * @return 是否成功
     */
    boolean saveOrUpdatePasskey(UserPasskey userPasskey);
    
    /**
     * 更新签名计数器
     * 
     * @param credentialId 凭据ID
     * @param signatureCount 新的签名计数
     * @return 是否成功
     */
    boolean updateSignatureCount(String credentialId, Long signatureCount);
    
    /**
     * 删除用户的Passkey凭据
     * 
     * @param userId 用户ID
     * @param credentialId 凭据ID
     * @return 是否成功
     */
    boolean deletePasskey(String userId, String credentialId);
    
    /**
     * 清理过期的Passkey记录
     * 
     * @return 清理的记录数
     */
    int cleanExpiredPasskeys();
    
    /**
     * 统计用户的Passkey数量
     * 
     * @param userId 用户ID
     * @return Passkey数量
     */
    int countByUserId(String userId);
    
    /**
     * 检查凭据ID是否已存在
     * 
     * @param credentialId 凭据ID
     * @return 是否存在
     */
    boolean existsByCredentialId(String credentialId);
    
    /**
     * 获取用户的Passkey统计信息
     * 
     * @param userId 用户ID
     * @return 统计信息Map
     */
    java.util.Map<String, Object> getPasskeyStats(String userId);
}