/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.synchronizer.SynchronizerJob;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
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
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@AutoConfiguration
public class SynchronizerAutoConfiguration   implements InitializingBean {
	private static final  Logger _logger = LoggerFactory.getLogger(SynchronizerAutoConfiguration.class);
	public static final String SYNCHRONIZERS_SELECT_STATEMENT = "select * from mxk_synchronizers where status ='1'";

    @Bean(name = "schedulerSynchronizerJobs")
    String schedulerSynchronizerJobs(
                JdbcTemplate  jdbcTemplate,
                SchedulerFactoryBean schedulerFactoryBean,
                @Value("${maxkey.job.cron.enable}") boolean jobCronEnable
    ) throws SchedulerException {
		
		 Scheduler scheduler = schedulerFactoryBean.getScheduler();
		 if(jobCronEnable) {
			 List<Synchronizers> synchronizerList = querySynchronizers(jdbcTemplate);
		     for(Synchronizers synchronizer : synchronizerList) {
		    	 if(synchronizer.getScheduler()!=null 
		    	         && !synchronizer.getScheduler().equals("")
		    	         && CronExpression.isValidExpression(synchronizer.getScheduler())) {
		    		 _logger.debug("synchronizer details : {}" , synchronizer);
		    		 buildJob(scheduler,synchronizer);
		    	 }
		     }
		 }
		 return "schedulerSynchronizerJobs";
	}
	    
		
	private void buildJob(Scheduler scheduler ,
	                      Synchronizers synchronizer) throws SchedulerException {
		JobDetail jobDetail = 
		        JobBuilder.newJob(SynchronizerJob.class) 
		        .withIdentity(synchronizer.getService()+"_Job", "SynchronizerGroups")
		        .build();
		
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("synchronizer", synchronizer);
		_logger.debug("synchronizer : {}" , synchronizer.getName()+"("+synchronizer.getId()+"_"+synchronizer.getSourceType()+")");
		_logger.debug("synchronizer service : {}", synchronizer.getService());
		_logger.debug("synchronizer Scheduler : {} " ,synchronizer.getScheduler());
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(synchronizer.getScheduler());
		CronTrigger cronTrigger = 
		        TriggerBuilder.newTrigger()
		        .withIdentity("trigger_"+synchronizer.getService(), "SynchronizerGroups")
		        .usingJobData(jobDataMap)
		        .withSchedule(scheduleBuilder)
		        .build();
		scheduler.scheduleJob(jobDetail,cronTrigger);    
	}

	public List<Synchronizers> querySynchronizers(JdbcTemplate  jdbcTemplate) {
		return  jdbcTemplate.query(SYNCHRONIZERS_SELECT_STATEMENT, new RowMapper<Synchronizers>() {
			@Override
			public Synchronizers mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	 Synchronizers synchronizer = new Synchronizers();
	        	 synchronizer.setId(         rs.getString("id"));
	        	 synchronizer.setName(       rs.getString("name"));
	        	 synchronizer.setScheduler(  rs.getString("scheduler"));
	        	 synchronizer.setSourceType( rs.getString("sourcetype"));
	        	 synchronizer.setProviderUrl(rs.getString("providerurl"));
	        	 synchronizer.setDriverClass(rs.getString("driverclass"));
	        	 synchronizer.setPrincipal(  rs.getString("principal"));
	        	 synchronizer.setCredentials(
	        			 PasswordReciprocal.getInstance().decoder(rs.getString("credentials")));
	        	 synchronizer.setResumeTime( rs.getString("resumetime"));
	        	 synchronizer.setSuspendTime(rs.getString("suspendtime"));
	        	 synchronizer.setUserFilters(	 rs.getString("userfilters"));
	        	 synchronizer.setUserBasedn(     rs.getString("userbasedn"));
	        	 synchronizer.setOrgFilters(	 rs.getString("orgfilters"));
	        	 synchronizer.setOrgBasedn(     rs.getString("orgbasedn"));
	        	 synchronizer.setMsadDomain( rs.getString("msaddomain"));
	        	 synchronizer.setSslSwitch(  rs.getString("sslswitch"));
	        	 synchronizer.setTrustStore( rs.getString("truststore"));
	        	 synchronizer.setStatus(   rs.getString("status"));
	        	 synchronizer.setDescription(rs.getString("description"));
	        	 synchronizer.setSyncStartTime(rs.getInt("syncstarttime"));
	        	 synchronizer.setService(rs.getString("service"));

	             return synchronizer;
        	}
		});
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
}
