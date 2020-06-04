package org.maxkey;

import org.maxkey.authn.support.basic.BasicEntryPoint;
import org.maxkey.authn.support.httpheader.HttpHeaderEntryPoint;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.web.interceptor.HistoryLoginAppAdapter;
import org.maxkey.web.interceptor.HistoryLogsAdapter;
import org.maxkey.web.interceptor.PermissionAdapter;
import org.maxkey.web.interceptor.PreLoginAppAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
@PropertySource(ConstantsProperties.maxKeyPropertySource)
public class MaxKeyMvcConfig implements WebMvcConfigurer {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMvcConfig.class);
    @Autowired
    PermissionAdapter permissionAdapter;
    
    @Autowired
    HistoryLogsAdapter historyLogsAdapter;
    
    @Autowired
    LocaleChangeInterceptor localeChangeInterceptor;
    
    @Autowired
    PreLoginAppAdapter preLoginAppAdapter;
    
    @Autowired
    HistoryLoginAppAdapter historyLoginAppAdapter;
    
    @Value("${config.support.httpheader.enable:false}")
    private boolean httpHeaderEnable;
    
    @Value("${config.support.httpheader.headername:iv-user}")
    private String httpHeaderName;
    
    @Value("${config.support.basic.enable:false}")
    private boolean basicEnable;
    
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
                .addPathPatterns("/index/**")
                .addPathPatterns("/logs/**")
                .addPathPatterns("/userinfo/**")
                .addPathPatterns("/profile/**")
                .addPathPatterns("/safe/**")
                .addPathPatterns("/historys/**")
                .addPathPatterns("/appList/**")
                .addPathPatterns("/socialsignon/**")
                
                .addPathPatterns("/authz/basic/*")
                .addPathPatterns("/authz/ltpa/*")
                .addPathPatterns("/authz/desktop/*")
                .addPathPatterns("/authz/formbased/*")
                .addPathPatterns("/authz/tokenbased/*")
                //SAML
                .addPathPatterns("/authz/saml20/idpinit/*")
                .addPathPatterns("/authz/saml20/assertion")
                .addPathPatterns("/authz/saml20/assertion/")
                //CAS
                .addPathPatterns("/authz/cas/*")
                .addPathPatterns("/authz/cas/*/*")
                .addPathPatterns("/authz/cas/login")
                .addPathPatterns("/authz/cas/login/")
                .addPathPatterns("/authz/cas/granting/*")
                //OAuth
                .addPathPatterns("/oauth/v20/authorize")
                .addPathPatterns("/oauth/v20/authorize/*")
                ;
        
        _logger.debug("add PermissionAdapter");
        
        registry.addInterceptor(historyLogsAdapter)
                .addPathPatterns("/safe/changePassword/**")
                ;
        _logger.debug("add HistoryLogsAdapter");

        registry.addInterceptor(preLoginAppAdapter)
                .addPathPatterns("/authz/basic/*")
                .addPathPatterns("/authz/ltpa/*")
                .addPathPatterns("/authz/desktop/*")
                .addPathPatterns("/authz/formbased/*")
                .addPathPatterns("/authz/tokenbased/*")
                .addPathPatterns("/authz/saml20/idpinit/*")
                .addPathPatterns("/authz/saml20/assertion")
                .addPathPatterns("/authz/cas/login")
                .addPathPatterns("/authz/cas/granting")
        ;
        _logger.debug("add PreLoginAppAdapter");
        
        registry.addInterceptor(historyLoginAppAdapter)
                .addPathPatterns("/authz/basic/*")
                .addPathPatterns("/authz/ltpa/*")
                .addPathPatterns("/authz/desktop/*")
                .addPathPatterns("/authz/formbased/*")
                .addPathPatterns("/authz/tokenbased/*")
                .addPathPatterns("/authz/saml20/idpinit/*")
                .addPathPatterns("/authz/saml20/assertion")
                .addPathPatterns("/authz/cas/granting")
        ;
        _logger.debug("add HistoryLoginAppAdapter");
        
       
        registry.addInterceptor(localeChangeInterceptor);
        _logger.debug("add LocaleChangeInterceptor");
        
        if(httpHeaderEnable) {
            registry.addInterceptor(new HttpHeaderEntryPoint(httpHeaderName,httpHeaderEnable))
                    .addPathPatterns("/*");
            _logger.debug("add HttpHeaderEntryPoint");
        }
        
        if(basicEnable) {
            registry.addInterceptor(new BasicEntryPoint(basicEnable))
                    .addPathPatterns("/*");
            _logger.debug("add BasicEntryPoint");
        }
    }

}
