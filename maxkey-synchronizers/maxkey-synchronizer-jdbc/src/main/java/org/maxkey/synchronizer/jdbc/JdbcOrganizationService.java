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
 

package org.maxkey.synchronizer.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.Organizations;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.maxkey.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JdbcOrganizationService  extends AbstractSynchronizerService  implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(JdbcOrganizationService.class);

	 Connection conn = null;
     Statement  stmt = null;
     ResultSet  rs 	 = null;
	
     String querySql = "select * from org";
	
	public void sync() {
		try {
			conn = JdbcUtils.connect(
					synchronizer.getProviderUrl(), 
					synchronizer.getPrincipal(), 
					synchronizer.getCredentials(), 
					synchronizer.getDriverClass());
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySql);
			while(rs.next()) {
				Organizations queryOrg = this.organizationsService.get(rs.getString("id"));
				if(queryOrg == null) {
					Organizations org = buildOrganization(rs);
					organizationsService.insert(org);
				}else{
					//this.organizationsService.update(org);
				}
			}
			JdbcUtils.release(conn, stmt, rs);
		} catch (Exception e) {
			_logger.error("Exception " , e);
		}
	}
	
	
	public Organizations buildOrganization(ResultSet  rs) {
		try {
		    Organizations org = new Organizations();
		
			org.setId(org.generateId());
			org.setOrgCode(rs.getString("code"));
			org.setOrgName(rs.getString("orgname"));
			org.setInstId(this.synchronizer.getInstId());
			org.setStatus(ConstsStatus.ACTIVE);
			
			_logger.debug("Organization " + org);
			return org;
		} catch (Exception e) {
			_logger.error("NamingException " , e);
		}
		return null;
	}
}
