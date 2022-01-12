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
 

package org.maxkey.synchronizer.dingding;

import org.maxkey.entity.Organizations;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse.DeptBaseResponse;
import com.taobao.api.ApiException;

@Service
public class DingdingOrganizationService  extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(DingdingOrganizationService.class);
	
	OapiV2DepartmentListsubResponse rspDepts;
	
	String access_token;
	
	public void sync() {
		_logger.info("Sync Organizations ...");
	
		try {			
			OapiV2DepartmentListsubResponse rsp = requestDepartmentList(access_token);
			
			for(DeptBaseResponse dept : rsp.getResult()) {
				_logger.info("dept : " + dept.getDeptId()+" "+ dept.getName()+" "+ dept.getParentId());
				Organizations org = buildOrganization(dept);
				this.organizationsService.merge(org);
				_logger.info("Organizations : " + org);
			}

		} catch (ApiException e) {
			e.printStackTrace();
		}
		
	}
	
	public OapiV2DepartmentListsubResponse requestDepartmentList(String access_token) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
		OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
		req.setDeptId(1L);
		req.setLanguage("zh_CN");
		rspDepts = client.execute(req, access_token);
		_logger.info("response : " + rspDepts.getBody());
		return rspDepts;
	}
	
	
	
	public Organizations buildOrganization(DeptBaseResponse dept) {
		Organizations org = new Organizations();
		org.setId(dept.getDeptId()+"");
		org.setName(dept.getName());
		org.setParentId(dept.getParentId()+"");
		org.setInstId(this.synchronizer.getInstId());
		return org;
	}



	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public OapiV2DepartmentListsubResponse getRspDepts() {
		return rspDepts;
	}
	
	
}
