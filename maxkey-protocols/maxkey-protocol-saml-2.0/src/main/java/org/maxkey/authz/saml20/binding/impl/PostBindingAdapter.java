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
 


package org.maxkey.authz.saml20.binding.impl;

import java.security.KeyStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.apache.velocity.app.VelocityEngine;
import org.maxkey.authz.saml.common.AuthnRequestInfo;
import org.maxkey.authz.saml.common.TrustResolver;
import org.maxkey.authz.saml20.binding.BindingAdapter;
import org.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.keystore.KeyStoreUtil;
import org.maxkey.domain.apps.AppsSAML20Details;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.saml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPPostEncoder;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.KeyStoreCredentialResolver;
import org.opensaml.security.criteria.UsageCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;

public class PostBindingAdapter implements BindingAdapter, InitializingBean{
	private final static Logger logger = LoggerFactory.getLogger(PostBindingAdapter.class);

	static final String SAML_REQUEST_POST_PARAM_NAME = "SAMLRequest";
	static final String SAML_RESPONSE_POST_PARAM_NAME = "SAMLResponse";

	protected VelocityEngine velocityEngine;

	protected SAMLMessageEncoder encoder;	
	protected  String issuerEntityName;
	
	protected CredentialResolver credentialResolver;
	protected Credential signingCredential;
	protected Credential spSigningCredential;
	protected SecurityPolicyResolver securityPolicyResolver;

	protected ExtractBindingAdapter extractBindingAdapter;
	
	protected AuthnRequestInfo authnRequestInfo;
	
	protected String relayState;

	public PostBindingAdapter() {
		super();
	}
	
	public PostBindingAdapter(SAMLMessageDecoder decoder,String issuerEntityName) {
		super();
		this.issuerEntityName = issuerEntityName;
	}
	
	public PostBindingAdapter(String issuerEntityName, SecurityPolicyResolver securityPolicyResolver) {
		super();
		this.issuerEntityName = issuerEntityName;
		
		this.securityPolicyResolver = securityPolicyResolver;
	}


	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	@Override
	public void sendSAMLMessage(SignableSAMLObject samlMessage,
								Endpoint endpoint, 
								HttpServletRequest request,
								HttpServletResponse response) throws MessageEncodingException {
		
		HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response, false);
		
		BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
		
		if (relayState!=null) {
			messageContext.setRelayState(relayState);
		}
		
		messageContext.setOutboundMessageTransport(outTransport);
		messageContext.setPeerEntityEndpoint(endpoint);
		messageContext.setOutboundSAMLMessage(samlMessage);
		messageContext.setOutboundMessageIssuer(issuerEntityName);
		messageContext.setOutboundSAMLMessageSigningCredential(signingCredential);
		
		encoder.encode(messageContext);
		
	}


	public void  buildCredentialResolver(CredentialResolver credentialResolver) throws Exception{
		this.credentialResolver=credentialResolver;
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(new EntityIdCriterion(getKeyStoreLoader().getEntityName()));
		criteriaSet.add(new UsageCriterion(UsageType.SIGNING));

		try {
			signingCredential = credentialResolver.resolveSingle(criteriaSet);
		} catch (SecurityException e) {
			logger.error("Credential Resolver error . ", e);
			throw new Exception(e);
		}
		Validate.notNull(signingCredential);
	}
	
	public Credential  buildSPSigningCredential() throws Exception{
		KeyStore trustKeyStore = KeyStoreUtil.bytes2KeyStore(getSaml20Details().getKeyStore(),
				getKeyStoreLoader().getKeyStore().getType(),
				getKeyStoreLoader().getKeystorePassword());
		
		TrustResolver trustResolver=new TrustResolver();
		KeyStoreCredentialResolver credentialResolver =trustResolver.buildKeyStoreCredentialResolver(
							trustKeyStore, 
							getSaml20Details().getEntityId(), 
							getKeyStoreLoader().getKeystorePassword());
	
		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(new EntityIdCriterion(getSaml20Details().getEntityId()));
		criteriaSet.add(new UsageCriterion(UsageType.ENCRYPTION));

		try {
			spSigningCredential = credentialResolver.resolveSingle(criteriaSet);
		} catch (SecurityException e) {
			logger.error("Credential Resolver error . ", e);
			throw new Exception(e);
		}
		Validate.notNull(spSigningCredential);
		
		return spSigningCredential;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		HTTPPostEncoder HTTPPostEncoder = new HTTPPostEncoder();
		HTTPPostEncoder.setVelocityEngine(velocityEngine);
		HTTPPostEncoder.setVelocityTemplateId("/templates/saml2-post-binding.vm");
		encoder = HTTPPostEncoder;
	}

	/**
	 * @param securityPolicyResolver the securityPolicyResolver to set
	 */
	public void setSecurityPolicyResolver(
			SecurityPolicyResolver securityPolicyResolver) {
		this.securityPolicyResolver = securityPolicyResolver;
	}



	public void setIssuerEntityName(String issuerEntityName) {
		this.issuerEntityName = issuerEntityName;
	}

	public KeyStoreLoader getKeyStoreLoader() {
		return extractBindingAdapter.getKeyStoreLoader();
	}

	public Credential getSigningCredential() {
		return signingCredential;
	}

	public void setSigningCredential(Credential signingCredential) {
		this.signingCredential = signingCredential;
	}

	public Credential getSpSigningCredential() {
		return spSigningCredential;
	}

	public void setSpSigningCredential(Credential spSigningCredential) {
		this.spSigningCredential = spSigningCredential;
	}
	
	public AuthnRequestInfo getAuthnRequestInfo() {
		return authnRequestInfo;
	}

	public void setAuthnRequestInfo(AuthnRequestInfo authnRequestInfo) {
		this.authnRequestInfo = authnRequestInfo;
	}

	public void setRelayState(String relayState) {
		this.relayState = relayState;
	}

	@Override
	public void setExtractBindingAdapter(
			ExtractBindingAdapter extractBindingAdapter) {
		this.extractBindingAdapter=extractBindingAdapter;
		this.credentialResolver=extractBindingAdapter.getCredentialResolver();
		try {
			buildCredentialResolver(extractBindingAdapter.getCredentialResolver());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public AppsSAML20Details getSaml20Details() {
		return extractBindingAdapter.getSaml20Detail();
	}
}
