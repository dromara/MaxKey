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

	 Connection conn = null;
     Statement  stmt = null;
     ResultSet  rs 	 = null;
	
     String querySql = "select * from account";
	public void sync() {
		_logger.info("Sync Jdbc Users...");
		try {
			conn = JdbcUtils.connect(
					synchronizer.getProviderUrl(), 
					synchronizer.getPrincipal(), 
					synchronizer.getCredentials(), 
					synchronizer.getDriverClass());
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySql);
			long insertCount = 0;
			long updateCount = 0;
			long readCount 	 = 0;
			while(rs.next()) {
				UserInfo queryUser = this.userInfoService.findByUsername(rs.getString("mobile"));
				readCount ++;
				if(queryUser == null) {
					UserInfo user = buildUserInfo(rs);
					if(StringUtils.isBlank(rs.getString("password"))) {
						user.setPassword(rs.getString("mobile"));
						userInfoService.insert(user,true);
					}else {
						user.setPassword("{bcrypt}"+rs.getString("password"));
						userInfoService.insert(user,false);
					}
					insertCount++;
				}else{
					//userInfoService.update(queryUser);
					updateCount++;
				}
				_logger.debug("read Count {} , insert Count {} , updateCount {} " , readCount,insertCount,updateCount);
			}
			_logger.info("read Count {} , insert Count {} , updateCount {} " , readCount,insertCount,updateCount);
			JdbcUtils.release(conn, stmt, rs);
		} catch (Exception e) {
			_logger.error("Exception " , e);
		}
	}
	
	public UserInfo buildUserInfo(ResultSet  rs) throws SQLException {
		UserInfo user = new UserInfo();
		user.setId(rs.getString("id"));
		user.setUsername(rs.getString("mobile"));
		user.setDisplayName(rs.getString("nickname"));
		user.setNickName(rs.getString("nickname"));
		user.setMobile(rs.getString("mobile"));
		user.setEmail(rs.getString("email"));
		user.setInstId(this.synchronizer.getInstId());
		user.setStatus(ConstsStatus.ACTIVE);
		return user;
	}

}
