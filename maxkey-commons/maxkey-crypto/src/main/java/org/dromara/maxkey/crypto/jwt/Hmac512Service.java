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
 

package org.dromara.maxkey.crypto.jwt;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.util.Base64URL;

public class Hmac512Service {

	public static final  String MXK_AUTH_JWK = "mxk_auth_jwk";
	
	JWSSigner signer;
	
	MACVerifier verifier;
	
	public Hmac512Service() {
		super();
	}
	
	public Hmac512Service(String secretString) throws JOSEException {
		Base64URL secret=new Base64URL(secretString);
		OctetSequenceKey octKey=  new OctetSequenceKey.Builder(secret)
				.keyID(MXK_AUTH_JWK)
				.keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.HS512)
				.build();
		signer = new MACSigner(octKey);
		verifier = new MACVerifier(octKey);
	}
	
	public String sign(Payload payload) {
		try {
			// Prepare JWS object with payload
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS512), payload);
			// Apply the HMAC
			jwsObject.sign(signer);
			String jwt = jwsObject.serialize();
			return jwt;
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String sign(String  payload) {
		return sign(new Payload(payload));
	}
	
	
	public boolean verify(String jwt) {
		try {
		JWSObject jwsObjected =JWSObject.parse(jwt);
		boolean isVerifier = verifier.verify(
								jwsObjected.getHeader(), 
								jwsObjected.getSigningInput(), 
								jwsObjected.getSignature());
		return isVerifier;
		}catch(JOSEException JOSEException) {
			
		}catch(ParseException ParseException) {
			
		}
		return false;
	}
}
