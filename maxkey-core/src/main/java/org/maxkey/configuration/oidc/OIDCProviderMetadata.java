package org.maxkey.configuration.oidc;

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