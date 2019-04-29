package org.maxkey.authn.realm.jdbc;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * same as JdbcAuthenticationRealm
 * @author Crystal.Sea
 * 
 */
public class DefaultJdbcAuthenticationRealm extends AbstractAuthenticationRealm{
	private static Logger _logger = LoggerFactory.getLogger(DefaultJdbcAuthenticationRealm.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public DefaultJdbcAuthenticationRealm() {
	
	}
	
	public DefaultJdbcAuthenticationRealm(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}


	public boolean passwordMatches(UserInfo userInfo, String j_password) {
		boolean passwordMatches=false;
		
		_logger.info("password : "+PasswordReciprocal.getInstance().rawPassword(userInfo.getUsername(), j_password));
		passwordMatches= passwordEncoder.matches(PasswordReciprocal.getInstance().rawPassword(userInfo.getUsername(), j_password), userInfo.getPassword());
    	_logger.debug("passwordvalid : "+passwordMatches);
    	if(!passwordMatches){
    		setBadPasswordCount(userInfo);
    		throw new BadCredentialsException(WebContext.getI18nValue("login.error.password"));
    	}
    	return passwordMatches;
	}
}
