/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.authn.realm.jdbc;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * same as JdbcAuthenticationRealm.
 * 
 * @author Crystal.Sea
 * 
 */
public class DefaultJdbcAuthenticationRealm extends AbstractAuthenticationRealm {
    private static Logger _logger = LoggerFactory.getLogger(DefaultJdbcAuthenticationRealm.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DefaultJdbcAuthenticationRealm() {

    }

    public DefaultJdbcAuthenticationRealm(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * passwordMatches.
     */
    public boolean passwordMatches(UserInfo userInfo, String password) {
        boolean passwordMatches = false;
        _logger.info("password : " 
                + PasswordReciprocal.getInstance().rawPassword(userInfo.getUsername(), password));
        passwordMatches = passwordEncoder.matches(password,userInfo.getPassword());
        _logger.debug("passwordvalid : " + passwordMatches);
        if (!passwordMatches) {
            passwordPolicyValidator.setBadPasswordCount(userInfo);
            insertLoginHistory(userInfo, ConstantsLoginType.LOCAL, "", "xe00000004", "password error");
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.password"));
        }
        return passwordMatches;
    }
    
    
    
 
}
