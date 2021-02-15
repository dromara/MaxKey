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
 * package org.maxkey.crypto.cert;
 * 
 * import java.io.FileInputStream; import java.io.FileOutputStream; import
 * java.io.IOException; import java.security.InvalidKeyException; import
 * java.security.KeyStore; import java.security.KeyStoreException; import
 * java.security.NoSuchAlgorithmException; import
 * java.security.NoSuchProviderException; import java.security.PrivateKey;
 * import java.security.SignatureException; import
 * java.security.UnrecoverableKeyException; import
 * java.security.cert.CertificateException; import java.util.Date;
 * 
 * import org.junit.Test;
 * 
 * import sun.security.x509.AlgorithmId; import
 * sun.security.x509.CertificateAlgorithmId; import
 * sun.security.x509.CertificateIssuerName; import
 * sun.security.x509.CertificateSerialNumber; import
 * sun.security.x509.CertificateSubjectName; import
 * sun.security.x509.CertificateValidity; import sun.security.x509.X500Name;
 * import sun.security.x509.X509CertImpl; import sun.security.x509.X509CertInfo;
 * 
 * public class X509CertUtilsTest {
 * 
 * //@Test public void test() throws KeyStoreException,
 * NoSuchAlgorithmException, CertificateException, IOException,
 * UnrecoverableKeyException, InvalidKeyException, NoSuchProviderException,
 * SignatureException { ////
 * 
 * String keystoreFile = "c:\\keyStoreFile.jks"; String caAlias = "caAlias";
 * String certToSignAlias = "cert"; String newAlias = "newAlias";
 * 
 * char[] password = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
 * char[] caPassword = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
 * char[] certPassword = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g','h' };
 * 
 * FileInputStream input = new FileInputStream(keystoreFile); KeyStore keyStore
 * = KeyStore.getInstance("JKS"); keyStore.load(input, password); input.close();
 * 
 * PrivateKey caPrivateKey = (PrivateKey) keyStore.getKey(caAlias,caPassword);
 * java.security.cert.Certificate caCert = keyStore.getCertificate(caAlias);
 * 
 * byte[] encoded = caCert.getEncoded(); X509CertImpl caCertImpl = new
 * X509CertImpl(encoded);
 * 
 * X509CertInfo caCertInfo = (X509CertInfo) caCertImpl.get(X509CertImpl.NAME +
 * "." + X509CertImpl.INFO);
 * 
 * X500Name issuer = (X500Name) caCertInfo.get(X509CertInfo.SUBJECT + "."+
 * CertificateIssuerName.DN_NAME);
 * 
 * java.security.cert.Certificate cert =
 * keyStore.getCertificate(certToSignAlias); PrivateKey privateKey =
 * (PrivateKey) keyStore.getKey(certToSignAlias,certPassword); encoded =
 * cert.getEncoded(); X509CertImpl certImpl = new X509CertImpl(encoded);
 * X509CertInfo certInfo = (X509CertInfo) certImpl.get(X509CertImpl.NAME+ "." +
 * X509CertImpl.INFO);
 * 
 * Date firstDate = new Date(); Date lastDate = new Date(firstDate.getTime() +
 * 365 * 24 * 60 * 60* 1000L); CertificateValidity interval = new
 * CertificateValidity(firstDate,lastDate);
 * 
 * certInfo.set(X509CertInfo.VALIDITY, interval);
 * 
 * certInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber((int)
 * (firstDate.getTime() / 1000)));
 * 
 * certInfo.set(X509CertInfo.ISSUER + "." +
 * CertificateSubjectName.DN_NAME,issuer);
 * 
 * AlgorithmId algorithm = new
 * AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
 * certInfo.set(CertificateAlgorithmId.NAME + "."+
 * CertificateAlgorithmId.ALGORITHM, algorithm); X509CertImpl newCert = new
 * X509CertImpl(certInfo);
 * 
 * newCert.sign(caPrivateKey, "MD5WithRSA");
 * 
 * keyStore.setKeyEntry(newAlias, privateKey, certPassword,new
 * java.security.cert.Certificate[] { newCert });
 * 
 * FileOutputStream output = new FileOutputStream(keystoreFile);
 * keyStore.store(output, password); output.close();
 * 
 * }
 * 
 * }
 */
