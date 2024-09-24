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
 
package org.dromara.maxkey.schedule;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleAdapter {
	private static final  Logger _logger = LoggerFactory.getLogger(ScheduleAdapter.class);
	
	JobExecutionContext context;
	
	protected int jobStatus = JOBSTATUS.STOP;
	
    public static final  class JOBSTATUS{
        public static final int STOP 		= 0;
        public static final int RUNNING 	= 1;
        public static final int ERROR 		= 2;
        public static final int FINISHED 	= 3;
    }
    
    protected void init(JobExecutionContext context){
    	this.context = context;
    }
    
    @SuppressWarnings("unchecked")
	public <T> T getParameter(String name, Class<T> requiredType) {
    	_logger.trace("requiredType {}",requiredType);
    	return (T) context.getMergedJobDataMap().get(name);
    }
    
}
