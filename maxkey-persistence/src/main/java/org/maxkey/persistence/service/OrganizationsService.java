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
 

package org.maxkey.persistence.service;

import java.sql.Types;
import java.util.List;
import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.maxkey.entity.Organizations;
import org.maxkey.persistence.mapper.OrganizationsMapper;
import org.maxkey.persistence.mq.MqIdentityAction;
import org.maxkey.persistence.mq.MqIdentityTopic;
import org.maxkey.persistence.mq.MqPersistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class OrganizationsService  extends JpaBaseService<Organizations>{

    @Autowired
    MqPersistService mqPersistService;
    
	public OrganizationsService() {
		super(OrganizationsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public OrganizationsMapper getMapper() {
		return (OrganizationsMapper)super.getMapper();
	}
	
	 public boolean insert(Organizations organization) {
	     if(super.insert(organization)){
	    	 mqPersistService.send(
                     MqIdentityTopic.ORG_TOPIC, organization, MqIdentityAction.CREATE_ACTION);
             return true;
         }
         return false;
	 }
	 
	 public boolean update(Organizations organization) {
	     if(super.update(organization)){
	    	 mqPersistService.send(
                     MqIdentityTopic.ORG_TOPIC, organization, MqIdentityAction.UPDATE_ACTION);
             return true;
         }
         return false;
     }
 
	 public void saveOrUpdate(Organizations organization) {
		 Organizations loadOrg =findOne(" id = ? and instid = ?", 
					new Object[] { organization.getId().toString(), organization.getInstId() },
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
	     if(super.delete(organization)){
	    	 mqPersistService.send(
                     MqIdentityTopic.ORG_TOPIC, organization, MqIdentityAction.DELETE_ACTION);
             return true;
         }
         return false;
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
