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

import org.maxkey.authn.session.SessionManager;
import org.maxkey.entity.HistoryLogin;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListenerAdapter extends AbstractScheduleJob   implements Job , Serializable {
	final static Logger _logger = LoggerFactory.getLogger(SessionListenerAdapter.class);
	
	private static final long serialVersionUID = 4782358765969474833L;
	
	SessionManager sessionManager;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 if(jobStatus == JOBSTATUS.RUNNING) {return;}
		 init(context);
		 	
		 _logger.debug("SessionListener Job is running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(sessionManager != null) { 
            	for (HistoryLogin onlineSession : sessionManager.querySessions()) {
            		if(sessionManager.get(onlineSession.getSessionId()) == null) {
            			sessionManager.terminate(
            					onlineSession.getSessionId(), 
            					onlineSession.getUserId(), 
            					onlineSession.getUsername());
            		}
            	}
            }
            _logger.debug("SessionListener Job finished  " );
            jobStatus = JOBSTATUS.FINISHED;
        }catch(Exception e) {
            jobStatus = JOBSTATUS.ERROR;
            _logger.error("Exception " ,e);
        }
		
	}

	 @Override
    void init(JobExecutionContext context){
    	if(sessionManager == null) {
    		sessionManager = 
            		(SessionManager) context.getMergedJobDataMap().get("service");
        }
    }
}
