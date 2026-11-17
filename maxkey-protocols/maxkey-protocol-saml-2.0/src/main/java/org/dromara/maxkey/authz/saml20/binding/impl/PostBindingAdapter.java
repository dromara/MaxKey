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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.dromara.maxkey.authz.saml.common.AuthnRequestInfo;
import org.dromara.maxkey.authz.saml.common.TrustResolver;
import org.dromara.maxkey.authz.saml20.binding.BindingAdapter;
import org.dromara.maxkey.authz.saml20.extract.binding.ExtractBindingAdapter;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.crypto.keystore.KeyStoreUtil;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.saml2.binding.encoding.HTTPPostSignEncoder;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
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
import org.springframework.web.servlet.ModelAndView;

public class PostBindingAdapter implements BindingAdapter{
    private static final  Logger logger = LoggerFactory.getLogger(PostBindingAdapter.class);

    static final String SAML_ACTION 	= "action";
    static final String SAML_RELAYSTATE 	= "RelayState";
    static final String SAML_RESPONSE 	= "SAMLResponse";

    protected  HTTPPostSignEncoder encoder = new HTTPPostSignEncoder();    
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
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ModelAndView sendSAMLMessage(SignableSAMLObject samlMessage,Endpoint endpoint) throws MessageEncodingException {
    	ModelAndView modelAndView = new ModelAndView("saml/saml2-post-binding");
        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        
        if (relayState!=null) {
            messageContext.setRelayState(relayState);
        }
        
        messageContext.setPeerEntityEndpoint(endpoint);
        messageContext.setOutboundSAMLMessage(samlMessage);
        messageContext.setOutboundMessageIssuer(issuerEntityName);
        messageContext.setOutboundSAMLMessageSigningCredential(signingCredential);
        
        modelAndView.addObject(SAML_ACTION, endpoint.getLocation());
        
        String encodeSAMLResponse = encoder.doSignEncode(messageContext);
        logger.debug("encode SAMLResponse {}",encodeSAMLResponse);
        modelAndView.addObject(SAML_RESPONSE,encodeSAMLResponse);
        
        if(StringUtils.isNotBlank(messageContext.getRelayState())) {
        	modelAndView.addObject(SAML_RELAYSTATE,messageContext.getRelayState());
        }
        return modelAndView;
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
