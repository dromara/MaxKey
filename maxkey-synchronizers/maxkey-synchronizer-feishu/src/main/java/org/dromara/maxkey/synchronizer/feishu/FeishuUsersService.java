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

import java.util.HashMap;
import java.util.List;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.synchronizer.feishu.entity.FeishuUsers;
import org.dromara.maxkey.synchronizer.feishu.entity.FeishuUsersResponse;
import org.dromara.maxkey.util.AuthorizationHeaderUtils;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeishuUsersService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(FeishuUsersService.class);
	
	String access_token;
	
	static String USERS_URL="https://open.feishu.cn/open-apis/contact/v3/users/find_by_department?department_id=%s&page_size=50";
	
	public void sync() {
		_logger.info("Sync Feishu Users...");
		try {
			List<SynchroRelated> synchroRelateds = 
					synchroRelatedService.findOrgs(this.synchronizer);
					
			for(SynchroRelated relatedOrg : synchroRelateds) {
				HttpRequestAdapter request =new HttpRequestAdapter();
				HashMap<String,String> headers =new HashMap<String,String>();
				headers.put("Authorization", AuthorizationHeaderUtils.createBearer(access_token));
				String responseBody = request.get(String.format(USERS_URL,relatedOrg.getOriginId()),headers);
				FeishuUsersResponse usersResponse  =JsonUtils.gsonStringToObject(responseBody, FeishuUsersResponse.class);
				_logger.trace("response : " + responseBody);
				if(usersResponse.getCode() == 0 && usersResponse.getData().getItems() != null) {
					for(FeishuUsers feiShuUser : usersResponse.getData().getItems()) {
						UserInfo userInfo  = buildUserInfo(feiShuUser,relatedOrg);
						_logger.debug("userInfo : " + userInfo);
						userInfo.setPassword(userInfo.getUsername() + UserInfo.DEFAULT_PASSWORD_SUFFIX);
						userInfoService.saveOrUpdate(userInfo);
						
						SynchroRelated synchroRelated = new SynchroRelated(
								userInfo.getId(),
								userInfo.getUsername(),
								userInfo.getDisplayName(),
								UserInfo.CLASS_TYPE,
								synchronizer.getId(),
								synchronizer.getName(),
								feiShuUser.getOpen_id(),
								feiShuUser.getName(),
								feiShuUser.getUser_id(),
								feiShuUser.getUnion_id(),
								synchronizer.getInstId());
						synchroRelatedService.updateSynchroRelated(
								this.synchronizer,synchroRelated,UserInfo.CLASS_TYPE);
						
						synchroRelated.setOriginId(feiShuUser.getUnion_id());
						socialsAssociate(synchroRelated,"feishu");
						
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void postSync(UserInfo userInfo) {
		
	}

	public UserInfo buildUserInfo(FeishuUsers user,SynchroRelated relatedOrg) {
		UserInfo userInfo = new  UserInfo();
		userInfo.setId(userInfo.generateId());
		userInfo.setUsername(user.getUser_id());//账号
		userInfo.setNickName(user.getNickname());//名字
		userInfo.setDisplayName(user.getName());//名字
		
		userInfo.setMobile(user.getMobile());//手机
		userInfo.setEmail(user.getEmail());
		userInfo.setGender(user.getGender());
		
		userInfo.setEmployeeNumber(user.getEmployee_no());
		userInfo.setWorkPhoneNumber(user.getMobile());//工作电话
		
		userInfo.setDepartmentId(relatedOrg.getObjectId());
		userInfo.setDepartment(relatedOrg.getObjectName());
		
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
