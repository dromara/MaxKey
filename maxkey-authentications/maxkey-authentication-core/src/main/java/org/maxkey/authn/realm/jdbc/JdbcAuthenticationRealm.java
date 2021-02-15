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

import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.persistence.db.LoginHistoryService;
import org.maxkey.persistence.db.LoginService;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * JdbcAuthenticationRealm.
 * @author Crystal.Sea
 *
 */
public class JdbcAuthenticationRealm extends DefaultJdbcAuthenticationRealm {
    private static Logger _logger = LoggerFactory.getLogger(JdbcAuthenticationRealm.class);

    public JdbcAuthenticationRealm() {
        _logger.debug("init . ");
    }

    public JdbcAuthenticationRealm(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public JdbcAuthenticationRealm(
    		PasswordEncoder passwordEncoder,
    		PasswordPolicyValidator passwordPolicyValidator,
    		LoginService loginService,
    		LoginHistoryService loginHistoryService,
    		AbstractRemeberMeService remeberMeService,
    	    JdbcTemplate jdbcTemplate) {
    	
    	this.passwordEncoder =passwordEncoder;
    	this.passwordPolicyValidator=passwordPolicyValidator;
    	this.loginService = loginService;
    	this.loginHistoryService = loginHistoryService;
    	this.remeberMeService = remeberMeService;
        this.jdbcTemplate = jdbcTemplate;
    }
    
    

}
