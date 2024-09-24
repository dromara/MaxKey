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

import java.io.Serializable;

import org.dromara.maxkey.persistence.service.GroupsService;
import org.dromara.maxkey.schedule.ScheduleAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicGroupsListenerAdapter extends ScheduleAdapter  implements Job , Serializable {
	static final  Logger logger = LoggerFactory.getLogger(DynamicGroupsListenerAdapter.class);
    
    private static final long serialVersionUID = 8831626240807856084L;

    transient GroupsService groupsService;

    @Override
    public void execute(JobExecutionContext context){
        if(jobStatus == JOBSTATUS.RUNNING) {return;}
        
        init(context);
        
        logger.debug("running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(groupsService != null) {
            	groupsService.refreshAllDynamicGroups();
            	Thread.sleep(10 * 1000);//10 minutes
            }
            logger.debug("finished  " );
            jobStatus = JOBSTATUS.FINISHED;
        }catch(Exception e) {
            jobStatus = JOBSTATUS.ERROR;
            logger.error("Exception " ,e);
        }
    }

    @Override
	protected void init(JobExecutionContext context){
    	super.init(context);
    	if(groupsService == null) {
    		groupsService = getParameter("groupsService",GroupsService.class);
        }
    }

}
