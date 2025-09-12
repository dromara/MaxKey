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

package org.dromara.maxkey.passkey.service;

import org.dromara.maxkey.entity.passkey.UserPasskey;
import org.dromara.maxkey.entity.passkey.PasskeyChallenge;
import java.util.List;
import java.util.Map;

/**
 * Passkey服务接口
 */
public interface PasskeyService {

    /**
     * 生成注册选项
     * @param userId 用户ID
     * @param username 用户名
     * @param displayName 显示名称
     * @return 注册选项JSON
     */
    Map<String, Object> generateRegistrationOptions(String userId, String username, String displayName);

    /**
     * 验证注册响应
     * @param userId 用户ID
     * @param registrationResponse 注册响应JSON
     * @return 验证成功时返回新创建的UserPasskey对象，失败时返回null
     */
    UserPasskey verifyRegistrationResponse(String userId, Map<String, Object> registrationResponse);

    /**
     * 生成认证选项
     * @param userId 用户ID（可选，为空时返回所有可用凭据）
     * @return 认证选项JSON
     */
    Map<String, Object> generateAuthenticationOptions(String userId);

    /**
     * 验证认证响应
     * @param authenticationResponse 认证响应JSON
     * @return 验证结果，包含用户ID
     */
    Map<String, Object> verifyAuthenticationResponse(Map<String, Object> authenticationResponse);

    /**
     * 获取用户的所有Passkey凭据
     * @param userId 用户ID
     * @return Passkey凭据列表
     */
    List<UserPasskey> getUserPasskeys(String userId);

    /**
     * 删除Passkey凭据
     * @param userId 用户ID
     * @param credentialId 凭据ID
     * @return 删除结果
     */
    boolean deletePasskey(String userId, String credentialId);

    /**
     * 保存Passkey凭据
     * @param userPasskey Passkey凭据
     * @return 保存结果
     */
    boolean savePasskey(UserPasskey userPasskey);

    /**
     * 根据凭据ID获取Passkey
     * @param credentialId 凭据ID
     * @return Passkey凭据
     */
    UserPasskey getPasskeyByCredentialId(String credentialId);

    /**
     * 更新签名计数
     * @param credentialId 凭据ID
     * @param signatureCount 新的签名计数
     * @return 更新结果
     */
    boolean updateSignatureCount(String credentialId, Long signatureCount);

    /**
     * 保存挑战信息
     * @param challenge 挑战信息
     * @return 保存结果
     */
    boolean saveChallenge(PasskeyChallenge challenge);

    /**
     * 获取挑战信息
     * @param challengeId 挑战ID
     * @return 挑战信息
     */
    PasskeyChallenge getChallenge(String challengeId);

    /**
     * 删除过期的挑战信息
     * @return 删除的记录数
     */
    int cleanExpiredChallenges();

    /**
     * 检查系统中是否有任何注册的 Passkey
     * @return 如果系统中有注册的 Passkey 返回 true，否则返回 false
     */
    boolean hasAnyRegisteredPasskeys();
}