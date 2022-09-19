/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.autoconfigure;

import org.maxkey.constants.ConstsPersistence;
import org.maxkey.password.onetimepwd.OtpAuthnService;
import org.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.maxkey.persistence.service.EmailSendersService;
import org.maxkey.persistence.service.SmsProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class OneTimePasswordAutoConfiguration  implements InitializingBean {
    private static final  Logger _logger = 
            LoggerFactory.getLogger(OneTimePasswordAutoConfiguration.class);
    
  
    @Bean(name = "otpAuthnService")
    public OtpAuthnService otpAuthnService(
            @Value("${maxkey.server.persistence}") int persistence,
            SmsProviderService smsProviderService,
            EmailSendersService emailSendersService,
            RedisConnectionFactory redisConnFactory) {
        OtpAuthnService otpAuthnService = 
        							new OtpAuthnService(smsProviderService,emailSendersService);
        
        if (persistence == ConstsPersistence.REDIS) {
            RedisOtpTokenStore redisOptTokenStore = new RedisOtpTokenStore(redisConnFactory);
            otpAuthnService.setRedisOptTokenStore(redisOptTokenStore);
        }
        
        _logger.debug("OneTimePasswordService {} inited." , 
        				persistence == ConstsPersistence.REDIS ? "Redis" : "InMemory");
        return otpAuthnService;
    }
   
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
