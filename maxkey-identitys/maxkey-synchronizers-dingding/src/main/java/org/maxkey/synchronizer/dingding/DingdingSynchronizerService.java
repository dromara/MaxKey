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

import org.maxkey.entity.Synchronizers;
import org.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;

@Service
public class DingdingSynchronizerService  implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(DingdingSynchronizerService.class);
	Synchronizers synchronizer;
	
	@Autowired
	DingdingUsersService dingdingUsersService;
	
	@Autowired
	DingdingOrganizationService dingdingOrganizationService;
	

	DingdingAccessTokenService dingdingAccessTokenService = new DingdingAccessTokenService();
	
	public DingdingSynchronizerService() {
		super();
	}

	public void sync() throws ApiException {
		_logger.info("Sync ...");
		dingdingAccessTokenService.setAppkey(synchronizer.getPrincipal());
		dingdingAccessTokenService.setAppsecret(synchronizer.getCredentials());
		String access_token=dingdingAccessTokenService.requestToken();
		
		dingdingOrganizationService.setSynchronizer(synchronizer);
		dingdingOrganizationService.setAccess_token(access_token);
		dingdingOrganizationService.sync();
		
		dingdingUsersService.setSynchronizer(synchronizer);
		dingdingUsersService.setAccess_token(access_token);
		dingdingUsersService.sync();
	}

	public DingdingUsersService getDingdingUsersService() {
		return dingdingUsersService;
	}

	public void setDingdingUsersService(DingdingUsersService dingdingUsersService) {
		this.dingdingUsersService = dingdingUsersService;
	}

	public DingdingOrganizationService getDingdingOrganizationService() {
		return dingdingOrganizationService;
	}

	public void setDingdingOrganizationService(DingdingOrganizationService dingdingOrganizationService) {
		this.dingdingOrganizationService = dingdingOrganizationService;
	}

	public DingdingAccessTokenService getDingdingAccessTokenService() {
		return dingdingAccessTokenService;
	}

	public void setDingdingAccessTokenService(DingdingAccessTokenService dingdingAccessTokenService) {
		this.dingdingAccessTokenService = dingdingAccessTokenService;
	}

	@Override
	public void setSynchronizer(Synchronizers synchronizer) {
		this.synchronizer = synchronizer;
		
	}

}
