package org.dromara.maxkey.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@AutoConfiguration
public class ResourceMvcConfig implements WebMvcConfigurer {
    private static final  Logger logger = LoggerFactory.getLogger(ResourceMvcConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	logger.debug("add Resource Handlers");
    	
        logger.debug("add statics");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        logger.debug("add templates");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        
        logger.debug("add swagger");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        logger.debug("add knife4j");
        registry.addResourceHandler("doc.html")
        		.addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/webjars/**")
        		.addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        logger.debug("add Resource Handler finished .");
    }
}
