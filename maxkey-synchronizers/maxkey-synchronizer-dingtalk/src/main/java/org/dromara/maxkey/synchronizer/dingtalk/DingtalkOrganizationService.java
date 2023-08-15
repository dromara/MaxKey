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
 

package org.dromara.maxkey.synchronizer.dingtalk;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.Organizations;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentGetRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse.DeptBaseResponse;
import com.taobao.api.ApiException;

@Service
public class DingtalkOrganizationService  extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(DingtalkOrganizationService.class);
	
	static Long ROOT_DEPT_ID = 1L;
	
	String access_token;
	
	public void sync() {
		_logger.info("Sync Dingtalk Organizations ...");
		LinkedBlockingQueue<Long> deptsQueue = new LinkedBlockingQueue<Long>();
		deptsQueue.add(ROOT_DEPT_ID);
		try {
			//root
			Organizations rootOrganization = organizationsService.get(Organizations.ROOT_ORG_ID);
			OapiV2DepartmentGetResponse rootDeptRsp = requestDepartment(access_token,ROOT_DEPT_ID);
			_logger.debug("root dept   deptId {} , name {} ,  parentId {}" 
							,rootDeptRsp.getResult().getDeptId(), 
							rootDeptRsp.getResult().getName(), 
							rootDeptRsp.getResult().getParentId());
			//root
			SynchroRelated rootSynchroRelated = buildSynchroRelated(rootOrganization,
					rootDeptRsp.getResult().getDeptId()+"", 
					rootDeptRsp.getResult().getName(), 
					rootDeptRsp.getResult().getParentId()+"");
			
			synchroRelatedService.updateSynchroRelated(
					this.synchronizer,rootSynchroRelated,Organizations.CLASS_TYPE);
			
			while(deptsQueue.element() != null) {
				OapiV2DepartmentListsubResponse rsp = requestDepartmentList(access_token,deptsQueue.poll());
				
				for(DeptBaseResponse dept : rsp.getResult()) {
					_logger.debug("dept  deptId {} , name {} ,  parentId {} " , 
							dept.getDeptId(), 
							dept.getName(), 
							dept.getParentId());
					
					deptsQueue.add(dept.getDeptId());

					//synchro Related
					SynchroRelated synchroRelated = 
							synchroRelatedService.findByOriginId(
									this.synchronizer,dept.getDeptId() + "",Organizations.CLASS_TYPE );
					
					Organizations organization = buildOrganization(dept);
					if(synchroRelated == null) {
						organization.setId(organization.generateId());
						organizationsService.insert(organization);
						_logger.debug("Organizations : " + organization);
						
						synchroRelated = buildSynchroRelated(organization,
								dept.getDeptId() + "", 
								dept.getName(), 
								dept.getParentId() + "");
						
					}else {
						organization.setId(synchroRelated.getObjectId());
						organizationsService.update(organization);
					}
					
					synchroRelatedService.updateSynchroRelated(
							this.synchronizer,synchroRelated,Organizations.CLASS_TYPE);
					
					_logger.debug("Organizations : " + organization);
				}
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}catch (NoSuchElementException e) {
			_logger.debug("Sync Department successful .");
		}
		
	}
	
	public OapiV2DepartmentListsubResponse requestDepartmentList(String access_token,Long deptId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
		OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
		req.setDeptId(deptId);
		req.setLanguage("zh_CN");
		OapiV2DepartmentListsubResponse rspDepts = client.execute(req, access_token);
		_logger.trace("response : " + rspDepts.getBody());
		return rspDepts;
	}
	
	public OapiV2DepartmentGetResponse requestDepartment(String access_token,Long deptId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
		OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
		req.setDeptId(deptId);
		req.setLanguage("zh_CN");
		OapiV2DepartmentGetResponse rspDepts = client.execute(req, access_token);
		_logger.trace("response : " + rspDepts.getBody());
		return rspDepts;
	}
	
	public SynchroRelated buildSynchroRelated(Organizations organization,String deptId,String name,String parentId) {
		return new SynchroRelated(
				organization.getId(),
				organization.getOrgName(),
				organization.getOrgName(),
				Organizations.CLASS_TYPE,
				synchronizer.getId(),
				synchronizer.getName(),
				deptId+"",
				name,
				"",
				parentId,
				synchronizer.getInstId());
	}
	
	public Organizations buildOrganization(DeptBaseResponse dept) {
		//Parent
		SynchroRelated synchroRelatedParent = 
				synchroRelatedService.findByOriginId(
				this.synchronizer,dept.getParentId() + "",Organizations.CLASS_TYPE);
		Organizations org = new Organizations();
		org.setId(dept.getDeptId()+"");
		org.setOrgCode(dept.getDeptId()+"");
		org.setOrgName(dept.getName());
		org.setParentCode(dept.getParentId()+"");
		if(synchroRelatedParent != null) {
			org.setParentId(synchroRelatedParent.getObjectId());
			org.setParentName(synchroRelatedParent.getObjectName());
		}
		org.setInstId(this.synchronizer.getInstId());
		org.setStatus(ConstsStatus.ACTIVE);
		org.setDescription("dingtalk");
		return org;
	}



	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
}
