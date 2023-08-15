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
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.DbTableMetaData;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.util.JdbcUtils;
import org.dromara.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JdbcUsersService extends AbstractSynchronizerService    implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(JdbcUsersService.class);

	static ArrayList< ColumnFieldMapper> mapperList = new ArrayList< ColumnFieldMapper>();
	
	public void sync() {
		_logger.info("Sync Jdbc Users...");
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs 	 = null;
		
		try {
			if(StringUtils.isNotBlank(synchronizer.getOrgFilters())){
				_logger.info("Sync User Filters {}",synchronizer.getOrgFilters());
				conn = JdbcUtils.connect(
						synchronizer.getProviderUrl(), 
						synchronizer.getPrincipal(), 
						synchronizer.getCredentials(), 
						synchronizer.getDriverClass());
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(synchronizer.getUserFilters());
				long insertCount = 0;
				long updateCount = 0;
				long readCount 	 = 0;
				while(rs.next()) {
					UserInfo user = buildUserInfo(rs);
					UserInfo queryUser = this.userInfoService.findByUsername(user.getUsername());
					readCount ++;
					if(queryUser == null) {
						if(user.getPassword().indexOf("{") > -1 && user.getPassword().indexOf("}") > -1) {
							userInfoService.insert(user,false);
						}else {
							//passwordEncoder
							userInfoService.insert(user,true);
						}
						user.setBadPasswordCount(1);
						insertCount++;
					}else{
						//no need update password , set null
						user.setPassword(null);
						userInfoService.update(user);
						updateCount++;
					}
					_logger.trace("read Count {} , insert Count {} , updateCount {} " , readCount,insertCount,updateCount);
				}
				_logger.info("read Count {} , insert Count {} , updateCount {} " , readCount,insertCount,updateCount);
			}
		} catch (Exception e) {
			_logger.error("Exception " , e);
		}finally {
			JdbcUtils.release(conn, stmt, rs);
		}
	}
	
	public UserInfo buildUserInfo(ResultSet  rs) throws SQLException {
		DbTableMetaData meta = JdbcUtils.getMetaData(rs);
		UserInfo user = new UserInfo();
		//basic
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
						PropertyUtils.setSimpleProperty(user, mapper.getField(), value);
					} catch (Exception e) {
						_logger.error("setSimpleProperty {}" , e);
					}
				}
			}
		}
		
		if(meta.getColumnsMap().containsKey("status")) {
			user.setStatus(rs.getInt("status"));
		}else {
			user.setStatus(ConstsStatus.ACTIVE);
		}
		user.setInstId(this.synchronizer.getInstId());
		
		//password
		if(meta.getColumnsMap().containsKey("password")) {
			user.setPassword(rs.getString("password"));
		}else {
			//后4位
			String last4Char = "6666";
			if(StringUtils.isNotBlank(user.getIdCardNo())) {
				last4Char = user.getIdCardNo().substring(user.getIdCardNo().length() - 4);
			}else if(StringUtils.isNotBlank(user.getMobile())) {
				last4Char = user.getMobile().substring(user.getMobile().length() - 4);
			}else if(StringUtils.isNotBlank(user.getEmployeeNumber())) {
				last4Char = user.getEmployeeNumber().substring(user.getEmployeeNumber().length() - 4);
			}
			user.setPassword(user.getUsername()+"@M"+last4Char);
		}
		
		_logger.debug("User {} " , user);
		return user;
	}
	
	static {
		mapperList.add(new  ColumnFieldMapper("id"						, "id","String"));
		mapperList.add(new  ColumnFieldMapper("username"				, "userName","String"));
		mapperList.add(new  ColumnFieldMapper("picture"					, "picture","String"));
		mapperList.add(new  ColumnFieldMapper("displayname"				, "displayName","String"));
		mapperList.add(new  ColumnFieldMapper("nickname"				, "nickName","String"));
		mapperList.add(new  ColumnFieldMapper("mobile"					, "mobile","String"));
		mapperList.add(new  ColumnFieldMapper("email"					, "email","String"));
		mapperList.add(new  ColumnFieldMapper("birthdate"				, "birthDate","String"));
		mapperList.add(new  ColumnFieldMapper("usertype"				, "userType","String"));
		mapperList.add(new  ColumnFieldMapper("userstate"				, "userState","String"));
		mapperList.add(new  ColumnFieldMapper("windowsaccount"			, "windowsAccount","String"));
		mapperList.add(new  ColumnFieldMapper("givenname"				, "givenName","String"));
		mapperList.add(new  ColumnFieldMapper("middlename"				, "middleName","String"));
		mapperList.add(new  ColumnFieldMapper("married"					, "married","Int"));
		mapperList.add(new  ColumnFieldMapper("gender"					, "gender","Int"));
		mapperList.add(new  ColumnFieldMapper("idtype"					, "idType","Int"));
		mapperList.add(new  ColumnFieldMapper("idcardno"				, "idCardNo","String"));
		mapperList.add(new  ColumnFieldMapper("website"					, "webSite","String"));
		mapperList.add(new  ColumnFieldMapper("startworkdate"			, "startWorkDate","String"));
		//work	
		mapperList.add(new  ColumnFieldMapper("workcountry"				, "workCountry","String"));
		mapperList.add(new  ColumnFieldMapper("workregion"				, "workRegion","String"));
		mapperList.add(new  ColumnFieldMapper("worklocality"			, "workLocality","String"));
		mapperList.add(new  ColumnFieldMapper("workstreetaddress"		, "workStreetAddress","String"));
		mapperList.add(new  ColumnFieldMapper("workaddressformatted"	, "workAddressFormatted","String"));
		mapperList.add(new  ColumnFieldMapper("workemail"				, "workEmail","String"));
		mapperList.add(new  ColumnFieldMapper("workphonenumber"			, "workPhoneNumber","String"));
		mapperList.add(new  ColumnFieldMapper("workpostalcode"			, "workPostalCode","String"));
		mapperList.add(new  ColumnFieldMapper("workfax"					, "workFax","String"));
		mapperList.add(new  ColumnFieldMapper("workofficename"			, "workOfficeName","String"));
		//home	
		mapperList.add(new  ColumnFieldMapper("homecountry"				, "homeCountry","String"));
		mapperList.add(new  ColumnFieldMapper("homeregion"				, "homeRegion","String"));
		mapperList.add(new  ColumnFieldMapper("homelocality"			, "homeLocality","String"));
		mapperList.add(new  ColumnFieldMapper("homestreetaddress"		, "homeStreetAddress","String"));
		mapperList.add(new  ColumnFieldMapper("homeaddressformatted"	, "homeAddressFormatted","String"));
		mapperList.add(new  ColumnFieldMapper("homeemail"				, "homeEmail","String"));
		mapperList.add(new  ColumnFieldMapper("homephonenumber"			, "homePhonenumber","String"));
		mapperList.add(new  ColumnFieldMapper("homepostalcode"			, "homePostalCode","String"));
		mapperList.add(new  ColumnFieldMapper("homefax"					, "homeFax","String"));
		//company	
		mapperList.add(new  ColumnFieldMapper("employeenumber"			, "employeeNumber","String"));
		mapperList.add(new  ColumnFieldMapper("costcenter"				, "costCenter","String"));
		mapperList.add(new  ColumnFieldMapper("organization"			, "organization","String"));
		mapperList.add(new  ColumnFieldMapper("division"				, "division","String"));
		mapperList.add(new  ColumnFieldMapper("departmentid"			, "departmentId","String"));
		mapperList.add(new  ColumnFieldMapper("department"				, "department","String"));
		mapperList.add(new  ColumnFieldMapper("jobtitle"				, "jobTitle","String"));
		mapperList.add(new  ColumnFieldMapper("joblevel"				, "jobLevel","String"));
		mapperList.add(new  ColumnFieldMapper("managerid"				, "managerId","String"));
		mapperList.add(new  ColumnFieldMapper("manager"					, "manager","String"));
		mapperList.add(new  ColumnFieldMapper("assistantid"				, "assistantId","String"));
		mapperList.add(new  ColumnFieldMapper("assistant"				, "assistant","String"));
		mapperList.add(new  ColumnFieldMapper("entrydate"				, "entrydate","String"));
		mapperList.add(new  ColumnFieldMapper("quitdate"				, "quitdate","String"));
		mapperList.add(new  ColumnFieldMapper("ldapdn"					, "ldapDn","String"));
			
		mapperList.add(new  ColumnFieldMapper("description"				, "description","String"));
		mapperList.add(new  ColumnFieldMapper("status"					, "status","String"));
	}
}
