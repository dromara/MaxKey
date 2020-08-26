package org.maxkey.persistence.db;

import java.io.InputStreamReader;
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
import org.maxkey.domain.PasswordPolicy;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.passay.CharacterRule;
import org.passay.DictionaryRule;
import org.passay.EnglishCharacterData;
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
import org.springframework.security.authentication.BadCredentialsException;

public class PasswordPolicyValidator {
    private static Logger _logger = LoggerFactory.getLogger(PasswordPolicyValidator.class);

    public static final String topWeakPasswordPropertySource      = 
            "classpath:/top_weak_password.txt";
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
    
    private static final String PASSWORD_POLICY_KEY = "PASSWORD_POLICY_KEY";
    private static final String LOCK_USER_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET ISLOCKED = ?  , UNLOCKTIME = ? WHERE ID = ?";

    private static final String PASSWORD_POLICY_SELECT_STATEMENT = "SELECT ID,MINLENGTH,MAXLENGTH,LOWERCASE,UPPERCASE,DIGITS,SPECIALCHAR,ATTEMPTS,DURATION,EXPIRATION,USERNAME,SIMPLEPASSWORDS FROM MXK_PASSWORD_POLICY ";

    private static final String UNLOCK_USER_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET ISLOCKED = ? , UNLOCKTIME = ? WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET BADPASSWORDCOUNT = ? , BADPASSWORDTIME = ?  WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_RESET_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET BADPASSWORDCOUNT = ? , ISLOCKED = ? ,UNLOCKTIME = ?  WHERE ID = ?";

    public PasswordPolicyValidator() {
    }
    
    public PasswordPolicyValidator(JdbcTemplate jdbcTemplate,MessageSource messageSource) {
        this.messageSource=messageSource;
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    public PasswordPolicy getPasswordPolicy() {
        passwordPolicy = passwordPolicyStore.get(PASSWORD_POLICY_KEY);
       
        if (passwordPolicy == null) {
            passwordPolicy = jdbcTemplate.queryForObject(PASSWORD_POLICY_SELECT_STATEMENT,
                    new PasswordPolicyRowMapper());
            _logger.debug("query PasswordPolicy : " + passwordPolicy);
            passwordPolicyStore.put(PASSWORD_POLICY_KEY,passwordPolicy);
            
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
            
            if(passwordPolicy.getSimplePasswords().length()>0 ) {
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
     * validator .
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
           System.out.println("Password is valid");
         } else {
           System.out.println("Invalid password:");
           for (String msg : validator.getMessages(result)) {
             System.out.println(msg);
           }
         }
       
       return true;
   }
   
   
   /**
    * passwordPolicyValid .
    * @param userInfo
    * @return boolean
    */
   public boolean passwordPolicyValid(UserInfo userInfo) {
       
       getPasswordPolicy();
       
        /*
         * check login attempts fail times
         */
        if (userInfo.getBadPasswordCount() >= passwordPolicy.getAttempts()) {
            _logger.debug("PasswordPolicy : " + passwordPolicy);
            _logger.debug("login Attempts is " + userInfo.getBadPasswordCount());
            lockUser(userInfo);

            throw new BadCredentialsException(
                                userInfo.getUsername() + " " +
                                WebContext.getI18nValue("login.error.attempts") + " " +
                                userInfo.getBadPasswordCount()
                                );
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
                                userInfo.getUsername()+ " status "+ 
                                userInfo.getStatus() +
                                WebContext.getI18nValue("login.error.inactive") 
                                );
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

            DateTime currentdateTime = new DateTime();
            DateTime changePwdDateTime = DateTime.parse(passwordLastSetTimeString,
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            Duration duration = new Duration(changePwdDateTime, currentdateTime);
            int intDuration = Integer.parseInt(duration.getStandardDays() + "");
            _logger.debug("validate duration " + intDuration);
            _logger.debug("validate result " + (intDuration <= passwordPolicy.getExpiration()));
            if (intDuration > passwordPolicy.getExpiration()) {
                WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                        ConstantsPasswordSetType.PASSWORD_EXPIRED);
            }
        }
        
        //initial password need change
        if(userInfo.getLoginCount()<=0) {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                    ConstantsPasswordSetType.INITIAL_PASSWORD);
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
   public void resetBadPasswordCountAndLockout(UserInfo userInfo) {
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
   
   
   
   public void setPasswordPolicy(PasswordPolicy passwordPolicy) {
    this.passwordPolicy = passwordPolicy;
   }

 
   
}
