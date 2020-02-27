/**
 * 
 */
package org.maxkey.crypto.keystore;

import java.security.KeyStore;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ResourceUtils;


/**
 * @author Crystal.Sea
 *
 */
public class KeyStoreLoader implements InitializingBean{
	private final static Logger _logger = LoggerFactory.getLogger(KeyStoreLoader.class);

	private KeyStore keyStore;
	
	private String entityName;
	private String keystoreFile;
	private String keystorePassword;
	
	private String keystoreType = "JKS";
	
	
	/**
	 * 
	 */
	public KeyStoreLoader() {
	}

	/**
	 * @return the keyStore
	 */
	public KeyStore getKeyStore() {
		return keyStore;
	}

	/**
	 * @param keystoreFile the keystoreFile to set
	 */
	public void setKeystoreFile(String keystoreFile) {
		this.keystoreFile = keystoreFile;
	}



	/**
	 * @param keystorePassword the keystorePassword to set
	 */
	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	/**
	 * ��ȡKeyStore����
	 * @return
	 */
	public String getKeystorePassword() {
		return keystorePassword;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		_logger.debug("Load KeyStore from file "+ResourceUtils.getFile(keystoreFile).getPath());
		keyStore =KeyStoreUtil.loadKeyStore(ResourceUtils.getFile(keystoreFile), keystorePassword.toCharArray(), KeyStoreType.JKS);
		_logger.debug("Load KeyStore success . ");
		
		Enumeration<String> temp = keyStore.aliases();
		int i=0;
		while(temp.hasMoreElements()){
			_logger.debug("KeyStore alias name "+(i++)+" : "+temp.nextElement());
		}
	}



	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @return the keystoreType
	 */
	public String getKeystoreType() {
		return keystoreType;
	}


}
