package org.maxkey;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.maxkey.authz.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.maxkey.crypto.password.opt.algorithm.KeyUriFormat;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;




@Configuration
@ImportResource(locations = { "classpath:spring/maxkey.xml" })
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
        //single sign on protocol
        "org.maxkey.authz.endpoint",
        "org.maxkey.authz.desktop.endpoint",
        "org.maxkey.authz.exapi.endpoint",
        "org.maxkey.authz.formbased.endpoint",
        "org.maxkey.authz.ltpa.endpoint",
        "org.maxkey.authz.token.endpoint",
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

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }


    
}
