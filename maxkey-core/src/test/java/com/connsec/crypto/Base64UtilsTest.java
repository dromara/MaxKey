package com.connsec.crypto;

import org.junit.Test;
import org.maxkey.crypto.Base64Utils;

public class Base64UtilsTest {

	/**
	 * @param args
	 */
	@Test
	public void test() {
		// TODO Auto-generated method stub
		 String encode=Base64Utils.encoder("base64ToFile".getBytes());
		 System.out.println(encode);
		 String decode=Base64Utils.decode(encode);
		 System.out.println(decode);
		 
		 
		 
		 String urlEncode=Base64Utils.base64UrlEncode("{\"typ\":\"JWT\",\"alg\":\"HS256\"}".getBytes());
		 System.out.println(urlEncode);
		 String urlDecode=new String(Base64Utils.base64UrlDecode(urlEncode));
		 System.out.println(urlDecode);
		 
		 System.out.println(Base64Utils.decode("AAMkADU2OWY1MGQ3LWEyNWQtNDFmOC04MWFiLTI5YTE2NGM5YTZmNABGAAAAAABPKgpqnlfYQ7BVC/BfH2XIBwCS0xhUjzMYSLVky9bw7LddAAAAjov5AACS0xhUjzMYSLVky9bw7LddAAADzoyxAAA="));
		 
		
		 
	}

}
