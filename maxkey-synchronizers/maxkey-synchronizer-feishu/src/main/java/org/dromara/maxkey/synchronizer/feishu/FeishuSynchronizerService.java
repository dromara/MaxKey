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

import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeishuSynchronizerService  implements ISynchronizerService{
	static final  Logger _logger = LoggerFactory.getLogger(FeishuSynchronizerService.class);
	Synchronizers synchronizer;
	
	@Autowired
	FeishuUsersService feishuUsersService;
	
	@Autowired
	FeishuOrganizationService feishuOrganizationService;
	

	FeishuAccessTokenService feishuAccessTokenService = new FeishuAccessTokenService();
	
	public FeishuSynchronizerService() {
		super();
	}

	public void sync() throws Exception {
		_logger.info("Sync ...");
		feishuAccessTokenService.setAppId(synchronizer.getPrincipal());
		feishuAccessTokenService.setAppSecret(synchronizer.getCredentials());
		String access_token=feishuAccessTokenService.requestToken();
		
		feishuOrganizationService.setSynchronizer(synchronizer);
		feishuOrganizationService.setAccess_token(access_token);
		feishuOrganizationService.sync();
		
		feishuUsersService.setSynchronizer(synchronizer);
		feishuUsersService.setAccess_token(access_token);
		feishuUsersService.sync();
	}


	public void setFeishuUsersService(FeishuUsersService feishuUsersService) {
		this.feishuUsersService = feishuUsersService;
	}

	public void setFeishuOrganizationService(FeishuOrganizationService feishuOrganizationService) {
		this.feishuOrganizationService = feishuOrganizationService;
	}

	@Override
	public void setSynchronizer(Synchronizers synchronizer) {
		this.synchronizer = synchronizer;
		
	}

}
