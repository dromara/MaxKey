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
 

package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.authn.listener.SessionListenerAdapter;
import org.dromara.maxkey.authn.session.SessionCategory;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.listener.DynamicGroupsListenerAdapter;
import org.dromara.maxkey.listener.DynamicRolesListenerAdapter;
import org.dromara.maxkey.listener.ReorgDeptListenerAdapter;
import org.dromara.maxkey.persistence.service.ConnectorsService;
import org.dromara.maxkey.persistence.service.GroupsService;
import org.dromara.maxkey.persistence.service.OrganizationsService;
import org.dromara.maxkey.persistence.service.RolesService;
import org.dromara.maxkey.provision.thread.ProvisioningRunner;
import org.dromara.maxkey.provision.thread.ProvisioningRunnerThread;
import org.dromara.maxkey.schedule.ScheduleAdapterBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
public class MaxKeyMgtListenerConfig  {
    private static final  Logger logger = LoggerFactory.getLogger(MaxKeyMgtListenerConfig.class);

    @Bean
    String sessionListenerAdapter(
            Scheduler scheduler,
            SessionManager sessionManager) throws SchedulerException {
    	new ScheduleAdapterBuilder()
    		.setScheduler(scheduler)
    		.setCron("0 0/10 * * * ?")
    		.setJobClass(SessionListenerAdapter.class)
    		.setJobData("sessionManager",sessionManager)
    		.setJobData("category", SessionCategory.MGMT)
    		.build();
        logger.debug("Session ListenerAdapter inited .");
    	return "sessionListenerAdapter";
    }

    @Bean
    String reorgDeptListenerAdapter(
            Scheduler scheduler,
            OrganizationsService organizationsService) throws SchedulerException {
    	new ScheduleAdapterBuilder()
			.setScheduler(scheduler)
			.setCron("0 0/30 * * * ?")
			.setJobClass(ReorgDeptListenerAdapter.class)
			.setJobData("organizationsService",organizationsService)
			.build();
        logger.debug("ReorgDept ListenerAdapter inited .");
    	return "reorgDeptListenerAdapter";
    }

    @Bean
    String dynamicGroupsListenerAdapter(
            Scheduler scheduler,
            GroupsService groupsService,
            @Value("${maxkey.job.cron.schedule}") String cronSchedule
    ) throws SchedulerException {
    	new ScheduleAdapterBuilder()
			.setScheduler(scheduler)
			.setCron(cronSchedule)
			.setJobClass(DynamicGroupsListenerAdapter.class)
			.setJobData("groupsService",groupsService)
			.build();

        logger.debug("DynamicGroups ListenerAdapter inited .");
        return "dynamicGroupsListenerAdapter";
    }
    
    @Bean
    String dynamicRolesListenerAdapter(
            Scheduler scheduler,
            RolesService rolesService,
            @Value("${maxkey.job.cron.schedule}") String cronSchedule
    ) throws SchedulerException {
    	new ScheduleAdapterBuilder()
			.setScheduler(scheduler)
			.setCron(cronSchedule)
			.setJobClass(DynamicRolesListenerAdapter.class)
			.setJobData("rolesService",rolesService)
			.build();

        logger.debug("Dynamic Roles ListenerAdapter inited .");
        return "dynamicRolesListenerAdapter";
    }

    @Bean
    String provisioningRunnerThread(
            ConnectorsService connectorsService,
            JdbcTemplate jdbcTemplate,
            ApplicationConfig applicationConfig
    ) {
        if(applicationConfig.isProvisionSupport()) {
	    	ProvisioningRunner runner = new ProvisioningRunner(connectorsService,jdbcTemplate);
	    	ProvisioningRunnerThread runnerThread = new ProvisioningRunnerThread(runner);
	    	runnerThread.start();
	        logger.debug("provisioning Runner Thread .");
        }else {
        	logger.debug("not need init provisioning Runner Thread .");
        }
        return "provisioningRunnerThread";
    }
}
