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
 

package org.maxkey.synchronizer.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.DbTableMetaData;
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
	
	public void sync() {
		Connection conn = null;
	    Statement  stmt = null;
	    ResultSet  rs 	 = null;
		try {
			if(StringUtils.isNotBlank(synchronizer.getOrgFilters())){
				_logger.info("Sync Org Filters {}",synchronizer.getOrgFilters());
				conn = JdbcUtils.connect(
						synchronizer.getProviderUrl(), 
						synchronizer.getPrincipal(), 
						synchronizer.getCredentials(), 
						synchronizer.getDriverClass());
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(synchronizer.getOrgFilters());
				while(rs.next()) {
					Organizations org = buildOrganization(rs);
					Organizations queryOrg = this.organizationsService.get(org.getId());
					if(queryOrg == null) {
						organizationsService.insert(org);
					}else{
						this.organizationsService.update(org);
					}
				}
			}
		} catch (Exception e) {
			_logger.error("Exception " , e);
		}finally {
			JdbcUtils.release(conn, stmt, rs);
		}
	}
	
	
	public Organizations buildOrganization(ResultSet  rs) throws SQLException {
		DbTableMetaData meta = JdbcUtils.getMetaData(rs);
	    Organizations org = new Organizations();
	    if(meta.getColumnsMap().containsKey("id")) {
	    	org.setId(org.generateId());
	    }
	    if(meta.getColumnsMap().containsKey("orgcode")) {
	    	org.setOrgCode(rs.getString("orgcode"));
	    }
	    if(meta.getColumnsMap().containsKey("orgname")) {
	    	org.setOrgName(rs.getString("orgname"));
	    }
	    if(meta.getColumnsMap().containsKey("fullname")) {
	    	org.setFullName(rs.getString("fullname"));
	    }
		//parent
	    if(meta.getColumnsMap().containsKey("parentid")) {
	    	org.setParentId(rs.getString("parentid"));
	    }
	    if(meta.getColumnsMap().containsKey("parentcode")) {
	    	org.setParentCode(rs.getString("parentcode"));
	    }
	    if(meta.getColumnsMap().containsKey("parentname")) {
	    	org.setParentName(rs.getString("parentname"));
	    }
		//ex attr
	    if(meta.getColumnsMap().containsKey("type")) {
	    	org.setType(rs.getString("type"));
	    }
	    if(meta.getColumnsMap().containsKey("codepath")) {
	    	org.setCodePath(rs.getString("codepath"));
	    }
	    if(meta.getColumnsMap().containsKey("namepath")) {
	    	org.setNamePath(rs.getString("namepath"));
	    }
	    if(meta.getColumnsMap().containsKey("level")) {
	    	org.setLevel(rs.getInt("level"));
	    }
	    if(meta.getColumnsMap().containsKey("haschild")) {
	    	org.setHasChild(rs.getString("haschild"));
	    }
	    if(meta.getColumnsMap().containsKey("division")) {
	    	org.setDivision(rs.getString("division"));
	    }
	    if(meta.getColumnsMap().containsKey("country")) {
	    	org.setCountry(rs.getString("country"));
	    }
	    if(meta.getColumnsMap().containsKey("region")) {
	    	org.setRegion(rs.getString("region"));
	    }
	    if(meta.getColumnsMap().containsKey("locality")) {
	    	org.setLocality(rs.getString("locality"));
	    }
	    if(meta.getColumnsMap().containsKey("street")) {
	    	org.setStreet(rs.getString("street"));
	    }
	    if(meta.getColumnsMap().containsKey("address")) {
	    	org.setAddress(rs.getString("address"));
	    }
	    if(meta.getColumnsMap().containsKey("contact")) {
	    	org.setContact(rs.getString("contact"));
	    }
	    if(meta.getColumnsMap().containsKey("postalcode")) {
	    	org.setPostalCode(rs.getString("postalcode"));
	    }
	    if(meta.getColumnsMap().containsKey("phone")) {
	    	org.setPhone(rs.getString("phone"));
	    }
	    if(meta.getColumnsMap().containsKey("fax")) {
	    	org.setFax(rs.getString("fax"));
	    }
	    if(meta.getColumnsMap().containsKey("email")) {
	    	org.setEmail(rs.getString("email"));
	    }
	    if(meta.getColumnsMap().containsKey("sortindex")) {
	    	org.setSortIndex(rs.getInt("sortindex"));
	    }
	    if(meta.getColumnsMap().containsKey("ldapdn")) {
	    	org.setLdapDn(rs.getString("ldapdn"));
	    }
	    if(meta.getColumnsMap().containsKey("description")) {
	    	org.setDescription(rs.getString("description"));
	    }
		org.setInstId(this.synchronizer.getInstId());
		if(meta.getColumnsMap().containsKey("status")) {
			org.setStatus(rs.getInt("status"));
		}else {
			org.setStatus(ConstsStatus.ACTIVE);
		}
		
		_logger.debug("Organization {}" , org);
		return org;
	}
}
