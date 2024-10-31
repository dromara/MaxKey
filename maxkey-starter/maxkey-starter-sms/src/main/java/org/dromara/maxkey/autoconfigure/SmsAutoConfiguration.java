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
 

package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.dromara.maxkey.password.sms.SmsOtpAuthnService;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.dromara.maxkey.persistence.service.CnfEmailSendersService;
import org.dromara.maxkey.persistence.service.CnfSmsProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class SmsAutoConfiguration  {
    private static final  Logger _logger = LoggerFactory.getLogger(SmsAutoConfiguration.class);


    @Bean(name = "smsOtpAuthnService")
    SmsOtpAuthnService smsOtpAuthnService(
            @Value("${maxkey.server.persistence}") int persistence,
            CnfSmsProviderService smsProviderService,
            CnfEmailSendersService emailSendersService,
            RedisConnectionFactory redisConnFactory) {
    	SmsOtpAuthnService smsOtpAuthnService = 
        							new SmsOtpAuthnService(smsProviderService,emailSendersService);
        
        if (persistence == ConstsPersistence.REDIS) {
            RedisOtpTokenStore redisOptTokenStore = new RedisOtpTokenStore(redisConnFactory);
            smsOtpAuthnService.setRedisOptTokenStore(redisOptTokenStore);
        }
        
        _logger.debug("SmsOtpAuthnService {} inited." , persistence == ConstsPersistence.REDIS ? "Redis" : "InMemory");
        return smsOtpAuthnService;
    }
   
}
