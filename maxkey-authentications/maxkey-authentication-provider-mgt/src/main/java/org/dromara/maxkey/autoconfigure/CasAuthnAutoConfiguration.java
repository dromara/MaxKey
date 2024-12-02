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
 

package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.authn.support.cas.CasTrustLoginService;
import org.dromara.maxkey.configuration.LoginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class CasAuthnAutoConfiguration  {
    private static final  Logger _logger = LoggerFactory.getLogger(CasAuthnAutoConfiguration.class);
    
    /**
     * CAS LoginService.
     * @return
     */
    @Bean
    CasTrustLoginService casTrustLoginService(LoginConfig loginConfig) {
        CasTrustLoginService casTrustLoginService = new CasTrustLoginService(
        		loginConfig.getCasServerUrlPrefix() , 
        		loginConfig.getCasService());
        _logger.debug("CAS Login Service init.");
        return casTrustLoginService;
    }
    
}
