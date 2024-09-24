/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authz.cas.endpoint.adapter;

import org.dromara.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.entity.apps.AppsCasDetails;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class CasPlainAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(CasPlainAdapter.class);

	ServiceResponseBuilder serviceResponseBuilder;
	
	@Override
	public ModelAndView authorize(ModelAndView modelAndView) {

		return modelAndView;
	}

	@Override
	public Object generateInfo() {
		//user for return 
		String user = getValueByUserAttr(userInfo,((AppsCasDetails)this.app).getCasUser());
		_logger.debug("cas user {}",user);
		serviceResponseBuilder.success().setUser(user);
		
		//for user
		serviceResponseBuilder.setAttribute("uid", userInfo.getId());
		serviceResponseBuilder.setAttribute("username", userInfo.getUsername());
		serviceResponseBuilder.setAttribute("displayName", userInfo.getDisplayName());
		serviceResponseBuilder.setAttribute("firstName", userInfo.getGivenName());
		serviceResponseBuilder.setAttribute("lastname", userInfo.getFamilyName());
		serviceResponseBuilder.setAttribute("mobile", userInfo.getMobile());
		serviceResponseBuilder.setAttribute("birthday", userInfo.getBirthDate());
		serviceResponseBuilder.setAttribute("gender", userInfo.getGender()+"");
		
		//for work
		serviceResponseBuilder.setAttribute("employeeNumber", userInfo.getEmployeeNumber());
		serviceResponseBuilder.setAttribute("title", userInfo.getJobTitle());
		serviceResponseBuilder.setAttribute("email", userInfo.getWorkEmail());
		serviceResponseBuilder.setAttribute("department", userInfo.getDepartment());
		serviceResponseBuilder.setAttribute("departmentId", userInfo.getDepartmentId());
		serviceResponseBuilder.setAttribute("workRegion",userInfo.getWorkRegion());
		serviceResponseBuilder.setAttribute("institution", userInfo.getInstId());
		serviceResponseBuilder.setAttribute(WebConstants.ONLINE_TICKET_NAME,principal.getSessionId());
	
		return serviceResponseBuilder;
	}

	public void setServiceResponseBuilder(ServiceResponseBuilder serviceResponseBuilder) {
		this.serviceResponseBuilder = serviceResponseBuilder;
	}

}
