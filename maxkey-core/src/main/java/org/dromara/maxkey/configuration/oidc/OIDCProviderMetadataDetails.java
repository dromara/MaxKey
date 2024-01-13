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
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * OIDCProviderMetadataDetails.
 * @author cm
 *
 */
public class OIDCProviderMetadataDetails implements OIDCProviderMetadata {
    protected String issuer;

    protected URI authorizationEndpoint;

    protected URI tokenEndpoint;

    protected URI userinfoEndpoint;

    protected URI jwksUri;

    protected URI registrationEndpoint;

    protected Set<String> scopesSupported;

    protected Set<String> responseTypesSupported;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public URI getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(URI authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    public URI getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(URI tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public URI getUserinfoEndpoint() {
        return userinfoEndpoint;
    }

    public void setUserinfoEndpoint(URI userinfoEndpoint) {
        this.userinfoEndpoint = userinfoEndpoint;
    }

    public URI getJwksUri() {
        return jwksUri;
    }

    public void setJwksUri(URI jwksUri) {
        this.jwksUri = jwksUri;
    }

    public URI getRegistrationEndpoint() {
        return registrationEndpoint;
    }

    public void setRegistrationEndpoint(URI registrationEndpoint) {
        this.registrationEndpoint = registrationEndpoint;
    }

    public Set<String> getScopesSupported() {
        return scopesSupported;
    }

    public void setScopesSupported(Set<String> scopesSupported) {
        this.scopesSupported = scopesSupported;
    }

    public Set<String> getResponseTypesSupported() {
        return responseTypesSupported;
    }

    public void setResponseTypesSupported(Set<String> responseTypesSupported) {
        this.responseTypesSupported = responseTypesSupported;
    }

    @Override
    public String toString() {
        final int maxLen = 4;
        StringBuilder builder = new StringBuilder();
        builder.append("OIDCProviderMetadataDetails [issuer=");
        builder.append(issuer);
        builder.append(", authorizationEndpoint=");
        builder.append(authorizationEndpoint);
        builder.append(", tokenEndpoint=");
        builder.append(tokenEndpoint);
        builder.append(", userinfoEndpoint=");
        builder.append(userinfoEndpoint);
        builder.append(", jwksUri=");
        builder.append(jwksUri);
        builder.append(", registrationEndpoint=");
        builder.append(registrationEndpoint);
        builder.append(", scopesSupported=");
        builder.append(scopesSupported != null ? toString(scopesSupported, maxLen) : null);
        builder.append(", responseTypesSupported=");
        builder.append(responseTypesSupported != null ? toString(responseTypesSupported, maxLen) : null);
        builder.append("]");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

    //  Complete remaining properties from
    // http://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata
    
}
