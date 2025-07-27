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
package org.maxkey.crypto.signature;

import java.util.Map;

import org.dromara.maxkey.crypto.KeyPairUtil;
import org.dromara.maxkey.crypto.signature.RsaSigner;
import org.junit.Test;


public final class RsaSignerTest  {

	@Test
	public void test() throws Exception {

		RsaSigner rsaSigner = new RsaSigner();
		Map<String, Object> key = KeyPairUtil.genKeyPairMap(RsaSigner.KEY_ALGORTHM);
		String privateKey = KeyPairUtil.getPrivateKey(key);
		String publicKey = KeyPairUtil.getPublicKey(key);
		System.out.println("privateKey:" + privateKey);
		System.out.println("privateKey:" + privateKey.length());
		System.out.println("publicKey:" + publicKey);
		System.out.println("publicKey:" + publicKey.length());
		String sdata = "MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUWEKjQXEsmz9cfPNxwhAlXl90U8c=";
		String signedStringuuid = rsaSigner.signB64(sdata, privateKey);
		System.out.println("signedStringuuid:" + signedStringuuid);
		System.out.println("signedStringuuid:" + signedStringuuid.length());
		boolean isSigneduuid = rsaSigner.verifyB64(sdata, publicKey,
				signedStringuuid);
		System.out.println("isSigneduuid:" + isSigneduuid);

	}

}
