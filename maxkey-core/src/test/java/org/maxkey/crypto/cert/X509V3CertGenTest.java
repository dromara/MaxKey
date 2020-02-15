package org.maxkey.crypto.cert;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.maxkey.crypto.cert.X509V3CertGen;

public class X509V3CertGenTest {

	@Test
	public void generateV3() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPair keyPair =X509V3CertGen.genRSAKeyPair();
		String issuer="CN=connsec.com,O=connsec,L=SH,ST=SH,C=CN";
		Date startDate=DateTime.now().toDate();
		Date endDate=DateTime.now().plusMonths(10).toDate();
		System.out.println("Private : "+ keyPair.getPrivate().toString());
	  
		System.out.println("Public : "+ keyPair.getPublic().toString());
		X509Certificate cert = X509V3CertGen.genV3Certificate(issuer,issuer,startDate,endDate,keyPair);
	   
		FileOutputStream out = new FileOutputStream("c:\\Cert345.cer");
		out.write(cert.getEncoded());
		out.close();
	
		cert.checkValidity(new Date());
		cert.verify(cert.getPublicKey());
		
	}
	
}
