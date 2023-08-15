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
 

/*
 * KeyPairUtil.java
 */

package org.dromara.maxkey.crypto;


import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.interfaces.DHKey;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHKeyParameters;
import org.bouncycastle.crypto.params.DSAKeyParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.dromara.maxkey.crypto.cert.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utility methods for the generation of keys.
 */
public final class KeyPairUtil
{
	/** Logger */
	private static final Logger _logger 		= 	LoggerFactory.getLogger(KeyPairUtil.class);

	/** Constant representing unknown key size */
	public static final int UNKNOWN_KEY_SIZE 	= 	-1;
	
	public static final int DEFAULT_KEY_SIZE	=	1024;
	
	public static final String PUBLIC_KEY 		= 	"publicKey";

	public static final String PRIVATE_KEY 		= 	"privateKey";

	/**
	 * Private to prevent construction.
	 */
	private KeyPairUtil()
	{
		// Nothing to do
	}
	
	
	public static KeyPair genKeyPair(KeyPairType keyPairType) throws CryptoException, NoSuchProviderException{
		return genKeyPair( keyPairType, DEFAULT_KEY_SIZE);
	}
	/**
	 * Generate a key pair.
	 * 
	 * @param keyPairType Key pair type to generate
	 * @param iKeySize Key size of key pair
	 * @return A key pair
	 * @throws CryptoException If there was a problem generating the key pair
	 * @throws NoSuchProviderException 
	 */
	public static KeyPair genKeyPair(KeyPairType keyPairType, int keySize)throws CryptoException, NoSuchProviderException
	{
		try{	
			return genKeyPair(keyPairType,null,keySize);
		}catch (InvalidParameterException ex){
			ex.printStackTrace();
			throw new CryptoException("Invalid parameter for a ''"+keyPairType+"'' key pair." , ex);
		}
	}
	
	public static KeyPair genKeyPair(KeyPairType keyPairType,String provider)throws CryptoException, NoSuchProviderException{
		return genKeyPair(keyPairType,provider,DEFAULT_KEY_SIZE);
	}
	
	/**
	 * Generate a key pair.
	 * 
	 * @param keyPairType Key pair type to generate
	 * @param iKeySize Key size of key pair
	 * @return A key pair
	 * @throws CryptoException If there was a problem generating the key pair
	 * @throws NoSuchProviderException 
	 */
	public static KeyPair genKeyPair(KeyPairType keyPairType,String provider, int keySize)throws CryptoException, NoSuchProviderException
	{
		try{	
			
			if(keyPairType==KeyPairType.ECDSA){
				throw new CryptoException("Could not support ''"+keyPairType+"'' key pair.");
			}

			KeyPairGenerator keyPairGenerator =null;
			if(provider==null){
				 keyPairGenerator = KeyPairGenerator.getInstance(keyPairType.name());
			}else{
				 keyPairGenerator = KeyPairGenerator.getInstance(keyPairType.name(),provider);
			}
			
			// Create a SecureRandom
			SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
			//SecureRandom rand = new SecureRandom();
			// Initialize key pair generator with key strength and a randomness
			keyPairGenerator.initialize(keySize, rand);

			// Generate and return the key pair
			return keyPairGenerator.generateKeyPair();
		}catch (NoSuchAlgorithmException ex){
			ex.printStackTrace();
			throw new CryptoException("Could not generate ''"+keyPairType+"'' key pair.", ex);
		}catch (InvalidParameterException ex){
			ex.printStackTrace();
			throw new CryptoException("Invalid parameter for a ''"+keyPairType+"'' key pair." , ex);
		}
	}
	
	
	public static Map<String, Object> genKeyPairMap(KeyPairType keyPairType) throws Exception {
		return  genKeyPairMap(keyPairType,DEFAULT_KEY_SIZE);
	}
	
	
	public static Map<String, Object> genKeyPairMap(KeyPairType keyPairType, int keySize) throws Exception {
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		
		if(keyPairType==KeyPairType.RSA){
			KeyPair keyPair=genKeyPair(keyPairType,keySize);
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyMap.put(PUBLIC_KEY , publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
			
		}else if(keyPairType==KeyPairType.DSA){
			KeyPair keyPair=genKeyPair(keyPairType,keySize);
			DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
			DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();
			keyMap.put(PUBLIC_KEY , publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
		}
		
		return keyMap;
	}
	
	

	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64Utils.encoder(key.getEncoded());
	}

	
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Utils.encoder(key.getEncoded());
	}

	/**
	 * Get the key size of a public key.
	 * 
	 * @param pubKey The public key
	 * @return The key size, {@link #UNKNOWN_KEY_SIZE} if not known
	 */
	public static int getKeyLength(PublicKey pubKey)
	{
		if (pubKey instanceof RSAKey)
		{
			return ((RSAKey) pubKey).getModulus().bitLength();
		}
		else if (pubKey instanceof DSAKey)
		{
			return ((DSAKey) pubKey).getParams().getP().bitLength();
		}
		else if (pubKey instanceof DHKey)
		{
			return ((DHKey) pubKey).getParams().getP().bitLength();
		}
		else if (pubKey instanceof ECKey)
		{
			// how to get key size from these?
			return UNKNOWN_KEY_SIZE;
		}

		_logger.warn("Don't know how to get key size from key " + pubKey);
		return UNKNOWN_KEY_SIZE;
	}

	/**
	 * Get the key size of a key represented by key parameters.
	 * 
	 * @param keyParams The key parameters
	 * @return The key size, {@link #UNKNOWN_KEY_SIZE} if not known
	 */
	public static int getKeyLength(AsymmetricKeyParameter keyParams)
	{
		if (keyParams instanceof RSAKeyParameters)
		{
			return ((RSAKeyParameters) keyParams).getModulus().bitLength();
		}
		else if (keyParams instanceof DSAKeyParameters)
		{
			return ((DSAKeyParameters) keyParams).getParameters().getP().bitLength();
		}
		else if (keyParams instanceof DHKeyParameters)
		{
			return ((DHKeyParameters) keyParams).getParameters().getP().bitLength();
		}
		else if (keyParams instanceof ECKeyParameters)
		{
			// how to get key length from these?
			return UNKNOWN_KEY_SIZE;
		}

		_logger.warn("Don't know how to get key size from parameters " + keyParams);
		return UNKNOWN_KEY_SIZE;
	}
}
