package org.dromara.maxkey.autoconfigure;

import java.util.List;

import org.dromara.maxkey.authn.web.CurrentUserMethodArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@AutoConfiguration
public class MvcCurrentUserAutoConfiguration  implements WebMvcConfigurer {
    private static final  Logger logger = LoggerFactory.getLogger(MvcCurrentUserAutoConfiguration.class);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
        logger.debug("add currentUserMethodArgumentResolver");
    }
    
    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }
    
}
