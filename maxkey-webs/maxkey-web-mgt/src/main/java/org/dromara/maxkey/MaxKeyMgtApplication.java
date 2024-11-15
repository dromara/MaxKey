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
 

package org.dromara.maxkey;

import org.dromara.maxkey.web.InitializeContext;
import org.dromara.maxkey.web.ProductEnvironment;
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

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.dromara.maxkey.persistence.mapper,")
public class MaxKeyMgtApplication extends SpringBootServletInitializer {
	static final Logger _logger = LoggerFactory.getLogger(MaxKeyMgtApplication.class);

	public static void main(String[] args) {
		_logger.info("Start MaxKeyMgt Application ...");
	    ProductEnvironment.listEnvVars();
	    
		ConfigurableApplicationContext  applicationContext = SpringApplication.run(MaxKeyMgtApplication.class, args);
		new InitializeContext(applicationContext).init();
		
		_logger.info("MaxKeyMgt at {}" , new DateTime());
		_logger.info("MaxKeyMgt Server Port {}" , WebContext.getServerPort());
		_logger.info("MaxKeyMgt started.");
		
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MaxKeyMgtApplication.class);
	}

}
