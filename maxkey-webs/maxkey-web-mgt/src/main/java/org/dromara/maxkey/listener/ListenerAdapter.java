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
package org.dromara.maxkey.listener;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListenerAdapter {
	private static final  Logger logger = LoggerFactory.getLogger(ListenerAdapter.class);
	
	JobExecutionContext context;
	
    public static final  class JOBSTATUS{
        public static int STOP 		= 0;
        public static int RUNNING 	= 1;
        public static int ERROR 	= 2;
        public static int FINISHED 	= 3;
    }
    
    protected int jobStatus = JOBSTATUS.STOP;
    
    
    void init(JobExecutionContext context){
    	this.context = context;
    };
    
    @SuppressWarnings("unchecked")
	public <T> T getParameter(String name, Class<T> requiredType) {
    	return (T) context.getMergedJobDataMap().get(name);
    };
    
    public static void addListener(
    		Class <? extends Job> jobClass,
    		Scheduler scheduler ,
			JobDataMap jobDataMap,
			String cronSchedule,
			String identity
		) throws SchedulerException {
		logger.debug("Cron {}  , Job schedule {}  ", cronSchedule , identity );
		
		JobDetail jobDetail = 
				JobBuilder.newJob(jobClass) 
				.withIdentity(identity, identity + "Group")
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
}
