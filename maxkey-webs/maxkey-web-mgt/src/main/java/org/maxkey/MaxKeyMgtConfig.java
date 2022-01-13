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
 

package org.maxkey;

import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.password.onetimepwd.impl.TimeBasedOtpAuthn;
import org.maxkey.persistence.repository.LoginHistoryRepository;
import org.maxkey.persistence.repository.LoginRepository;
import org.maxkey.persistence.repository.PasswordPolicyValidator;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MaxKeyMgtConfig  implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtConfig.class);
    
	//authenticationRealm for MaxKeyMgtApplication
	@Bean(name = "authenticationRealm")
	public JdbcAuthenticationRealm authenticationRealm(
 			PasswordEncoder passwordEncoder,
	    		PasswordPolicyValidator passwordPolicyValidator,
	    		LoginRepository loginRepository,
	    		LoginHistoryRepository loginHistoryRepository,
	    		AbstractRemeberMeService remeberMeService,
	    		UserInfoService userInfoService,
             JdbcTemplate jdbcTemplate) {
		
        JdbcAuthenticationRealm authenticationRealm = new JdbcAuthenticationRealm(
        		passwordEncoder,
        		passwordPolicyValidator,
        		loginRepository,
        		loginHistoryRepository,
        		remeberMeService,
        		userInfoService,
        		jdbcTemplate);
        
        _logger.debug("JdbcAuthenticationRealm inited.");
        return authenticationRealm;
    }

	@Bean(name = "timeBasedOtpAuthn")
    public AbstractOtpAuthn timeBasedOtpAuthn() {
		AbstractOtpAuthn tfaOtpAuthn = new TimeBasedOtpAuthn();
	    _logger.debug("TimeBasedOtpAuthn inited.");
        return tfaOtpAuthn;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

}
