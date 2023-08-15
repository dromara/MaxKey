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
 

package org.maxkey.crypto.signature;

import java.util.Map;

import org.dromara.maxkey.crypto.KeyPairUtil;
import org.dromara.maxkey.crypto.signature.DsaSigner;
import org.junit.Test;

public final class DsaSignerTest {
	@Test
	public void test() throws Exception {

		DsaSigner dsaSigner = new DsaSigner();
		// genKeyPair
		Map<String, Object> keyMap = KeyPairUtil.genKeyPairMap(DsaSigner.KEY_ALGORITHM);
		
		String publicKey = KeyPairUtil.getPublicKey(keyMap);
		String privateKey = KeyPairUtil.getPrivateKey(keyMap);
		System.out.println("privateKey:" + privateKey);
		System.out.println("privateKey:" + privateKey.length());
		System.out.println("publicKey:" + publicKey);
		System.out.println("publicKey:" + publicKey.length());
		
		String signStr = "my data need to sign use DSA Digital signature";
		System.out.println("signStr:" + signStr);

		String sign = dsaSigner.signB64(signStr, privateKey);
		System.out.println("sign��" + sign);
		// verify
		boolean status = dsaSigner.verifyB64(signStr, publicKey, sign);
		System.out.println("status��" + status);

	}

}
