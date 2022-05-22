/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import org.maxkey.authn.session.SessionManager;
import org.maxkey.listener.AccountsStrategyListenerAdapter;
import org.maxkey.listener.DynamicGroupsListenerAdapter;
import org.maxkey.listener.ListenerAdapter;
import org.maxkey.listener.ListenerParameter;
import org.maxkey.listener.SessionListenerAdapter;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.GroupsService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaxKeyMgtListenerConfig  implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtListenerConfig.class);
 
    @Bean
    public String  sessionListenerAdapter(
    		Scheduler scheduler,
    		SessionManager sessionManager) throws SchedulerException {
        ListenerAdapter.addListener(
    			SessionListenerAdapter.class,
    			scheduler,
    			new ListenerParameter().add("sessionManager",sessionManager).build(),
    			"0 0/1 * * * ?",//10 minutes
    			SessionListenerAdapter.class.getSimpleName()
    		);
        _logger.debug("Session ListenerAdapter inited .");
    	return "sessionListenerAdapter";
    }
    
    @Bean
    public String  dynamicGroupsListenerAdapter(
    		Scheduler scheduler,
            GroupsService groupsService,
            @Value("${maxkey.job.cron.schedule}") String cronSchedule
            ) throws SchedulerException {
        
        ListenerAdapter.addListener(
    			DynamicGroupsListenerAdapter.class,
    			scheduler,
    			new ListenerParameter().add("groupsService",groupsService).build(),
    			cronSchedule,
    			DynamicGroupsListenerAdapter.class.getSimpleName()
    		);
        _logger.debug("DynamicGroups ListenerAdapter inited .");
        return "dynamicGroupsListenerAdapter";
    }
    
    @Bean
    public String  accountsStrategyListenerAdapter(
    		Scheduler scheduler,
            AccountsService accountsService,
            @Value("${maxkey.job.cron.schedule}") String cronSchedule
            ) throws SchedulerException {    	
        ListenerAdapter.addListener(
        		AccountsStrategyListenerAdapter.class,
        		scheduler,
        		new ListenerParameter().add("accountsService",accountsService).build(),
    			cronSchedule,
    			AccountsStrategyListenerAdapter.class.getSimpleName()
    		);
        _logger.debug("AccountsStrategy ListenerAdapter inited .");
        return "accountsStrategyListenerAdapter";
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

}
