package org.maxkey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm;
import org.maxkey.authn.support.kerberos.KerberosProxy;
import org.maxkey.authn.support.kerberos.RemoteKerberosService;
import org.maxkey.authn.support.socialsignon.service.JdbcSocialsAssociateService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.authz.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.maxkey.crypto.password.opt.algorithm.KeyUriFormat;
import org.maxkey.crypto.password.opt.impl.MailOtpAuthn;
import org.maxkey.crypto.password.opt.impl.SmsOtpAuthn;
import org.maxkey.crypto.password.opt.impl.TimeBasedOtpAuthn;
import org.maxkey.crypto.password.opt.impl.sms.SmsOtpAuthnYunxin;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;




@Configuration
//@ImportResource(locations = { "classpath:spring/maxkey.xml" })
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/config/applicationConfig.properties")
@MapperScan("org.maxkey.dao.persistence,")
@ComponentScan(basePackages = {
        "org.maxkey.config",
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
        "org.maxkey.authz.token.endpoint",
        "org.maxkey.web.authentication.support.socialsignon"
})
public class MaxKeyConfig  implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyConfig.class);
    
    @Value("${server.port:8080}")
    private int port;

    public int getPort() {
        return port;
    }

    @Bean
    public FilterRegistrationBean<TokenEndpointAuthenticationFilter> TokenEndpointAuthenticationFilter() {
        _logger.debug("TokenEndpointAuthenticationFilter init ");
        FilterRegistrationBean<TokenEndpointAuthenticationFilter> registration = new FilterRegistrationBean<TokenEndpointAuthenticationFilter>();
        registration.setFilter(new TokenEndpointAuthenticationFilter());
        registration.addUrlPatterns("/oauth/v20/token/*");
        registration.setName("TokenEndpointAuthenticationFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public Connector connector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(80);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        return connector;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }
   
    
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

    @Bean(name = "authenticationRealm")
    public JdbcAuthenticationRealm JdbcAuthenticationRealm(
                JdbcTemplate jdbcTemplate) {
        JdbcAuthenticationRealm authenticationRealm = new JdbcAuthenticationRealm(jdbcTemplate);
        _logger.debug("JdbcAuthenticationRealm inited.");
        return authenticationRealm;
    }
    
    @Bean(name = "tfaOptAuthn")
    public TimeBasedOtpAuthn tfaOptAuthn() {
        TimeBasedOtpAuthn tfaOptAuthn = new TimeBasedOtpAuthn();
        _logger.debug("TimeBasedOtpAuthn inited.");
        return tfaOptAuthn;
    }
    
    @Bean(name = "tfaMailOptAuthn")
    public MailOtpAuthn mailOtpAuthn() {
        MailOtpAuthn mailOtpAuthn = new MailOtpAuthn();
        _logger.debug("tfaMailOptAuthn inited.");
        return mailOtpAuthn;
    }
    
    @Bean(name = "tfaMobileOptAuthn")
    public SmsOtpAuthn smsOtpAuthn() {
        SmsOtpAuthnYunxin smsOtpAuthn = new SmsOtpAuthnYunxin();
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
    
    @Bean(name = "socialSignOnProviderService")
    @ConditionalOnClass(SocialSignOnProvider.class)
    public SocialSignOnProviderService socialSignOnProviderService() throws IOException {
        SocialSignOnProviderService socialSignOnProviderService = new SocialSignOnProviderService();
        
        Resource resource = new ClassPathResource("/config/applicationConfig.properties");
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        String [] providerList =properties.get("config.login.socialsignon.providers").toString().split(",");
        List<SocialSignOnProvider> socialSignOnProviderList = new ArrayList<SocialSignOnProvider>();
        for(String provider : providerList) {
            String providerName = properties.getProperty("config.socialsignon."+provider+".provider.name");
            String icon=properties.getProperty("config.socialsignon."+provider+".icon");
            String clientId=properties.getProperty("config.socialsignon."+provider+".client.id");
            String clientSecret=properties.getProperty("config.socialsignon."+provider+".client.secret");
            String sortOrder = properties.getProperty("config.socialsignon."+provider+".sortorder");
            SocialSignOnProvider socialSignOnProvider = new SocialSignOnProvider();
            socialSignOnProvider.setProvider(provider);
            socialSignOnProvider.setProviderName(providerName);
            socialSignOnProvider.setIcon(icon);
            socialSignOnProvider.setClientId(clientId);
            socialSignOnProvider.setClientSecret(clientSecret);
            socialSignOnProvider.setSortOrder(Integer.valueOf(sortOrder));
            _logger.debug("socialSignOnProvider " + socialSignOnProvider);
            socialSignOnProviderList.add(socialSignOnProvider);            
        }
        socialSignOnProviderService.setSocialSignOnProviders(socialSignOnProviderList);
        _logger.debug("SocialSignOnProviderService inited.");
        return socialSignOnProviderService;
    }
    
    @Bean(name = "socialsAssociateService")
    public JdbcSocialsAssociateService socialsAssociateService(
                JdbcTemplate jdbcTemplate) {
        JdbcSocialsAssociateService socialsAssociateService = new JdbcSocialsAssociateService(jdbcTemplate);
        _logger.debug("JdbcSocialsAssociateService inited.");
        return socialsAssociateService;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }


    
}
