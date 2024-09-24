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
 

package org.dromara.maxkey.synchronizer.dingtalk;

import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;

@Service
public class DingtalkSynchronizerService  implements ISynchronizerService{
	static final  Logger _logger = LoggerFactory.getLogger(DingtalkSynchronizerService.class);
	Synchronizers synchronizer;
	
	@Autowired
	DingtalkUsersService dingtalkUsersService;
	
	@Autowired
	DingtalkOrganizationService dingtalkOrganizationService;
	

	DingtalkAccessTokenService dingtalkAccessTokenService = new DingtalkAccessTokenService();
	
	public DingtalkSynchronizerService() {
		super();
	}

	public void sync() throws ApiException {
		_logger.info("Sync ...");
		dingtalkAccessTokenService.setAppkey(synchronizer.getPrincipal());
		dingtalkAccessTokenService.setAppsecret(synchronizer.getCredentials());
		String access_token=dingtalkAccessTokenService.requestToken();
		
		dingtalkOrganizationService.setSynchronizer(synchronizer);
		dingtalkOrganizationService.setAccess_token(access_token);
		dingtalkOrganizationService.sync();
		
		dingtalkUsersService.setSynchronizer(synchronizer);
		dingtalkUsersService.setAccess_token(access_token);
		dingtalkUsersService.sync();
	}

	public DingtalkUsersService getDingtalkUsersService() {
		return dingtalkUsersService;
	}

	public void setDingtalkUsersService(DingtalkUsersService dingtalkUsersService) {
		this.dingtalkUsersService = dingtalkUsersService;
	}

	public DingtalkOrganizationService getDingtalkOrganizationService() {
		return dingtalkOrganizationService;
	}

	public void setDingtalkOrganizationService(DingtalkOrganizationService dingtalkOrganizationService) {
		this.dingtalkOrganizationService = dingtalkOrganizationService;
	}

	public Synchronizers getSynchronizer() {
		return synchronizer;
	}


	public DingtalkAccessTokenService getDingtalkAccessTokenService() {
		return dingtalkAccessTokenService;
	}

	public void setDingtalkAccessTokenService(DingtalkAccessTokenService dingtalkAccessTokenService) {
		this.dingtalkAccessTokenService = dingtalkAccessTokenService;
	}

	@Override
	public void setSynchronizer(Synchronizers synchronizer) {
		this.synchronizer = synchronizer;
		
	}

}
