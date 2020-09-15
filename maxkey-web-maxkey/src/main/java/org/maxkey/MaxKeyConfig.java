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

import java.util.ArrayList;
import java.util.List;
import org.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm;
import org.maxkey.authn.realm.ldap.LdapAuthenticationRealm;
import org.maxkey.authn.realm.ldap.LdapServer;
import org.maxkey.authn.realm.IAuthenticationServer;
import org.maxkey.authn.realm.activedirectory.ActiveDirectoryAuthenticationRealm;
import org.maxkey.authn.realm.activedirectory.ActiveDirectoryServer;
import org.maxkey.authn.support.kerberos.KerberosProxy;
import org.maxkey.authn.support.kerberos.RemoteKerberosService;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.crypto.password.opt.algorithm.KeyUriFormat;
import org.maxkey.crypto.password.opt.impl.MailOtpAuthn;
import org.maxkey.crypto.password.opt.impl.SmsOtpAuthn;
import org.maxkey.crypto.password.opt.impl.TimeBasedOtpAuthn;
import org.maxkey.crypto.password.opt.impl.sms.SmsOtpAuthnAliyun;
import org.maxkey.crypto.password.opt.impl.sms.SmsOtpAuthnTencentCloud;
import org.maxkey.crypto.password.opt.impl.sms.SmsOtpAuthnYunxin;
import org.maxkey.persistence.ldap.ActiveDirectoryUtils;
import org.maxkey.persistence.ldap.LdapUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;




@Configuration
//@ImportResource(locations = { "classpath:spring/maxkey.xml" })
@PropertySource(ConstantsProperties.applicationPropertySource)
@PropertySource(ConstantsProperties.maxKeyPropertySource)
@MapperScan("org.maxkey.persistence.mapper,")
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
    

    @Bean(name = "keyUriFormat")
    public KeyUriFormat keyUriFormat(
            @Value("${config.otp.keyuri.format.type:totp}")
            String keyuriFormatType,
            @Value("${config.otp.keyuri.format.domain:MaxKey.top}")
            String keyuriFormatDomain,
            @Value("${config.otp.keyuri.format.issuer:MaxKey}")
            String keyuriFormatIssuer,
            @Value("${config.otp.keyuri.format.digits:6}")
            int keyuriFormatDigits,
            @Value("${config.otp.keyuri.format.period:30}")
            int keyuriFormatPeriod) {
        
        KeyUriFormat keyUriFormat=new KeyUriFormat();
        keyUriFormat.setType(keyuriFormatType);
        keyUriFormat.setDomain(keyuriFormatDomain);
        keyUriFormat.setIssuer(keyuriFormatIssuer);
        keyUriFormat.setDigits(keyuriFormatDigits);
        keyUriFormat.setPeriod(keyuriFormatPeriod);
        _logger.debug("KeyUri Format " + keyUriFormat);
        return keyUriFormat;
    }
    
    //可以在此实现其他的登陆认证方式，请实现AbstractAuthenticationRealm
    @Bean(name = "authenticationRealm")
    public JdbcAuthenticationRealm authenticationRealm(
                JdbcTemplate jdbcTemplate) {
        JdbcAuthenticationRealm authenticationRealm = jdbcAuthenticationRealm(jdbcTemplate);
        return authenticationRealm;
    }
    
    //JdbcAuthenticationRealm
    public JdbcAuthenticationRealm jdbcAuthenticationRealm(
                JdbcTemplate jdbcTemplate) {
        JdbcAuthenticationRealm authenticationRealm = new JdbcAuthenticationRealm(jdbcTemplate);
        _logger.debug("JdbcAuthenticationRealm inited.");
        return authenticationRealm;
    }
    
    //LdapAuthenticationRealm
    public LdapAuthenticationRealm ldapAuthenticationRealm(
                JdbcTemplate jdbcTemplate) {
        LdapAuthenticationRealm authenticationRealm = new LdapAuthenticationRealm(jdbcTemplate);
        LdapServer ldapServer=new LdapServer();
        String providerUrl = "ldap://localhost:389";
        String principal = "cn=root";
        String credentials = "maxkey";
        String baseDN = "dc=maxkey,dc=top";
        LdapUtils ldapUtils = new LdapUtils(providerUrl,principal,credentials,baseDN);
        ldapServer.setLdapUtils(ldapUtils);
        ldapServer.setFilterAttribute("uid");
        List<IAuthenticationServer> ldapServers = new ArrayList<IAuthenticationServer>();
        ldapServers.add(ldapServer);
        authenticationRealm.setLdapServers(ldapServers);
        _logger.debug("LdapAuthenticationRealm inited.");
        return authenticationRealm;
    }
    
    //ActiveDirectoryAuthenticationRealm
    public ActiveDirectoryAuthenticationRealm activeDirectoryAuthenticationRealm(
                JdbcTemplate jdbcTemplate) {
        ActiveDirectoryAuthenticationRealm authenticationRealm = new ActiveDirectoryAuthenticationRealm(jdbcTemplate);
        ActiveDirectoryServer ldapServer=new ActiveDirectoryServer();
        String providerUrl = "ldap://localhost:389";
        String principal = "cn=root";
        String credentials = "maxkey";
        String domain = "maxkey";
        ActiveDirectoryUtils ldapUtils = new ActiveDirectoryUtils(providerUrl,principal,credentials,domain);
        ldapServer.setActiveDirectoryUtils(ldapUtils);
        
        List<IAuthenticationServer> ldapServers = new ArrayList<IAuthenticationServer>();
        ldapServers.add(ldapServer);
        authenticationRealm.setActiveDirectoryServers(ldapServers);
        _logger.debug("LdapAuthenticationRealm inited.");
        return authenticationRealm;
    }
    
    //default tfaOptAuthn
    @Bean(name = "tfaOptAuthn")
    public AbstractOptAuthn tfaOptAuthn(
            @Value("${config.login.mfa.type}")String mfaType,
            MailOtpAuthn tfaMailOptAuthn) {    
        
        if(mfaType.equalsIgnoreCase("SmsOtpAuthnAliyun")) {
            SmsOtpAuthnAliyun tfaOptAuthn = new SmsOtpAuthnAliyun();
            tfaOptAuthn.initPropertys();
            _logger.debug("SmsOtpAuthnAliyun inited.");
            return tfaOptAuthn;
        }else if(mfaType.equalsIgnoreCase("SmsOtpAuthnTencentCloud")) {
            SmsOtpAuthnTencentCloud tfaOptAuthn = new SmsOtpAuthnTencentCloud();
            tfaOptAuthn.initPropertys();
            _logger.debug("SmsOtpAuthnTencentCloud inited.");
            return tfaOptAuthn;
        }else if(mfaType.equalsIgnoreCase("SmsOtpAuthnYunxin")) {
            SmsOtpAuthnYunxin tfaOptAuthn = new SmsOtpAuthnYunxin();
            tfaOptAuthn.initPropertys();
            _logger.debug("SmsOtpAuthnYunxin inited.");
            return tfaOptAuthn;
        }else if(mfaType.equalsIgnoreCase("MailOtpAuthn")) {
            _logger.debug("MailOtpAuthn inited.");
            return tfaMailOptAuthn;
        }else {
            TimeBasedOtpAuthn tfaOptAuthn = new TimeBasedOtpAuthn();
            _logger.debug("TimeBasedOtpAuthn inited.");
            return tfaOptAuthn;
        }
    }
    
    @Bean(name = "tfaMailOptAuthn")
    public MailOtpAuthn mailOtpAuthn(
            @Value("${spring.mail.properties.mailotp.message.subject}")
            String messageSubject,
            @Value("${spring.mail.properties.mailotp.message.template}")
            String messageTemplate
            ) {
        MailOtpAuthn mailOtpAuthn = new MailOtpAuthn();
        mailOtpAuthn.setSubject(messageSubject);
        mailOtpAuthn.setMessageTemplate(messageTemplate);
        _logger.debug("tfaMailOptAuthn inited.");
        return mailOtpAuthn;
    }
    
    @Bean(name = "tfaMobileOptAuthn")
    public SmsOtpAuthn smsOtpAuthn(@Value("${config.otp.sms}")String optSmsProvider) {
        SmsOtpAuthn smsOtpAuthn = null;
        
        if(optSmsProvider.equalsIgnoreCase("SmsOtpAuthnAliyun")) {
            smsOtpAuthn = new SmsOtpAuthnAliyun();
        }else if(optSmsProvider.equalsIgnoreCase("SmsOtpAuthnTencentCloud")) {
            smsOtpAuthn = new SmsOtpAuthnTencentCloud();
        }else {
            smsOtpAuthn = new SmsOtpAuthnYunxin();
        }
        smsOtpAuthn.initPropertys();
        
        _logger.debug("SmsOtpAuthn inited.");
        return smsOtpAuthn;
    }
    
    
    @Bean(name = "kerberosService")
    public RemoteKerberosService kerberosService(
            @Value("${config.support.kerberos.default.userdomain}")
            String userDomain,
            @Value("${config.support.kerberos.default.fulluserdomain}")
            String fullUserDomain,
            @Value("${config.support.kerberos.default.crypto}")
            String crypto,
            @Value("${config.support.kerberos.default.redirecturi}")
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
        // TODO Auto-generated method stub
        
    }


    
}
