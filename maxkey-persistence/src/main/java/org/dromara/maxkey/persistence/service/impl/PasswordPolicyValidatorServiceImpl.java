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
 

package org.dromara.maxkey.persistence.service.impl;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.crypto.password.PasswordGen;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.cnf.CnfPasswordPolicy;
import org.dromara.maxkey.persistence.service.CnfPasswordPolicyService;
import org.dromara.maxkey.persistence.service.PasswordPolicyValidatorService;
import org.dromara.maxkey.web.WebContext;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;

public class PasswordPolicyValidatorServiceImpl  implements  PasswordPolicyValidatorService{
    static final Logger _logger = LoggerFactory.getLogger(PasswordPolicyValidatorServiceImpl.class);
    
    CnfPasswordPolicyService cnfPasswordPolicyService;
    
    MessageSource messageSource;
    
    public static final String PASSWORD_POLICY_VALIDATE_RESULT = "PASSWORD_POLICY_SESSION_VALIDATE_RESULT_KEY";
 
    public PasswordPolicyValidatorServiceImpl() {
    }
    
    public PasswordPolicyValidatorServiceImpl(CnfPasswordPolicyService cnfPasswordPolicyService,MessageSource messageSource) {
        this.messageSource=messageSource;
        this.cnfPasswordPolicyService = cnfPasswordPolicyService;
        
    }
    
    public CnfPasswordPolicy getPasswordPolicy(){
    	return cnfPasswordPolicyService.getPasswordPolicy();
    }
    
    
    /**
     * static validator .
     * @param userInfo
     * @return boolean
     */
   public boolean validator(ChangePassword changePassword) {
       
       
       String password = changePassword.getPassword();
       String username = changePassword.getUsername();
       
       if(StringUtils.isBlank(username)){
           _logger.debug("username  is Empty ");
           return false;
       }
       
       if(StringUtils.isBlank(password)){
           _logger.debug("password  is Empty ");
           return false;
       }
       
       PasswordValidator validator = new PasswordValidator(
               new PasswordPolicyMessageResolver(messageSource),cnfPasswordPolicyService.getPasswordPolicyRuleList());
       
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
           WebContext.setAttribute(PasswordPolicyValidatorServiceImpl.PASSWORD_POLICY_VALIDATE_RESULT, passwordPolicyMessage);
           return false;
       }
   }
   

   
   public String generateRandomPassword() {
       CnfPasswordPolicy passwordPolicy = cnfPasswordPolicyService.getPasswordPolicy();
       
       PasswordGen passwordGen = new PasswordGen(
               passwordPolicy.getRandomPasswordLength()
       );
       
       return passwordGen.gen(
               passwordPolicy.getLowerCase(), 
               passwordPolicy.getUpperCase(), 
               passwordPolicy.getDigits(), 
               passwordPolicy.getSpecialChar());
   }

	public class PasswordPolicyMessageResolver  implements MessageResolver{

	    /** A accessor for Spring's {@link MessageSource} */
	    private final MessageSourceAccessor messageSourceAccessor;

	    /** The {@link MessageResolver} for fallback */
	    private final MessageResolver fallbackMessageResolver = new PropertiesMessageResolver();

	    /**
	     * Create a new instance with the locale associated with the current thread.
	     * @param messageSource a message source managed by spring
	     */
	    public PasswordPolicyMessageResolver(final MessageSource messageSource)
	    {
	      this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	    }

	    /**
	     * Create a new instance with the specified locale.
	     * @param messageSource a message source managed by spring
	     * @param locale the locale to use for message access
	     */
	    public PasswordPolicyMessageResolver(final MessageSource messageSource, final Locale locale)
	    {
	      this.messageSourceAccessor = new MessageSourceAccessor(messageSource, locale);
	    }

	    /**
	     * Resolves the message for the supplied rule result detail using Spring's {@link MessageSource}.
	     * (If the message can't retrieve from a {@link MessageSource}, return default message provided by passay)
	     * @param detail rule result detail
	     * @return message for the detail error code
	     */
	    @Override
	    public String resolve(final RuleResultDetail detail)
	    {
	      try {
	        return this.messageSourceAccessor.getMessage("PasswordPolicy."+detail.getErrorCode(), detail.getValues());
	      } catch (NoSuchMessageException e) {
	        return this.fallbackMessageResolver.resolve(detail);
	      }
	    }
	}

}


