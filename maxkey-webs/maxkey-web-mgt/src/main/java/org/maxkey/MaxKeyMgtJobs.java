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

import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.jobs.AccountsStrategyJob;
import org.maxkey.jobs.DynamicGroupsJob;
import org.maxkey.jobs.TicketListenerJob;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.GroupsService;
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
 
    @Bean(name = "schedulerTicketListenerJobs")
    public String  ticketListenerJob(
    		SchedulerFactoryBean schedulerFactoryBean,
    		OnlineTicketService onlineTicketService) throws SchedulerException {
    	
    	JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("service", onlineTicketService);
    	addJobScheduler(
    			TicketListenerJob.class,
    			schedulerFactoryBean,
    			jobDataMap,
    			"0 0/10 * * * ?",//10 minutes
    			"TicketListener"
    		);
    	
    	return "schedulerTicketListenerJobs";
    }
    
    @Bean(name = "schedulerDynamicGroupsJobs")
    public String  dynamicGroupsJobs(
            SchedulerFactoryBean schedulerFactoryBean,
            GroupsService groupsService,
            @Value("${maxkey.job.cron.schedule}") String cronSchedule
            ) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("service", groupsService);
        
        addJobScheduler(
    			DynamicGroupsJob.class,
    			schedulerFactoryBean,
    			jobDataMap,
    			cronSchedule,
    			"DynamicGroups"
    		);
        
        return "schedulerDynamicGroupsJobs";
    }
    
    @Bean(name = "schedulerAccountsStrategyJobs")
    public String  accountsStrategyJobs(
            SchedulerFactoryBean schedulerFactoryBean,
            AccountsService accountsService,
            @Value("${maxkey.job.cron.schedule}") String cronSchedule
            ) throws SchedulerException {    	
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("service", accountsService);
        addJobScheduler(
        		AccountsStrategyJob.class,
    			schedulerFactoryBean,
    			jobDataMap,
    			cronSchedule,
    			"AccountsStrategy"
    		);
        
        return "schedulerAccountsStrategyJobs";
    }
    
    private void addJobScheduler(	Class <? extends Job> jobClass,
    									SchedulerFactoryBean schedulerFactoryBean ,
    									JobDataMap jobDataMap,
            							String cronSchedule,
            							String identity
            						) throws SchedulerException {
    	_logger.debug("Cron {}  , Job schedule {}  ", cronSchedule , identity );
    	Scheduler scheduler = schedulerFactoryBean.getScheduler();
    	
		JobDetail jobDetail = 
				JobBuilder.newJob(jobClass) 
					.withIdentity(identity + "Job", identity + "Group")
					.build();
		
		
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
