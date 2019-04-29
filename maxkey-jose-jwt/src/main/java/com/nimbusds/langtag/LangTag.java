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


import java.util.LinkedList;
import java.util.List;


/**
 * Language tag according to <a href="http://tools.ietf.org/html/rfc5646">RFC 5646</a>.
 *
 * <p>Supports normal language tags. Special private language tags beginning 
 * with "x" and grandfathered tags beginning with "i" are not supported.
 *
 * <p>To construct a new language tag from scratch:
 *
 * <pre>
 * // English as used in the United States
 * LangTag tag = new LangTag("en");
 * tag.setRegion("US");
 *
 * // Returns "en-US"
 * tag.toString();
 * </pre>
 *
 * <p>To parse a language tag:
 *
 * <pre>
 * // Chinese, Mandarin, Simplified script, as used in China
 * LangTag tag = LangTag.parse("zh-cmn-Hans-CN");
 *
 * // Returns "zh"
 * tag.getPrimaryLanguage();
 *
 * // Returns "cmn"
 * tag.getExtendedLanguageSubtags()[0];
 *
 * // Returns "zh-cmn"
 * tag.getLanguage();
 *
 * // Returns "Hans"
 * tag.getScript();
 *
 * // Returns "CN"
 * tag.getRegion();
 * </pre>
 *
 * <p>See <a href="http://tools.ietf.org/html/rfc5646">RFC 5646</a>.
 */
public class LangTag implements ReadOnlyLangTag {


	/**
	 * The primary language, as the shortest ISO 639 code (2*3ALPHA). Must
	 * always be defined, unless sufficient language subtags exist.
	 */
	private String primaryLanguage;
	
	
	/**
	 * Optional extended language subtags, as three-letter ISO-639-3 codes.
	 */
	private String[] languageSubtags;
	
	
	/**
	 * Optional script, (4ALPHA) ISO 15924 code.
	 */
	private String script = null;


	/**
	 * Optional region, (2ALPHA) ISO 3166-1 code or (3DIGIT) UN M.49 code.
	 */
	private String region = null;
	
	
	/**
	 * Optional variants, (5*8alphanum) or (DIGIT 3alphanum).
	 */
	private String[] variants = null;
	
	
	/**
	 * Optional extensions.
	 */
	private String[] extensions = null;
	
	
	/**
	 * Optional private use subtag.
	 */
	private String privateUse = null;
	
	
	/**
	 * Ensures the specified subtag has a valid maximum length of eight
	 * characters.
	 *
	 * @param subtag The sub tag to check. Must not be {@code null}.
	 *
	 * @throws LangTagException If the subtag has length greater than eight
	 *                          characters.
	 */
	private static void ensureMaxLength(final String subtag)
		throws LangTagException {
		
		if (subtag.length() > 8)
		
			// extension or private use subtag?
			if (subtag.charAt(1) != '-' && subtag.length() > 10)
			
				throw new LangTagException("Invalid subtag syntax: Max character length exceeded");
	}
	
	
	/**
	 * Creates a new simple language tag.
	 *
	 * <p>Use for simple language tags such as "en" (English), "fr" 
	 * (French) or "pt" (Portuguese).
	 *
	 * @param primaryLanguage The primary language, as the shortest two or 
	 *                        three-letter ISO 639 code. Must not be 
	 *                        {@code null}.
	 *
	 * @throws LangTagException If the primary language syntax is invalid.
	 */
	public LangTag(final String primaryLanguage)
		throws LangTagException {
	
		this(primaryLanguage, new String[]{});
	}
	
	
	/**
	 * Creates a new extended language tag.
	 *
	 * <p>Use for extended language tags such as "zh-cmn" (Mandarin 
	 * Chinese) or "zh-yue" (Cantonese Chinese).
	 *
	 * @param primaryLanguage The primary language, as the shortest two or 
	 *                        three-letter ISO 639 code. May be {@code null}
	 *                        if the subtags are sufficient to identify the
	 *                        language.
	 * @param languageSubtags One or more extended language subtags, as
	 *                        three-letter ISO 639-3 codes. {@code null} if
	 *                        none.
	 *
	 * @throws LangTagException If the primary or extended language syntax 
	 *                          is invalid.
	 */
	public LangTag(final String primaryLanguage, final String... languageSubtags)
		throws LangTagException {
		
		if (primaryLanguage == null && 
		    (languageSubtags == null || languageSubtags.length == 0))
			throw new LangTagException("Either the primary language or the extended language subtags, or both must be defined");
		
		setPrimaryLanguage(primaryLanguage);
		setExtendedLanguageSubtags(languageSubtags);
	}
	
	
	@Override
	public String getLanguage() {
	
		StringBuilder sb = new StringBuilder();
		
		if (primaryLanguage != null)
			sb.append(primaryLanguage);
			
		if (languageSubtags != null && languageSubtags.length > 0) {
		
			for (String tag: languageSubtags) {
			
				if (sb.length() > 0)
					sb.append('-');
				
				sb.append(tag);
			}
		}
			
		return sb.toString();
	}
	
	
	@Override
	public String getPrimaryLanguage() {
	
		return primaryLanguage;
	}
	
	
	/**
	 * Checks if the specified string has a valid primary language subtag 
	 * syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isPrimaryLanguage(final String s) {
	
		return s.matches("[a-zA-Z]{2,3}");
	}
	
	
	/**
	 * Sets the primary language subtag.
	 *
	 * <p>See RFC 5646 section 2.2.1.
	 *
	 * @param primaryLanguage The primary language, as the shortest two or
	 *                        three-letter ISO 639 code. May be 
	 *                        {@code null}.
	 *
	 * @throws LangTagException If the primary language syntax is invalid.
	 */
	private void setPrimaryLanguage(final String primaryLanguage)
		throws LangTagException {
		
		if (primaryLanguage == null) {
			this.primaryLanguage = null;
			return;
		}
		
		ensureMaxLength(primaryLanguage);
		
		if (! isPrimaryLanguage(primaryLanguage))
			throw new LangTagException("Invalid primary language subtag: Must be a two or three-letter ISO 639 code");
		
		this.primaryLanguage = primaryLanguage.toLowerCase();
	}
	
	
	@Override
	public String[] getExtendedLanguageSubtags() {
	
		return languageSubtags;
	}
	
	
	/**
	 * Checks if the specified string has a valid extended language subtag 
	 * syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isExtendedLanguageSubtag(final String s) {
	
		return s.matches("[a-zA-Z]{3}");
	}
	
	
	/**
	 * Sets the extended language subtags.
	 *
	 * <p>See RFC 5646 section 2.2.2.
	 *
	 * @param languageSubtags The extended language subtags, as three-letter
	 *                        ISO 639-3 codes. {@code null} if none.
	 */
	private void setExtendedLanguageSubtags(final String... languageSubtags)
		throws LangTagException {
		
		if (languageSubtags == null || languageSubtags.length == 0) {
			this.languageSubtags = null;
			return;
		}

		this.languageSubtags = new String[languageSubtags.length];
		
		for (int i=0; i < languageSubtags.length; i++) {
		
			ensureMaxLength(languageSubtags[i]);

			if (! isExtendedLanguageSubtag(languageSubtags[i]))
				throw new LangTagException("Invalid extended language subtag: Must be a three-letter ISO 639-3 code");

			this.languageSubtags[i] = languageSubtags[i].toLowerCase();
		}
	}
	
	
	@Override
	public String getScript() {
	
		return script;
	}
	
	
	/**
	 * Checks if the specified string has a valid script subtag syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isScript(final String s) {
		
		return s.matches("[a-zA-Z]{4}");
	}
	
	
	/**
	 * Sets the script.
	 *
	 * <p>See RFC 5646 section 2.2.3.
	 *
	 * @param script The script, as a four-letter ISO 15924 code. 
	 *               {@code null} if not defined.
	 *
	 * @throws LangTagException If the script syntax is invalid.
	 */
	public void setScript(final String script)
		throws LangTagException {
	
		if (script == null) {
			this.script = null;
			return;
		}
		
		ensureMaxLength(script);
		
		if (! isScript(script))
			throw new LangTagException("Invalid script subtag: Must be a four-letter ISO 15924 code");
		
		this.script = script.substring(0, 1).toUpperCase() + 
		              script.substring(1).toLowerCase();
	}
	
	
	@Override
	public String getRegion() {
	
		return region;
	}
	
	
	/**
	 * Checks if the specified string has a valid region subtag syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isRegion(final String s) {
	
		return s.matches("[a-zA-Z]{2}|\\d{3}");
	}
	
	
	/**
	 * Sets the region.
	 *
	 * <p>See RFC 5646 section 2.2.4.
	 *
	 * @param region The region, as a two-letter ISO 3166-1 code or a three-
	 *               digit UN M.49 code. {@code null} if not defined.
	 *
	 * @throws LangTagException If the region syntax is invalid.
	 */
	public void setRegion(final String region)
		throws LangTagException {
		
		if (region == null) {
			this.region = null;
			return;
		}
		
		ensureMaxLength(region);
		
		if (! isRegion(region))
			throw new LangTagException("Invalid region subtag: Must be a two-letter ISO 3166-1 code or a three-digit UN M.49 code");
	
		this.region = region.toUpperCase();
	}
	
	
	@Override
	public String[] getVariants() {
	
		return variants;
	}
	
	
	/**
	 * Checks if the specified string has a valid variant subtag syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isVariant(final String s) {
	
		return s.matches("[a-zA-Z][a-zA-Z0-9]{4,}|[0-9][a-zA-Z0-9]{3,}");
	}
	
	
	/**
	 * Sets the variants.
	 *
	 * <p>See RFC 5646 section 2.2.5.
	 *
	 * @param variants The variants. {@code null} if not defined.
	 *
	 * @throws LangTagException If the variant syntax is invalid.
	 */
	public void setVariants(final String... variants)
		throws LangTagException {
		
		if (variants == null || variants.length == 0) {
			this.variants = null;
			return;
		}
	
		this.variants = new String[variants.length];
		
		for (int i=0; i < variants.length; i++) {
		
			ensureMaxLength(variants[i]);

			if (! isVariant(variants[i]))
				throw new LangTagException("Invalid variant subtag");

			this.variants[i] = variants[i].toLowerCase();
		}
	}
	
	
	@Override
	public String[] getExtensions() {
	
		return extensions;
	}
	
	
	/**
	 * Checks if the specified string has a valid extension singleton 
	 * syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isExtensionSingleton(final String s) {
	
		return s.matches("[0-9a-wA-Wy-zY-Z]");
	}
	
	
	/**
	 * Checks if the specified string has a valid extension subtag syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isExtension(final String s) {
	
		return s.matches("[0-9a-wA-Wy-zY-Z]-[0-9a-zA-Z]+");
	}
	
	
	/**
	 * Sets the extensions.
	 *
	 * <p>See RFC 5646 section 2.2.6.
	 *
	 * @param extensions The extensions. {@code null} if not defined.
	 *
	 * @throws LangTagException If the extension syntax is invalid.
	 */
	public void setExtensions(final String... extensions)
		throws LangTagException {
		
		if (extensions == null || extensions.length == 0) {
			this.extensions = null;
			return;
		}
	
		this.extensions = new String[extensions.length];
		
		for (int i=0; i < extensions.length; i++) {
		
			ensureMaxLength(extensions[i]);

			if (! isExtension(extensions[i]))
				throw new LangTagException("Invalid extension subtag");

			this.extensions[i] = extensions[i].toLowerCase();
		}
	}
	
	
	@Override
	public String getPrivateUse() {
	
		return privateUse;
	}
	
	
	/**
	 * Checks if the specified string has a valid private use subtag syntax.
	 *
	 * @param s The string to check. Must not be {@code null}.
	 *
	 * @return {@code true} if the syntax is correct, else {@code false}.
	 */
	private static boolean isPrivateUse(final String s) {
	
		return s.matches("x-[0-9a-zA-Z]+");
	}
	
	
	/**
	 * Sets the private use.
	 *
	 * <p>See RFC 5646 section 2.2.7.
	 *
	 * @param privateUse The private use. {@code null} if not defined.
	 *
	 * @throws LangTagException If the extension syntax is invalid.
	 */
	public void setPrivateUse(final String privateUse)
		throws LangTagException {
	
		if (privateUse == null) {
			this.privateUse = null;
			return;
		}
		
		ensureMaxLength(privateUse);
		
		if (! isPrivateUse(privateUse))
			throw new LangTagException("Invalid private use subtag");
	
		this.privateUse = privateUse.toLowerCase();
	}
	
	
	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder(getLanguage());
		
		if (script != null) {
			sb.append('-');
			sb.append(script);
		}
		
		if (region != null) {
			sb.append('-');
			sb.append(region);
		}
		
		if (variants != null) {
		
			for (String v: variants) {
				sb.append('-');
				sb.append(v);
			}
		}
		
		if (extensions != null) {
		
			for (String e: extensions) {
				sb.append('-');
				sb.append(e);
			}
		}
		
		if (privateUse != null) {
		
			sb.append('-');
			sb.append(privateUse);
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Overrides {@code Object.hashCode()}.
	 *
	 * @return The object hash code.
	 */
	@Override
	public int hashCode() {
	
		return toString().hashCode();
	}
	
	
	/**
	 * Overrides {@code Object.equals()}.
	 *
	 * @param object The object to compare to.
	 *
	 * @return {@code true} if the objects have the same value, otherwise
	 *         {@code false}.
	 */
	@Override
	public boolean equals(Object object) {
	
		return object != null &&
		       object instanceof LangTag && 
		       this.toString().equals(object.toString());
	}
	
	
	/**
	 * Parses the specified string representation of a language tag.
	 *
	 * @param s The string to parse. May be {@code null}.
	 *
	 * @return The language tag. {@code null} if the string was empty or
	 *         {@code null}.
	 *
	 * @throws LangTagException If the string has invalid language tag 
	 *                          syntax.
	 */
	public static LangTag parse(final String s)
		throws LangTagException {
		
		if (s == null || s.trim().isEmpty())
			return null;
			
		final String[] subtags = s.split("-");
		
		int pos = 0;
		
		// Parse primary lang + ext lang subtags
		String primaryLang = null;
		List<String> extLangSubtags = new LinkedList<String>();
		
		if (isPrimaryLanguage(subtags[0]))
			primaryLang = subtags[pos++];
		
		// Multiple ext lang subtags possible
		while (pos < subtags.length && isExtendedLanguageSubtag(subtags[pos]))
			extLangSubtags.add(subtags[pos++]);
		
		LangTag langTag = new LangTag(primaryLang, extLangSubtags.toArray(new String[]{}));
		
		
		// Parse script
		if (pos < subtags.length && isScript(subtags[pos]))
			langTag.setScript(subtags[pos++]);
				
		// Parse region
		if (pos < subtags.length && isRegion(subtags[pos]))
			langTag.setRegion(subtags[pos++]);
				
		// Parse variants
		List<String> variantSubtags = new LinkedList<String>();
			
		while (pos < subtags.length && isVariant(subtags[pos]))
			variantSubtags.add(subtags[pos++]);
			
		if (! variantSubtags.isEmpty())
			langTag.setVariants(variantSubtags.toArray(new String[]{}));
			
		// Parse extensions, e.g. u-usercal
		List<String> extSubtags = new LinkedList<String>();
		
		while (pos < subtags.length && isExtensionSingleton(subtags[pos])) {
			
			String singleton = subtags[pos++];
			
			if (pos == subtags.length)
				throw new LangTagException("Invalid extension subtag");
			
			extSubtags.add(singleton + "-" + subtags[pos++]);
		}
			
		if (! extSubtags.isEmpty())
			langTag.setExtensions(extSubtags.toArray(new String[]{}));
			
			
		// Parse private use, e.g. x-abc
		if (pos < subtags.length && subtags[pos].equals("x")) {
		
			if (++pos == subtags.length)
				throw new LangTagException("Invalid private use subtag");
			
			langTag.setPrivateUse("x-" + subtags[pos++]);
		}
		
		// End of tag?
		if (pos < subtags.length)
			throw new LangTagException("Invalid language tag: Unexpected subtag");
		
		return langTag;
	}
}
