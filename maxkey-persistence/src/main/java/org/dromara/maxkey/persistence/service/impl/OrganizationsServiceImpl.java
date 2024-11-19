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
 

package org.dromara.maxkey.persistence.service.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.persistence.mapper.OrganizationsMapper;
import org.dromara.maxkey.persistence.service.OrganizationsService;
import org.dromara.maxkey.provision.ProvisionAct;
import org.dromara.maxkey.provision.ProvisionService;
import org.dromara.maxkey.provision.ProvisionTopic;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class OrganizationsServiceImpl  extends JpaServiceImpl<OrganizationsMapper,Organizations> implements OrganizationsService{
	static final Logger _logger = LoggerFactory.getLogger(OrganizationsServiceImpl.class);
	
    @Autowired
    ProvisionService provisionService;
    
	 @Override
	 public boolean insert(Organizations organization) {
	     if(super.insert(organization)){
	    	 provisionService.send(
                     ProvisionTopic.ORG_TOPIC, organization, ProvisionAct.CREATE);
             return true;
         }
         return false;
	 }
	 
	 @Override
	 public boolean update(Organizations organization) {
	     if(super.update(organization)){
	    	 provisionService.send(
                     ProvisionTopic.ORG_TOPIC, organization, ProvisionAct.UPDATE);
             return true;
         }
         return false;
     }
 
	 public void saveOrUpdate(Organizations organization) {
		 Organizations loadOrg =findOne(" id = ? and instid = ?", 
					new Object[] { organization.getId(), organization.getInstId() },
              new int[] { Types.VARCHAR, Types.VARCHAR });
		 if( loadOrg == null) {
				insert(organization);
			}else {
				organization.setId(organization.getId());
				update(organization);
			}
	 }
	 public List<Organizations> queryOrgs(Organizations organization){
		 return getMapper().queryOrgs(organization);
	 }
	 
	 public boolean delete(Organizations organization) {
	     if(super.delete(organization.getId())){
	    	 provisionService.send(
                     ProvisionTopic.ORG_TOPIC, organization, ProvisionAct.DELETE);
             return true;
         }
         return false;
	 }


	public void reorgNamePath(String instId) {
		_logger.debug("instId {}", instId);
		if (StringUtils.isBlank(instId)) {
			instId = "1";
		}

		HashMap<String, Organizations> reorgOrgMap = new HashMap<>();
		List<Organizations> orgList = find(" where instid ='" + instId + "'");
		List<Organizations> originOrgList = new ArrayList<>();
		Organizations rootOrg = null;
		for (Organizations org : orgList) {
			reorgOrgMap.put(org.getId(), org);
			if (isRootOrg(org)) {
				rootOrg = org;
			}
			Organizations cloneOrg = new Organizations();
			BeanUtils.copyProperties(org, cloneOrg);
			originOrgList.add(cloneOrg);
		}
		try {
			reorg(reorgOrgMap, orgList, rootOrg);
			_logger.debug("reorged .");
			long reorgCount = 0;
			for (Organizations originOrg : originOrgList) {
				Organizations reorgOrg = reorgOrgMap.get(originOrg.getId());
				_logger.trace("reorged Organization {}", reorgOrg);
				if (originOrg.getNamePath() == null || !originOrg.getNamePath().equals(reorgOrg.getNamePath())) {
					_logger.debug("update reorgCount {} , Organization {}", ++reorgCount, reorgOrg);
					getMapper().updateNamePath(reorgOrg);
				}
			}
			_logger.debug("reorg finished .");
		} catch (Exception e) {
			_logger.error("reorgNamePath Exception ", e);
		}
	}

	boolean isRootOrg(Organizations rootOrg) {
		if (rootOrg.getParentId() == null || rootOrg.getParentId().equalsIgnoreCase("-1")
				|| rootOrg.getParentId().equalsIgnoreCase(rootOrg.getId())
				|| rootOrg.getParentId().equalsIgnoreCase(rootOrg.getInstId())) {
			return true;
		}
		return false;
	}

	void reorg(HashMap<String, Organizations> orgMap, List<Organizations> orgList, Organizations rootOrg) {
		if (isRootOrg(rootOrg)) {
			rootOrg.setCodePath("/" + rootOrg.getId() + "/");
			rootOrg.setNamePath("/" + rootOrg.getOrgName() + "/");
		} else {
			Organizations parent = orgMap.get(rootOrg.getParentId());
			rootOrg.setCodePath(parent.getCodePath() + rootOrg.getId() + "/");
			rootOrg.setNamePath(parent.getNamePath() + rootOrg.getOrgName() + "/");
		}
		rootOrg.setReorgNamePath(true);

		for (Organizations org : orgList) {
			if (org.isReorgNamePath())
				continue;
			if (org.getParentId().equalsIgnoreCase(rootOrg.getId())) {
				reorg(orgMap, orgList, org);
			}
		}
	}
	 /**
	     *       根据数据格式返回数据
     *
     * @param cell
     * @return
     */
    public static String getValue(Cell cell) {
        if (cell == null) {
            return "";
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            cell.setBlank();
            return String.valueOf(cell.getStringCellValue().trim());
        } else {
            return String.valueOf(cell.getStringCellValue().trim());
        }
    }
}
