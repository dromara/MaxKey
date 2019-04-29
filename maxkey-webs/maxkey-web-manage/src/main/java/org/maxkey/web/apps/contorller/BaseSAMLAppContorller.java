/**
 * 
 */
package org.maxkey.web.apps.contorller;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import org.maxkey.authz.saml20.metadata.MetadataDescriptorUtil;
import org.maxkey.crypto.cert.NameUtil;
import org.maxkey.crypto.cert.X509CertUtils;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.keystore.KeyStoreUtil;
import org.maxkey.domain.apps.SAMLBaseDetails;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Crystal.Sea
 *
 */
public class BaseSAMLAppContorller extends BaseAppContorller {

	final static Logger _logger = LoggerFactory.getLogger(BaseSAMLAppContorller.class);
	
	@Autowired
	@Qualifier("keyStoreLoader")
	private KeyStoreLoader idpKeyStoreLoader;

	protected SAMLBaseDetails transform(SAMLBaseDetails samlDetails) throws Exception{
	
		super.transform(samlDetails);
		
		X509Certificate trustCert = null;
		if (null!=samlDetails.getCertMetaFile()&&!samlDetails.getCertMetaFile().isEmpty()) {
			if(null==samlDetails.getFileType()||samlDetails.getFileType().equals("certificate")){//certificate file
				try {
					InputStream isCert = samlDetails.getCertMetaFile().getInputStream();
					trustCert = X509CertUtils.loadCertFromInputStream(isCert);
					isCert.close();
				} catch (IOException e) {
					_logger.error("read certificate file error .", e);
					throw new Exception("read certificate file error", e);
				}
			}else if(samlDetails.getFileType().equals("metadata")){//metadata file
				EntityDescriptor entityDescriptor;
				try {
					entityDescriptor = MetadataDescriptorUtil.getInstance().getEntityDescriptor(samlDetails.getCertMetaFile().getInputStream());
				} catch (IOException e) {
					_logger.error("metadata  file resolve error .", e);
					throw new Exception("metadata  file resolve error", e);
				}
				SPSSODescriptor sPSSODescriptor = entityDescriptor.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
				String b64Encoder = sPSSODescriptor.getKeyDescriptors().get(0).getKeyInfo().getX509Datas().get(0).getX509Certificates().get(0).getValue();

				trustCert = X509CertUtils.loadCertFromB64Encoded(b64Encoder);

				samlDetails.setSpAcsUrl(sPSSODescriptor.getAssertionConsumerServices().get(0).getLocation());
				samlDetails.setEntityId(entityDescriptor.getEntityID());

				_logger.info("SPSSODescriptor EntityID"+ entityDescriptor.getEntityID());
			}

			samlDetails.setCertSubject(trustCert.getSubjectDN().getName());
			samlDetails.setCertExpiration(trustCert.getNotAfter().toString());

			samlDetails.setCertIssuer(NameUtil.getCommonName(trustCert.getIssuerX500Principal()));
			
			KeyStore keyStore = KeyStoreUtil.clone(idpKeyStoreLoader.getKeyStore(),idpKeyStoreLoader.getKeystorePassword());

			KeyStore trustKeyStore = null;
			if (!samlDetails.getEntityId().equals("")) {
				trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,trustCert, samlDetails.getEntityId());
			} else {
				trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,trustCert);
			}

			byte[] keyStoreByte = KeyStoreUtil.keyStore2Bytes(trustKeyStore,idpKeyStoreLoader.getKeystorePassword());

			// store KeyStore content
			samlDetails.setKeyStore(keyStoreByte);
		} 
		
		return samlDetails;
	}
	

}
