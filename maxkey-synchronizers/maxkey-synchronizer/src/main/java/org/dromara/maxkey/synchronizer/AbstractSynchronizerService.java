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
 

package org.dromara.maxkey.synchronizer;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.dromara.maxkey.entity.SocialsAssociate;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.persistence.service.HistorySynchronizerService;
import org.dromara.maxkey.persistence.service.OrganizationsService;
import org.dromara.maxkey.persistence.service.SocialsAssociatesService;
import org.dromara.maxkey.persistence.service.SynchroRelatedService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSynchronizerService {
    private static final Logger _logger = 
            LoggerFactory.getLogger(AbstractSynchronizerService.class);
    
    @Autowired
    protected OrganizationsService organizationsService;
    @Autowired
    protected UserInfoService userInfoService;
    @Autowired
    protected SynchroRelatedService synchroRelatedService;
    @Autowired
    protected SocialsAssociatesService socialsAssociatesService;
    @Autowired
    protected HistorySynchronizerService historySynchronizerService;
    
    protected Synchronizers synchronizer;
    
    protected HashMap<String,Organizations> orgsNamePathMap;
     
    protected Organizations rootOrganization = null;
    
    
    public HashMap<String,Organizations> loadOrgsByInstId(String instId,String rootOrgId) {
        List<Organizations> orgsList = organizationsService.find("instid = '" + instId + "'");
        if(rootOrgId== null || rootOrgId.equals("")) {
        	rootOrgId="1";
        }
        
        for(Organizations org : orgsList) {
           if(org.getId().equals(rootOrgId) && rootOrgId.equals("1")) {
               rootOrganization = org; 
               rootOrganization.setNamePath("/"+rootOrganization.getOrgName());
               rootOrganization.setCodePath("/1");
               rootOrganization.setParentId("-1");
               rootOrganization.setParentName("");
           }else if(org.getId().equals(rootOrgId)){
               rootOrganization = org; 
           }
        }
        
        orgsNamePathMap =new HashMap<>();
        orgsNamePathMap.put(rootOrganization.getNamePath(), rootOrganization);
        push(orgsNamePathMap,orgsList,rootOrganization);
        
        _logger.trace("orgsNamePathMap {}" , orgsNamePathMap);
        return orgsNamePathMap;
    }
    
    public void socialsAssociate(SynchroRelated synchroRelated,String provider) {
    	SocialsAssociate socialsAssociate =
    			socialsAssociatesService.findOne("instid = ? and userid = ? and socialuserid = ? and provider = ? ",
    					new Object[] { 
    							synchroRelated.getInstId(),
    							synchroRelated.getObjectId(),
    							synchroRelated.getOriginId(),
    							provider 
    					},
    					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR});
    	if(socialsAssociate == null) {
    		socialsAssociate = new SocialsAssociate();
    		socialsAssociate.setUserId(synchroRelated.getObjectId());
    		socialsAssociate.setUsername(synchroRelated.getObjectName());
    		socialsAssociate.setInstId(synchroRelated.getInstId());
    		socialsAssociate.setProvider(provider);
    		socialsAssociate.setSocialUserId(synchroRelated.getOriginId());
    		socialsAssociatesService.insert(socialsAssociate);
    	}
    }
    public void push(HashMap<String,Organizations> orgsNamePathMap,
                     List<Organizations> orgsList,
                     Organizations parentOrg) {
        for(Organizations org : orgsList) {
            if(org.getParentId().equals(parentOrg.getId())) {
                if(org.getNamePath() == null 
                        || !org.getNamePath().equals(parentOrg.getNamePath()+"/"+org.getOrgName())) {
                    org.setParentName(parentOrg.getOrgName());
                    org.setNamePath(parentOrg.getNamePath()+"/"+org.getOrgName());
                    org.setCodePath(parentOrg.getCodePath()+"/"+org.getId());
                    organizationsService.update(org);
                }
                orgsNamePathMap.put(org.getNamePath(), org);
                push(orgsNamePathMap,orgsList,org);
            }
        }
        return;
    }

    public OrganizationsService getOrganizationsService() {
        return organizationsService;
    }

    public void setOrganizationsService(OrganizationsService organizationsService) {
        this.organizationsService = organizationsService;
    }

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public HashMap<String, Organizations> getOrgsNamePathMap() {
        return orgsNamePathMap;
    }

    public void setOrgsNamePathMap(HashMap<String, Organizations> orgsNamePathMap) {
        this.orgsNamePathMap = orgsNamePathMap;
    }

    public Organizations getRootOrganization() {
        return rootOrganization;
    }

    public void setRootOrganization(Organizations rootOrganization) {
        this.rootOrganization = rootOrganization;
    }

    public Synchronizers getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(Synchronizers synchronizer) {
        this.synchronizer = synchronizer;
    }

    public HistorySynchronizerService getHistorySynchronizerService() {
        return historySynchronizerService;
    }

    public void setHistorySynchronizerService(HistorySynchronizerService historySynchronizerService) {
        this.historySynchronizerService = historySynchronizerService;
    }

	public SynchroRelatedService getSynchroRelatedService() {
		return synchroRelatedService;
	}

	public void setSynchroRelatedService(SynchroRelatedService synchroRelatedService) {
		this.synchroRelatedService = synchroRelatedService;
	}
    
    
}
