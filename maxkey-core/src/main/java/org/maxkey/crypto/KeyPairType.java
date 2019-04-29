/*
 * KeyPairType.java
 */

package org.maxkey.crypto;

/**
 * Key pair type. Enum constant names are compatible with JCA standard names.
 * 
 * @see <a
 *      href="http://download.oracle.com/javase/6/docs/technotes/guides/security/StandardNames.html">JCA
 *      Standard Names</a>
 */
public enum KeyPairType {
	/** RSA key pair type. */
	RSA,
	/** DSA key pair type. */
	DSA,
	/** ECDSA key pair type. */
	ECDSA;
}
