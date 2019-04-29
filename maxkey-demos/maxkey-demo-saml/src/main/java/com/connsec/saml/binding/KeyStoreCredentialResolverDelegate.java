/*
*   Copyright 2010 James Cox <james.s.cox@gmail.com>
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package com.connsec.saml.binding;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;

import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.ResourceUtils;


/**
 * 
 * Trivial implementation of CredentialResolver.  Not recommended for production use as
 * it is not secure.
 * 
 * This class loads a Java keystore from spring config and instantiates 
 * an Open SAML KeyStoreCredentialResolver.  All calls are then delegated to the
 * KeyStoreCredentialResolver.
 * 
 * 
 * @author jcox
 *
 */
public class KeyStoreCredentialResolverDelegate implements CredentialResolver, InitializingBean  {

	
	private KeyStoreCredentialResolver  keyStoreCredentialResolver;
	
	private String b64EncodedKeystore; 
	private String keystorePassword;
	private String keyStoreFile;
	private Map<String,String> privateKeyPasswordsByAlias;
	
	
	@Required
	public void setB64EncodedKeystore(String b64EncodedKeystore) {
		this.b64EncodedKeystore = b64EncodedKeystore;
	}

	@Required
	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	@Required
	public void setPrivateKeyPasswordsByAlias(
			Map<String, String> privateKeyPasswordsByAlias) {
		this.privateKeyPasswordsByAlias = privateKeyPasswordsByAlias;
	}

	@Override
	public Iterable<Credential> resolve(CriteriaSet criteriaSet)
			throws SecurityException {
		return keyStoreCredentialResolver.resolve(criteriaSet);
	}

	@Override
	public Credential resolveSingle(CriteriaSet criteriaSet) throws SecurityException {
		return keyStoreCredentialResolver.resolveSingle(criteriaSet);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		KeyStore ks = KeyStore.getInstance("JKS");
		File file = ResourceUtils.getFile(keyStoreFile);
		ks.load(new FileInputStream(file), keystorePassword.toCharArray());
//		ks.load(new ByteArrayInputStream(Base64.decodeBase64(b64EncodedKeystore.getBytes())), keystorePassword.toCharArray());

		keyStoreCredentialResolver = new KeyStoreCredentialResolver(ks, privateKeyPasswordsByAlias);
	}

	/**
	 * @param keyStoreFile the keyStoreFile to set
	 */
	public void setKeyStoreFile(String keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	/**
	 * @return the keyStoreFile
	 */
	public String getKeyStoreFile() {
		return keyStoreFile;
	}



}
