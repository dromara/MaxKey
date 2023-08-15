/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.synchronizer.workweixin.service;

import java.util.HashMap;
import java.util.List;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.Organizations;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReorgDeptService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(ReorgDeptService.class);

	String rootParentOrgId = "-1";

	public void sync() {
		_logger.info("Sync Organizations ...");

		try {
			long responseCount =0;
			HashMap<String,Organizations>orgCastMap =new HashMap<String,Organizations>();
			Organizations queryOrganization =new Organizations();
			queryOrganization.setInstId(this.synchronizer.getInstId());
			List<Organizations> listOrg = organizationsService.query(queryOrganization);

			buildNamePath(orgCastMap,listOrg);
			
			for(Organizations org :listOrg) {
				_logger.info("Dept "+(++responseCount)+" : " + org);
				org.setStatus(ConstsStatus.ACTIVE);
				organizationsService.update(org);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	
	/**
	 * Reorganization name path & code path	
	 * @param orgCastMap
	 * @param listOrgCast
	 */
	public void buildNamePath(HashMap<String,Organizations>orgMap,
			List<Organizations> listOrg) {
			Organizations tempOrg = null;
		//root org
		for(int i=0;i<listOrg.size();i++) {
			if(listOrg.get(i).getParentId().equals(rootParentOrgId)){
				tempOrg = listOrg.get(i); 
				tempOrg.setReorgNamePath(true);
				tempOrg.setNamePath("/"+tempOrg.getOrgName());
				tempOrg.setCodePath("/"+tempOrg.getId());
				tempOrg.setParentId("-1");
				tempOrg.setParentName("");
				orgMap.put(tempOrg.getId(), tempOrg);
	        }
		}
		
 	   	do {
 	   		for(int i=0;i<listOrg.size();i++) {
 	   			if(!listOrg.get(i).isReorgNamePath()) {
 	   				Organizations parentOrg = orgMap.get(listOrg.get(i).getParentId());
	 	   			tempOrg = listOrg.get(i); 
	 	   			if(!tempOrg.isReorgNamePath() && parentOrg != null){
	 	   				tempOrg.setReorgNamePath(true);
	 	   				tempOrg.setParentName(parentOrg.getOrgName());
	 	   				tempOrg.setCodePath(parentOrg.getCodePath()+"/"+tempOrg.getId());
	 	   				tempOrg.setNamePath(parentOrg.getNamePath()+"/"+tempOrg.getOrgName());
	 	   				orgMap.put(tempOrg.getId(), tempOrg);
						_logger.info("reorg : " + tempOrg);
	 	   			}
 	   			}
 	   		}
 	   	}while(listOrg.size()>listOrg.size());
	}

}
