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
package org.maxkey.listener;

import java.io.Serializable;
import java.util.Date;

import org.maxkey.authn.session.Session;
import org.maxkey.authn.session.SessionManager;
import org.maxkey.entity.HistoryLogin;
import org.maxkey.util.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListenerAdapter extends ListenerAdapter   implements Job , Serializable {
	final static Logger _logger = LoggerFactory.getLogger(SessionListenerAdapter.class);
	
	private static final long serialVersionUID = 4782358765969474833L;
	
	SessionManager sessionManager;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 if(jobStatus == JOBSTATUS.RUNNING) {return;}
		 init(context);
		 	
		 _logger.debug("running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(sessionManager != null) { 
            	int sessionCount = 0;
            	for (HistoryLogin login : sessionManager.querySessions()) {
            		Session session = sessionManager.get(login.getSessionId());
            		if(session == null) {
            			_logger.debug("user {} session {}  Login at {} and TimeOut at {} ." ,
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
            			_logger.debug("user {} session {} Login at {} , Last Access at {} will Expired at {}." ,
            					login.getUsername(), 
            					login.getId(),
            					session.getStartTimestamp(),
            					session.getLastAccessTime(),
            					session.getExpiredTime()
            			);
            			sessionCount ++ ;
            		}
            	}
            	_logger.debug("current session count {} ." ,sessionCount);
            }
            _logger.debug("finished  " );
            jobStatus = JOBSTATUS.FINISHED;
        }catch(Exception e) {
            jobStatus = JOBSTATUS.ERROR;
            _logger.error("Exception " ,e);
        }
		
	}

	 @Override
    void init(JobExecutionContext context){
		 super.init(context);
    	if(sessionManager == null) {
    		sessionManager = getParameter("sessionManager",SessionManager.class);
        }
    }
}
