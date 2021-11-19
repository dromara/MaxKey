/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.support.basic.BasicEntryPoint;
import org.maxkey.authn.support.httpheader.HttpHeaderEntryPoint;
import org.maxkey.authn.support.kerberos.HttpKerberosEntryPoint;
import org.maxkey.authn.support.kerberos.KerberosService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.rememberme.HttpRemeberMeEntryPoint;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.web.interceptor.HistoryLoginAppAdapter;
import org.maxkey.web.interceptor.HistoryLogsAdapter;
import org.maxkey.web.interceptor.PermissionAdapter;
import org.maxkey.web.interceptor.PreLoginAppAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
public class MaxKeyMvcConfig implements WebMvcConfigurer {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMvcConfig.class);
    
    @Autowired
  	@Qualifier("applicationConfig")
  	ApplicationConfig applicationConfig;
    
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider ;
    
    @Autowired
	@Qualifier("remeberMeService")
	AbstractRemeberMeService remeberMeService;
    
    @Autowired
	@Qualifier("kerberosService")
    KerberosService kerberosService;
    
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
    
    @Value("${maxkey.login.httpheader.enable:false}")
    private boolean httpHeaderEnable;
    
    @Value("${maxkey.login.httpheader.headername:iv-user}")
    private String httpHeaderName;
    
    @Value("${maxkey.login.basic.enable:false}")
    private boolean basicEnable;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	_logger.debug("addResourceHandlers");
        _logger.debug("add statics");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        _logger.debug("add templates");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        
        _logger.debug("add swagger");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        _logger.debug("add knife4j");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        _logger.debug("addResourceHandler finished .");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
        //excludePathPatterns 表示改路径不用拦截
        _logger.debug("add HttpRemeberMeEntryPoint");
        registry.addInterceptor(new HttpRemeberMeEntryPoint(
        			authenticationProvider,remeberMeService,applicationConfig,true))
        		.addPathPatterns("/login");
        
        _logger.debug("add HttpKerberosEntryPoint");
        registry.addInterceptor(new HttpKerberosEntryPoint(
    			authenticationProvider,kerberosService,applicationConfig,true))
    		.addPathPatterns("/login");
        
        
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
        
        registry.addInterceptor(permissionAdapter)
                .addPathPatterns("/index/**")
                .addPathPatterns("/logs/**")
                .addPathPatterns("/userinfo/**")
                .addPathPatterns("/profile/**")
                .addPathPatterns("/safe/**")
                .addPathPatterns("/historys/**")
                .addPathPatterns("/session/**")
                .addPathPatterns("/session/**/**")
                .addPathPatterns("/appList")
                .addPathPatterns("/appList/**")
                .addPathPatterns("/socialsignon/**")
                
                .addPathPatterns("/authz/basic/*")
                .addPathPatterns("/authz/ltpa/*")
                //Form based
                .addPathPatterns("/authz/formbased/*")
                //Token based
                .addPathPatterns("/authz/tokenbased/*")
                //JWT
                .addPathPatterns("/authz/jwt/*")
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
                //cas1.0 validate
                .excludePathPatterns("/authz/cas/validate")
                //cas2.0 Validate
                .excludePathPatterns("/authz/cas/serviceValidate")
                .excludePathPatterns("/authz/cas/proxyValidate")
                .excludePathPatterns("/authz/cas/proxy")
                //cas3.0 Validate
                .excludePathPatterns("/authz/cas/p3/serviceValidate")
                .excludePathPatterns("/authz/cas/p3/proxyValidate")
                .excludePathPatterns("/authz/cas/p3/proxy")
                //rest
                .excludePathPatterns("/authz/cas/v1/tickets")
                .excludePathPatterns("/authz/cas/v1/tickets/*")
                
                //OAuth
                .addPathPatterns("/authz/oauth/v20/authorize")
                .addPathPatterns("/authz/oauth/v20/authorize/*")
                
                //OAuth TENCENT_IOA
                .addPathPatterns("/oauth2/authorize")
                .addPathPatterns("/oauth2/authorize/*")
                
                //online ticket Validate
                .excludePathPatterns("/onlineticket/ticketValidate")
                .excludePathPatterns("/onlineticket/ticketValidate/*")
                ;
        
        _logger.debug("add PermissionAdapter");
        
        registry.addInterceptor(historyLogsAdapter)
                .addPathPatterns("/safe/changePassword/**")
                ;
        _logger.debug("add HistoryLogsAdapter");

        registry.addInterceptor(preLoginAppAdapter)
                .addPathPatterns("/authz/basic/*")
                .addPathPatterns("/authz/ltpa/*")
                //Form based
                .addPathPatterns("/authz/formbased/*")
                //Token based
                .addPathPatterns("/authz/tokenbased/*")
                //JWT
                .addPathPatterns("/authz/jwt/*")
                //SAML
                .addPathPatterns("/authz/saml20/idpinit/*")
                .addPathPatterns("/authz/saml20/assertion")
                //CAS
                .addPathPatterns("/authz/cas/login")
                .addPathPatterns("/authz/cas/granting")
        ;
        _logger.debug("add PreLoginAppAdapter");
        
        registry.addInterceptor(historyLoginAppAdapter)
                .addPathPatterns("/authz/basic/*")
                .addPathPatterns("/authz/ltpa/*")
                //Extend api
                .addPathPatterns("/authz/api/*")
                //Form based
                .addPathPatterns("/authz/formbased/*")
                //Token based
                .addPathPatterns("/authz/tokenbased/*")
                //JWT
                .addPathPatterns("/authz/jwt/*")
                //SAML
                .addPathPatterns("/authz/saml20/idpinit/*")
                .addPathPatterns("/authz/saml20/assertion")
                //CAS
                .addPathPatterns("/authz/cas/granting")
                //OAuth
                .addPathPatterns("/authz/oauth/v20/approval_confirm")
        ;
        _logger.debug("add HistoryLoginAppAdapter");
        
       
        registry.addInterceptor(localeChangeInterceptor);
        _logger.debug("add LocaleChangeInterceptor");
        

    }
    
}
