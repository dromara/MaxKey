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
 

package org.maxkey.autoconfigure;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.password.LdapShaPasswordEncoder;
import org.maxkey.crypto.password.Md4PasswordEncoder;
import org.maxkey.crypto.password.NoOpPasswordEncoder;
import org.maxkey.crypto.password.MessageDigestPasswordEncoder;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.crypto.password.SM3PasswordEncoder;
import org.maxkey.crypto.password.StandardPasswordEncoder;
import org.maxkey.util.IdGenerator;
import org.maxkey.util.SnowFlakeId;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;


@Configuration
public class ApplicationAutoConfiguration  implements InitializingBean {
    private static final  Logger _logger = 
            LoggerFactory.getLogger(ApplicationAutoConfiguration.class);
    
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(name = "passwordReciprocal")
    public PasswordReciprocal passwordReciprocal() {
        return new PasswordReciprocal();
    }
    
    
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    
    /**
     * Authentication Password Encoder .
     * @return
     */
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String ,PasswordEncoder > encoders = new HashMap<String ,PasswordEncoder>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("plain", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        //md
        encoders.put("md4", new Md4PasswordEncoder());
        encoders.put("md5", new MessageDigestPasswordEncoder("MD5"));
        //sha
        encoders.put("sha1", new StandardPasswordEncoder("SHA-1",""));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("sha384", new StandardPasswordEncoder("SHA-384",""));
        encoders.put("sha512", new StandardPasswordEncoder("SHA-512",""));
        
        encoders.put("sm3", new SM3PasswordEncoder());
        
        encoders.put("ldap", new LdapShaPasswordEncoder());
        
        //idForEncode is default for encoder
        PasswordEncoder passwordEncoder =
            new DelegatingPasswordEncoder(idForEncode, encoders);
       
        if(_logger.isDebugEnabled()) {
        	 _logger.debug("Password Encoders :");
	        for (String key : encoders.keySet()) {
	            _logger.debug(key + "=" + encoders.get(key));
	        }
        }
        _logger.debug("default encoder " + idForEncode);
        return passwordEncoder;
    }

    
    /**
     * keyStoreLoader .
     * @return
     */
    @Bean(name = "keyStoreLoader")
    public KeyStoreLoader keyStoreLoader(
            @Value("${maxkey.saml.v20.idp.issuing.entity.id}") String entityName,
            @Value("${maxkey.saml.v20.idp.keystore.password}") String keystorePassword,
            @Value("${maxkey.saml.v20.idp.keystore}") Resource keystoreFile) {
        KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
        keyStoreLoader.setEntityName(entityName);
        keyStoreLoader.setKeystorePassword(keystorePassword);
        keyStoreLoader.setKeystoreFile(keystoreFile);
        return keyStoreLoader;
    }
    
    /**
     * spKeyStoreLoader .
     * @return
     */
    @Bean(name = "spKeyStoreLoader")
    public KeyStoreLoader spKeyStoreLoader(
            @Value("${maxkey.saml.v20.sp.issuing.entity.id}") String entityName,
            @Value("${maxkey.saml.v20.sp.keystore.password}") String keystorePassword,
            @Value("${maxkey.saml.v20.sp.keystore}") Resource keystoreFile) {
        KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
        keyStoreLoader.setEntityName(entityName);
        keyStoreLoader.setKeystorePassword(keystorePassword);
        keyStoreLoader.setKeystoreFile(keystoreFile);
        return keyStoreLoader;
    }
    
    /**
     * spKeyStoreLoader .
     * @return
     */
    @Bean(name = "spIssuingEntityName")
    public String spIssuingEntityName(
            @Value("${maxkey.saml.v20.sp.issuing.entity.id}") String spIssuingEntityName) {
        return spIssuingEntityName;
    }
    
    
    /**
     * spKeyStoreLoader .
     * @return
     */
    @Bean(name = "idGenerator")
    public IdGenerator idGenerator(
            @Value("${maxkey.id.strategy:SnowFlake}") String strategy,
            @Value("${maxkey.id.datacenterId:0}") int datacenterId,
            @Value("${maxkey.id.machineId:0}") int machineId) {
    	IdGenerator idGenerator = new IdGenerator(strategy);
    	SnowFlakeId SnowFlakeId = new SnowFlakeId(datacenterId,machineId);
    	idGenerator.setSnowFlakeId(SnowFlakeId);
    	WebContext.idGenerator = idGenerator;
        return idGenerator;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
