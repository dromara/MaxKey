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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.entity.UserInfo;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse.DeptBaseResponse;
import com.dingtalk.api.response.OapiV2UserListResponse.ListUserResponse;

@Service
public class DingdingUsersService  extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(DingdingUsersService.class);
	
	@Autowired
	DingdingOrganizationService dingdingOrganizationService;
	
	String access_token;
	
	public void sync() {
		_logger.info("Sync Users...");
		try {
			
			OapiV2DepartmentListsubResponse rspDepts = dingdingOrganizationService.getRspDepts();
			for(DeptBaseResponse dept : rspDepts.getResult()) {
				DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
				OapiV2UserListRequest req = new OapiV2UserListRequest();
				req.setDeptId(dept.getDeptId());
				req.setCursor(0L);
				req.setSize(100L);
				req.setOrderField("modify_desc");
				req.setContainAccessLimit(true);
				req.setLanguage("zh_CN");
				OapiV2UserListResponse rsp = client.execute(req, access_token);
				_logger.info("response : " + rsp.getBody());
				
				if(rsp.getErrcode()==0) {
					for(ListUserResponse user :rsp.getResult().getList()) {
						_logger.info("name : " + user.getName()+" , "+user.getLoginId()+" , "+user.getUserid());
						UserInfo userInfo  = buildUserInfo(user);
						_logger.info("userInfo " + userInfo);
					}
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void postSync(UserInfo userInfo) {
		
	}

	public UserInfo buildUserInfo(ListUserResponse user) {
		UserInfo userInfo = new  UserInfo();

		userInfo.setUsername(user.getUserid());//鐧诲綍鍚�
		userInfo.setNickName(user.getName());//鐢ㄦ埛鍚�
		userInfo.setDisplayName(user.getName());//鐢ㄦ埛鍚�
		userInfo.setFormattedName(user.getName());//鐢ㄦ埛鍚�
		
		userInfo.setEmail(user.getEmail());
		userInfo.setEntryDate(new DateTime(user.getHiredDate()).toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		userInfo.setMobile(user.getMobile());//鎵嬫満
		userInfo.setDepartmentId(user.getDeptIdList().get(0)+"");
		userInfo.setJobTitle(user.getTitle());//鑱屽姟
		userInfo.setWorkEmail(user.getOrgEmail());//宸ヤ綔閭欢
		userInfo.setWorkPhoneNumber(user.getTelephone());//鍏徃鐢佃瘽
		userInfo.setWorkOfficeName(user.getWorkPlace());//鍔炲叕瀹�
		userInfo.setDescription(user.getRemark());//澶囨敞
		userInfo.setInstId(this.synchronizer.getInstId());
		return userInfo;
	}



	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public void setDingdingOrganizationService(DingdingOrganizationService dingdingOrganizationService) {
		this.dingdingOrganizationService = dingdingOrganizationService;
	}


}
