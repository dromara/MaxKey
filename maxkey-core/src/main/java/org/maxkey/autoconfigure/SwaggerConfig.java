package org.maxkey.autoconfigure;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableOpenApi
@EnableKnife4j
public class SwaggerConfig {

    @Value("${maxkey.swagger.title}")
    String title;
    
    @Value("${maxkey.swagger.description}")
    String description;
    
    @Value("${maxkey.swagger.version}")
    String version;
    
    @Value("${maxkey.swagger.enable}")
    boolean enable;

    @Bean
    public Docket docket(){
        if(enable) {
            return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.maxkey")
                        .and(RequestHandlerSelectors.withClassAnnotation(Api.class))
                        .and(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)))
                .paths(PathSelectors.any())
                .build();
        }else {
            return null;
        }

    }
    
    //    配置swagger信息
    @SuppressWarnings(value = { })
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                                "MaxKey.top", 
                                "https://www.maxkey.top/", 
                                "maxkeysupport@163.com");
        
        return new ApiInfo(
                title,
                description,
                version,
                "https://www.maxkey.top/",
                contact,
                "Apache License, Version 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
