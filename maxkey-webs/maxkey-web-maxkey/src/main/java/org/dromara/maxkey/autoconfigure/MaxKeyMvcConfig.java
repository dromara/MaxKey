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
 

package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.support.basic.BasicEntryPoint;
import org.dromara.maxkey.authn.support.httpheader.HttpHeaderEntryPoint;
import org.dromara.maxkey.authn.support.kerberos.HttpKerberosEntryPoint;
import org.dromara.maxkey.authn.support.kerberos.KerberosService;
import org.dromara.maxkey.authn.web.interceptor.PermissionInterceptor;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.web.interceptor.HistorySingleSignOnInterceptor;
import org.dromara.maxkey.web.interceptor.SingleSignOnInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@AutoConfiguration
public class MaxKeyMvcConfig implements WebMvcConfigurer {
    private static final  Logger logger = LoggerFactory.getLogger(MaxKeyMvcConfig.class);
   
    @Value("${maxkey.login.basic.enable:false}")
    private boolean basicEnable;
    
    @Value("${maxkey.login.httpheader.enable:false}")
    private boolean httpHeaderEnable;
    
    @Value("${maxkey.login.httpheader.headername:iv-user}")
    private String httpHeaderName;
    
    @Autowired
  	ApplicationConfig applicationConfig;
    
    @Autowired
    AbstractAuthenticationProvider authenticationProvider ;
    
    @Autowired
    KerberosService kerberosService;
    
    @Autowired
    PermissionInterceptor permissionInterceptor;    
    
    @Autowired
    SingleSignOnInterceptor singleSignOnInterceptor;
    
    @Autowired
    HistorySingleSignOnInterceptor historySingleSignOnInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
        //excludePathPatterns 表示改路径不用拦截
        
        logger.debug("add Http Kerberos Entry Point");
        registry.addInterceptor(new HttpKerberosEntryPoint(
    			authenticationProvider,kerberosService,applicationConfig,true))
    		.addPathPatterns("/login");
        
        
        if(httpHeaderEnable) {
            registry.addInterceptor(new HttpHeaderEntryPoint(httpHeaderName,httpHeaderEnable))
                    .addPathPatterns("/*");
            logger.debug("add Http Header Entry Point");
        }
        
        if(basicEnable) {
            registry.addInterceptor(new BasicEntryPoint(basicEnable))
                    .addPathPatterns("/*");
            logger.debug("add Basic Entry Point");
        }
        
        //for frontend
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/config/**")
                .addPathPatterns("/users/**")
                .addPathPatterns("/historys/**")
                .addPathPatterns("/access/session/**")
                .addPathPatterns("/access/session/**/**")
                .addPathPatterns("/appList")
                .addPathPatterns("/appList/**")
                .addPathPatterns("/socialsignon/**")
                .addPathPatterns("/authz/credential/**")
                .addPathPatterns("/authz/oauth/v20/approval_confirm/**")
        		.addPathPatterns("/authz/oauth/v20/authorize/approval/**")
        		.addPathPatterns("/logon/oauth20/bind/**")
        		.addPathPatterns("/logout")
                .addPathPatterns("/logout/**")
                .addPathPatterns("/authz/refused")
                .excludePathPatterns("/logon/oauth20/**/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v3/api-docs/**")
                ;
        
        logger.debug("add Permission Interceptor");

        //for Single Sign On
        registry.addInterceptor(singleSignOnInterceptor)
                .addPathPatterns("/authz/basic/*")
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
                .excludePathPatterns("/authz/cas/v1/users")
                
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
        logger.debug("add Single SignOn Interceptor");
        
        registry.addInterceptor(historySingleSignOnInterceptor)
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
        logger.debug("add history SignOn App Interceptor");
        

    }
    
}
