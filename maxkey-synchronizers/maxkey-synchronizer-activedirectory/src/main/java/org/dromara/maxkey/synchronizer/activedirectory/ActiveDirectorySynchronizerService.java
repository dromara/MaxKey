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
 

package org.dromara.maxkey.synchronizer.activedirectory;

import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.ldap.activedirectory.ActiveDirectoryUtils;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveDirectorySynchronizerService   implements ISynchronizerService{
	static final  Logger _logger = LoggerFactory.getLogger(ActiveDirectorySynchronizerService.class);
	
	Synchronizers synchronizer;
	
	@Autowired
	ActiveDirectoryUsersService activeDirectoryUsersService;
	
	@Autowired
	ActiveDirectoryOrganizationService activeDirectoryOrganizationService;
	
	public ActiveDirectorySynchronizerService() {
		super();
	}

	public void sync() {
		_logger.info("Sync ...");
		ActiveDirectoryUtils ldapUtils = new ActiveDirectoryUtils(
		        synchronizer.getProviderUrl(),
		        synchronizer.getPrincipal(),
		        synchronizer.getCredentials(),
		        synchronizer.getUserBasedn(),
		        synchronizer.getMsadDomain());
		ldapUtils.openConnection();
		
		activeDirectoryOrganizationService.setSynchronizer(synchronizer);
		activeDirectoryOrganizationService.setLdapUtils(ldapUtils);
		activeDirectoryOrganizationService.sync();
		
		activeDirectoryUsersService.setSynchronizer(synchronizer);
		activeDirectoryUsersService.setLdapUtils(ldapUtils);
		activeDirectoryUsersService.sync();
		
		ldapUtils.close();
	}

	public ActiveDirectoryUsersService getActiveDirectoryUsersService() {
		return activeDirectoryUsersService;
	}

	public void setActiveDirectoryUsersService(ActiveDirectoryUsersService activeDirectoryUsersService) {
		this.activeDirectoryUsersService = activeDirectoryUsersService;
	}

	public ActiveDirectoryOrganizationService getActiveDirectoryOrganizationService() {
		return activeDirectoryOrganizationService;
	}

	public void setActiveDirectoryOrganizationService(
			ActiveDirectoryOrganizationService activeDirectoryOrganizationService) {
		this.activeDirectoryOrganizationService = activeDirectoryOrganizationService;
	}

	@Override
	public void setSynchronizer(Synchronizers synchronizer) {
		this.synchronizer = synchronizer;
	}


	
}
