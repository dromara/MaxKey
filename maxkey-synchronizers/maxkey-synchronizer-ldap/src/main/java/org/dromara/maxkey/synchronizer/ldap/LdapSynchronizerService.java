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
 

package org.dromara.maxkey.synchronizer.ldap;

import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.ldap.LdapUtils;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LdapSynchronizerService  implements ISynchronizerService{
	static final  Logger _logger = LoggerFactory.getLogger(LdapSynchronizerService.class);
	
	Synchronizers synchronizer;
	
	@Autowired
	LdapUsersService ldapUsersService;
	
	@Autowired
	LdapOrganizationService ldapOrganizationService;
	
	public LdapSynchronizerService() {
		super();
	}

	public void sync() {
		_logger.info("Sync ...");
		LdapUtils ldapUtils = new LdapUtils(
		        synchronizer.getProviderUrl(),
		        synchronizer.getPrincipal(),
		        synchronizer.getCredentials(),
		        synchronizer.getUserBasedn());
		ldapUtils.openConnection();
		
		ldapOrganizationService.setSynchronizer(synchronizer);
		ldapUsersService.setSynchronizer(synchronizer);
		
		ldapOrganizationService.setLdapUtils(ldapUtils);
		ldapUsersService.setLdapUtils(ldapUtils);
		
		
		ldapOrganizationService.sync();
		ldapUsersService.sync();
		
		ldapUtils.close();
	}

	public LdapUsersService getLdapUsersService() {
		return ldapUsersService;
	}

	public void setLdapUsersService(LdapUsersService ldapUsersService) {
		this.ldapUsersService = ldapUsersService;
	}

	public LdapOrganizationService getLdapOrganizationService() {
		return ldapOrganizationService;
	}

	public void setLdapOrganizationService(LdapOrganizationService ldapOrganizationService) {
		this.ldapOrganizationService = ldapOrganizationService;
	}

	@Override
	public void setSynchronizer(Synchronizers synchronizer) {
		this.synchronizer = synchronizer;
		
	}

	
}
