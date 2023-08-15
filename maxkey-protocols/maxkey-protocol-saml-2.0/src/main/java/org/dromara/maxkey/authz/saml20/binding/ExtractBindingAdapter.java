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
 


package org.dromara.maxkey.authz.saml20.binding;

import java.security.KeyStore;

import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.CredentialResolver;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * Abstracts the SAML Binding used to send/receive messages.
 * 
 *
 */
public interface ExtractBindingAdapter {

	@SuppressWarnings("rawtypes")
	public SAMLMessageContext extractSAMLMessageContext(HttpServletRequest request) throws MessageDecodingException, SecurityException;

	public String extractSAMLMessage(HttpServletRequest request);
	
	public void setSecurityPolicyResolver(SecurityPolicyResolver securityPolicyResolver);
	
	public void buildSecurityPolicyResolver(KeyStore trustKeyStore);
	
	public void setSaml20Detail(AppsSAML20Details  saml20Detail);
	
	public AppsSAML20Details getSaml20Detail();
	
	public KeyStoreLoader getKeyStoreLoader();
	
	public CredentialResolver getCredentialResolver();
}
