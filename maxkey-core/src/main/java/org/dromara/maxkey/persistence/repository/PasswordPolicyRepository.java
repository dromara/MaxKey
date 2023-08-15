/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.constants.ConstsProperties;
import org.dromara.maxkey.entity.PasswordPolicy;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class PasswordPolicyRepository {
    private static Logger _logger = LoggerFactory.getLogger(PasswordPolicyRepository.class);
    
    //Dictionary topWeakPassword Source
    public static final String topWeakPasswordPropertySource      = 
            "classpath:/top_weak_password.txt";
    
    //Cache PasswordPolicy in memory ONE_HOUR
    protected static final Cache<String, PasswordPolicy> passwordPolicyStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    
    protected PasswordPolicy passwordPolicy;
    
    protected JdbcTemplate jdbcTemplate;
    
    ArrayList <Rule> passwordPolicyRuleList;

    private static final String PASSWORD_POLICY_KEY = "PASSWORD_POLICY_KEY";
    
    private static final String PASSWORD_POLICY_SELECT_STATEMENT = "select * from mxk_password_policy ";

    public PasswordPolicyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * init PasswordPolicy and load Rules
     * @return
     */
    public PasswordPolicy getPasswordPolicy() {
        passwordPolicy = passwordPolicyStore.getIfPresent(PASSWORD_POLICY_KEY);
       
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
                                    ConstsProperties.classPathResource(topWeakPasswordPropertySource));
                    Dictionary dictionary =new DictionaryBuilder().addReader(new InputStreamReader(dictFile.getInputStream())).build();
                    passwordPolicyRuleList.add(new DictionaryRule(dictionary));
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return passwordPolicy;
    }
  
 
   public ArrayList<Rule> getPasswordPolicyRuleList() {
	   getPasswordPolicy();
		return passwordPolicyRuleList;
	}


public class PasswordPolicyRowMapper implements RowMapper<PasswordPolicy> {

       @Override
       public PasswordPolicy mapRow(ResultSet rs, int rowNum) throws SQLException {
           PasswordPolicy passwordPolicy = new PasswordPolicy();
           passwordPolicy.setId(rs.getString("id"));
           passwordPolicy.setMinLength(rs.getInt("minlength"));
           passwordPolicy.setMaxLength(rs.getInt("maxlength"));
           passwordPolicy.setLowerCase(rs.getInt("lowercase"));
           passwordPolicy.setUpperCase(rs.getInt("uppercase"));
           passwordPolicy.setDigits(rs.getInt("digits"));
           passwordPolicy.setSpecialChar(rs.getInt("specialchar"));
           passwordPolicy.setAttempts(rs.getInt("attempts"));
           passwordPolicy.setDuration(rs.getInt("duration"));
           passwordPolicy.setExpiration(rs.getInt("expiration"));
           passwordPolicy.setUsername(rs.getInt("username"));
           passwordPolicy.setHistory(rs.getInt("history"));
           passwordPolicy.setDictionary(rs.getInt("dictionary"));
           passwordPolicy.setAlphabetical(rs.getInt("alphabetical"));
           passwordPolicy.setNumerical(rs.getInt("numerical"));
           passwordPolicy.setQwerty(rs.getInt("qwerty"));
           passwordPolicy.setOccurances(rs.getInt("occurances"));
           return passwordPolicy;
       }

   }
}
