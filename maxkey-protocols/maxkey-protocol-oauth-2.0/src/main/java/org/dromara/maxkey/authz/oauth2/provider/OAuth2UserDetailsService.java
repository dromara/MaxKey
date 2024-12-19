/*
 * Copyright 2006-2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.dromara.maxkey.authz.oauth2.provider;

import java.util.List;

import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.LoginService;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Dave Syer
 * 
 */
public class OAuth2UserDetailsService implements UserDetailsService {
	 private static final Logger _logger = 
	            LoggerFactory.getLogger(OAuth2UserDetailsService.class);
	
    LoginService loginRepository;
	
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo;
		try {
		    userInfo = loginRepository.find(username, "");
		} catch (NoSuchClientException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
		
		String onlineTickitId = WebConstants.ONLINE_TICKET_PREFIX + "-" + java.util.UUID.randomUUID().toString().toLowerCase();
		
		SignPrincipal principal = new SignPrincipal(userInfo);
		Session onlineTicket = new Session(onlineTickitId);
		//set OnlineTicket
		principal.setSessionId(onlineTicket.getId());
        
        List<GrantedAuthority> grantedAuthoritys = loginRepository.grantAuthority(userInfo);
        principal.setAuthenticated(true);
        
        for(GrantedAuthority administratorsAuthority : AbstractAuthenticationProvider.grantedAdministratorsAuthoritys) {
            if(grantedAuthoritys.contains(administratorsAuthority)) {
            	principal.setRoleAdministrators(true);
                _logger.trace("ROLE ADMINISTRATORS Authentication .");
            }
        }
        _logger.debug("Granted Authority " + grantedAuthoritys);
        
        principal.setGrantedAuthorityApps(grantedAuthoritys);
        
		return principal;
	}

	public void setLoginRepository(LoginService loginRepository) {
		this.loginRepository = loginRepository;
	}

    
}
