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
 

package org.dromara.maxkey.authz.saml20.binding.impl;

import java.security.KeyStore;

import org.dromara.maxkey.authz.saml.common.TrustResolver;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.xml.security.credential.CredentialResolver;

public class ExtractRedirectBindingAdapter extends ExtractPostBindingAdapter{

	public ExtractRedirectBindingAdapter(SAMLMessageDecoder decoder) {
		this.decoder = decoder;
	}
	
	public ExtractRedirectBindingAdapter(SAMLMessageDecoder decoder,String issuingEntityName) {
		this.decoder = decoder;
		this.issuingEntityName = issuingEntityName;
	}
	
	public ExtractRedirectBindingAdapter(SAMLMessageDecoder decoder,String issuingEntityName, SecurityPolicyResolver securityPolicyResolver) {
		this.decoder = decoder;
		this.issuingEntityName = issuingEntityName;
		
		this.securityPolicyResolver = securityPolicyResolver;
	}
	
	@Override
	public void buildSecurityPolicyResolver(KeyStore trustKeyStore) {

		TrustResolver trustResolver = new TrustResolver(trustKeyStore,
					keyStoreLoader.getEntityName(),
					keyStoreLoader.getKeystorePassword(), 
					issueInstantRule,
					messageReplayRule,
					"Redirect");
		credentialResolver = (CredentialResolver)trustResolver.getKeyStoreCredentialResolver();
		this.securityPolicyResolver = trustResolver.getStaticSecurityPolicyResolver();
	}

}
