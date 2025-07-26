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
 * KeyStoreUtil.java
 */

package org.dromara.maxkey.crypto.keystore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openssl.PEMParser;
import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.cert.CryptoException;
import org.dromara.maxkey.crypto.cert.X509CertUtils;
import org.dromara.maxkey.crypto.cert.X509V3CertGen;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * Provides utility methods for loading/saving keystores. The Bouncy Castle
 * provider must be registered before using this class to create or load BKS or
 * UBER type keystores.
 */
public final class KeyStoreUtil {
    private static final Logger _logger = LoggerFactory.getLogger(KeyStoreUtil.class);
    
    public static final String X509 = "X.509";

    /** Map of available keystore types */
    private static final HashMap<KeyStoreType, Boolean> AVAILABLE_TYPES = new HashMap<KeyStoreType, Boolean>();

    /**
     * Private to prevent construction.
     */
    private KeyStoreUtil() {
        // Nothing to do
    }

    /**
     * Gets the preferred (by us) KeyStore instance for the given keystore type.
     * 
     * @param keyStoreType The keystore type
     * @return The keystore
     * @throws KeyStoreException No implementation found
     */
    private static KeyStore getKeyStoreImpl(KeyStoreType keyStoreType) throws KeyStoreException {
        KeyStore keyStore = null;
        if (keyStoreType == KeyStoreType.PKCS12) {
            // Prefer BC for PKCS #12 for now; the BC and SunJSSE 1.5+ implementations are
            // incompatible in how
            // they handle empty/missing passwords; BC works consistently with char[0] on
            // load and store (does
            // not accept nulls), SunJSSE throws division by zero with char[0] on load and
            // store, works with
            // null on load, does not work with null on store.
            // Checked with BC 1.{29,40}, SunJSSE 1.5.0_0{3,4,14}, 1.6.0 (OpenJDK)
            try {
                keyStore = KeyStore.getInstance(keyStoreType.name(), "BC");
            } catch (NoSuchProviderException ex) {
                ex.printStackTrace();
            }
        }
        if (keyStore == null) {
            try {
                keyStore = KeyStore.getInstance(keyStoreType.name());
            } catch (KeyStoreException e) {
                AVAILABLE_TYPES.put(keyStoreType, Boolean.FALSE);
                throw e;
            }
        }
        AVAILABLE_TYPES.put(keyStoreType, Boolean.TRUE);
        return keyStore;
    }

    /**
     * Create a new, empty keystore.
     * 
     * @param keyStoreType The keystore type to create
     * @return The keystore
     * @throws CryptoException Problem encountered creating the keystore
     * @throws IOException     An I/O error occurred
     */
    public static KeyStore createKeyStore(KeyStoreType keyStoreType) throws CryptoException, IOException {
        KeyStore keyStore = null;
        try {
            keyStore = getKeyStoreImpl(keyStoreType);
            keyStore.load(null, null);
        } catch (GeneralSecurityException ex) {
            throw new CryptoException("Could not create " + keyStoreType + " keystore.", ex);
        }
        return keyStore;
    }

    /**
     * Load keystore entries from PEM reader into a new PKCS #12 keystore. The
     * reader is not closed.
     * 
     * @param reader reader to read entries from
     * @return new PKCS #12 keystore containing read entries, possibly empty
     * @throws CryptoException Problem encountered creating the keystore
     * @throws IOException     An I/O error occurred
     */
    public static KeyStore loadEntries(PEMParser reader, String password) throws CryptoException, IOException {
        LinkedHashSet<KeyPair> keyPairs = new LinkedHashSet<KeyPair>();
        LinkedHashSet<Certificate> certs = new LinkedHashSet<Certificate>();
        KeyStore keyStore = createKeyStore(KeyStoreType.PKCS12);

        Object obj;
        while ((obj = reader.readObject()) != null) {
            if (obj instanceof KeyPair) {
                keyPairs.add((KeyPair) obj);
            } else if (obj instanceof Certificate) {
                certs.add((Certificate) obj);
            }
        }

        // Add key pairs
        for (KeyPair keyPair : keyPairs) {
            Certificate keyPairCert = null;
            for (Iterator<Certificate> it = certs.iterator(); it.hasNext();) {
                Certificate cert = it.next();
                if (cert.getPublicKey().equals(keyPair.getPublic())) {
                    keyPairCert = cert;
                    it.remove();
                    break;
                }
            }

            if (keyPairCert != null) {
                String alias = "keypair";
                if (keyPairCert instanceof X509Certificate) {
                    alias = X509CertUtils.getCertificateAlias((X509Certificate) keyPairCert);
                }

                KeyStore.PrivateKeyEntry entry = new KeyStore.PrivateKeyEntry(keyPair.getPrivate(),
                        new Certificate[] { keyPairCert });
                KeyStore.PasswordProtection prot = new KeyStore.PasswordProtection(password.toCharArray());

                try {
                    alias = findUnusedAlias(keyStore, alias);
                    keyStore.setEntry(alias, entry, prot);
                } catch (KeyStoreException e) {
                    throw new CryptoException(e);
                }
            }
        }

        // Add remaining certificates as trusted certificate entries
        for (Certificate cert : certs) {
            String alias = "certificate";
            if (cert instanceof X509Certificate) {
                alias = X509CertUtils.getCertificateAlias((X509Certificate) cert);
            }

            KeyStore.TrustedCertificateEntry entry = new KeyStore.TrustedCertificateEntry(cert);
            try {
                keyStore.setEntry(alias, entry, null);
            } catch (KeyStoreException e) {
                throw new CryptoException(e);
            }
        }

        return keyStore;
    }

    /**
     * Check if a keystore type is available.
     * 
     * @param keyStoreType the keystore type
     * @return true if the keystore type is available, false otherwise
     */
    public static boolean isAvailable(KeyStoreType keyStoreType) {
        Boolean available;
        if ((available = AVAILABLE_TYPES.get(keyStoreType)) != null) {
            return available;
        }
        try {
            // Populate AVAILABLE_TYPES
            getKeyStoreImpl(keyStoreType);
        } catch (KeyStoreException e) {
            // Ignore
            e.printStackTrace();
        }
        return AVAILABLE_TYPES.get(keyStoreType);
    }

    /**
     * Get available keystore types.
     * 
     * @return available keystore types
     */
    public static KeyStoreType[] getAvailableTypes() {
        //  populate only once
        KeyStoreType[] known = KeyStoreType.values();
        ArrayList<KeyStoreType> available = new ArrayList<KeyStoreType>();
        for (KeyStoreType type : known) {
            if (isAvailable(type)) {
                available.add(type);
            }
        }
        return available.toArray(new KeyStoreType[available.size()]);
    }

    /**
     * Load a Keystore from a file accessed by a password.
     * 
     * @param keyStoreType      The type of the keystore to open
     * @param fKeyStore         File to load keystore from
     * @param cKeyStorePassword Password of the keystore
     * @return The keystore
     * @throws CryptoException       Problem encountered loading the keystore
     * @throws FileNotFoundException If the keystore file does not exist, is a
     *                               directory rather than a regular file, or for
     *                               some other reason cannot be opened for reading
     */
    public static KeyStore loadKeyStore(
            File fKeyStore, char[] cKeyStorePassword, KeyStoreType keyStoreType)
            throws CryptoException, FileNotFoundException {
        KeyStore keyStore = null;
        try {
            keyStore = getKeyStoreImpl(keyStoreType);
        } catch (KeyStoreException ex) {
            throw new CryptoException("Could not create " + keyStoreType + " keystore.", ex);
        }

        FileInputStream fis = new FileInputStream(fKeyStore);
        try {
            keyStore.load(fis, cKeyStorePassword);
        } catch (GeneralSecurityException ex) {
            throw new CryptoException("Could not load keystore as type ''" + keyStoreType + "''.", ex);
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new CryptoException("Could not load keystore as type ''" + keyStoreType + "''.", ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                // Ignore
                ex.printStackTrace();
            }
        }

        return keyStore;
    }
    
    public static KeyStore loadKeyStore(
            Resource keystoreFile, char[] cKeyStorePassword, KeyStoreType keyStoreType)
            throws CryptoException, IOException {
        KeyStore keyStore = null;
        try {
            keyStore = getKeyStoreImpl(keyStoreType);
        } catch (KeyStoreException ex) {
            throw new CryptoException("Could not create " + keyStoreType + " keystore.", ex);
        }

        InputStream fis = keystoreFile.getInputStream();
        try {
            keyStore.load(fis, cKeyStorePassword);
        } catch (GeneralSecurityException ex) {
            throw new CryptoException("Could not load keystore as type ''" + keyStoreType + "''.", ex);
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new CryptoException("Could not load keystore as type ''" + keyStoreType + "''.", ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                // Ignore
                ex.printStackTrace();
            }
        }

        return keyStore;
    }

    public static KeyStore loadKeyStore(String keyStoreB64Encoded, char[] cKeyStorePassword,
            KeyStoreType keyStoreType) {
        KeyStore keyStore;
        try {
            //
            keyStore = KeyStore.getInstance(keyStoreType.name());

            byte[] keyStoreB64 = Base64.decodeBase64(keyStoreB64Encoded.getBytes());
            ByteArrayInputStream keyStoreBAIS = new ByteArrayInputStream(keyStoreB64);

            keyStore.load(keyStoreBAIS, cKeyStorePassword);

            return keyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String keyStore2Base64(KeyStore keyStore, String password) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            keyStore.store(stream, password.toCharArray());
            byte[] keyStoreByte = stream.toByteArray();
            String keyStoreBase64 = Base64Utils.encodeBase64(keyStoreByte);

            return keyStoreBase64;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyStore base642KeyStore(String keyStoreBase64, String password) {
        byte[] keyStoreByte = Base64Utils.decoderBase64(keyStoreBase64);
        return bytes2KeyStore(keyStoreByte, "JKS", password);
    }

    /**
     * Load a PKCS #11 keystore accessed by a password.
     * 
     * @param sPkcs11Provider   The name of the PKCS #11 provider
     * @param cKeyStorePassword Password of the keystore
     * @return The keystore
     * @throws CryptoException Problem encountered loading the keystore
     */
    public static KeyStore loadKeyStore(String sPkcs11Provider, char[] cKeyStorePassword) throws CryptoException {
        KeyStore keyStore = null;

        try {
            if (Security.getProvider(sPkcs11Provider) == null) {
                throw new CryptoException("The ''" + sPkcs11Provider + "'' provider is not present.");
            }
            keyStore = KeyStore.getInstance(KeyStoreType.PKCS11.name(), sPkcs11Provider);
        } catch (GeneralSecurityException ex) {
            throw new CryptoException("Could not create " + KeyStoreType.PKCS11 + " keystore.", ex);
        }

        try {
            keyStore.load(null, cKeyStorePassword);
        } catch (Exception ex) {
            throw new CryptoException("Could not load keystore as type ''" + KeyStoreType.PKCS11 + "''.", ex);
        }

        return keyStore;
    }

    /** */
    /**
     * <p>
     * get a Certificate from keyStore
     * </p>
     * 
     * @param keyStore
     * @param alias    Certificate alias name
     * @return
     * @throws Exception
     */
    public static Certificate getCertificate(KeyStore keyStore, String alias) {
        Certificate certificate = null;
        try {
            certificate = keyStore.getCertificate(alias);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return certificate;
    }

    /** */
    /**
     * <p>
     * 根据密钥库获得私�?
     * </p>
     * 
     * @param keyStorePath 密钥库存储路�?
     * @param alias        密钥库别�?
     * @param password     密钥库密�?
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String password) throws Exception {
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        return privateKey;
    }

    /** */
    /**
     * <p>
     * 根据证书获得公钥
     * </p>
     * 
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(Certificate certificate) throws Exception {
        PublicKey publicKey = certificate.getPublicKey();
        return publicKey;
    }

    /** */
    /**
     * <p>
     * 获得证书
     * </p>
     * 
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    public static Certificate loadCertificateFromFile(String certificatePath) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        return certificate;
    }

    /** */
    /**
     * <p>
     * 根据密钥库获得证�?
     * </p>
     * 
     * @param keyStorePath 密钥库存储路�?
     * @param alias        密钥库别�?
     * @param password     密钥库密�?
     * @return
     * @throws Exception
     */
    public static Certificate getCertificate(KeyStore keyStore, String alias, String password) throws Exception {
        Certificate certificate = keyStore.getCertificate(alias);
        return certificate;
    }

    /**
     * import X509Certificate trustCert to keyStore
     * 
     * @param keyStore
     * @param trustCert
     * @return KeyStore
     */
    public static KeyStore importTrustCertificate(KeyStore keyStore, X509Certificate trustCert) {
        String sMatchAlias;
        try {

            sMatchAlias = X509CertUtils.matchCertificate(keyStore, trustCert);
            System.out.println("sMatchAlias : " + sMatchAlias);
            if (sMatchAlias != null) {
                System.out.println("The certificate already exists in the Keystore under alias ''" + sMatchAlias
                        + "''.\nDo you still want to import it?");
            } else {
                KeyStore[] keyStores = { keyStore };
                if (X509CertUtils.establishTrust(keyStores, trustCert) == null) {
                    System.out.println(
                            "Could not establish a trust path for the certficate.\nThe certficate information will now be displayed after\nwhich you may confirm whether or not you trust the\ncertificate.");
                }

                String sCertAlias = X509CertUtils.getCertificateAlias(trustCert).toLowerCase();
                // Delete old entry first
                if (keyStore.containsAlias(sCertAlias)) {
                    keyStore.deleteEntry(sCertAlias);
                }
                // Import the trusted certificate
                keyStore.setCertificateEntry(sCertAlias, trustCert);
                return keyStore;
            }
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * import X509Certificate trustCert to keyStore
     * 
     * @param keyStore
     * @param trustCert
     * @return KeyStore
     */
    public static KeyStore importTrustCertificate(KeyStore keyStore, X509Certificate trustCert, String sCertAlias) {
        String sMatchAlias;
        try {

            sMatchAlias = X509CertUtils.matchCertificate(keyStore, trustCert);
            _logger.debug("sMatchAlias : " + sMatchAlias);
            if (sMatchAlias != null) {
                _logger.debug("\nThe certificate already exists in the Keystore under alias ''" + sMatchAlias
                        + "''.\nDo you still want to import it?");
            } else {
                KeyStore[] keyStores = { keyStore };
                if (X509CertUtils.establishTrust(keyStores, trustCert) == null) {
                    _logger.debug(
                            "\nCould not establish a trust path for the certficate.\nThe certficate information will now be displayed after\nwhich you may confirm whether or not you trust the certificate.");
                }

                // Delete old entry first
                if (keyStore.containsAlias(sCertAlias)) {
                    keyStore.deleteEntry(sCertAlias);
                }
                // Import the trusted certificate
                keyStore.setCertificateEntry(sCertAlias, trustCert);
                return keyStore;
            }
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Save a keystore to a file protected by a password.
     * 
     * @param keyStore          The keystore
     * @param fKeyStoreFile     The file to save the keystore to
     * @param cKeyStorePassword The password to protect the keystore with
     * @return the saved keystore ready for further use
     * @throws CryptoException       Problem encountered saving the keystore
     * @throws FileNotFoundException If the keystore file exists but is a directory
     *                               rather than a regular file, does not exist but
     *                               cannot be created, or cannot be opened for any
     *                               other reason
     * @throws IOException           An I/O error occurred
     */
    public static KeyStore saveKeyStore(KeyStore keyStore, File fKeyStoreFile, char[] cKeyStorePassword)
            throws CryptoException, IOException {
        FileOutputStream fos = new FileOutputStream(fKeyStoreFile);
        try {
            keyStore.store(fos, cKeyStorePassword);
        } catch (IOException ex) {
            throw new CryptoException("Could not save keystore.", ex);
        } catch (GeneralSecurityException ex) {
            throw new CryptoException("Could not save keystore.", ex);
        } finally {
            fos.close();
        }

        // As of GNU classpath 0.92, we need to reload GKR keystores after storing them,
        // otherwise
        // "masked envelope" IllegalStateExceptions occur when trying to access things
        // in the stored keystore
        // again.
        if (KeyStoreType.valueOf(keyStore.getType()) == KeyStoreType.GKR) {
            keyStore = loadKeyStore(fKeyStoreFile, cKeyStorePassword, KeyStoreType.GKR);
        }

        return keyStore;
    }

    /**
     * Find an unused alias in the keystore based on the given alias.
     * 
     * @param keyStore the keystore
     * @param alias    the alias
     * @return alias that is not in use in the keystore
     * @throws KeyStoreException
     */
    public static String findUnusedAlias(KeyStore keyStore, String alias) throws KeyStoreException {
        if (keyStore.containsAlias(alias)) {
            int i = 1;
            while (true) {
                String nextAlias = alias + " (" + i + ")";
                if (!keyStore.containsAlias(nextAlias)) {
                    alias = nextAlias;
                    break;
                }
            }
        }
        return alias;
    }

    public static KeyStore clone(KeyStore keyStore, String password) {
        try {
            KeyStore cloneKeyStore = KeyStore.getInstance(keyStore.getType());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            keyStore.store(byteArrayOutputStream, password.toCharArray());

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            cloneKeyStore.load(byteArrayInputStream, password.toCharArray());
            byteArrayInputStream.close();
            byteArrayOutputStream.close();
            return cloneKeyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] keyStore2Bytes(KeyStore keyStore, String password) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            keyStore.store(stream, password.toCharArray());
            return stream.toByteArray();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyStore bytes2KeyStore(byte[] keyStoreByte, String keyStoreType, String password) {
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(keyStoreByte);
            keyStore.load(byteArrayInputStream, password.toCharArray());
            return keyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyStore setKeyEntry(KeyStore keyStore, String keyEntry, KeyPair keyPair, Certificate certificate,
            String password) throws KeyStoreException {
        Certificate[] certChain = new Certificate[1];
        certChain[0] = certificate;
        keyStore.setKeyEntry(keyEntry, (Key) keyPair.getPrivate(), password.toCharArray(), certChain);
        return keyStore;
    }

    public static void main(String[] args) throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyPair keyPair = X509V3CertGen.genRSAKeyPair();
        String issuer = "CN=connsec.com,O=connsec,L=SH,ST=SH,C=CN";
        Date startDate = DateTime.now().toDate();
        Date endDate = DateTime.now().plusMonths(10).toDate();
        System.out.println("Private : " + keyPair.getPrivate().toString());

        System.out.println("Public : " + keyPair.getPublic().toString());
        X509Certificate cert = X509V3CertGen.genV3Certificate(issuer, issuer, startDate, endDate, keyPair);

        KeyStore keyStore = KeyStoreUtil.createKeyStore(KeyStoreType.JKS);
        keyStore = KeyStoreUtil.setKeyEntry(keyStore, "connsec.com", keyPair, cert, "password");
        KeyStoreUtil.saveKeyStore(keyStore, new File("c:\\mykeystore"), "password".toCharArray());

        keyStore = KeyStoreUtil.loadKeyStore(new File("c:\\mykeystore"), "password".toCharArray(), KeyStoreType.JKS);
        System.out.println(KeyStoreUtil.getPrivateKey(keyStore, "connsec.com", "password"));
        Certificate certificate = KeyStoreUtil.getCertificate(keyStore, "connsec.com");
        System.out.println(KeyStoreUtil.getPublicKey(certificate));

    }
    /**
     * @param args
     * 
     *             public static void main(String[] args) {
     * 
     *             // new //
     *             ByteArrayInputStream(Base64.decodeBase64(b64EncodedKeystore.getBytes()))
     * 
     *             try { // load a keyStore File file = new
     *             File("C:\\cert\\idp-keystore.jks"); String keystorePassword =
     *             "secret"; KeyStore ks = KeyStoreUtil.loadKeyStore(file,
     *             keystorePassword.toCharArray(), KeyStoreType.JKS);
     * 
     *             String b64 = KeyStoreUtil.keyStore2Base64(ks,keystorePassword);
     *             System.out.println(b64); Enumeration<String> temp = ks.aliases();
     *             int i = 0; while (temp.hasMoreElements()) {
     *             System.out.println("KeyStore alias name " + (i++) + " : " +
     *             temp.nextElement()); }
     * 
     *             System.out.println("=================================="); // load
     *             X509Certificate
     * 
     *             // one from pem file Reader reader =new FileReader(new
     *             File("C:\\cert\\onelogin.pem")); X509Certificate certPem =
     *             X509CertUtils.loadCertFromReader(reader);
     * 
     *             System.out.println("====loadCertificateFromPEMReader:"+certPem.getIssuerDN());
     * 
     *             // two from bin file File fileCert = new
     *             File("C:\\cert\\clientCert.cert"); InputStream isCert = new
     *             FileInputStream(fileCert); X509Certificate trustCert =
     *             X509CertUtils.loadCertFromInputStream(isCert);
     * 
     *             ks = KeyStoreUtil.importTrustCertificate(ks, trustCert);
     * 
     *             System.out
     *             .println("generatePEMEncoded==================================");
     *             System.out.println(X509CertUtils.generatePEMEncoded(trustCert));
     * 
     *             // output keystore to file KeyStoreUtil.saveKeyStore(ks, new
     *             File( "C:\\cert\\ClientRegistrarKeyStore12.jks"),
     *             keystorePassword.toCharArray());
     * 
     *             String pemString ="-----BEGIN CERTIFICATE-----"+'\n'; pemString
     *             +=
     *             "MIIEHjCCAwagAwIBAgIBATANBgkqhkiG9w0BAQUFADBnMQswCQYDVQQGEwJVUzET"+'\n';
     *             pemString +=
     *             "MBEGA1UECAwKQ2FsaWZvcm5pYTEVMBMGA1UEBwwMU2FudGEgTW9uaWNhMREwDwYD"+'\n';
     *             pemString +=
     *             "VQQKDAhPbmVMb2dpbjEZMBcGA1UEAwwQYXBwLm9uZWxvZ2luLmNvbTAeFw0xMjEx"+'\n';
     *             pemString +=
     *             "MDEwNzUzMTJaFw0xNzExMDEwNzUzMTJaMGcxCzAJBgNVBAYTAlVTMRMwEQYDVQQI"+'\n';
     *             pemString +=
     *             "DApDYWxpZm9ybmlhMRUwEwYDVQQHDAxTYW50YSBNb25pY2ExETAPBgNVBAoMCE9u"+'\n';
     *             pemString +=
     *             "ZUxvZ2luMRkwFwYDVQQDDBBhcHAub25lbG9naW4uY29tMIIBIjANBgkqhkiG9w0B"+'\n';
     *             pemString +=
     *             "AQEFAAOCAQ8AMIIBCgKCAQEAsVV3NROfDQBtSmsyZjdHKre1BMzmnjdyM5vViZV+"+'\n';
     *             pemString +=
     *             "OMjLU/aVejupyeNi6i6fqgBzU8a6vz3bXBnL4I8CAZYuRKxz57O2iTMTHLs6cAIT"+'\n';
     *             pemString +=
     *             "FTXSfSn/3gxgaOTNfvFXtwSD5yMaxAZckhHCTqVQgUgLLV+JApTSnW22NFadJ8aM"+'\n';
     *             pemString +=
     *             "hbajNCbpgIW0CFeiSlbojHzpeZewi8cTgjPDBbxwOeR8VUC6bMWsseqEyxUuHH9E"+'\n';
     *             pemString +=
     *             "TmO2pd9m5EKFpqZWlxGqa9qc6e89kpEhbIRpRjPWqSIjeDrsJllAmglsfD5MpnBq"+'\n';
     *             pemString +=
     *             "bHXx4BK9cziv6TWMyF0MZ+CnfBWl5JCJaWBFQCs5bG0m8QIDAQABo4HUMIHRMAwG"+'\n';
     *             pemString +=
     *             "A1UdEwEB/wQCMAAwHQYDVR0OBBYEFG6SGHTIayKeDRRGEkIdVBeRwjcFMIGRBgNV"+'\n';
     *             pemString +=
     *             "HSMEgYkwgYaAFG6SGHTIayKeDRRGEkIdVBeRwjcFoWukaTBnMQswCQYDVQQGEwJV"+'\n';
     *             pemString +=
     *             "UzETMBEGA1UECAwKQ2FsaWZvcm5pYTEVMBMGA1UEBwwMU2FudGEgTW9uaWNhMREw"+'\n';
     *             pemString +=
     *             "DwYDVQQKDAhPbmVMb2dpbjEZMBcGA1UEAwwQYXBwLm9uZWxvZ2luLmNvbYIBATAO"+'\n';
     *             pemString +=
     *             "BgNVHQ8BAf8EBAMCBPAwDQYJKoZIhvcNAQEFBQADggEBAGkBjaIhHusWRmY0O16+"+'\n';
     *             pemString +=
     *             "WoKC7l5Re2C+bz+tyuSLlDcuHniAsyhbYG8xvEJSOnxpeFbS/a4ko80wSsd+sUXJ"+'\n';
     *             pemString +=
     *             "FR3Z40W0JNT6ELn5Tf51b+cbm3erucMxKIDiMsQBcO/nHHBQs25kTXeKBjLnR/9u"+'\n';
     *             pemString +=
     *             "i3+naVemnRb1cvffenAPpm12yKqWWcKgN19mE2vdrw0y/GoirFFtO/STdkDPKuYu"+'\n';
     *             pemString +=
     *             "6wubRBeURNzqims0xe4/vPFE7iN50bjgKcuPn6LMaIDrLJVkwMC09MNsr0Dgmqgt"+'\n';
     *             pemString +=
     *             "hBdnEqXkhdE8F/VneHn5xLSfExC662OaU6jqDASBvN15mrLGaQ+Ou9qOsCFi7wg6"+'\n';
     *             pemString += "8QI="+'\n'; pemString += "-----END
     *             CERTIFICATE-----"+'\n';
     * 
     *             System.out.println(pemString); X509Certificate x509Certificate =
     *             X509CertUtils.loadCertFromPEM(pemString);
     *             System.out.println(x509Certificate.getIssuerDN()); } catch
     *             (IOException e) { e.printStackTrace(); } catch (KeyStoreException
     *             e) { e.printStackTrace(); } catch (Exception e) {
     *             e.printStackTrace(); }
     * 
     *             }
     */

}
