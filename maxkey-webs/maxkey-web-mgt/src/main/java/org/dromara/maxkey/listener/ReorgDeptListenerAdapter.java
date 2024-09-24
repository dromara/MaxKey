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
package org.dromara.maxkey.listener;

import java.io.Serializable;

import org.dromara.maxkey.persistence.service.OrganizationsService;
import org.dromara.maxkey.schedule.ScheduleAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReorgDeptListenerAdapter extends ScheduleAdapter   implements Job , Serializable {
	static final  Logger _logger = LoggerFactory.getLogger(ReorgDeptListenerAdapter.class);
	
	private static final long serialVersionUID = 4782358765969474833L;
	
	transient OrganizationsService organizationsService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 if(jobStatus == JOBSTATUS.RUNNING) {return;}
		 init(context);
		 	
		 _logger.debug("running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
        	organizationsService.reorgNamePath(null);
            _logger.debug("finished  " );
            jobStatus = JOBSTATUS.FINISHED;
        }catch(Exception e) {
            jobStatus = JOBSTATUS.ERROR;
            _logger.error("Exception " ,e);
        }
		
	}

	 @Override
	protected void init(JobExecutionContext context){
		 super.init(context);
    	if(organizationsService == null) {
    		organizationsService = getParameter("organizationsService",OrganizationsService.class);
        }
    }
}
