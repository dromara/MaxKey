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

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.dromara.maxkey.crypto.HexUtils;
import org.dromara.maxkey.crypto.keystore.KeyStoreUtil;


/** *//**
 * <p>
 * 证书数字签名
 * 
 * @version 1.0
 */
public class CertSigner {

    /** *//**
     * <p>
     * 生成数据签名
     * </p>
     * 
     * @param data 源数�?
     * @param keyStorePath 密钥库存储路�?
     * @param alias x509Certificate alias
     * @param password 密钥库密�?
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, KeyStore keyStore, String alias, String password) throws Exception {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) KeyStoreUtil.getCertificate(keyStore, alias, password);
        // 取得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }
    
    
    /** *//**
     * <p>
     * 验证签名
     * </p>
     * 
     * @param data 已加密数�?
     * @param sign 数据签名[BASE64]
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    public static boolean verifySign(byte[] data, String sign, X509Certificate certificate) 
            throws Exception {
        // 获得公钥
        PublicKey publicKey = certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(certificate.getSigAlgName());
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(HexUtils.hex2Bytes(sign));
    }
    

    
    
    /** *//**
     * <p>
     * 生成数据签名并以BASE64编码
     * </p>
     * 
     * @param data 源数�?
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     * @throws Exception
     */
    public static String sign2Hex(byte[] data, KeyStore keyStore, String alias, String password) 
            throws Exception {
        return HexUtils.bytes2HexString(sign(data, keyStore, alias, password));
    }
    

    /** *//**
     * <p>
     * BASE64解码->签名校验
     * </p>
     * 
     * @param base64String BASE64编码字符�?
     * @param sign 数据签名[BASE64]
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    public static boolean verifyHexSign(String HexString, String sign, X509Certificate certificate) 
            throws Exception {
        byte[] data = HexUtils.hex2Bytes(HexString);
        return verifySign(data, sign, certificate);
    }
    
    /** *//**
     * <p>
     * 生成文件数据签名(BASE64)
     * </p>
     * <p>
     * �?��先将文件私钥加密，再根据加密后的数据生成签名(BASE64)，�?用于小文�?
     * </p>
     * 
     * @param filePath 源文�?
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     * @throws Exception
     */
    public static String signFile2HexWithEncrypt(String filePath, KeyStore keyStore, String alias, String password)
            throws Exception {
        byte[] encryptedData = CertCrypto.encryptFileByPrivateKey(filePath, keyStore, alias, password);
        return sign2Hex(encryptedData, keyStore, alias, password);
    }
    

    

    

 
    
    /** *//**
     * <p>
     * BASE64解码->公钥解密-签名校验
     * </p>
     * 
     * 
     * @param base64String BASE64编码字符�?
     * @param sign 数据签名[BASE64]
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    public static boolean verifyHexSignWithDecrypt(String hexString, String sign, X509Certificate certificate) 
            throws Exception {
        byte[] encryptedData = HexUtils.hex2Bytes(hexString);
        byte[] data = CertCrypto.decryptByPublicKey(encryptedData, certificate);
        return verifySign(data, sign, certificate);
    }
    

    /** *//**
     * <p>
     * 校验证书当前是否有效
     * </p>
     * 
     * @param certificate 证书
     * @return
     */
    public static boolean verifyCertificate(Certificate certificate) {
        return verifyCertificate(new Date(), certificate);
    }
    
    /** *//**
     * <p>
     * 验证证书是否过期或无�?
     * </p>
     * 
     * @param date 日期
     * @param certificate 证书
     * @return
     */
    public static boolean verifyCertificate(Date date, Certificate certificate) {
        boolean isValid = true;
        try {
            X509Certificate x509Certificate = (X509Certificate) certificate;
            x509Certificate.checkValidity(date);
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
    
    
    /** *//**
     * <p>
     * 验证数字证书是在给定的日期是否有�?
     * </p>
     * 
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     */
    public static boolean verifyCertificate(Date date,KeyStore keyStore, String alias, String password) {
        Certificate certificate;
        try {
            certificate = KeyStoreUtil.getCertificate(keyStore, alias, password);
            return verifyCertificate(certificate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** *//**
     * <p>
     * 验证数字证书当前是否有效
     * </p>
     * 
     * @param keyStorePath 密钥库存储路�?
     * @param alias 密钥库别�?
     * @param password 密钥库密�?
     * @return
     */
    public static boolean verifyCertificate(KeyStore keyStore, String alias, String password) {
        return verifyCertificate(new Date(), keyStore, alias, password);
    }
    
}
