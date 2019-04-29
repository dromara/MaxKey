/*
 * Copyright 2009-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.kerberos.authentication;

import java.io.UnsupportedEncodingException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Collection;

import javax.security.auth.Subject;

import org.ietf.jgss.GSSContext;
import org.ietf.jgss.MessageProp;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;

/**
 * <p>Holds the Kerberos/SPNEGO token for requesting a kerberized service and is
 * also the output of <code>KerberosServiceAuthenticationProvider</code>.</p>
 * <p>Will mostly be created in <code>SpnegoAuthenticationProcessingFilter</code>
 * and authenticated in <code>KerberosServiceAuthenticationProvider</code>.</p>
 *
 * This token cannot be re-authenticated, as you will get a Kerberos Reply
 * error.
 *
 * @author Mike Wiesner
 * @author Jeremy Stone
 * @since 1.0
 * @see KerberosServiceAuthenticationProvider
 */
public class KerberosServiceRequestToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 395488921064775014L;

	private final byte[] token;

	private final Object principal;

	private final transient KerberosTicketValidation ticketValidation;

	/**
	 * Creates an authenticated token, normally used as an output of an
	 * authentication provider.
	 *
	 * @param principal the user principal (mostly of instance <code>UserDetails</code>)
	 * @param ticketValidation result of ticket validation
	 * @param authorities the authorities which are granted to the user
	 * @param token the Kerberos/SPNEGO token
	 * @see UserDetails
	 */
	public KerberosServiceRequestToken(Object principal, KerberosTicketValidation ticketValidation,
			Collection<? extends GrantedAuthority> authorities, byte[] token) {
		super(authorities);
		this.token = token;
		this.principal = principal;
		this.ticketValidation = ticketValidation;
		super.setAuthenticated(true);
	}

	/**
	 * Creates an unauthenticated instance which should then be authenticated by
	 * <code>KerberosServiceAuthenticationProvider</code>.
	 *
	 * @param token Kerberos/SPNEGO token
	 * @see KerberosServiceAuthenticationProvider
	 */
	public KerberosServiceRequestToken(byte[] token) {
		super(null);
		this.token = token;
		this.ticketValidation = null;
		this.principal = null;
	}

	/**
	 * Calculates hashcode based on the Kerberos token
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(token);
		return result;
	}

	/**
	 * equals() is based only on the Kerberos token
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KerberosServiceRequestToken other = (KerberosServiceRequestToken) obj;
		if (!Arrays.equals(token, other.token))
			return false;
		return true;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	/**
	 * Returns the Kerberos token
	 * @return the token data
	 */
	public byte[] getToken() {
		return this.token;
	}

	/**
	 * Gets the ticket validation
	 *
	 * @return the ticket validation (which will be null if the token is unauthenticated)
	 */
	public KerberosTicketValidation getTicketValidation() {
		return ticketValidation;
	}

	/**
	 * Determines whether an authenticated token has a response token
	 *
	 * @return whether a response token is available
	 */
	public boolean hasResponseToken() {
		return ticketValidation != null && ticketValidation.responseToken() != null;
	}

	/**
	 * Gets the (Base64) encoded response token assuming one is available.
	 *
	 * @return encoded response token
	 */
	public String getEncodedResponseToken() {
		if (!hasResponseToken())
			throw new IllegalStateException("Unauthenticated or no response token");

		try {
			return new String(Base64.encode(ticketValidation.responseToken()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Unable to encode response token", e);
		}
	}

	/**
	 * Unwraps an encrypted message using the gss context
	 *
	 * @param data the data
	 * @param offset data offset
	 * @param length data length
	 * @return the decrypted message
	 * @throws PrivilegedActionException if jaas throws and error
	 */
	public byte[] decrypt(final byte[] data, final int offset, final int length) throws PrivilegedActionException {
		return Subject.doAs(getTicketValidation().subject(), new PrivilegedExceptionAction<byte[]>() {
			public byte[] run() throws Exception {
				final GSSContext context = getTicketValidation().getGssContext();
				return context.unwrap(data, offset, length, new MessageProp(true));
			}
		});
	}

	/**
	 * Unwraps an encrypted message using the gss context
	 *
	 * @param data the data
	 * @return the decrypted message
	 * @throws PrivilegedActionException if jaas throws and error
	 */
	public byte[] decrypt(final byte[] data) throws PrivilegedActionException {
		return decrypt(data, 0, data.length);
	}

	/**
	 * Wraps an message using the gss context
	 *
	 * @param data the data
	 * @param offset data offset
	 * @param length data length
	 * @return the encrypted message
	 * @throws PrivilegedActionException if jaas throws and error
	 */
	public byte[] encrypt(final byte[] data, final int offset, final int length) throws PrivilegedActionException {
		return Subject.doAs(getTicketValidation().subject(), new PrivilegedExceptionAction<byte[]>() {
			public byte[] run() throws Exception {
				final GSSContext context = getTicketValidation().getGssContext();
				return context.wrap(data, offset, length, new MessageProp(true));
			}
		});
	}

	/**
	 * Wraps an message using the gss context
	 *
	 * @param data the data
	 * @return the encrypted message
	 * @throws PrivilegedActionException if jaas throws and error
	 */
	public byte[] encrypt(final byte[] data) throws PrivilegedActionException {
		return encrypt(data, 0, data.length);
	}

}
