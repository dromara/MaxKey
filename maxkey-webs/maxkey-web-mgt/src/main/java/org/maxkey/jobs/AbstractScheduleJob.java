package org.maxkey.jobs;

import org.quartz.JobExecutionContext;

public class AbstractScheduleJob {

    public final static class JOBSTATUS{
        public static int STOP 		= 0;
        public static int RUNNING 	= 1;
        public static int ERROR 	= 2;
        public static int FINISHED 	= 3;
    }
    
    protected int jobStatus = JOBSTATUS.STOP;
    
    
    void init(JobExecutionContext context){};
    
}
