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

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.persistence.service.InstitutionsService;
import org.dromara.maxkey.web.WebInstRequestFilter;
import org.dromara.maxkey.web.WebXssRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;


@AutoConfiguration
public class MvcAutoConfiguration implements WebMvcConfigurer {
    static final  Logger _logger = LoggerFactory.getLogger(MvcAutoConfiguration.class);
    
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    /**
     * 消息处理，可以直接使用properties的key值，返回的是对应的value值
     * messageSource .
     * @return messageSource
     */
    @Bean(name = "messageSource")
    ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource(
            @Value("${spring.messages.basename:classpath:messages/message}")
            String messagesBasename)  {
        _logger.debug("Basename {}" , messagesBasename);
        String passwordPolicyMessagesBasename="classpath:messages/passwordpolicy_message";
        
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(messagesBasename,passwordPolicyMessagesBasename);
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }

    /**
     * Locale Change Interceptor and Resolver definition .
     * @return localeChangeInterceptor
     */
    //@Primary
    @Bean(name = "localeChangeInterceptor")
    LocaleChangeInterceptor localeChangeInterceptor()  {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    /**
     * handlerMapping .
     * @return handlerMapping
     */
    @Bean(name = "handlerMapping")
    RequestMappingHandlerMapping requestMappingHandlerMapping(
                                    LocaleChangeInterceptor localeChangeInterceptor) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.setInterceptors(localeChangeInterceptor);
        return requestMappingHandlerMapping;
    }

    /**
     * jaxb2Marshaller .
     * @return jaxb2Marshaller
     */
    @Bean(name = "jaxb2Marshaller")
    Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(org.dromara.maxkey.entity.xml.UserInfoXML.class);
        return jaxb2Marshaller;
    }

    /**
     * marshallingHttpMessageConverter .
     * @return marshallingHttpMessageConverter
     */
    @Bean(name = "marshallingHttpMessageConverter")
    MarshallingHttpMessageConverter marshallingHttpMessageConverter(
                                                Jaxb2Marshaller jaxb2Marshaller) {
        MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
        marshallingHttpMessageConverter.setMarshaller(jaxb2Marshaller);
        marshallingHttpMessageConverter.setUnmarshaller(jaxb2Marshaller);
        ArrayList<MediaType> mediaTypesList = new ArrayList<>();
        mediaTypesList.add(MediaType.APPLICATION_XML);
        mediaTypesList.add(MediaType.TEXT_XML);
        mediaTypesList.add(MediaType.TEXT_PLAIN);
        _logger.debug("marshallingHttpMessageConverter MediaTypes {}" , mediaTypesList);
        marshallingHttpMessageConverter.setSupportedMediaTypes(mediaTypesList);
        return marshallingHttpMessageConverter;
    }

    /**
     * mappingJacksonHttpMessageConverter .
     * @return mappingJacksonHttpMessageConverter
     */
    @Bean(name = "mappingJacksonHttpMessageConverter")
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ArrayList<MediaType> mediaTypesList = new ArrayList<>();
        mediaTypesList.add(MediaType.APPLICATION_JSON);
        mediaTypesList.add(MediaType.valueOf(ApiVersion.V2.getProducedMimeType().toString()));
        mediaTypesList.add(MediaType.valueOf(ApiVersion.V3.getProducedMimeType().toString()));
        //mediaTypesList.add(MediaType.TEXT_PLAIN);
        _logger.debug("mappingJacksonHttpMessageConverter MediaTypes {}" , mediaTypesList);
        mappingJacksonHttpMessageConverter.setSupportedMediaTypes(mediaTypesList);
        ObjectMapper objectMapper = mappingJacksonHttpMessageConverter.getObjectMapper();
        // 时间格式化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        _logger.debug("DateFormat {}" , pattern);
        objectMapper.setDateFormat(new SimpleDateFormat(pattern));
        // 设置格式化内容
        mappingJacksonHttpMessageConverter.setObjectMapper(objectMapper);
        return mappingJacksonHttpMessageConverter;
    }

    /**
     * cookieLocaleResolver .
     * @return cookieLocaleResolver
     */

    @Bean(name = "cookieLocaleResolver")
    LocaleResolver cookieLocaleResolver(
            @Value("${maxkey.server.domain:maxkey.top}")
            String domainName
    ) {
        _logger.debug("DomainName {}" , domainName);
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver("mxk_locale");
        cookieLocaleResolver.setCookieDomain(domainName);
        cookieLocaleResolver.setCookieMaxAge(Duration.ofDays(14));
        return cookieLocaleResolver;
    }

    /**
     * AnnotationMethodHandlerAdapter
     * requestMappingHandlerAdapter .
     * @return requestMappingHandlerAdapter
     */
    @Bean(name = "addConverterRequestMappingHandlerAdapter")
    RequestMappingHandlerAdapter requestMappingHandlerAdapter(
            @Qualifier("mappingJacksonHttpMessageConverter")
            MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter,
            MarshallingHttpMessageConverter marshallingHttpMessageConverter,
            StringHttpMessageConverter stringHttpMessageConverter,
            RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        List<HttpMessageConverter<?>> httpMessageConverterList = new ArrayList<>();
        //需要追加byte，否则springdoc-openapi接口会响应Base64编码内容，导致接口文档显示失败
        // https://github.com/springdoc/springdoc-openapi/issues/2143
        // 解决方案
        httpMessageConverterList.add(new ByteArrayHttpMessageConverter());
        httpMessageConverterList.add(stringHttpMessageConverter);
        httpMessageConverterList.add(mappingJacksonHttpMessageConverter);
        httpMessageConverterList.add(marshallingHttpMessageConverter);
        _logger.debug("stringHttpMessageConverter {}",stringHttpMessageConverter.getDefaultCharset());   
        
        requestMappingHandlerAdapter.setMessageConverters(httpMessageConverterList);
        return requestMappingHandlerAdapter;
    }

    /**
     * restTemplate .
     * @return restTemplate
     */
    @Bean(name = "restTemplate")
    RestTemplate restTemplate(
            @Qualifier("mappingJacksonHttpMessageConverter")
            MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter,
            MarshallingHttpMessageConverter marshallingHttpMessageConverter) {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> httpMessageConverterList = new ArrayList<>();
        httpMessageConverterList.add(mappingJacksonHttpMessageConverter);
        httpMessageConverterList.add(marshallingHttpMessageConverter);
        restTemplate.setMessageConverters(httpMessageConverterList);
        return restTemplate;
    }

    /**
     * 配置默认错误页面（仅用于内嵌tomcat启动时） 使用这种方式，在打包为war后不起作用.
     *
     * @return webServerFactoryCustomizer
     */
    @Bean
    WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                _logger.debug("WebServerFactoryCustomizer ... ");
                ErrorPage errorPage400 = 
                        new ErrorPage(HttpStatus.BAD_REQUEST, "/exception/error/400");
                ErrorPage errorPage404 = 
                        new ErrorPage(HttpStatus.NOT_FOUND, "/exception/error/404");
                ErrorPage errorPage500 = 
                        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/exception/error/500");
                factory.addErrorPages(errorPage400, errorPage404, errorPage500);
            }
        };
    }

    @Bean
    SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter() {
        _logger.debug("securityContextHolderAwareRequestFilter init ");
        return new SecurityContextHolderAwareRequestFilter();
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        bean.setOrder(1);
        bean.setFilter(new CorsFilter(source));
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    FilterRegistrationBean<Filter> delegatingFilterProxy() {
        _logger.debug("delegatingFilterProxy init for /* ");
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DelegatingFilterProxy("securityContextHolderAwareRequestFilter"));
        registrationBean.addUrlPatterns("/*");
        //registrationBean.
        registrationBean.setName("delegatingFilterProxy");
        registrationBean.setOrder(2);
        
        return registrationBean;
    }

    @Bean
    FilterRegistrationBean<Filter> webXssRequestFilter() {
        _logger.debug("webXssRequestFilter init for /* ");
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(new WebXssRequestFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("webXssRequestFilter");
        registrationBean.setOrder(3);
        return registrationBean;
    }

    @Bean
    FilterRegistrationBean<Filter> webInstRequestFilter(
    		InstitutionsService institutionsService,
    		ApplicationConfig applicationConfig) {
        _logger.debug("WebInstRequestFilter init for /* ");
        FilterRegistrationBean<Filter> registrationBean = 
        		new FilterRegistrationBean<>(new WebInstRequestFilter(institutionsService,applicationConfig));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("webInstRequestFilter");
        registrationBean.setOrder(4);
        return registrationBean;
    }
        
}
