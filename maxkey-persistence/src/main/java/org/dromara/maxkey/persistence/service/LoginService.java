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

import java.util.List;

import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.springframework.security.core.GrantedAuthority;

public interface LoginService {
    
    public UserInfo find(String username, String password);

    public List<UserInfo> findByUsername(String username, String password);

    public List<UserInfo> findByUsernameOrMobile(String username, String password);

    public List<UserInfo> findByUsernameOrMobileOrEmail(String username, String password);


    
    /**
     * dynamic passwordPolicy Valid for user login.
     * @param userInfo
     * @return boolean
     */
    public boolean passwordPolicyValid(UserInfo userInfo) ;
    
    public void applyPasswordPolicy(UserInfo userInfo) ;
    
    /**
     * lockUser
     * 
     * @param userInfo
     */
    public void lockUser(UserInfo userInfo) ;
    

    /**
     * unlockUser
     * 
     * @param userInfo
     */
    public void unlockUser(UserInfo userInfo);

    /**
    * reset BadPasswordCount And Lockout
     * 
     * @param userInfo
     */
    public void resetAttempts(UserInfo userInfo);

    public void plusBadPasswordCount(UserInfo userInfo) ;
    
    public void resetBadPasswordCount(UserInfo userInfo);

    public List<GrantedAuthority> queryAuthorizedApps(List<GrantedAuthority> grantedAuthoritys);

    public List<Groups> queryGroups(UserInfo userInfo) ;

    /**
     * grant Authority by userinfo
     *
     * @param userInfo
     * @return ArrayList<GrantedAuthority>
     */
    public List<GrantedAuthority> grantAuthority(UserInfo userInfo) ;


    public void updateLastLogin(UserInfo userInfo) ;
}


