package org.maxkey.springboot.oauthclient.config;

import org.maxkey.springboot.oauthclient.http.HttpsTrusts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {
	Logger log = LoggerFactory.getLogger(ResourceServerConfiguration.class);
	
	@Value("${maxkey-auth-url}") 
	String maxkeyAuthUrl;
	
	@Value("${security.oauth2.client.user-authorization-uri}") 
	String userAuthorizationUri;
	
	@Value("${security.oauth2.client.access-token-uri}") 
	String accessTokenUri;
	
	@Value("${security.oauth2.resource.user-info-uri}") 
	String userInfoUri;
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
       //http.antMatcher("/orgs/**").antMatcher("/userinfo").antMatcher("/login").authorizeRequests().anyRequest().authenticated();
    	http.authorizeRequests().anyRequest().authenticated().and().csrf().disable();
    	log.info("UserAuthorizationUri {}" ,userAuthorizationUri);
    	log.info("AccessTokenUri {}" ,accessTokenUri);
    	log.info("UserInfoUri {}" ,userInfoUri);
    	if(accessTokenUri.startsWith("https")) {
    		HttpsTrusts.beforeConnection();
    	}
    	log.debug("ResourceServerConfiguration");
 
    }
}
