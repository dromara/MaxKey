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
 

package org.dromara.maxkey.synchronizer.workweixin;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.Organizations;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinDepts;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinDeptsResponse;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WorkweixinOrganizationService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(WorkweixinOrganizationService.class);
	
	String access_token;
	
	static String DEPTS_URL="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=%s";
	static long ROOT_DEPT_ID = 1;
	
	public void sync() {
		_logger.info("Sync Workweixin Organizations ...");

		try {			
			WorkWeixinDeptsResponse rsp = requestDepartmentList(access_token);
			
			for(WorkWeixinDepts dept : rsp.getDepartment()) {
				_logger.debug("dept : " + dept.getId()+" "+ dept.getName()+" "+ dept.getParentid());
				//root
				if(dept.getId() == ROOT_DEPT_ID) {
					Organizations rootOrganization = organizationsService.get(Organizations.ROOT_ORG_ID);
					SynchroRelated rootSynchroRelated = buildSynchroRelated(rootOrganization,dept);
					synchroRelatedService.updateSynchroRelated(
							this.synchronizer,rootSynchroRelated,Organizations.CLASS_TYPE);
				}else {
					//synchro Related
					SynchroRelated synchroRelated = 
							synchroRelatedService.findByOriginId(
									this.synchronizer,dept.getId() + "",Organizations.CLASS_TYPE );
					
					Organizations organization = buildOrganization(dept);
					if(synchroRelated == null) {
						organization.setId(organization.generateId());
						organizationsService.insert(organization);
						_logger.debug("Organizations : " + organization);
						
						synchroRelated = buildSynchroRelated(organization,dept);
					}else {
						organization.setId(synchroRelated.getObjectId());
						organizationsService.update(organization);
					}
					
					synchroRelatedService.updateSynchroRelated(
							this.synchronizer,synchroRelated,Organizations.CLASS_TYPE);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public SynchroRelated buildSynchroRelated(Organizations organization,WorkWeixinDepts dept) {
		return new SynchroRelated(
					organization.getId(),
					organization.getOrgName(),
					organization.getOrgName(),
					Organizations.CLASS_TYPE,
					synchronizer.getId(),
					synchronizer.getName(),
					dept.getId()+"",
					dept.getName(),
					"",
					dept.getParentid()+"",
					synchronizer.getInstId());
	}
	
	public WorkWeixinDeptsResponse requestDepartmentList(String access_token) {
		HttpRequestAdapter request =new HttpRequestAdapter();
		String responseBody = request.get(String.format(DEPTS_URL, access_token));
		WorkWeixinDeptsResponse deptsResponse  =JsonUtils.gsonStringToObject(responseBody, WorkWeixinDeptsResponse.class);
		
		_logger.trace("response : " + responseBody);
		for(WorkWeixinDepts dept : deptsResponse.getDepartment()) {
			_logger.debug("WorkWeixinDepts : " + dept);
		}
		return deptsResponse;
	}
	
	public Organizations buildOrganization(WorkWeixinDepts dept) {
		//Parent
		SynchroRelated synchroRelatedParent = 
				synchroRelatedService.findByOriginId(
				this.synchronizer,dept.getParentid() + "",Organizations.CLASS_TYPE);
		Organizations org = new Organizations();
		org.setOrgName(dept.getName());
		org.setOrgCode(dept.getId()+"");
		org.setParentId(synchroRelatedParent.getObjectId());
		org.setParentName(synchroRelatedParent.getObjectName());
		org.setSortIndex(dept.getOrder());
		org.setInstId(this.synchronizer.getInstId());
		org.setStatus(ConstsStatus.ACTIVE);
		org.setDescription("WorkWeixin");
		return org;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
