/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.synchronizer.feishu;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.synchronizer.feishu.entity.FeishuDepts;
import org.dromara.maxkey.synchronizer.feishu.entity.FeishuDeptsResponse;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.dromara.maxkey.util.AuthorizationHeaderUtils;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.dromara.maxkey.synchronizer.utils.FieldUtil.*;

@Service
public class FeishuOrganizationService extends AbstractSynchronizerService implements ISynchronizerService{
	static final  Logger _logger = LoggerFactory.getLogger(FeishuOrganizationService.class);
	
	String access_token;
	private static final Integer ORG_TYPE = 2;



	@Autowired
	private SyncJobConfigFieldService syncJobConfigFieldService;
	
	static String DEPTS_URL = "https://open.feishu.cn/open-apis/contact/v3/departments/%s/children?page_size=50";
	static String ROOT_DEPT_URL = "https://open.feishu.cn/open-apis/contact/v3/departments/%s";
	static String ROOT_DEPT_ID = "0";
	public void sync() {
		_logger.info("Sync Feishu Organizations ...");

		LinkedBlockingQueue<String> deptsQueue = new LinkedBlockingQueue<String>();
		
		deptsQueue.add(ROOT_DEPT_ID);
		//root
		FeishuDeptsResponse rspRoot = requestDepartment(ROOT_DEPT_URL,ROOT_DEPT_ID,access_token);
		Organizations rootOrganization = organizationsService.get(Organizations.ROOT_ORG_ID);
		SynchroRelated rootSynchroRelated = buildSynchroRelated(rootOrganization,rspRoot.getData().getDepartment());
				
		synchroRelatedService.updateSynchroRelated(
				this.synchronizer,rootSynchroRelated,Organizations.CLASS_TYPE);
		
		//child
		try {
			while(deptsQueue.element() != null) {
				FeishuDeptsResponse rsp = requestDepartmentList(access_token,deptsQueue.poll());
				if(rsp.getCode() == 0 && rsp.getData().getItems() != null) {
					for(FeishuDepts dept : rsp.getData().getItems()) {
						_logger.debug("dept : id {} , Parent {} , Name {} , od {}" ,
								 dept.getDepartment_id(),
								 dept.getParent_department_id(),
								 dept.getName(),
								 dept.getOpen_department_id()
								 );
						deptsQueue.add(dept.getOpen_department_id());
						//synchro Related
						SynchroRelated synchroRelated = 
								synchroRelatedService.findByOriginId(
										this.synchronizer,dept.getOpen_department_id(),Organizations.CLASS_TYPE );
						//Parent
						SynchroRelated synchroRelatedParent =
								synchroRelatedService.findByOriginId(
										this.synchronizer,dept.getParent_department_id(),Organizations.CLASS_TYPE);
						Organizations organization = buildOrganizationByFieldMap(dept,synchroRelatedParent);
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
			}
		} catch (NoSuchElementException e) {
			_logger.debug("Sync Department successful .");
		}
		
	}
	
	public FeishuDeptsResponse requestDepartmentList(String access_token,String deptId) {
		HttpRequestAdapter request =new HttpRequestAdapter();
		HashMap<String,String> headers =new HashMap<String,String>();
		headers.put("Authorization", AuthorizationHeaderUtils.createBearer(access_token));
		String responseBody = request.get(String.format(DEPTS_URL, deptId),headers);
		FeishuDeptsResponse deptsResponse  =JsonUtils.gsonStringToObject(responseBody, FeishuDeptsResponse.class);
		
		_logger.trace("response : " + responseBody);

		return deptsResponse;
	}
	
	public FeishuDeptsResponse requestDepartment(String url ,String deptId ,String access_token) {
		HttpRequestAdapter request =new HttpRequestAdapter();
		HashMap<String,String> headers =new HashMap<String,String>();
		headers.put("Authorization", AuthorizationHeaderUtils.createBearer(access_token));
		String responseBody = request.get(String.format(url, deptId),headers);
		FeishuDeptsResponse deptsResponse  =JsonUtils.gsonStringToObject(responseBody, FeishuDeptsResponse.class);
		
		_logger.trace("response : " + responseBody);

		return deptsResponse;
	}
	
	public SynchroRelated buildSynchroRelated(Organizations org,FeishuDepts dept) {
		return  new SynchroRelated(
				org.getId(),
				org.getOrgName(),
				org.getOrgName(),
				Organizations.CLASS_TYPE,
				synchronizer.getId(),
				synchronizer.getName(),
				dept.getOpen_department_id(),
				dept.getName(),
				dept.getDepartment_id(),
				dept.getParent_department_id(),
				synchronizer.getInstId());
	}
	
	public Organizations buildOrganization(FeishuDepts dept,SynchroRelated synchroRelatedParent) {

		
		Organizations org = new Organizations();
		org.setOrgCode(dept.getDepartment_id()+"");
		org.setOrgName(dept.getName());
		org.setFullName(dept.getName());
		org.setParentId(synchroRelatedParent.getObjectId());
		org.setParentName(synchroRelatedParent.getObjectName());
		org.setSortIndex(Integer.parseInt(dept.getOrder()));
		org.setInstId(this.synchronizer.getInstId());
		org.setStatus(ConstsStatus.ACTIVE);
		org.setDescription("Feishu");
		return org;
	}

	public Organizations buildOrganizationByFieldMap(FeishuDepts dept,SynchroRelated synchroRelatedParent){
		Map<String, String> fieldMap = getFiledMap(Long.parseLong(synchronizer.getId()));
		Organizations org = new Organizations();
		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			String   orgProperty = entry.getKey();
			String sourceProperty = entry.getValue();
			try {
				Object sourceValue = null;

				if (hasField(dept.getClass(), sourceProperty)) {
					sourceValue = getFieldValue(dept, sourceProperty);
				}
				else if (synchroRelatedParent != null && hasField(SynchroRelated.class, sourceProperty)) {
					sourceValue = getFieldValue(synchroRelatedParent, sourceProperty);
				}
				if (sourceValue != null) {
					setFieldValue(org, orgProperty, sourceValue);
				}
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		// 额外处理特定逻辑:意味着这些属性不能出现在属性映射表中
		try {
            /*if (synchroRelatedParent != null) {
                setFieldValue(org, "parentId", synchroRelatedParent.getObjectId());
                setFieldValue(org, "parentName", synchroRelatedParent.getObjectName());
            }*/
			setFieldValue(org, "instId", this.synchronizer.getInstId());
			setFieldValue(org, "status", ConstsStatus.ACTIVE);
			setFieldValue(org, "description", "Feishu");
			org.setType("department");
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return org;
	}

	public Map<String,String> getFiledMap(Long jobId){
		//key是maxkey的属性，value是其他应用的属性
		Map<String,String> filedMap = new HashMap<>();
		//根据job id查询属性映射表
		List<SyncJobConfigField> syncJobConfigFieldList = syncJobConfigFieldService.findByJobId(jobId);
		//获取组织属性映射
		for(SyncJobConfigField element:syncJobConfigFieldList){
			if(Integer.parseInt(element.getObjectType()) == ORG_TYPE.intValue()){
				filedMap.put(element.getTargetField(), element.getSourceField());
			}
		}
		return filedMap;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public SyncJobConfigFieldService getSyncJobConfigFieldService() {
		return syncJobConfigFieldService;
	}

	public void setSyncJobConfigFieldService(SyncJobConfigFieldService syncJobConfigFieldService) {
		this.syncJobConfigFieldService = syncJobConfigFieldService;
	}

}
