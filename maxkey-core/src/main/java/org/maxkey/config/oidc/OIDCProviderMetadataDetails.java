package org.maxkey.config.oidc;

import java.net.URI;
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

    // TODO: Complete remaining properties from
    // http://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata
}
