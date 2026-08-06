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
package org.dromara.maxkey.authz.saml20.metadata;

import org.dromara.maxkey.authz.saml.common.TrustResolver;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.junit.jupiter.api.Test;
import org.opensaml.util.storage.MapBasedStorageService;
import org.opensaml.util.storage.ReplayCache;

import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.common.binding.security.MessageReplayRule;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.util.XMLHelper;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

import java.security.KeyStore;

public class MetadataGeneratorTest {
    private static final  Logger logger = LoggerFactory.getLogger(MetadataGeneratorTest.class);

   @Test     
   void metadataGeneratorTest() {
	   MetadataGeneratorTest metadataGenerator=new  MetadataGeneratorTest();
       
       metadataGenerator.samlmtest();
   }
   
   
   @SuppressWarnings({ "unchecked", "rawtypes" })
   void samlmtest(){
        try {
             KeyStoreLoader keyStoreLoader=new  KeyStoreLoader();
             keyStoreLoader.setKeystorePassword("secret");
             keyStoreLoader.setKeystoreFile(new FileSystemResource("D:/JavaIDE/cert/idp-keystore.jks"));
             keyStoreLoader.afterPropertiesSet();
             KeyStore trustKeyStore =keyStoreLoader.getKeyStore();
             
             IssueInstantRule issueInstantRule=new IssueInstantRule(90,300);
             ReplayCache replayCache=new ReplayCache(new MapBasedStorageService(),14400000);
             MessageReplayRule messageReplayRule=new MessageReplayRule(replayCache);
             
             TrustResolver trustResolver = new TrustResolver(
                        trustKeyStore,
                        "idp",
                        keyStoreLoader.getKeystorePassword(), issueInstantRule,
                        messageReplayRule,
                        "POST"
                    );
             CredentialResolver credentialResolver=(CredentialResolver)trustResolver.getKeyStoreCredentialResolver();
             
             CriteriaSet criteriaSet = new CriteriaSet();
             
              criteriaSet.add(new EntityIDCriteria("idp"));
             
              criteriaSet.add(new UsageCriteria(UsageType.SIGNING));
              Credential signingCredential=null;
             
             try {
                  signingCredential = credentialResolver.resolveSingle(criteriaSet);
            } catch (SecurityException e) {
                System.out.println("Credential resolve error : "+ e);
                throw new Exception(e);
            }
             
             MetadataGenerator metadataGenerator=new MetadataGenerator();
             
            IDPSSODescriptor descriptor = metadataGenerator.buildIDPSSODescriptor();

            descriptor.getSingleSignOnServices().add(metadataGenerator.getSingleSignOnService("http://sso.maxkey.org/sso",null));
            
            descriptor.getSingleSignOnServices().add(metadataGenerator.getSingleSignOnService("http://sso.maxkey.org/sso",SAMLConstants.SAML2_POST_SIMPLE_SIGN_BINDING_URI));
            
            descriptor.getSingleLogoutServices().add(metadataGenerator.getSingleLogoutService("http://sso.maxkey.org/slo",null));
                 
            descriptor.getKeyDescriptors().add(metadataGenerator.generateEncryptionKeyDescriptor(signingCredential));  
             
            descriptor.getKeyDescriptors().add(metadataGenerator.generateSignKeyDescriptor(signingCredential));  
             
            descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.TRANSIENT)); 
            descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.PERSISTENT)); 
            descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.EMAIL)); 
            descriptor.getNameIDFormats().add(metadataGenerator.generateNameIDFormat(NameIDType.ENTITY));
             
            descriptor.getContactPersons().add(metadataGenerator.getContactPerson("maxkey","shi","ming","shimingxy@163.com","18724229876",null));
             
            descriptor.setOrganization(metadataGenerator.getOrganization("maxkey","maxkey","http://sso.maxkey.org"));

            String entityId="http://www.test.com";
            
            EntityDescriptor entityDescriptor=metadataGenerator.buildEntityDescriptor(entityId,descriptor);
            
            String descriptorelementxml=XMLHelper.prettyPrintXML(metadataGenerator.marshallerMetadata(entityDescriptor));
             
            System.out.println("descriptor elementxm:\\n");
            System.out.println(descriptorelementxml);
             
            logger.info(descriptorelementxml);
          }
          catch (Exception e) {
                    e.printStackTrace();
            }
   }
   
}
