/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@AutoConfiguration
public class MvcResourceAutoConfiguration implements WebMvcConfigurer {
    private static final  Logger logger = LoggerFactory.getLogger(MvcResourceAutoConfiguration.class);

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
