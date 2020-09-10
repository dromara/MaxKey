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
 

package org.maxkey.persistence.db;

import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.constants.ConstantsPasswordSetType;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.constants.ConstantsStatus;
import org.maxkey.constants.ConstantsTimeInterval;
import org.maxkey.crypto.password.PasswordGen;
import org.maxkey.domain.PasswordPolicy;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.passay.CharacterOccurrencesRule;
import org.passay.CharacterRule;
import org.passay.DictionaryRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.UsernameRule;
import org.passay.WhitespaceRule;
import org.passay.dictionary.Dictionary;
import org.passay.dictionary.DictionaryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.BadCredentialsException;

public class PasswordPolicyValidator {
    private static Logger _logger = LoggerFactory.getLogger(PasswordPolicyValidator.class);
    
    //Dictionary topWeakPassword Source
    public static final String topWeakPasswordPropertySource      = 
            "classpath:/top_weak_password.txt";
    
    //Cache PasswordPolicy in memory ONE_HOUR
    protected static final UserManagedCache<String, PasswordPolicy> passwordPolicyStore = 
            UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, PasswordPolicy.class)
                .withExpiry(
                    ExpiryPolicyBuilder.timeToLiveExpiration(
                            java.time.Duration.ofMinutes(ConstantsTimeInterval.ONE_HOUR)
                    )
                )
                .build(true);
    
    protected PasswordPolicy passwordPolicy;
    
    ArrayList <Rule> passwordPolicyRuleList;
    
    protected JdbcTemplate jdbcTemplate;
    
    MessageSource messageSource;
    
    public static final String PASSWORD_POLICY_VALIDATE_RESULT = "PASSWORD_POLICY_SESSION_VALIDATE_RESULT_KEY";
    
    private static final String PASSWORD_POLICY_KEY = "PASSWORD_POLICY_KEY";
    
    private static final String LOCK_USER_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET ISLOCKED = ?  , UNLOCKTIME = ? WHERE ID = ?";

    private static final String PASSWORD_POLICY_SELECT_STATEMENT = "SELECT * FROM MXK_PASSWORD_POLICY ";

    private static final String UNLOCK_USER_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET ISLOCKED = ? , UNLOCKTIME = ? WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET BADPASSWORDCOUNT = ? , BADPASSWORDTIME = ?  WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_RESET_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET BADPASSWORDCOUNT = ? , ISLOCKED = ? ,UNLOCKTIME = ?  WHERE ID = ?";

    public PasswordPolicyValidator() {
    }
    
    public PasswordPolicyValidator(JdbcTemplate jdbcTemplate,MessageSource messageSource) {
        this.messageSource=messageSource;
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * init PasswordPolicy and load Rules
     * @return
     */
    public PasswordPolicy getPasswordPolicy() {
        passwordPolicy = passwordPolicyStore.get(PASSWORD_POLICY_KEY);
       
        if (passwordPolicy == null) {
            passwordPolicy = jdbcTemplate.queryForObject(PASSWORD_POLICY_SELECT_STATEMENT,
                    new PasswordPolicyRowMapper());
            _logger.debug("query PasswordPolicy : " + passwordPolicy);
            passwordPolicyStore.put(PASSWORD_POLICY_KEY,passwordPolicy);
            
            //RandomPasswordLength =(MaxLength +MinLength)/2
            passwordPolicy.setRandomPasswordLength(
                Math.round(
                        (
                                passwordPolicy.getMaxLength() + 
                                passwordPolicy.getMinLength()
                        )/2
                   )
            );
            
            passwordPolicyRuleList = new ArrayList<Rule>();
            passwordPolicyRuleList.add(new WhitespaceRule());
            passwordPolicyRuleList.add(new LengthRule(passwordPolicy.getMinLength(), passwordPolicy.getMaxLength()));
            
            if(passwordPolicy.getUpperCase()>0) {
                passwordPolicyRuleList.add(new CharacterRule(EnglishCharacterData.UpperCase, passwordPolicy.getUpperCase()));
            }
            
            if(passwordPolicy.getLowerCase()>0) {
                passwordPolicyRuleList.add(new CharacterRule(EnglishCharacterData.LowerCase, passwordPolicy.getLowerCase()));
            }
            
            if(passwordPolicy.getDigits()>0) {
                passwordPolicyRuleList.add(new CharacterRule(EnglishCharacterData.Digit, passwordPolicy.getDigits()));
            }
            
            if(passwordPolicy.getSpecialChar()>0) {
                passwordPolicyRuleList.add(new CharacterRule(EnglishCharacterData.Special, passwordPolicy.getSpecialChar()));
            }
            
            if(passwordPolicy.getUsername()>0) {
                passwordPolicyRuleList.add(new UsernameRule());
            }
            
            if(passwordPolicy.getOccurances()>0) {
                passwordPolicyRuleList.add(new CharacterOccurrencesRule(passwordPolicy.getOccurances()));
            }
            
            if(passwordPolicy.getAlphabetical()>0) {
                passwordPolicyRuleList.add(new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 4, false));
            }
            
            if(passwordPolicy.getNumerical()>0) {
                passwordPolicyRuleList.add(new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, false));
            }
            
            if(passwordPolicy.getQwerty()>0) {
                passwordPolicyRuleList.add(new IllegalSequenceRule(EnglishSequenceData.USQwerty, 4, false));
            }
                        
            if(passwordPolicy.getDictionary()>0 ) {
                try {
                    ClassPathResource dictFile= 
                            new ClassPathResource(
                                    ConstantsProperties.classPathResource(topWeakPasswordPropertySource));
                    Dictionary dictionary =new DictionaryBuilder().addReader(new InputStreamReader(dictFile.getInputStream())).build();
                    passwordPolicyRuleList.add(new DictionaryRule(dictionary));
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return passwordPolicy;
    }
    
    /**
     * static validator .
     * @param userInfo
     * @return boolean
     */
   public boolean validator(UserInfo userInfo) {
       
       
       String password = userInfo.getPassword();
       String username = userInfo.getUsername();
       
       if(password.equals("") || password==null){
           _logger.debug("password  is Empty ");
           return false;
       }
       
       getPasswordPolicy();

       PasswordValidator validator = new PasswordValidator(
               new PasswordPolicyMessageResolver(messageSource),passwordPolicyRuleList);
       
       RuleResult result = validator.validate(new PasswordData(username,password));
       
       if (result.isValid()) {
           _logger.debug("Password is valid");
           return true;
       } else {
           _logger.debug("Invalid password:");
           String passwordPolicyMessage = "";
           for (String msg : validator.getMessages(result)) {
               passwordPolicyMessage = passwordPolicyMessage + msg + "<br>";
               _logger.debug("Rule Message " + msg);
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
       
       getPasswordPolicy();
       
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
            int intDuration = Integer.parseInt(duration.getStandardHours() + "");
            _logger.debug("bad Password duration " + intDuration
                    + " , password policy Duration "+passwordPolicy.getDuration()
                    + " , validate result " + (intDuration > passwordPolicy.getDuration()));
            //auto unlock attempts when intDuration > set Duration
            if(intDuration > passwordPolicy.getDuration()) {
                _logger.debug("resetAttempts ...");
                resetAttempts(userInfo);
            }else {
                lockUser(userInfo);
                throw new BadCredentialsException(
                        WebContext.getI18nValue("login.error.attempts",
                                new Object[]{userInfo.getUsername(),userInfo.getBadPasswordCount()}) 
                        );
            }
        }
        
        //locked
        if(userInfo.getIsLocked()==ConstantsStatus.LOCK) {
            throw new BadCredentialsException(
                                userInfo.getUsername()+ " "+
                                WebContext.getI18nValue("login.error.locked")
                                );
        }
        // inactive
        if(userInfo.getStatus()!=ConstantsStatus.ACTIVE) {
            throw new BadCredentialsException(
                                userInfo.getUsername()+ 
                                WebContext.getI18nValue("login.error.inactive") 
                                );
        }

        //initial password need change
        if(userInfo.getLoginCount()<=0) {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                    ConstantsPasswordSetType.INITIAL_PASSWORD);
        }
        
        if (userInfo.getPasswordSetType() != ConstantsPasswordSetType.PASSWORD_NORMAL) {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                        userInfo.getPasswordSetType());
            return true;
        } else {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                    ConstantsPasswordSetType.PASSWORD_NORMAL);
        }

        /*
         * check password is Expired,Expiration is Expired date ,if Expiration equals 0,not need check
         *
         */
        if (passwordPolicy.getExpiration() > 0) {
            String passwordLastSetTimeString = userInfo.getPasswordLastSetTime().substring(0, 19);
            _logger.info("last password set date " + passwordLastSetTimeString);

            DateTime changePwdDateTime = DateTime.parse(passwordLastSetTimeString,
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            Duration duration = new Duration(changePwdDateTime, currentdateTime);
            int intDuration = Integer.parseInt(duration.getStandardDays() + "");
            _logger.debug("password Last Set duration day " + intDuration
                    + " , password policy Expiration " +passwordPolicy.getExpiration()
                    +" , validate result " + (intDuration <= passwordPolicy.getExpiration()));
            if (intDuration > passwordPolicy.getExpiration()) {
                WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                        ConstantsPasswordSetType.PASSWORD_EXPIRED);
            }
        }
        
        return true;
    }
   
   
   /**
    * lockUser
    * 
    * @param userInfo
    */
   public void lockUser(UserInfo userInfo) {
       try {
           if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
               jdbcTemplate.update(LOCK_USER_UPDATE_STATEMENT,
                       new Object[] { ConstantsStatus.LOCK, new Date(), userInfo.getId() },
                       new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });
           }
       } catch (Exception e) {
           e.printStackTrace();
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
                       new Object[] { ConstantsStatus.ACTIVE, new Date(), userInfo.getId() },
                       new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });
           }
       } catch (Exception e) {
           e.printStackTrace();
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
                       new Object[] { 0, ConstantsStatus.ACTIVE, new Date(), userInfo.getId() },
                       new int[] { Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR });
           }
       } catch (Exception e) {
           e.printStackTrace();
           _logger.error(e.getMessage());
       }
   }

   /**
    * if login password is error ,BadPasswordCount++ and set bad date
    * 
    * @param userInfo
    */
   public void setBadPasswordCount(UserInfo userInfo) {
       try {
           if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
               int badPasswordCount = userInfo.getBadPasswordCount() + 1;
               userInfo.setBadPasswordCount(badPasswordCount);
               jdbcTemplate.update(BADPASSWORDCOUNT_UPDATE_STATEMENT,
                       new Object[] { badPasswordCount, new Date(), userInfo.getId() },
                       new int[] { Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR });
               
           }
       } catch (Exception e) {
           e.printStackTrace();
           _logger.error(e.getMessage());
       }
   }
   
   public String generateRandomPassword() {
       getPasswordPolicy();
       PasswordGen passwordGen = new PasswordGen(
               passwordPolicy.getRandomPasswordLength()
       );
       
       return passwordGen.gen(
               passwordPolicy.getLowerCase(), 
               passwordPolicy.getUpperCase(), 
               passwordPolicy.getDigits(), 
               passwordPolicy.getSpecialChar());
   }
   
   public void setPasswordPolicy(PasswordPolicy passwordPolicy) {
    this.passwordPolicy = passwordPolicy;
   }

 
   public class PasswordPolicyRowMapper implements RowMapper<PasswordPolicy> {

       @Override
       public PasswordPolicy mapRow(ResultSet rs, int rowNum) throws SQLException {
           PasswordPolicy passwordPolicy = new PasswordPolicy();
           passwordPolicy.setId(rs.getString("ID"));
           passwordPolicy.setMinLength(rs.getInt("MINLENGTH"));
           passwordPolicy.setMaxLength(rs.getInt("MAXLENGTH"));
           passwordPolicy.setLowerCase(rs.getInt("LOWERCASE"));
           passwordPolicy.setUpperCase(rs.getInt("UPPERCASE"));
           passwordPolicy.setDigits(rs.getInt("DIGITS"));
           passwordPolicy.setSpecialChar(rs.getInt("SPECIALCHAR"));
           passwordPolicy.setAttempts(rs.getInt("ATTEMPTS"));
           passwordPolicy.setDuration(rs.getInt("DURATION"));
           passwordPolicy.setExpiration(rs.getInt("EXPIRATION"));
           passwordPolicy.setUsername(rs.getInt("USERNAME"));
           passwordPolicy.setHistory(rs.getInt("HISTORY"));
           passwordPolicy.setDictionary(rs.getInt("DICTIONARY"));
           passwordPolicy.setAlphabetical(rs.getInt("ALPHABETICAL"));
           passwordPolicy.setNumerical(rs.getInt("NUMERICAL"));
           passwordPolicy.setQwerty(rs.getInt("QWERTY"));
           passwordPolicy.setOccurances(rs.getInt("OCCURANCES"));
           return passwordPolicy;
       }

   }
}
