/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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

import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.web.interceptor.PermissionInterceptor;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.web.interceptor.RestApiPermissionAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@AutoConfiguration
public class MaxKeyOpenApiMvcConfig implements WebMvcConfigurer {
    private static final  Logger logger = LoggerFactory.getLogger(MaxKeyOpenApiMvcConfig.class);
    
    @Autowired
  	ApplicationConfig applicationConfig;
    
    @Autowired
    AbstractAuthenticationProvider authenticationProvider ;
    
    @Autowired
    PermissionInterceptor permissionInterceptor;
    
    @Autowired
    RestApiPermissionAdapter restApiPermissionAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
        //excludePathPatterns 表示改路径不用拦截
        logger.debug("add Interceptors");

        permissionInterceptor.setMgmt(true);
        
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/dashboard/**")
                .addPathPatterns("/orgs/**")
                .addPathPatterns("/users/**")
                .addPathPatterns("/apps/**")
                .addPathPatterns("/session/**")
                .addPathPatterns("/accounts/**")
                
                
                .addPathPatterns("/access/**")
                .addPathPatterns("/access/**/**")
                
                .addPathPatterns("/permissions/**")
                .addPathPatterns("/permissions/**/**")
                
                .addPathPatterns("/config/**")
                .addPathPatterns("/config/**/**")
                
                .addPathPatterns("/historys/**")
                .addPathPatterns("/historys/**/**")
                
                .addPathPatterns("/institutions/**")
                .addPathPatterns("/localization/**")
                
                .addPathPatterns("/file/upload/")
                
                .addPathPatterns("/logout")
                .addPathPatterns("/logout/**")
                ;
        
        logger.debug("add Permission Adapter");

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
		
        logger.debug("add Rest Api Permission Adapter");
        
    }

}
