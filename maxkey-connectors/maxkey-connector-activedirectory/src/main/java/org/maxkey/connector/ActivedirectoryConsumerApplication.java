package org.maxkey.connector;

import org.maxkey.constants.ConstantsProperties;
import org.maxkey.persistence.ldap.ActiveDirectoryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ConstantsProperties.applicationPropertySource)
@SpringBootApplication
@ComponentScan(basePackages = {
        "org.maxkey.connector",
        "org.maxkey.connector.receiver",
        "org.maxkey.connector.activedirectory"
    })
public class ActivedirectoryConsumerApplication {

	public static void main(String[] args) {
	    ConfigurableApplicationContext context = SpringApplication.run(ActivedirectoryConsumerApplication.class, args);
		  
	}
	
	//@Bean(name = "activeDirectoryUtils")
	public ActiveDirectoryUtils getLdapConnection(
			@Value("${config.connector.activedirectory.providerUrl}") String providerUrl,
			@Value("${config.connector.activedirectory.principal}") String principal,
			@Value("${config.connector.activedirectory.credentials}") String credentials,
			@Value("${config.connector.activedirectory.baseDN}") String baseDn,
			@Value("${config.connector.activedirectory.domain}") String domain,
			@Value("${config.connector.activedirectory.trustStore}") String trustStore,
			@Value("${config.connector.activedirectory.trustStore.password}") String trustStorePassword
			)throws Exception{
		ActiveDirectoryUtils ldapUtils=new ActiveDirectoryUtils(
				providerUrl,
				principal,
				credentials,
				baseDn,
				domain);
		
		ldapUtils.setTrustStore(trustStore);
		ldapUtils.setTrustStorePassword(trustStorePassword);
		ldapUtils.setSsl(true);
		
		if(ldapUtils.openConnection()==null){
			 throw new Exception("connection to Ldap Error.");   
		}
		return ldapUtils;
	}

}
