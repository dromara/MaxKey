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
 

/*
 * X509V3CertGen.java
 */

package org.dromara.maxkey.crypto.cert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.dromara.maxkey.crypto.KeyPairType;
import org.dromara.maxkey.crypto.KeyPairUtil;


/**
 * Provides utility methods relating to X509 Certificates Gen
 */
public final class X509V3CertGen {
	
	public static X509Certificate genV3Certificate(String issuerName,String subjectName,Date notBefore,Date notAfter,KeyPair keyPair) throws Exception {
		
		
		//issuer same as  subject is CA
		BigInteger  serial=BigInteger.valueOf(System.currentTimeMillis());
		 
		X500Name x500Name =new X500Name(issuerName);
		 
		X500Name subject =new X500Name(subjectName);
		 
		PublicKey publicKey =keyPair.getPublic();
		PrivateKey privateKey=keyPair.getPrivate();
		 
		SubjectPublicKeyInfo subjectPublicKeyInfo = null;  
		ASN1InputStream publicKeyInputStream =null;
		try {
		    publicKeyInputStream =new ASN1InputStream(publicKey.getEncoded());
    		Object aiStream=publicKeyInputStream.readObject();
    		subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(aiStream);  
		} catch (IOException e1) {  
			e1.printStackTrace();  
		} finally {
		    if(publicKeyInputStream !=null) {
		    	publicKeyInputStream.close();
		    }
		}
	        
	        
		X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(x500Name,
				 serial,
				 notBefore,
				 notAfter,
				 subject,
				 subjectPublicKeyInfo);
		 
		ContentSigner sigGen = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(privateKey); 
		//certBuilder.addExtension(X509Extensions.BasicConstraints,  true, new BasicConstraints(false));
		//certBuilder.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature| KeyUsage.keyEncipherment));
		//certBuilder.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
		//certBuilder.addExtension(X509Extensions.SubjectAlternativeName, false, new GeneralNames(new GeneralName(GeneralName.rfc822Name, "connsec@163.com")));


		X509CertificateHolder x509CertificateHolder = certBuilder.build(sigGen);  
	    CertificateFactory certificateFactory = CertificateFactory.class.newInstance();
	    InputStream inputStream = new ByteArrayInputStream(x509CertificateHolder.toASN1Structure().getEncoded());  
	    X509Certificate x509Certificate = (X509Certificate) certificateFactory.engineGenerateCertificate(inputStream);  
	    inputStream.close();
 
		return x509Certificate;
	 }


	 public static KeyPair genRSAKeyPair() throws Exception {
		 return KeyPairUtil.genKeyPair(KeyPairType.RSA, "BC");
	 }
}
