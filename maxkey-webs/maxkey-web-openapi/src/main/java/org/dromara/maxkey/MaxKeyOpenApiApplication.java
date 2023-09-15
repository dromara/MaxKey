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
 

package org.dromara.maxkey;


import org.dromara.maxkey.web.InitializeContext;
import org.dromara.maxkey.web.WebContext;
import org.joda.time.DateTime;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import jakarta.servlet.ServletException;

@ComponentScan(basePackages = {
	"org.dromara.maxkey.authn",
	"org.dromara.maxkey.configuration",
	"org.dromara.maxkey.entity",
    "org.dromara.maxkey.entity.apps",
    "org.dromara.maxkey.entity.userinfo",
    "org.dromara.maxkey.web.apis.identity.kafka",
    "org.dromara.maxkey.web.apis.identity.rest",
    "org.dromara.maxkey.web.apis.identity.scim",
    "org.dromara.maxkey.persistence",
    "org.dromara.maxkey.provision",
    "org.dromara.maxkey.web",
    "org.dromara.maxkey.web.api.endpoint",
    "org.dromara.maxkey.web.contorller",
    "org.dromara.maxkey.web.endpoint",
    "org.dromara.maxkey.web.interceptor",
})
@MapperScan("org.dromara.maxkey.persistence.mapper,")
@SpringBootApplication
@EnableDiscoveryClient
public class MaxKeyOpenApiApplication extends SpringBootServletInitializer {
	private static final Logger logger = LoggerFactory.getLogger(MaxKeyOpenApiApplication.class);

	public static void main(String[] args) {
	    logger.info("Start MaxKey OpenApi Application ...");

		ConfigurableApplicationContext  applicationContext = 
							SpringApplication.run(MaxKeyOpenApiApplication.class, args);
		InitializeContext initWebContext = new InitializeContext(applicationContext);
		
		try {
			initWebContext.init(null);
		} catch (ServletException e) {
			logger.error("Exception ",e);
		}
		logger.info("MaxKey OpenApi at {}" , new DateTime());
		logger.info("MaxKey OpenApi Server Port {}" , WebContext.properties.getProperty("server.port"));
		logger.info("MaxKey OpenApi started.");
		
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MaxKeyOpenApiApplication.class);
	}

}
