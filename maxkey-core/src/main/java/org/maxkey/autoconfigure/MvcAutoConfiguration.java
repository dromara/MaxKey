package org.maxkey.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Configuration
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/config/applicationConfig.properties")
public class MvcAutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(MvcAutoConfiguration.class);
   
    /**
     * cookieLocaleResolver .
     * @return cookieLocaleResolver
     */
    @Bean (name = "localeResolver")
    public CookieLocaleResolver cookieLocaleResolver(
            @Value("${config.server.domain.sub:maxkey.top}")String subDomainName) {
        _logger.debug("subDomainName " + subDomainName);
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("maxkey_lang");
        cookieLocaleResolver.setCookieDomain(subDomainName);
        cookieLocaleResolver.setCookieMaxAge(604800);
        return cookieLocaleResolver;
    }
    
    /**
     * 消息处理，可以直接使用properties的key值，返回的是对应的value值
     * messageSource .
     * @return messageSource
     */
    @Bean (name = "messageSource")
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource(
            @Value("${spring.messages.basename:classpath:messages/message}")
            String messagesBasename)  {
        _logger.debug("Basename " + messagesBasename);
        
        ReloadableResourceBundleMessageSource messageSource = 
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messagesBasename);
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }
    
    /**
     * Locale Change Interceptor and Resolver definition .
     * @return localeChangeInterceptor
     */
    //@Primary
    @Bean (name = "localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor()  {
        LocaleChangeInterceptor localeChangeInterceptor = 
                new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }
    
    /**
     * upload file support .
     * @return multipartResolver
     */
    @Bean (name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver(
            @Value("${spring.servlet.multipart.max-file-size:0}") int maxUploadSize)  {
        _logger.debug("maxUploadSize " + maxUploadSize);
        CommonsMultipartResolver multipartResolver = 
                new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(maxUploadSize);
        return multipartResolver;
    }
    
    /**
     * handlerMapping .
     * @return handlerMapping
     */
    @Bean (name = "handlerMapping")
    public RequestMappingHandlerMapping requestMappingHandlerMapping(
                                    LocaleChangeInterceptor localeChangeInterceptor) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = 
                new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.setInterceptors(localeChangeInterceptor);
        return requestMappingHandlerMapping;
    }
    
    /**
     * jaxb2Marshaller .
     * @return jaxb2Marshaller
     */
    @Bean (name = "jaxb2Marshaller")
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(org.maxkey.domain.xml.UserInfoXML.class);;
        return jaxb2Marshaller;
    }
    
    /**
     * marshallingHttpMessageConverter .
     * @return marshallingHttpMessageConverter
     */
    @Bean (name = "marshallingHttpMessageConverter")
    public MarshallingHttpMessageConverter marshallingHttpMessageConverter(
                                                Jaxb2Marshaller jaxb2Marshaller) {
        MarshallingHttpMessageConverter marshallingHttpMessageConverter = 
                new MarshallingHttpMessageConverter();
        marshallingHttpMessageConverter.setMarshaller(jaxb2Marshaller);
        marshallingHttpMessageConverter.setUnmarshaller(jaxb2Marshaller);
        ArrayList<MediaType> mediaTypesList = new ArrayList<MediaType>();
        mediaTypesList.add(MediaType.APPLICATION_XML);
        marshallingHttpMessageConverter.setSupportedMediaTypes(mediaTypesList);
        return marshallingHttpMessageConverter;
    }
    
    /**
     * mappingJacksonHttpMessageConverter .
     * @return mappingJacksonHttpMessageConverter
     */
    @Bean (name = "mappingJacksonHttpMessageConverter")
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = 
                new MappingJackson2HttpMessageConverter();
        ArrayList<MediaType> mediaTypesList = new ArrayList<MediaType>();
        mediaTypesList.add(MediaType.APPLICATION_JSON);
        mappingJacksonHttpMessageConverter.setSupportedMediaTypes(mediaTypesList);
        return mappingJacksonHttpMessageConverter;
    }
    
    /**
     * AnnotationMethodHandlerAdapter
     * requestMappingHandlerAdapter .
     * @return requestMappingHandlerAdapter
     */
    @Bean (name = "requestMappingHandlerAdapter")
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(
            MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter,
            MarshallingHttpMessageConverter marshallingHttpMessageConverter) {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = 
                new RequestMappingHandlerAdapter();
        List<HttpMessageConverter<?>> httpMessageConverterList = 
                new ArrayList<HttpMessageConverter<?>>();
        httpMessageConverterList.add(mappingJacksonHttpMessageConverter);
        httpMessageConverterList.add(marshallingHttpMessageConverter);
        requestMappingHandlerAdapter.setMessageConverters(httpMessageConverterList);
        return requestMappingHandlerAdapter;
    }
    
    /**
     * restTemplate .
     * @return restTemplate
     */
    @Bean (name = "restTemplate")
    public RestTemplate restTemplate(
            MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter,
            MarshallingHttpMessageConverter marshallingHttpMessageConverter) {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> httpMessageConverterList = 
                new ArrayList<HttpMessageConverter<?>>();
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
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
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
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    
}
