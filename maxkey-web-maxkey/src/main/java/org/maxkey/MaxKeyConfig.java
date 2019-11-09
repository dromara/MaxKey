package org.maxkey;

import org.maxkey.authz.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

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
}
