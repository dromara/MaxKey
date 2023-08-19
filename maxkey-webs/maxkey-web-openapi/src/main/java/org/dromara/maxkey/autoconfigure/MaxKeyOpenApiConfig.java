/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.impl.TimeBasedOtpAuthn;
import org.dromara.maxkey.persistence.repository.LoginHistoryRepository;
import org.dromara.maxkey.persistence.repository.LoginRepository;
import org.dromara.maxkey.persistence.repository.PasswordPolicyValidator;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@AutoConfiguration
public class MaxKeyOpenApiConfig  implements InitializingBean {
    private static final  Logger logger = LoggerFactory.getLogger(MaxKeyOpenApiConfig.class);
    
	//authenticationRealm for MaxKeyMgtApplication
	@Bean
	public JdbcAuthenticationRealm authenticationRealm(
 			PasswordEncoder passwordEncoder,
	    		PasswordPolicyValidator passwordPolicyValidator,
	    		LoginRepository loginRepository,
	    		LoginHistoryRepository loginHistoryRepository,
	    		UserInfoService userInfoService,
             JdbcTemplate jdbcTemplate) {
		
        JdbcAuthenticationRealm authenticationRealm = new JdbcAuthenticationRealm(
        		passwordEncoder,
        		passwordPolicyValidator,
        		loginRepository,
        		loginHistoryRepository,
        		userInfoService,
        		jdbcTemplate);
        
        logger.debug("JdbcAuthenticationRealm inited.");
        return authenticationRealm;
    }

	@Bean
    public AbstractOtpAuthn timeBasedOtpAuthn() {
		AbstractOtpAuthn tfaOtpAuthn = new TimeBasedOtpAuthn();
	    logger.debug("TimeBasedOtpAuthn inited.");
        return tfaOtpAuthn;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

}
