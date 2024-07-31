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
 

package org.dromara.maxkey.synchronizer.jdbc;

import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcSynchronizerService   implements ISynchronizerService{
	static final  Logger _logger = LoggerFactory.getLogger(JdbcSynchronizerService.class);
	
	Synchronizers synchronizer;
	
	@Autowired
	JdbcUsersService jdbcUsersService;
	
	@Autowired
	JdbcOrganizationService jdbcOrganizationService;
	
	public JdbcSynchronizerService() {
		super();
	}

	public void sync() {
		_logger.info("Sync ...");
		jdbcOrganizationService.setSynchronizer(synchronizer);
		jdbcOrganizationService.sync();
		
		jdbcUsersService.setSynchronizer(synchronizer);
		jdbcUsersService.sync();
		
	}

	public void setJdbcUsersService(JdbcUsersService jdbcUsersService) {
		this.jdbcUsersService = jdbcUsersService;
	}

	public void setJdbcOrganizationService(JdbcOrganizationService jdbcOrganizationService) {
		this.jdbcOrganizationService = jdbcOrganizationService;
	}

	@Override
	public void setSynchronizer(Synchronizers synchronizer) {
		this.synchronizer = synchronizer;
	}


	
}
