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
 

package org.maxkey.crypto.cert;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.dromara.maxkey.crypto.cert.X509V3CertGen;
import org.joda.time.DateTime;
import org.junit.Test;

public class X509V3CertGenTest {

	@Test
	public void generateV3() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPair keyPair =X509V3CertGen.genRSAKeyPair();
		String issuer="CN=maxkey.top,O=maxkey,L=SH,ST=SH,C=CN";
		Date startDate=DateTime.now().toDate();
		Date endDate=DateTime.now().plusMonths(10).toDate();
		System.out.println("Private : "+ keyPair.getPrivate().toString());
	  
		System.out.println("Public : "+ keyPair.getPublic().toString());
		X509Certificate cert = X509V3CertGen.genV3Certificate(issuer,issuer,startDate,endDate,keyPair);
		String certFileString = "D:\\MaxKey\\Workspaces\\maxkey\\Cert345.cer";
	    File certFile =new File(certFileString);
	    if(certFile.exists()) {
	        certFile.deleteOnExit();
	    }
	    
		FileOutputStream out = new FileOutputStream(certFileString);
		out.write(cert.getEncoded());
		out.close();
	
		cert.checkValidity(new Date());
		cert.verify(cert.getPublicKey());
		
	}
	
}
