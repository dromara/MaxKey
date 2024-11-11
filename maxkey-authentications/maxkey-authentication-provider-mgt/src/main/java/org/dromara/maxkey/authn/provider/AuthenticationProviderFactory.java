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
package org.dromara.maxkey.authn.provider;

import java.util.concurrent.ConcurrentHashMap;

import org.dromara.maxkey.authn.LoginCredential;
import org.springframework.security.core.Authentication;

public class AuthenticationProviderFactory extends AbstractAuthenticationProvider {

    private  static ConcurrentHashMap<String,AbstractAuthenticationProvider> providers = new ConcurrentHashMap<>();
    
    @Override
    public Authentication authenticate(LoginCredential authentication){
    	if(authentication.getAuthType().equalsIgnoreCase("trusted")) {
    		//risk remove
    		return null;
    	}
    	AbstractAuthenticationProvider provider = providers.get(authentication.getAuthType() + PROVIDER_SUFFIX);
    	
    	return provider == null ? null : provider.doAuthenticate(authentication);
    }
    
    @Override
    public Authentication authenticate(LoginCredential authentication,boolean trusted){
    	AbstractAuthenticationProvider provider = providers.get(AuthType.TRUSTED + PROVIDER_SUFFIX);
    	return provider.doAuthenticate(authentication);
    }
    
    public void addAuthenticationProvider(AbstractAuthenticationProvider provider) {
    	providers.put(provider.getProviderName(), provider);
    }

	@Override
	public String getProviderName() {
		return "AuthenticationProviderFactory";
	}

	@Override
	public Authentication doAuthenticate(LoginCredential authentication) {
		//AuthenticationProvider Factory do nothing 
		return null;
	}
}
