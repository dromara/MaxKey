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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.dromara.maxkey.configuration.EmailConfig;
import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.MailOtpAuthnService;
import org.dromara.maxkey.password.onetimepwd.algorithm.OtpKeyUriFormat;
import org.dromara.maxkey.password.onetimepwd.impl.MailOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.impl.TimeBasedOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.dromara.maxkey.persistence.service.CnfEmailSendersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


@AutoConfiguration
public class OneTimePasswordAutoConfiguration {
    private static final  Logger _logger = LoggerFactory.getLogger(OneTimePasswordAutoConfiguration.class);

    @Bean
    OtpKeyUriFormat otpKeyUriFormat(
                @Value("${maxkey.otp.policy.type:totp}")
                String type,
                @Value("${maxkey.otp.policy.domain:MaxKey.top}")
                String domain,
                @Value("${maxkey.otp.policy.issuer:MaxKey}")
                String issuer,
                @Value("${maxkey.otp.policy.digits:6}")
                int digits,
                @Value("${maxkey.otp.policy.period:30}")
                int period) {
        
        OtpKeyUriFormat otpKeyUriFormat=new OtpKeyUriFormat(type,issuer,domain,digits,period);
        _logger.debug("OTP KeyUri Format {}" , otpKeyUriFormat);
        return otpKeyUriFormat;
    }

    @Bean(name = "mailOtpAuthnService")
    MailOtpAuthnService mailOtpAuthnService(
            @Value("${maxkey.server.persistence:0}") int persistence,
            CnfEmailSendersService emailSendersService,
            RedisConnectionFactory redisConnFactory) {
        MailOtpAuthnService otpAuthnService = 
                                    new MailOtpAuthnService(emailSendersService);
        
        if (persistence == ConstsPersistence.REDIS) {
            RedisOtpTokenStore redisOptTokenStore = new RedisOtpTokenStore(redisConnFactory);
            otpAuthnService.setRedisOptTokenStore(redisOptTokenStore);
        }
        
        _logger.debug("MailOtpAuthnService {} inited." , 
                        persistence == ConstsPersistence.REDIS ? "Redis" : "InMemory");
        return otpAuthnService;
    }
    
    @Bean
    TimeBasedOtpAuthn timeBasedOtpAuthn(
                @Value("${maxkey.otp.policy.digits:6}")
                int digits,
                @Value("${maxkey.otp.policy.period:30}")
                int period) {
        TimeBasedOtpAuthn timeBasedOtpAuthn = new TimeBasedOtpAuthn(digits , period);
        _logger.debug("TimeBasedOtpAuthn inited.");
        return timeBasedOtpAuthn;
    }
    
    @Bean
    AbstractOtpAuthn tfaOtpAuthn(
                @Value("${maxkey.login.mfa.type:TimeBasedOtpAuthn}") String mfaType,
                @Value("${maxkey.otp.policy.digits:6}")
                int digits,
                @Value("${maxkey.otp.policy.period:30}")
                int period,
                @Value("${maxkey.server.persistence:0}") int persistence,
                RedisConnectionFactory redisConnFactory) {    
        AbstractOtpAuthn tfaOtpAuthn  = new TimeBasedOtpAuthn(digits , period);
        _logger.debug("TFAOtpAuthn inited.");

        if (persistence == ConstsPersistence.REDIS) {
            RedisOtpTokenStore redisOptTokenStore = new RedisOtpTokenStore(redisConnFactory);
            tfaOtpAuthn.setOptTokenStore(redisOptTokenStore);
        }
        
        tfaOtpAuthn.initPropertys();
        return tfaOtpAuthn;
    }

    @Bean
    MailOtpAuthn mailOtpAuthn(
            EmailConfig emailConfig,
            @Value("${spring.mail.properties.mailotp.message.subject:One Time PassWord}")
            String messageSubject,
            @Value("${spring.mail.properties.mailotp.message.template:You Token is %s }")
            String messageTemplate,
            @Value("${spring.mail.properties.mailotp.message.validity:300}")
            int messageValidity,
            @Value("${spring.mail.properties.mailotp.message.type:text}")
            String messageType
    ) {
        if(messageType!= null && messageType.equalsIgnoreCase("html")) {
            Resource resource = new ClassPathResource("messages/email/forgotpassword.html");
            try {
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(resource.getInputStream()));
                messageTemplate = bufferedReader.lines().collect(Collectors.joining("\n"));
                bufferedReader.close();
            } catch (IOException e) {
            	_logger.error("mailOtpAuthn IOException ",e);
            }
        }
        _logger.trace("messageTemplate \n {}"  ,messageTemplate);
        MailOtpAuthn mailOtpAuthn = new MailOtpAuthn();
        mailOtpAuthn.setSubject(messageSubject);
        mailOtpAuthn.setMessageTemplate(messageTemplate);
        mailOtpAuthn.setEmailConfig(emailConfig);
        mailOtpAuthn.setInterval(messageValidity);
        _logger.debug("MailOtpAuthn inited.");
        return mailOtpAuthn;
    }
   
}
