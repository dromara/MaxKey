package org.maxkey.jobs;

import java.util.List;

import org.maxkey.domain.Groups;
import org.maxkey.persistence.service.GroupsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicGroupsJob  implements Job {
    final static Logger _logger = LoggerFactory.getLogger(DynamicGroupsJob.class);
    
    private static  GroupsService groupsService = null;
    
    public static class JOBSTATUS{
        public static int STOP = 0;
        public static int RUNNING = 1;
        public static int FINISHED = 2;
    }
    
    private static int jobStatus = JOBSTATUS.STOP;

    @Override
    public void execute(JobExecutionContext context){
        if(jobStatus == JOBSTATUS.RUNNING) {
            _logger.info("DynamicGroupsJob is in running . " );
            return;
        }
        
        _logger.debug("DynamicGroupsJob is running ... " );
        jobStatus = JOBSTATUS.RUNNING;
        try {
            if(groupsService == null) {
                groupsService = (GroupsService) context.getMergedJobDataMap().get("groupsService");
            }

            List<Groups>  groupsList = groupsService.queryDynamicGroups(null);
            for(Groups group : groupsList) {
                _logger.debug("group " + group);
                groupsService.refreshDynamicGroups(group);
            }
            Thread.sleep(10 *1000);
            _logger.debug("DynamicGroupsJob is success  " );
        }catch(Exception e) {
            _logger.error("Exception " ,e);
            jobStatus = JOBSTATUS.STOP;
        }
        jobStatus = JOBSTATUS.FINISHED;
        _logger.debug("DynamicGroupsJob is finished . " );
    }
    
    

}
