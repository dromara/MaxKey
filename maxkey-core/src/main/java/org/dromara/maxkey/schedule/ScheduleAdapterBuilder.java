/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.schedule;

import org.apache.commons.lang3.StringUtils;
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

public class ScheduleAdapterBuilder {
	private static final  Logger _logger = LoggerFactory.getLogger(ScheduleAdapterBuilder.class);
	
	Scheduler scheduler ;
	
	String cron;
	
	Class <? extends Job> jobClass;
	
	JobDataMap jobDataMap;
	
	String identity ;
	
    public  void addListener(
    		Scheduler scheduler ,
    		Class <? extends Job> jobClass,
    		String cronSchedule,
			JobDataMap jobDataMap
		) throws SchedulerException {
    	this.cron = cronSchedule;
    	this.scheduler = scheduler;
    	this.jobClass = jobClass;
    	this.jobDataMap = jobDataMap;
    	this.build();
	}
    
    public ScheduleAdapterBuilder setIdentity(String identity) {
    	this.identity = identity;
    	return this;
    }
    
    public ScheduleAdapterBuilder setScheduler(Scheduler scheduler) {
    	this.scheduler = scheduler;
    	return this;
    }
    
    public ScheduleAdapterBuilder setJobDataMap(JobDataMap jobDataMap) {
    	this.jobDataMap = jobDataMap;
    	return this;
    }
    
    public ScheduleAdapterBuilder setJobData(String key,Object data) {
    	if(this.jobDataMap == null) {
    		jobDataMap = new JobDataMap();
    	}
    	this.jobDataMap.put(key, data);
    	return this;
    }
    
    public ScheduleAdapterBuilder setCron(String cron) {
    	this.cron = cron;
    	return this;
    }
    
    public ScheduleAdapterBuilder setJobClass(Class <? extends Job> jobClass) {
    	this.jobClass = jobClass;
    	return this;
    }
    
    public void build() throws SchedulerException {
    	if(StringUtils.isBlank(identity)) {
    		identity = jobClass.getSimpleName();
    	}
		_logger.debug("Job schedule {} ,Cron {}  ",  identity ,cron);
		
		JobDetail jobDetail = 
				JobBuilder.newJob(jobClass) 
				.withIdentity(identity, identity + "Group")
				.build();
		
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
		
		CronTrigger cronTrigger = 
			TriggerBuilder.newTrigger()
				.withIdentity("trigger" + identity, identity + "TriggerGroup")
				.usingJobData(jobDataMap)
				.withSchedule(scheduleBuilder)
				.build();
		
		scheduler.scheduleJob(jobDetail,cronTrigger);    
    }
    
}
