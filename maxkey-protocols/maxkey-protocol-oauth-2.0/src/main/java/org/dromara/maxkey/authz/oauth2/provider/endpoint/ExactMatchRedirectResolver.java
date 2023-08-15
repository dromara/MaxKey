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
 

package org.dromara.maxkey.authz.oauth2.provider.endpoint;



/**
 * Strict implementation for a redirect resolver which requires
 * an exact match between the registered and requested redirect_uri.
 *
 * @author Ryan Heaton
 * @author Dave Syer
 */
public class ExactMatchRedirectResolver extends DefaultRedirectResolver {

	/**
	 * Whether the requested redirect URI "matches" the specified redirect URI. This implementation tests strict
	 * equality.
	 *
	 * @param requestedRedirect The requested redirect URI.
	 * @param redirectUri The registered redirect URI.
	 * @return Whether the requested redirect URI "matches" the specified redirect URI.
	 */
	protected boolean redirectMatches(String requestedRedirect, String redirectUri) {
		return requestedRedirect.equals(redirectUri);
	}

}
