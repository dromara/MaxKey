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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dromara.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm;
import org.dromara.maxkey.authn.realm.ldap.LdapAuthenticationRealmService;
import org.dromara.maxkey.authn.support.kerberos.KerberosProxy;
import org.dromara.maxkey.authn.support.kerberos.RemoteKerberosService;
import org.dromara.maxkey.configuration.EmailConfig;
import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.MailOtpAuthnService;
import org.dromara.maxkey.password.onetimepwd.algorithm.OtpKeyUriFormat;
import org.dromara.maxkey.password.onetimepwd.impl.MailOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.impl.TimeBasedOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.dromara.maxkey.persistence.repository.LoginHistoryRepository;
import org.dromara.maxkey.persistence.repository.LoginRepository;
import org.dromara.maxkey.persistence.repository.PasswordPolicyValidator;
import org.dromara.maxkey.persistence.service.LdapContextService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@AutoConfiguration
@ComponentScan(basePackages = {
		"org.maxkey.authn",
        "org.maxkey.configuration",
        "org.maxkey.domain",
        "org.maxkey.domain.apps",
        "org.maxkey.domain.userinfo",
        "org.maxkey.api.v1.contorller",
        "org.maxkey.web.endpoint",
        "org.maxkey.web.contorller",
        "org.maxkey.web.interceptor",
        //single sign on protocol
        "org.maxkey.authz.endpoint",
        "org.maxkey.authz.desktop.endpoint",
        "org.maxkey.authz.exapi.endpoint",
        "org.maxkey.authz.formbased.endpoint",
        "org.maxkey.authz.ltpa.endpoint",
        "org.maxkey.authz.token.endpoint"
})
public class MaxKeyConfig  implements InitializingBean {
    private static final  Logger logger = LoggerFactory.getLogger(MaxKeyConfig.class);
    

    @Bean
    public OtpKeyUriFormat otpKeyUriFormat(
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
        logger.debug("OTP KeyUri Format {}" , otpKeyUriFormat);
        return otpKeyUriFormat;
    }
    
    //可以在此实现其他的登陆认证方式，请实现AbstractAuthenticationRealm
    @Bean
    public JdbcAuthenticationRealm authenticationRealm(
    			PasswordEncoder passwordEncoder,
	    		PasswordPolicyValidator passwordPolicyValidator,
	    		LoginRepository loginService,
	    		LoginHistoryRepository loginHistoryService,
	    		UserInfoService userInfoService,
                JdbcTemplate jdbcTemplate,
                MailOtpAuthnService otpAuthnService,
                LdapContextService ldapContextService) {
    	LdapAuthenticationRealmService ldapRealmService = new LdapAuthenticationRealmService(ldapContextService);
        return new JdbcAuthenticationRealm(
        		passwordEncoder,
        		passwordPolicyValidator,
        		loginService,
        		loginHistoryService,
        		userInfoService,
        		jdbcTemplate,
        		ldapRealmService
        	);
    }
    
	@Bean
    public TimeBasedOtpAuthn timeBasedOtpAuthn(
                @Value("${maxkey.otp.policy.digits:6}")
                int digits,
                @Value("${maxkey.otp.policy.period:30}")
                int period) {
	    TimeBasedOtpAuthn tfaOtpAuthn = new TimeBasedOtpAuthn(digits , period);
	    logger.debug("TimeBasedOtpAuthn inited.");
        return tfaOtpAuthn;
    }
    
    @Bean
    public AbstractOtpAuthn tfaOtpAuthn(
                @Value("${maxkey.login.mfa.type}")String mfaType,
                @Value("${maxkey.otp.policy.digits:6}")
                int digits,
                @Value("${maxkey.otp.policy.period:30}")
                int period,
                @Value("${maxkey.server.persistence}") int persistence,
                RedisConnectionFactory redisConnFactory) {    
        AbstractOtpAuthn tfaOtpAuthn  = new TimeBasedOtpAuthn(digits , period);
        logger.debug("TimeBasedOtpAuthn inited.");

        if (persistence == ConstsPersistence.REDIS) {
            RedisOtpTokenStore redisOptTokenStore = new RedisOtpTokenStore(redisConnFactory);
            tfaOtpAuthn.setOptTokenStore(redisOptTokenStore);
        }
        
        tfaOtpAuthn.initPropertys();
        return tfaOtpAuthn;
    }
    
    @Bean
    public MailOtpAuthn mailOtpAuthn(
            EmailConfig emailConfig,
            @Value("${spring.mail.properties.mailotp.message.subject}")
            String messageSubject,
            @Value("${spring.mail.properties.mailotp.message.template}")
            String messageTemplate,
            @Value("${spring.mail.properties.mailotp.message.validity}")
            int messageValidity,
            @Value("${spring.mail.properties.mailotp.message.type}")
            String messageType
            ) {
        if(messageType!= null && messageType.equalsIgnoreCase("html")) {
            Resource resource = new ClassPathResource("messages/email/forgotpassword.html");
            try {
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(resource.getInputStream()));
                messageTemplate = bufferedReader.lines().collect(Collectors.joining("\n"));
                bufferedReader.close();
            } catch (IOException e) {
                 logger.error("mailOtpAuthn IOException ",e);
            }
        }
        logger.trace("messageTemplate \n {}"  ,messageTemplate);
        MailOtpAuthn mailOtpAuthn = new MailOtpAuthn();
        mailOtpAuthn.setSubject(messageSubject);
        mailOtpAuthn.setMessageTemplate(messageTemplate);
        mailOtpAuthn.setEmailConfig(emailConfig);
        mailOtpAuthn.setInterval(messageValidity);
        logger.debug("MailOtpAuthn inited.");
        return mailOtpAuthn;
    }
    
    
    @Bean
    public RemoteKerberosService kerberosService(
            @Value("${maxkey.login.kerberos.default.userdomain}")
            String userDomain,
            @Value("${maxkey.login.kerberos.default.fulluserdomain}")
            String fullUserDomain,
            @Value("${maxkey.login.kerberos.default.crypto}")
            String crypto,
            @Value("${maxkey.login.kerberos.default.redirecturi}")
            String redirectUri
            ) {
        RemoteKerberosService kerberosService = new RemoteKerberosService();
        KerberosProxy kerberosProxy = new KerberosProxy();
        
        kerberosProxy.setCrypto(crypto);
        kerberosProxy.setFullUserdomain(fullUserDomain);
        kerberosProxy.setUserdomain(userDomain);
        kerberosProxy.setRedirectUri(redirectUri);
        
        List<KerberosProxy> kerberosProxysList = new ArrayList<>();
        kerberosProxysList.add(kerberosProxy);
        kerberosService.setKerberosProxys(kerberosProxysList);
        
        logger.debug("RemoteKerberosService inited.");
        return kerberosService;
    }
    

    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }


    
}
