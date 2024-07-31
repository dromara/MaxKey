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

import org.apache.commons.lang.Validate;
import org.apache.velocity.app.VelocityEngine;
import org.dromara.maxkey.authz.saml.common.AuthnRequestInfo;
import org.dromara.maxkey.authz.saml.common.TrustResolver;
import org.dromara.maxkey.authz.saml20.binding.BindingAdapter;
import org.dromara.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.crypto.keystore.KeyStoreUtil;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.saml2.binding.encoding.HTTPPostEncoder;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostBindingAdapter implements BindingAdapter, InitializingBean{
	private static final  Logger logger = LoggerFactory.getLogger(PostBindingAdapter.class);

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		criteriaSet.add(new EntityIDCriteria(getKeyStoreLoader().getEntityName()));
		criteriaSet.add(new UsageCriteria(UsageType.SIGNING));

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
		criteriaSet.add(new EntityIDCriteria(getSaml20Details().getEntityId()));
		criteriaSet.add(new UsageCriteria(UsageType.ENCRYPTION));

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
		encoder = new HTTPPostEncoder(velocityEngine,"/templates/saml2-post-binding.vm");
	}

	/**
	 * @param securityPolicyResolver the securityPolicyResolver to set
	 */
	@Override
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

	@Override
	public Credential getSigningCredential() {
		return signingCredential;
	}

	public void setSigningCredential(Credential signingCredential) {
		this.signingCredential = signingCredential;
	}

	@Override
	public Credential getSpSigningCredential() {
		return spSigningCredential;
	}

	public void setSpSigningCredential(Credential spSigningCredential) {
		this.spSigningCredential = spSigningCredential;
	}
	
	@Override
	public AuthnRequestInfo getAuthnRequestInfo() {
		return authnRequestInfo;
	}

	@Override
	public void setAuthnRequestInfo(AuthnRequestInfo authnRequestInfo) {
		this.authnRequestInfo = authnRequestInfo;
	}

	@Override
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
