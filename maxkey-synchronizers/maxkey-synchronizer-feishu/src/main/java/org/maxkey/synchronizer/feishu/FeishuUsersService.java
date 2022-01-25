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

import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.Organizations;
import org.maxkey.entity.UserInfo;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.maxkey.synchronizer.feishu.entity.FeishuUsers;
import org.maxkey.synchronizer.feishu.entity.FeishuUsersResponse;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeishuUsersService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(FeishuUsersService.class);
	
	String access_token;
	
	static String USERS_URL="https://open.feishu.cn/open-apis/contact/v3/users/find_by_department";
	
	public void sync() {
		_logger.info("Sync Feishu Users...");
		try {
			List<Organizations> organizations = 
					 organizationsService.find("instid = ?",
									 		new Object[] { this.synchronizer.getInstId() },
					                        new int[] { Types.VARCHAR});
			for(Organizations dept : organizations) {
				HttpRequestAdapter request =new HttpRequestAdapter();
				HashMap<String,String> headers =new HashMap<String,String>();
				headers.put("Authorization", AuthorizationHeaderUtils.createBearer(access_token));
				String responseBody = request.get(String.format(USERS_URL, access_token,dept.getId()),headers);
				FeishuUsersResponse usersResponse  =JsonUtils.gson2Object(responseBody, FeishuUsersResponse.class);
				_logger.info("response : " + responseBody);
				
				for(FeishuUsers user : usersResponse.getData().getItems()) {
					UserInfo userInfo  = buildUserInfo(user);
					_logger.info("userInfo : " + userInfo);
					userInfo.setPassword(userInfo.getUsername() + "Maxkey@888");
					userInfoService.saveOrUpdate(userInfo);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void postSync(UserInfo userInfo) {
		
	}

	public UserInfo buildUserInfo(FeishuUsers user) {
		UserInfo userInfo = new  UserInfo();
		userInfo.setUsername(user.getUser_id());//账号
		userInfo.setNickName(user.getNickname());//名字
		userInfo.setDisplayName(user.getName());//名字
		
		userInfo.setMobile(user.getMobile());//手机
		userInfo.setEmail(user.getEmail());
		userInfo.setGender(user.getGender());
		
		userInfo.setEmployeeNumber(user.getEmployee_no());
		userInfo.setWorkPhoneNumber(user.getMobile());//工作电话
		userInfo.setDepartmentId(user.getDepartment_ids()[0]+"");
		userInfo.setJobTitle(user.getJob_title());//职务
		userInfo.setWorkAddressFormatted(user.getWork_station());//工作地点

		//激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
		if(user.getStatus().isIs_activated() ) {
			userInfo.setStatus(ConstsStatus.ACTIVE);
		}else {
			userInfo.setStatus(ConstsStatus.INACTIVE);
		}
		userInfo.setInstId(this.synchronizer.getInstId());
		return userInfo;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
}
