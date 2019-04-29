/*
*   Copyright 2010 James Cox <james.s.cox@gmail.com>
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package com.connsec.spring;

import java.util.Collection;

import org.opensaml.saml2.core.Response;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author jcox
 *
 */
public class SAMLAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private final Object credentials;
	 
	/**
	 * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link
     * #isAuthenticated()} will return <code>false</code>.
     * 
	 * @param response
	 * @param credentials
	 */
	public SAMLAuthenticationToken(Response response, String credentials,   Collection<? extends GrantedAuthority> authorities) {
		  super(authorities);
		  this.principal = response;
		  this.credentials = credentials;
		  setAuthenticated(false);

	}

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
     * implementations that are satisfied with producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
  
     * @param user
     * @param credentials
     * @param authorities
     */
    public SAMLAuthenticationToken(User user, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = user;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	/* taken from Spring Security's UsernamePasswordAuthenticationToken implementation
	 * @see org.springframework.security.authentication.AbstractAuthenticationToken#setAuthenticated(boolean)
	 */
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public String toString() {
		return "SAMLAuthenticationToken [principal=" + principal
				+ ", credentials=" + credentials + "]";
	}	
	
	
}
