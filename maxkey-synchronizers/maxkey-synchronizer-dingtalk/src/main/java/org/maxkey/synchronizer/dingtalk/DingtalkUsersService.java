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
 

package org.maxkey.synchronizer.dingtalk;

import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.Organizations;
import org.maxkey.entity.UserInfo;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.dingtalk.api.response.OapiV2UserListResponse.ListUserResponse;

@Service
public class DingtalkUsersService  extends AbstractSynchronizerService implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(DingtalkUsersService.class);
	
	String access_token;
	
	public void sync() {
		_logger.info("Sync Dingtalk Users...");
		try {
			
			 List<Organizations> organizations = 
					 organizationsService.find("instid = ?",
									 		new Object[] { this.synchronizer.getInstId() },
					                        new int[] { Types.VARCHAR});
			 
			for(Organizations dept : organizations) {
				DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
				OapiV2UserListRequest req = new OapiV2UserListRequest();
				_logger.info("DingTalk deptId : {}" , dept.getCode());
				req.setDeptId(Long.parseLong(dept.getCode()));
				req.setCursor(0L);
				req.setSize(100L);
				req.setOrderField("modify_desc");
				req.setContainAccessLimit(true);
				req.setLanguage("zh_CN");
				OapiV2UserListResponse rsp = client.execute(req, access_token);
				_logger.trace("response : {}" , rsp.getBody());
				
				if(rsp.getErrcode()==0) {
					for(ListUserResponse user :rsp.getResult().getList()) {
						_logger.debug("name : {} , {} , {}", user.getName(),user.getLoginId(),user.getUserid());
						UserInfo userInfo  = buildUserInfo(user);
						_logger.trace("userInfo {}" , userInfo);
						userInfo.setPassword(userInfo.getUsername() + "Maxkey@888");
						userInfoService.saveOrUpdate(userInfo);
					}
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public UserInfo buildUserInfo(ListUserResponse user) {
		UserInfo userInfo = new  UserInfo();

		userInfo.setUsername(user.getUserid());//閻ц缍嶉崥锟�
		userInfo.setNickName(user.getName());//閻€劍鍩涢崥锟�
		userInfo.setDisplayName(user.getName());//閻€劍鍩涢崥锟�
		userInfo.setFormattedName(user.getName());//閻€劍鍩涢崥锟�
		
		userInfo.setEmail(StringUtils.isBlank(user.getEmail())? user.getUserid() +"@maxkey.top":user.getEmail());
		userInfo.setEntryDate(new DateTime(user.getHiredDate()).toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		userInfo.setMobile(user.getMobile());//閹靛婧�
		
		userInfo.setDepartmentId(user.getDeptIdList().get(0)+"");
		userInfo.setEmployeeNumber(user.getJobNumber());
		userInfo.setJobTitle(user.getTitle());//閼卞苯濮�
		userInfo.setWorkEmail(user.getOrgEmail());//瀹搞儰缍旈柇顔绘
		userInfo.setWorkPhoneNumber(user.getTelephone());//閸忣剙寰冮悽浣冪樈
		userInfo.setWorkOfficeName(user.getWorkPlace());//閸旂偛鍙曠�癸拷
		if(user.getActive()) {
			userInfo.setStatus(ConstsStatus.ACTIVE);
		}else {
			userInfo.setStatus(ConstsStatus.INACTIVE);
		}
		
		userInfo.setInstId(this.synchronizer.getInstId());
		userInfo.setDescription("dingtalk "+user.getRemark());
		return userInfo;
	}



	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
