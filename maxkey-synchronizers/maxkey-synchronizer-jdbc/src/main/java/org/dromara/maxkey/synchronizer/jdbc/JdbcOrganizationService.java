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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.DbTableMetaData;
import org.dromara.maxkey.entity.Organizations;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JdbcOrganizationService  extends AbstractSynchronizerService  implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(JdbcOrganizationService.class);
	static ArrayList< ColumnFieldMapper> mapperList = new ArrayList< ColumnFieldMapper>();
	
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
		
		for (ColumnFieldMapper mapper :mapperList ) {
			if(meta.getColumnsMap().containsKey(mapper.getColumn())) {
				Object value = null;
				if(mapper.getType().equalsIgnoreCase("String")) {
					value = rs.getString(mapper.getColumn());
				}else {
					value = rs.getInt(mapper.getColumn());
				}
				if(value != null ) {
					try {
						PropertyUtils.setSimpleProperty(org, mapper.getField(), value);
					} catch (Exception e) {
						_logger.error("setSimpleProperty {}" , e);
					}
				}
			}
		}
		
		org.setId(org.generateId());
		org.setInstId(this.synchronizer.getInstId());
		if(meta.getColumnsMap().containsKey("status")) {
			org.setStatus(rs.getInt("status"));
		}else {
			org.setStatus(ConstsStatus.ACTIVE);
		}
		
		_logger.debug("Organization {}" , org);
		return org;
	}
	

	static {
		mapperList.add(new  ColumnFieldMapper("id"			, "id","String"));
		mapperList.add(new  ColumnFieldMapper("orgcode"		, "orgCode","String"));
		mapperList.add(new  ColumnFieldMapper("orgname"		, "orgName","String"));
		mapperList.add(new  ColumnFieldMapper("fullname"	, "fullName","String"));
		mapperList.add(new  ColumnFieldMapper("parentid"	, "parentId","String"));
		mapperList.add(new  ColumnFieldMapper("parentcode"	, "parentCode","String"));
		mapperList.add(new  ColumnFieldMapper("parentname"	, "parentName","String"));
		
		mapperList.add(new  ColumnFieldMapper("type"		, "type","String"));
		mapperList.add(new  ColumnFieldMapper("codepath"	, "codePath","String"));
		mapperList.add(new  ColumnFieldMapper("namepath"	, "namePath","String"));
		mapperList.add(new  ColumnFieldMapper("level"		, "level","Int"));
		mapperList.add(new  ColumnFieldMapper("haschild"	, "hasChild","String"));
		mapperList.add(new  ColumnFieldMapper("division"	, "division","String"));
		mapperList.add(new  ColumnFieldMapper("country"		, "country","String"));
		mapperList.add(new  ColumnFieldMapper("region"		, "region","String"));
		mapperList.add(new  ColumnFieldMapper("locality"	, "locality","String"));
		mapperList.add(new  ColumnFieldMapper("street"		, "street","String"));
		mapperList.add(new  ColumnFieldMapper("address"		, "address","String"));
		mapperList.add(new  ColumnFieldMapper("contact"		, "contact","String"));
		mapperList.add(new  ColumnFieldMapper("postalcode"	, "postalCode","String"));
		mapperList.add(new  ColumnFieldMapper("phone"		, "phone","String"));
		mapperList.add(new  ColumnFieldMapper("fax"			, "fax","String"));
		mapperList.add(new  ColumnFieldMapper("email"		, "email","String"));
		mapperList.add(new  ColumnFieldMapper("sortindex"	, "sortIndex","Int"));
		mapperList.add(new  ColumnFieldMapper("ldapdn"		, "ldapDn","String"));
		mapperList.add(new  ColumnFieldMapper("description"	, "description","String"));
		mapperList.add(new  ColumnFieldMapper("status"		, "status","int"));
	}
}
