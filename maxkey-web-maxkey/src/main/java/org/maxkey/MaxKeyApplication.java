package org.maxkey;

import java.util.Date;

import javax.servlet.ServletException;

import org.apache.ibatis.io.VFS;
import org.apache.mybatis.jpa.SpringBootVFS;
import org.maxkey.web.InitApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MaxKeyApplication extends SpringBootServletInitializer {
	private static final Logger _logger = LoggerFactory.getLogger(MaxKeyApplication.class);

	public static void main(String[] args) {
		 VFS.addImplClass(SpringBootVFS.class);
		ConfigurableApplicationContext  applicationContext =SpringApplication.run(MaxKeyApplication.class, args);
		InitApplicationContext initWebContext=new InitApplicationContext(applicationContext);
		try {
			initWebContext.init(null);
		} catch (ServletException e) {
			e.printStackTrace();
			_logger.error("",e);
		}
		_logger.info("MaxKey at "+new Date(applicationContext.getStartupDate()));
		_logger.info("MaxKey Server Port "+applicationContext.getBean(MaxKeyConfig.class).getPort());
		_logger.info("MaxKey started.");
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MaxKeyApplication.class);
	}

}
