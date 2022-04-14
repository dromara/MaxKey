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
 

package org.maxkey.jobs;

import java.io.Serializable;
import org.maxkey.persistence.service.GroupsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicGroupsJob extends AbstractScheduleJob  implements Job , Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8831626240807856084L;

    final static Logger _logger = LoggerFactory.getLogger(DynamicGroupsJob.class);
    
    private static  GroupsService groupsService = null;

    @Override
    public void execute(JobExecutionContext context){
        if(jobStatus == JOBSTATUS.RUNNING) {return;}
        
        init(context);
        
        _logger.debug("DynamicGroups Job running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(groupsService != null) {
            	groupsService.refreshAllDynamicGroups();
            	Thread.sleep(10 * 1000);//10 minutes
            }
            _logger.debug("DynamicGroups Job finished  " );
            jobStatus = JOBSTATUS.FINISHED;
        }catch(Exception e) {
            jobStatus = JOBSTATUS.ERROR;
            _logger.error("Exception " ,e);
        }
    }

    @Override
    void init(JobExecutionContext context){
    	if(groupsService == null) {
            groupsService = 
            		(GroupsService) context.getMergedJobDataMap().get("service");
        }
    }

}
