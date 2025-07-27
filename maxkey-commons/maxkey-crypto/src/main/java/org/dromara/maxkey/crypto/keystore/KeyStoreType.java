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
 * KeyStoreType.java
 */

package org.dromara.maxkey.crypto.keystore;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * KeyStore type. Enum constant names are compatible with JCA names.
 * 
 * @see <a
 *      href="http://download.oracle.com/javase/6/docs/technotes/guides/security/StandardNames.html">JCA
 *      Standard Names</a>
 */
public enum KeyStoreType {
	/** JKS keystore Type */
	JKS("JKS", true, true, new String[] { "jks" }),
	/** PKCS #12 keystore Type */
	PKCS12("PKCS #12", false, false, new String[] { "p12", "pfx" }),
	/** JCEKS keystore Type */
	JCEKS("JCEKS", true, true, new String[] { "jceks" }),
	/** Case sensitive JKS keystore Type */
	CaseExactJKS("JKS (case sensitive)", true, true, new String[] { "jks" }),
	/** BKS keystore Type */
	BKS("BKS", true, true, new String[] { "bks" }),
	/** UBER keystore Type */
	UBER("UBER", true, true, new String[] { "ubr" }),
	/** GKR keystore Type */
	GKR("GKR", true, true, new String[] { "gkr" }),
	/** PKCS #11 keystore Type */
	PKCS11("PKCS #11", false, true, new String[0]);

	/** Keystore "pretty" name */
	private final String prettyName;

	/**
	 * Whether the keystore type provides useful values for entry creation dates
	 */
	private final boolean entryCreationDateUseful;

	/** Whether the keystore supports entry passwords */
	private final boolean entryPasswordSupported;

	/** Associated filename extensions */
	private final Set<String> filenameExtensions;

	/**
	 * Construct a KeyStoreType. Private to prevent construction from outside
	 * this class.
	 * 
	 * @param sType
	 *            Keystore type
	 * @param supportsCreationDates
	 *            Whether the keystore supports creation dates
	 * @param filenameExtensions
	 *            associated filename extensions
	 */
	private KeyStoreType(String prettyName, boolean entryCreationDateUseful,
			boolean entryPasswordSupported, String[] filenameExtensions) {
		this.prettyName = prettyName;
		this.entryCreationDateUseful = entryCreationDateUseful;
		this.entryPasswordSupported = entryPasswordSupported;
		switch (filenameExtensions.length) {
		case 0:
			this.filenameExtensions = Collections.emptySet();
			break;
		case 1:
			this.filenameExtensions = Collections
					.singleton(filenameExtensions[0]);
			break;
		default:
			LinkedHashSet<String> exts = new LinkedHashSet<String>(
					filenameExtensions.length);
			Collections.addAll(exts, filenameExtensions);
			this.filenameExtensions = Collections.unmodifiableSet(exts);
		}
	}

	/**
	 * Does the keystore type provide useful values for entry creation dates?
	 * Some keystores return the keystore load time as creation date for all
	 * entries, this is not considered useful by this class.
	 * 
	 * @return true if creation dates are useful, false otherwise
	 */
	public boolean isEntryCreationDateUseful() {
		return entryCreationDateUseful;
	}

	/**
	 * Does the keystore type support passwords for entries?
	 * 
	 * @return true if entry passwords are supported, false otherwise
	 */
	public boolean isEntryPasswordSupported() {
		return entryPasswordSupported;
	}

	/**
	 * Common filename extensions associated with this type.
	 * 
	 * @return filename extensions (without leading dot, in lower case), empty
	 *         if not applicable
	 */
	public Set<String> getFilenameExtensions() {
		return filenameExtensions;
	}

	/**
	 * Return string representation of keystore type.
	 * 
	 * @return String representation of a keystore type
	 */
	@Override
	public String toString() {
		return prettyName;
	}

	/**
	 * Get set of all known keystore filename extensions.
	 * 
	 * @return
	 */
	public static Set<String> getKeyStoreFilenameExtensions() {
		HashSet<String> exts = new HashSet<String>();
		for (KeyStoreType ksType : values()) {
			for (String ext : ksType.getFilenameExtensions()) {
				exts.add(ext);
			}
		}
		return exts;
	}
}
