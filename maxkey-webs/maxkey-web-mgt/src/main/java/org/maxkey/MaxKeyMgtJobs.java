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

import org.maxkey.jobs.AccountsStrategyJob;
import org.maxkey.jobs.DynamicGroupsJob;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.GroupsService;
import org.opensaml.xml.ConfigurationException;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class MaxKeyMgtJobs  implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtJobs.class);
 
    /**
     * schedulerJobsInit.
     * @return schedulerJobsInit
     * @throws ConfigurationException 
     * @throws SchedulerException 
     */
    @Bean(name = "schedulerJobs")
    public String  schedulerJobs(
            SchedulerFactoryBean schedulerFactoryBean,
            GroupsService groupsService,
            AccountsService accountsService,
            @Value("${maxkey.job.cron.schedule}") String cronSchedule
            ) throws SchedulerException {
    	_logger.debug("cron schedule : " + cronSchedule);
    	
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        
        addDynamicGroupsJob(
        			DynamicGroupsJob.class,
        			scheduler,
        			groupsService,
        			cronSchedule,
        			"DynamicGroups"
        		);
        
        addAccountsStrategyJob(
        			AccountsStrategyJob.class,
        			scheduler,
        			accountsService,
        			cronSchedule,
        			"AccountsStrategy"
        		);
        
        return "schedulerJobs";
    }
    
	
    private void addDynamicGroupsJob(	Class <? extends Job> jobClass,
    									Scheduler scheduler ,
            							GroupsService groupsService,
            							String cronSchedule,
            							String identity
            						) throws SchedulerException {
    	
    	_logger.debug("add DynamicGroups Job");
		JobDetail jobDetail = 
				JobBuilder.newJob(jobClass) 
					.withIdentity(identity + "Job", identity + "Group")
					.build();
		
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("service", groupsService);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronSchedule);
		
		CronTrigger cronTrigger = 
				TriggerBuilder.newTrigger()
					.withIdentity("trigger" + identity, identity + "TriggerGroup")
					.usingJobData(jobDataMap)
					.withSchedule(scheduleBuilder)
					.build();
		
		scheduler.scheduleJob(jobDetail,cronTrigger);    
	}

    private void addAccountsStrategyJob(Class <? extends Job> jobClass,
    									Scheduler scheduler ,
            							AccountsService accountsService,
            							String cronSchedule,
            							String identity
            						) throws SchedulerException {
    	
    	_logger.debug("add Accounts Strategy Job");
		JobDetail jobDetail = 
		JobBuilder.newJob(jobClass) 
			.withIdentity(identity + "Job", identity + "Group")
			.build();
		
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("service", accountsService);
		
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronSchedule);
		CronTrigger cronTrigger = 
		TriggerBuilder.newTrigger()
			.withIdentity("trigger" + identity, identity + "TriggerGroup")
			.usingJobData(jobDataMap)
			.withSchedule(scheduleBuilder)
			.build();
		
		scheduler.scheduleJob(jobDetail,cronTrigger);    
	}
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

}
