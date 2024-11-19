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
import java.util.Date;
import java.util.List;

import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.persistence.mapper.SynchroRelatedMapper;
import org.dromara.maxkey.persistence.service.SynchroRelatedService;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class SynchroRelatedServiceImpl  extends JpaServiceImpl<SynchroRelatedMapper,SynchroRelated> implements SynchroRelatedService{

	public int updateSyncTime(SynchroRelated synchroRelated) {
		return getMapper().updateSyncTime(synchroRelated);
	}
	
	public List<SynchroRelated> findOrgs(Synchronizers synchronizer) {
		return find(
				"instid = ? and syncid = ? and objecttype = ? ",
		 		new Object[] { synchronizer.getInstId() ,synchronizer.getId(),Organizations.CLASS_TYPE},
                new int[] { Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);
	}
	
	public SynchroRelated findByOriginId(Synchronizers synchronizer,String originId,String classType) {
		return findOne("instid = ? and syncId = ? and originid = ? and objecttype = ? ",
		 		new Object[] { synchronizer.getInstId(),synchronizer.getId(),originId,classType },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR});
	}
	
	public void updateSynchroRelated(Synchronizers synchronizer,SynchroRelated synchroRelated,String classType) {
		SynchroRelated loadSynchroRelated = 
				findByOriginId(
						synchronizer,synchroRelated.getOriginId(),classType );
		if(loadSynchroRelated == null) {
			insert(synchroRelated);
		}else {
			synchroRelated.setId(loadSynchroRelated.getId());
			synchroRelated.setSyncTime(DateUtils.formatDateTime(new Date()));
			updateSyncTime(synchroRelated);
		}
	}
}
