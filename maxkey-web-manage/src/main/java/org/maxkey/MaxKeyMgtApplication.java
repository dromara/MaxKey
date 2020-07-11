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

import java.util.Date;
import javax.servlet.ServletException;

import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.web.InitializeContext;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "org.maxkey.MaxKeyMgtConfig",
    "org.maxkey.MaxKeyMgtMvcConfig",
    "org.maxkey.web.interceptor",
    "org.maxkey.configuration",
    "org.maxkey.domain",
    "org.maxkey.domain.apps",
    "org.maxkey.domain.userinfo",
    "org.maxkey.web.endpoint",
    "org.maxkey.web.contorller",
    "org.maxkey.web.apps.contorller",
    "org.maxkey.web.endpoint",
    "org.maxkey.authn",
    "org.maxkey.persistence",
    "org.maxkey.web",
    "org.maxkey.web.tag",
    "org.maxkey.identity.kafka",
    "org.maxkey.identity.scim.controller",
    "org.maxkey.identity.rest"
})
@MapperScan("org.maxkey.persistence.mapper,")
public class MaxKeyMgtApplication extends SpringBootServletInitializer {
	private static final Logger _logger = LoggerFactory.getLogger(MaxKeyMgtApplication.class);

	public static void main(String[] args) {
	    _logger.info("Start MaxKeyMgtApplication ...");

		ConfigurableApplicationContext  applicationContext =SpringApplication.run(MaxKeyMgtApplication.class, args);
		InitializeContext initWebContext=new InitializeContext(applicationContext);
		
		try {
			initWebContext.init(null);
		} catch (ServletException e) {
			e.printStackTrace();
			_logger.error("",e);
		}
		_logger.info("MaxKeyMgt at "+new Date(applicationContext.getStartupDate()));
		_logger.info("MaxKeyMgt Server Port "+applicationContext.getBean(ApplicationConfig.class).getPort());
		_logger.info("MaxKeyMgt started.");
		
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MaxKeyMgtApplication.class);
	}

}
