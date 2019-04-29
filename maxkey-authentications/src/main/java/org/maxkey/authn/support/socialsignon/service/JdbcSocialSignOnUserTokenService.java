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

public class JdbcSocialSignOnUserTokenService   implements SocialSignOnUserTokenService{
	private static final Logger _logger = LoggerFactory.getLogger(JdbcSocialSignOnUserTokenService.class);
	
	private static final String DEFAULT_DEFAULT_INSERT_STATEMENT = "INSERT INTO  SOCIALSIGNON_USERS_TOKEN(ID, UID , USERNAME , PROVIDER , SOCIALUID , ACCESSTOKEN , SOCIALUSERINFO , EXATTRIBUTE )VALUES( ? , ? , ? , ? , ?, ? , ? , ?)";
	
	private static final String DEFAULT_DEFAULT_SIGNON_SELECT_STATEMENT = "SELECT ID, UID , USERNAME , PROVIDER , SOCIALUID , ACCESSTOKEN , SOCIALUSERINFO , EXATTRIBUTE  FROM SOCIALSIGNON_USERS_TOKEN WHERE PROVIDER = ?  AND SOCIALUID = ?";
	
	private static final String DEFAULT_DEFAULT_BIND_SELECT_STATEMENT = "SELECT ID, UID , USERNAME , PROVIDER , SOCIALUID , ACCESSTOKEN , SOCIALUSERINFO , EXATTRIBUTE  FROM SOCIALSIGNON_USERS_TOKEN WHERE UID = ?" ;
	
	private static final String DEFAULT_DEFAULT_DELETE_STATEMENT = "DELETE FROM  SOCIALSIGNON_USERS_TOKEN WHERE  UID = ? AND PROVIDER = ?";
	
	private static final String DEFAULT_DEFAULT_UPDATE_STATEMENT= "UPDATE SOCIALSIGNON_USERS_TOKEN  SET ACCESSTOKEN  = ? , SOCIALUSERINFO = ? , EXATTRIBUTE = ? ,UPDATEDDATE = ?  WHERE ID = ?";

	private final JdbcTemplate jdbcTemplate;
	
	public JdbcSocialSignOnUserTokenService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;	
	}

	@Override
	public boolean insert(SocialSignOnUserToken socialSignOnUserToken) {
		socialSignOnUserToken.setId(socialSignOnUserToken.generateId());
		jdbcTemplate.update(DEFAULT_DEFAULT_INSERT_STATEMENT, 
				new Object[] { 
					socialSignOnUserToken.getId(),
					socialSignOnUserToken.getUid(),
					socialSignOnUserToken.getUsername(),
					socialSignOnUserToken.getProvider(),
					socialSignOnUserToken.getSocialuid(),
					socialSignOnUserToken.getAccessToken(),
					socialSignOnUserToken.getSocialUserInfo(),
					socialSignOnUserToken.getExAttribute()},
				new int[] {Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR });
		return true;
	}

	@Override
	public boolean delete(SocialSignOnUserToken socialSignOnUserToken) {
		jdbcTemplate.update(DEFAULT_DEFAULT_DELETE_STATEMENT, 
				new Object[] { 
					socialSignOnUserToken.getUid(),
					socialSignOnUserToken.getProvider()
					},
				new int[] {Types.VARCHAR, Types.VARCHAR});
		return true;
	}

	@Override
	public SocialSignOnUserToken get(SocialSignOnUserToken socialSignOnUserToken) {
		List<SocialSignOnUserToken> listSocialSignOnUserToken=jdbcTemplate.query(
					DEFAULT_DEFAULT_SIGNON_SELECT_STATEMENT, 
					new SocialSignOnUserTokenRowMapper(),
					socialSignOnUserToken.getProvider(),
					socialSignOnUserToken.getSocialuid());
		_logger.debug("list SocialSignOnUserToken "+listSocialSignOnUserToken);
		return (listSocialSignOnUserToken.size()>0)?listSocialSignOnUserToken.get(0):null;
	}
	
	@Override
	public List<SocialSignOnUserToken> query(
			SocialSignOnUserToken socialSignOnUserToken) {
		List<SocialSignOnUserToken> listSocialSignOnUserToken=jdbcTemplate.query(
					DEFAULT_DEFAULT_BIND_SELECT_STATEMENT,
					new SocialSignOnUserTokenRowMapper(),
					socialSignOnUserToken.getUid());
		_logger.debug("query bind  SocialSignOnUser "+listSocialSignOnUserToken);
		return listSocialSignOnUserToken;
	}


	@Override
	public boolean update(SocialSignOnUserToken socialSignOnUserToken) {
		jdbcTemplate.update(DEFAULT_DEFAULT_UPDATE_STATEMENT, 
				new Object[] {socialSignOnUserToken.getAccessToken(),socialSignOnUserToken.getSocialUserInfo(),socialSignOnUserToken.getExAttribute(),new Date(),socialSignOnUserToken.getId()},
				new int[] {Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.TIMESTAMP,Types.VARCHAR });
		return false;
	}
	
	private final class SocialSignOnUserTokenRowMapper  implements RowMapper<SocialSignOnUserToken> {
		@Override
		public SocialSignOnUserToken mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			SocialSignOnUserToken socialSignOnUserToken=new SocialSignOnUserToken();
			socialSignOnUserToken.setId(rs.getString(1));
			socialSignOnUserToken.setUid(rs.getString(2));
			socialSignOnUserToken.setUsername(rs.getString(3));
			socialSignOnUserToken.setProvider(rs.getString(4));
			socialSignOnUserToken.setSocialuid(rs.getString(5));
			socialSignOnUserToken.setAccessToken(rs.getString(6));
			socialSignOnUserToken.setSocialUserInfo(rs.getString(7));
			socialSignOnUserToken.setExAttribute(rs.getString(8));
			return socialSignOnUserToken;
		}
	}
	
}



