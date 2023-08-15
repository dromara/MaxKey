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
 * X509CertUtil.java
 */

package org.dromara.maxkey.crypto.cert;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import javax.security.auth.x500.X500Principal;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openssl.PEMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provides utility methods relating to X509 Certificates, CRLs and CSRs.
 */
public final class X509CertUtils {
	/** Logger */
	// private static final Logger LOG =
	// Logger.getLogger(X509CertUtil.class.getCanonicalName());
	private static final Logger _logger = LoggerFactory.getLogger(X509CertUtils.class);
	/** PKCS #7 encoding name */
	public static final String PKCS7_ENCODING = "PKCS7";

	/** PkiPath encoding name */
	public static final String PKIPATH_ENCODING = "PkiPath";

	/** OpenSSL PEM encoding name */
	public static final String OPENSSL_PEM_ENCODING = "OpenSSL_PEM";

	/** Type name for X.509 certificates */
	public static final String X509_CERT_TYPE = "X.509";

	/**
	 * Private to prevent construction.
	 */
	private X509CertUtils() {
		// Nothing to do
	}



	public static X509Certificate loadCertFromPEM(String strPEM) {
		StringReader stringReader = new StringReader(strPEM);
		return loadCertFromReader(stringReader);
	}

	public static X509Certificate loadCertFromReader(Reader reader) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PEMParser pEMReader = new PEMParser(reader);
		try {
			X509Certificate cert = null;
			Object pemObject = pEMReader.readObject();
			if (pemObject instanceof X509Certificate) {
				cert = (X509Certificate) pemObject;
			}
			pEMReader.close();
			return cert;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static X509Certificate loadCertFromB64Encoded(String certB64Encoded)
			throws Exception {
		byte[] keyStoreB64 = Base64.decodeBase64(certB64Encoded.getBytes());
		ByteArrayInputStream keyStoreBAIS = new ByteArrayInputStream(
				keyStoreB64);
		return loadCertFromInputStream(keyStoreBAIS);
	}

	public static X509Certificate loadCertFromInputStream(
			InputStream inputStream) throws Exception {
		CertificateFactory certificateFactory;
		try {
			certificateFactory = CertificateFactory.getInstance("X.509");
			X509Certificate x509Certificate = (X509Certificate) certificateFactory
					.generateCertificate(inputStream);
			return x509Certificate;
		} catch (CertificateException e) {
			_logger.error("证书无法识别，证书类型必须是X.509");
			throw new Exception("证书无法识别，证书类型必须是X.509", e);
		}
	}

	

	/**
	 * Load a CRL from the specified URL.
	 * 
	 * @param url
	 *            The URL to load CRL from
	 * @return The CRL
	 * @throws CryptoException
	 *             Problem encountered while loading the CRL
	 * @throws FileNotFoundException
	 *             If the CRL file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading
	 * @throws IOException
	 *             An I/O error occurred
	 */
	public static X509CRL loadCRL(URL url) throws CryptoException, IOException {
		InputStream in = NetUtil.openGetStream(url);
		try {
			CertificateFactory cf = CertificateFactory
					.getInstance(X509_CERT_TYPE);
			X509CRL crl = (X509CRL) cf.generateCRL(in);
			return crl;
		} catch (GeneralSecurityException ex) {
			throw new CryptoException("Could not load CRL.", ex);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				_logger.warn("Could not close input stream from " + url, e);
				// LOG.log(Level.WARNING, "Could not close input stream from "+
				// url, e);
			}
		}
	}



	/**
	 * Convert the supplied array of certificate objects into X509Certificate
	 * objects.
	 * 
	 * @param certsIn
	 *            The Certificate objects
	 * @return The converted X509Certificate objects
	 * @throws CryptoException
	 *             A problem occurred during the conversion
	 */
	public static X509Certificate[] convertCertificates(Certificate[] certsIn)
			throws CryptoException {
		X509Certificate[] certsOut = new X509Certificate[certsIn.length];

		for (int iCnt = 0; iCnt < certsIn.length; iCnt++) {
			certsOut[iCnt] = convertCertificate(certsIn[iCnt]);
		}

		return certsOut;
	}

	/**
	 * Convert the supplied certificate object into an X509Certificate object.
	 * 
	 * @param certIn
	 *            The Certificate object
	 * @return The converted X509Certificate object
	 * @throws CryptoException
	 *             A problem occurred during the conversion
	 */
	public static X509Certificate convertCertificate(Certificate certIn)
			throws CryptoException {
		try {
			CertificateFactory cf = CertificateFactory
					.getInstance(X509_CERT_TYPE);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					certIn.getEncoded());
			return (X509Certificate) cf.generateCertificate(bais);
		} catch (CertificateException ex) {
			throw new CryptoException("Could not convert certificate.", ex);
		}
	}

	/**
	 * Attempt to order the supplied array of X.509 certificates in issued to to
	 * issued from order.
	 * 
	 * @param certs
	 *            The X.509 certificates in order
	 * @return The ordered X.509 certificates
	 */
	public static X509Certificate[] orderX509CertChain(X509Certificate[] certs) {
		int iOrdered = 0;
		X509Certificate[] tmpCerts = certs.clone();
		X509Certificate[] orderedCerts = new X509Certificate[certs.length];

		X509Certificate issuerCert = null;

		// Find the root issuer (i.e. certificate where issuer is the same as
		// subject)
		for (X509Certificate aCert : tmpCerts) {
			if (aCert.getIssuerDN().equals(aCert.getSubjectDN())) {
				issuerCert = aCert;
				orderedCerts[iOrdered] = issuerCert;
				iOrdered++;
			}
		}

		// Couldn't find a root issuer so just return the unordered array
		if (issuerCert == null) {
			return certs;
		}

		// Keep making passes through the array of certificates looking for the
		// next certificate in the chain
		// until the links run out
		while (true) {
			boolean bFoundNext = false;
			for (X509Certificate aCert : tmpCerts) {
				// Is this certificate the next in the chain?
				if (aCert.getIssuerDN().equals(issuerCert.getSubjectDN())
						&& aCert != issuerCert) {
					// Yes
					issuerCert = aCert;
					orderedCerts[iOrdered] = issuerCert;
					iOrdered++;
					bFoundNext = true;
					break;
				}
			}
			if (!bFoundNext) {
				break;
			}
		}

		// Resize array
		tmpCerts = new X509Certificate[iOrdered];
		System.arraycopy(orderedCerts, 0, tmpCerts, 0, iOrdered);

		// Reverse the order of the array
		orderedCerts = new X509Certificate[iOrdered];

		for (int iCnt = 0; iCnt < iOrdered; iCnt++) {
			orderedCerts[iCnt] = tmpCerts[tmpCerts.length - 1 - iCnt];
		}

		return orderedCerts;
	}

	/**
	 * DER encode a certificate.
	 * 
	 * @return The binary encoding
	 * @param cert
	 *            The certificate
	 * @throws CryptoException
	 *             If there was a problem encoding the certificate
	 */
	public static byte[] getCertEncodedDer(X509Certificate cert)
			throws CryptoException {
		try {
			return cert.getEncoded();
		} catch (CertificateException ex) {
			throw new CryptoException("Could not DER encode certificate.", ex);
		}
	}

	/**
	 * PKCS #7 encode a certificate.
	 * 
	 * @return The PKCS #7 encoded certificate
	 * @param cert
	 *            The certificate
	 * @throws CryptoException
	 *             If there was a problem encoding the certificate
	 */
	public static byte[] getCertEncodedPkcs7(X509Certificate cert)
			throws CryptoException {
		return getCertsEncodedPkcs7(new X509Certificate[] { cert });
	}

	/**
	 * PKCS #7 encode a number of certificates.
	 * 
	 * @return The PKCS #7 encoded certificates
	 * @param certs
	 *            The certificates
	 * @throws CryptoException
	 *             If there was a problem encoding the certificates
	 */
	public static byte[] getCertsEncodedPkcs7(X509Certificate[] certs)
			throws CryptoException {
		return getCertsEncoded(certs, PKCS7_ENCODING,
				"Could not PKCS #7 encode certificate(s).");
	}

	/**
	 * PkiPath encode a certificate.
	 * 
	 * @return The PkiPath encoded certificate
	 * @param cert
	 *            The certificate
	 * @throws CryptoException
	 *             If there was a problem encoding the certificate
	 */
	public static byte[] getCertEncodedPkiPath(X509Certificate cert)
			throws CryptoException {
		return getCertsEncodedPkiPath(new X509Certificate[] { cert });
	}

	/**
	 * PkiPath encode a number of certificates.
	 * 
	 * @return The PkiPath encoded certificates
	 * @param certs
	 *            The certificates
	 * @throws CryptoException
	 *             If there was a problem encoding the certificates
	 */
	public static byte[] getCertsEncodedPkiPath(X509Certificate[] certs)
			throws CryptoException {
		return getCertsEncoded(certs, PKIPATH_ENCODING,
				"Could not PkiPath encode certificate(s).");
	}

	/**
	 * Encode a number of certificates using the given encoding.
	 * 
	 * @return The encoded certificates
	 * @param certs
	 *            The certificates
	 * @param encoding
	 *            The encoding
	 * @param errkey
	 *            The error message key to use in the possibly occurred
	 *            exception
	 * @throws CryptoException
	 *             If there was a problem encoding the certificates
	 */
	private static byte[] getCertsEncoded(X509Certificate[] certs,
			String encoding, String errkey) throws CryptoException {
		try {
			CertificateFactory cf = CertificateFactory
					.getInstance(X509_CERT_TYPE);
			return cf.generateCertPath(Arrays.asList(certs)).getEncoded(
					encoding);
		} catch (CertificateException ex) {
			throw new CryptoException(errkey, ex);
		}
	}



	/**
	 * Verify that one X.509 certificate was signed using the private key that
	 * corresponds to the public key of a second certificate.
	 * 
	 * @return True if the first certificate was signed by private key
	 *         corresponding to the second signature
	 * @param signedCert
	 *            The signed certificate
	 * @param signingCert
	 *            The signing certificate
	 * @throws CryptoException
	 *             If there was a problem verifying the signature.
	 */
	public static boolean verifyCertificate(X509Certificate signedCert,
			X509Certificate signingCert) throws CryptoException {
		try {
			signedCert.verify(signingCert.getPublicKey());
		}
		// Verification failed
		catch (InvalidKeyException ex) {
			return false;
		}
		// Verification failed
		catch (SignatureException ex) {
			return false;
		}
		// Problem verifying
		catch (GeneralSecurityException ex) {
			throw new CryptoException("Could not verify certificate.", ex);
		}
		return true;
	}

	/**
	 * Check whether or not a trust path exists between the supplied X.509
	 * certificate and and the supplied keystores based on the trusted
	 * certificates contained therein, i.e. that a chain of trust exists between
	 * the supplied certificate and a self-signed trusted certificate in the
	 * keystores.
	 * 
	 * @return The trust chain, or null if trust could not be established
	 * @param cert
	 *            The certificate
	 * @param keyStores
	 *            The keystores
	 * @throws CryptoException
	 *             If there is a problem establishing trust
	 */
	public static X509Certificate[] establishTrust(KeyStore[] keyStores,
			X509Certificate cert) throws CryptoException {
		// Extract all certificates from the Keystores creating
		ArrayList<X509Certificate> ksCerts = new ArrayList<X509Certificate>();
		for (KeyStore ks : keyStores) {
			ksCerts.addAll(extractCertificates(ks));
		}

		// Try and establish trust against the set of all certificates
		return establishTrust(ksCerts, cert);
	}

	/**
	 * Check whether or not a trust path exists between the supplied X.509
	 * certificate and and the supplied comparison certificates based on the
	 * trusted certificates contained therein, i.e. that a chain of trust exists
	 * between the supplied certificate and a self-signed trusted certificate in
	 * the comparison set.
	 * 
	 * @return The trust chain, or null if trust could not be established
	 * @param cert
	 *            The certificate
	 * @param vCompCerts
	 *            The comparison set of certificates
	 * @throws CryptoException
	 *             If there is a problem establishing trust
	 */
	private static X509Certificate[] establishTrust(
			List<X509Certificate> vCompCerts, X509Certificate cert)
			throws CryptoException {
		// For each comparison certificate...
		for (X509Certificate compCert : vCompCerts) {
			// Check if the Comparison certificate's subject is the same as the
			// certificate's issuer
			if (cert.getIssuerDN().equals(compCert.getSubjectDN())) {
				// If so verify with the comparison certificate's corresponding
				// private key was used to sign
				// the certificate
				if (X509CertUtils.verifyCertificate(cert, compCert)) {
					// If the keystore certificate is self-signed then a chain
					// of trust exists
					if (compCert.getSubjectDN().equals(compCert.getIssuerDN())) {
						return new X509Certificate[] { cert, compCert };
					}
					// Otherwise try and establish a chain of trust for the
					// comparison certificate against the
					// other comparison certificates
					X509Certificate[] tmpChain = establishTrust(vCompCerts,
							compCert);
					if (tmpChain != null) {
						X509Certificate[] trustChain = new X509Certificate[tmpChain.length + 1];
						trustChain[0] = cert;
						System.arraycopy(tmpChain, 0, trustChain, 1,
								tmpChain.length);
						return trustChain;
					}
				}
			}
		}

		// No chain of trust
		return null;
	}

	/**
	 * Extract a copy of all trusted certificates contained within the supplied
	 * keystore.
	 * 
	 * @param keyStore
	 *            The keystore
	 * @return The extracted certificates
	 * @throws CryptoException
	 *             If a problem is encountered extracting the certificates
	 */
	private static Collection<X509Certificate> extractCertificates(
			KeyStore keyStore) throws CryptoException {
		try {
			ArrayList<X509Certificate> vCerts = new ArrayList<X509Certificate>();

			for (Enumeration<String> en = keyStore.aliases(); en
					.hasMoreElements();) {
				String sAlias = en.nextElement();
				if (keyStore.isCertificateEntry(sAlias)) {
					vCerts.add(X509CertUtils.convertCertificate(keyStore
							.getCertificate(sAlias)));
				}
			}

			return vCerts;
		} catch (KeyStoreException ex) {
			throw new CryptoException(
					"Could not extract trusted certificates from Keystore.", ex);
		}
	}

	/**
	 * Check whether or not a trusted certificate in the supplied keystore
	 * matches the the supplied X.509 certificate.
	 * 
	 * @return The alias of the matching certificate in the keystore or null if
	 *         there is no match
	 * @param cert
	 *            The certificate
	 * @param keyStore
	 *            The keystore
	 * @throws CryptoException
	 *             If there is a problem establishing trust
	 */
	public static String matchCertificate(KeyStore keyStore,
			X509Certificate cert) throws CryptoException {
		try {
			for (Enumeration<String> en = keyStore.aliases(); en
					.hasMoreElements();) {
				String sAlias = en.nextElement();
				if (keyStore.isCertificateEntry(sAlias)) {
					X509Certificate compCert = X509CertUtils
							.convertCertificate(keyStore.getCertificate(sAlias));

					if (cert.equals(compCert)) {
						return sAlias;
					}
				}
			}
			return null;
		} catch (KeyStoreException ex) {
			throw new CryptoException("Could not match certificate.", ex);
		}
	}

	/**
	 * For a given X.509 certificate get a representative alias for it in a
	 * keystore. For a self-signed certificate this will be the subject's common
	 * name (if any). For a non-self-signed certificate it will be the subject's
	 * common name followed by the issuer's common name in parenthesis.
	 * 
	 * @param cert
	 *            The certificate
	 * @return The alias or a blank string if none could be worked out
	 */
	public static String getCertificateAlias(X509Certificate cert) {
		X500Principal subject = cert.getSubjectX500Principal();
		X500Principal issuer = cert.getIssuerX500Principal();

		String sSubjectCN = getCommonName(subject);

		// Could not get a subject CN - return blank
		if (sSubjectCN == null) {
			return "";
		}

		String sIssuerCN = getCommonName(issuer);

		// Self-signed certificate or could not get an issuer CN
		if (subject.equals(issuer) || sIssuerCN == null) {
			// Alias is the subject CN
			return sSubjectCN;
		}
		_logger.debug("{0} ({1})", sSubjectCN, sIssuerCN);
		// else non-self-signed certificate
		// Alias is the subject CN followed by the issuer CN in parenthesis
		return MessageFormat.format("{0} ({1})", sSubjectCN, sIssuerCN);
	}
	
	
	public static String getCommonName(X500Principal name) {
        if (name == null) {
            return null;
        }
        String value = name.getName();
        
        if(value.indexOf(",") > -1) {
            value = value.split(",")[0];
        }
        
        if(value.indexOf("=")>-1) {
            value = value.split("=")[1];
        }
        
        return value;
    }
	
}
