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
 

package org.dromara.maxkey.synchronizer.workweixin;

import java.util.List;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinUsers;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinUsersResponse;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WorkweixinUsersService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(WorkweixinUsersService.class);
	
	String access_token;
	
	static String USERS_URL="https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=%s&department_id=%s&fetch_child=0";
	
	public void sync() {
		_logger.info("Sync Workweixin Users...");
		try {
			List<SynchroRelated> synchroRelateds = 
					synchroRelatedService.findOrgs(this.synchronizer);
			
			for(SynchroRelated relatedOrg : synchroRelateds) {
				HttpRequestAdapter request =new HttpRequestAdapter();
				String responseBody = request.get(String.format(USERS_URL, access_token,relatedOrg.getOriginId()));
				WorkWeixinUsersResponse usersResponse  =JsonUtils.gsonStringToObject(responseBody, WorkWeixinUsersResponse.class);
				_logger.trace("response : " + responseBody);
				
				for(WorkWeixinUsers user : usersResponse.getUserlist()) {
					UserInfo userInfo  = buildUserInfo(user);
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
							user.getUserid(),
							user.getName(),
							user.getUserid(),
							"",
							synchronizer.getInstId());
					
					synchroRelatedService.updateSynchroRelated(
							this.synchronizer,synchroRelated,UserInfo.CLASS_TYPE);
					
					socialsAssociate(synchroRelated,"workweixin");
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
		if(user.getStatus() == 1) {
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
