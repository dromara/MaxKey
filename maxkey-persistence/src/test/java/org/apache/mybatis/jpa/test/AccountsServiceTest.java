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
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.persistence.service.AccountsService;
import org.dromara.mybatis.jpa.spring.MybatisJpaContext;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountsServiceTest {
	
	private static final Logger _logger = LoggerFactory.getLogger(AccountsServiceTest.class);
	
	public static ApplicationContext context;
	
	public static AccountsService service;
	
	public AccountsService getservice() {
		service=(AccountsService)MybatisJpaContext.getBean("accountsService");
		return service;
	}
	

	@Test
	public void get() throws Exception{
		_logger.info("get...");
		Accounts accounts=service.get("26b1c864-ae81-4b1f-9355-74c4c699cb6b");
		
		 _logger.info("accounts "+accounts);

	}
	
	@Test
	public void load() throws Exception{
		_logger.info("get...");
		Accounts queryAccounts=new Accounts("7BF5315CA1004CDB8E614B0361C4D46B","fe86db85-5475-4494-b5aa-dbd3b886ff64");
		Accounts accounts=service.query(queryAccounts).get(0);
		
		 _logger.info("accounts "+accounts);

	}
	
	
	@Test
	public void findAll() throws Exception{
		_logger.info("findAll...");
		_logger.info("findAll "+service.findAll());
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
			AccountsServiceTest runner=new AccountsServiceTest();
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
