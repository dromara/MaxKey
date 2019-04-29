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

package com.nimbusds.jose;


import java.text.ParseException;

import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.StandardCharset;
import net.jcip.annotations.ThreadSafe;


/**
 * JSON Web Signature (JWS) secured object. This class is thread-safe.
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-07-26
 */
@ThreadSafe
public class JWSObject extends JOSEObject {


	private static final long serialVersionUID = 1L;


	/**
	 * Enumeration of the states of a JSON Web Signature (JWS) object.
	 */
	public enum State {


		/**
		 * The JWS object is created but not signed yet.
		 */
		UNSIGNED,


		/**
		 * The JWS object is signed but its signature is not verified.
		 */
		SIGNED,


		/**
		 * The JWS object is signed and its signature was successfully verified.
		 */
		VERIFIED
	}


	/**
	 * The header.
	 */
	private final JWSHeader header;


	/**
	 * The signing input for this JWS object.
	 *
	 * <p>Format:
	 *
	 * <pre>
	 * [header-base64url].[payload-base64url]
	 * </pre>
	 */
	private final String signingInputString;


	/**
	 * The signature, {@code null} if not signed.
	 */
	private Base64URL signature;


	/**
	 * The JWS object state.
	 */
	private State state;


	/**
	 * Creates a new to-be-signed JSON Web Signature (JWS) object with the 
	 * specified header and payload. The initial state will be 
	 * {@link State#UNSIGNED unsigned}.
	 *
	 * @param header  The JWS header. Must not be {@code null}.
	 * @param payload The payload. Must not be {@code null}.
	 */
	public JWSObject(final JWSHeader header, final Payload payload) {

		if (header == null) {

			throw new IllegalArgumentException("The JWS header must not be null");
		}

		this.header = header;

		if (payload == null) {

			throw new IllegalArgumentException("The payload must not be null");
		}

		setPayload(payload);

		signingInputString = composeSigningInput(header.toBase64URL(), payload.toBase64URL());

		signature = null;

		state = State.UNSIGNED;
	}


	/**
	 * Creates a new signed JSON Web Signature (JWS) object with the 
	 * specified serialised parts. The state will be 
	 * {@link State#SIGNED signed}.
	 *
	 * @param firstPart  The first part, corresponding to the JWS header. 
	 *                   Must not be {@code null}.
	 * @param secondPart The second part, corresponding to the payload. Must
	 *                   not be {@code null}.
	 * @param thirdPart  The third part, corresponding to the signature.
	 *                   Must not be {@code null}.
	 *
	 * @throws ParseException If parsing of the serialised parts failed.
	 */
	public JWSObject(final Base64URL firstPart, final Base64URL secondPart, final Base64URL thirdPart)	
		throws ParseException {

		if (firstPart == null) {

			throw new IllegalArgumentException("The first part must not be null");
		}

		try {
			this.header = JWSHeader.parse(firstPart);

		} catch (ParseException e) {

			throw new ParseException("Invalid JWS header: " + e.getMessage(), 0);
		}

		if (secondPart == null) {

			throw new IllegalArgumentException("The second part must not be null");
		}

		setPayload(new Payload(secondPart));

		signingInputString = composeSigningInput(firstPart, secondPart);

		if (thirdPart == null) {
			throw new IllegalArgumentException("The third part must not be null");
		}

		signature = thirdPart;

		state = State.SIGNED; // but signature not verified yet!

		setParsedParts(firstPart, secondPart, thirdPart);
	}


	@Override
	public JWSHeader getHeader() {

		return header;
	}


	/**
	 * Composes the signing input for the specified JWS object parts.
	 *
	 * <p>Format:
	 *
	 * <pre>
	 * [header-base64url].[payload-base64url]
	 * </pre>
	 *
	 * @param firstPart  The first part, corresponding to the JWS header.
	 *                   Must not be {@code null}.
	 * @param secondPart The second part, corresponding to the payload. 
	 *                   Must not be {@code null}.
	 *
	 * @return The signing input string.
	 */
	private static String composeSigningInput(final Base64URL firstPart, final Base64URL secondPart) {

		return firstPart.toString() + '.' + secondPart.toString();
	}


	/**
	 * Returns the signing input for this JWS object.
	 *
	 * <p>Format:
	 *
	 * <pre>
	 * [header-base64url].[payload-base64url]
	 * </pre>
	 *
	 * @return The signing input, to be passed to a JWS signer or verifier.
	 */
	public byte[] getSigningInput() {

		return signingInputString.getBytes(StandardCharset.UTF_8);
	}


	/**
	 * Returns the signature of this JWS object.
	 *
	 * @return The signature, {@code null} if the JWS object is not signed 
	 *         yet.
	 */
	public Base64URL getSignature() {

		return signature;
	}


	/**
	 * Returns the state of this JWS object.
	 *
	 * @return The state.
	 */
	public State getState() {

		return state;
	}


	/**
	 * Ensures the current state is {@link State#UNSIGNED unsigned}.
	 *
	 * @throws IllegalStateException If the current state is not unsigned.
	 */
	private void ensureUnsignedState() {

		if (state != State.UNSIGNED) {

			throw new IllegalStateException("The JWS object must be in an unsigned state");
		}
	}


	/**
	 * Ensures the current state is {@link State#SIGNED signed} or
	 * {@link State#VERIFIED verified}.
	 *
	 * @throws IllegalStateException If the current state is not signed or
	 *                               verified.
	 */
	private void ensureSignedOrVerifiedState() {

		if (state != State.SIGNED && state != State.VERIFIED) {

			throw new IllegalStateException("The JWS object must be in a signed or verified state");
		}
	}


	/**
	 * Ensures the specified JWS signer supports the algorithm of this JWS
	 * object.
	 *
	 * @throws JOSEException If the JWS algorithm is not supported.
	 */
	private void ensureJWSSignerSupport(final JWSSigner signer)
		throws JOSEException {

		if (! signer.supportedJWSAlgorithms().contains(getHeader().getAlgorithm())) {

			throw new JOSEException("The \"" + getHeader().getAlgorithm() + 
			                        "\" algorithm is not allowed or supported by the JWS signer: Supported algorithms: " + signer.supportedJWSAlgorithms());
		}
	}


	/**
	 * Signs this JWS object with the specified signer. The JWS object must
	 * be in a {@link State#UNSIGNED unsigned} state.
	 *
	 * @param signer The JWS signer. Must not be {@code null}.
	 *
	 * @throws IllegalStateException If the JWS object is not in an 
	 *                               {@link State#UNSIGNED unsigned state}.
	 * @throws JOSEException         If the JWS object couldn't be signed.
	 */
	public synchronized void sign(final JWSSigner signer)
		throws JOSEException {

		ensureUnsignedState();

		ensureJWSSignerSupport(signer);

		try {
			signature = signer.sign(getHeader(), getSigningInput());

		} catch (JOSEException e) {

			throw e;
				
		} catch (Exception e) {

			// Prevent throwing unchecked exceptions at this point,
			// see issue #20
			throw new JOSEException(e.getMessage(), e);
		}

		state = State.SIGNED;
	}


	/**
	 * Checks the signature of this JWS object with the specified verifier. 
	 * The JWS object must be in a {@link State#SIGNED signed} state.
	 *
	 * @param verifier The JWS verifier. Must not be {@code null}.
	 *
	 * @return {@code true} if the signature was successfully verified, 
	 *         else {@code false}.
	 *
	 * @throws IllegalStateException If the JWS object is not in a 
	 *                               {@link State#SIGNED signed} or
	 *                               {@link State#VERIFIED verified state}.
	 * @throws JOSEException         If the JWS object couldn't be
	 *                               verified.
	 */
	public synchronized boolean verify(final JWSVerifier verifier)
		throws JOSEException {

		ensureSignedOrVerifiedState();

		boolean verified;

		try {
			verified = verifier.verify(getHeader(), getSigningInput(), getSignature());

		} catch (JOSEException e) {

			throw e;

		} catch (Exception e) {

			// Prevent throwing unchecked exceptions at this point,
			// see issue #20
			throw new JOSEException(e.getMessage(), e);
		}

		if (verified) {

			state = State.VERIFIED;
		}

		return verified;
	}


	/**
	 * Serialises this JWS object to its compact format consisting of 
	 * Base64URL-encoded parts delimited by period ('.') characters. It 
	 * must be in a {@link State#SIGNED signed} or 
	 * {@link State#VERIFIED verified} state.
	 *
	 * <pre>
	 * [header-base64url].[payload-base64url].[signature-base64url]
	 * </pre>
	 *
	 * @return The serialised JWS object.
	 *
	 * @throws IllegalStateException If the JWS object is not in a 
	 *                               {@link State#SIGNED signed} or
	 *                               {@link State#VERIFIED verified} state.
	 */
	@Override
	public String serialize() {

		ensureSignedOrVerifiedState();

		return signingInputString + '.' + signature.toString();
	}


	/**
	 * Parses a JWS object from the specified string in compact format. The
	 * parsed JWS object will be given a {@link State#SIGNED} state.
	 *
	 * @param s The string to parse. Must not be {@code null}.
	 *
	 * @return The JWS object.
	 *
	 * @throws ParseException If the string couldn't be parsed to a valid 
	 *                        JWS object.
	 */
	public static JWSObject parse(final String s)
		throws ParseException {

		Base64URL[] parts = JOSEObject.split(s);

		if (parts.length != 3) {

			throw new ParseException("Unexpected number of Base64URL parts, must be three", 0);
		}

		return new JWSObject(parts[0], parts[1], parts[2]);
	}
}
