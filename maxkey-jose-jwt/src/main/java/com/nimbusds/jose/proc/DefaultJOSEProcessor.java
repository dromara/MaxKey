/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.nimbusds.jose.proc;


import java.security.Key;
import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.factories.DefaultJWEDecrypterFactory;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import net.jcip.annotations.ThreadSafe;


/**
 * Default processor of {@link com.nimbusds.jose.PlainObject unsecured}
 * (plain), {@link com.nimbusds.jose.JWSObject JWS} and
 * {@link com.nimbusds.jose.JWEObject JWE} objects.
 *
 * <p>Must be configured with the following:
 *
 * <ol>
 *     <li>To verify JWS objects: A {@link JWSKeySelector JWS key selector} to
 *     determine the key candidate(s) for the signature verification. The key
 *     selection procedure is application-specific and may involve key ID
 *     lookup, a certificate check and / or other information supplied in the
 *     message {@link SecurityContext context}.</li>
 *
 *     <li>To decrypt JWE objects: A {@link JWEKeySelector JWE key selector} to
 *     determine the key candidate(s) for decryption. The key selection
 *     procedure is application-specific and may involve key ID lookup, a
 *     certificate check and / or other information supplied in the message
 *     {@link SecurityContext context}.</li>
 * </ol>
 *
 * <p>An optional context parameter is available to facilitate passing of
 * additional data between the caller and the underlying selector of key
 * candidates (in both directions).
 *
 * <p>See sections 6 of RFC 7515 (JWS) and RFC 7516 (JWE) for guidelines on key
 * selection.
 *
 * <p>This processor comes with the default {@link DefaultJWSVerifierFactory
 * JWS verifier factory} and the default {@link DefaultJWEDecrypterFactory
 * JWE decrypter factory}; they can construct verifiers / decrypters for all
 * standard JOSE algorithms implemented by the library.
 *
 * <p>Note that for security reasons this processor is hardwired to reject
 * unsecured (plain) JOSE objects. Override the {@link #process(PlainObject,
 * SecurityContext)} method if you need to handle unsecured JOSE objects as
 * well.
 *
 * <p>To process JSON Web Tokens (JWTs) use the
 * {@link com.nimbusds.jwt.proc.DefaultJWTProcessor} class.
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-06-15
 */
@ThreadSafe
public class DefaultJOSEProcessor<C extends SecurityContext> implements ConfigurableJOSEProcessor<C>{

	// Cache exceptions
	private static final BadJOSEException PLAIN_JOSE_REJECTED_EXCEPTION =
		new BadJOSEException("Unsecured (plain) JOSE objects are rejected, extend class to handle");
	private static final BadJOSEException NO_JWS_KEY_SELECTOR_EXCEPTION =
		new BadJOSEException("JWS object rejected: No JWS key selector is configured");
	private static final BadJOSEException NO_JWE_KEY_SELECTOR_EXCEPTION =
		new BadJOSEException("JWE object rejected: No JWE key selector is configured");
	private static final JOSEException NO_JWS_VERIFIER_FACTORY_EXCEPTION =
		new JOSEException("No JWS verifier is configured");
	private static final JOSEException NO_JWE_DECRYPTER_FACTORY_EXCEPTION =
		new JOSEException("No JWE decrypter is configured");
	private static final BadJOSEException NO_JWS_KEY_CANDIDATES_EXCEPTION =
		new BadJOSEException("JWS object rejected: Another algorithm expected, or no matching key(s) found");
	private static final BadJOSEException NO_JWE_KEY_CANDIDATES_EXCEPTION =
		new BadJOSEException("JWE object rejected: Another algorithm expected, or no matching key(s) found");
	private static final BadJOSEException INVALID_SIGNATURE =
		new BadJWSException("JWS object rejected: Invalid signature");
	private static final BadJOSEException NO_MATCHING_VERIFIERS_EXCEPTION =
		new BadJOSEException("JWS object rejected: No matching verifier(s) found");
	private static final BadJOSEException NO_MATCHING_DECRYPTERS_EXCEPTION =
		new BadJOSEException("JWE object rejected: No matching decrypter(s) found");


	/**
	 * The JWS key selector.
	 */
	private JWSKeySelector<C> jwsKeySelector;


	/**
	 * The JWE key selector.
	 */
	private JWEKeySelector<C> jweKeySelector;


	/**
	 * The JWS verifier factory.
	 */
	private JWSVerifierFactory jwsVerifierFactory = new DefaultJWSVerifierFactory();


	/**
	 * The JWE decrypter factory.
	 */
	private JWEDecrypterFactory jweDecrypterFactory = new DefaultJWEDecrypterFactory();


	@Override
	public JWSKeySelector<C> getJWSKeySelector() {

		return jwsKeySelector;
	}


	@Override
	public void setJWSKeySelector(final JWSKeySelector<C> jwsKeySelector) {

		this.jwsKeySelector = jwsKeySelector;
	}


	@Override
	public JWEKeySelector<C> getJWEKeySelector() {

		return jweKeySelector;
	}


	@Override
	public void setJWEKeySelector(final JWEKeySelector<C> jweKeySelector) {

		this.jweKeySelector = jweKeySelector;
	}


	@Override
	public JWSVerifierFactory getJWSVerifierFactory() {

		return jwsVerifierFactory;
	}


	@Override
	public void setJWSVerifierFactory(final JWSVerifierFactory factory) {

		jwsVerifierFactory = factory;
	}


	@Override
	public JWEDecrypterFactory getJWEDecrypterFactory() {

		return jweDecrypterFactory;
	}


	@Override
	public void setJWEDecrypterFactory(final JWEDecrypterFactory factory) {

		jweDecrypterFactory = factory;
	}


	@Override
	public Payload process(final String compactJOSE, final C context)
		throws ParseException, BadJOSEException, JOSEException {

		return process(JOSEObject.parse(compactJOSE), context);
	}


	@Override
	public Payload process(final JOSEObject joseObject, final C context)
		throws BadJOSEException, JOSEException {

		if (joseObject instanceof JWSObject) {
			return process((JWSObject)joseObject, context);
		}

		if (joseObject instanceof JWEObject) {
			return process((JWEObject)joseObject, context);
		}

		if (joseObject instanceof PlainObject) {
			return process((PlainObject)joseObject, context);
		}

		// Should never happen
		throw new JOSEException("Unexpected JOSE object type: " + joseObject.getClass());
	}


	@Override
	public Payload process(final PlainObject plainObject, C context)
		throws BadJOSEException {

		throw PLAIN_JOSE_REJECTED_EXCEPTION;
	}


	@Override
	public Payload process(final JWSObject jwsObject, C context)
		throws BadJOSEException, JOSEException {

		if (getJWSKeySelector() == null) {
			// JWS key selector may have been deliberately omitted
			throw NO_JWS_KEY_SELECTOR_EXCEPTION;
		}

		if (getJWSVerifierFactory() == null) {
			throw NO_JWS_VERIFIER_FACTORY_EXCEPTION;
		}

		List<? extends Key> keyCandidates = getJWSKeySelector().selectJWSKeys(jwsObject.getHeader(), context);

		if (keyCandidates == null || keyCandidates.isEmpty()) {
			throw NO_JWS_KEY_CANDIDATES_EXCEPTION;
		}

		ListIterator<? extends Key> it = keyCandidates.listIterator();

		while (it.hasNext()) {

			JWSVerifier verifier = getJWSVerifierFactory().createJWSVerifier(jwsObject.getHeader(), it.next());

			if (verifier == null) {
				continue;
			}

			final boolean validSignature = jwsObject.verify(verifier);

			if (validSignature) {
				return jwsObject.getPayload();
			}

			if (! it.hasNext()) {
				// No more keys to try out
				throw INVALID_SIGNATURE;
			}
		}

		throw NO_MATCHING_VERIFIERS_EXCEPTION;
	}


	@Override
	public Payload process(final JWEObject jweObject, C context)
		throws BadJOSEException, JOSEException {

		if (getJWEKeySelector() == null) {
			// JWE key selector may have been deliberately omitted
			throw NO_JWE_KEY_SELECTOR_EXCEPTION;
		}

		if (getJWEDecrypterFactory() == null) {
			throw NO_JWE_DECRYPTER_FACTORY_EXCEPTION;
		}

		List<? extends Key> keyCandidates = getJWEKeySelector().selectJWEKeys(jweObject.getHeader(), context);

		if (keyCandidates == null || keyCandidates.isEmpty()) {
			throw NO_JWE_KEY_CANDIDATES_EXCEPTION;
		}

		ListIterator<? extends Key> it = keyCandidates.listIterator();

		while (it.hasNext()) {

			JWEDecrypter decrypter = getJWEDecrypterFactory().createJWEDecrypter(jweObject.getHeader(), it.next());

			if (decrypter == null) {
				continue;
			}

			try {
				jweObject.decrypt(decrypter);

			} catch (JOSEException e) {

				if (it.hasNext()) {
					// Try next key
					continue;
				}

				// No more keys to try
				throw new BadJWEException("JWE object rejected: " + e.getMessage(), e);
			}

			if ("JWT".equalsIgnoreCase(jweObject.getHeader().getContentType())) {

				// Handle nested signed JWT, see http://tools.ietf.org/html/rfc7519#section-5.2
				JWSObject nestedJWS = jweObject.getPayload().toJWSObject();

				if (nestedJWS == null) {
					// Cannot parse payload to JWS object, return original form
					return jweObject.getPayload();
				}

				return process(nestedJWS, context);
			}

			return jweObject.getPayload();
		}

		throw NO_MATCHING_DECRYPTERS_EXCEPTION;
	}
}
