/*
 * lang-tag
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

package com.nimbusds.langtag;


/**
 * Read-only view of a {@link LangTag Language tag}.
 */
public interface ReadOnlyLangTag {
	
	
	/**
	 * Gets the language (primary language plus extended language subtags).
	 *
	 * <p>See RFC 5646 section 2.2.1.
	 *
	 * <p>Examples:
	 *
	 * <pre>
	 * en
	 * de
	 * zh-cmn
	 * cmn
	 * </pre>
	 *
	 * @return The language, consisting of the primary and/or extended 
	 *         language subtags.
	 */
	public String getLanguage();
	
	
	/**
	 * Gets the primary language.
	 *
	 * <p>See RFC 5646 section 2.2.1.
	 *
	 * @return The primary language, as a two or three-letter ISO 639 code,
	 *         in canonical lower case format.
	 */
	public String getPrimaryLanguage();
	
	
	/**
	 * Gets the extended language subtags.
	 *
	 * <p>See RFC 5646 section 2.2.2.
	 *
	 * @return The extended language subtags, as three-letter ISO 639-3 
	 *         codes. {@code null} if none.
	 */
	public String[] getExtendedLanguageSubtags();
	
	
	/**
	 * Gets the script.
	 *
	 * <p>See RFC 5646 section 2.2.3.
	 *
	 * @return The script, as an ISO 15924 code, in canonical title case
	 *         format. {@code null} if not defined.
	 */
	public String getScript();
	
	
	/**
	 * Gets the region.
	 *
	 * <p>See RFC 5646 section 2.2.4.
	 *
	 * @return The region, as a two-letter ISO 3166-1 code or a three-digit
	 *         UN M.49 code. {@code null} if not defined.
	 */
	public String getRegion();
	
	
	/**
	 * Gets the variants.
	 *
	 * <p>See RFC 5646 section 2.2.5.
	 *
	 * @return The variants. {@code null} if not defined.
	 */
	public String[] getVariants();
	
	
	/**
	 * Gets the extensions.
	 *
	 * <p>See RFC 5646 section 2.2.6.
	 *
	 * @return The extensions. {@code null} if not defined.
	 */
	public String[] getExtensions();
	
	
	/**
	 * Gets the private use.
	 *
	 * <p>See RFC 5646 section 2.2.7.
	 *
	 * @return The private use. {@code null} if not defined.
	 */
	public String getPrivateUse();
	
	
	/**
	 * Returns the canonical string representation of this language tag.
	 *
	 * @return The canonical string representation.
	 */
	public String toString();
}
