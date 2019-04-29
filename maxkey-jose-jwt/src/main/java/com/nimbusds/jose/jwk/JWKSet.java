/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2018, Connect2id Ltd.
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

package com.nimbusds.jose.jwk;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.*;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.*;
import net.jcip.annotations.Immutable;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


/**
 * JSON Web Key (JWK) set. Represented by a JSON object that contains an array
 * of {@link JWK JSON Web Keys} (JWKs) as the value of its "keys" member.
 * Additional (custom) members of the JWK Set JSON object are also supported.
 *
 * <p>Example JSON Web Key (JWK) set:
 *
 * <pre>
 * {
 *   "keys" : [ { "kty" : "EC",
 *                "crv" : "P-256",
 *                "x"   : "MKBCTNIcKUSDii11ySs3526iDZ8AiTo7Tu6KPAqv7D4",
 *                "y"   : "4Etl6SRW2YiLUrN5vfvVHuhp7x8PxltmWWlbbM4IFyM",
 *                "use" : "enc",
 *                "kid" : "1" },
 *
 *              { "kty" : "RSA",
 *                "n"   : "0vx7agoebGcQSuuPiLJXZptN9nndrQmbXEps2aiAFbWhM78LhWx
 *                         4cbbfAAtVT86zwu1RK7aPFFxuhDR1L6tSoc_BJECPebWKRXjBZCiFV4n3oknjhMs
 *                         tn64tZ_2W-5JsGY4Hc5n9yBXArwl93lqt7_RN5w6Cf0h4QyQ5v-65YGjQR0_FDW2
 *                         QvzqY368QQMicAtaSqzs8KJZgnYb9c7d0zgdAZHzu6qMQvRL5hajrn1n91CbOpbI
 *                         SD08qNLyrdkt-bFTWhAI4vMQFh6WeZu0fM4lFd2NcRwr3XPksINHaQ-G_xBniIqb
 *                         w0Ls1jF44-csFCur-kEgU8awapJzKnqDKgw",
 *                "e"   : "AQAB",
 *                "alg" : "RS256",
 *                "kid" : "2011-04-29" } ]
 * }
 * </pre>
 *
 * @author Vladimir Dzhuvinov
 * @author Vedran Pavic
 * @version 2018-04-26
 */
@Immutable
public class JWKSet {


	/**
	 * The MIME type of JWK set objects: 
	 * {@code application/jwk-set+json; charset=UTF-8}
	 */
	public static final String MIME_TYPE = "application/jwk-set+json; charset=UTF-8";


	/**
	 * The JWK list.
	 */
	private final List<JWK> keys;


	/**
	 * Additional custom members.
	 */
	private final Map<String,Object> customMembers;


	/**
	 * Creates a new empty JSON Web Key (JWK) set.
	 */
	public JWKSet() {

		this(Collections.<JWK>emptyList());
	}


	/**
	 * Creates a new JSON Web Key (JWK) set with a single key.
	 *
	 * @param key The JWK. Must not be {@code null}.
	 */
	public JWKSet(final JWK key) {
		
		this(Collections.singletonList(key));
		
		if (key == null) {
			throw new IllegalArgumentException("The JWK must not be null");
		}
	}


	/**
	 * Creates a new JSON Web Key (JWK) set with the specified keys.
	 *
	 * @param keys The JWK list. Must not be {@code null}.
	 */
	public JWKSet(final List<JWK> keys) {

		this(keys, Collections.<String, Object>emptyMap());
	}


	/**
	 * Creates a new JSON Web Key (JWK) set with the specified keys and
	 * additional custom members.
	 *
	 * @param keys          The JWK list. Must not be {@code null}.
	 * @param customMembers The additional custom members. Must not be
	 *                      {@code null}.
	 */
	public JWKSet(final List<JWK> keys, final Map<String,Object> customMembers) {

		if (keys == null) {
			throw new IllegalArgumentException("The JWK list must not be null");
		}

		this.keys = Collections.unmodifiableList(keys);

		this.customMembers = Collections.unmodifiableMap(customMembers);
	}


	/**
	 * Gets the keys (ordered) of this JSON Web Key (JWK) set.
	 *
	 * @return The keys, empty list if none.
	 */
	public List<JWK> getKeys() {

		return keys;
	}

	
	/**
	 * Gets the key from this JSON Web Key (JWK) set as identified by its 
	 * Key ID (kid) member.
	 * 
	 * <p>If more than one key exists in the JWK Set with the same 
	 * identifier, this function returns only the first one in the set.
	 *
	 * @param kid They key identifier.
	 *
	 * @return The key identified by {@code kid} or {@code null} if no key 
	 *         exists.
	 */
	public JWK getKeyByKeyId(String kid) {
		
		for (JWK key : getKeys()) {
	        
	        	if (key.getKeyID() != null && key.getKeyID().equals(kid)) {
	        		return key;
	        	}
        	}
		
		// no key found
		return null;
	}


	/**
	 * Gets the additional custom members of this JSON Web Key (JWK) set.
	 *
	 * @return The additional custom members, empty map if none.
	 */
	public Map<String,Object> getAdditionalMembers() {

		return customMembers;
	}


	/**
	 * Returns a copy of this JSON Web Key (JWK) set with all private keys
	 * and parameters removed.
	 *
	 * @return A copy of this JWK set with all private keys and parameters
	 *         removed.
	 */
	public JWKSet toPublicJWKSet() {

		List<JWK> publicKeyList = new LinkedList<>();

		for (JWK key: keys) {

			JWK publicKey = key.toPublicJWK();

			if (publicKey != null) {
				publicKeyList.add(publicKey);
			}
		}

		return new JWKSet(publicKeyList, customMembers);
	}


	/**
	 * Returns the JSON object representation of this JSON Web Key (JWK) 
	 * set. Private keys and parameters will be omitted from the output.
	 * Use the alternative {@link #toJSONObject(boolean)} method if you
	 * wish to include them.
	 *
	 * @return The JSON object representation.
	 */
	public JSONObject toJSONObject() {

		return toJSONObject(true);
	}


	/**
	 * Returns the JSON object representation of this JSON Web Key (JWK) 
	 * set.
	 *
	 * @param publicKeysOnly Controls the inclusion of private keys and
	 *                       parameters into the output JWK members. If
	 *                       {@code true} private keys and parameters will
	 *                       be omitted. If {@code false} all available key
	 *                       parameters will be included.
	 *
	 * @return The JSON object representation.
	 */
	public JSONObject toJSONObject(final boolean publicKeysOnly) {

		JSONObject o = new JSONObject(customMembers);

		JSONArray a = new JSONArray();

		for (JWK key: keys) {

			if (publicKeysOnly) {

				// Try to get public key, then serialise
				JWK publicKey = key.toPublicJWK();

				if (publicKey != null) {
					a.add(publicKey.toJSONObject());
				}
			} else {

				a.add(key.toJSONObject());
			}
		}

		o.put("keys", a);

		return o;
	}


	/**
	 * Returns the JSON object string representation of this JSON Web Key
	 * (JWK) set.
	 *
	 * @return The JSON object string representation.
	 */
	@Override
	public String toString() {

		return toJSONObject().toString();
	}


	/**
	 * Parses the specified string representing a JSON Web Key (JWK) set.
	 *
	 * @param s The string to parse. Must not be {@code null}.
	 *
	 * @return The JWK set.
	 *
	 * @throws ParseException If the string couldn't be parsed to a valid
	 *                        JSON Web Key (JWK) set.
	 */
	public static JWKSet parse(final String s)
		throws ParseException {

		return parse(JSONObjectUtils.parse(s));
	}


	/**
	 * Parses the specified JSON object representing a JSON Web Key (JWK) 
	 * set.
	 *
	 * @param json The JSON object to parse. Must not be {@code null}.
	 *
	 * @return The JWK set.
	 *
	 * @throws ParseException If the string couldn't be parsed to a valid
	 *                        JSON Web Key (JWK) set.
	 */
	public static JWKSet parse(final JSONObject json)
		throws ParseException {

		JSONArray keyArray = JSONObjectUtils.getJSONArray(json, "keys");
		
		if (keyArray == null) {
			throw new ParseException("Missing required \"keys\" member", 0);
		}

		List<JWK> keys = new LinkedList<>();

		for (int i=0; i < keyArray.size(); i++) {

			if (! (keyArray.get(i) instanceof JSONObject)) {
				throw new ParseException("The \"keys\" JSON array must contain JSON objects only", 0);
			}

			JSONObject keyJSON = (JSONObject)keyArray.get(i);

			try {
				keys.add(JWK.parse(keyJSON));

			} catch (ParseException e) {

				throw new ParseException("Invalid JWK at position " + i + ": " + e.getMessage(), 0);
			}
		}

		// Parse additional custom members
		Map<String, Object> additionalMembers = new HashMap<>();
		for (Map.Entry<String,Object> entry: json.entrySet()) {
			
			if (entry.getKey() == null || entry.getKey().equals("keys")) {
				continue;
			}
			
			additionalMembers.put(entry.getKey(), entry.getValue());
		}
		
		return new JWKSet(keys, additionalMembers);
	}


	/**
	 * Loads a JSON Web Key (JWK) set from the specified input stream.
	 *
	 * @param inputStream The JWK set input stream. Must not be {@code null}.
	 *
	 * @return The JWK set.
	 *
	 * @throws IOException    If the input stream couldn't be read.
	 * @throws ParseException If the input stream couldn't be parsed to a valid
	 *                        JSON Web Key (JWK) set.
	 */
	public static JWKSet load(final InputStream inputStream)
		throws IOException, ParseException {

		return parse(IOUtils.readInputStreamToString(inputStream, Charset.forName("UTF-8")));
	}


	/**
	 * Loads a JSON Web Key (JWK) set from the specified file.
	 *
	 * @param file The JWK set file. Must not be {@code null}.
	 *
	 * @return The JWK set.
	 *
	 * @throws IOException    If the file couldn't be read.
	 * @throws ParseException If the file couldn't be parsed to a valid
	 *                        JSON Web Key (JWK) set.
	 */
	public static JWKSet load(final File file)
		throws IOException, ParseException {

		return parse(IOUtils.readFileToString(file, Charset.forName("UTF-8")));
	}


	/**
	 * Loads a JSON Web Key (JWK) set from the specified URL.
	 *
	 * @param url            The JWK set URL. Must not be {@code null}.
	 * @param connectTimeout The URL connection timeout, in milliseconds.
	 *                       If zero no (infinite) timeout.
	 * @param readTimeout    The URL read timeout, in milliseconds. If zero
	 *                       no (infinite) timeout.
	 * @param sizeLimit      The read size limit, in bytes. If zero no
	 *                       limit.
	 *
	 * @return The JWK set.
	 *
	 * @throws IOException    If the file couldn't be read.
	 * @throws ParseException If the file couldn't be parsed to a valid
	 *                        JSON Web Key (JWK) set.
	 */
	public static JWKSet load(final URL url,
				  final int connectTimeout,
				  final int readTimeout,
				  final int sizeLimit)
		throws IOException, ParseException {

		RestrictedResourceRetriever resourceRetriever = new DefaultResourceRetriever(
			connectTimeout,
			readTimeout,
			sizeLimit);
		Resource resource = resourceRetriever.retrieveResource(url);
		return parse(resource.getContent());
	}


	/**
	 * Loads a JSON Web Key (JWK) set from the specified URL.
	 *
	 * @param url The JWK set URL. Must not be {@code null}.
	 *
	 * @return The JWK set.
	 *
	 * @throws IOException    If the file couldn't be read.
	 * @throws ParseException If the file couldn't be parsed to a valid
	 *                        JSON Web Key (JWK) set.
	 */
	public static JWKSet load(final URL url)
		throws IOException, ParseException {

		return load(url, 0, 0, 0);
	}
	
	
	/**
	 * Loads a JSON Web Key (JWK) set from the specified JCA key store. Key
	 * conversion exceptions are silently swallowed. PKCS#11 stores are
	 * also supported. Requires BouncyCastle.
	 *
	 * <p><strong>Important:</strong> The X.509 certificates are not
	 * validated!
	 *
	 * @param keyStore The key store. Must not be {@code null}.
	 * @param pwLookup The password lookup for password-protected keys,
	 *                 {@code null} if not specified.
	 *
	 * @return The JWK set, empty if no keys were loaded.
	 *
	 * @throws KeyStoreException On a key store exception.
	 */
	public static JWKSet load(final KeyStore keyStore, final PasswordLookup pwLookup)
		throws KeyStoreException {
		
		List<JWK> jwks = new LinkedList<>();
		
		// Load RSA and EC keys
		for (Enumeration<String> keyAliases = keyStore.aliases(); keyAliases.hasMoreElements(); ) {
			
			final String keyAlias = keyAliases.nextElement();
			final char[] keyPassword = pwLookup == null ? "".toCharArray() : pwLookup.lookupPassword(keyAlias);
			
			Certificate cert = keyStore.getCertificate(keyAlias);
			if (cert == null) {
				continue; // skip
			}
			
			if (cert.getPublicKey() instanceof RSAPublicKey) {
				
				RSAKey rsaJWK;
				try {
					rsaJWK = RSAKey.load(keyStore, keyAlias, keyPassword);
				} catch (JOSEException e) {
					continue; // skip cert
				}
				
				if (rsaJWK == null) {
					continue; // skip key
				}
				
				jwks.add(rsaJWK);
				
			} else if (cert.getPublicKey() instanceof ECPublicKey) {
				
				ECKey ecJWK;
				try {
					ecJWK = ECKey.load(keyStore, keyAlias, keyPassword);
				} catch (JOSEException e) {
					continue; // skip cert
				}
				
				if (ecJWK != null) {
					jwks.add(ecJWK);
				}
				
			} else {
				continue;
			}
		}
		
		
		// Load symmetric keys
		for (Enumeration<String> keyAliases = keyStore.aliases(); keyAliases.hasMoreElements(); ) {
			
			final String keyAlias = keyAliases.nextElement();
			final char[] keyPassword = pwLookup == null ? "".toCharArray() : pwLookup.lookupPassword(keyAlias);
			
			OctetSequenceKey octJWK;
			try {
				octJWK = OctetSequenceKey.load(keyStore, keyAlias, keyPassword);
			} catch (JOSEException e) {
				continue; // skip key
			}
			
			if (octJWK != null) {
				jwks.add(octJWK);
			}
		}
		
		return new JWKSet(jwks);
	}
}
