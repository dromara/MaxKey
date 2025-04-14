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
 

package org.dromara.maxkey.autoconfigure;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.crypto.password.LdapShaPasswordEncoder;
import org.dromara.maxkey.crypto.password.Md4PasswordEncoder;
import org.dromara.maxkey.crypto.password.MessageDigestPasswordEncoder;
import org.dromara.maxkey.crypto.password.NoOpPasswordEncoder;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.crypto.password.SM3PasswordEncoder;
import org.dromara.maxkey.crypto.password.StandardPasswordEncoder;
import org.dromara.maxkey.persistence.cache.InMemoryMomentaryService;
import org.dromara.maxkey.persistence.cache.MomentaryService;
import org.dromara.maxkey.persistence.cache.RedisMomentaryService;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.dromara.maxkey.util.IdGenerator;
import org.dromara.maxkey.util.SnowFlakeId;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@AutoConfiguration
public class ApplicationAutoConfiguration {
    static final  Logger _logger = LoggerFactory.getLogger(ApplicationAutoConfiguration.class);

    @Bean
    PasswordReciprocal passwordReciprocal() {
        return new PasswordReciprocal();
    }

    @Bean
    DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Authentication Password Encoder .
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(
            @Value("${maxkey.crypto.password.encoder:bcrypt}") String idForEncode) {
    	Map<String ,PasswordEncoder > encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("plain", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
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
       
        if(_logger.isTraceEnabled()) {
        	 _logger.trace("Password Encoders :");
	        for (Map.Entry<String,PasswordEncoder> entry : encoders.entrySet()) {
	            _logger.trace("{}= {}" ,String.format("%-10s", entry.getKey()), entry.getValue().getClass().getName());
	        }
        }
        _logger.debug("{} is default encoder" , idForEncode);
        return passwordEncoder;
    }


    /**
     * keyStoreLoader .
     * @return
     */
    @Bean
    KeyStoreLoader keyStoreLoader(
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
    @Bean
    KeyStoreLoader serviceProviderKeyStoreLoader(
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
    @Bean
    String spIssuingEntityName(
            @Value("${maxkey.saml.v20.sp.issuing.entity.id}") String spIssuingEntityName) {
        return spIssuingEntityName;
    }

    /**
     * Id Generator .
     * @return
     */
    @Bean
    IdGenerator idGenerator(
            @Value("${maxkey.id.strategy:SnowFlake}") String strategy,
            @Value("${maxkey.id.datacenterId:0}") int datacenterId,
            @Value("${maxkey.id.machineId:0}") int machineId) {
    	IdGenerator idGenerator = new IdGenerator(strategy);
    	SnowFlakeId snowFlakeId = new SnowFlakeId(datacenterId,machineId);
    	idGenerator.setSnowFlakeId(snowFlakeId);
    	WebContext.setIdGenerator(idGenerator); 
        return idGenerator;
    }


    @Bean
    MomentaryService momentaryService(
            RedisConnectionFactory redisConnFactory,
            @Value("${maxkey.server.persistence}") int persistence) {
    	MomentaryService momentaryService;
    	if (persistence == ConstsPersistence.REDIS) {
    		momentaryService = new RedisMomentaryService(redisConnFactory);
    	}else {
    		momentaryService = new InMemoryMomentaryService();
    	}
    	return momentaryService;
    }
    
}
