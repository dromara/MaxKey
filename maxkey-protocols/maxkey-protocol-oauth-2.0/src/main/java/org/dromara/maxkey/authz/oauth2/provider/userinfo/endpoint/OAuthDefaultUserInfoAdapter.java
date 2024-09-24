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
 

package org.dromara.maxkey.authz.oauth2.provider.userinfo.endpoint;

import java.util.HashMap;

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.util.StringGenerator;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthDefaultUserInfoAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(OAuthDefaultUserInfoAdapter.class);
	ClientDetails clientDetails;
	
	public OAuthDefaultUserInfoAdapter() {}

	public OAuthDefaultUserInfoAdapter(ClientDetails clientDetails) {
		this.clientDetails = clientDetails;
	}

	@Override
	public Object generateInfo() {
		 String subject = AbstractAuthorizeAdapter.getValueByUserAttr(userInfo, clientDetails.getSubject());
		 _logger.debug("userId : {} , username : {} , displayName : {} , subject : {}" , 
				 userInfo.getId(),
				 userInfo.getUsername(),
				 userInfo.getDisplayName(),
				 subject);
		 
		HashMap<String, Object> beanMap = new HashMap<String, Object>();
		beanMap.put("randomId",(new StringGenerator()).uuidGenerate());
		beanMap.put("userId", userInfo.getId());
		//for spring security oauth2
		beanMap.put("user", subject);
		beanMap.put("username", subject);
		
		beanMap.put("displayName", userInfo.getDisplayName());
		beanMap.put("employeeNumber", userInfo.getEmployeeNumber());
		beanMap.put("email", userInfo.getEmail());
		beanMap.put("mobile", userInfo.getMobile());
		beanMap.put("realname", userInfo.getDisplayName());
		beanMap.put("birthday", userInfo.getBirthDate());
		beanMap.put("departmentId", userInfo.getDepartmentId());
		beanMap.put("department", userInfo.getDepartment());
		beanMap.put("createdate", userInfo.getCreatedDate());
		beanMap.put("title", userInfo.getJobTitle());
		beanMap.put("state", userInfo.getWorkRegion());
		beanMap.put("gender", userInfo.getGender());
		beanMap.put("institution", userInfo.getInstId());
		beanMap.put(WebConstants.ONLINE_TICKET_NAME, principal.getSessionId());
		
		String info= JsonUtils.toString(beanMap);
		
		return info;
	}

	public ClientDetails getClientDetails() {
		return clientDetails;
	}

	public void setClientDetails(ClientDetails clientDetails) {
		this.clientDetails = clientDetails;
	}
}
