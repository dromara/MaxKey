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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm;
import org.maxkey.authn.realm.ldap.LdapAuthenticationRealm;
import org.maxkey.authn.realm.ldap.LdapServer;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.realm.IAuthenticationServer;
import org.maxkey.authn.realm.activedirectory.ActiveDirectoryAuthenticationRealm;
import org.maxkey.authn.realm.activedirectory.ActiveDirectoryServer;
import org.maxkey.authn.support.kerberos.KerberosProxy;
import org.maxkey.authn.support.kerberos.RemoteKerberosService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.configuration.EmailConfig;
import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.password.onetimepwd.algorithm.OtpKeyUriFormat;
import org.maxkey.password.onetimepwd.impl.MailOtpAuthn;
import org.maxkey.password.onetimepwd.impl.SmsOtpAuthn;
import org.maxkey.password.onetimepwd.impl.TimeBasedOtpAuthn;
import org.maxkey.password.onetimepwd.impl.sms.SmsOtpAuthnAliyun;
import org.maxkey.password.onetimepwd.impl.sms.SmsOtpAuthnTencentCloud;
import org.maxkey.password.onetimepwd.impl.sms.SmsOtpAuthnYunxin;
import org.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.maxkey.persistence.ldap.ActiveDirectoryUtils;
import org.maxkey.persistence.ldap.LdapUtils;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.maxkey.persistence.repository.LoginHistoryRepository;
import org.maxkey.persistence.repository.LoginRepository;
import org.maxkey.persistence.repository.PasswordPolicyValidator;
import org.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = {
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
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyConfig.class);
    

    @Bean(name = "otpKeyUriFormat")
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
        _logger.debug("OTP KeyUri Format " + otpKeyUriFormat);
        return otpKeyUriFormat;
    }
    
    public AbstractAuthenticationRealm ldapAuthenticationRealm(
    			boolean ldapSupport,
    			boolean ldapJit,
    			String providerUrl,
    			String principal,
    			String credentials,
    			String filter,
    			String baseDN,
    			String domain,
    			String product,
                JdbcTemplate jdbcTemplate) {
    	
    	AbstractAuthenticationRealm authenticationRealm =null;
    	if(ldapSupport) {
	    	if(product.equalsIgnoreCase("activedirectory")) {
	    		ActiveDirectoryAuthenticationRealm activeDirectoryAuthenticationRealm = new ActiveDirectoryAuthenticationRealm(jdbcTemplate);
	            ActiveDirectoryServer ldapServer=new ActiveDirectoryServer();
	            ActiveDirectoryUtils ldapUtils = new ActiveDirectoryUtils(providerUrl,principal,credentials,domain);
	            ldapServer.setActiveDirectoryUtils(ldapUtils);
	            
	            List<IAuthenticationServer> ldapServers = new ArrayList<IAuthenticationServer>();
	            ldapServers.add(ldapServer);
	            activeDirectoryAuthenticationRealm.setActiveDirectoryServers(ldapServers);
	            authenticationRealm = activeDirectoryAuthenticationRealm;
	            _logger.debug("ActiveDirectoryAuthenticationRealm inited.");
	    	}else {
	    		LdapAuthenticationRealm ldapAuthenticationRealm = new LdapAuthenticationRealm(jdbcTemplate);
		        LdapServer ldapServer=new LdapServer();
		        LdapUtils ldapUtils = new LdapUtils(providerUrl,principal,credentials,baseDN);
		        ldapServer.setLdapUtils(ldapUtils);
		        ldapServer.setFilterAttribute(filter);
		        List<IAuthenticationServer> ldapServers = new ArrayList<IAuthenticationServer>();
		        ldapServers.add(ldapServer);
		        ldapAuthenticationRealm.setLdapServers(ldapServers);
		        authenticationRealm = ldapAuthenticationRealm;
		        _logger.debug("LdapAuthenticationRealm inited.");
	    	}
    	}
        return authenticationRealm;
        
    }
    
    //可以在此实现其他的登陆认证方式，请实现AbstractAuthenticationRealm
    @Bean(name = "authenticationRealm")
    public JdbcAuthenticationRealm authenticationRealm(
    			PasswordEncoder passwordEncoder,
	    		PasswordPolicyValidator passwordPolicyValidator,
	    		LoginRepository loginService,
	    		LoginHistoryRepository loginHistoryService,
	    		AbstractRemeberMeService remeberMeService,
	    		UserInfoService userInfoService,
                JdbcTemplate jdbcTemplate,
                @Value("${maxkey.login.ldap.enable:false}")boolean ldapSupport,
    			@Value("${maxkey.login.ldap.jit:false}")boolean ldapJit,
    			@Value("${maxkey.login.ldap.providerurl}")String providerUrl,
    			@Value("${maxkey.login.ldap.principal}")String principal,
    			@Value("${maxkey.login.ldap.credentials}")String credentials,
    			@Value("${maxkey.login.ldap.filter}")String filter,
    			@Value("${maxkey.login.ldap.basedn}")String baseDN,
    			@Value("${maxkey.login.ldap.activedirectory.domain}")String domain,
    			@Value("${maxkey.login.ldap.product:openldap}")String product) {
    	AbstractAuthenticationRealm ldapAuthenticationRealm = 
    			ldapAuthenticationRealm(
					ldapSupport,ldapJit,
					providerUrl,principal,credentials,
					filter,baseDN,domain,product,
					jdbcTemplate
				);
        JdbcAuthenticationRealm authenticationRealm = new JdbcAuthenticationRealm(
        		passwordEncoder,
        		passwordPolicyValidator,
        		loginService,
        		loginHistoryService,
        		remeberMeService,
        		userInfoService,
        		jdbcTemplate,
        		ldapAuthenticationRealm,
        		ldapSupport
        	);
        
        return authenticationRealm;
    }
    
	@Bean(name = "timeBasedOtpAuthn")
    public TimeBasedOtpAuthn timeBasedOtpAuthn(
                @Value("${maxkey.otp.policy.digits:6}")
                int digits,
                @Value("${maxkey.otp.policy.period:30}")
                int period) {
	    TimeBasedOtpAuthn tfaOtpAuthn = new TimeBasedOtpAuthn(digits , period);
	    _logger.debug("TimeBasedOtpAuthn inited.");
        return tfaOtpAuthn;
    }
    
    @Bean(name = "tfaOtpAuthn")
    public AbstractOtpAuthn tfaOptAuthn(
                @Value("${maxkey.login.mfa.type}")String mfaType,
                @Value("${maxkey.otp.policy.digits:6}")
                int digits,
                @Value("${maxkey.otp.policy.period:30}")
                int period,
                @Value("${maxkey.server.persistence}") int persistence,
                RedisConnectionFactory redisConnFactory) {    
        AbstractOtpAuthn tfaOtpAuthn  = new TimeBasedOtpAuthn(digits , period);
        _logger.debug("TimeBasedOtpAuthn inited.");

        if (persistence == ConstantsPersistence.REDIS) {
            RedisOtpTokenStore redisOptTokenStore = new RedisOtpTokenStore(redisConnFactory);
            tfaOtpAuthn.setOptTokenStore(redisOptTokenStore);
        }
        
        tfaOtpAuthn.initPropertys();
        return tfaOtpAuthn;
    }
    
    @Bean(name = "mailOtpAuthn")
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
                 _logger.error("mailOtpAuthn IOException ",e);
            }
        }
        _logger.trace("messageTemplate \n" +messageTemplate);
        MailOtpAuthn mailOtpAuthn = new MailOtpAuthn();
        mailOtpAuthn.setSubject(messageSubject);
        mailOtpAuthn.setMessageTemplate(messageTemplate);
        mailOtpAuthn.setEmailConfig(emailConfig);
        mailOtpAuthn.setInterval(messageValidity);
        _logger.debug("MailOtpAuthn inited.");
        return mailOtpAuthn;
    }
    
    @Bean(name = "smsOtpAuthn")
    public SmsOtpAuthn smsOtpAuthn(
            @Value("${maxkey.otp.sms.provider}")String provider,
            @Value("${maxkey.server.persistence}") int persistence,
            StandardEnvironment environment,
            RedisConnectionFactory redisConnFactory) {
        SmsOtpAuthn smsOtpAuthn = null;
        if(provider.equalsIgnoreCase("aliyun")) {
            smsOtpAuthn = new SmsOtpAuthnAliyun();
        }else if(provider.equalsIgnoreCase("tencentcloud")) {
            smsOtpAuthn = new SmsOtpAuthnTencentCloud();
        }else {
            smsOtpAuthn = new SmsOtpAuthnYunxin();
        }
        if (persistence == ConstantsPersistence.REDIS) {
            RedisOtpTokenStore redisOptTokenStore = new RedisOtpTokenStore(redisConnFactory);
            smsOtpAuthn.setOptTokenStore(redisOptTokenStore);
        }
        
        smsOtpAuthn.setProperties(environment);
        smsOtpAuthn.initPropertys();
        
        _logger.debug("SmsOtpAuthn {} inited." ,smsOtpAuthn.getClass().getCanonicalName());
        return smsOtpAuthn;
    }
    
    
    @Bean(name = "kerberosService")
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
        
        List<KerberosProxy> kerberosProxysList = new ArrayList<KerberosProxy>();
        kerberosProxysList.add(kerberosProxy);
        kerberosService.setKerberosProxys(kerberosProxysList);
        
        _logger.debug("RemoteKerberosService inited.");
        return kerberosService;
    }
    

    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }


    
}
