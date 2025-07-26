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
 

package org.dromara.maxkey.configuration.oidc;

import java.net.URI;
import java.util.Set;

public interface OIDCProviderMetadata {

	public String getIssuer();

	public void setIssuer(String issuer);

	public URI getAuthorizationEndpoint();

	public void setAuthorizationEndpoint(URI authorizationEndpoint);

	public URI getTokenEndpoint();

	public void setTokenEndpoint(URI tokenEndpoint);

	public URI getUserinfoEndpoint();

	public void setUserinfoEndpoint(URI userinfoEndpoint);

	public URI getJwksUri();

	public void setJwksUri(URI jwksUri);

	public URI getRegistrationEndpoint();

	public void setRegistrationEndpoint(URI registrationEndpoint);

	public Set<String> getScopesSupported();

	public void setScopesSupported(Set<String> scopesSupported);

	public Set<String> getResponseTypesSupported();

	public void setResponseTypesSupported(Set<String> responseTypesSupported);

}
