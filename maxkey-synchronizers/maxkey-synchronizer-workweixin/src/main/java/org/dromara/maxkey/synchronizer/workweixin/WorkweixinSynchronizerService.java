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

import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkweixinSynchronizerService  implements ISynchronizerService{
	static final  Logger _logger = LoggerFactory.getLogger(WorkweixinSynchronizerService.class);
	Synchronizers synchronizer;
	
	@Autowired
	WorkweixinUsersService workweixinUsersService;
	
	@Autowired
	WorkweixinOrganizationService workweixinOrganizationService;
	

	WorkweixinAccessTokenService workweixinAccessTokenService = new WorkweixinAccessTokenService();
	
	public WorkweixinSynchronizerService() {
		super();
	}

	public void sync() throws Exception {
		_logger.info("Sync ...");
		workweixinAccessTokenService.setCorpid(synchronizer.getPrincipal());
		workweixinAccessTokenService.setCorpsecret(synchronizer.getCredentials());
		String access_token=workweixinAccessTokenService.requestToken();
		
		workweixinOrganizationService.setSynchronizer(synchronizer);
		workweixinOrganizationService.setAccess_token(access_token);
		workweixinOrganizationService.sync();
		
		workweixinUsersService.setSynchronizer(synchronizer);
		workweixinUsersService.setAccess_token(access_token);
		workweixinUsersService.sync();
	}

	public WorkweixinUsersService getWorkweixinUsersService() {
		return workweixinUsersService;
	}

	public void setWorkweixinUsersService(WorkweixinUsersService workweixinUsersService) {
		this.workweixinUsersService = workweixinUsersService;
	}

	public WorkweixinOrganizationService getWorkweixinOrganizationService() {
		return workweixinOrganizationService;
	}

	public void setWorkweixinOrganizationService(WorkweixinOrganizationService workweixinOrganizationService) {
		this.workweixinOrganizationService = workweixinOrganizationService;
	}

	public WorkweixinAccessTokenService getWorkweixinAccessTokenService() {
		return workweixinAccessTokenService;
	}

	public void setWorkweixinAccessTokenService(WorkweixinAccessTokenService workweixinAccessTokenService) {
		this.workweixinAccessTokenService = workweixinAccessTokenService;
	}

	@Override
	public void setSynchronizer(Synchronizers synchronizer) {
		this.synchronizer = synchronizer;
		
	}

}
