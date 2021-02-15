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
 

package org.maxkey.crypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


public final class RSAUtils {
	
	public static final String KEY_ALGORTHM 	= 	"RSA";
	
	public static final String PUBLIC_KEY 		= 	"RSAPublicKey";

	public static final String PRIVATE_KEY 		= 	"RSAPrivateKey";

	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);

		return keyMap;
	}

	public static byte[] getPublicKey(Map<String, Object> keyMap)throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	
	public static byte[] getPrivateKey(Map<String, Object> keyMap)throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	public static String getPublicKey2Hex(Map<String, Object> keyMap)throws Exception {
		return HexUtils.bytes2HexString(getPublicKey(keyMap));
	}
	
	public static String getPrivateKey2Hex(Map<String, Object> keyMap)throws Exception {
		return HexUtils.bytes2HexString(getPrivateKey(keyMap));
	}

	/**
	 * ��˽Կ����
	 * @param data  �������
	 * @param hexKey  ��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String hexKey)throws Exception {
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		return encryptByPrivateKey(data,keyBytes);
	}
	
	public static byte[] encryptByPrivateKey(byte[] data, byte[] keyBytes)throws Exception {
	
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		// ����ݼ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * ��˽Կ����
	 * @param data  �������
	 * @param hexKey  ��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String hexKey)throws Exception {
		// ��˽Կ����
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		
		return decryptByPrivateKey(data,keyBytes);
	}
	
	public static byte[] decryptByPrivateKey(byte[] data, byte[] keyBytes)throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// ����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * �ù�Կ����
	 * @param data �������
	 * @param hexKey ��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String hexKey)throws Exception {
		// �Թ�Կ����
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		return encryptByPublicKey(data,keyBytes);
	}
	
	public static byte[] encryptByPublicKey(byte[] data, byte[] keyBytes)throws Exception {

		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		// ����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * �ù�Կ����
	 * @param data �������
	 * @param hexKey ��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String hexKey)throws Exception {
		// ��˽Կ����
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		return decryptByPublicKey(data,keyBytes);
	}
	
	public static byte[] decryptByPublicKey(byte[] data, byte[] keyBytes)throws Exception {
		// ��˽Կ����
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		// ����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

}
