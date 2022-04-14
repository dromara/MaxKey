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

import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.entity.HistoryLogin;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketListenerJob extends AbstractScheduleJob   implements Job , Serializable {
	final static Logger _logger = LoggerFactory.getLogger(TicketListenerJob.class);
	
	private static final long serialVersionUID = 4782358765969474833L;
	
	OnlineTicketService onlineTicketService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 if(jobStatus == JOBSTATUS.RUNNING) {return;}
		 init(context);
		 	
		 _logger.debug("TicketListener Job is running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(onlineTicketService != null) { 
            	for (HistoryLogin onlineTicket : onlineTicketService.queryOnlineTicket()) {
            		if(onlineTicketService.get(onlineTicket.getSessionId()) == null) {
            			onlineTicketService.terminate(
            					onlineTicket.getSessionId(), 
            					onlineTicket.getUserId(), 
            					onlineTicket.getUsername());
            		}
            	}
            }
            _logger.debug("TicketListener Job finished  " );
            jobStatus = JOBSTATUS.FINISHED;
        }catch(Exception e) {
            jobStatus = JOBSTATUS.ERROR;
            _logger.error("Exception " ,e);
        }
		
	}

	 @Override
    void init(JobExecutionContext context){
    	if(onlineTicketService == null) {
    		onlineTicketService = 
            		(OnlineTicketService) context.getMergedJobDataMap().get("service");
        }
    }
}
