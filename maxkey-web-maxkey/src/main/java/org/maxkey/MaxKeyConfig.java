package org.maxkey;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.maxkey.authz.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
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
import org.springframework.http.HttpStatus;

@Configuration
@ImportResource(locations={"classpath:spring/maxkey.xml"})
@PropertySource("classpath:/application.properties")
public class MaxKeyConfig {
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
     * 配置默认错误页面（仅用于内嵌tomcat启动时）
     * 使用这种方式，在打包为war后不起作用
     *
     * @return
     */  
	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
            	 ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST,"/exception/error/400");
                 ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/exception/error/404");
                 ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/exception/error/500");
                 factory.addErrorPages(errorPage400, errorPage404,errorPage500);

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

}
