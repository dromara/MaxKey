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
import org.maxkey.authn.support.jwt.HttpJwtEntryPoint;
import org.maxkey.authn.support.jwt.JwtLoginService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.rememberme.HttpRemeberMeEntryPoint;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.web.interceptor.HistoryLogsAdapter;
import org.maxkey.web.interceptor.PermissionAdapter;
import org.maxkey.web.interceptor.RestApiPermissionAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
  	@Qualifier("applicationConfig")
  	ApplicationConfig applicationConfig;
    
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider ;
    
    @Autowired
	@Qualifier("remeberMeService")
	AbstractRemeberMeService remeberMeService;
    
    @Autowired
   	@Qualifier("jwtLoginService")
    JwtLoginService jwtLoginService;
    
    @Autowired
    PermissionAdapter permissionAdapter;
    
    @Autowired
    HistoryLogsAdapter historyLogsAdapter;
    
    @Autowired
    LocaleChangeInterceptor localeChangeInterceptor;
    
    @Autowired
    RestApiPermissionAdapter restApiPermissionAdapter;
    
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
        
        _logger.debug("add HttpJwtEntryPoint");
        registry.addInterceptor(new HttpJwtEntryPoint(
        		authenticationProvider,jwtLoginService,applicationConfig,true))
        	.addPathPatterns("/login");
        
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
                .addPathPatterns("/historys/**")
                .addPathPatterns("/historys/**/**")
                .addPathPatterns("/session/**")
                .addPathPatterns("/socialsprovider/**")
                .addPathPatterns("/accountsstrategy/**")
                .addPathPatterns("/institutions/**")
                .addPathPatterns("/localization/**")
                .addPathPatterns("/ldapcontext/**")
                .addPathPatterns("/emailsenders/**")
                .addPathPatterns("/smsprovider/**")
                .addPathPatterns("/synchronizers/**")
                
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
        
        
        /*
         * api
         * idm
         * scim
         * */
        registry.addInterceptor(restApiPermissionAdapter)
                .addPathPatterns("/api/**")
                .addPathPatterns("/api/idm/**")
                .addPathPatterns("/api/idm/scim/**")
                ;
		
        _logger.debug("add RestApiPermissionAdapter");
        
    }

}
