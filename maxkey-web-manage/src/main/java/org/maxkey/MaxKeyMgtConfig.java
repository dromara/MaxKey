package org.maxkey;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan("org.maxkey.dao.persistence,")
public class MaxKeyMgtConfig {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtConfig.class);
    
	@Value("${server.port:8080}")
    private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

    /**
      * 配置默认错误页面（仅用于内嵌tomcat启动时）
      * 使用这种方式，在打包为war后不起作用
      *
      * @return
      */  
     @Bean
     public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
         return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
         @Override
         public void customize(ConfigurableWebServerFactory factory) {
             _logger.debug("WebServerFactoryCustomizer ... ");
             ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST,"/exception/error/400");
             ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/exception/error/404");
             ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/exception/error/500");
             factory.addErrorPages(errorPage400, errorPage404,errorPage500);

         }
     };
 }

}
