package org.maxkey.authz.oauth2.provider.implicit;

import java.util.concurrent.ConcurrentHashMap;

import org.maxkey.authz.oauth2.provider.OAuth2Request;
import org.maxkey.authz.oauth2.provider.TokenRequest;

/**
 * In-memory implementation of the ImplicitGrantService.
 * 
 * @author Amanda Anganes
 *
 */
@SuppressWarnings("deprecation")
public class InMemoryImplicitGrantService implements ImplicitGrantService {

	protected final ConcurrentHashMap<TokenRequest, OAuth2Request> requestStore = new ConcurrentHashMap<TokenRequest, OAuth2Request>();
	
	public void store(OAuth2Request originalRequest, TokenRequest tokenRequest) {
		this.requestStore.put(tokenRequest, originalRequest);
	}

	public OAuth2Request remove(TokenRequest tokenRequest) {
		OAuth2Request request = this.requestStore.remove(tokenRequest);
		return request;
	}

}
