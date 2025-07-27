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
 

package org.dromara.maxkey.crypto.cert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import javax.crypto.Cipher;

import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.keystore.KeyStoreUtil;


/** *//**
 * <p>
 * 证书加密解密工具�?
 * </p>
 * @version 1.0
 */
public class CertCrypto {

    /** *//**
     * Java密钥�?Java 密钥库，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "JKS";

    public static final String X509 = "X.509";
    
    /** *//**
     * 文件读取缓冲区大�?
     */
    private static final int CACHE_SIZE = 2048;
    
    /** *//**
     * �?��文件加密�?
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /** *//**
     * �?��文件解密�?
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

 

    /** *//**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param data 源数�?
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, KeyStore keyStore, String alias, String password) 
            throws Exception {
        // 取得私钥
        PrivateKey privateKey = KeyStoreUtil.getPrivateKey(keyStore, alias, password);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加�?
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    
    /** *//**
     * <p>
     * 文件私钥加密
     * </p>
     * <p>
     * 过大的文件可能会导致内存溢出
     * </>
     * 
     * @param filePath 文件路径
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     * @throws Exception
     */
    public static byte[] encryptFileByPrivateKey(String filePath, KeyStore keyStore, String alias, String password) 
            throws Exception {
        byte[] data = fileToByte(filePath);
        return encryptByPrivateKey(data, keyStore, alias, password);
    }
    
    /** *//**
     * <p>
     * 文件加密
     * </p>
     * 
     * @param srcFilePath 源文�?
     * @param destFilePath 加密后文�?
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @throws Exception
     */
    public static void encryptFileByPrivateKey(String srcFilePath, String destFilePath,KeyStore keyStore, String alias, String password)
            throws Exception {
        // 取得私钥
        PrivateKey privateKey = KeyStoreUtil.getPrivateKey(keyStore, alias, password);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        File srcFile = new File(srcFilePath);
        FileInputStream in = new FileInputStream(srcFile);
        File destFile = new File(destFilePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);   
        byte[] data = new byte[MAX_ENCRYPT_BLOCK];
        byte[] encryptedData;    // 加密�?
        while (in.read(data) != -1) {
            encryptedData = cipher.doFinal(data);
            out.write(encryptedData, 0, encryptedData.length);
            out.flush();
        }
        out.close();
        in.close();
    }

    /** *//**
     * <p>
     * 文件加密成BASE64编码的字符串
     * </p>
     * 
     * @param filePath 文件路径
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     * @throws Exception
     */
    public static String encryptFileToBase64ByPrivateKey(String filePath,KeyStore keyStore, String alias, String password) 
            throws Exception {
        byte[] encryptedData = encryptFileByPrivateKey(filePath, keyStore, alias, password);
        return Base64Utils.encoder(encryptedData);
    }
    
    /** *//**
     * <p>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数�?
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, KeyStore keyStore, String alias, String password) 
            throws Exception {
        // 取得私钥
        PrivateKey privateKey = KeyStoreUtil.getPrivateKey(keyStore, alias, password);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 解密byte数组�?��长度限制: 128
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解�?
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** *//**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param data 源数�?
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, Certificate certificate)
            throws Exception {
        // 取得公钥
        PublicKey publicKey = KeyStoreUtil.getPublicKey(certificate);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加�?
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** *//**
     * <p>
     * 公钥解密
     * </p>
     * 
     * @param encryptedData 已加密数�?
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, Certificate certificate)
            throws Exception {
        PublicKey publicKey = KeyStoreUtil.getPublicKey(certificate);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解�?
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    
    /** *//**
     * <p>
     * 文件解密
     * </p>
     * 
     * @param srcFilePath 源文�?
     * @param destFilePath 目标文件
     * @param certificatePath 证书存储路径
     * @throws Exception
     */
    public static void decryptFileByPublicKey(String srcFilePath, String destFilePath,Certificate certificate)
            throws Exception {
        PublicKey publicKey = KeyStoreUtil.getPublicKey(certificate);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        File srcFile = new File(srcFilePath);
        FileInputStream in = new FileInputStream(srcFile);
        File destFile = new File(destFilePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);   
        byte[] data = new byte[MAX_DECRYPT_BLOCK];
        byte[] decryptedData;    // 解密�?
        while (in.read(data) != -1) {
            decryptedData = cipher.doFinal(data);
            out.write(decryptedData, 0, decryptedData.length); 
            out.flush();
        }
        out.close();
        in.close();
    }
  
    
    /** *//**
     * <p>
     * 文件转换为byte数组
     * </p>
     * 
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
         }
        return data;
    }
    
    /** *//**
     * <p>
     * 二进制数据写文件
     * </p>
     * 
     * @param bytes 二进制数�?
     * @param filePath 文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);   
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {   
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }
    
    
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        
        is.close();
        
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
       
        return bytes;
    }
    public static void main(String[] args) throws Exception {
//		String result = encryptFileToBase64ByPrivateKey("C:\\sp.cer", "C:\\sp-keystore.jks", "sp", "secret");
//		System.out.println(result);
    	//Certificate certificate = getCertificate("C:\\sp-keystore.jks", "sp", "secret");
    	// System.out.println("certificate 1:"+certificate);
    	
    	//File file = new File("C:\\para-keystore.jks");
    	//byte[] content =  getBytesFromFile(file);
    	// System.out.println("base64keystore:\n"+Base64.encodeBase64String(content));
    	
    	KeyStore ks = KeyStore.getInstance("JKS");
		String b64EncodedKeystore = "/u3+7QAAAAIAAAACAAAAAQACc3AAAAErlmk7hAAAArowggK2MA4GCisGAQQBKgIRAQEFAASCAqIw4jynaKwOlOP74OM2+0lnYX8MOhvk0r71kvcbv9cusyIua3FJaIg1NBmgDrvF2JcUNcAhnyuBrafzW+3INGs8NNnmsPNgSPQ5cIMKRZ+44xxEmafy+FYPgw5RlmL+gXB/buiK1FzVuukjCR7/GCbQB2T0I1bZn5Ok/U0AlfGnAGBcXOR3efjdtKKImPBtMHQ9kBoCIlgROVKPEcSxPi7fm2SlN+tVjv1y9toYw2wRP+zsW5CAfY2mnRkQg58BtE2LhYhedSUUuaJAWTlWaWqA9rbTZmXlYqqOB/t86aYNuadT8nAu468MIucL3F2RQdMt9xDD3qRidT+h1I7ShnaF7pkUvynE5QKW3EIPhTaiRvMSUf6a984G4WerpdgmbzYEHUC9Kfw6WHcgKcGB5oAg4R2nlyEGLd2SBFv2vMRnXucwuofECK15YqCbu6wZGhQDKiGZo8MNcu8mPCq7vujOl4Azkjx1YyU1VHTQTyHP9BoOqS4lA8SjdEEGOm6p3R+CrwRratgET0UlopMInxuIvnuxXp5Vq4fHuY0GI65MRVQt9mSp5zeYvAYPYLSPmhcE2KchIR1Cb7NPbPID8D/EkNuCxG9FNPBhhtgMRdbOejJ3NPpt43DDt3nTpn/5pgBLsBxQ7hlPOb3Y4hsKCEI4UyVl+fTQieNIyEaAnt2Q/NNVGDJlc4aIAdDEfbOVbVdyYDViHskwTiKDdin4mAqTGj+qr0MVpoye6daZduftG85yx/1AnEjvPqUhKvi1kMKBi6q3z6XUMIq09RaQepx6xMboaiiqCU+Bi9kvdh3XNbnQ64DNPOhzytLAiBApb2IRaY1fkKSYOz+hFj0HbxF1cn5ITaQT1KeestiS+PuBO8JUR1yxTU0JQ4Tea0quTB+ragAAAAEABVguNTA5AAACSjCCAkYwggGvAgRMscTWMA0GCSqGSIb3DQEBBQUAMGoxJDAiBgkqhkiG9w0BCQEWFWphbWVzLnMuY294QGdtYWlsLmNvbTELMAkGA1UEBhMCVVMxGTAXBgNVBAoMEFNlcnZpY2UgUHJvdmlkZXIxCzAJBgNVBAsMAnNwMQ0wCwYDVQQDDARqY294MB4XDTEwMTAxMDEzNTExOFoXDTIwMTAwNzEzNTExOFowajEkMCIGCSqGSIb3DQEJARYVamFtZXMucy5jb3hAZ21haWwuY29tMQswCQYDVQQGEwJVUzEZMBcGA1UECgwQU2VydmljZSBQcm92aWRlcjELMAkGA1UECwwCc3AxDTALBgNVBAMMBGpjb3gwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAIvtAFv5pWA6oRlLz/JhEG1Y7+6Wcvc2aU4rAH4PdWlmr5YkpA/gEd/J25AilVIE+c8nQwIzn5rDgJFGoBfjN/jUkKWLcB5jZC8SFzEx5zvGnR9wWgnbBL+jWQTal8M10ilhbRfklYLpkyTrwYWQdpDmMqVrN9wbNgU2imTihooNAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAdwn3tIk3612PRNtkxcuW/O5MKQyoy6KTS7V95KklJX452zebWDeM6/dR+XWVJLhRfN8+87cyos5HMYjIFVUJFNl9W5+UnRK75ku2hHBvPuU3ZTuW3/vGrNOLuaroPqELp+bNbfZkr2hezpeQl/4JKRFE418dvJJXW2OxE4A1K0sAAAACAANpZHAAAAErlm6iEQAFWC41MDkAAAJOMIICSjCCAbMCBEyxxXgwDQYJKoZIhvcNAQEFBQAwbDEkMCIGCSqGSIb3DQEJARYVamFtZXMucy5jb3hAZ21haWwuY29tMQswCQYDVQQGEwJVUzEaMBgGA1UECgwRSWRlbnRpdHkgUHJvdmlkZXIxDDAKBgNVBAsMA2lkcDENMAsGA1UEAwwEamNveDAeFw0xMDEwMTAxMzU0MDBaFw0yMDEwMDcxMzU0MDBaMGwxJDAiBgkqhkiG9w0BCQEWFWphbWVzLnMuY294QGdtYWlsLmNvbTELMAkGA1UEBhMCVVMxGjAYBgNVBAoMEUlkZW50aXR5IFByb3ZpZGVyMQwwCgYDVQQLDANpZHAxDTALBgNVBAMMBGpjb3gwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAIt33orAL6MajhA8jeXaf8aPbIX24dlv0HwBBmdLBzkrO0I3bELtKSzbKFBkIwQZKaFHYdT7cxmy1epwffYsX2Ipguz99wGgH92GiWCLuPr14HqMAz/wx/1pAFFERa5rxadq0Jxmk1SF8gdz7FtoQOT0WUnIcs20yXta0Abqd1AxAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAOCsgCB7zc/OrY8u54nUb2apJEZ20sSO48ilzn5PoGBQxFZJIg0E0eBTfCTNGQuF5anI4NZ7Q0gTRT8IBxeiPhksz+5NG4eCb6+4VyKJszx6rY7S6uq/08N3EMru5jyNVEn/o3F1QpwtlMDipH8s+dpuR6sEAcpugQYBH6F1MfI1iptlkKubwxS31ebaol0N4M5BFyA==";
		String keystorePassword = "secret";
		ks.load(new ByteArrayInputStream(Base64Utils.decoder(b64EncodedKeystore)), keystorePassword.toCharArray());
		System.out.println("certificate 2:"+ks.getCertificate("sp"));
	}
}
