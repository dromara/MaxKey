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
package org.dromara.maxkey.crypto.signature;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.KeyPairType;

/**
 * RSA Digital signature
 * default signature  algorithm is SHA1withRSA
 * default key size is 1024
 * RsaSigner support MD5withRSA and MD5withRSA
 * @author Crystal.Sea
 *
 */

public final class RsaSigner implements ISigner {

	public static final KeyPairType KEY_ALGORTHM = KeyPairType.RSA;

	public  final class RsaAlgorithm {
		public static final String MD5withRSA="MD5withRSA";
		public static final String SHA1withRSA = "SHA1withRSA";
	}

	public static final String SIGNATURE_ALGORITHM = RsaAlgorithm.SHA1withRSA;

	public byte[] sign(byte[] dataBytes, byte[] privateKeyBytes, String algorithm)throws Exception {
		// ����PKCS8EncodedKeySpec����
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		// ָ�������㷨
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM.name());
		// ȡ˽Կ�׶���
		PrivateKey signPrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// ��˽Կ����Ϣ�������ǩ��
		Signature signature = Signature.getInstance(algorithm);
		signature.initSign(signPrivateKey);
		signature.update(dataBytes);

		return signature.sign();
	}
	
	@Override
	public byte[] sign(byte[] dataBytes, byte[] privateKeyBytes) throws Exception {
		return sign(dataBytes,privateKeyBytes,SIGNATURE_ALGORITHM);
	}

	/**
	 * sign with BASE64 privateKey use SHA1withRSA Algorithm
	 */
	@Override
	public String signB64(String data, String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decoder(privateKey);
		byte[] dataBytes = data.getBytes();
		byte[] signature=sign(dataBytes,keyBytes);

		return Base64Utils.encoder(signature);
	}



	public boolean verify(byte[] dataBytes, byte[] publicKeyBytes , byte[] signBytes,String algorithm)throws Exception {
		// ����X509EncodedKeySpec����
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		// ָ�������㷨
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM.name());
		// ȡ��Կ�׶���
		PublicKey verifyPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		Signature signature = Signature.getInstance(algorithm);
		signature.initVerify(verifyPublicKey);

		signature.update(dataBytes);
		// verify
		return signature.verify(signBytes);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.connsec.crypto.signature.Signer#verify(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean verify(byte[] dataBytes, byte[] publicKeyBytes , byte[] signBytes)throws Exception {
		// verify
		return verify(dataBytes,publicKeyBytes,signBytes,SIGNATURE_ALGORITHM);
	}
	/*
	 * (non-Javadoc)
	 * @param publicKey is base64
	 * @param sign is base64
	 * @see com.connsec.crypto.signature.Signer#verify(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean verifyB64(String data, String publicKey, String sign)throws Exception {
		// ���ܹ�Կ
		byte[] keyBytes = Base64Utils.decoder(publicKey);
		byte[] dataBytes = data.getBytes();
		byte[] signBytes=Base64Utils.decoder(sign);
		// ��֤ǩ���Ƿ���
		return verify(dataBytes,keyBytes,signBytes);
	}



}
