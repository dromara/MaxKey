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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


/**
 * @author shiming
 *
 */
public final class RSAUtils {
	
	public static final String 		KEY_ALGORTHM 		= 	"RSA";
	
	public static final String 		PUBLIC_KEY 			= 	"RSAPublicKey";

	public static final String 		PRIVATE_KEY 		= 	"RSAPrivateKey";
	
	public static final int 		KEY_SIZE			= 	1024;
	
	public static final int 		PEM_ARRAY_SIZE		= 	64;

	/**
	 * 生成KEY_SIZE长度的RSA密钥对,存放在keyMap中
	 * @return keyMap RSA密钥对
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPair keyPair = genRSAKeyPair();

		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);

		return keyMap;
	}
	
	/**
	 * gen RSA KeyPair 
	 * @return KeyPair
	 * @throws Exception
	 */
	public static KeyPair genRSAKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
		keyPairGenerator.initialize(KEY_SIZE);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * 获取公钥
	 * @param keyMap
	 * @return 公钥
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap)throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	
	/**
	 * 获取私钥
	 * @param keyMap
	 * @return 私钥
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap)throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	/**
	 * 公钥数据转换为Hex字符串
	 * @param keyMap
	 * @return 公钥
	 * @throws Exception
	 */
	public static String getPublicKey2Hex(Map<String, Object> keyMap)throws Exception {
		return HexUtils.bytes2HexString(getPublicKey(keyMap));
	}
	
	/**
	 * 私钥数据转换为Hex字符串
	 * @param keyMap
	 * @return 私钥
	 * @throws Exception
	 */
	public static String getPrivateKey2Hex(Map<String, Object> keyMap)throws Exception {
		return HexUtils.bytes2HexString(getPrivateKey(keyMap));
	}

	/**
	 * 私钥加密
	 * @param data  明文数据
	 * @param hexKey  私钥HEX编码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String hexKey)throws Exception {
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		return encryptByPrivateKey(data,keyBytes);
	}
	
	/**
	 * 私钥加密
	 * @param data  明文数据
	 * @param hexKey  私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] keyBytes)throws Exception {
	
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 * @param data  解密数据
	 * @param hexKey  私钥HEX编码
	 * @return 明文数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String hexKey)throws Exception {
		// 私钥HEX编码转换为byte
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		
		return decryptByPrivateKey(data,keyBytes);
	}
	
	/**
	 * 私钥解密
	 * @param data 解密数据
	 * @param keyBytes 私钥
	 * @return 明文数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] keyBytes)throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// 解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 公钥解密
	 * @param data 明文数据
	 * @param hexKey 公钥HEX
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String hexKey)throws Exception {
		// �Թ�Կ����
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		return encryptByPublicKey(data,keyBytes);
	}
	
	/**
	 * 公钥解密
	 * @param data 明文数据
	 * @param hexKey 公钥
	 * @return 密文
	 * @throws Exception
	 */
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
	 * 公钥解密
	 * @param data 密文数据
	 * @param hexKey 公钥HEX
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String hexKey)throws Exception {
		// hexKey 公钥HEX转换为byte
		byte[] keyBytes = HexUtils.hex2Bytes(hexKey);
		return decryptByPublicKey(data,keyBytes);
	}
	
	/**
	 * 公钥解密
	 * @param data 密文数据
	 * @param keyBytes 公钥
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] keyBytes)throws Exception {
		// 通过keyBytes构建公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		// 解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	/**
	 * 获取公钥的PEM格式
	 * @param encoded 公钥
	 * @return PEM格式公钥
	 */
	public static String getPublicKeyPEM(byte[] encoded) {
		StringBuffer base64String = 
				new StringBuffer("");
		base64String.append("-----BEGIN PUBLIC KEY-----").append("\n");
		base64String.append(getBase64PEM(encoded));
		base64String.append("-----END PUBLIC KEY-----").append("\n");
		return base64String.toString();
	}
	
	/**
	 * 获取私钥的PEM格式
	 * @param encoded 私钥
	 * @return PEM格式私钥
	 */
	public static String getPrivateKeyPEM(byte[] encoded) {
		StringBuffer base64String = 
				new StringBuffer("");
		base64String.append("-----BEGIN RSA PRIVATE KEY-----").append("\n");
		base64String.append(getBase64PEM(encoded));
		base64String.append("-----END RSA PRIVATE KEY-----").append("\n");
		return base64String.toString();
	}
	
	/**
	 * 获取密钥的PEM格式
	 * @param encoded 密钥
	 * @return PEM格式密钥
	 */
	public static String getBase64PEM(byte[] encoded) {
		String base64String = Base64.getEncoder().encodeToString(encoded);
		StringBuffer base64ArrayString = new StringBuffer("");
		int startPosition = 0;
		int endPosition = PEM_ARRAY_SIZE;
		while(endPosition < base64String.length()) {
			base64ArrayString.append(base64String.substring(startPosition, endPosition)).append("\n");
			startPosition = endPosition;
			endPosition = endPosition + PEM_ARRAY_SIZE;
		}
		if(startPosition < base64String.length()) {
			base64ArrayString.append(base64String.substring(startPosition)).append("\n");
		}
		
		return base64ArrayString.toString();
	}
	

}
