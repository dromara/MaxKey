/**
 * 
 */
package com.connsec.crypto.signature;

import java.util.Map;

import org.junit.Test;
import org.maxkey.crypto.KeyPairUtil;
import org.maxkey.crypto.signature.RsaSigner;


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
