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
 

package org.maxkey.jobs;

import java.io.Serializable;
import org.maxkey.persistence.service.AccountsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountsStrategyJob extends AbstractScheduleJob  implements Job , Serializable {

   /**
     * 
     */
    private static final long serialVersionUID = 167999890940939820L;

    final static Logger _logger = LoggerFactory.getLogger(AccountsStrategyJob.class);
    
    private static  AccountsService accountsService = null;
    
    @Override
    public void execute(JobExecutionContext context){
        if(jobStatus == JOBSTATUS.RUNNING) {return;}
        
        init(context);
        
        _logger.debug("Accounts Strategy Job running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(accountsService != null) { 
            	accountsService.refreshAllByStrategy();
            	Thread.sleep(10 * 1000);//10 minutes
            }
            _logger.debug("Accounts Strategy Job finished  " );
            jobStatus = JOBSTATUS.FINISHED;
        }catch(Exception e) {
            jobStatus = JOBSTATUS.ERROR;
            _logger.error("Exception " ,e);
        }
    }

    @Override
    void init(JobExecutionContext context){
    	if(accountsService == null) {
            accountsService = 
            		(AccountsService) context.getMergedJobDataMap().get("service");
        }
    }

}
