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
 

/**
 * 
 */
package org.dromara.maxkey.authz.saml.common;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.common.binding.security.MessageReplayRule;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.ws.security.provider.StaticSecurityPolicyResolver;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;

/**
 * @author Crystal.Sea
 * 
 */
public class TrustResolver {

	KeyStoreCredentialResolver keyStoreCredentialResolver;
	SignatureSecurityPolicyRule signatureSecurityPolicyRule;
	StaticSecurityPolicyResolver staticSecurityPolicyResolver;
	String binding;

	public TrustResolver() {
		super();
	}
	
	public KeyStoreCredentialResolver  buildKeyStoreCredentialResolver(KeyStore trustKeyStore, String key, String password){
		Map<String, String> passwords = new HashMap<String, String>();
		passwords.put(key, password);

		keyStoreCredentialResolver = new KeyStoreCredentialResolver(trustKeyStore, passwords);
		
		return keyStoreCredentialResolver;
	}

	public TrustResolver(KeyStore trustKeyStore, String issuing, String password) {
		super();
		buildKeyStoreCredentialResolver(trustKeyStore,  issuing,  password);
		initPolicyRule();
	}

	public TrustResolver(KeyStore trustKeyStore, String issuing,
			String password, IssueInstantRule issueInstantRule,
			MessageReplayRule messageReplayRule,String binding) {
		super();
		
		this.binding=binding;
		buildKeyStoreCredentialResolver(trustKeyStore,  issuing,  password);
		
		initPolicyRule();
		
		SecurityPolicyDelegate securityPolicyDelegate = new SecurityPolicyDelegate();
		if(binding.equalsIgnoreCase("post")){
			securityPolicyDelegate.addSecurityPolicy(signatureSecurityPolicyRule);
		}
		securityPolicyDelegate.addSecurityPolicy(issueInstantRule);
		securityPolicyDelegate.addSecurityPolicy(messageReplayRule);
		staticSecurityPolicyResolver = new StaticSecurityPolicyResolver(securityPolicyDelegate);
	}
	
	public void initPolicyRule(){
		signatureSecurityPolicyRule = new SignatureSecurityPolicyRule(keyStoreCredentialResolver, new SAMLSignatureProfileValidator());
		signatureSecurityPolicyRule.loadTrustEngine();
	}

	public void loadStaticSecurityPolicyResolver(
			IssueInstantRule issueInstantRule,
			MessageReplayRule messageReplayRule) {
		SecurityPolicyDelegate securityPolicyDelegate = new SecurityPolicyDelegate();
		if(binding.equalsIgnoreCase("post")){
			securityPolicyDelegate.addSecurityPolicy(signatureSecurityPolicyRule);
		}
		securityPolicyDelegate.addSecurityPolicy(issueInstantRule);
		securityPolicyDelegate.addSecurityPolicy(messageReplayRule);

		staticSecurityPolicyResolver = new StaticSecurityPolicyResolver(securityPolicyDelegate);
	}

	public KeyStoreCredentialResolver getKeyStoreCredentialResolver() {
		return keyStoreCredentialResolver;
	}

	public StaticSecurityPolicyResolver getStaticSecurityPolicyResolver() {
		return staticSecurityPolicyResolver;
	}

}
