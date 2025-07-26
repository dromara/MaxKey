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
 * DSA Digital signature
 * default signature  algorithm is SHA1withDSA
 * default key size is 1024
 * DsaSigner support SHA1withDSA
 * @author Crystal.Sea
 *
 */
public final class DsaSigner implements ISigner {

	// ����ǩ����Կ�㷨
	public static final KeyPairType KEY_ALGORITHM = KeyPairType.DSA;

	/**
	 * ����ǩ�� ǩ��/��֤�㷨
	 * */
	public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";

	@Override
	public  byte[] sign(byte[] dataBytes, byte[] privateKeyByte) throws Exception {
		// ȡ��˽Կ
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM.name());
		// ���˽Կ
		PrivateKey signPrivateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// ʵ��Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// ��ʼ��Signature
		signature.initSign(signPrivateKey);
		// ����
		signature.update(dataBytes);

		return signature.sign();
	}
	
	@Override
	public  String signB64(String data, String privateKey) throws Exception {
		
		byte[] privateKeyByte = Base64Utils.decoder(privateKey);
		byte[] dataBytes = data.getBytes();	
		
		byte[] signatureBytes=sign(dataBytes,privateKeyByte);

		return Base64Utils.encoder(signatureBytes);
	}

	@Override
	public boolean verify(byte[] dataBytes, byte[] publicKeyBytes, byte[] signBytes)throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM.name());
		// ��ʼ����Կ
		// ��Կ����ת��
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
		// ����Կ
		PublicKey verifyPublicKey = keyFactory.generatePublic(x509KeySpec);
		// ʵ��Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// ��ʼ��Signature
		signature.initVerify(verifyPublicKey);
		// ����
		signature.update(dataBytes);
		// ��֤
		return signature.verify(signBytes);
	}
	
	@Override
	public boolean verifyB64(String data, String publicKey, String sign)throws Exception {

		byte[] privateKeyByte = Base64Utils.decoder(publicKey);
		byte[] dataBytes = data.getBytes();
		byte[] signBytes=Base64Utils.decoder(sign);
		
		// ��֤
		return verify(dataBytes,privateKeyByte,signBytes);
	}

}
