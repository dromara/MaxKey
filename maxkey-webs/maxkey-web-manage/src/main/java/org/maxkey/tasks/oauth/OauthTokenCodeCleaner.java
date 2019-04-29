package org.maxkey.tasks.oauth;

import java.util.Date;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class OauthTokenCodeCleaner {
	
	final static Logger _logger = LoggerFactory.getLogger(OauthTokenCodeCleaner.class);
	
	JdbcTemplate jdbcTemplate;

	public static final String OAUTH_CODE_CLEANER_SQL="DELETE FROM OAUTH_CODE WHERE ALLOCATEDTIME < ?";
	
	public static final String OAUTH10A_REQUEST_TOKEN_CLEANER_SQL="DELETE FROM OAUTH10A_REQUEST_TOKEN WHERE ALLOCATEDTIME < ?";
	
	public static final String OAUTH10A_VERIFIER_CLEANER_SQL="DELETE FROM OAUTH10A_VERIFIER WHERE ALLOCATEDTIME < ?";
	
	public void clean() {
		_logger.info("OAuth Token & Code Cleaner start . ");
		DateTime currentdateTime = new DateTime();
		_logger.info("current date time : " +currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
		currentdateTime=currentdateTime.minusMinutes(10);
		Date date=currentdateTime.toDate();
		_logger.info("OAuth Code Cleaner date time : " +currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
		jdbcTemplate.update(OAUTH_CODE_CLEANER_SQL, date);
		jdbcTemplate.update(OAUTH10A_REQUEST_TOKEN_CLEANER_SQL, date);
		jdbcTemplate.update(OAUTH10A_VERIFIER_CLEANER_SQL, date);
		_logger.info("OAuth Token & Code Cleaner Successful");
	}
	 
	 
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}
