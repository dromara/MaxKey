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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.synchronizer.feishu.entity.FeishuUsers;
import org.dromara.maxkey.synchronizer.feishu.entity.FeishuUsersResponse;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.dromara.maxkey.util.AuthorizationHeaderUtils;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static org.dromara.maxkey.synchronizer.utils.FieldUtil.*;

@Service
public class FeishuUsersService extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(FeishuUsersService.class);
	@Autowired
	private SyncJobConfigFieldService syncJobConfigFieldService;
	String access_token;
	private static final Integer USER_TYPE = 1;
	
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
						UserInfo userInfo  = buildUserInfoByFieldMapper(feiShuUser,relatedOrg);
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

	public UserInfo buildUserInfoByFieldMapper(FeishuUsers user,SynchroRelated relatedOrg){
		UserInfo userInfo = new  UserInfo();
		Map<String, String> fieldMap = this.getFiledMap(Long.parseLong(synchronizer.getId()));
		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {

			String userInfoProperty = entry.getKey();
			String sourceProperty = entry.getValue();

			try {
				Object sourceValue = null;
				if(sourceProperty.equals("status")){
					if (user.getStatus().isIs_activated()) {
						setFieldValue(userInfo, "status", ConstsStatus.ACTIVE);
					} else {
						setFieldValue(userInfo, "status", ConstsStatus.INACTIVE);
					}
					continue;
				}
				if (hasField(user.getClass(), sourceProperty)) {
					sourceValue = getFieldValue(user, sourceProperty);
				}
				else if (hasField(SynchroRelated.class, sourceProperty)) {
					sourceValue = getFieldValue(relatedOrg, sourceProperty);
				}

				if (sourceValue != null) {
					setFieldValue(userInfo, userInfoProperty, sourceValue);
				}

			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		// 额外处理特定逻辑 ：意味着这些属性映射不能保存在数据库中。
		try {
			if(userInfo.getUsername() == null){
				userInfo.setUsername(user.getOpen_id());
			}
			setFieldValue(userInfo, "id", userInfo.generateId());
			setFieldValue(userInfo, "instId", this.synchronizer.getInstId());
			userInfo.setUserType("EMPLOYEE");
			userInfo.setUserState("RESIDENT");
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	public Map<String,String> getFiledMap(Long jobId){
		Map<String,String> fieldMap = new HashMap<>();
		//根据job id查询属性映射表
		List<SyncJobConfigField> syncJobConfigFieldList = syncJobConfigFieldService.findByJobId(jobId);
		//获取用户属性映射
		for(SyncJobConfigField element:syncJobConfigFieldList){
			if(Integer.parseInt(element.getObjectType()) == USER_TYPE.intValue()){
				fieldMap.put(element.getTargetField(), element.getSourceField());
			}
		}
		return fieldMap;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public SyncJobConfigFieldService getSyncJobConfigFieldService() {
		return syncJobConfigFieldService;
	}

	public void setSyncJobConfigFieldService(SyncJobConfigFieldService syncJobConfigFieldService) {
		this.syncJobConfigFieldService = syncJobConfigFieldService;
	}


}
