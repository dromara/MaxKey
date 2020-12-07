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
 

/**
 * 
 */
package org.maxkey.authz.saml20.metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.maxkey.crypto.cert.StringUtil;
import org.opensaml.core.config.Configuration;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.metadata.resolver.impl.FilesystemMetadataResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.impl.EntityDescriptorImpl;
import org.opensaml.saml.saml2.metadata.impl.IDPSSODescriptorImpl;
import org.opensaml.saml.saml2.metadata.impl.SPSSODescriptorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;

/**
 * @author Crystal.Sea
 * 
 */
public class MetadataDescriptorUtil {
	private final static Logger logger = LoggerFactory.getLogger(MetadataDescriptorUtil.class);
	
	private static MetadataDescriptorUtil instance = null;
	
	/**
	 * 
	 */
	public MetadataDescriptorUtil() {
		try {
			InitializationService.initialize();
		} catch (InitializationException e) {
			e.printStackTrace();
		}
	}

	public static synchronized MetadataDescriptorUtil getInstance() {
		if (instance == null) {
			instance = new MetadataDescriptorUtil();
			// instance.bootstrap();
			logger.debug("getInstance()" + " new ConfigFile instance");
		}
		return instance;
	}

	// public void bootstrap() throws ConfigurationException {
	// // DefaultBootstrap.bootstrap();
	// }

	public EntityDescriptor getEntityDescriptor(File file)
			throws Exception {
		try {
			FilesystemMetadataResolver filesystemMetadataProvider = new FilesystemMetadataResolver(
					file);
			filesystemMetadataProvider.setRequireValidMetadata(true); // Enable
			// validation
			filesystemMetadataProvider.setParserPool(new BasicParserPool());
			filesystemMetadataProvider.initialize();
			EntityDescriptor entityDescriptor = (EntityDescriptorImpl) (filesystemMetadataProvider.getIndexes().toArray()[0]);
			return entityDescriptor;
		} catch (Exception e) {
			logger.error("鍏冩暟鎹В鏋愬嚭閿�", e);
			throw new Exception("鍏冩暟鎹枃浠惰В鏋愬嚭閿�", e);
		}

	}

	public EntityDescriptor getEntityDescriptor(InputStream inputStream)
			throws Exception {
		BasicParserPool basicParserPool = new BasicParserPool();
		basicParserPool.setNamespaceAware(true);
		try {
			Document inMetadataDoc = basicParserPool.parse(inputStream);
			Element metadataRoot = inMetadataDoc.getDocumentElement();

			Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(metadataRoot);;

			// unmarshaller.unmarshall(arg0)
			// Unmarshall using the document root element, an EntitiesDescriptor
			// in this case
			XMLObject xMLObject = unmarshaller.unmarshall(metadataRoot);

			EntityDescriptor entityDescriptor = (EntityDescriptorImpl) xMLObject;
			return entityDescriptor;
		} catch (XMLParserException e) {
			logger.error("鍏冩暟鎹В鏋愬嚭閿�", e);
			throw new Exception("鍏冩暟鎹枃浠惰В鏋愬嚭閿�", e);
		} catch (UnmarshallingException e) {
			logger.error("鍏冩暟鎹В鏋愬嚭閿�", e);
			throw new Exception("鍏冩暟鎹枃浠惰В鏋愬嚭閿�", e);
		}

	}

	public EntityDescriptor getEntityDescriptor(String strMetadata)
			throws Exception {
		InputStream inputStream = StringUtil.String2InputStream(strMetadata);
		return getEntityDescriptor(inputStream);
	}

	// from dom
	public EntityDescriptor getEntityDescriptor(Element elementMetadata)
			throws Exception {
		try {
			DOMMetadataProvider dOMMetadataProvider = new DOMMetadataProvider(elementMetadata);
			dOMMetadataProvider.setRequireValidMetadata(true); // Enable
																// validation
			dOMMetadataProvider.setParserPool(new BasicParserPool());
			dOMMetadataProvider.initialize();
			EntityDescriptor entityDescriptor = (EntityDescriptorImpl) dOMMetadataProvider.getMetadata();
			return entityDescriptor;
		} catch (Exception e) {
			logger.error("鍏冩暟鎹В鏋愬嚭閿�", e);
			throw new Exception("鍏冩暟鎹В鏋愬嚭閿�", e);
		}

	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 * @throws ConfigurationException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws Exception,FileNotFoundException {
		//
		File file = new File("d:\\SAMLSP-00D90000000hf9n.xml");
		InitializationService.initialize();
		
		EntityDescriptor entityDescriptor = MetadataDescriptorUtil.getInstance().getEntityDescriptor(file);

		// System.out.println("2 : "+entityDescriptor.getRoleDescriptors());

		// System.out.println("3 : "+idpEntityDescriptor.);
		// System.out.println("+"+ entityDescriptor.getOrganization());

		List<RoleDescriptor> listRoleDescriptor = entityDescriptor.getRoleDescriptors();
		for (RoleDescriptor roleDescriptor : listRoleDescriptor) {

			// SPSSODescriptor
			// sPSSODescriptor1=idpEntityDescriptor.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
			if (roleDescriptor instanceof IDPSSODescriptorImpl) {
				IDPSSODescriptor iDPSSODescriptor = (IDPSSODescriptorImpl) roleDescriptor;
				 System.out.println("3 : "+iDPSSODescriptor.getSingleSignOnServices().get(0).getLocation());
				// System.out.println("- : "+iDPSSODescriptor.getNameIDFormats().get(0).getFormat());

				// System.out.println("- : "+iDPSSODescriptor.getKeyDescriptors().get(0).getKeyInfo().getX509Datas().get(0));
			} else {
				SPSSODescriptor sPSSODescriptor = (SPSSODescriptorImpl) roleDescriptor;

				 System.out.println("- : "+sPSSODescriptor.getAssertionConsumerServices().get(0).getLocation());

				// System.out.println("- : "+sPSSODescriptor.getAssertionConsumerServices().get(0).getBinding());
			}

			// System.out.println("===============================================");
		}
		// //two
		InputStream in = new FileInputStream(file);

		EntityDescriptor entityDescriptor1 = MetadataDescriptorUtil.getInstance().getEntityDescriptor(in);

		SPSSODescriptor sPSSODescriptor = entityDescriptor1.getSPSSODescriptor(SAMLConstants.SAML20P_NS);

		 System.out.println("ok :"+sPSSODescriptor.getAssertionConsumerServices().get(0).getLocation());

		// System.out.println("ok :"+sPSSODescriptor.getAssertionConsumerServices().get(0).getBinding());

		// System.out.println("ok :"+sPSSODescriptor.getNameIDFormats().get(0).getFormat());

		// System.out.println("ok :"+sPSSODescriptor.getKeyDescriptors().get(0).getKeyInfo().getX509Datas().get(0));
	}

}
