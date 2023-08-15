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
 

package org.dromara.maxkey.persistence.repository;

import java.sql.Types;
import java.util.Date;

import org.dromara.maxkey.constants.ConstsPasswordSetType;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.crypto.password.PasswordGen;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.PasswordPolicy;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.util.StringUtils;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;

public class PasswordPolicyValidator {
    private static Logger _logger = LoggerFactory.getLogger(PasswordPolicyValidator.class);
    
    PasswordPolicyRepository passwordPolicyRepository;
    
    protected JdbcTemplate jdbcTemplate;
    
    MessageSource messageSource;
    
    public static final String PASSWORD_POLICY_VALIDATE_RESULT = "PASSWORD_POLICY_SESSION_VALIDATE_RESULT_KEY";
    
    private static final String LOCK_USER_UPDATE_STATEMENT = "update mxk_userinfo set islocked = ?  , unlocktime = ? where id = ?";

    private static final String UNLOCK_USER_UPDATE_STATEMENT = "update mxk_userinfo set islocked = ? , unlocktime = ? where id = ?";

    private static final String BADPASSWORDCOUNT_UPDATE_STATEMENT = "update mxk_userinfo set badpasswordcount = ? , badpasswordtime = ?  where id = ?";

    private static final String BADPASSWORDCOUNT_RESET_UPDATE_STATEMENT = "update mxk_userinfo set badpasswordcount = ? , islocked = ? ,unlocktime = ?  where id = ?";

    public PasswordPolicyValidator() {
    }
    
    public PasswordPolicyValidator(JdbcTemplate jdbcTemplate,MessageSource messageSource) {
        this.messageSource=messageSource;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordPolicyRepository = new PasswordPolicyRepository(jdbcTemplate);
        
    }
    
    /**
     * static validator .
     * @param userInfo
     * @return boolean
     */
   public boolean validator(ChangePassword changePassword) {
       
       
       String password = changePassword.getPassword();
       String username = changePassword.getUsername();
       
       if(password.equals("") || password==null){
           _logger.debug("password  is Empty ");
           return false;
       }
       
       PasswordValidator validator = new PasswordValidator(
               new PasswordPolicyMessageResolver(messageSource),passwordPolicyRepository.getPasswordPolicyRuleList());
       
       RuleResult result = validator.validate(new PasswordData(username,password));
       
       if (result.isValid()) {
           _logger.debug("Password is valid");
           return true;
       } else {
           _logger.debug("Invalid password:");
           String passwordPolicyMessage = "";
           for (String msg : validator.getMessages(result)) {
               passwordPolicyMessage = passwordPolicyMessage + msg + "<br>";
               _logger.debug("Rule Message {}" , msg);
           }
           WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, passwordPolicyMessage);
           return false;
       }
   }
   
   
   /**
    * dynamic passwordPolicy Valid for user login.
    * @param userInfo
    * @return boolean
    */
   public boolean passwordPolicyValid(UserInfo userInfo) {
       
	   PasswordPolicy passwordPolicy = passwordPolicyRepository.getPasswordPolicy();
	   
       DateTime currentdateTime = new DateTime();
        /*
         * check login attempts fail times
         */
        if (userInfo.getBadPasswordCount() >= passwordPolicy.getAttempts()) {
            _logger.debug("login Attempts is " + userInfo.getBadPasswordCount());
            //duration
            String badPasswordTimeString = userInfo.getBadPasswordTime().substring(0, 19);
            _logger.trace("bad Password Time " + badPasswordTimeString);
            
            DateTime badPasswordTime = DateTime.parse(badPasswordTimeString,
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            Duration duration = new Duration(badPasswordTime, currentdateTime);
            int intDuration = Integer.parseInt(duration.getStandardMinutes() + "");
            _logger.debug("bad Password duration {} , " + 
                          "password policy Duration {} , "+
                          "validate result {}" ,
                          intDuration,
                          passwordPolicy.getDuration(), 
                          (intDuration > passwordPolicy.getDuration())
                    );
            //auto unlock attempts when intDuration >= set Duration
            if(intDuration >= passwordPolicy.getDuration()) {
                _logger.debug("resetAttempts ...");
                resetAttempts(userInfo);
            }else {
                lockUser(userInfo);
                throw new BadCredentialsException(
                        WebContext.getI18nValue("login.error.attempts",
                                new Object[]{userInfo.getBadPasswordCount(),passwordPolicy.getDuration()}) 
                        );
            }
        }
        
        //locked
        if(userInfo.getIsLocked()==ConstsStatus.LOCK) {
            throw new BadCredentialsException(
                                userInfo.getUsername()+ " "+
                                WebContext.getI18nValue("login.error.locked")
                                );
        }
        // inactive
        if(userInfo.getStatus()!=ConstsStatus.ACTIVE) {
            throw new BadCredentialsException(
                                userInfo.getUsername()+ 
                                WebContext.getI18nValue("login.error.inactive") 
                                );
        }

        return true;
    }
   
   public void applyPasswordPolicy(UserInfo userInfo) {
	   PasswordPolicy passwordPolicy = passwordPolicyRepository.getPasswordPolicy();
	   
       DateTime currentdateTime = new DateTime();
       //initial password need change
       if(userInfo.getLoginCount()<=0) {
           WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                   ConstsPasswordSetType.INITIAL_PASSWORD);
       }
       
       if (userInfo.getPasswordSetType() != ConstsPasswordSetType.PASSWORD_NORMAL) {
           WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                       userInfo.getPasswordSetType());
           return;
       } else {
           WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                   ConstsPasswordSetType.PASSWORD_NORMAL);
       }

       /*
        * check password is Expired,Expiration is Expired date ,if Expiration equals 0,not need check
        *
        */
       if (passwordPolicy.getExpiration() > 0) {
           String passwordLastSetTimeString = userInfo.getPasswordLastSetTime().substring(0, 19);
           _logger.info("last password set date {}" , passwordLastSetTimeString);

           DateTime changePwdDateTime = DateTime.parse(passwordLastSetTimeString,
                   DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
           Duration duration = new Duration(changePwdDateTime, currentdateTime);
           int intDuration = Integer.parseInt(duration.getStandardDays() + "");
           _logger.debug("password Last Set duration day {} , " +
                         "password policy Expiration {} , " +
                         "validate result {}", 
                    intDuration,
                    passwordPolicy.getExpiration(),
                    intDuration <= passwordPolicy.getExpiration()
                );
           if (intDuration > passwordPolicy.getExpiration()) {
               WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                       ConstsPasswordSetType.PASSWORD_EXPIRED);
           }
       }
       
       resetBadPasswordCount(userInfo);
   }
   
   /**
    * lockUser
    * 
    * @param userInfo
    */
   public void lockUser(UserInfo userInfo) {
       try {
           if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
        	   if(userInfo.getIsLocked() == ConstsStatus.ACTIVE) {
	               jdbcTemplate.update(LOCK_USER_UPDATE_STATEMENT,
	                       new Object[] { ConstsStatus.LOCK, new Date(), userInfo.getId() },
	                       new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });
	               userInfo.setIsLocked(ConstsStatus.LOCK);
        	   }
           }
       } catch (Exception e) {
           _logger.error("lockUser Exception",e);
       }
   }
   

   /**
    * unlockUser
    * 
    * @param userInfo
    */
   public void unlockUser(UserInfo userInfo) {
       try {
           if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
               jdbcTemplate.update(UNLOCK_USER_UPDATE_STATEMENT,
                       new Object[] { ConstsStatus.ACTIVE, new Date(), userInfo.getId() },
                       new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });
               userInfo.setIsLocked(ConstsStatus.ACTIVE);
           }
       } catch (Exception e) {
           _logger.error("unlockUser Exception",e);
       }
   }

   /**
   * reset BadPasswordCount And Lockout
    * 
    * @param userInfo
    */
   public void resetAttempts(UserInfo userInfo) {
       try {
           if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
               jdbcTemplate.update(BADPASSWORDCOUNT_RESET_UPDATE_STATEMENT,
                       new Object[] { 0, ConstsStatus.ACTIVE, new Date(), userInfo.getId() },
                       new int[] { Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR });
               userInfo.setIsLocked(ConstsStatus.ACTIVE);
               userInfo.setBadPasswordCount(0);
           }
       } catch (Exception e) {
           _logger.error("resetAttempts Exception",e);
       }
   }

   /**
    * if login password is error ,BadPasswordCount++ and set bad date
    * 
    * @param userInfo
    */
   private void setBadPasswordCount(String userId,int badPasswordCount) {
       try {
           jdbcTemplate.update(BADPASSWORDCOUNT_UPDATE_STATEMENT,
                   new Object[] { badPasswordCount, new Date(), userId },
                   new int[] { Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR });
       } catch (Exception e) {
           _logger.error("setBadPasswordCount Exception",e);
       }
   }
   
   public void plusBadPasswordCount(UserInfo userInfo) {
       if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
           userInfo.setBadPasswordCount(userInfo.getBadPasswordCount() + 1);
           setBadPasswordCount(userInfo.getId(),userInfo.getBadPasswordCount());
           PasswordPolicy passwordPolicy = passwordPolicyRepository.getPasswordPolicy();
           if(userInfo.getBadPasswordCount() >= passwordPolicy.getAttempts()) {
        	   _logger.debug("Bad Password Count {} , Max Attempts {}",
        			   userInfo.getBadPasswordCount() + 1,passwordPolicy.getAttempts());
        	   this.lockUser(userInfo);
           }
       }
   }
   
   public void resetBadPasswordCount(UserInfo userInfo) {
       if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
           if(userInfo.getBadPasswordCount()>0) {
               setBadPasswordCount(userInfo.getId(),0);
           } 
       }
   }
   
   public String generateRandomPassword() {
       PasswordPolicy passwordPolicy = passwordPolicyRepository.getPasswordPolicy();
       
       PasswordGen passwordGen = new PasswordGen(
               passwordPolicy.getRandomPasswordLength()
       );
       
       return passwordGen.gen(
               passwordPolicy.getLowerCase(), 
               passwordPolicy.getUpperCase(), 
               passwordPolicy.getDigits(), 
               passwordPolicy.getSpecialChar());
   }

	public PasswordPolicyRepository getPasswordPolicyRepository() {
		return passwordPolicyRepository;
	}

}
