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
 

package org.maxkey.crypto.signature;

import java.security.SecureRandom;

import org.dromara.maxkey.crypto.Base64Utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

public class RSAKeyTest {

	public static void main(String[] args) throws JOSEException {
		// RSA signatures require a public and private RSA key pair, the public key 
		// must be made known to the JWS recipient in order to verify the signatures
		RSAKey rsaJWK = new RSAKeyGenerator(2048)
		    .keyID("123")
		    .keyUse(KeyUse.SIGNATURE)
		    .algorithm(JWSAlgorithm.RS256)
		    .generate();
		RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();
		System.out.println(rsaPublicJWK.toJSONString());
		
		System.out.println(rsaJWK.toJSONString());
		
		byte[] sharedKey = new byte[32];
		new SecureRandom().nextBytes(sharedKey);
		System.out.println(Base64Utils.encoder(sharedKey));
		
		
		OctetSequenceKey octKey=  new OctetSequenceKeyGenerator(2048)
				.keyID("123")
				.keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.HS256)
				.generate();
		System.out.println(octKey.toJSONString());
		
		// Create HMAC signer
		JWSSigner signer = new MACSigner(octKey);

		// Prepare JWS object with "Hello, world!" payload
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello, world!"));

		// Apply the HMAC
		jwsObject.sign(signer);
		String s = jwsObject.serialize();
		System.out.println(s);
		
		System.out.print("A128KW".substring(1, 4));
	}

}
