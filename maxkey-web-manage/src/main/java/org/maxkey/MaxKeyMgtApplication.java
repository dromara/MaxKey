package org.maxkey;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;

import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.web.InitializeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@SpringBootApplication
@ImportResource(locations={"classpath:spring/maxkey-mgt.xml"})
@ComponentScan(basePackages = {
		"org.maxkey.MaxKeyMgtConfig"
	}
)
public class MaxKeyMgtApplication extends SpringBootServletInitializer {
	private static final Logger _logger = LoggerFactory.getLogger(MaxKeyMgtApplication.class);

	public static void main(String[] args) {
		System.out.println("MaxKeyMgtApplication");

		ConfigurableApplicationContext  applicationContext =SpringApplication.run(MaxKeyMgtApplication.class, args);
		InitializeContext initWebContext=new InitializeContext(applicationContext);
		
		
		try {
			initWebContext.init(null);
		} catch (ServletException e) {
			e.printStackTrace();
			_logger.error("",e);
		}
		_logger.info("MaxKeyMgt at "+new Date(applicationContext.getStartupDate()));
		_logger.info("MaxKeyMgt Server Port "+applicationContext.getBean(MaxKeyMgtConfig.class).getPort());
		_logger.info("MaxKeyMgt started.");
		
		
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(MaxKeyMgtApplication.class);
	}
	
	@Bean
    MaxKeyMgtConfig MaxKeyMgtConfig() {
        return new MaxKeyMgtConfig();
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
                 ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST,"/exception/error/400");
                 ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/exception/error/404");
                 ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/exception/error/500");
                 factory.addErrorPages(errorPage400, errorPage404,errorPage500);

            }
        };
    }
    
    @Bean(name = "passwordReciprocal")
    public PasswordReciprocal passwordReciprocal() {
        return new PasswordReciprocal();
    }
    
    @Bean(name = "savedRequestSuccessHandler")
    public SavedRequestAwareAuthenticationSuccessHandler SavedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
    
    /**
     * Captcha Producer  Config .
     * @return Producer
     * @throws IOException
     */
    @Bean(name = "captchaProducer")
    public Producer captchaProducer() throws IOException{
        Resource resource = new ClassPathResource("config/kaptcha.properties");
        _logger.debug("Kaptcha config file " + resource.getURL());
        DefaultKaptcha  kaptcha=new DefaultKaptcha();
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

}
