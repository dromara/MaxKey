
package org.maxkey.authz.saml20;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.saml.common.AuthnRequestInfo;
import org.maxkey.domain.apps.AppsSAML20Details;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;

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
