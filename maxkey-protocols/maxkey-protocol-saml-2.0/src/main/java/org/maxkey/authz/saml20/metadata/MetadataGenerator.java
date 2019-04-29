package org.maxkey.authz.saml20.metadata;


import org.opensaml.DefaultBootstrap;
import org.maxkey.authz.saml.common.TrustResolver;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.opensaml.Configuration;

import org.opensaml.util.storage.MapBasedStorageService;
import org.opensaml.util.storage.ReplayCache;

import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.common.binding.security.MessageReplayRule;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.util.XMLHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.metadata.Company;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml2.metadata.EmailAddress;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.GivenName;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.LocalizedString;
import org.opensaml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml2.metadata.OrganizationName;
import org.opensaml.saml2.metadata.OrganizationURL;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.SurName;
import org.opensaml.saml2.metadata.TelephoneNumber;
import org.opensaml.saml2.metadata.impl.CompanyBuilder;
import org.opensaml.saml2.metadata.impl.EmailAddressBuilder;
import org.opensaml.saml2.metadata.impl.GivenNameBuilder;
import org.opensaml.saml2.metadata.impl.OrganizationBuilder;
import org.opensaml.saml2.metadata.impl.OrganizationDisplayNameBuilder;
import org.opensaml.saml2.metadata.impl.OrganizationNameBuilder;
import org.opensaml.saml2.metadata.impl.OrganizationURLBuilder;
import org.opensaml.saml2.metadata.impl.SurNameBuilder;
import org.opensaml.saml2.metadata.impl.TelephoneNumberBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStore;

public class MetadataGenerator {
	private final static Logger logger = LoggerFactory.getLogger(MetadataGenerator.class);

   public static void main(String args[]) {
	   MetadataGenerator metadataGenerator=new  MetadataGenerator();
	   
	   metadataGenerator.samlmtest();
   }
   
   
   public  void samlmtest(){
	    try {
	         // OpenSAML 2.5.3
	       
	         XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();     
	         
	         KeyStoreLoader keyStoreLoader=new  KeyStoreLoader();
	         keyStoreLoader.setKeystorePassword("secret");
	         keyStoreLoader.setKeystoreFile("D:/JavaIDE/cert/idp-keystore.jks");
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
	 		
	        IDPSSODescriptor descriptor = buildIDPSSODescriptor();

	        descriptor.getSingleSignOnServices().add(getSingleSignOnService("http://www.qoros.com/sso",null));
	        
	        descriptor.getSingleSignOnServices().add(getSingleSignOnService("http://www.qoros.com/sso",SAMLConstants.SAML2_POST_SIMPLE_SIGN_BINDING_URI));
	        
	        descriptor.getSingleLogoutServices().add(getSingleLogoutService("http://www.qoros.com/slo",null));
	             
	        descriptor.getKeyDescriptors().add(generateEncryptionKeyDescriptor(signingCredential));  
	         
	        descriptor.getKeyDescriptors().add(generateSignKeyDescriptor(signingCredential));  
	         
	        descriptor.getNameIDFormats().add(generateNameIDFormat(NameIDType.TRANSIENT)); 
	        descriptor.getNameIDFormats().add(generateNameIDFormat(NameIDType.PERSISTENT)); 
	        descriptor.getNameIDFormats().add(generateNameIDFormat(NameIDType.EMAIL)); 
	        descriptor.getNameIDFormats().add(generateNameIDFormat(NameIDType.ENTITY));
	         
            descriptor.getContactPersons().add(getContactPerson("qoros","shi","ming","shimh@connsec.com","18724229876",null));
             
            descriptor.setOrganization(getOrganization("qoros","qorosc","http://www.qoros.com"));

            String entityId="http://www.test.com";
            
            EntityDescriptor entityDescriptor=buildEntityDescriptor(entityId,descriptor);
            
	        String descriptorelementxml=XMLHelper.prettyPrintXML(marshallerMetadata(entityDescriptor));
	         
	        System.out.println("descriptor elementxm:\\n");
	        System.out.println(descriptorelementxml);
	         
	        logger.info(descriptorelementxml);
	      }
	      catch (Exception e) {
	                e.printStackTrace();
	        }
   }
   
   
   public IDPSSODescriptor buildIDPSSODescriptor(){
	   
	   QName qname = new QName(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
       IDPSSODescriptor idpSSODescriptor = (IDPSSODescriptor) buildXMLObject(qname);
       idpSSODescriptor.addSupportedProtocol(SAMLConstants.SAML20P_NS); 
       
       return idpSSODescriptor;
   }
   
   public EntityDescriptor buildEntityDescriptor(String entityId,RoleDescriptor roleDescriptor){
	   
	   SAMLObjectBuilder<EntityDescriptor> builder = (SAMLObjectBuilder<EntityDescriptor>) builderFactory.getBuilder(EntityDescriptor.DEFAULT_ELEMENT_NAME);
       EntityDescriptor entityDescriptor = builder.buildObject();
       entityDescriptor.setEntityID(entityId);
       entityDescriptor.getRoleDescriptors().add(roleDescriptor);
       
       return entityDescriptor;
   }
   
   public Document marshallerMetadata(EntityDescriptor entityDescriptor){
	   Document document = null;
	   try{
		   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	       
	       DocumentBuilder documentBuilder = factory.newDocumentBuilder();  
	       
	       document = documentBuilder.newDocument();  
	       
	       Marshaller marshaller = marshallerFactory.getMarshaller(entityDescriptor);  
	       marshaller.marshall(entityDescriptor, document);  
	   }catch (Exception e) {
           e.printStackTrace();
	   }
	   
       return document;
       
   }
   
   public ManageNameIDService getManageNameIDService(String url){
	   QName manageNameIDServiceQName = new QName(SAMLConstants.SAML20MD_NS, ManageNameIDService.DEFAULT_ELEMENT_LOCAL_NAME,
               SAMLConstants.SAML20MD_PREFIX);
	   ManageNameIDService manageNameIDService= (ManageNameIDService) buildXMLObject(manageNameIDServiceQName);
	   manageNameIDService.setLocation(url);
	   manageNameIDService.setBinding(SAMLConstants.SAML2_POST_BINDING_URI);
	   
	   return null;
   }
   
   public Organization getOrganization(String name,String displayName,String url){
	   Organization organization=new OrganizationBuilder().buildObject();
	   
       OrganizationName organizationName=new OrganizationNameBuilder().buildObject();
       LocalizedString orglocalizedString=new LocalizedString();
       orglocalizedString.setLocalizedString(name);
       organizationName.setName(orglocalizedString);
       organization.getOrganizationNames().add(organizationName);
       
       OrganizationDisplayName organizationDisplayName=new OrganizationDisplayNameBuilder().buildObject();
	   LocalizedString localizedString=new LocalizedString();
	   localizedString.setLocalizedString(displayName);
	   organizationDisplayName.setName(localizedString);
       organization.getDisplayNames().add(organizationDisplayName);
       
       OrganizationURL organizationURL=new OrganizationURLBuilder().buildObject();
       LocalizedString urllocalizedString=new LocalizedString();
       urllocalizedString.setLocalizedString(url);
       organizationURL.setURL(urllocalizedString);
       organization.getURLs().add(organizationURL);
       
       return organization;
   }
   
   public ContactPerson getContactPerson(String companyName,String givenName,String surName,String emailAddress,String telephoneNumber,ContactPersonTypeEnumeration contactPersonType){
	   
	 QName contactQName = new QName(SAMLConstants.SAML20MD_NS, ContactPerson.DEFAULT_ELEMENT_LOCAL_NAME,
               SAMLConstants.SAML20MD_PREFIX);

  	 ContactPerson contactPerson= (ContactPerson) buildXMLObject(contactQName);
  	 
  	 contactPerson.setType(contactPersonType);
  	 
  	 Company company =new CompanyBuilder ().buildObject();
  	 company.setName(companyName);
  	 contactPerson.setCompany(company);
  	 
  	 GivenName contactPersonGivenName=(new GivenNameBuilder()).buildObject();
  	 contactPersonGivenName.setName(givenName);
  	 contactPerson.setGivenName(contactPersonGivenName);//名
  	 
  	 SurName contactPersonSurName =new SurNameBuilder().buildObject();
  	 contactPersonSurName.setName(surName);
  	 contactPerson.setSurName(contactPersonSurName);//姓

  	 EmailAddress contactPersonEmailAddress =(new EmailAddressBuilder()).buildObject();
  	 contactPersonEmailAddress.setAddress(emailAddress);
  	 contactPerson.getEmailAddresses().add(contactPersonEmailAddress);
  	 
  	 TelephoneNumber contactPersonTelephoneNumber=(new TelephoneNumberBuilder()).buildObject();
  	 contactPersonTelephoneNumber.setNumber(telephoneNumber);
  	 contactPerson.getTelephoneNumbers().add(contactPersonTelephoneNumber);
  	 
  	 return contactPerson;
   }
   public SingleSignOnService getSingleSignOnService(String location,String binding){
	   QName ssoQName = new QName(SAMLConstants.SAML20MD_NS, SingleSignOnService.DEFAULT_ELEMENT_LOCAL_NAME,SAMLConstants.SAML20MD_PREFIX);
	   SingleSignOnService singleSignOnService=(SingleSignOnService) buildXMLObject(ssoQName);
	   if(binding==null){
		   binding=SAMLConstants.SAML2_POST_BINDING_URI;
	   }
	   singleSignOnService.setBinding(binding);
	   singleSignOnService.setLocation(location);
	  
	   return singleSignOnService ;
   }
   
   public SingleLogoutService getSingleLogoutService(String location,String binding){
	   QName sloQName = new QName(SAMLConstants.SAML20MD_NS, SingleLogoutService.DEFAULT_ELEMENT_LOCAL_NAME,SAMLConstants.SAML20MD_PREFIX);
	   SingleLogoutService singleLogoutService=(SingleLogoutService) buildXMLObject(sloQName);
	   if(binding==null){
		   binding=SAMLConstants.SAML2_REDIRECT_BINDING_URI;
	   }
	   singleLogoutService.setBinding(binding);
  	   singleLogoutService.setLocation(location);
  	   return singleLogoutService;
   }
   
   public NameIDFormat generateNameIDFormat(String nameIDType){
	   NameIDFormat nameIDFormat =((SAMLObjectBuilder<NameIDFormat>) builderFactory.getBuilder(NameIDFormat.DEFAULT_ELEMENT_NAME)).buildObject();  
       nameIDFormat.setFormat(nameIDType);  
       return nameIDFormat;
   }
   
   
   public KeyInfoGenerator getKeyInfoGenerator (){
	   X509KeyInfoGeneratorFactory keyInfoGeneratorFactory = new X509KeyInfoGeneratorFactory();  
	   keyInfoGeneratorFactory.setEmitEntityCertificate(true);  
	   KeyInfoGenerator keyInfoGenerator = keyInfoGeneratorFactory.newInstance();  
	   return keyInfoGenerator;
   }
   
   public KeyDescriptor generateSignKeyDescriptor(Credential signingCredential){
	   KeyDescriptor signKeyDescriptor = ((SAMLObjectBuilder<KeyDescriptor>) builderFactory.getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
       
       signKeyDescriptor.setUse(UsageType.SIGNING);  //Set usage  
       
       // Generating key info. The element will contain the public key. The key is used to by the IDP to verify signatures  
       try {  
        signKeyDescriptor.setKeyInfo(getKeyInfoGenerator().generate(signingCredential));  
       } catch (SecurityException e) {  
        log.error(e.getMessage(), e);  
       }  
       
       return signKeyDescriptor;
   }
   
   public KeyDescriptor generateEncryptionKeyDescriptor(Credential signingCredential){
	   KeyDescriptor encryptionKeyDescriptor = ((SAMLObjectBuilder<KeyDescriptor>) builderFactory.getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
	   encryptionKeyDescriptor.setUse(UsageType.ENCRYPTION); 
	   
	   // Generating key info. The element will contain the public key. The key is used to by the IDP to encrypt data  
	   try {  
		   encryptionKeyDescriptor.setKeyInfo(getKeyInfoGenerator().generate(signingCredential));  
	   } catch (SecurityException e) {  
	    log.error(e.getMessage(), e);  
	   }  
	   
	   return encryptionKeyDescriptor;
   }
   
   public static XMLObject buildXMLObject(QName objectQName){
       XMLObjectBuilder builder = builderFactory.getBuilder(objectQName);
       if(builder == null){
          ; //fail("Unable to retrieve builder for object QName " + objectQName);
       }
       return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
   }
   
   
   protected static  XMLObject unmarshallElement( Document doc) {
       try {
           Element samlElement = doc.getDocumentElement();

           Unmarshaller unmarshaller = org.opensaml.xml.Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
           if (unmarshaller == null) {
               ;//fail("Unable to retrieve unmarshaller by DOM Element");
           }

           return unmarshaller.unmarshall(samlElement);
       }catch (UnmarshallingException e) {
           //fail("Unmarshalling failed when parsing element file " + elementFile + ": " + e);
       }

       return null;
   }
   
   
   public static Element marshallerElement( XMLObject xmlObject) {
       Marshaller marshaller = marshallerFactory.getMarshaller(xmlObject);
       if(marshaller == null){
           //fail("Unable to locate marshaller for " + xmlObject.getElementQName() + " can not perform equality check assertion");
       }
       
       Element generatedDOM=null;
       try {
           generatedDOM = marshaller.marshall(xmlObject, parser.newDocument());
           if(log.isDebugEnabled()) {
               log.debug("Marshalled DOM was " + XMLHelper.nodeToString(generatedDOM));
           }
          // assertXMLEqual(failMessage, expectedDOM, generatedDOM.getOwnerDocument());
       } catch (Exception e) {
           log.error("Marshalling failed with the following error:", e);
          // fail("Marshalling failed with the following error: " + e);
       }
       return generatedDOM;
   }
   
   
   /** Parser manager used to parse XML. */
   protected static BasicParserPool parser;
   
   /** XMLObject builder factory. */
   protected static XMLObjectBuilderFactory builderFactory;

   /** XMLObject marshaller factory. */
   protected static MarshallerFactory marshallerFactory;

   /** XMLObject unmarshaller factory. */
   protected static UnmarshallerFactory unmarshallerFactory;
   
   /** Class logger. */
   private static Logger log = LoggerFactory.getLogger(MetadataGenerator.class);
   
   /** Constructor. */
   public MetadataGenerator(){
       
       parser = new BasicParserPool();
       parser.setNamespaceAware(true);
       try {
		DefaultBootstrap.bootstrap();
	} catch (ConfigurationException e) {
		e.printStackTrace();
	}
       builderFactory = org.opensaml.xml.Configuration.getBuilderFactory();
       marshallerFactory = org.opensaml.xml.Configuration.getMarshallerFactory();
       unmarshallerFactory = org.opensaml.xml.Configuration.getUnmarshallerFactory();
   }

}
