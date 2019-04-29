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

package com.nimbusds.jose.jwk;


import java.io.Serializable;
import java.net.URI;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECParameterSpec;
import java.text.ParseException;
import java.util.*;

import net.minidev.json.JSONAware;
import net.minidev.json.JSONObject;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.*;


/**
 * The base abstract class for JSON Web Keys (JWKs). It serialises to a JSON
 * object.
 *
 * <p>The following JSON object members are common to all JWK types:
 *
 * <ul>
 *     <li>{@link #getKeyType kty} (required)
 *     <li>{@link #getKeyUse use} (optional)
 *     <li>{@link #getKeyOperations key_ops} (optional)
 *     <li>{@link #getKeyID kid} (optional)
 *     <li>{@link #getX509CertURL()  x5u} (optional)
 *     <li>{@link #getX509CertThumbprint()  x5t} (optional)
 *     <li>{@link #getX509CertSHA256Thumbprint()  x5t#S256} (optional)
 *     <li>{@link #getX509CertChain() x5c} (optional)
 *     <li>{@link #getKeyStore()}
 * </ul>
 *
 * <p>Example JWK (of the Elliptic Curve type):
 *
 * <pre>
 * {
 *   "kty" : "EC",
 *   "crv" : "P-256",
 *   "x"   : "MKBCTNIcKUSDii11ySs3526iDZ8AiTo7Tu6KPAqv7D4",
 *   "y"   : "4Etl6SRW2YiLUrN5vfvVHuhp7x8PxltmWWlbbM4IFyM",
 *   "use" : "enc",
 *   "kid" : "1"
 * }
 * </pre>
 *
 * @author Vladimir Dzhuvinov
 * @author Justin Richer
 * @author Stefan Larsson
 * @version 2019-04-15
 */
public abstract class JWK implements JSONAware, Serializable {


	private static final long serialVersionUID = 1L;


	/**
	 * The MIME type of JWK objects: 
	 * {@code application/jwk+json; charset=UTF-8}
	 */
	public static final String MIME_TYPE = "application/jwk+json; charset=UTF-8";


	/**
	 * The key type, required.
	 */
	private final KeyType kty;


	/**
	 * The key use, optional.
	 */
	private final KeyUse use;


	/**
	 * The key operations, optional.
	 */
	private final Set<KeyOperation> ops;


	/**
	 * The intended JOSE algorithm for the key, optional.
	 */
	private final Algorithm alg;


	/**
	 * The key ID, optional.
	 */
	private final String kid;


	/**
	 * X.509 certificate URL, optional.
	 */
	private final URI x5u;


	/**
	 * X.509 certificate SHA-1 thumbprint, optional.
	 */
	@Deprecated
	private final Base64URL x5t;
	
	
	/**
	 * X.509 certificate SHA-256 thumbprint, optional.
	 */
	private Base64URL x5t256;


	/**
	 * The X.509 certificate chain, optional.
	 */
	private final List<Base64> x5c;
	
	
	/**
	 * The parsed X.509 certificate chain, optional.
	 */
	private final List<X509Certificate> parsedX5c;
	
	
	/**
	 * Reference to the underlying key store, {@code null} if none.
	 */
	private final KeyStore keyStore;


	/**
	 * Creates a new JSON Web Key (JWK).
	 *
	 * @param kty    The key type. Must not be {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID, {@code null} if not specified.
	 * @param x5u    The X.509 certificate URL, {@code null} if not
	 *               specified.
	 * @param x5t    The X.509 certificate thumbprint, {@code null} if not
	 *               specified.
	 * @param x5t256 The X.509 certificate SHA-256 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5c    The X.509 certificate chain, {@code null} if not
	 *               specified.
	 * @param ks     Reference to the underlying key store, {@code null} if
	 *               none.
	 */
	protected JWK(final KeyType kty,
		      final KeyUse use,
		      final Set<KeyOperation> ops,
		      final Algorithm alg,
		      final String kid,
		      final URI x5u,
		      final Base64URL x5t,
		      final Base64URL x5t256,
		      final List<Base64> x5c,
		      final KeyStore ks) {

		if (kty == null) {
			throw new IllegalArgumentException("The key type \"kty\" parameter must not be null");
		}

		this.kty = kty;

		if (! KeyUseAndOpsConsistency.areConsistent(use, ops)) {
			throw new IllegalArgumentException("The key use \"use\" and key options \"key_opts\" parameters are not consistent, " +
				"see RFC 7517, section 4.3");
		}

		this.use = use;
		this.ops = ops;

		this.alg = alg;
		this.kid = kid;

		this.x5u = x5u;
		this.x5t = x5t;
		this.x5t256 = x5t256;
		
		if (x5c != null && x5c.isEmpty()) {
			throw new IllegalArgumentException("The X.509 certificate chain \"x5c\" must not be empty");
		}
		this.x5c = x5c;
		
		try {
			parsedX5c = X509CertChainUtils.parse(x5c);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid X.509 certificate chain \"x5c\": " + e.getMessage(), e);
		}
		
		this.keyStore = ks;
	}


	/**
	 * Gets the type ({@code kty}) of this JWK.
	 *
	 * @return The key type.
	 */
	public KeyType getKeyType() {

		return kty;
	}


	/**
	 * Gets the use ({@code use}) of this JWK.
	 *
	 * @return The key use, {@code null} if not specified or if the key is
	 *         intended for signing as well as encryption.
	 */
	public KeyUse getKeyUse() {

		return use;
	}


	/**
	 * Gets the operations ({@code key_ops}) for this JWK.
	 *
	 * @return The key operations, {@code null} if not specified.
	 */
	public Set<KeyOperation> getKeyOperations() {

		return ops;
	}


	/**
	 * Gets the intended JOSE algorithm ({@code alg}) for this JWK.
	 *
	 * @return The intended JOSE algorithm, {@code null} if not specified.
	 */
	public Algorithm getAlgorithm() {

		return alg;
	}


	/**
	 * Gets the ID ({@code kid}) of this JWK. The key ID can be used to 
	 * match a specific key. This can be used, for instance, to choose a 
	 * key within a {@link JWKSet} during key rollover. The key ID may also 
	 * correspond to a JWS/JWE {@code kid} header parameter value.
	 *
	 * @return The key ID, {@code null} if not specified.
	 */
	public String getKeyID() {

		return kid;
	}


	/**
	 * Gets the X.509 certificate URL ({@code x5u}) of this JWK.
	 *
	 * @return The X.509 certificate URL, {@code null} if not specified.
	 */
	public URI getX509CertURL() {

		return x5u;
	}


	/**
	 * Gets the X.509 certificate SHA-1 thumbprint ({@code x5t}) of this
	 * JWK.
	 *
	 * @return The X.509 certificate SHA-1 thumbprint, {@code null} if not
	 *         specified.
	 */
	@Deprecated
	public Base64URL getX509CertThumbprint() {

		return x5t;
	}
	
	
	/**
	 * Gets the X.509 certificate SHA-256 thumbprint ({@code x5t#S256}) of
	 * this JWK.
	 *
	 * @return The X.509 certificate SHA-256 thumbprint, {@code null} if
	 *         not specified.
	 */
	public Base64URL getX509CertSHA256Thumbprint() {
		
		return x5t256;
	}


	/**
	 * Gets the X.509 certificate chain ({@code x5c}) of this JWK.
	 *
	 * @return The X.509 certificate chain as a unmodifiable list,
	 *         {@code null} if not specified.
	 */
	public List<Base64> getX509CertChain() {

		if (x5c == null) {
			return null;
		}

		return Collections.unmodifiableList(x5c);
	}
	
	
	/**
	 * Gets the parsed X.509 certificate chain ({@code x5c}) of this JWK.
	 *
	 * @return The X.509 certificate chain as a unmodifiable list,
	 *         {@code null} if not specified.
	 */
	public List<X509Certificate> getParsedX509CertChain() {
		
		if (parsedX5c == null) {
			return null;
		}
		
		return Collections.unmodifiableList(parsedX5c);
	}
	
	
	/**
	 * Returns a reference to the underlying key store.
	 *
	 * @return The underlying key store, {@code null} if none.
	 */
	public KeyStore getKeyStore() {
		
		return keyStore;
	}


	/**
	 * Returns the required JWK parameters. Intended as input for JWK
	 * thumbprint computation. See RFC 7638 for more information.
	 *
	 * @return The required JWK parameters, sorted alphanumerically by key
	 *         name and ready for JSON serialisation.
	 */
	public abstract LinkedHashMap<String,?> getRequiredParams();


	/**
	 * Computes the SHA-256 thumbprint of this JWK. See RFC 7638 for more
	 * information.
	 *
	 * @return The SHA-256 thumbprint.
	 *
	 * @throws JOSEException If the SHA-256 hash algorithm is not
	 *                       supported.
	 */
	public Base64URL computeThumbprint()
		throws JOSEException {

		return computeThumbprint("SHA-256");
	}


	/**
	 * Computes the thumbprint of this JWK using the specified hash
	 * algorithm. See RFC 7638 for more information.
	 *
	 * @param hashAlg The hash algorithm. Must not be {@code null}.
	 *
	 * @return The SHA-256 thumbprint.
	 *
	 * @throws JOSEException If the hash algorithm is not supported.
	 */
	public Base64URL computeThumbprint(final String hashAlg)
		throws JOSEException {

		return ThumbprintUtils.compute(hashAlg, this);
	}


	/**
	 * Returns {@code true} if this JWK contains private or sensitive
	 * (non-public) parameters.
	 *
	 * @return {@code true} if this JWK contains private parameters, else
	 *         {@code false}.
	 */
	public abstract boolean isPrivate();


	/**
	 * Creates a copy of this JWK with all private or sensitive parameters 
	 * removed.
	 * 
	 * @return The newly created public JWK, or {@code null} if none can be
	 *         created.
	 */
	public abstract JWK toPublicJWK();


	/**
	 * Returns the size of this JWK.
	 *
	 * @return The JWK size, in bits.
	 */
	public abstract int size();


	/**
	 * Returns a JSON object representation of this JWK. This method is 
	 * intended to be called from extending classes.
	 *
	 * <p>Example:
	 *
	 * <pre>
	 * {
	 *   "kty" : "RSA",
	 *   "use" : "sig",
	 *   "kid" : "fd28e025-8d24-48bc-a51a-e2ffc8bc274b"
	 * }
	 * </pre>
	 *
	 * @return The JSON object representation.
	 */
	public JSONObject toJSONObject() {

		JSONObject o = new JSONObject();

		o.put("kty", kty.getValue());

		if (use != null) {
			o.put("use", use.identifier());
		}

		if (ops != null) {

			List<String> sl = new ArrayList<>(ops.size());

			for (KeyOperation op: ops) {
				sl.add(op.identifier());
			}

			o.put("key_ops", sl);
		}

		if (alg != null) {
			o.put("alg", alg.getName());
		}

		if (kid != null) {
			o.put("kid", kid);
		}

		if (x5u != null) {
			o.put("x5u", x5u.toString());
		}

		if (x5t != null) {
			o.put("x5t", x5t.toString());
		}
		
		if (x5t256 != null) {
			o.put("x5t#S256", x5t256.toString());
		}

		if (x5c != null) {
			o.put("x5c", x5c);
		}

		return o;
	}


	/**
	 * Returns the JSON object string representation of this JWK.
	 *
	 * @return The JSON object string representation.
	 */
	@Override
	public String toJSONString() {

		return toJSONObject().toString();
	}


	/**
	 * @see #toJSONString
	 */
	@Override
	public String toString() {

		return toJSONObject().toString();
	}


	/**
	 * Parses a JWK from the specified JSON object string representation. 
	 * The JWK must be an {@link ECKey}, an {@link RSAKey}, or a 
	 * {@link OctetSequenceKey}.
	 *
	 * @param s The JSON object string to parse. Must not be {@code null}.
	 *
	 * @return The JWK.
	 *
	 * @throws ParseException If the string couldn't be parsed to a
	 *                        supported JWK.
	 */
	public static JWK parse(final String s)
		throws ParseException {

		return parse(JSONObjectUtils.parse(s));
	}


	/**
	 * Parses a JWK from the specified JSON object representation. The JWK 
	 * must be an {@link ECKey}, an {@link RSAKey}, or a 
	 * {@link OctetSequenceKey}.
	 *
	 * @param jsonObject The JSON object to parse. Must not be 
	 *                   {@code null}.
	 *
	 * @return The JWK.
	 *
	 * @throws ParseException If the JSON object couldn't be parsed to a 
	 *                        supported JWK.
	 */
	public static JWK parse(final JSONObject jsonObject)
		throws ParseException {

		KeyType kty = KeyType.parse(JSONObjectUtils.getString(jsonObject, "kty"));

		if (kty == KeyType.EC) {
			
			return ECKey.parse(jsonObject);

		} else if (kty == KeyType.RSA) {
			
			return RSAKey.parse(jsonObject);

		} else if (kty == KeyType.OCT) {
			
			return OctetSequenceKey.parse(jsonObject);
			
		} else if (kty == KeyType.OKP) {
			
			return OctetKeyPair.parse(jsonObject);

		} else {

			throw new ParseException("Unsupported key type \"kty\" parameter: " + kty, 0);
		}
	}
	
	
	/**
	 * Parses a public {@link RSAKey RSA} or {@link ECKey EC JWK} from the
	 * specified X.509 certificate. Requires BouncyCastle.
	 *
	 * <p><strong>Important:</strong> The X.509 certificate is not
	 * validated!
	 *
	 * <p>Sets the following JWK parameters:
	 *
	 * <ul>
	 *     <li>For an EC key the curve is obtained from the subject public
	 *         key info algorithm parameters.
	 *     <li>The JWK use inferred by {@link KeyUse#from}.
	 *     <li>The JWK ID from the X.509 serial number (in base 10).
	 *     <li>The JWK X.509 certificate chain (this certificate only).
	 *     <li>The JWK X.509 certificate SHA-256 thumbprint.
	 * </ul>
	 *
	 * @param cert The X.509 certificate. Must not be {@code null}.
	 *
	 * @return The public RSA or EC JWK.
	 *
	 * @throws JOSEException If parsing failed.
	 */
	public static JWK parse(final X509Certificate cert)
		throws JOSEException {
		
		if (cert.getPublicKey() instanceof RSAPublicKey) {
			return RSAKey.parse(cert);
		} else if (cert.getPublicKey() instanceof ECPublicKey) {
			return ECKey.parse(cert);
		} else {
			throw new JOSEException("Unsupported public key algorithm: " + cert.getPublicKey().getAlgorithm());
		}
	}
	
	
	/**
	 * Parses a public {@link RSAKey RSA} or {@link ECKey EC JWK} from the
	 * specified PEM-encoded X.509 certificate. Requires BouncyCastle.
	 *
	 * <p><strong>Important:</strong> The X.509 certificate is not
	 * validated!
	 *
	 * <p>Sets the following JWK parameters:
	 *
	 * <ul>
	 *     <li>For an EC key the curve is obtained from the subject public
	 *         key info algorithm parameters.
	 *     <li>The JWK use inferred by {@link KeyUse#from}.
	 *     <li>The JWK ID from the X.509 serial number (in base 10).
	 *     <li>The JWK X.509 certificate chain (this certificate only).
	 *     <li>The JWK X.509 certificate SHA-256 thumbprint.
	 * </ul>
	 *
	 * @param pemEncodedCert The PEM-encoded X.509 certificate. Must not be
	 *                       {@code null}.
	 *
	 * @return The public RSA or EC JWK.
	 *
	 * @throws JOSEException If parsing failed.
	 */
	public static JWK parseFromPEMEncodedX509Cert(final String pemEncodedCert)
		throws JOSEException {
		
		X509Certificate cert = X509CertUtils.parse(pemEncodedCert);
		
		if (cert == null) {
			throw new JOSEException("Couldn't parse PEM-encoded X.509 certificate");
		}
		
		return parse(cert);
	}
	
	
	/**
	 * Loads a JWK from the specified JCE key store. The JWK can be a
	 * public / private {@link RSAKey RSA key}, a public / private
	 * {@link ECKey EC key}, or a {@link OctetSequenceKey secret key}.
	 * Requires BouncyCastle.
	 *
	 * <p><strong>Important:</strong> The X.509 certificate is not
	 * validated!
	 *
	 * @param keyStore The key store. Must not be {@code null}.
	 * @param alias    The alias. Must not be {@code null}.
	 * @param pin      The pin to unlock the private key if any, empty or
	 *                 {@code null} if not required.
	 *
	 * @return The public / private RSA or EC JWK, or secret JWK, or
	 *         {@code null} if no key with the specified alias was found.
	 *
	 * @throws KeyStoreException On a key store exception.
	 * @throws JOSEException     If RSA or EC key loading failed.
	 */
	public static JWK load(final KeyStore keyStore, final String alias, final char[] pin)
		throws KeyStoreException, JOSEException {
		
		java.security.cert.Certificate cert = keyStore.getCertificate(alias);
		
		if (cert == null) {
			// Try secret key
			return OctetSequenceKey.load(keyStore, alias, pin);
		}
		
		if (cert.getPublicKey() instanceof RSAPublicKey) {
			return RSAKey.load(keyStore, alias, pin);
		} else if (cert.getPublicKey() instanceof ECPublicKey) {
			return ECKey.load(keyStore, alias, pin);
		} else {
			throw new JOSEException("Unsupported public key algorithm: " + cert.getPublicKey().getAlgorithm());
		}
	}

	/**
	 * Parses an RSA or EC JWK from the specified string of one or more
	 * PEM-encoded object(s):
	 *
	 * <ul>
	 *     <li>X.509 certificate (PEM header: BEGIN CERTIFICATE)
	 *     <li>PKCS#1 RSAPublicKey (PEM header: BEGIN RSA PUBLIC KEY)
	 *     <li>X.509 SubjectPublicKeyInfo (PEM header: BEGIN PUBLIC KEY)
	 *     <li>PKCS#1 RSAPrivateKey (PEM header: BEGIN RSA PRIVATE KEY)
	 *     <li>PKCS#8 PrivateKeyInfo (PEM header: BEGIN PRIVATE KEY)
	 *     <li>matching pair of the above
	 * </ul>
	 *
	 * <p>Requires BouncyCastle.
	 *
	 * @param pemEncodedObjects The string of PEM-encoded object(s).
	 *
	 * @return The public / (private) RSA or EC JWK.
	 *
	 * @throws JOSEException If RSA or EC key parsing failed.
	 */
	public static JWK parseFromPEMEncodedObjects(final String pemEncodedObjects)
		throws JOSEException {
		
		final List<KeyPair> keys = PEMEncodedKeyParser.parseKeys(pemEncodedObjects);
		if (keys.isEmpty()) {
			throw new JOSEException("No PEM-encoded keys found");
		}

		final KeyPair pair = mergeKeyPairs(toKeyPairList(pemEncodedObjects));

		final PublicKey publicKey = pair.getPublic();
		final PrivateKey privateKey = pair.getPrivate();

		if (publicKey instanceof ECPublicKey) {
			final ECPublicKey ecPubKey = (ECPublicKey) publicKey;
			final ECParameterSpec pubParams = ecPubKey.getParams();

			if (privateKey instanceof ECPrivateKey) {
				validateEcCurves(ecPubKey, (ECPrivateKey) privateKey);
			}
			if (privateKey != null && !(privateKey instanceof ECPrivateKey)) {
				throw new JOSEException("Unsupported EC private key type: " + privateKey);
			}

			final Curve curve = Curve.forECParameterSpec(pubParams);
			final ECKey.Builder builder = new ECKey.Builder(curve, (ECPublicKey) publicKey);

			if (privateKey != null) {
				builder.privateKey((ECPrivateKey) privateKey);
			}
			return builder.build();
		}

		if (publicKey instanceof RSAPublicKey) {
			final RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) publicKey);
			if (privateKey instanceof RSAPrivateKey) {
				builder.privateKey((RSAPrivateKey) privateKey);
			} else if (privateKey != null) {
				throw new JOSEException("Unsupported RSA private key type: " + privateKey);
			}
			return builder.build();
		}

		throw new JOSEException("Unsupported algorithm of PEM-encoded key: " + publicKey.getAlgorithm());
	}
	

	private static void validateEcCurves(ECPublicKey publicKey, ECPrivateKey privateKey) throws JOSEException {
		final ECParameterSpec pubParams = publicKey.getParams();
		final ECParameterSpec privParams = privateKey.getParams();
		if (!pubParams.getCurve().equals(privParams.getCurve())) {
			throw new JOSEException("Public/private EC key curve mismatch: " + publicKey);
		}
		if (pubParams.getCofactor() != privParams.getCofactor()) {
			throw new JOSEException("Public/private EC key cofactor mismatch: " + publicKey);
		}
		if (!pubParams.getGenerator().equals(privParams.getGenerator())) {
			throw new JOSEException("Public/private EC key generator mismatch: " + publicKey);
		}
		if (!pubParams.getOrder().equals(privParams.getOrder())) {
			throw new JOSEException("Public/private EC key order mismatch: " + publicKey);
		}
	}

	
	private static KeyPair mergeKeyPairs(final List<KeyPair> keys) throws JOSEException {
		final KeyPair pair;
		if (keys.size() == 1) {
			// Assume public key, or private key easy to convert to public,
			// otherwise not representable as a JWK
			pair = keys.get(0);
		} else if (keys.size() == 2) {
			// If two keys, assume public + private keys separated
			pair = twoKeysToKeyPair(keys);
		} else {
			throw new JOSEException("Expected key or pair of PEM-encoded keys");
		}
		return pair;
	}

	
	private static List<KeyPair> toKeyPairList(final String pem) throws JOSEException {
		final List<KeyPair> keys = PEMEncodedKeyParser.parseKeys(pem);
		if (keys.isEmpty()) {
			throw new JOSEException("No PEM-encoded keys found");
		}
		return keys;
	}

	
	private static KeyPair twoKeysToKeyPair(final List<? extends KeyPair> keys) throws JOSEException {
		final KeyPair key1 = keys.get(0);
		final KeyPair key2 = keys.get(1);
		if (key1.getPublic() != null && key2.getPrivate() != null) {
			return new KeyPair(key1.getPublic(), key2.getPrivate());
		} else if (key1.getPrivate() != null && key2.getPublic() != null) {
			return new KeyPair(key2.getPublic(), key1.getPrivate());
		} else {
			throw new JOSEException("Not a public/private key pair");
		}
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JWK)) return false;
		JWK jwk = (JWK) o;
		return Objects.equals(kty, jwk.kty) &&
				Objects.equals(use, jwk.use) &&
				Objects.equals(ops, jwk.ops) &&
				Objects.equals(alg, jwk.alg) &&
				Objects.equals(kid, jwk.kid) &&
				Objects.equals(x5u, jwk.x5u) &&
				Objects.equals(x5t, jwk.x5t) &&
				Objects.equals(x5t256, jwk.x5t256) &&
				Objects.equals(x5c, jwk.x5c) &&
				Objects.equals(parsedX5c, jwk.parsedX5c) &&
				Objects.equals(keyStore, jwk.keyStore);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(kty, use, ops, alg, kid, x5u, x5t, x5t256, x5c, parsedX5c, keyStore);
	}
}
