package org.dromara.maxkey.autoconfigure;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@AutoConfiguration
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
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags()!=null){
                openApi.getTags().forEach(tag -> {
                    Map<String,Object> map=new HashMap<>();
                    map.put("x-order",1);
                    tag.setExtensions(map);
                });
            }
            if(openApi.getPaths()!=null){
                openApi.addExtension("x-test123","333");
                openApi.getPaths().addExtension("x-abb",1);
            }

        };
    }
    
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
						.termsOfService("http://www.maxkey.top/")
						.license(
							new License()
								.name("Apache License, Version 2.0")
								.url("http://www.apache.org/licenses/LICENSE-2.0")
						)
				).
				externalDocs(
						new ExternalDocumentation()
						.description("MaxKey.top contact support@maxsso.net")
						.url("http://www.maxkey.top/")
				);
	}
}
