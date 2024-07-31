/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authz.exapi.endpoint.adapter.netease;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeteaseRSATool {

	static final  Logger _logger = LoggerFactory.getLogger(NeteaseRSATool.class);
			
	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private String description = "1024-bit RSA key";
	private String priKey = null;
	private String pubKey = null;
	

	public String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	public byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}

		return bytes;
	}

	public void genRSAKeyPair() {
		KeyPairGenerator rsaKeyGen = null;
		KeyPair rsaKeyPair = null;
		try {
			_logger.trace("Generating a pair of RSA key ... ");
			rsaKeyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			random.setSeed(System.currentTimeMillis());

			rsaKeyGen.initialize(1024, random);

			rsaKeyPair = rsaKeyGen.genKeyPair();
			PublicKey rsaPublic = rsaKeyPair.getPublic();
			PrivateKey rsaPrivate = rsaKeyPair.getPrivate();

			pubKey = bytesToHexStr(rsaPublic.getEncoded());
			priKey = bytesToHexStr(rsaPrivate.getEncoded());
			_logger.trace("pubKey: {}" , pubKey);
			_logger.trace("priKey: {}" , priKey);
			_logger.trace("1024-bit RSA key GENERATED.");
		} catch (Exception e) {
			_logger.error("Exception genRSAKeyPair:" + e);
		}
	}

	public String generateSHA1withRSASigature(String src, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
			
			Signature sigEng = Signature.getInstance("SHA1withRSA");
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes());
			byte[] signature = sigEng.sign();
			return bytesToHexStr(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String encryptWithPriKey(String src, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key privateKey = fac.generatePrivate(keySpec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			
			byte[] bytes = src.getBytes();
			byte[] encodedByteArray = new byte[] {};
			for (int i = 0; i < bytes.length; i += 102){
				byte[] subarray = ArrayUtils.subarray(bytes, i, i + 102);
				byte[] doFinal = cipher.doFinal(subarray);
				encodedByteArray = ArrayUtils.addAll(encodedByteArray, doFinal);
			}
			return bytesToHexStr(encodedByteArray);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean verifySHA1withRSASigature(String sign, String src,
			String pubKeyStr) {
		try {

			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pubbyte = hexStrToBytes(pubKeyStr.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPublicKey pubKey = (RSAPublicKey) fac.generatePublic(keySpec);

			sigEng.initVerify(pubKey);
			sigEng.update(src.getBytes());

			byte[] sign1 = hexStrToBytes(sign);
			return sigEng.verify(sign1);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public String encryptLongTextWithPriKey(String src, String priKey) {
		final int ENCRYPT_LENGTH = 117;
		if (src.length() <= ENCRYPT_LENGTH) {
			return encryptWithPriKey(src, priKey);
		}
		
		StringBuffer sb = new StringBuffer();
		int idx = 0;
		while (idx < src.length()) {
			int end = idx + ENCRYPT_LENGTH > src.length() ? src.length() : idx + ENCRYPT_LENGTH;
			String sub = src.substring(idx, end);
			String encSub = encryptWithPriKey(sub, priKey);
			sb.append(encSub);
			idx += ENCRYPT_LENGTH;
		}
		
		return sb.toString();
	}

	public String encryptWithPriKeyWithBase64(String src, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key privateKey = fac.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] signature = cipher.doFinal(src.getBytes());

			return Base64.getEncoder().encodeToString(signature).replaceAll("[^a-zA-Z0-9+/=]", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String encryptWithPubKey(String src, String pubKey) {
		try {
			byte[] pubbyte = hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);

			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key publicKey = fac.generatePublic(keySpec);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] signature = cipher.doFinal(src.getBytes());

			return bytesToHexStr(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String decryptWithPriKey(String enc, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) fac
					.generatePrivate(keySpec);

			// privateKey.getModulus() + privateKey.getPrivateExponent() +
			// privateKey.getAlgorithm();

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			byte[] forumcookie = hexStrToBytes(enc);

			byte[] plainText = cipher.doFinal(forumcookie);

			return bytesToHexStr(plainText);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String decryptWithPubKey(String enc, String pubKey) {
		try {
			byte[] pubbyte = hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key publicKey = fac.generatePublic(keySpec);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			byte[] forumcookie = hexStrToBytes(enc);

			byte[] plainText = cipher.doFinal(forumcookie);

			return new String(plainText);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			return null;
		}
	}

	public RSAPrivateKey getPriKey(String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPrivateKey key = (RSAPrivateKey) fac.generatePrivate(keySpec);
			return key;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public RSAPublicKey getPubKey(String pubKey) {
		try {
			byte[] pubbyte = hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPublicKey key = (RSAPublicKey) fac.generatePublic(keySpec);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPri() {
		return priKey;
	}

	public void setPri(String pri) {
		this.priKey = pri;
	}

	public String getPub() {
		return pubKey;
	}

	public void setPub(String pub) {
		this.pubKey = pub;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
