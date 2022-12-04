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

import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.DbTableMetaData;
import org.maxkey.entity.UserInfo;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.maxkey.util.JdbcUtils;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JdbcUsersService extends AbstractSynchronizerService    implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(JdbcUsersService.class);

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
		if(meta.getColumnsMap().containsKey("id")) {
			user.setId(rs.getString("id"));
		}
		if(meta.getColumnsMap().containsKey("username")) {
			user.setUsername(rs.getString("username"));
		}
		if(meta.getColumnsMap().containsKey("picture")) {
			user.setPicture(rs.getBytes("picture"));
		}
		if(meta.getColumnsMap().containsKey("displayname")) {
			user.setDisplayName(rs.getString("displayname"));
		}
		if(meta.getColumnsMap().containsKey("nickname")) {
			user.setNickName(rs.getString("nickname"));
		}
		if(meta.getColumnsMap().containsKey("mobile")) {
			user.setMobile(rs.getString("mobile"));
		}
		if(meta.getColumnsMap().containsKey("email")) {
			user.setEmail(rs.getString("email"));
		}
		if(meta.getColumnsMap().containsKey("birthdate")) {
			user.setBirthDate(rs.getString("birthdate"));
		}
		if(meta.getColumnsMap().containsKey("usertype")) {
			user.setUserType(rs.getString("usertype"));
		}
		if(meta.getColumnsMap().containsKey("userstate")) {
			user.setUserState(rs.getString("userstate"));
		}
		if(meta.getColumnsMap().containsKey("windowsaccount")) {
			user.setWindowsAccount(rs.getString("windowsaccount"));
		}
		if(meta.getColumnsMap().containsKey("givenname")) {
			user.setGivenName(rs.getString("givenname"));
		}
		if(meta.getColumnsMap().containsKey("middlename")) {
			user.setMiddleName(rs.getString("middlename"));
		}
		if(meta.getColumnsMap().containsKey("married")) {
			user.setMarried(rs.getInt("married"));
		}
		if(meta.getColumnsMap().containsKey("gender")) {
			user.setGender(rs.getInt("gender"));
		}
		if(meta.getColumnsMap().containsKey("idtype")) {
			user.setIdType(rs.getInt("idtype"));
		}
		if(meta.getColumnsMap().containsKey("idcardno")) {
			user.setIdCardNo(rs.getString("idcardno"));
		}
		if(meta.getColumnsMap().containsKey("website")) {
			user.setWebSite(rs.getString("website"));
		}
		if(meta.getColumnsMap().containsKey("startworkdate")) {
			user.setStartWorkDate(rs.getString("startworkdate"));
		}
		//work
		if(meta.getColumnsMap().containsKey("workcountry")) {
			user.setWorkCountry(rs.getString("workcountry"));
		}
		if(meta.getColumnsMap().containsKey("workregion")) {
			user.setWorkRegion(rs.getString("workregion"));
		}
		if(meta.getColumnsMap().containsKey("worklocality")) {
			user.setWorkLocality(rs.getString("worklocality"));
		}
		if(meta.getColumnsMap().containsKey("workstreetaddress")) {
			user.setWorkStreetAddress(rs.getString("workstreetaddress"));
		}
		if(meta.getColumnsMap().containsKey("workaddressformatted")) {
			user.setWorkAddressFormatted(rs.getString("workaddressformatted"));
		}
		if(meta.getColumnsMap().containsKey("workemail")) {
			user.setWorkEmail(rs.getString("workemail"));
		}
		if(meta.getColumnsMap().containsKey("workphonenumber")) {
			user.setWorkPhoneNumber(rs.getString("workphonenumber"));
		}
		if(meta.getColumnsMap().containsKey("workpostalcode")) {
			user.setWorkPostalCode(rs.getString("workpostalcode"));
		}
		if(meta.getColumnsMap().containsKey("workfax")) {
			user.setWorkFax(rs.getString("workfax"));
		}
		if(meta.getColumnsMap().containsKey("workofficename")) {
			user.setWorkOfficeName(rs.getString("workofficename"));
		}
		//home
		if(meta.getColumnsMap().containsKey("homecountry")) {
			user.setHomeCountry(rs.getString("homecountry"));
		}
		if(meta.getColumnsMap().containsKey("homeregion")) {
			user.setHomeRegion(rs.getString("homeregion"));
		}
		if(meta.getColumnsMap().containsKey("homelocality")) {
			user.setHomeLocality(rs.getString("homelocality"));
		}
		if(meta.getColumnsMap().containsKey("homestreetaddress")) {
			user.setHomeStreetAddress(rs.getString("homestreetaddress"));
		}
		if(meta.getColumnsMap().containsKey("homeaddressformatted")) {
			user.setHomeAddressFormatted(rs.getString("homeaddressformatted"));
		}
		if(meta.getColumnsMap().containsKey("homeemail")) {
			user.setHomeEmail(rs.getString("homeemail"));
		}
		if(meta.getColumnsMap().containsKey("homephonenumber")) {
			user.setHomePhoneNumber(rs.getString("homephonenumber"));
		}
		if(meta.getColumnsMap().containsKey("homepostalcode")) {
			user.setHomePostalCode(rs.getString("homepostalcode"));
		}
		if(meta.getColumnsMap().containsKey("homefax")) {
			user.setHomeFax(rs.getString("homefax"));
		}
		//company
		if(meta.getColumnsMap().containsKey("employeenumber")) {
			user.setEmployeeNumber(rs.getString("employeenumber"));
		}
		if(meta.getColumnsMap().containsKey("costcenter")) {
			user.setCostCenter(rs.getString("costcenter"));
		}
		if(meta.getColumnsMap().containsKey("organization")) {
			user.setOrganization(rs.getString("organization"));
		}
		if(meta.getColumnsMap().containsKey("division")) {
			user.setDivision(rs.getString("division"));
		}
		if(meta.getColumnsMap().containsKey("departmentid")) {
			user.setDepartmentId(rs.getString("departmentid"));
		}
		if(meta.getColumnsMap().containsKey("department")) {
			user.setDepartment(rs.getString("department"));
		}
		if(meta.getColumnsMap().containsKey("jobtitle")) {
			user.setJobTitle(rs.getString("jobtitle"));
		}
		if(meta.getColumnsMap().containsKey("joblevel")) {
			user.setJobLevel(rs.getString("joblevel"));
		}
		if(meta.getColumnsMap().containsKey("managerid")) {
			user.setManagerId(rs.getString("managerid"));
		}
		if(meta.getColumnsMap().containsKey("manager")) {
			user.setManager(rs.getString("manager"));
		}
		if(meta.getColumnsMap().containsKey("assistantid")) {
			user.setAssistantId(rs.getString("assistantid"));
		}
		if(meta.getColumnsMap().containsKey("assistant")) {
			user.setAssistant(rs.getString("assistant"));
		}
		if(meta.getColumnsMap().containsKey("entrydate")) {
			user.setEntryDate(rs.getString("entrydate"));
		}
		if(meta.getColumnsMap().containsKey("quitdate")) {
			user.setQuitDate(rs.getString("quitdate"));
		}
		//common
		if(meta.getColumnsMap().containsKey("ldapdn")) {
			user.setLdapDn(rs.getString("ldapdn"));
		}
		if(meta.getColumnsMap().containsKey("status")) {
			user.setStatus(rs.getInt("status"));
		}else {
			user.setStatus(ConstsStatus.ACTIVE);
		}
		if(meta.getColumnsMap().containsKey("description")) {
			user.setDescription(rs.getString("description"));
		}
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
		user.setInstId(this.synchronizer.getInstId());
		_logger.debug("User {} " , user);
		return user;
	}

}
