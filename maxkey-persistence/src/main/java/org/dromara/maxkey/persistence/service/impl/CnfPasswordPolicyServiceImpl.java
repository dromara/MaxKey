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

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.constants.ConstsProperties;
import org.dromara.maxkey.entity.cnf.CnfPasswordPolicy;
import org.dromara.maxkey.persistence.mapper.CnfPasswordPolicyMapper;
import org.dromara.maxkey.persistence.service.CnfPasswordPolicyService;
import org.dromara.mybatis.jpa.query.LambdaQuery;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.passay.CharacterOccurrencesRule;
import org.passay.CharacterRule;
import org.passay.DictionaryRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.Rule;
import org.passay.UsernameRule;
import org.passay.WhitespaceRule;
import org.passay.dictionary.Dictionary;
import org.passay.dictionary.DictionaryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Repository
public class CnfPasswordPolicyServiceImpl  extends JpaServiceImpl<CnfPasswordPolicyMapper,CnfPasswordPolicy> implements CnfPasswordPolicyService{
	static final Logger _logger = LoggerFactory.getLogger(CnfPasswordPolicyServiceImpl.class);
	
	//Dictionary topWeakPassword Source
    public static final String TOPWEAKPASSWORD_PROPERTYSOURCE      = "classpath:/top_weak_password.txt";
    
    //Cache PasswordPolicy in memory ONE_HOUR
    protected static final Cache<String, CnfPasswordPolicy> passwordPolicyStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    
    protected CnfPasswordPolicy passwordPolicy;
    
    ArrayList <Rule> passwordPolicyRuleList;

    private static final String PASSWORD_POLICY_KEY = "PASSWORD_POLICY_KEY";
	
	   /**
     * init PasswordPolicy and load Rules
     * @return
     */
    public CnfPasswordPolicy getPasswordPolicy() {
        passwordPolicy = passwordPolicyStore.getIfPresent(PASSWORD_POLICY_KEY);
       
        if (passwordPolicy == null) {
        	LambdaQuery<CnfPasswordPolicy>query = new LambdaQuery<>();
        	query.notNull(CnfPasswordPolicy::getId);
            passwordPolicy = this.get(query);
            _logger.debug("query PasswordPolicy : {}" , passwordPolicy);
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
            
            passwordPolicyRuleList = new ArrayList<>();
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
                                    ConstsProperties.classPathResource(TOPWEAKPASSWORD_PROPERTYSOURCE));
                    Dictionary dictionary =new DictionaryBuilder().addReader(new InputStreamReader(dictFile.getInputStream())).build();
                    passwordPolicyRuleList.add(new DictionaryRule(dictionary));
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return passwordPolicy;
    }
  
 
   public List<Rule> getPasswordPolicyRuleList() {
	   getPasswordPolicy();
		return passwordPolicyRuleList;
	}

}
