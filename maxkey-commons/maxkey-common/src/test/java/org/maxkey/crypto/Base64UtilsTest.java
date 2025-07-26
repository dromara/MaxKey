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

import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.DigestUtils;
import org.junit.Test;

public class Base64UtilsTest {

	/**
	 * @param args
	 */
	@Test
	public void test() {
		 String encode=Base64Utils.encoder("base64ToFile".getBytes());
		 System.out.println(encode);
		 String decode=Base64Utils.decode(encode);
		 System.out.println(decode);
		 
		 
		 
		 String urlEncode=Base64Utils.base64UrlEncode("{\"typ\":\"JWT\",\"alg\":\"HS256\"}".getBytes());
		 System.out.println(urlEncode);
		 String urlDecode=new String(Base64Utils.base64UrlDecode(urlEncode));
		 System.out.println(urlDecode);
		 
		 System.out.println(Base64Utils.decode("AAMkADU2OWY1MGQ3LWEyNWQtNDFmOC04MWFiLTI5YTE2NGM5YTZmNABGAAAAAABPKgpqnlfYQ7BVC/BfH2XIBwCS0xhUjzMYSLVky9bw7LddAAAAjov5AACS0xhUjzMYSLVky9bw7LddAAADzoyxAAA="));
		 
		
		 String b = "UsWdAIe4opTqcrX6~SrIMhBu5Gc9oZKEnnSDFRx9JwBINK8XTgnXUs2A3b7QmxDM9nRu8~mGsikVEoISLg.JTIHYRwv-Bp5ljIADLwUHv9iJAWo1delBOlW0Hd7nIVF0";
		 
		 System.out.println(DigestUtils.digestBase64Url(b,DigestUtils.Algorithm.SHA256));
	}

}
