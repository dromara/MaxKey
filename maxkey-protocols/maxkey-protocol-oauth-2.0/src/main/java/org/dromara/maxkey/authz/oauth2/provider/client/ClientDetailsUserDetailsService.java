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
package org.dromara.maxkey.authz.oauth2.provider.client;

import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.NoSuchClientException;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Dave Syer
 * 
 */
public class ClientDetailsUserDetailsService implements UserDetailsService {

	private final ClientDetailsService clientDetailsService;
	private PasswordEncoder passwordEncoder;
	
	public ClientDetailsUserDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}
	
	/**
	 * @param passwordEncoder the password encoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClientDetails clientDetails;
		try {
			clientDetails = clientDetailsService.loadClientByClientId(username,true);
		} catch (NoSuchClientException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
		
		String clientSecret = clientDetails.getClientSecret();
		if (clientSecret== null || clientSecret.trim().length()==0) {
			clientSecret = "";
		}else{
			if(passwordEncoder instanceof PasswordReciprocal){
				clientSecret = ((PasswordReciprocal)passwordEncoder).decoder(clientSecret);
			}
		}
		
		return new User(username, clientSecret, clientDetails.getAuthorities());
	}

}
