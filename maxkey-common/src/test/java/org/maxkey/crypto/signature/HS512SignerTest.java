package org.maxkey.crypto.signature;

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

public class HS512SignerTest {

	public static void main(String[] args) throws JOSEException, ParseException {
		// TODO Auto-generated method stub

		Base64URL secret=new Base64URL(
				"7heM-14BtxjyKPuH3ITIm7q2-ps5MuBirWCsrrdbzzSAOuSPrbQYiaJ54AeA0uH2XdkYy3hHAkTFIsieGkyqxOJZ_dQzrCbaYISH9rhUZAKYx8tUY0wkE4ArOC6LqHDJarR6UIcMsARakK9U4dhoOPO1cj74XytemI-w6ACYfzRUn_Rn4e-CQMcnD1C56oNEukwalf06xVgXl41h6K8IBEzLVod58y_VfvFn-NGWpNG0fy_Qxng6dg8Dgva2DobvzMN2eejHGLGB-x809MvC4zbG7CKNVlcrzMYDt2Gt2sOVDrt2l9YqJNfgaLFjrOEVw5cuXemGkX1MvHj6TAsbLg"
				);
		OctetSequenceKey octKey=  new OctetSequenceKey.Builder(secret)
				.keyID("mxk_auth_jwk_secret")
				.keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.HS512)
				.build();
		System.out.println(octKey.toJSONString());
		// Create HMAC signer
				JWSSigner signer = new MACSigner(octKey);

				// Prepare JWS object with "Hello, world!" payload
				JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello, world!"));

				// Apply the HMAC
				jwsObject.sign(signer);
				String s = jwsObject.serialize();
				System.out.println(s);
				
				JWSObject jwsObjected =JWSObject.parse(s);
				MACVerifier verifier = new MACVerifier(octKey);
				boolean isVerifier = verifier.verify(jwsObjected.getHeader(), jwsObjected.getSigningInput(), jwsObjected.getSignature());
				System.out.println(isVerifier);
				
	}

}
