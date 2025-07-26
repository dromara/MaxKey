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
 

package org.dromara.maxkey.crypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;


/**
 * @author shiming
 *
 */
public final class RSAUtils {
	
	public static final String 		KEY_ALGORTHM 		= 	"RSA";
	
	public static final String 		LINE_SEPARATOR 		= 	"\n";
	
	public static final int 		KEY_SIZE			= 	1024;
	
	public static final int 		PEM_ARRAY_SIZE		= 	64;
	
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
	 * 通过keyBytes构建私钥
	 * @param keyBytes
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey privateKey(byte[] keyBytes)throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		return  keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	}
	
	/**
	 * 通过keyBytes构建公钥
	 * @param keyBytes
	 * @return
	 * @throws Exception
	 */
	public static PublicKey publicKey(byte[] keyBytes)throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		return keyFactory.generatePublic(x509EncodedKeySpec);
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
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = privateKey(keyBytes);

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
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = privateKey(keyBytes);
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
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = publicKey(keyBytes);

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
		
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = publicKey(keyBytes);

		// 解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	public static  byte[]  sign(byte[] src, RSAPrivateKey privateKey, String algorithm) {
		if(StringUtils.isBlank(algorithm)) {
			algorithm = "SHA1withRSA";
		}
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(privateKey);
			signature.update(src);
			return signature.sign();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean verify(byte[] sign, byte[] src, RSAPublicKey publicKey, String algorithm) {
		try {
			if(StringUtils.isBlank(algorithm)) {
				algorithm = "SHA1withRSA";
			}
			
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(publicKey);
			signature.update(src);
			return signature.verify(sign);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取公钥的PEM格式
	 * @param encoded 公钥
	 * @return PEM格式公钥
	 */
	public static String getPublicKeyPEM(byte[] encoded) {
		StringBuffer base64String = 
				new StringBuffer("");
		base64String.append("-----BEGIN PUBLIC KEY-----").append(LINE_SEPARATOR);
		base64String.append(getBase64PEM(encoded)).append(LINE_SEPARATOR);
		base64String.append("-----END PUBLIC KEY-----").append(LINE_SEPARATOR);
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
		base64String.append("-----BEGIN RSA PRIVATE KEY-----").append(LINE_SEPARATOR);
		base64String.append(getBase64PEM(encoded)).append(LINE_SEPARATOR);
		base64String.append("-----END RSA PRIVATE KEY-----").append(LINE_SEPARATOR);
		return base64String.toString();
	}
	
	/**
	 * 获取Certificate的PEM格式
	 * @param encoded 公钥
	 * @return PEM格式公钥
	 */
	public static String getCertificatePEM(byte[] encoded) {
		StringBuffer base64String = 
				new StringBuffer("");
		base64String.append("-----BEGIN CERTIFICATE-----").append(LINE_SEPARATOR);
		base64String.append(getBase64PEM(encoded)).append(LINE_SEPARATOR);
		base64String.append("-----END CERTIFICATE-----").append(LINE_SEPARATOR);
		return base64String.toString();
	}
	
	/**
	 * 获取密钥的PEM格式
	 * @param encoded 密钥
	 * @return PEM格式密钥
	 */
	public static String getBase64PEM(byte[] encoded) {
		String base64String = 
				Base64.getMimeEncoder(PEM_ARRAY_SIZE,LINE_SEPARATOR.getBytes()).encodeToString(encoded);
		//StringBuffer base64ArrayString = new StringBuffer("");
		//int startPosition = 0;
		//int endPosition = PEM_ARRAY_SIZE;
		//while(endPosition < base64String.length()) {
		//	base64ArrayString.append(base64String.substring(startPosition, endPosition)).append("\n");
		//	startPosition = endPosition;
		//	endPosition = endPosition + PEM_ARRAY_SIZE;
		//}
		//if(startPosition < base64String.length()) {
		//	base64ArrayString.append(base64String.substring(startPosition)).append("\n");
		//}
		
		//return base64ArrayString.toString();
		return base64String;
	}
}
