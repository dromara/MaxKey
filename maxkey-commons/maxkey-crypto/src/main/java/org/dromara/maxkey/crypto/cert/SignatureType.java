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
 

/*
 * SignatureType.java
 */

package org.dromara.maxkey.crypto.cert;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.dromara.maxkey.crypto.KeyPairType;


/**
 * Signature type. Enum constant names are compatible with JCA standard names.
 * 
 * @see <a href="http://download.oracle.com/javase/6/docs/technotes/guides/security/StandardNames.html">JCA
 *      Standard Names</a>
 */
public enum SignatureType
{
	/** MD2 with RSA Signature Type */
	MD2withRSA(PKCSObjectIdentifiers.md2WithRSAEncryption.getId()),
	/** MD5 with RSA Signature Type */
	MD5withRSA(PKCSObjectIdentifiers.md5WithRSAEncryption.getId()),
	/** SHA-1 with RSA Signature Type */
	SHA1withRSA(PKCSObjectIdentifiers.sha1WithRSAEncryption.getId()),
	/** SHA-224 with RSA Signature Type */
	SHA224withRSA(PKCSObjectIdentifiers.sha224WithRSAEncryption.getId()),
	/** SHA-256 with RSA Signature Type */
	SHA256withRSA(PKCSObjectIdentifiers.sha256WithRSAEncryption.getId()),
	/** SHA-384 with RSA Signature Type */
	SHA384withRSA(PKCSObjectIdentifiers.sha384WithRSAEncryption.getId()),
	/** SHA-512 with RSA Signature Type */
	SHA512withRSA(PKCSObjectIdentifiers.sha512WithRSAEncryption.getId()),
	/** RIPEMD128 with RSA Signature Type */
	RIPEMD128withRSA(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128.getId()),
	/** RIPEMD160 with RSA Signature Type */
	RIPEMD160withRSA(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160.getId()),
	/** RIPEMD256 with RSA Signature Type */
	RIPEMD256withRSA(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256.getId()),
	/** SHA-1 with DSA Signature Type */
	SHA1withDSA(X9ObjectIdentifiers.id_dsa_with_sha1.getId()),
	/** SHA-224 with DSA signature type */
	SHA224withDSA(NISTObjectIdentifiers.dsa_with_sha224.getId()),
	/** SHA-256 with DSA signature type */
	SHA256withDSA(NISTObjectIdentifiers.dsa_with_sha256.getId()),
	/** SHA-384 with DSA signature type */
	SHA384withDSA(NISTObjectIdentifiers.dsa_with_sha384.getId()),
	/** SHA-512 with DSA signature type */
	SHA512withDSA(NISTObjectIdentifiers.dsa_with_sha512.getId()),
	/** SHA-1 with ECDSA Signature Type */
	SHA1withECDSA(X9ObjectIdentifiers.ecdsa_with_SHA1.getId()),
	/** SHA-224 with ECDSA Signature Type */
	SHA224withECDSA(X9ObjectIdentifiers.ecdsa_with_SHA224.getId()),
	/** SHA-256 with ECDSA Signature Type */
	SHA256withECDSA(X9ObjectIdentifiers.ecdsa_with_SHA256.getId()),
	/** SHA-384 with ECDSA Signature Type */
	SHA384withECDSA(X9ObjectIdentifiers.ecdsa_with_SHA384.getId()),
	/** SHA-512 with ECDSA Signature Type */
	SHA512withECDSA(X9ObjectIdentifiers.ecdsa_with_SHA512.getId());

	/** OID-to-type map */
	private static final Map<String, SignatureType> OID_MAP;
	static
	{
		HashMap<String, SignatureType> oidMap = new HashMap<String, SignatureType>();
		for (SignatureType st : values())
		{
			oidMap.put(st.oid, st);
		}
		OID_MAP = Collections.unmodifiableMap(oidMap);
	}

	private static final Map<KeyPairType, Collection<SignatureType>> KEYPAIR_MAP;
	static
	{
		HashMap<KeyPairType, Collection<SignatureType>> kpMap =
		    new HashMap<KeyPairType, Collection<SignatureType>>();

		// X509V1CertificateGenerator doesn't like SHA384withDSA and SHA512withDSA as of BC 1.44
		kpMap.put(KeyPairType.DSA,
		    Collections.unmodifiableSet(EnumSet.of(SHA1withDSA, SHA224withDSA, SHA256withDSA)));

		kpMap.put(KeyPairType.RSA, Collections.unmodifiableSet(EnumSet.of(MD2withRSA, MD5withRSA,
		    SHA1withRSA, SHA224withRSA, SHA256withRSA, SHA384withRSA, SHA512withRSA, RIPEMD128withRSA,
		    RIPEMD160withRSA, RIPEMD256withRSA)));

		kpMap.put(KeyPairType.ECDSA, Collections.unmodifiableSet(EnumSet.of(SHA1withECDSA, SHA224withECDSA,
		    SHA256withECDSA, SHA384withECDSA, SHA512withECDSA)));

		KEYPAIR_MAP = Collections.unmodifiableMap(kpMap);
	}

	private final String oid;

	private SignatureType(String oid)
	{
		this.oid = oid;
	}

	/**
	 * Gets supported signature types for given key pair type.
	 * 
	 * @param keyPairType
	 * @return signature types for key pair type
	 */
	public static Collection<SignatureType> valuesFor(KeyPairType keyPairType)
	{
		Collection<SignatureType> values = KEYPAIR_MAP.get(keyPairType);
		if (values == null)
		{
			values = Collections.emptySet();
		}
		return values;
	}

	/**
	 * Gets a default signature type for given key pair type.
	 * 
	 * @param keyPairType
	 * @return default signature type for key pair type
	 */
	public static SignatureType defaultFor(KeyPairType keyPairType)
	{
		switch (keyPairType)
		{
			case RSA:
				return SHA1withRSA;
			case DSA:
				return SHA1withDSA;
			case ECDSA:
				return SHA1withECDSA;
			default:
				return null;
		}
	}

	/**
	 * Gets a SignatureType corresponding to the given object identifier.
	 * 
	 * @param oid the object identifier
	 * @return the corresponding SignatureType, <code>null</code> if unknown
	 */
	public static SignatureType valueOfOid(String oid)
	{
		return OID_MAP.get(oid);
	}

	/**
	 * Gets a string representation of signature type corresponding to the given object identifier.
	 * 
	 * @param oid the object identifier
	 * @return the corresponding signature type as string, <code>oid</code> itself if unknown
	 */
	public static String toString(String oid)
	{
		SignatureType type = valueOfOid(oid);
		if (type != null)
		{
			return type.toString();
		}
		return oid;
	}
}
