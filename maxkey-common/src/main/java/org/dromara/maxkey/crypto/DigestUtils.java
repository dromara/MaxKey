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
 

/**
 * 
 */
package org.dromara.maxkey.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author Crystal.Sea
 *  * algorithm Support  MD5,SHA,SHA-1|SHA-256|SHA-384|SHA-512 then encodeBase64
 *
 */
public final class DigestUtils {

	public final class Algorithm {
		
		public static final String MD5 		= 	"MD5";
		public static final String SHA 		= 	"SHA";
		public static final String SHA1 	= 	"SHA-1";
		public static final String SHA256 	= 	"SHA-256";
		public static final String SHA384 	= 	"SHA-384";
		public static final String SHA512 	= 	"SHA-512";
		
		public static final String Base64 	= 	"Base64";
		
	}
	/**
	 * @param simple
	 * @param algorithm MD5,SHA,SHA-1|SHA-256|SHA-384|SHA-512 then encodeBase64
	 * @return cipher
	 */
	public static String digestBase64(String simple,String algorithm) {
		MessageDigest messageDigest;
		String cipherBASE64="";
		try {
			messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
			messageDigest.update(simple.getBytes());
			byte[] bCipher=messageDigest.digest();
			cipherBASE64=Base64Utils.encodeBase64(bCipher);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return cipherBASE64;
	}
	
	/**
     * @param simple
     * @param algorithm MD5,SHA,SHA-1|SHA-256|SHA-384|SHA-512 then encodeBase64
     * @return cipher
     */
    public static String digestBase64Url(String simple,String algorithm) {
        MessageDigest messageDigest;
        String cipherBASE64="";
        try {
            messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
            messageDigest.update(simple.getBytes());
            byte[] bCipher=messageDigest.digest();
            cipherBASE64=Base64Utils.base64UrlEncode(bCipher);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cipherBASE64;
    }

	//B64
	public static String md5B64(String simple) {
		return digestBase64(simple,Algorithm.MD5);
	}
	
	public static String shaB64(String simple) {
		return digestBase64(simple,Algorithm.SHA);
	}
	
	public static String sha1B64(String simple) {
		return digestBase64(simple,Algorithm.SHA1);
	}
	
	public static String sha256B64(String simple) {
		return digestBase64(simple,Algorithm.SHA256);
	}
	
	public static String sha384B64(String simple) {
		return digestBase64(simple,Algorithm.SHA384);
	}
	
	public static String sha512B64(String simple) {
		return digestBase64(simple,Algorithm.SHA512);
	}
	
	public static String digestHex(String simple,String algorithm) {
		return digestHex( simple.getBytes(),algorithm);
	}
	
	/**
	 * @param simple
	 * @param algorithm  MD5,SHA,SHA-1|SHA-256|SHA-384|SHA-512 then encodeHexString
	 * @return cipher
	 */
	public static String digestHex(byte[] simpleBytes,String algorithm) {
		MessageDigest messageDigest;
		String cipherHex="";
		try {
			messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
			messageDigest.update(simpleBytes);
			byte[] bCipher=messageDigest.digest();
			cipherHex=HexUtils.bytes2HexString(bCipher);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return cipherHex;
	}
	
	
	//HEX
	public static String md5Hex(String simple) {
		return digestHex(simple,Algorithm.MD5);
	}
	
	public static String shaHex(String simple) {
		return digestHex(simple,Algorithm.SHA);
	}
	
	public static String sha1Hex(String simple) {
		return digestHex(simple,Algorithm.SHA1);
	}
	
	public static String sha256Hex(String simple) {
		return digestHex(simple,Algorithm.SHA256);
	}
	
	public static String sha384Hex(String simple) {
		return digestHex(simple,Algorithm.SHA384);
	}
	
	public static String sha512Hex(String simple) {
		return digestHex(simple,Algorithm.SHA512);
	}
	
	
	
}
