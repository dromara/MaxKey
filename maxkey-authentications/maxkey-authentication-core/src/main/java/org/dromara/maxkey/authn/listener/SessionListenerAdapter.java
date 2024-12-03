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
package org.dromara.maxkey.authn.listener;

import java.io.Serializable;
import java.util.Date;

import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.dromara.maxkey.schedule.ScheduleAdapter;
import org.dromara.maxkey.util.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListenerAdapter extends ScheduleAdapter   implements Job , Serializable {
	static final Logger logger = LoggerFactory.getLogger(SessionListenerAdapter.class);
	
	private static final long serialVersionUID = 4782358765969474833L;
	
	transient SessionManager sessionManager;

	Integer category;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 if(jobStatus == JOBSTATUS.RUNNING) {return;}
		 init(context);
		 	
		 logger.debug("running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(sessionManager != null) { 
            	int sessionCount = 0;
            	for (HistoryLogin login : sessionManager.querySessions(category)) {
            		Session session = sessionManager.get(login.getSessionId());
            		if(session == null) {
            			logger.debug("TimeOut user {} session {}  Login at {} and  at {} ." ,
            					login.getUsername(), 
            					login.getId(),
            					login.getLoginTime(),
            					DateUtils.formatDateTime(new Date())
            			);
            			sessionManager.terminate(
            					login.getSessionId(), 
            					login.getUserId(), 
            					login.getUsername());
            		}else {
            			logger.debug("user {} session {} Login at {} , Last Access at {} will Expired at {}." ,
            					login.getUsername(), 
            					login.getId(),
            					session.getStartTimestamp(),
            					session.getLastAccessTime(),
            					session.getExpiredTime()
            			);
            			sessionCount ++ ;
            		}
            	}
            	logger.debug("current session count {} ." ,sessionCount);
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
    	if(sessionManager == null) {
    		sessionManager = getParameter("sessionManager",SessionManager.class);
    		category = getParameter("category",Integer.class);
        }
    }
}
