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
 

package org.maxkey.synchronizer.feishu;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.Organizations;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.maxkey.synchronizer.feishu.entity.FeishuDepts;
import org.maxkey.synchronizer.feishu.entity.FeishuDeptsResponse;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeishuOrganizationService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(FeishuOrganizationService.class);
	
	String access_token;
	
	static String DEPTS_URL="https://open.feishu.cn/open-apis/contact/v3/departments/%s/children?page_size=50";
	
	public void sync() {
		_logger.info("Sync Feishu Organizations ...");

		LinkedBlockingQueue<String> deptsQueue = new LinkedBlockingQueue<String>();
		deptsQueue.add("0");
		HashMap<String,FeishuDepts> deptMap = new HashMap<String,FeishuDepts>();
		try {
			while(deptsQueue.element() != null) {
				FeishuDeptsResponse rsp = requestDepartmentList(access_token,deptsQueue.poll());
				
				for(FeishuDepts dept : rsp.getData().getItems()) {
					_logger.info("dept : " + dept.getDepartment_id()+" "+ dept.getName()+" "+ dept.getParent_department_id());
					deptsQueue.add(dept.getDepartment_id());
					deptMap.put(dept.getDepartment_id(), dept);
					Organizations organization = buildOrganization(dept,deptMap.get(dept.getParent_department_id()));
					organizationsService.saveOrUpdate(organization);
					_logger.info("Organizations : " + organization);
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
		FeishuDeptsResponse deptsResponse  =JsonUtils.gson2Object(responseBody, FeishuDeptsResponse.class);
		
		_logger.info("response : " + responseBody);

		return deptsResponse;
	}
	
	public Organizations buildOrganization(FeishuDepts dept,FeishuDepts parentDept) {
		Organizations org = new Organizations();
		org.setId(dept.getDepartment_id()+"");
		org.setName(dept.getName());
		if(parentDept == null) {
			org.setParentId("1");
		}else {
			org.setParentId(dept.getParent_department_id()+"");
		}
		org.setSortIndex(Integer.parseInt(dept.getOrder()));
		org.setInstId(this.synchronizer.getInstId());
		org.setStatus(ConstsStatus.ACTIVE);
		org.setDescription("Feishu");
		return org;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
