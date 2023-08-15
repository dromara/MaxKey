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

import org.dromara.maxkey.authz.saml.common.AuthnRequestInfo;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.xml.security.credential.Credential;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * Abstracts the SAML Binding used to send/receive messages.
 * 
 *
 */
public interface BindingAdapter {

	public void sendSAMLMessage(SignableSAMLObject samlMessage, Endpoint endpoint, HttpServletRequest request, HttpServletResponse response) throws MessageEncodingException;
	
	public void setSecurityPolicyResolver(SecurityPolicyResolver securityPolicyResolver);
	
	public void setExtractBindingAdapter(ExtractBindingAdapter extractBindingAdapter);
	
	public void setAuthnRequestInfo(AuthnRequestInfo authnRequestInfo);
	
	public void setRelayState(String relayState);
	
	public AppsSAML20Details getSaml20Details();
	
	public AuthnRequestInfo getAuthnRequestInfo();
	
	public Credential getSigningCredential();
	
	public Credential getSpSigningCredential();
	
}
