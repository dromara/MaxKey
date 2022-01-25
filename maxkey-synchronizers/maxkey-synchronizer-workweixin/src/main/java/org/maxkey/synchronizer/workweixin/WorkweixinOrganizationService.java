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
 

package org.maxkey.synchronizer.workweixin;

import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.Organizations;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.maxkey.synchronizer.workweixin.entity.WorkWeixinDepts;
import org.maxkey.synchronizer.workweixin.entity.WorkWeixinDeptsResponse;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WorkweixinOrganizationService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(WorkweixinOrganizationService.class);
	
	String access_token;
	
	static String DEPTS_URL="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=%s";
	
	public void sync() {
		_logger.info("Sync Workweixin Organizations ...");

		try {			
			WorkWeixinDeptsResponse rsp = requestDepartmentList(access_token);
			
			for(WorkWeixinDepts dept : rsp.getDepartment()) {
				_logger.info("dept : " + dept.getId()+" "+ dept.getName()+" "+ dept.getParentid());
				Organizations organization = buildOrganization(dept);
				organizationsService.saveOrUpdate(organization);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public WorkWeixinDeptsResponse requestDepartmentList(String access_token) {
		HttpRequestAdapter request =new HttpRequestAdapter();
		String responseBody = request.get(String.format(DEPTS_URL, access_token));
		WorkWeixinDeptsResponse deptsResponse  =JsonUtils.gson2Object(responseBody, WorkWeixinDeptsResponse.class);
		
		_logger.info("response : " + responseBody);
		for(WorkWeixinDepts dept : deptsResponse.getDepartment()) {
			_logger.info("WorkWeixinDepts : " + dept);
		}
		return deptsResponse;
	}
	
	public Organizations buildOrganization(WorkWeixinDepts dept) {
		Organizations org = new Organizations();
		org.setId(dept.getId()+"");
		org.setName(dept.getName());
		org.setParentId(dept.getParentid()+"");
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
