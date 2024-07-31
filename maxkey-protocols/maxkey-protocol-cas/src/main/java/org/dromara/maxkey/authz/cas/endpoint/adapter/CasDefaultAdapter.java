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
 

package org.dromara.maxkey.authz.cas.endpoint.adapter;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.dromara.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.entity.apps.AppsCasDetails;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class CasDefaultAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(CasDefaultAdapter.class);
	
	static String Charset_UTF8="UTF-8";
	
	ServiceResponseBuilder serviceResponseBuilder;
	
	@Override
	public ModelAndView authorize(ModelAndView modelAndView) {

		return modelAndView;
	}

	public String base64Attr(String attrValue){
		String b64="";
		try {
			b64=(attrValue == null? "":"base64:"+Base64.encodeBase64String(attrValue.getBytes(Charset_UTF8)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return b64;
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
		serviceResponseBuilder.setAttribute("displayName", base64Attr(userInfo.getDisplayName()));
		serviceResponseBuilder.setAttribute("firstName", base64Attr(userInfo.getGivenName()));
		serviceResponseBuilder.setAttribute("lastname", base64Attr(userInfo.getFamilyName()));
		serviceResponseBuilder.setAttribute("mobile", userInfo.getMobile());
		serviceResponseBuilder.setAttribute("birthday", userInfo.getBirthDate());
		serviceResponseBuilder.setAttribute("gender", userInfo.getGender()+"");
		
		//for work
		serviceResponseBuilder.setAttribute("employeeNumber", userInfo.getEmployeeNumber());
		serviceResponseBuilder.setAttribute("title", base64Attr(userInfo.getJobTitle()));
		serviceResponseBuilder.setAttribute("email", userInfo.getWorkEmail());
		serviceResponseBuilder.setAttribute("department", base64Attr(userInfo.getDepartment()));
		serviceResponseBuilder.setAttribute("departmentId", userInfo.getDepartmentId());
		serviceResponseBuilder.setAttribute("workRegion",base64Attr(userInfo.getWorkRegion()));
		serviceResponseBuilder.setAttribute("institution", userInfo.getInstId());
		serviceResponseBuilder.setAttribute(WebConstants.ONLINE_TICKET_NAME,principal.getSessionId());
	
		return serviceResponseBuilder;
	}

	public void setServiceResponseBuilder(ServiceResponseBuilder serviceResponseBuilder) {
		this.serviceResponseBuilder = serviceResponseBuilder;
	}

}
