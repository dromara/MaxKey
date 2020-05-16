package org.maxkey;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan("org.maxkey.dao.persistence,")
public class MaxKeyMgtConfig {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtConfig.class);
    
    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;
    
    @Autowired
    @Qualifier("sqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;
    
	@Value("${server.port:8080}")
    private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(name = "passwordReciprocal")
    public PasswordReciprocal passwordReciprocal() {
        return new PasswordReciprocal();
    }
    
    @Bean(name = "savedRequestSuccessHandler")
    public SavedRequestAwareAuthenticationSuccessHandler SavedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
    

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
    /*
    @Bean(name = "sqlSession")
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/
    
    @Bean(name = "transactionManager")
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
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
