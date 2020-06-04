package org.maxkey;

import org.maxkey.web.interceptor.HistoryLogsAdapter;
import org.maxkey.web.interceptor.PermissionAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
public class MaxKeyMgtMvcConfig implements WebMvcConfigurer {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtMvcConfig.class);
    @Autowired
    PermissionAdapter permissionAdapter;
    
    @Autowired
    HistoryLogsAdapter historyLogsAdapter;
    
    @Autowired
    LocaleChangeInterceptor localeChangeInterceptor;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        _logger.debug("add addResourceHandler");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
        //excludePathPatterns 表示改路径不用拦截
        registry.addInterceptor(permissionAdapter)
                .addPathPatterns("/main/**")
                .addPathPatterns("/orgs/**")
                .addPathPatterns("/userinfo/**")
                .addPathPatterns("/apps/**")
                .addPathPatterns("/app/accounts/**")
                .addPathPatterns("/groups/**")
                .addPathPatterns("/groupMember/**")
                .addPathPatterns("/groupPrivileges/**")
                .addPathPatterns("/roles/**")
                .addPathPatterns("/rolemembers/**")
                .addPathPatterns("/resources/**")
                .addPathPatterns("/permissions/**")
                .addPathPatterns("/config/**")
                .addPathPatterns("/logs/**")
                ;
        
        _logger.debug("add PermissionAdapter");
        
        registry.addInterceptor(historyLogsAdapter)
                .addPathPatterns("/userinfo/**")
                .addPathPatterns("/enterprises/**")
                .addPathPatterns("/employees/**")
                .addPathPatterns("/authInfo/**")
                .addPathPatterns("/usercenter/**")
                .addPathPatterns("/retrievePassword/**")
                .addPathPatterns("/roles/**")
                .addPathPatterns("/apps/**")
                .addPathPatterns("/approles/**")
                ;
        _logger.debug("add HistoryLogsAdapter");
        
        registry.addInterceptor(localeChangeInterceptor);
        _logger.debug("add LocaleChangeInterceptor");
        
    }

}
