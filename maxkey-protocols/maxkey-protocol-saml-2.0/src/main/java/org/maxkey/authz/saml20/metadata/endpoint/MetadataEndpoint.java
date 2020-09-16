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
 

package org.maxkey.authz.saml20.metadata.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.maxkey.authz.saml.common.TrustResolver;
import org.maxkey.authz.saml20.metadata.MetadataGenerator;
import org.maxkey.constants.ContentType;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.domain.Saml20Metadata;
import org.maxkey.web.WebContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = { "/metadata/saml20/" })
public class MetadataEndpoint {
	private final static Logger logger = LoggerFactory
			.getLogger(MetadataEndpoint.class);

	@Autowired
	@Qualifier("keyStoreLoader")
	private KeyStoreLoader keyStoreLoader;
	
	@Autowired
	@Qualifier("issuerEntityName")
	private String issuerEntityName;
	
	@Autowired
	@Qualifier("saml20Metadata")	
	private Saml20Metadata saml20Metadata;
	
	private Credential signingCredential;
	
	public static String IDP_METADATA_PREFIX = "Idp_Metadata_";

	@RequestMapping(value = "/{appid}.xml",produces = "application/xml")
	public ModelAndView  metadata(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("appid") String appId) {
	    response.setContentType(ContentType.APPLICATION_XML_UTF8);
	    appId = appId.substring(IDP_METADATA_PREFIX.length(), appId.length());
		if(signingCredential==null){
	        TrustResolver trustResolver = new TrustResolver();
	        CredentialResolver credentialResolver=(CredentialResolver)trustResolver.buildKeyStoreCredentialResolver(
	        		keyStoreLoader.getKeyStore(),
					keyStoreLoader.getEntityName(),
					keyStoreLoader.getKeystorePassword());
	        
	         CriteriaSet criteriaSet = new CriteriaSet();
	         
	 		 criteriaSet.add(new EntityIDCriteria(keyStoreLoader.getEntityName()));
	 		
	 		 criteriaSet.add(new UsageCriteria(UsageType.SIGNING));
	 		 
			
			try {
				signingCredential = credentialResolver.resolveSingle(criteriaSet);
			}catch (SecurityException e) {
				logger.error("Credential Resolver error .", e);
				
			}
		}
			
		Validate.notNull(signingCredential);
		
		try{
			
			MetadataGenerator metadataGenerator =new MetadataGenerator();
			
	        IDPSSODescriptor descriptor = metadataGenerator.buildIDPSSODescriptor();
	
	        descriptor.getSingleSignOnServices().add(metadataGenerator.getSingleSignOnService(WebContext.getHttpContextPath()+"/authz/saml20/"+appId,null));
	        
	        descriptor.getSingleSignOnServices().add(metadataGenerator.getSingleSignOnService(WebContext.getHttpContextPath()+"/authz/saml20/"+appId,SAMLConstants.SAML2_REDIRECT_BINDING_URI));
	        
	        descriptor.getSingleSignOnServices().add(metadataGenerator.getSingleSignOnService(WebContext.getHttpContextPath()+"/authz/saml20/"+appId,SAMLConstants.SAML2_POST_SIMPLE_SIGN_BINDING_URI));
	        
	        descriptor.getSingleLogoutServices().add(metadataGenerator.getSingleLogoutService(WebContext.getHttpContextPath()+"/logout",null));
	        
	        descriptor.getManageNameIDServices().add(metadataGenerator.getManageNameIDService(WebContext.getHttpContextPath()+"/saml/metadata/"+IDP_METADATA_PREFIX+appId+".xml"));
	             
	        descriptor.getKeyDescriptors().add(metadataGenerator.generateEncryptionKeyDescriptor(signingCredential));  
	         
	        descriptor.getKeyDescriptors().add(metadataGenerator.generateSignKeyDescriptor(signingCredential));  
	         
	        descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.TRANSIENT)); 
	        descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.PERSISTENT)); 
	        descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.EMAIL)); 
	        descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.ENTITY));
	        
	        ContactPersonTypeEnumeration contactPersonType=null;
	        if(saml20Metadata.getContactType().equalsIgnoreCase(Saml20Metadata.ContactPersonType.ADMINISTRATIVE)){
	        	contactPersonType=ContactPersonTypeEnumeration.ADMINISTRATIVE;
	        }else if(saml20Metadata.getContactType().equalsIgnoreCase(Saml20Metadata.ContactPersonType.TECHNICAL)){
	        	contactPersonType=ContactPersonTypeEnumeration.TECHNICAL;
	        }else if(saml20Metadata.getContactType().equalsIgnoreCase(Saml20Metadata.ContactPersonType.BILLING)){
	        	contactPersonType=ContactPersonTypeEnumeration.BILLING;
	        }else if(saml20Metadata.getContactType().equalsIgnoreCase(Saml20Metadata.ContactPersonType.SUPPORT)){
	        	contactPersonType=ContactPersonTypeEnumeration.SUPPORT;
			}else if(saml20Metadata.getContactType().equalsIgnoreCase(Saml20Metadata.ContactPersonType.OTHER)){
				contactPersonType=ContactPersonTypeEnumeration.OTHER;
			}
	        descriptor.getContactPersons().add(metadataGenerator.getContactPerson(
	        					saml20Metadata.getCompany(),
	        					saml20Metadata.getGivenName(),
	        					saml20Metadata.getSurName(),
	        					saml20Metadata.getEmailAddress(),
	        					saml20Metadata.getTelephoneNumber(),
	        					contactPersonType));
	         
	        descriptor.setOrganization(metadataGenerator.getOrganization(
	        					saml20Metadata.getOrgName(),
	        					saml20Metadata.getOrgDisplayName(),
	        					saml20Metadata.getOrgURL()));
	
	        EntityDescriptor entityDescriptor=metadataGenerator.buildEntityDescriptor(issuerEntityName,descriptor);
	        
	        String entityDescriptorXml=XMLHelper.prettyPrintXML(metadataGenerator.marshallerMetadata(entityDescriptor));
	         
	        logger.trace("EntityDescriptor element XML : \\n");
	        logger.trace(entityDescriptorXml);
	        
	        ModelAndView mv = new ModelAndView("trusts/saml_v20_metadata");
	        mv.addObject("metadata", entityDescriptorXml);
	        return mv;
		}catch (Exception e) {
			logger.error(e.getMessage(),e); 
        }
		

		return null;
	}

	/**
	 * @param keyStoreLoader
	 *            the keyStoreLoader to set
	 */
	public void setKeyStoreLoader(KeyStoreLoader keyStoreLoader) {
		this.keyStoreLoader = keyStoreLoader;
	}


	public void setIssuerEntityName(String issuerEntityName) {
		this.issuerEntityName = issuerEntityName;
	}

}
