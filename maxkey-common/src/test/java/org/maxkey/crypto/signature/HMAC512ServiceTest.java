package org.maxkey.crypto.signature;

import org.maxkey.crypto.jwt.HMAC512Service;

import com.nimbusds.jose.JOSEException;

public class HMAC512ServiceTest {

	public static void main(String[] args) throws JOSEException {
		// TODO Auto-generated method stub
      String key ="7heM-14BtxjyKPuH3ITIm7q2-ps5MuBirWCsrrdbzzSAOuSPrbQYiaJ54AeA0uH2XdkYy3hHAkTFIsieGkyqxOJZ_dQzrCbaYISH9rhUZAKYx8tUY0wkE4ArOC6LqHDJarR6UIcMsARakK9U4dhoOPO1cj74XytemI-w6ACYfzRUn_Rn4e-CQMcnD1C56oNEukwalf06xVgXl41h6K8IBEzLVod58y_VfvFn-NGWpNG0fy_Qxng6dg8Dgva2DobvzMN2eejHGLGB-x809MvC4zbG7CKNVlcrzMYDt2Gt2sOVDrt2l9YqJNfgaLFjrOEVw5cuXemGkX1MvHj6TAsbLg";
      HMAC512Service HMAC512Service = new HMAC512Service(key);
      String jwt = HMAC512Service.sign("hkkkk");	
      boolean isverify = HMAC512Service.verify(jwt);
      System.out.println(isverify);
	}

}
