package org.maxkey.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	final static Logger _logger = LoggerFactory.getLogger(SwaggerConfig.class);
	
    @Value("${maxkey.swagger.title}")
    String title;
    
    @Value("${maxkey.swagger.description}")
    String description;
    
    @Value("${maxkey.swagger.version}")
    String version;
    
    @Value("${maxkey.swagger.enable}")
    boolean enable;

    @Bean
    public GroupedOpenApi userApi(){
        String[] paths = { 
        		"/login",
        		"/logout",
        		"/login/**",
        		"/logout/**",
        		"/authz/**",
        		"/authz/**/**",
        		"/metadata/saml20/**" , 
        		"/onlineticket/validate/**",
        		"/api/connect/v10/userinfo",
        		"/api/oauth/v20/me"
        		
        	};
        String[] packagedToMatch = { "org.maxkey.authz" };
        return GroupedOpenApi.builder().group(title)
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }
    
	@Bean
	public OpenAPI docOpenAPI() {
		return new OpenAPI()
				.info(
					new Info()
						.title(title)
						.description(description)
						.version(version)
						.termsOfService("https://www.maxkey.top/")
						.license(
							new License()
								.name("Apache License, Version 2.0")
								.url("http://www.apache.org/licenses/LICENSE-2.0")
						)
				).
				externalDocs(
						new ExternalDocumentation()
						.description("MaxKey.top contact maxkeysupport@163.com")
						.url("https://www.maxkey.top/")
				);
	}
}
