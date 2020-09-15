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
package org.maxkey.authz.oauth2.provider;

import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.db.LoginService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Dave Syer
 * 
 */
public class OAuth2UserDetailsService implements UserDetailsService {

	
    LoginService loginService;
	

	public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo;
		try {
		    userInfo = loginService.loadUserInfo(username, "");
		} catch (NoSuchClientException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
		
		
		return new User(username, userInfo.getPassword(), loginService.grantAuthority(userInfo));
	}

}
