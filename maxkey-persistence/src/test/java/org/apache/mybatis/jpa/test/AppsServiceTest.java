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
 

package org.apache.mybatis.jpa.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.mybatis.jpa.spring.MybatisJpaContext;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppsServiceTest {
	
	private static final Logger _logger = LoggerFactory.getLogger(AppsServiceTest.class);
	
	public static ApplicationContext context;
	
	public static AppsService service;
	
	public AppsService getservice() {
		service=(AppsService)MybatisJpaContext.getBean("appsService");
		return service;
	}
	

	@Test
	public void get() throws Exception{
		_logger.info("get...");
		Apps a=new Apps();
		a.setPageNumber(2);
		a.setPageSize(10);
		;
		getservice().fetchPageResults(a);
		// _logger.info("apps "+);

	}
	
	
	
	@Before
	public void initSpringContext(){
		if(context!=null) {
			return;
		}
		_logger.info("init Spring Context...");
		SimpleDateFormat sdf_ymdhms =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=sdf_ymdhms.format(new Date());

		try{
			AppsServiceTest runner=new AppsServiceTest();
			runner.init();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		_logger.info("-- --Init Start at " + startTime+" , End at  "+sdf_ymdhms.format(new Date()));
	}
	
	//Initialization ApplicationContext for Project
	public void init(){
		_logger.info("init ...");
		
		_logger.info("Application dir "+System.getProperty("user.dir"));
		context = new ClassPathXmlApplicationContext(new String[] {"spring/applicationContext.xml"});
		MybatisJpaContext.init(context);
		getservice();
		System.out.println("init ...");
		
	}
	
}
