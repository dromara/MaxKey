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
 

package org.maxkey.synchronizer;

import org.apache.mybatis.jpa.util.WebContext;
import org.maxkey.entity.Synchronizers;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizerJob  implements Job {
	final static Logger _logger = LoggerFactory.getLogger(SynchronizerJob.class);
    
    
    public static class JOBSTATUS{
        public static int STOP = 0;
        public static int RUNNING = 1;
        public static int FINISHED = 2;
    }
    
    private static int jobStatus = JOBSTATUS.STOP;

    @Override
    public void execute(JobExecutionContext context){
        if(jobStatus == JOBSTATUS.RUNNING) {
            _logger.info("SynchronizerJob is in running . " );
            return;
        }
        
        _logger.debug("SynchronizerJob is running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
        	Synchronizers synchronizer = (Synchronizers)context.getMergedJobDataMap().get("synchronizer");
        	_logger.debug("synchronizer : " + synchronizer.getName()+"("+synchronizer.getId()+"_"+synchronizer.getSourceType()+")");
    		_logger.debug("synchronizer service : " + synchronizer.getService());
    		_logger.debug("synchronizer Scheduler : " + synchronizer.getScheduler());
        	ISynchronizerService service = (ISynchronizerService)WebContext.getBean(synchronizer.getService());
        	service.setSynchronizer(synchronizer);
        	service.sync();
            
            _logger.debug("SynchronizerJob is success  " );
        }catch(Exception e) {
            _logger.error("Exception " ,e);
            jobStatus = JOBSTATUS.STOP;
        }
        jobStatus = JOBSTATUS.FINISHED;
        _logger.debug("SynchronizerJob is finished . " );
    }
    
    

}
