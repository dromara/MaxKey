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
 

package org.maxkey.authz.oauth2.provider.userinfo.endpoint;

import java.util.HashMap;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.entity.UserInfo;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringGenerator;
import org.maxkey.web.WebConstants;
import org.springframework.web.servlet.ModelAndView;

public class OAuthDefaultUserInfoAdapter extends AbstractAuthorizeAdapter {

	@Override
	public String generateInfo(SigninPrincipal authentication,UserInfo userInfo,Object app) {
		HashMap<String, Object> beanMap = new HashMap<String, Object>();
		beanMap.put("randomId",(new StringGenerator()).uuidGenerate());
		beanMap.put("userId", userInfo.getId());
		//for spring security oauth2
		beanMap.put("user", userInfo.getUsername());
		beanMap.put("username", userInfo.getUsername());
		beanMap.put("employeeNumber", userInfo.getEmployeeNumber());
		beanMap.put("email", userInfo.getEmail());
		beanMap.put("mobile", userInfo.getMobile());
		beanMap.put("realname", userInfo.getDisplayName());
		beanMap.put("birthday", userInfo.getBirthDate());
		beanMap.put("department", userInfo.getDepartment());
		beanMap.put("createdate", userInfo.getCreatedDate());
		beanMap.put("title", userInfo.getJobTitle());
		beanMap.put("state", userInfo.getWorkRegion());
		beanMap.put("gender", userInfo.getGender());
		beanMap.put(WebConstants.ONLINE_TICKET_NAME, authentication.getOnlineTicket().getTicketId());
		
		String info= JsonUtils.object2Json(beanMap);
		
		return info;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		// TODO Auto-generated method stub
		return null;
	}

}
