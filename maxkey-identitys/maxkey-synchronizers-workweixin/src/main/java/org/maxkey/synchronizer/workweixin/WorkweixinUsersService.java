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

import org.maxkey.entity.UserInfo;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.maxkey.synchronizer.workweixin.entity.WorkWeixinDepts;
import org.maxkey.synchronizer.workweixin.entity.WorkWeixinUsers;
import org.maxkey.synchronizer.workweixin.entity.WorkWeixinUsersResponse;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkweixinUsersService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(WorkweixinUsersService.class);
	
	@Autowired
	WorkweixinOrganizationService workweixinOrganizationService;
	
	String access_token;
	
	static String USERS_URL="https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=%s&department_id=%s&fetch_child=0";
	
	public void sync() {
		_logger.info("Sync Users...");
		try {
			
			for (WorkWeixinDepts dept : workweixinOrganizationService.getDeptsResponse().getDepartment()) {
				HttpRequestAdapter request =new HttpRequestAdapter();
				String responseBody = request.get(String.format(USERS_URL, access_token,dept.getId()));
				WorkWeixinUsersResponse usersResponse  =JsonUtils.gson2Object(responseBody, WorkWeixinUsersResponse.class);
				_logger.info("response : " + responseBody);
				
				for(WorkWeixinUsers user : usersResponse.getUserlist()) {
					UserInfo userInfo  = buildUserInfo(user);
					_logger.info("userInfo : " + userInfo);
					this.userInfoService.merge(userInfo);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void postSync(UserInfo userInfo) {
		
	}

	public UserInfo buildUserInfo(WorkWeixinUsers user) {
		UserInfo userInfo = new  UserInfo();
		userInfo.setUsername(user.getUserid());//账号
		userInfo.setNickName(user.getAlias());//名字
		userInfo.setDisplayName(user.getName());//名字
		
		userInfo.setMobile(user.getMobile());//手机
		userInfo.setEmail(user.getEmail());
		userInfo.setGender(Integer.parseInt(user.getGender()));
		
		userInfo.setWorkPhoneNumber(user.getTelephone());//工作电话
		userInfo.setDepartmentId(user.getMain_department()+"");
		userInfo.setJobTitle(user.getPosition());//职务
		userInfo.setWorkAddressFormatted(user.getAddress());//工作地点

		//激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
		userInfo.setStatus(user.getStatus());
		userInfo.setInstId(this.synchronizer.getInstId());
		return userInfo;
	}


	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public void setWorkweixinOrganizationService(WorkweixinOrganizationService workweixinOrganizationService) {
		this.workweixinOrganizationService = workweixinOrganizationService;
	}

}
