package org.maxkey.authz.saml20.consumer.spring;

import org.springframework.security.core.AuthenticationException;

/**
 * Indicates that the Service Provider was the entity that failed the authentication.
 * This could be because of an invalid Signature, invalid Message structure....
 * 
 * The user 'may' be authenticated with the Identity Provider.
 * 
 * Further attempts to authenticate the user with the IDP would most probably
 * be fruitless;
 * 
 * 
 * @author jcox
 *
 */
public class ServiceProviderAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817095932085915398L;

	public ServiceProviderAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	public ServiceProviderAuthenticationException(String msg) {
		super(msg);
	}

}
