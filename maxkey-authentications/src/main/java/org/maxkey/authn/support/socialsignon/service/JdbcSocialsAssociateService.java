/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.authn.support.socialsignon.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcSocialsAssociateService   implements SocialsAssociateService{
	private static final Logger _logger = LoggerFactory.getLogger(JdbcSocialsAssociateService.class);
	
	private static final String DEFAULT_DEFAULT_INSERT_STATEMENT = "INSERT INTO  MXK_SOCIALS_ASSOCIATE(ID, UID , USERNAME , PROVIDER , SOCIALUID , ACCESSTOKEN , SOCIALUSERINFO , EXATTRIBUTE )VALUES( ? , ? , ? , ? , ?, ? , ? , ?)";
	
	private static final String DEFAULT_DEFAULT_SIGNON_SELECT_STATEMENT = "SELECT ID, UID , USERNAME , PROVIDER , SOCIALUID , ACCESSTOKEN , SOCIALUSERINFO , EXATTRIBUTE  FROM MXK_SOCIALS_ASSOCIATE WHERE PROVIDER = ?  AND SOCIALUID = ?";
	
	private static final String DEFAULT_DEFAULT_BIND_SELECT_STATEMENT = "SELECT ID, UID , USERNAME , PROVIDER , SOCIALUID , ACCESSTOKEN , SOCIALUSERINFO , EXATTRIBUTE  FROM MXK_SOCIALS_ASSOCIATE WHERE UID = ?" ;
	
	private static final String DEFAULT_DEFAULT_DELETE_STATEMENT = "DELETE FROM  MXK_SOCIALS_ASSOCIATE WHERE  UID = ? AND PROVIDER = ?";
	
	private static final String DEFAULT_DEFAULT_UPDATE_STATEMENT= "UPDATE MXK_SOCIALS_ASSOCIATE  SET ACCESSTOKEN  = ? , SOCIALUSERINFO = ? , EXATTRIBUTE = ? ,UPDATEDDATE = ?  WHERE ID = ?";

	private final JdbcTemplate jdbcTemplate;
	
	public JdbcSocialsAssociateService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;	
	}

	@Override
	public boolean insert(SocialsAssociate socialsAssociate) {
		socialsAssociate.setId(socialsAssociate.generateId());
		jdbcTemplate.update(DEFAULT_DEFAULT_INSERT_STATEMENT, 
				new Object[] { 
					socialsAssociate.getId(),
					socialsAssociate.getUid(),
					socialsAssociate.getUsername(),
					socialsAssociate.getProvider(),
					socialsAssociate.getSocialuid(),
					socialsAssociate.getAccessToken(),
					socialsAssociate.getSocialUserInfo(),
					socialsAssociate.getExAttribute()},
				new int[] {Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR });
		return true;
	}

	@Override
	public boolean delete(SocialsAssociate socialsAssociate) {
		jdbcTemplate.update(DEFAULT_DEFAULT_DELETE_STATEMENT, 
				new Object[] { 
					socialsAssociate.getUid(),
					socialsAssociate.getProvider()
					},
				new int[] {Types.VARCHAR, Types.VARCHAR});
		return true;
	}

	@Override
	public SocialsAssociate get(SocialsAssociate socialsAssociate) {
		List<SocialsAssociate> listsocialsAssociate=jdbcTemplate.query(
					DEFAULT_DEFAULT_SIGNON_SELECT_STATEMENT, 
					new SocialsAssociateRowMapper(),
					socialsAssociate.getProvider(),
					socialsAssociate.getSocialuid());
		_logger.debug("list socialsAssociate "+listsocialsAssociate);
		return (listsocialsAssociate.size()>0)?listsocialsAssociate.get(0):null;
	}
	
	@Override
	public List<SocialsAssociate> query(
			SocialsAssociate socialsAssociate) {
		List<SocialsAssociate> listsocialsAssociate=jdbcTemplate.query(
					DEFAULT_DEFAULT_BIND_SELECT_STATEMENT,
					new SocialsAssociateRowMapper(),
					socialsAssociate.getUid());
		_logger.debug("query bind  SocialSignOnUser "+listsocialsAssociate);
		return listsocialsAssociate;
	}


	@Override
	public boolean update(SocialsAssociate socialsAssociate) {
		jdbcTemplate.update(DEFAULT_DEFAULT_UPDATE_STATEMENT, 
				new Object[] {socialsAssociate.getAccessToken(),socialsAssociate.getSocialUserInfo(),socialsAssociate.getExAttribute(),new Date(),socialsAssociate.getId()},
				new int[] {Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.TIMESTAMP,Types.VARCHAR });
		return false;
	}
	
	private final class SocialsAssociateRowMapper  implements RowMapper<SocialsAssociate> {
		@Override
		public SocialsAssociate mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			SocialsAssociate socialsAssociate=new SocialsAssociate();
			socialsAssociate.setId(rs.getString(1));
			socialsAssociate.setUid(rs.getString(2));
			socialsAssociate.setUsername(rs.getString(3));
			socialsAssociate.setProvider(rs.getString(4));
			socialsAssociate.setSocialuid(rs.getString(5));
			socialsAssociate.setAccessToken(rs.getString(6));
			socialsAssociate.setSocialUserInfo(rs.getString(7));
			socialsAssociate.setExAttribute(rs.getString(8));
			return socialsAssociate;
		}
	}
	
}



