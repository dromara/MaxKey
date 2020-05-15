package org.maxkey;

import java.io.IOException;
import java.util.Properties;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.maxkey.authz.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
@ImportResource(locations = { "classpath:spring/maxkey.xml" })
@PropertySource("classpath:/application.properties")
public class MaxKeyConfig {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyConfig.class);
    @Value("${server.port:8080}")
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Bean
    public FilterRegistrationBean<TokenEndpointAuthenticationFilter> TokenEndpointAuthenticationFilter() {
        FilterRegistrationBean<TokenEndpointAuthenticationFilter> registration = new FilterRegistrationBean<TokenEndpointAuthenticationFilter>();
        registration.setFilter(new TokenEndpointAuthenticationFilter());
        registration.addUrlPatterns("/oauth/v20/token/*");
        registration.setName("TokenEndpointAuthenticationFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 配置默认错误页面（仅用于内嵌tomcat启动时） 使用这种方式，在打包为war后不起作用
     *
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/exception/error/400");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/exception/error/404");
                ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/exception/error/500");
                factory.addErrorPages(errorPage400, errorPage404, errorPage500);
            }
        };
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
    
    @Bean(name = "passwordReciprocal")
    public PasswordReciprocal passwordReciprocal() {
        return new PasswordReciprocal();
    }
    
    @Bean(name = "savedRequestSuccessHandler")
    public SavedRequestAwareAuthenticationSuccessHandler SavedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
    
    /**
     * Captcha Producer  Config .
     * @return Producer
     * @throws IOException
     */
    @Bean(name = "captchaProducer")
    public Producer captchaProducer() throws IOException{
        Resource resource = new ClassPathResource("config/kaptcha.properties");
        _logger.debug("Kaptcha config file " + resource.getURL());
        DefaultKaptcha  kaptcha=new DefaultKaptcha();
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

}
