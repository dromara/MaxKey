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

import java.security.KeyPair;

import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.HexUtils;
import org.dromara.maxkey.crypto.RSAUtils;
import org.junit.Test;


public class RSAUtilsTest {

	@Test
	public void test() throws Exception {

		// RSA KeyPair
		KeyPair keyPair   = RSAUtils.genRSAKeyPair();
		String privateKey = HexUtils.hex2String(keyPair.getPrivate().getEncoded());
		String publicKey = HexUtils.hex2String(keyPair.getPublic().getEncoded());
		System.out.println("privateKey:" + privateKey);
		System.out.println("publicKey:" + publicKey);
		String signString = "my name is shiming";
		System.out.println("privateKey:");
		System.out.println( Base64Utils.base64UrlEncode(keyPair.getPublic().getEncoded()));
		System.out.println("PublicKeyPEM:");
		System.out.println(RSAUtils.getPublicKeyPEM(keyPair.getPublic().getEncoded()));
		
		byte[] encodedData = RSAUtils.encryptByPrivateKey(signString.getBytes(), privateKey);
		System.out.println("encodedData \r\n" + new String(encodedData));
		System.out.println("encodedData HexString \r\n" + HexUtils.bytes2HexString(encodedData));
		byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
		String target = new String(decodedData);
		System.out.println("target:" + target);
		
	}

}
