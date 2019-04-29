/*
 * NameUtil.java
 */

package org.maxkey.crypto.cert;

import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.X509Name;

/**
 * Provides utility methods relating to X50* names.
 */

public final class NameUtil {
	/**
	 * Private to prevent construction.
	 */
	private NameUtil() {
		// Nothing to do
	}

	/**
	 * Gets the common name from the given X509Name.
	 * 
	 * @param name
	 *            the X.509 name
	 * @return the common name, null if not found
	 */
	public static String getCommonName(X509Name name) {
		if (name == null) {
			return null;
		}

		Vector<?> values = name.getValues(X509Name.CN);
		if (values == null || values.isEmpty()) {
			return null;
		}

		return values.get(0).toString();
	}

	/**
	 * Gets the common name from the given X500Principal.
	 * 
	 * @param name
	 *            the X.500 principal
	 * @return the common name, null if not found
	 */
	public static String getCommonName(X500Principal name) {
		if (name == null) {
			return null;
		}

		return getCommonName(new X509Name(name.getName()));
	}
}
