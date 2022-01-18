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
 

package org.maxkey.authn.realm.jdbc;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.realm.ldap.LdapAuthenticationRealm;
import org.maxkey.authn.realm.ldap.LdapAuthenticationRealmService;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.constants.ConstsLoginType;
import org.maxkey.entity.PasswordPolicy;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.repository.LoginHistoryRepository;
import org.maxkey.persistence.repository.LoginRepository;
import org.maxkey.persistence.repository.PasswordPolicyValidator;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * JdbcAuthenticationRealm.
 * @author Crystal.Sea
 *
 */
public class JdbcAuthenticationRealm extends AbstractAuthenticationRealm {
    private static Logger _logger = LoggerFactory.getLogger(JdbcAuthenticationRealm.class);

    protected PasswordEncoder passwordEncoder;
    
    public JdbcAuthenticationRealm() {
        _logger.debug("init . ");
    }

    public JdbcAuthenticationRealm(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public JdbcAuthenticationRealm(
    		PasswordEncoder passwordEncoder,
    		PasswordPolicyValidator passwordPolicyValidator,
    		LoginRepository loginRepository,
    		LoginHistoryRepository loginHistoryRepository,
    		AbstractRemeberMeService remeberMeService,
    		UserInfoService userInfoService,
    	    JdbcTemplate jdbcTemplate) {
    	
    	this.passwordEncoder =passwordEncoder;
    	this.passwordPolicyValidator=passwordPolicyValidator;
    	this.loginRepository = loginRepository;
    	this.loginHistoryRepository = loginHistoryRepository;
    	this.remeberMeService = remeberMeService;
    	this.userInfoService = userInfoService;
        this.jdbcTemplate = jdbcTemplate;
    }
  
    public JdbcAuthenticationRealm(
    		PasswordEncoder passwordEncoder,
    		PasswordPolicyValidator passwordPolicyValidator,
    		LoginRepository loginRepository,
    		LoginHistoryRepository loginHistoryRepository,
    		AbstractRemeberMeService remeberMeService,
    		UserInfoService userInfoService,
    	    JdbcTemplate jdbcTemplate,
    	    LdapAuthenticationRealmService ldapAuthenticationRealmService) {
		this.passwordEncoder = passwordEncoder;
		this.passwordPolicyValidator = passwordPolicyValidator;
		this.loginRepository = loginRepository;
		this.loginHistoryRepository = loginHistoryRepository;
		this.remeberMeService = remeberMeService;
		this.userInfoService = userInfoService;
		this.jdbcTemplate = jdbcTemplate;
		this.ldapAuthenticationRealmService = ldapAuthenticationRealmService;
    }
    
    /**
     * passwordMatches.
     */
    public boolean passwordMatches(UserInfo userInfo, String password) {
        boolean passwordMatches = false;
        //jdbc password check
        //_logger.trace("password : " 
        //        + PasswordReciprocal.getInstance().rawPassword(userInfo.getUsername(), password));
        passwordMatches = passwordEncoder.matches(password,userInfo.getPassword());
        
        if(ldapAuthenticationRealmService != null) {
        	//passwordMatches == false and ldapSupport ==true
        	//validate password with LDAP
	        LdapAuthenticationRealm ldapRealm = ldapAuthenticationRealmService.getByInstId(userInfo.getInstId());
	        if(!passwordMatches && ldapRealm != null && ldapRealm.isLdapSupport()) {
	            passwordMatches = ldapRealm.passwordMatches(userInfo, password);
	            if(passwordMatches) {
	                //write password to database Realm
	                UserInfo changePasswordUser = new UserInfo();
	                changePasswordUser.setId(userInfo.getId());
	                changePasswordUser.setUsername(userInfo.getUsername());
	                changePasswordUser.setPassword(password);
	                userInfoService.changePassword(changePasswordUser, false);
	            }
	        }
        }
        _logger.debug("passwordvalid : {}" , passwordMatches);
        if (!passwordMatches) {
            passwordPolicyValidator.plusBadPasswordCount(userInfo);
            insertLoginHistory(userInfo, ConstsLoginType.LOCAL, "", "xe00000004", WebConstants.LOGIN_RESULT.PASSWORD_ERROE);
            PasswordPolicy passwordPolicy = passwordPolicyValidator.getPasswordPolicyRepository().getPasswordPolicy();
            if(userInfo.getBadPasswordCount()>=(passwordPolicy.getAttempts()/2)) {
                throw new BadCredentialsException(
                        WebContext.getI18nValue("login.error.password.attempts",
                                new Object[]{
                                        userInfo.getBadPasswordCount() + 1,
                                        passwordPolicy.getAttempts(),
                                        passwordPolicy.getDuration()}));
            }else {
                throw new BadCredentialsException(WebContext.getI18nValue("login.error.password"));
            }
        }
        return passwordMatches;
    }

}
